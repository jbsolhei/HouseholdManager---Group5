var currentUser;
var currentHousehold;

$(document).ready(function() {
    setCurrentUser(1);
    inviteCheck();
    swapContent("dashboard.html")
});

var sessionToken = "validforever";
var dashboard = "dashboard.html";
var household = "HouseholdOverview.html";
var shoppinglists = "shoppinglist.html";
var shoppingtrips = "shoppingtrip.html";
var todo = "dashboard.html";
var statistics = "dashboard.html";
var news = "dashboard.html";
var profile = "profile.html";

function ajaxAuth(attr){
    attr["headers"]["Authorization"] = "Bearer " + sessionToken;
    attr["error"] = function (jqXHR, exception) {
        var msg = '';
        if (jqXHR.status === 0) {
            msg = 'Not connect.\n Verify Network.';
        } else if (jqXHR.status == 404) {
            msg = 'Requested page not found. [404]';
        } else if (jqXHR.status == 500) {
            msg = 'Internal Server Error [500].';
        } else if (exception === 'parsererror') {
            msg = 'Requested JSON parse failed.';
        } else if (exception === 'timeout') {
            msg = 'Time out error.';
        } else if (exception === 'abort') {
            msg = 'Ajax request aborted.';
        } else {
            msg = 'Uncaught Error.\n' + jqXHR.responseText;
        }
        alert(msg)

    };
    return $.ajax(attr);
}

function setCurrentUser(id) {
    $.ajax({
        url: "res/user/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            currentUser = data;
            setCurrentHousehold();
        },
        dataType: "json"
    });
}

function setCurrentHousehold() {
    $.ajax({
        url: "res/user/"+currentUser.userId+"/hh",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            currentHousehold = data[0];
        },
        dataType: "json"
    });
}
function getHouseholdFromId(id,handleData){
    $.ajaxAuth({
        url: "res/household/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            handleData(data);
        },
        dataType: "json"
    });
}

function inviteCheck() {
}

function callModal(modalContent) {
    $("#modal").load(modalContent)
}

function swapContent(bodyContent) {
    $(".page-wrapper").load(bodyContent);
}