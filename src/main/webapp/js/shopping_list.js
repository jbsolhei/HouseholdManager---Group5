/* --- Variables --- */
var SHL_active;
var SHL_archived;
var current_SHL_index;

/* --- Ajax- methods --- */

/**
 * Ajax-method to create a new Shopping List given a shopping list name
 *
 * @param shoppingListName, the shopping list name
 * @param userIds, an array of users you want to be associated with the new list
 * @success returns the generated shopping list ID, and updates users
 */
function ajax_createNewList(shoppingListName, userIds) {
    console.log('ajax_createNewList()');
    console.log('data:' + shoppingListName);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/',
        data: shoppingListName,
        contentType: 'text/plain',
        success: function (shoppingListId) {
            console.log("success: ajax_createNewList(). data: " + shoppingListId);
            ajax_updateUsers(userIds, shoppingListId);
        },
        error: function () {
            console.log("error: ajax_createNewList()");
        }
    })
}

/**
 * Ajax-method to update users associated with a shopping list
 *
 * @param userIds, an array of user IDs that are associated with the shopping list
 * @param shoppingListId, the shopping list ID
 */
function ajax_updateUsers (userIds, shoppingListId) {
    console.log('ajax_updateUsers()');
    console.log(JSON.stringify(userIds));
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/users',
        data: JSON.stringify(userIds),
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("success: ajax_updateUsers()");
        },
        error: function () {
            console.log("error: ajax_updateUsers()");
        }
    })
}

/**
 * Ajax-method to get a shopping list given it's shopping list ID
 *
 * @param shoppingListId, the shopping list ID
 */
function ajax_getShoppingList(shoppingListId, handleData) {
    console.log('ajax_getShoppingList()');
    ajaxAuth({
        type: 'GET',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log("success: ajax_getShoppingList()");
            handleData(data);
        },
        error: function (data) {
            console.log("error: ajax_getShoppingList()");
            console.log(data);
        }
    })
}

/**
 * Ajax-method that updates checkedBy to an Item given the item ID
 * @param itemId, the item ID
 */
function ajax_updateCheckedBy(itemId) {
    console.log('ajax_updateCheckedBy()');
    console.log('json:' + JSON.stringify(getCurrentUser().userId));
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/items/' + itemId + '/user/',
        data: JSON.stringify(getCurrentUser().userId),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("success: ajax_updateCheckedBy()");
        },
        error: function (result) {
            console.log("error: ajax_updateCheckedBy()");
            console.log(result);
        }
    })
}

/**
 * Deletes a shopping list given the shopping list ID
 *
 * @param shoppingListId, the shopping list ID
 */
function ajax_deleteShoppingList(shoppingListId) {
    console.log('ajax_deleteShoppingList()');
    ajaxAuth({
        type: 'DELETE',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        success: function () {
            console.log("success: ajax_deleteShoppingList()");
        },
        error: function (result) {
            console.log("error: ajax_deleteShoppingList()");
            console.log(result);
        }
    })
}

/**
 * Ajax-method to update the archived status of a shopping list
 *
 * @param shoppingListId, the shopping list ID
 * @param archived, the wanted value of archived
 */
function ajax_updateArchived(shoppingListId, archived){
    console.log('ajax_updateArchived()');
    ajaxAuth({
        type: 'PUT',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        data: archived,
        contentType: 'text/plain',
        success: function () {
            console.log("success: ajax_updateArchived()");
        },
        error: function (result) {
            console.log("error: ajax_updateArchived()");
            console.log(result);
        }
    })
}

function ajax_getShoppingListUsers(shoppingListId, handleData) {
    console.log('ajax_getShoppingListUsers()');
    ajaxAuth({
        type: 'GET',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/users',
        data: shoppingListId,
        contentType: 'text/plain',
        success: function (data) {
            console.log("success: ajax_getShoppingListUsers()")
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_getShoppingListUsers()");
            console.log(result);
        }
    })
}

/* --- visual updates methods --- */


function readyShoppingList(){
    SHL = getCurrentHousehold().shoppingLists;
    if(SHL!==null&&SHL!==undefined)numberOfLists = SHL.length;
    if(numberOfLists>0){
        insertShoppingLists();
        $("#shoppingList" + activeSHL).addClass("active");
        showList(activeSHL);
    }
}

/**
 * Function to load the side menu
 */
