
function ajaxAuth(attr){
    attr.headers = {
        Authorization: "Bearer "+window.sessionStorage.getItem("sessionToken")
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
            currentUser = data;
        },
        dataType: "json"
    });
}

function setCurrentHousehold(id) {
    if (id!==0&&id!==undefined&&id!==null){
        getHouseholdFromId(id, function (hh) {
            currentHousehold = hh;
        });
    }
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