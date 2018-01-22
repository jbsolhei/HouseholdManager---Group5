/**
 * Created by Simen Moen Storvik on 19.01.2018.
 */

//TODO: Replace this variable with something smoother:
var selectedUserForNewChore;
var userChoreList;
var householdChoreList;
var selectedChore;

function readyChores(){
    console.log("readyChores()");
    listUserChores();
    listHouseholdChores();

    //Display selected household chore if selected from dashboard:
    if(activeChore[0]===1){
        showChoreInfo(householdChoreList(activeChore[1]));
        activeChore[0]=0;
    }
}

function listUserChores(){
    console.log("listUserChores()");
    getChoresForUser(getCurrentUser().userId,function(data){
        userChoreList = data;
        var leftUpperTableBodyHTML = "";
        $.each(data,function(i,val){
            console.log("It LOOPS!!");
            leftUpperTableBodyHTML += "<tr onclick='showChoreInfo("+i+")'>" +
                "<td>"+val.title+"</td>" +
                "<td>"+getHouseholdFromId(val.houseId,function(data){return data}).name+"</td>" +
                "<td>"+val.date+"</td></tr>";
        });
        $("#choresLeftUpperTableBody").html(leftUpperTableBodyHTML);
    });
}

function listHouseholdChores() {
    console.log("listHouseholdChores");
    getChoresForHousehold(getCurrentHousehold().houseId, function(data){
        householdChoreList = data;
        var leftLowerTableBodyHTML = "";
        $.each(data,function(i,val){
            console.log("It LOOPS!!");
            leftLowerTableBodyHTML += "<tr onclick='showChoreInfo("+i+")'>" +
                "<td>"+val.title+"</td>" +
                "<td>"+getLocalResident(userId).name+"</td>" +
                "<td>"+val.date+"</td></tr>";
        });
        $("#choresLeftLowerTableBody").html(leftLowerTableBodyHTML);
    });
}
function checkSelectedChore(){
    //TODO: fill with content and decide id/index usage
}

function deleteSelectedChore(id){
    console.log("deleteSelectedChore() on " + id);
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/chores/"+id,
        type: "DELETE",
        success: function(){
            console.log("Chore deleted.");
        },
        error: function(){
            console.log("Error in deleteSelectedChore()");
        }
    })
}

function showChoreInfo(chore){
    console.log("showChoreInfo()");
    switchChoresContent(0);
    selectedChore = chore;
    $("#choresRightUpperPanelHeading").html(chore.title);
    $("#choresDetailsDescriptionContent").html(chore.description);
    $("#choresDetailsDateTimeContent").html(chore.time);
    $("#choresDetailsHouseholdContent").html(getHouseholdFromId((chore.houseId),function (data) {return data;}).name);
    $("#choresDetailsUserNameContent").html(getLocalResident(chore.userId));
    if(chore.done){
        $("#choresDetailsCheckedContent").html("Done");
    }else{
        $("#choresDetailsCheckedContent").html("Not done");
    }
}

function switchChoresContent(num) {
    //TODO: This needs to be updated with a parameter and more body.
    if(num===0){
        $("#choresRightPanelSecondWindow").addClass("hide");
        $("#choresRightPanelFirstWindow").removeClass("hide");
    }else{
        $("#choresRightPanelFirstWindow").addClass("hide");
        $("#choresRightPanelSecondWindow").removeClass("hide");
        $("#newChoreTitleInput").focus();
        var newChoreDropdownHTML = "";

        $.each(getCurrentHousehold().residents, function(i,val){
            newChoreDropdownHTML+="<li id='newChoreDropdownElement"+i+"' onclick='setNewChorePersonFromDropdown("+i+")'><a href='#'>"+val.name+"</a></li>";
        });
        $("#newChoreDropdownMenu").html(newChoreDropdownHTML);
    }
}

function newChoreButtonPressed(){
    console.log($("#newChoreLocalTimeInput").val());

    //input formatting:
    var newChoreTitle = $("#newChoreTitleInput").val();
    var newChoreDescription = $("#newChoreDescriptionInput").val();
    var newChoreDate = $("#newChoreLocalTimeInput").val();
    var newChoreUserId = getCurrentHousehold().residents[selectedUserForNewChore].userId;
    var checked = false;
    var newChore = {title:newChoreTitle, description:newChoreDescription, time: newChoreDate, userId: newChoreUserId, done:checked};

    postNewChore(newChore);
}
function setNewChorePersonFromDropdown(index){
    $("#newChoreDropdownButton").html(getCurrentHousehold().residents[index].name);
    selectedUserForNewChore = index;
    console.log(index);
}

function getChoresForUser(id, handleData){
    console.log("Get chores for user: " + id);
    ajaxAuth({
        url: "res/user/"+id+"/chores",
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

function getChoresForHousehold(id, handleData){
    console.log("Get chores for household: " + id);
    ajaxAuth({
        url: "res/household/"+id+"/chores",
        type: "GET",
        contentType:"application/json; charset=utf-8",
        success: function(data){
            console.log("Success in getChoresForHousehold()");
            console.log(data);
            handleData(data);
        },
        error: function(data){
            console.log("Error in getChoresForHousehold");
            console.log(data);
        }
    });
}

function postNewChore(chore){
    console.log("postNewCore()");
    console.log(chore);
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/chores",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(chore),
        success: function () {
            console.log("Mor di jobbe ikke her kis");
            switchChoresContent(0);
        },
        error:function(data) {
            console.log("Error in postNewChore()");
            console.log(data);
        }
    });
}

function updateChore(chore){
    console.log("updateChore()");
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/chores",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(chore),
        success: function(){
            console.log("Success in updateChore()");
        },
        error: function(data){
            console.log("error in updateChore()");
            console.log(data);
        }
    });
}