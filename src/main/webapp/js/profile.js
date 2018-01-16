/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */

function loadUser(){
    var currUs = getCurrentUser();
    printInfoToWall(currUs);
    printHouseholdsToWall(currUs.userId);
    printTasksToWall(currUs.userId);
}

function printInfoToWall(current_user){
    $("#profile_information_list_name").html(current_user.name);
    $("#profile_information_list_email").html(current_user.email);
    $("#profile_information_list_phone").html(current_user.telephone);
    console.log(getCurrentUser());
}
function printHouseholdsToWall(id) {
    getHouseholdsForUser(id, function(data){
        $.each(data,function (i, val) {
            console.log(val);
            var admins = val.admins;
            var isAdmin = "No";
            $.each(admins, function(j, adm){
                if(id===adm.userId){
                    isAdmin="Yes";
                }
            });
            var inputString = "<tr><td>" + val.name + "</td><td>" + val.address + "</td><td>"+isAdmin+"</td></tr>";
            $("#profile_households_table_body").append(inputString);
        });
    });
}

function printTasksToWall(id){
    getTasksForUser(id, function (data) {
        if (data.length !== 0) {
            $.each(data, function (i, val) {
                var inputString = "<tr><td>" + val.description + "</td><td>" + val.date + "</td><td>" + getHouseholdFromId(val.houseId, function (data) {
                    return data.name;
                }) + "</td></tr>";
                $("#profile_todos_body").append();
            });
        } else {
            $("#profile_todos_body").append("No todos for you!");
        }
    });
}