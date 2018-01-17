var dashboard = "dashboard.html";
var household = "HouseholdOverview.html";
var shoppinglists = "shoppinglist.html";
var shoppingtrips = "shoppingtrip.html";
var todo = "dashboard.html";
var statistics = "dashboard.html";
var news = "dashboard.html";
var profile = "profile.html";
var activeSHL = 0;


$(document).ready(function() {
    addHouseholdsToList(getCurrentUser().userId);
});

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
                window.localStorage.clear();
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
            setCurrentHousehold(0)
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

function updateCurrentHousehold(bodyContent){
    var id = getCurrentHousehold().houseId;
    ajaxAuth({
        url:"res/household/"+id,
        type: "GET",
        contentType: "application/json; charser=utf-8",
        success: function(data) {
            window.localStorage.setItem("house", JSON.stringify(data));
            if (bodyContent!==undefined){
                $(".page-wrapper").load(bodyContent);

            }
        },
        dataType: "json"
    });
}

function setCurrentHousehold(hid) {
    if (hid === 0){
        ajaxAuth({
            url: "res/user/"+window.localStorage.getItem("userId")+"/hh",
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function (data) {
                if (data.length>0) {
                    window.localStorage.setItem("house", JSON.stringify(data[0]));
                }
                window.location.replace("index.html")
            },
            error: function () {
                console.log("Error in sethh")
            },
            dataType: "json"
        });
    } else {
        ajaxAuth({
            url: "res/household/" + hid,
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function (data) {
                window.localStorage.setItem("house", JSON.stringify(data));
                window.location.replace("index.html")
            },
            error: function () {
                console.log("Error in sethh")
            },
            dataType: "json"
        });
    }
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

function logout() {
    console.log("clicked")
    window.localStorage.clear();
    window.location.replace("OpeningPage.html");
}

//Adds the user's households to the dropdown
function addHouseholdsToList(userId) {
    var households;

    ajaxAuth({
        url:"res/user/"+userId+"/hh",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            households = data;
            households = $.map(data, function(el) { return el });
            for (var i = 0; i < households.length; i++) {
                $("#listOfHouseholds").prepend("<li><a class='householdElement' id='"+households[i].houseId+"'>" + households[i].name + "</a></li>");
            }
            console.log("DATA LOADET");
            $('#coverScreen').css('display', "none");
        },
        dataType: "json"
    });

    $("#currentHouseholdId").text(getCurrentHousehold().name);
}

//Sets the chosen household to current household.
$(document).on('click', '.householdElement', function () {
    var houseId = $(this).attr('id');
    setCurrentHousehold(houseId);
    $("#currentHouseholdId").text($(this).text());
});

function callModal(modalContent) {
    $("#modal").load(modalContent);
}

function callModalRun(modalContent, functions) {
    console.log(modalContent);
    $("#modal").load(modalContent);
    for (var i = 0; i<functions.length; i++) {
        functions[i]();
    }
}

function swapContent(bodyContent) {
    updateCurrentHousehold(bodyContent);
}

function navToShoppingList(shoppingListId){
    activeSHL = shoppingListId;
    swapContent(shoppinglists);
}