/**
 * Created by camhl on 1/11/2018.
 */
var numberOfItems = 0;
var numberOfDeleteItems = 0;
var activeTab = 0;
var householdId = 1;
var shoppingLists = [];
var newItems = []; // [shoppinglist[item]]
var deleteItems = [];
var numberOfLists = 0;
var itemsTab = [];

function ready(){
    $.get("res/household/" + householdId + "/shopping_lists", function (SL) {
        numberOfLists = SL.length;
        for(var i = 0; i < SL.length; i++){
            shoppingLists[i] = SL[i];
            insertShoppingLists(i, shoppingLists[i].name)
        }
        $("#" + activeTab).addClass("active");
        showList(0);
    });
}

/*$(document).ready(function(){
    console.log("current household: " + 1);
    $.get("res/household/" + householdId + "/shopping_lists", function (SL) {
        numberOfLists = SL.length;
        for(var i = 0; i < SL.length; i++){
            shoppingLists[i] = SL[i];
            insertShoppingLists(i, shoppingLists[i].name)
        }
        $("#" + activeTab).addClass("active");
        showList(0);
    });
});*/

function insertShoppingLists(shoppingListIndex, shoppingListName){
    $("#sideMenu").append('<li onclick="showList(' + shoppingListIndex + ')" id="' + shoppingListIndex + '"><a>' + shoppingListName + '</a></li>');
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

function check(itemNumber){
    $("#unchecked" + itemNumber).replaceWith('<span onclick="unCheck(' + itemNumber + ')" name="checked" id="checked' + itemNumber + '" class="glyphicon glyphicon-check"></span>');
}

function unCheck(itemNumber){
    $("#checked" + itemNumber).replaceWith('<span onclick="check(' + itemNumber + ')" name="unchecked" id="unchecked' + itemNumber + '" class="glyphicon glyphicon-unchecked"></span>');
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
    $.get("res/household/" + householdId + "/shopping_lists/" + shoppingLists[SLIndex].shoppingListId + "/items", function (items) {

        if(items.length == 0){
            $("#emptyListText").removeClass("hide");
        } else {
            $("#emptyListText").addClass("hide");
            for (var i = 0; i < items.length; i++) {
                itemsTab[i] = items[i].itemId;
                $("#newItem").append('<tr id="item' + items[i].itemId + '"><td><span onclick="check(' + items[i].itemId + ')" id="unchecked' + items[i].itemId + '" class="\tglyphicon glyphicon-unchecked"></span></td><td>' + items[i].name + '</td><td><span onclick="deleteItem(' + items[i].itemId + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
            }
        }
        $("#headline").replaceWith('<h4 id="headline">' + shoppingLists[SLIndex].name + '</h4>');
        $("#item").focus();
        $("#" + activeTab).removeClass("active");
        $("#" + SLIndex).addClass("active");
        activeTab = SLIndex;
    });
}
function showShoppingListById(listId){
    for(var i = 0; i<shoppingLists.length;i++){
        if(shoppingLists[i].shoppingListId===listId){
            showList(i);
        }
    }
}

function createNewList(name){
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    $("#emptyListText").removeClass("hide");
    $("#headline").addClass("hide");
    $("#headlineInput").removeClass("hide");
    $("#headlineInput").focus();
    $("#okButton").removeClass("hide");
    //$("#date").replaceWith('<h5 id="date"><span class="glyphicon glyphicon-time"></span> Post by Camilla Larsen' + date + '</h5>');
    $("#" + activeTab).removeClass("active");
}

function deleteList(){
    $.ajax({
        type: 'DELETE',
        url: 'res/household/' + 1 + '/shopping_lists/' + shoppingLists[activeTab].shoppingListId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully deleted from database")

            shoppingLists[activeTab] = null;
            $("#" + activeTab).remove();
            for(var i = 0; i < shoppingLists.length; i++){
                if(shoppingLists[i] != null){
                    showList(i);
                    i = shoppingLists.length +1;
                }
            }
        }
    });
}

function okButton(){
    numberOfLists += 1;
    var name = document.getElementById("headlineInput").value;
    document.getElementById("headlineInput").value = "";
    $("#headline").removeClass("hide");
    $("#headline").replaceWith('<h4 id="headline">' + name + '</h4>');

    $("#headlineInput").addClass("hide");
    $("#okButton").addClass("hide");
    $("#sideMenu").append('<li onclick="showList(' + numberOfLists + ')" id="' + numberOfLists + '"><a>' + name + '</a></li>');
    $("#item").focus();
    $("#" + numberOfLists).addClass("active");
    activeTab = numberOfLists;

    addNewList(name);
}

function addNewList(name){
    $.ajax({
        type: 'POST',
        url: 'res/household/' + 1 + '/shopping_lists/',
        data: JSON.stringify({"name": name}),

        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully added to database")
        }
    });
}

function saveChanges(){
    for (var i = 0; i < newItems.length; i++) {
        if(newItems[i] != null) {
            $.ajax({
                type: 'POST',
                url: 'res/household/' + 1 + '/shopping_lists/' + shoppingLists[activeTab].shoppingListId + "/items",
                data: JSON.stringify({'name': newItems[i], 'checkedBy': null}),
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
                url: 'res/household/' + 1 + '/shopping_lists/' + shoppingLists[activeTab].shoppingListId + "/items/" + deleteItems[i],
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

function updateUsers(users) {
    $.ajax({
        type: 'POST',
        url: 'res/household/' + 1 + '/shopping_lists/' + activeTab +'/users',
        data: JSON.stringify({"name": name}),

        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully added to database")
        }
    });
}

function getUsers() {
    var hh = getCurrentHousehold();
    ajaxAuth({
        url: 'res/household/' + hh.houseId + 'shopping_lists/' + activeTab,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(users){
            for (var i = 0; i<users.length; i++) {
                $("#inList").append('<li>' + users[i].name + '</li>');
            }
        },
        error: console.log("Error in getUsers"),
        dataType: "json"
    });
}

function editUsers() {
    console.log("clicked");

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

