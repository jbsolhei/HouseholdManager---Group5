/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    var leif = getCurrentUser();
    printInfoToWall(leif);
    printHouseholdsToWall(leif.userId);
    printTasksToWall(leif.userId);
});


function printInfoToWall(current_user){
    $("#profile_information_list_name").html(current_user.name);
    $("#profile_information_list_email").html(current_user.email);
    $("#profile_information_list_phone").html(current_user.telephone);
}
function printHouseholdsToWall(id) {
    getHouseholdsForUser(id, function(data){
        $.each(data,function (i, val) {
            var admins = val.admins;
            var isAdmin = "No";
            $.each(admins, function(j, adm){
                if(id===adm.userId){
                    isAdmin="Yes";
                }
            });
            var inputString = "<tr><td>" + val.name + "</td><td>" + val.adress + "</td><td>"+isAdmin+"</td></tr>";
            $("#profile_households_table_body").append(inputString);
        });
    });
}

function printTasksToWall(id){
    getTasksForUser(id, function (data) {
        if (data.length !== 0) {
            $.each(data, function (i, val) {
                var inputString = "<tr><td>" + val.description + "</td><td>" + val.date + "</td><td>" + getHouseholdFromId(houseId, function (data) {
                    return data.name
                }) + "</td></tr>"
                $("#profile_todos_body").append();
            });
        } else {
            $("#profile_todos_body").append("No todos for you!");
        }
    });
}