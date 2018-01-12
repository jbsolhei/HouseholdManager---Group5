$(document).ready(function() {
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
    return $.ajax(attr);
}

function callModal(modalContent) {
    $("#modal").load(modalContent);
}

function swapContent(bodyContent) {
    $(".page-wrapper").load(bodyContent);
}