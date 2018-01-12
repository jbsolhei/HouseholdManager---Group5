/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    function printInfoToWall(id){
        getUserFromRest(id,function (data){
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
    function printHouseholdsToWall(id) {
        getHouseholdsForUser(id, function(data){
            var inputString;
            $.each(data,function () {
                var admins = data.admins;
                var isAdmin = false;
                $.each(admins, function(){
                    if(id===admins.userId){isAdmin=true}
                });
                inputString = "<tr><td>" + data.name + "</td><td>" + data.adress + "</td><td>"+isAdmin?"Yes":"No"+"</td></tr>";
                $("#profile_households_table_body").append(inputString);
            });
        });
    }
    function getHouseholdsForUser(userId, handleData){
        $.ajax({
            url:"res/user/"+userId+"/hh",
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function(data){
                handleData(data);
            },
            dataType: "json"
        });
    }
    function printTasksToWall(id){

    }
    function getTasksForUser(userId, handleData){
        $.ajax({
            url:"res/user/"+userId+"/tasks",
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function(data){
                handleData(data);
            },
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
    printInfoToWall(1);
    printHouseholdsToWall(1);
});