/* --- Variables --- */
var SHL_active;
var SHL_archived;
var current_SHL_index;
var userIdsNewShoppingList = [];

/* --- Ajax- methods --- */

function ajax_getShoppingLists(handleData) {
    console.log('ajax_getShoppingLists()');
    ajaxAuth({
        type: 'GET',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/user/' + getCurrentUser().userId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log("success: ajax_getShoppingLists()");
            handleData(data);
        },
        error: function (data) {
            console.log("error: ajax_getShoppingLists()");
            console.log(data);
        }
    })
}

/**
 * Ajax-method to create a new Shopping List given a shopping list name
 *
 * @param shoppingListName, the shopping list name
 * @param userIds, an array of users you want to be associated with the new list
 * @success returns the generated shopping list ID, and updates users
 */
function ajax_createNewList(shoppingListName, handleData) {
    console.log('ajax_createNewList()');
    console.log('data:' + shoppingListName);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/',
        data: shoppingListName,
        contentType: 'text/plain',
        success: function (shoppingListId) {
            console.log("success: ajax_createNewList(). shoppingListId: " + shoppingListId);
            handleData(shoppingListId);
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
function ajax_updateUsers (userIds, shoppingListId, handleData) {
    console.log('ajax_updateUsers()');
    console.log(JSON.stringify(userIds));
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/users',
        data: JSON.stringify(userIds),
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log("success: ajax_updateUsers()");
            handleData(data)
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
    console.log('ajax_getShoppingList(). shoppingListId: ' + shoppingListId);
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
function ajax_updateCheckedBy(itemId, userId, handleData) {
    console.log('ajax_updateCheckedBy(). itemId: ' + itemId + ". userId: " + userId);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/items/' + itemId + '/user/',
        data: JSON.stringify(userId),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log("success: ajax_updateCheckedBy()");
            handleData(data);
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
function ajax_deleteShoppingList(shoppingListId, handleData) {
    console.log('ajax_deleteShoppingList()');
    ajaxAuth({
        type: 'DELETE',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        success: function (data) {
            console.log("success: ajax_deleteShoppingList()");
            handleData(data);
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
function ajax_updateArchived(shoppingListId, archived, handleData){
    console.log('ajax_updateArchived(). shoppingListId: ' + shoppingListId + '. archived: ' + archived);
    ajaxAuth({
        type: 'PUT',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        data: archived,
        contentType: 'text/plain',
        success: function (data) {
            console.log("success: ajax_updateArchived()");
            handleData(data);
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
            console.log("success: ajax_getShoppingListUsers()");
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_getShoppingListUsers()");
            console.log(result);
        }
    })
}

/**
 * Ajax-method to remove a single users association with a shopping list
 *
 * @param shoppingListId, the shopping list ID
 * @param userId, the user ID
 * @param handleData, function to be called upon success
 */
function ajax_deleteUserInShoppingList(shoppingListId, userId, handleData) {
    console.log('ajax_deleteUserInShoppingList(). shoppingListId: ' + shoppingListId + ". userId: " + userId);
    ajaxAuth({
        type: 'DELETE',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/user',
        data: ""+userId,
        contentType: 'text/plain',
        success: function (data) {
            console.log("success: ajax_deleteUserInShoppingList()");
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_deleteUserInShoppingList()");
            console.log(result);
        }
    })
}

/**
 * Ajax-method to add a single users association with a shopping list
 *
 * @param shoppingListId, the shopping list ID
 * @param userId, the user ID
 * @param handleData, function to be called upon success
 */
function ajax_insertUserInShoppingList(shoppingListId, userId, handleData) {
    console.log('ajax_insertUserInShoppingList(). shoppingListId: ' + shoppingListId + ". userId: " + userId);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/user',
        data: ""+userId,
        contentType: 'text/plain',
        success: function (data) {
            console.log("success: ajax_insertUserInShoppingList()");
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_insertUserInShoppingList()");
            console.log(result);
        }
    })
}

function ajax_deleteItem(shoppingListId, itemId, handleData) {
    console.log('ajax_deleteItem(). shoppingListId: ' + shoppingListId + ". itemId: " + itemId);
    ajaxAuth({
        type: 'DELETE',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/items/' + itemId,
        success: function (data) {
            console.log("success: ajax_insertUserInShoppingList()");
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_insertUserInShoppingList()");
            console.log(result);
        }
    })
}

function ajax_addItem(shoppingListId, itemName, handleData) {
    console.log('ajax_addItem(). shoppingListId: ' + shoppingListId + ". itemName: " + itemName);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/items',
        data: itemName,
        contentType: 'text/plain',
        success: function (data) {
            console.log("success: ajax_addItem()");
            handleData(data);
        },
        error: function (result) {
            console.log("error: ajax_addItem()");
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
        showListFromMenu(activeSHL);
    }
}

/**
 * Function to load the side menu
 */
function loadSideMenu(){
    ajax_getShoppingLists(function (ShoppingLists) {
        console.log(ShoppingLists);
        SHL = ShoppingLists;
        if(SHL!==null&&SHL!==undefined)numberOfLists = SHL.length;
        if(numberOfLists>0){
            var firstActive = true;
            var firstArchived = true;
            var inputStringActive = "<ul id=\"shopping-list-active-side-menu\" class=\"nav nav-pills nav-stacked\">";
            var inputStringArchived = "<ul id=\"shopping-list-archived-side-menu\" class=\"nav nav-pills nav-stacked\">";
            $.each(SHL, function(i,val){
                if (val.archived) {
                    if (firstActive) {
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
            $("#shopping_list_active_tab").html(inputStringActive);
            $("#shopping_list_archived_tab").html(inputStringArchived);
        }
        $("#shoppingList" + activeSHL).addClass("active");
        showListFromMenu(activeSHL, false);
    })
}

/**
 * Funtion to show a list from the menu
 * @param SLIndex
 */
function showListFromMenu(SLIndex, isArchived){
    closeListOfAssociatedUsers();
    hideInputHeader();
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    console.log("SLIndex: " + SLIndex);
    ajax_getShoppingList(SHL[SLIndex].shoppingListId, function (shoppingList) {
        console.log(shoppingList);
        if(shoppingList.items.length===0){
            $("#emptyListText").removeClass("hide");
        }else{
            $("#emptyListText").addClass("hide");
            var items = shoppingList.items;
            $.each(items,function(i,val){
                var checkedBy;
                if(val.checkedBy === null) {
                    checkedBy="";
                    $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="checkItem(' + val.itemId + ')" id="unchecked' + val.itemId + '" class="glyphicon glyphicon-unchecked"></span></td><td id="name_item_id_' + val.itemId + '">' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
                } else {
                    checkedBy = val.checkedBy.name;
                    $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="uncheckItem(' + val.itemId + ')" id="checked' + val.itemId + '" class="glyphicon glyphicon-check"></span></td><td id="name_item_id_' + val.itemId + '" class="item-is-checked">' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
                }
            });
        }
        $("#headline").replaceWith('<p id="headline" class="col-md-10">' + shoppingList.name + '</p>');
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

/**
 * method to refresh the list of toggle the list of associated users
 */
function toggleListOfAssociatedUsers() {
    if ($("#list_of_users_associated_with_shopping_list").css('display') === "none") {
        var shoppingListId = SHL[activeSHL].shoppingListId;
        console.log(shoppingListId);
        var householdUsers = getCurrentHousehold().residents;
        $("#associated_users_table").empty();
        ajax_getShoppingListUsers(shoppingListId, function (users) {
            console.log(users);
            $("#associated_users_table").append('<thead><tr><th><button class="btn glyphicon glyphicon-unchecked"></button> Select All</th><th>' + "Users that can view this list" + '</th></tr></thead>');
            $.each(householdUsers, function (i, val) {
                $("#associated_users_table").append('<tbody><tr><td id="associated_user_id_' + val.userId + '" onclick="checkAssociatedUser(' + val.userId + ')" class="glyphicon glyphicon-unchecked"></td><td>' + val.name + '</td></tr></tbody>');
            });
            $.each(users, function (i, val) {
                $("#associated_user_id_" + val.userId).replaceWith('<td id="associated_user_id_' + val.userId + '" onclick="uncheckAssociatedUser(' + val.userId + ')" class="glyphicon glyphicon-check"></td>')
            });
            $("#list_of_users_associated_with_shopping_list").css('display', 'block');
        })
    } else $("#list_of_users_associated_with_shopping_list").css('display', 'none');
}

function closeListOfAssociatedUsers() {
    if ($("#list_of_users_associated_with_shopping_list").css('display') === "block") {
        $("#list_of_users_associated_with_shopping_list").css('display', 'none');
    }
}

/**
 * Replaces the unchecked-icon with a refresh icon
 * Calls the Ajax-method: ajax_updateCheckedBy(), which in turn updates the item given the item id and the user ID
 * the user ID is pulled from getCurrentUser()
 * @param itemId, the item ID
 */
function checkItem(itemId) {
    console.log("The right function is being called :D");
    $("#unchecked" + itemId).addClass("glyphicon-refresh").removeClass("glyphicon-unchecked");
    ajax_updateCheckedBy(itemId, getCurrentUser().userId, function (data) {
        if (data === true) {
            $("#unchecked" + itemId).replaceWith('<span onclick="checkItem(' + itemId + ')" id="unchecked' + itemId + '" class="glyphicon glyphicon-unchecked"></span>');
            $("#name_item_id_" + itemId).addClass("item-is-checked");
            $("#checkedBy" + itemId).html(getCurrentUser().name);
            showListFromMenu(activeSHL);
        } else {
            console.log("error: data === false");
            $("#unchecked" + itemId).addClass("glyphicon-check").removeClass("glyphicon-refresh");
        }
    })
}

/**
 * Replaces the check-icon with a refresh icon
 * Calls the Ajax-method: ajax_updateCheckedBy(), which in turn updates the item given the item id and the user ID
 * the user ID is always 0
 * @param itemId, the item ID
 */
function uncheckItem(itemId) {
    console.log("The right function is being called :D");
    $("#checked" + itemId).addClass("glyphicon-refresh").removeClass("glyphicon-unchecked");
    ajax_updateCheckedBy(itemId, 0, function (data) {
        if (data === true) {
            $("#checked" + itemId).replaceWith('<span onclick="unCheckItem(' + itemId + ')" id="checked' + itemId + '" class="glyphicon glyphicon-check"></span>');
            $("#name_item_id_" + itemId).removeClass("item-is-checked");
            $("#checkedBy" + itemId).html('');
            showListFromMenu(activeSHL);
        } else  {
            console.log("error: data === false");
            $("#checked" + itemId).addClass("glyphicon-unchecked").removeClass("glyphicon-refresh");
        }
    })
}

/**
 * Updates the database, and checks an user as associated with a shopping list
 * The shoppingList
 *
 * @param userId, the user ID
 */
function checkAssociatedUser(userId) {
    console.log("check user:" + userId);
    $("#associated_user_id_" + userId).addClass("glyphicon-refresh").removeClass("glyphicon-unchecked");
    ajax_insertUserInShoppingList(SHL[activeSHL].shoppingListId, userId, function (data) {
        if (data) {
            $("#associated_user_id_" + userId).replaceWith('<td id="associated_user_id_' + userId +'" onclick="uncheckAssociatedUser('+ userId +')" class="glyphicon glyphicon-check"></td>')
        }
    })
}

/**
 * Updates the database, and unchecks an user as associated with a shopping list
 * @param userId
 */
function uncheckAssociatedUser(userId) {
    console.log("uncheck user:" + userId);
    $("#associated_user_id_" + userId).addClass("glyphicon-refresh").removeClass("glyphicon-check");
    ajax_deleteUserInShoppingList(SHL[activeSHL].shoppingListId, userId, function (data) {
        if (data) {
            $("#associated_user_id_" + userId).replaceWith('<td id="associated_user_id_' + userId +'" onclick="checkAssociatedUser('+ userId +')" class="glyphicon glyphicon-unchecked"></td>')
        }
    })
}

/**
 * Deletes an item and refreshes the list
 *
 * @param itemId the itemId
 */
function deleteItem(itemId) {
    console.log("delete item:" + itemId);
    ajax_deleteItem(SHL[activeSHL].shoppingListId, itemId, function (data) {
        if (data) {
            showListFromMenu(activeSHL, false)
        }
    })
}

/**
 * method for adding an item to a list
 * The shopping list ID is pulled from SHL[activeSHL]
 * The shopping list name is pulled from $("#emptyListText")
 */
function addItemToShoppingList() {
    var itemName = $("#shoppingListItemInput").val();
    $("#shoppingListItemInput").replaceWith('<input id="shoppingListItemInput" type="text" class="form-control" name="item" placeholder="Item">');
    if(itemName !== "" && itemName !== null) {
        $("#emptyListText").addClass("hide");
        ajax_addItem(SHL[activeSHL].shoppingListId, itemName, function (data) {
            if (data) {
                showListFromMenu(activeSHL)
            }
        })
    }
}

/**
 * Method to reveal the input field in the header, and set the headline as active
 */
function showInputHeader() {
    hidePanelBody();
    $("#title_header").css('display', 'none');
    $("#input_header").css('display', 'block');
    $("#headlineInput").focus();
    $.each(getCurrentHousehold().residents, function (i, val) {
        userIdsNewShoppingList.push(val.userId);
    });
}

/**
 * Method to hide the input field in the header
 */
function hideInputHeader() {
    if ($("#input_header").css('display') === 'block') {
        $("#input_header").css('display', 'none');
        $("#title_header").css('display', 'block');
        $("#shopping_list_white_space").addClass("hide");
        $("#shopping_list_data_panel").removeClass("hide");
    }
}

/**
 * Function to create a new shoppingList
 * Pulls users to be added from the userIdsNewShoppingList variable
 */
function createNewShoppingList() {
    hideInputHeader();
    closeListOfAssociatedUsersToNewShoppingList();
    console.log($("#text_input_new_shopping_list").val());
    var shoppingListName = $("#text_input_new_shopping_list").val();
    if (shoppingListName !== null || shoppingListName !== '') {
        ajax_createNewList(shoppingListName, function (shoppingListId) {
            if (shoppingListId) {
                ajax_updateUsers(userIdsNewShoppingList, shoppingListId, function () {
                    $("#headline").replaceWith('<p id="headline" class="col-md-10">' + shoppingListName + '</p>');
                    loadSideMenu();
                })
            }
        })
    }
}

/**
 * Function to delete a shopping list
 * The shopping list ID is automatically pulled from the SHL variable
 */
function deleteShoppingList() {
    var shoppingListId = SHL[activeSHL].shoppingListId;
    ajax_deleteShoppingList(shoppingListId, function (data) {
        if (data) {
            ajax_getShoppingLists(function (shoppingLists) {
                SHL = shoppingLists;
                loadSideMenu();
            })
        }
    })
}

/**
 * function to mark a shopping list as archived
 */
function archiveShoppingList() {
    var shoppingListId = SHL[activeSHL].shoppingListId;
    ajax_updateArchived(shoppingListId, "true", function (data) {
        if (data) {
            loadSideMenu();
        }
    })
}

/**
 * function to hide the panel body
 */
function hidePanelBody() {
    $("#shopping_list_data_panel").addClass("hide");
    $("#shopping_list_white_space").removeClass("hide");
}

/**
 * function to assign users to a new shopping list
 * function uses the global variable userIdsNewShoppingList
 */
function toggleListOfAssociatedUsersToNewShoppingList() {
    if ($("#list_of_users_associated_with_shopping_list").css('display') === "none") {

        var householdUsers = getCurrentHousehold().residents;
        $("#associated_users_table").empty();
        $("#associated_users_table").append('<thead><tr><th><button class="btn glyphicon glyphicon-unchecked"></button> Select All</th><th>' + "Users that can view this list" + '</th></tr></thead>');
        $.each(householdUsers, function (i, val) {
            $("#associated_users_table").append('<tbody><tr><td id="associated_user_id_' + val.userId + '" onclick="checkUserInNewShoppingList(' + val.userId + ')" class="glyphicon glyphicon-unchecked"></td><td>' + val.name + '</td></tr></tbody>');

        });
        console.log(userIdsNewShoppingList);
        $.each(userIdsNewShoppingList, function (i, val) {
            console.log(val);
            $("#associated_user_id_" + val).replaceWith('<td id="associated_user_id_' + val + '" onclick="uncheckUserInNewShoppingList(' + val + ')" class="glyphicon glyphicon-check"></td>')
        });
        $("#list_of_users_associated_with_shopping_list").css('display', 'block');
    }
    else {
        $("#list_of_users_associated_with_shopping_list").css('display', 'none');
    }
}

function closeListOfAssociatedUsersToNewShoppingList() {
    if ($("#list_of_users_associated_with_shopping_list").css('display') === "block") {
        $("#list_of_users_associated_with_shopping_list").css('display', 'none');
    }
}

/**
 * function to check off a user to be added to the new shopping list
 * @param userId the user ID. Is automatically generated in the toggleListOfAssociatedUsersToNewShoppingList
 */
function checkUserInNewShoppingList(userId) {
    userIdsNewShoppingList.push(userId);
    $("#associated_user_id_" + userId).replaceWith('<td id="associated_user_id_' + userId + '" onclick="uncheckUserInNewShoppingList(' + userId + ')" class="glyphicon glyphicon-check"></td>');
    console.log(userIdsNewShoppingList);
}

/**
 * function to uncheck a user to be added to the new shopping list
 * @param userId the user ID. Is automatically generated in the toggleListOfAssociatedUsersToNewShoppingList
 */
function uncheckUserInNewShoppingList(userId) {
    removeObjectArray(userIdsNewShoppingList, userId);
    $("#associated_user_id_" + userId).replaceWith('<td id="associated_user_id_' + userId + '" onclick="checkUserInNewShoppingList(' + userId + ')" class="glyphicon glyphicon-unchecked"></td>');
    console.log(userIdsNewShoppingList);
}

/**
 * An helping function to remove an item from an array
 * @param array
 * @param element
 */
function removeObjectArray(array, element) {
    const index = array.indexOf(element);
    array.splice(index, 1);
}