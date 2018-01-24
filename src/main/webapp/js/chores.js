/**
 * Created by Simen Moen Storvik on 19.01.2018.
 */

//TODO: Replace this variable with something smoother:
var selectedUserForNewChore = null;
var userChoreList;
var householdChoreList;
var selectedChore;

function readyChores(){
    console.log("readyChores()");
    switchChoresContent(3);
    listUserChores();
}

function listUserChores(){
    console.log("listUserChores()");
    getChoresForUser(getCurrentUser().userId,function(data){
        userChoreList = data;
        var leftUpperTableBodyHTML = "";
        $.each(data,function(i,val){
            if(Date.now()>toJSDate(val.time)&&!val.done){
                leftUpperTableBodyHTML+="<tr class='clickableChore overdueChoreListElement'";
            }else if(Date.now()>toJSDate(val.time)&&val.done){
                leftUpperTableBodyHTML += "<tr class='hide'";
            }else if(Date.now()<toJSDate(val.time)&&val.done){
                leftUpperTableBodyHTML+="<tr class='clickableChore checkedChoreListElement'";
            }else{
                leftUpperTableBodyHTML += "<tr class='clickableChore'";
            }
            leftUpperTableBodyHTML += " onclick='selectChoreInfo(0,"+i+")'>" +
                "<td>"+val.title+"</td>" +
                "<td>"+val.user.name+"</td>" +
                "<td>"+val.time.dayOfMonth + "."+val.time.monthValue+"." + val.time.year+"</td></tr>";
        });
        $("#choresLeftUpperTableBody").html(leftUpperTableBodyHTML);
        listHouseholdChores();
    });
}

function toJSDate(ltd) {
    var y = ltd.year;
    var mon = ltd.monthValue-1;
    var d = ltd.dayOfMonth;
    var h = ltd.hour;
    var min = ltd.minute;
    return new Date(y,mon,d,h,min);
}

function listHouseholdChores() {
    console.log("listHouseholdChores()");
    getChoresForHousehold(getCurrentHousehold().houseId, function(data){
        householdChoreList = data;
        var leftLowerTableBodyHTML = "";
        $.each(data,function(i,val){
            var today = new Date;
            if(today>toJSDate(val.time)&&!val.done){
                leftLowerTableBodyHTML+="<tr class='clickableChore overdueChoreListElement'";
            }else if(today>toJSDate(val.time)&&val.done){
                leftLowerTableBodyHTML += "<tr class='hide'";
            }else if(today<toJSDate(val.time)&&val.done){
                leftLowerTableBodyHTML+="<tr class='clickableChore checkedChoreListElement'";
            }else{
                leftLowerTableBodyHTML += "<tr class='clickableChore'";
            }
            leftLowerTableBodyHTML += " onclick='selectChoreInfo(1,"+i+")'>" +
                "<td>"+val.title+"</td>";
            if(val.user==null){
                leftLowerTableBodyHTML += "<td>No User</td>"
            } else {
                leftLowerTableBodyHTML += "<td>"+val.user.name+"</td>"
            }
            leftLowerTableBodyHTML +=
                "<td>"+val.time.dayOfMonth + "."+val.time.monthValue+"." + val.time.year+"</td></tr>";
        });
        $("#choresLeftLowerTableBody").html(leftLowerTableBodyHTML);
        if(selectedChore!==undefined) {
            showChoreInfo(getSelectedChoreFromUpdatedTotal());
        }
    });
}
function getSelectedChoreFromUpdatedTotal(){
    $.each(userChoreList,function(i,val){
        if(val.choreId===selectedChore.choreId){
            return val;
        }
    });
    $.each(householdChoreList,function (i,val){
        if(val.choreId===selectedChore.choreId){
            return val;
        }
    });
}
function checkSelectedChore(chore){
    chore.done = !chore.done;
    chore.time = chore.time.year + "-" + pad(chore.time.monthValue) + "-" + pad(chore.time.dayOfMonth) + "T" + pad(chore.time.hour) + ":" + pad(chore.time.minute);
    selectedChore = undefined;
    updateChore(chore);
}

function deleteSelectedChore(id){
    console.log("deleteSelectedChore() on " + id);
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/chores/"+id,
        type: "DELETE",
        success: function(){
            console.log("Chore deleted.");
            selectedChore = undefined;
            readyChores();
        },
        error: function(data){
            console.log("Error in deleteSelectedChore()");
            console.log(data);
        }
    })
}

function selectChoreInfo(from, choreId){
    console.log("selectChoreInfo()");
    if (from===0){
        selectedChore = userChoreList[choreId];
    } else {
        selectedChore = householdChoreList[choreId];
    }
    showChoreInfo(selectedChore);
}
function showChoreInfo(chore){
    console.log("showChoreInfo");
    if(chore!==undefined){
        switchChoresContent(0);
        $("#choresRightUpperPanelHeading").html(chore.title);
        $("#choresDetailsDescriptionContent").html(chore.description);
        $("#choresDetailsDateTimeContent").html(chore.time.dayOfMonth + "."+chore.time.monthValue+"." + chore.time.year + " " + chore.time.hour + ":" + chore.time.minute);
        getHouseholdFromId((chore.houseId),function (data) {$("#choresDetailsHouseholdContent").html(data.name);});
        $("#choresDetailsUserNameContent").html(chore.user==null?"No user":chore.user.name);
        if(chore.done){
            $("#choresDetailsCheckedContent").html("&#9745");
        }else{
            $("#choresDetailsCheckedContent").html("&#9744");
        }
        if(chore.user!==null&&chore.user!==undefined){
            chore.userId = chore.user.userId;
        }
    }else{
        switchChoresContent(3);
    }

}

