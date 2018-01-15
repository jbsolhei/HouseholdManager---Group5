var dashboard = "dashboard.html";
var household = "HouseholdOverview.html";
var shoppinglists = "shoppinglist.html";
var shoppingtrips = "shoppingtrip.html";
var todo = "dashboard.html";
var statistics = "dashboard.html";
var news = "dashboard.html";
var profile = "profile.html";

function ajaxAuth(attr) {
    attr.headers = {
        Authorization: "Bearer " + window.localStorage.getItem("sessionToken")
    };
    if (attr.error === undefined) {
        attr.error = function (jqXHR, exception) {
            console.log("Error: " + jqXHR.status);
        };
    }
    return $.ajax(attr);
}

function checkSession(){
    ajaxAuth({
        url: "res/user/checkSession",
        type: "GET",
        error: function (e) {
            if (e.status == 401){
                window.location.replace("login.html")
            }
        }
    })
}

function setCurrentUser(id) {
    ajaxAuth({
        url: "res/user/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            window.localStorage.setItem("user",JSON.stringify(data));
            setCurrentHousehold()
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

function setCurrentHousehold() {
    var id = window.localStorage.getItem("userId");
    ajaxAuth({
        url:"res/user/"+id+"/hh",
        type: "GET",
        contentType: "application/json; charser=utf-8",
        success: function(data) {
            console.log(data);
            if (data.length > 0) {
                window.localStorage.setItem("house", JSON.stringify(data[0]));
                console.log("User has "+data.length+" households")
            } else {
                console.log("User has no household")
            }
            window.location.replace("index.html")
        },
        error: function () {
            console.log("Error in sethh")
        },
        dataType: "json"
    });
}

function getUserFromId(id, handleData){
    ajaxAuth({
        url: "res/user/"+id,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            handleData(data);
        },
        dataType:"json"
    })
}

function getHouseholdsForUser(userId, handleData){
    ajaxAuth({
        url:"res/user/"+userId+"/hh",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            handleData(data);
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

function getTaskinHousehold(id, handleData){
    ajaxAuth({
        url: "res/household/" + id + "/tasks",
        type: "GET",
        contentType: "application/json; charset=utf8",
        success: function(data){
            handleData(data);
        },
        dataType: "json"
    })
}
function getTasksForUser(userId, handleData){
    ajaxAuth({
        url:"res/user/"+userId+"/tasks",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            console.log("getTasksForUser(), profile.html");
            handleData(data);
        },
        error: console.log("Error in getTasksForUser"),
        dataType: "json"
    });
}

function logout() {
    window.localStorage.clear();
    window.location.replace("login.html")
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