/**
 * Created by camhl on 1/11/2018.
 */
var numberOfItems = 0;
var numberOfDeleteItems = 0;
var newItems = []; // [shoppinglist[item]]
var deleteItems = [];
var numberOfLists;
var itemsTab = [];

var SHL;

function readyShoppingList(){
    SHL = getCurrentHousehold().shoppingLists;
    numberOfLists = SHL.length;
    console.log("6: JS updates currentHousehold. numOfLists: " + numberOfLists + ". activeSHL: " + activeSHL + ". SHL.length: " + SHL.length);
    console.log("JS insertsShoppingLists");
    insertShoppingLists();
    $("#shoppingList" + activeSHL).addClass("active");
    showList(activeSHL);
}

function insertShoppingLists(){
    var inputString = "";
    $.each(SHL, function(i,val){
        inputString += '<li onclick="showList(' + i + ')" id="shoppingList' + i + '"><a>' + val.name + '</a></li>';
    });
    $("#shoppingSideMenu").html(inputString);
}

function additem() {
    var newItem = document.getElementById("item").value;
    if(newItem != "" && newItem != null) {
        newItems[numberOfItems] = newItem;
        numberOfItems += 1;
        $("#emptyListText").addClass("hide");
        $("#newItem").append('<tr id="item' + numberOfItems + '"><td><span onclick="check(' + numberOfItems + ')" id="unchecked' + numberOfItems + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + newItem + '</td><td><span onclick="deleteItem(' + numberOfItems + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        document.getElementById("item").value = "";
    }
    document.getElementById("item").focus();
}

function check(itemId){
    var user = getCurrentUser();
    $.ajax({
        type: 'POST',
        url: 'res/household/shopping_lists/items/'+ itemId +'/user/',
        data: JSON.stringify(user.userId),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully added to database");
            $("#unchecked" + itemId).replaceWith('<span onclick="unCheck(' + itemId + ')" name="checked" id="checked' + itemId + '" class="glyphicon glyphicon-check"></span>');
            $("#checkedBy" + itemId).html(user.name);
        }
    });
}

function unCheck(itemId){
    $.ajax({
        type: 'POST',
        url: 'res/household/shopping_lists/items/'+ itemId +'/user/',
        data: JSON.stringify(0),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("Item successfully added to database");
            console.log(itemId);
            $("#checked" + itemId).replaceWith('<span onclick="check(' + itemId + ')" name="unchecked" id="unchecked' + itemId + '" class="glyphicon glyphicon-unchecked"></span>');
            $("#checkedBy" + itemId).html('');
        }
    });
}

function deleteItem(itemNumber){
    $("#item" + itemNumber).remove();
    console.log(itemNumber);
    deleteItems[numberOfDeleteItems] = itemNumber;
    numberOfDeleteItems += 1;
}

function showList(SLIndex){
    if(newItems.length != 0 || deleteItems.length != 0) {
        saveChanges();
    }
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    var listItems = SHL[SLIndex].items;
    if(listItems.length===0){
        $("#emptyListText").removeClass("hide");
    }else{
        $("#emptyListText").addClass("hide");
        $.each(listItems,function(i,val){
            console.log(typeof val.checkedBy);
            var checkedBy;
            if(val.checkedBy === null) {
                checkedBy="";
                $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="check(' + val.itemId + ')" id="unchecked' + val.itemId + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
            } else {
                checkedBy = val.checkedBy.name
                $("#newItem").append('<tr id="item' + val.itemId + '"><td><span onclick="check(' + val.itemId + ')" id="unchecked' + val.itemId + '" class="glyphicon glyphicon-check"></span></td><td>' + val.name + '</td><td id="checkedBy'+val.itemId+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + val.itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
            }
        });
    }$("#headline").replaceWith('<h4 id="headline">' + SHL[SLIndex].name + '</h4>');
    $("#item").focus();
    $("#shoppingList" + activeSHL).removeClass("active");
    $("#shoppingList" + SLIndex).addClass("active");
    activeSHL = SLIndex;
}

function createNewList(name){
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    $("#emptyListText").removeClass("hide");
    $("#headline").addClass("hide");
    $("#headlineInput").removeClass("hide");
    $("#headlineInput").focus();
    $("#okButton").removeClass("hide");
    //$("#date").replaceWith('<h5 id="date"><span class="glyphicon glyphicon-time"></span> Post by Camilla Larsen' + date + '</h5>');
    $("#" + activeSHL).removeClass("active");
}

function deleteList(){
    $.ajax({
        type: 'DELETE',
        url: 'res/household/' + 1 + '/shopping_lists/' + SHL[activeSHL].shoppingListId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully deleted from database");
            if(activeSHL!==0)activeSHL--;
            navToShoppingList(activeSHL);
        }
    });
}

