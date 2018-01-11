/**
 * Created by camhl on 1/11/2018.
 */
var numberOfItems = 0;
var activeTab = 0;
var householdId = 1;
var shoppingLists = [];
var items = [[]]; // [shoppinglist[item]]
var numberOfLists = 0;

$(document).ready(function(){
    $.get("res/household/" + householdId + "/shopping_lists", function (SL) {
        console.log("ant shopping lists: " + SL.length);
        numberOfLists = SL.length;
        for(var i = 0; i < SL.length; i++){
            shoppingLists[i] = SL[i];
            insertShoppingLists(i, shoppingLists[i].name)
        }

        $("#" + activeTab).addClass("active");
        showList(0);

    });
});

function insertShoppingLists(shoppingListIndex, shoppingListName){
    $("#sideMenu").append('<li onclick="showList(' + shoppingListIndex + ')" id="' + shoppingListIndex + '"><a>' + shoppingListName + '</a></li>');
}

function additem() {
    var newItem = document.getElementById("item").value;
    if(newItem != "" && newItem != null) {
        numberOfItems += 1;
        $("#newItem").append('<tr id="item' + numberOfItems + '"><td><span onclick="check(' + numberOfItems + ')" id="unchecked' + numberOfItems + '" class="glyphicon glyphicon-asterisk"></span></td><td>' + newItem + '</td><td><span onclick="deleteItem(' + numberOfItems + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        document.getElementById("item").value = "";
    }
    document.getElementById("item").focus();
}

function check(itemNumber){
    $("#unchecked" + itemNumber).replaceWith('<span onclick="unCheck(' + itemNumber + ')" name="checked" id="checked' + itemNumber + '" class="glyphicon glyphicon-ok"></span>');
}

function unCheck(itemNumber){
    $("#checked" + itemNumber).replaceWith('<span onclick="check(' + itemNumber + ')" name="unchecked" id="unchecked' + itemNumber + '" class="glyphicon glyphicon-asterisk"></span>');
}

function deleteItem(itemNumber){
    $("#item" + itemNumber).remove();
}

function showList(SLIndex){
   $.get("res/household/" + householdId + "/shopping_lists/" + shoppingLists[SLIndex].shoppingListId + "/items", function (items) {


        for(var i = 0; i < items.length; i++){
            $("#newItem").append('<tr id="item' + i + '"><td><span onclick="check(' + i + ')" id="unchecked' + i + '" class="glyphicon glyphicon-asterisk"></span></td><td>' + items[i].name + '</td><td><span onclick="deleteItem(' + i + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        }

        $("#headline").replaceWith('<h4 id="headline">' + shoppingLists[SLIndex].name + '</h4>');
        $("#" + activeTab).removeClass("active");
        $("#" + SLIndex).addClass("active");
        activeTab = SLIndex;

   });
}

function createNewList(name){
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    $("#headline").addClass("hide");
    $("#headlineInput").removeClass("hide");
    $("#okButton").removeClass("hide");
    //$("#date").replaceWith('<h5 id="date"><span class="glyphicon glyphicon-time"></span> Post by Camilla Larsen' + date + '</h5>');

}

function deleteList(listName){

}

function okButton(){
    numberOfLists += 0;
    var name = document.getElementById("headlineInput").value;
    $("#headline").removeClass("hide");
    $("#headline").replaceWith('<h2 id="headline">' + name + '</h2>');

    $("#headlineInput").addClass("hide");
    $("#okButton").addClass("hide");

    $("#sideMenu").append('<li onclick="showList(' + numberOfLists + ')" id="' + numberOfLists + '"><a>' + name + '</a></li>');

}

/* Make it so that you can use the 'enter'-key to add items*/
$("#item").keyup(function(event) {
    if (event.keyCode === 13) {
        $("#additem").click();
    }
});
