/**
 * Created by Simen Moen Storvik on 19.01.2018.
 */
var currentHouseHold;
var householdUsers;

function readyChores(){
    currentHouseHold = getCurrentHousehold();
    householdUsers = ["Leif", "Gunnar", "Enda en leif"];
    $("#todosRightLowerPanelHeading").html("What to do with this panel...?");
}

function switchChoresContent() {
    if ($("#choresRightPanelSecondRow").hasClass("hide")) {
        $("#choresRightPanelFirstRow").addClass("hide");
        $("#choresRightPanelSecondRow").removeClass("hide");
    }else{
        $("#choresRightPanelSecondRow").addClass("hide");
        $("#choresRightPanelFirstRow").removeClass("hide");
    }
}

function newChoreButtonPressed(){
    console.log($("#newChoreDateInput").val());
    console.log($("#newChoreTimeInput").val());
    console.log($("#newChoreLocalTimeInput").val());
}
function setNewChorePersonFromDropdown(index){
    $("#newChoreDropdownButton").html(householdUsers[index]);
}