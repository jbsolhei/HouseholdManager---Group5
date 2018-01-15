var dashboard = "dashboard.html";
var household = "HouseholdOverview.html";
var shoppinglists = "shoppinglist.html";
var shoppingtrips = "shoppingtrip.html";
var todo = "dashboard.html";
var statistics = "dashboard.html";
var news = "dashboard.html";
var profile = "profile.html";

$(document).ready(function() {
    setCurrentUser(window.localStorage.getItem("userId"));
    setCurrentHousehold(0);
    swapContent("dashboard.html");
});

function ajaxAuth(attr){
    attr.headers = {
        Authorization: "Bearer "+window.localStorage.getItem("sessionToken")
    };
    attr.error = function (jqXHR, exception) {
        console.log("Error: "+jqXHR.status);
    };
    return $.ajax(attr);
}

function setCurrentUser(id) {
    ajaxAuth({
        url: "res/user/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            window.localStorage.setItem("user",JSON.stringify(data));
        },
        dataType: "json"
    });
}

function getCurrentUser() {
    return JSON.parse(window.localStorage.getItem("user"));
}

function getCurrentHousehold() {
    return JSON.parse(window.localStorage.getItem("house"));
}

function setCurrentHousehold(id) {
    if (id==0||id==undefined){
        id = 1;
    }
    ajaxAuth({
        url:"res/user/"+id+"/hh",
        type: "GET",
        contentType: "application/json; charser=utf-8",
        success: function(data){
            window.localStorage.setItem("house",JSON.stringify(data));
            console.log(getCurrentHousehold())
        },
        dataType: "json"
    });
}

function getHouseholdFromId(id,handleData){
    ajaxAuth({
        url: "res/household/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            handleData(data);
        },
        dataType: "json"
    });
}
function getShoppingListsInHousehold(id, handleData){
    ajaxAuth({
        url: "res/household/"+id+"/shopping_lists",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            handleData(data);
        },
        dataType: "json"
    })
}

function callModal(modalContent) {
    $("#modal").load(modalContent);
}

function swapContent(bodyContent) {
    $(".page-wrapper").load(bodyContent);
}

function swapContentRun(bodyContent,functions) {
    $(".page-wrapper").load(bodyContent);
    for (var i = 0;i<functions.length;i++){
        functions[i]();
    }
}

function navToShoppingList(shoppingListId){
    swapContent("shoppinglist.html");
    showShoppingListById(shoppingListId);
}