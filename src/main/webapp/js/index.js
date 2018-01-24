var dashboard = "dashboard.html";
var finance = "finance.html";
var household = "HouseholdOverview.html";
var shoppinglists = "shoppinglist.html";
var shoppingtrips = "shoppingtrip.html";
var chores = "chores.html";
var statistics = "dashboard.html";
var news = "news.html";
var profile = "profile.html";
var stats = "stats.html";
var activeSHL = 0;
var archivedSHL = 0;
var householdsLoaded = false;
var activeChore = [0,0];

function ajaxAuth(attr) {
    var attributes = {};
    $.extend(attributes, attr);

    if (attributes.headers === undefined || attributes.headers === null) {
        attributes.headers = {};
    }
    attributes.headers.Authorization = "Bearer " + window.localStorage.getItem("sessionToken");

    attributes.error = function (xhr, textStatus, exceptionThrown) {
        console.log("[AjaxAuth] Error: " + xhr.status);

        if (typeof attr.error === "function") {
            attr.error(xhr, textStatus, exceptionThrown);
        }

        if (xhr.status === 401){
            window.localStorage.clear();
            window.location.replace("OpeningPage.html");
        }
    };

    return $.ajax(attributes);
}

function checkSession(){
    ajaxAuth({
        url: "res/user/checkSession",
        type: "GET",
        error: function (e) {
            if (e.status === 401){
                window.localStorage.clear();
                window.location.replace("OpeningPage.html");
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
            var hid = JSON.parse(window.localStorage.getItem("welcome"));
            if (hid!==null&&hid!==undefined) {
                setCurrentHousehold(hid.houseId)
            } else {
                setCurrentHousehold(0);
            }
        },
        error: function () {
            showLoadingScreen(false);
            alert("Error loading user");
        },
        dataType: "json"
    });
}


function updateCurrentUser(runThisAfter) {
    ajaxAuth({
        url: "res/user/"+getCurrentUser().userId,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            window.localStorage.setItem("user",JSON.stringify(data));
            runThisAfter();
        },
        error: function () {
            showLoadingScreen(false);
            alert("Error loading user");
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

function updateCurrentHousehold(bodyContent, successCallback){
    if (getCurrentHousehold()!==null&&getCurrentHousehold()!==undefined) {
        var id = getCurrentHousehold().houseId;
        ajaxAuth({
            url: "res/household/" + id,
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function (data) {
                window.localStorage.setItem("house", JSON.stringify(data));
                if (bodyContent !== undefined) {
                    $(".page-wrapper").load(bodyContent);

                }

                if (typeof successCallback === "function") {
                    successCallback();
                }
            },
            dataType: "json"
        });
    } else {
        setCurrentHousehold(0);
    }
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
                    window.location.replace("index.html");
                } else {
                    showLoadingScreen(false);
                    callModal("modals/addHousehold.html");
                    $("#theModal").modal();
                }
            },
            error: function () {
                showLoadingScreen(false);
                alert("Error setting household");
            },
            dataType: "json"
        });
    } else {
        ajaxAuth({
            url: "res/household/" + hid,
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function (data) {
                if (data.length!==null) {
                    window.localStorage.setItem("house", JSON.stringify(data));
                    window.location.replace("index.html")
                } else {
                    showLoadingScreen(false);
                    callModal("modals/addHousehold.html");
                    $("#theModal").modal();
                }
            },
            error: function () {
                showLoadingScreen(false);
                alert("Error setting household")
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

function checkWelcome() {
    if (window.localStorage.getItem("welcome")!==null){
        window.localStorage.removeItem("welcome");
        callModal("modals/welcome.html");
        $("#theModal").modal();
    }
}

function logout() {
    console.log("clicked");
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
            householdsLoaded = true;
            console.log("DATA LOADET");
            $('#coverScreen').css('display', "none");
        },
        dataType: "json"
    });

    $("#currentHouseholdId").html(getCurrentHousehold().name + ' <span class="caret"></span>');


}

//Sets the chosen household to current household.
$(document).on('click', '.householdElement', function () {
    var houseId = $(this).attr('id');
    setCurrentHousehold(houseId);
    $("#currentHouseholdId").html($(this).text() + ' <span class="caret"></span>');
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

function getNews(runThisAfter){
    ajaxAuth({
        url:"res/household/"+getCurrentHousehold().houseId+"/news",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            var house = JSON.parse(window.localStorage.getItem("house"));
            house.news = data;
            window.localStorage.setItem("house",JSON.stringify(house));
            runThisAfter(data);
        },
        dataType: "json"
    });
}

function postNews(text,runThisAfter){
    var message = {"message":text};
    ajaxAuth({
        url:"res/household/"+getCurrentHousehold().houseId+"/news",
        type: "POST",
        data: JSON.stringify(message),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            console.log("Success!");
            runThisAfter(data);
            // Do things after post here
        }
    });
}

function deleteNews(newsId,runThisAfter){
    ajaxAuth({
        url:"res/household/"+getCurrentHousehold().houseId+"/news/"+newsId,
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            runThisAfter(data);
            // Do things after delete here
        }
    });
}

function getLocalResident(userId){
    $.each(getCurrentHousehold().residents, function (i, val) {
        if(val.userId===userID){
            return val;
        }
    });
}

//Set show to true if you want to show loadingscreen and false if you want to hide it
function showLoadingScreen(show) {
    if (show) {
        $("#coverScreen").css('display', 'block');
    } else {
        $("#coverScreen").css('display', 'none');
    }
}