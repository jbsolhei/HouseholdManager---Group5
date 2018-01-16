/**
 * Created by camhl on 1/11/2018.
 */
var numberOfItems = 0;
var numberOfDeleteItems = 0;
var activeTab;
var newItems = []; // [shoppinglist[item]]
var deleteItems = [];
var numberOfLists;
var itemsTab = [];

var SHL;

function readyShoppingList(){
    updateCurrentHousehold();
    SHL = getCurrentHousehold().shoppingLists;
    numberOfLists = SHL.length;
    console.log("6: JS updates currentHousehold. numOfLists: " + numberOfLists + ". activeSHL: " + activeSHL + ". SHL.length: " + SHL.length);
    console.log("JS insertsShoppingLists");
    insertShoppingLists();
    $("#shoppingList" + activeTab).addClass("active");
    showList(activeSHL);
}

function insertShoppingLists(){
    var inputString;
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
    $("#unchecked" + itemId).replaceWith('<span onclick="unCheck(' + itemId + ')" name="checked" id="checked' + itemId + '" class="glyphicon glyphicon-check"></span>');
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
    console.log("7: showList() for list #"+SLIndex + " started.");
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
            itemsTab[i] = val.itemId;
            $("#newItem").append('<tr id="item' + itemsTab[i] + '"><td><span onclick="check(' + itemsTab[i] + ')" id="unchecked' + itemsTab[i] + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + val.name + '</td><td><span onclick="deleteItem(' + itemsTab[i] + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        });
    }$("#headline").replaceWith('<h4 id="headline">' + SHL[SLIndex].name + '</h4>');
    $("#item").focus();
    $("#shoppingList" + activeTab).removeClass("active");
    $("#shoppingList" + SLIndex).addClass("active");
    activeTab = SLIndex;
    activeSHL = SLIndex;
    console.log(activeTab);
    console.log("8: showList() for list #"+SLIndex + " ended.");
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
        url: 'res/household/' + 1 + '/shopping_lists/' + SHL[activeTab].shoppingListId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully deleted from database")

            SHL[activeTab] = null;
            $("#" + activeTab).remove();
            for(var i = 0; i < SHL.length; i++){
                if(SHL[i] != null){
                    showList(i);
                    i = SHL.length +1;
                }
            }
        }
    });
}

function okButton(){
    console.log("1: okButton pressed, previous numOfLists: " + numberOfLists);
    numberOfLists += 1;
    console.log("2: Current number of lists: " + numberOfLists);
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
    console.log("5: JS updates activeSHL.");
    activeSHL = SHL[numberOfLists-1];
    readyShoppingList();
}

function addNewList(name){
    console.log("3: addNewList() starts. Name: " + name);
    $.ajax({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/',
        data: JSON.stringify({"name": name}),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
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
                url: 'res/household/' + 1 + '/shopping_lists/' + SHL[activeTab].shoppingListId + "/items",
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
                url: 'res/household/' + 1 + '/shopping_lists/' + SHL[activeTab].shoppingListId + "/items/" + deleteItems[i],
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
    var usersIds = [];
    $('.glyphicon-check').each(function () {
        var id = this.id;
        id = id.replace('uniqueUserId_', '');
        usersIds.push(id);
    });
    console.log(JSON.stringify({'userids': usersIds}));

    $.ajax({
        type: 'POST',
        url: 'res/household/' + 1 + '/shopping_list/' + 3 +'/users',
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
    console.log("yay!");
    var hh = getCurrentHousehold();
    console.log(hh);
    var allUsers = hh.residents;
    ajaxAuth({
        url: 'res/household/1/shopping_lists/3',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(users){
            console.log(users);
            console.log(allUsers);
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