
function ajaxAuth(attr){
    attr.headers = {
        Authorization: "Bearer "+sessionToken
    };
    attr.error = function (jqXHR, exception) {
        var msg = '';
        if (jqXHR.status == 401) {
            window.location.replace("login.html")
        } else {
            $("html").html(jqXHR.responseText);
        }
    };
    return $.ajax(attr);
}

function setCurrentUser(id) {
    ajaxAuth({
        url: "res/user/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            currentUser = data;
        },
        dataType: "json"
    });
}

function setCurrentHousehold() {
    ajaxAuth({
        url: "res/user/"+currentUser.userId+"/hh",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            window.location.replace("index.html");
        },
        error: function (data) {
            window.location.replace("login.html");
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
    $("#modal").load(modalContent)
}

function swapContent(bodyContent) {
    $(".page-wrapper").load(bodyContent);
}
function swapContentReload(bodyContent) {
    $(".page-wrapper").load(bodyContent);
    loadDashboard();
}
function navToShoppingList(shoppingListId){
    swapContent("shoppinglist.html");
    showShoppingListById(shoppingListId);
}