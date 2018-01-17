/**
 * Created by camhl on 1/11/2018.
 */
var numberOfItems = 0;
var numberOfDeleteItems = 0;
var newItems = []; // [shoppinglist[item]]
var deleteItems = [];
var numberOfLists;
var currentItemList;

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
    var newItem = $("#shoppingListItemInput").val();
    if(newItem !== "" && newItem != null) {
        /*newItems[numberOfItems] = newItem;
        numberOfItems += 1;*/
        $("#emptyListText").addClass("hide");
        console.log("This item gets the ID: " + currentItemList.length);
        $("#newItem").append('<tr id="item' + currentItemList.length+ '"><td><span onclick="check(' + currentItemList.length + ')" id="unchecked' + currentItemList.length + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + newItem + '</td><td id="checkedBy'+currentItemList.length+'"></td><td><span onclick="deleteItem(' + currentItemList.length + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        $("#shoppingListItemInput").text("");
    }
    /*$.ajax({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + SHL[activeSHL].shoppingListId + "/items",
        data: JSON.stringify({'name': newItems[j], 'checkedBy': null}),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("Items successfully saved in database");
        }
    });*/
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
    /*deleteItems[numberOfDeleteItems] = itemNumber;
    numberOfDeleteItems += 1;*/
    $.ajax({
        type: "DELETE",
        url: "res/household/"+getCurrentHousehold().houseId +"/shopping_lists/"+SHL[activeSHL].shoppingListId+"/items/"+currentItemList[itemNumber].itemId,
        success: function(){
            console.log("Item #" + currentItemList[itemNumber].itemId + ", with local number: " + itemNumber + " deleted.");
        }
    });
    navToShoppingList(activeSHL);
}

function showList(SLIndex){
    currentItemList = [];
    if(newItems.length !== 0 || deleteItems.length !== 0) {
        saveChanges();
    }
    $("#newItem").replaceWith('<tbody id="newItem"></tbody>');
    console.log("SLIndex: " + SLIndex);
    currentItemList = SHL[SLIndex].items;
    console.log("Length of current itemlist: " + currentItemList.length);
    if(currentItemList.length===0){
        $("#emptyListText").removeClass("hide");
    }else{
        $("#emptyListText").addClass("hide");
        $.each(currentItemList,function(i, val){
            if(val.checkedBy===null)checkedBy="";
            $("#newItem").append('<tr id="item' + i + '"><td><span onclick="check(' + i + ')" id="unchecked' + i + '" class="glyphicon glyphicon-unchecked"></span></td><td>' + val.name + '</td><td id="checkedBy'+i+'">'+checkedBy+'</td><td><span onclick="deleteItem(' + i + ')" class="glyphicon glyphicon-remove"></span></td></tr>');
        });
    }$("#headline").replaceWith('<h4 id="headline">' + SHL[SLIndex].name + '</h4>');
    $("#shoppingListItemInput").focus();
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
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + SHL[activeSHL].shoppingListId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("List successfully deleted from database")
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
    $("#shoppingListItemInput").focus();
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

        }
    }
    newItems = [];
    deleteItems = [];
    numberOfDeleteItems = 0;
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
        url: 'res/household/'+hh.houseId+'/shopping_lists/'+SHL[activeSHL].shoppingListId,
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

function editUsers() {
    console.log("clicked");

}

/* Make it so that you can use the 'enter'-key to add items*/
$("#shoppingListItemInput").keyup(function(event) {
    if (event.keyCode === 13) {
        $("#additem").click();
    }
});

$("#headlineInput").keyup(function(event){
    if(event.keyCode == 13){
        $("#okButton").click();
    }
});