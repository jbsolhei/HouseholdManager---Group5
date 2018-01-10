$(document).ready(function() {
    swapContent("dashboard.html")
});

var dashboard = "dashboard.html";
var household = "";
var shoppinglists = "";
var shoppingtrips = "";
var todo = "";
var statistics = "";
var news = "";
var profile = "profile.html";

function callModal(modalContent) {
    $("#modal").load(modalContent);
}

function swapContent(bodyContent) {
    $(".page-wrapper").load(bodyContent);
}