function switchChoresContent(num) {

    if(num===0){
        $("#choresRightPanelSecondWindow").addClass("hide");
        $("#choresRightPanelThirdWindow").addClass("hide");
        $("#choresRightPanelFirstWindow").removeClass("hide");
    }else if(num===1){
        $("#choresRightPanelFirstWindow").addClass("hide");
        $("#choresRightPanelThirdWindow").addClass("hide");
        $("#choresRightPanelSecondWindow").removeClass("hide");
        $(".newChoreInput").val("");
        $("#newChoreTitleInput").focus();
        $("#newChoreDropdownButton").html("No user");
        selectedUserForNewChore = null;
        var newChoreDropdownHTML = "";
        $.each(getCurrentHousehold().residents, function(i,val){
            newChoreDropdownHTML+="<li id='newChoreDropdownElement"+i+"' onclick='setNewChorePersonFromDropdown("+i+")'><a href='#'>"+val.name+"</a></li>";
        });
        $("#newChoreDropdownMenu").html(newChoreDropdownHTML);
    }else if(num===2){
        $("#choresRightPanelFirstWindow").addClass("hide");
        $("#choresRightPanelSecondWindow").addClass("hide");
        $("#choresRightPanelThirdWindow").removeClass("hide");
        var newChoreDropdownHTML = "";
        $.each(getCurrentHousehold().residents, function(i,val){
            newChoreDropdownHTML+="<li id='editChoreDropdownElement"+i+"' onclick='setNewChorePersonFromDropdown("+i+")'><a href='#'>"+val.name+"</a></li>";
        });
        $("#editChoreDropdownMenu").html(newChoreDropdownHTML);
    }else if(num===3){
        $("#choresRightPanelFirstWindow").addClass("hide");
        $("#choresRightPanelSecondWindow").addClass("hide");
        $("#choresRightPanelThirdWindow").addClass("hide");
    }
}

function newChoreButtonPressed(){

    //input formatting:
    var newChoreTitle = $("#newChoreTitleInput").val();
    var newChoreDescription = $("#newChoreDescriptionInput").val();
    var newChoreDate = $("#newChoreLocalTimeInput").val();
    var newChoreUserId;
    if(selectedUserForNewChore!==null){
        newChoreUserId = getCurrentHousehold().residents[selectedUserForNewChore].userId;
        selectedUserForNewChore = null;
    }else{
        newChoreUserId = 0;
    }
    var newHouseId = getCurrentHousehold().houseId;
    var checked = false;
    var newChore = {houseId:newHouseId,title:newChoreTitle, description:newChoreDescription, time: newChoreDate, userId: newChoreUserId, done:checked};
    console.log(newChore);
    postNewChore(newChore);
}
function setNewChorePersonFromDropdown(index){
    $("#newChoreDropdownButton").html(getCurrentHousehold().residents[index].name);
    $("#editChoreDropdownButton").html(getCurrentHousehold().residents[index].name);
    selectedUserForNewChore = index;
}

function editChore(chore){
    switchChoresContent(2);
    $("#editChoreTitleInput").val(chore.title);
    $("#editChoreDescriptionInput").val(chore.description);
    $("#editChoreDropdownButton").html(chore.user==null?"No user":chore.user.name);
    document.getElementById("editChoreLocalTimeInput").value = (chore.time.year+"-"+pad(chore.time.monthValue)+"-"+chore.time.dayOfMonth+"T"+pad(chore.time.hour)+":"+pad(chore.time.minute));
    console.log(chore.time.year+"-"+pad(chore.time.monthValue)+"-"+chore.time.dayOfMonth+"T"+pad(chore.time.hour)+":"+pad(chore.time.minute));
}
function editChoreButtonPressed(){
    var editedChoreTitle = $("#editChoreTitleInput").val();
    var editedChoreDescription = $("#editChoreDescriptionInput").val();
    var editedChoreDate = $("#editChoreLocalTimeInput").val();
    var editedChoreUserId;
    if(selectedUserForNewChore!==null){
        editedChoreUserId = getCurrentHousehold().residents[selectedUserForNewChore].userId;
        selectedUserForNewChore = null;
    }else{
        editedChoreUserId = selectedChore.userId;
    }
    var editedChoreDone = selectedChore.done;
    var editedChore = {choreId:selectedChore.choreId, title:editedChoreTitle, description:editedChoreDescription, time: editedChoreDate, userId: editedChoreUserId, done:editedChoreDone};
    console.log(editedChore);
    updateChore(editedChore);
}
function pad(n){
    return ((""+n).length<2)?("0"+n):(""+n);
}

function getChoresForUser(id, handleData){
    console.log("Get chores for user: " + id);
    ajaxAuth({
        url: "res/user/"+id+"/chores",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(data){
            console.log("Success in getChoresForUser()");
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
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/chores",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(chore),
        success: function () {
            console.log("Mor di jobbe ikke her kis");
            selectedChore = undefined;
            readyChores();
            switchChoresContent(3);
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
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(chore),
        success: function(data){
            console.log("Success in updateChore()");
            console.log("Updateresult: ");
            console.log(data);
            readyChores();
        },
        error: function(data){
            console.log("error in updateChore()");
            console.log(data);
        }
    });
}