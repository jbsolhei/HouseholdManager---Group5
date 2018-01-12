/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    function printInfoToWall(id){
        getUserFromRest(1,function (data){
            $("#profile_information_list_name").html(data.name);
            $("#profile_information_list_email").html(data.email);
            $("#profile_information_list_phone").html(data.telephone);
        })
    }
    function getUserFromRest(id,handleData) {
        $.ajax({
            url: "res/user/"+id,
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            success: function(data){
                console.log("getUserFromRest(), profile.html");
                handleData(data);
            },
            error: console.log("Error in getUserFromRest(), profile.html"),
            dataType: "json"
        });
    }
    function getHouseholdFromRest(id,handleData) {
        $.ajax({
            url: "res/household/"+householdId+"/users",
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            success: function(data){
                handleData(data);
            },
            dataType: "json"
        });
    }

    function printHouseholdsToWall(id){
        getHouseholdsForUser()
        inputString += "<tr><td>" + householdName + "</td><td>" + householdAdress + "</td><td>isAdmin?</td></tr>";
        $("#profile_households_table_body").html(inputString);
    }

    printInfoToWall(1);
});