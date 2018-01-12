/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    function printInfoToWall(){
        $("#profile_information_list_name").html(currentUser.name);
        $("#profile_information_list_email").html(currentUser.email);
        $("#profile_information_list_phone").html(currentUser.telephone);
    }
    function printHouseholdsToWall(id) {
        getHouseholdsForUser(id, function(data){
            $.each(data,function (i, val) {
                var admins = val.admins;
                var isAdmin = "No";
                $.each(admins, function(j, adm){
                    if(currentUser.userId===adm.userId){isAdmin="Yes"}
                });
                var inputString = "<tr><td>" + val.name + "</td><td>" + val.adress + "</td><td>"+isAdmin+"</td></tr>";
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
        getTasksForUser(id, function (data) {
            if(data.length!==0){
                $.each(data, function(i,val){
                    var inputString = "<tr><td>" + val.description + "</td><td>" + val.date + "</td><td>"+getHouseholdFromId(houseId,function(data){return data.name})+"</td></tr>"
                    $("#profile_todos_body").append();
                });
            }else{$("#profile_todos_body").append("No todos for you!");}
        })
    }
    function getTasksForUser(userId, handleData){
        $.ajax({
            url:"res/user/"+userId+"/tasks",
            type: "GET",
            contentType: "application/json; charser=utf-8",
            success: function(data){
                console.log("getTasksForUser(), profile.html");
                handleData(data);
            },
            error: console.log("Error in getTasksForUser"),
            dataType: "json"
        });
    }


    printInfoToWall();
    printHouseholdsToWall(currentUser.userId);
    printTasksToWall(currentUser.userId);
});