function okButton(){
    console.log("1: okButton pressed, previous numOfLists: " + numberOfLists);
    numberOfLists += 1;
    console.log("2: Current number of lists: " + numberOfLists);
    var name = $("#headlineInput").val();
    $("#headlineInput").value = "";
    $("#headline").removeClass("hide");
    $("#headline").replaceWith('<h4 id="headline">' + name + '</h4>');
    $("#headlineInput").addClass("hide");
    $("#okButton").addClass("hide");
    $("#sideMenu").append('<li onclick="showList(' + numberOfLists + ')" id="shoppingList' + numberOfLists + '"><a>' + name + '</a></li>');
    $("#item").focus();
    $("#" + numberOfLists).addClass("active");
    addNewList(name);
    console.log("5: JS updates activeSHL.");
    activeSHL = numberOfLists-1;
    navToShoppingList(activeSHL);
}

function addNewList(name){
    console.log("3: addNewList() starts. Name: " + name);
    $.ajax({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/',
        data: name,
        contentType: 'text/plain',
        success: function () {
            console.log("List successfully added to database");
        }
    });
    console.log("4: addNewList() is done.");
}

function saveChanges(){
    for (var j = 0; j < newItems.length; j++) {
        if(newItems[j] !== null) {
            $.ajax({
                type: 'POST',
                url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + SHL[activeSHL].shoppingListId + "/items",
                data: JSON.stringify({'name': newItems[j], 'checkedBy': null}),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function () {
                    console.log("Items successfully saved in database");
                }
            });
        }
    }
    for (var i = 0; i < deleteItems.length; i++) {
        if(deleteItems[i] != null) {
            $.ajax({
                type: 'DELETE',
                url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + SHL[activeSHL].shoppingListId + "/items/" + deleteItems[i],
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function () {
                    console.log("Items successfully deleted in database");
                }
            });
        }
    }

    newItems = [];
    deleteItems = [];
}

function updateUsers() {
    var hh = getCurrentHousehold();
    var usersIds = [];
    $('.glyphicon-check').each(function () {
        var id = this.id;
        id = id.replace('uniqueUserId_', '');
        usersIds.push(id);
    });
    console.log(JSON.stringify({'userids': usersIds}));
    $.ajax({
        type: 'POST',
        url: 'res/household/' + hh.houseId + '/shopping_list/' + SHL[activeSHL].shoppingListId +'/users',
        data: JSON.stringify(usersIds),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully added to database")
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function getUsers() {
    var hh = getCurrentHousehold();
    var allUsers = hh.residents;
    ajaxAuth({
        url: 'res/household/' + hh.houseId + '/shopping_lists/' + SHL[activeSHL].shoppingListId,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(users){
            for (var i = 0; i<allUsers.length; i++) {
                var userId = allUsers[i].userId;
                $("#inList").append('<tr><td id="uniqueUserId_'+ userId +'" onclick="checkUser('+ userId +')" class="glyphicon glyphicon-unchecked"></td><td>' + allUsers[i].name + '</td></tr>');
            }
            for (var i = 0; i<users.length; i++) {
                console.log(users[i].userId);
                $("#uniqueUserId_" + users[i].userId).replaceWith('<td id="uniqueUserId_' + users[i].userId +'" onclick="uncheckUser('+ users[i].userId +')" class="glyphicon glyphicon-check"></td>')
            }
        },
        error: function(data) {
            console.log("Error in getUsers");
            console.log(data);
        },
        dataType: "json"
    });
}

function checkUser(userId) {
    console.log("check user:" + userId);
    $("#uniqueUserId_" + userId).replaceWith('<td id="uniqueUserId_' + userId +'" onclick="uncheckUser('+ userId +')" class="glyphicon glyphicon-check"></td>')
}

function uncheckUser(userId) {
    console.log("uncheck user:" + userId);
    $("#uniqueUserId_" + userId).replaceWith('<td id="uniqueUserId_' + userId +'" onclick="checkUser('+ userId +')" class="glyphicon glyphicon-unchecked"></td>')
}

/* Make it so that you can use the 'enter'-key to add items*/
$("#item").keyup(function(event) {
    if (event.keyCode === 13) {
        $("#additem").click();
    }
});

$("#headlineInput").keyup(function(event){
    if(event.keyCode == 13){
        $("#okButton").click();
    }
});

function isEmpty(obj) {

    // null and undefined are "empty"
    if (obj == null) return true;

    // Assume if it has a length property with a non-zero value
    // that that property is correct.
    if (obj.length > 0)    return false;
    if (obj.length === 0)  return true;

    // If it isn't an object at this point
    // it is empty, but it can't be anything *but* empty
    // Is it empty?  Depends on your application.
    if (typeof obj !== "object") return true;

    // Otherwise, does it have any properties of its own?
    // Note that this doesn't handle
    // toString and valueOf enumeration bugs in IE < 9
    for (var key in obj) {
        if (hasOwnProperty.call(obj, key)) return false;
    }

    return true;
}