function loadSideMenu(){
    SHL = getCurrentHousehold().shoppingLists;
    console.log(SHL);
    if(SHL!==null&&SHL!==undefined)numberOfLists = SHL.length;
    if(numberOfLists>0){
        var firstActive = true;
        var firstArchived = true;
        var inputStringActive = "<ul id=\"shopping-list-active-side-menu\" class=\"nav nav-pills nav-stacked\">";
        var inputStringArchived = "<ul id=\"shopping-list-archived-side-menu\" class=\"nav nav-pills nav-stacked\">";
        $.each(SHL, function(i,val){
            if (val.archived) {
                if (firstActive) {
                    activeSHL = i;
                    firstActive = false;
                }
                inputStringArchived += '<li onclick="showListFromMenu(' + i + ')" id="shoppingList' + i + '"><a>' + val.name + '</a></li>';

            } else {
                if (firstArchived) {
                    archivedSHL = i;
                    firstArchived = false;
                }
                inputStringActive += '<li onclick="showListFromMenu(' + i + ')" id="shoppingList' + i + '"><a>' + val.name + '</a></li>';
            }
        });
        inputStringActive += '</ul>';
        inputStringArchived += '</ul>';
        $("#shopping-list-active-tab").html(inputStringActive);
        $("#shopping-list-archived-tab").html(inputStringArchived);
    }
    $("#shoppingList" + activeSHL).addClass("active");
}

/**
 * Funtion to show a list from the menu
 * @param SLIndex
 */
function showListFromMenu(SLIndex, isArchived){
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    console.log("SLIndex: " + SLIndex);
    console.log("id: " + SHL[SLIndex].shoppingListId);
    ajax_getShoppingList(SHL[SLIndex].shoppingListId, function (shoppingList) {
        console.log(shoppingList);
        console.log(shoppingList.items.length);
        if(shoppingList.items.length===0){
            $("#emptyListText").removeClass("hide");
        }else{
            $("#emptyListText").addClass("hide");
            var items = shoppingList.items;
            $.each(items,function(i,val){
                console.log('checkedBy: ' + val.checkedBy);
                var checkedBy;
                if(val.checkedBy === null) {
                    checkedBy="";
                    $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="check(' + val.itemId + ')" id="unchecked' + val.itemId + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
                } else {
                    checkedBy = val.checkedBy.name;
                    $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="unCheck(' + val.itemId + ')" id="checked' + val.itemId + '" class="glyphicon glyphicon-check"></span></td><td>' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
                }
            });
        }
        $("#headline").replaceWith('<h4 id="headline">' + shoppingList.name + '</h4>');
        $("#shoppingListItemInput").focus();
        $("#shoppingList" + activeSHL).removeClass("active");
        $("#shoppingList" + SLIndex).addClass("active");
        if (isArchived) archivedSHL = SLIndex;
        else activeSHL = SLIndex;
    });
}

/**
 * Opens the shoppingList based on the ShoppingListIndex
 *
 * @param ShoppingListIndex the index of the shopping list
 */
function navToAShoppingList(shoppingListIndex, isArchived) {
    if (isArchived) archivedSHL = shoppingListIndex;
    else activeSHL = shoppingListIndex;
    showListFromMenu(activeSHL)
}

function showListOfAssociatedUsers() {

    var shoppingListId = SHL[0].shoppingListId;
    console.log(shoppingListId);
    var householdUsers = getCurrentHousehold().residents;
    $("#associated_users_table").empty();
    ajax_getShoppingListUsers(shoppingListId, function (users) {
        console.log(users);
        $("#associated_users_table").append('<thead><tr><th></th><th>' + "Users that can view this list" + '</th></tr></thead>');
        $.each(householdUsers, function (i, val){
            $("#associated_users_table").append('<tbody><tr><td id="associated_user_id_"'+ val.userId +' onclick="checkUser('+ val.userId +')" class="glyphicon glyphicon-unchecked"></td><td>' + val.name + '</td></tr></tbody>');
        });
        $.each(users, function (i, val) {
            $("#associated_user_id_" + val.userId).replaceWith('<td id="associated_user_id_' + val.userId +'" onclick="uncheckUser('+ val.userId +')" class="glyphicon glyphicon-check checked-in-modal"></td>')
        });
        $("#list_of_users_associated_with_shopping_list").slideToggle("slow");
    })
}



