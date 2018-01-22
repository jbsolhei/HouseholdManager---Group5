/**
 * Created by Simen Moen Storvik on 19.01.2018.
 */
    //TODO: Replace this variable with something smoother:
var selectedUserForNewChore;

function readyChores(){
    console.log("readyChores()");
    listUserChores();
}

function listUserChores(){
    console.log("listUserChores()");
    getChoresForUser(getCurrentUser().userId,function(data){
        var leftUpperTableBodyHTML;
        $.each(data,function(i,val){
            leftUpperTableBodyHTML += "<tr onclick='showChoreInfo()'>" +
                "<td>"+val.title+"</td>" +
                "<td>"+getHouseholdFromId(val.houseId,function(data){return data}).name+"</td>" +
                "<td>"+val.date+"</td></tr>";
        });
        $("#choresLeftUpperTableBody").html(leftUpperTableBodyHTML);
    });
}

function listHouseholdChores(data) {
    console.log("listHouseholdChores");
    var leftLowerTableBodyHTML;
    $.each(data,function(i,val){
        leftLowerTableBodyHTML += "<tr onclick='showChoreInfo()'>" +
            "<td>"+val.title+"</td>" +
            "<td>"+getLocalResident(userId).name+"</td>" +
            "<td>"+val.date+"</td></tr>";
    });
    $("#choresLeftLowerTableBody").html(leftLowerTableBodyHTML);
}

function showChoreInfo(){
    console.log("showChoreInfo()");
}

function switchChoresContent() {
    //TODO: This needs to be updated with a parameter and more body.
    $("#choresRightPanelFirstWindow").addClass("hide");
    $("#choresRightPanelSecondWindow").removeClass("hide");


    var newChoreDropdownHTML = "";
    $.each(getCurrentHousehold().residents, function(i,val){
        newChoreDropdownHTML+="<li id='newChoreDropdownElement"+i+"' onclick='setNewChorePersonFromDropdown("+i+")'><a href='#'>"+val.name+"</a></li>";
    });
    $("#newChoreDropdownMenu").html(newChoreDropdownHTML);

    $("#newChoreTitleInput").focus();
}

function newChoreButtonPressed(){
    console.log($("#newChoreLocalTimeInput").val());

    //input formatting:
    var newChoreTitle = $("#newChoreTitleInput").val();
    var newChoreDescription = $("#newChoreDescriptionInput").val();
    var newChoreDate = $("#newChoreLocalTimeInput").val();
    //TODO: Watch out for this badboy:
    var newChoreUserId = getCurrentHousehold().residents[selectedUserForNewChore].userId;
    var checked = false;
    //TODO: Fnn ut om user skal være int userId eller user-objekt
    var newChore = {title:newChoreTitle, description:newChoreDescription, date: newChoreDate, userId: newChoreUserId, done:checked};

    postNewChore(newChore);

    $(".newChoreInput").val('');
    $("#newChoreDropdownButton").html("User");
    $("#choresRightPanelSecondWindow").addClass("hide");
    $("#choresRightPanelFirstWindow").removeClass("hide");
}
function setNewChorePersonFromDropdown(index){
    $("#newChoreDropdownButton").html(getCurrentHousehold().residents[index].name);
    selectedUserForNewChore = index;
    console.log(index);
}

function getChoresForUser(id, handleData){
    console.log("Get chores for user: " + id);
    $.ajax({
        url: "/res/user/"+id+"/chores",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            console.log("Success in getChoresForUser()");
            console.log(data);
            handleData(data);
        },
        error: function(data){
            console.log("Error in getChoresForUser()");
            console.log(data);
        }
    })
}

function postNewChore(chore){
    $.ajax({
        url: "insert url here",
        type: "POST",
        dataType: "application/json; charset=utf-8",
        data: chore,
        success: function () {
            console.log("Mor di jobbe ikke her kis");
        },
        error:function() {
            console.log("Error in postNewChore()");
        }
    });
}