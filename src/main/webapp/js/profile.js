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
    ajaxAuth({
        url: "res/user/"+id+"/hh",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data!==null&&data!==undefined){
                for (var i=0;i<data.length;i++){
                    var val = data[i];
                    var isAdmin = "No";
                    var inputString = "<tr><td>" + val.name + "</td><td>" + val.address + "</td><td>"+isAdmin+"</td></tr>";
                    $("#profile_households_table_body").append(inputString);
                }
            }
        },
        dataType: "json"
    });
    /*getHouseholdsForUser(id, function(data){
        for (var i=0;i<data.length;i++){
            var val = data[i];
            getAdminIds(data[i].houseId,function(data2){
                var isAdmin = "No";
                for (var j=0;j<data2.length;j++){
                    if(id===data2[j]){
                        isAdmin="Yes";
                    }
                }
                var inputString = "<tr><td>" + val.name + "</td><td>" + val.address + "</td><td>"+isAdmin+"</td></tr>";
                $("#profile_households_table_body").append(inputString);
            });
        }
    });*/
}

function printTasksToWall(id){
    ajaxAuth({
        url: "res/user/"+id+"/tasks",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data!==null&&data!==undefined&&data.length!==0){
                for (var i=0;i<data.length;i++){
                    var val = data[i];
                    var inputString = "<tr><td>" + val.description + "</td><td>" + val.date + "</td><td>" + getHouseholdFromId(val.houseId, function (data) {
                        return data.name;
                    }) + "</td></tr>";
                    $("#profile_todos_body").append(inputString);
                }
            } else {
                $("#profile_todos_body").append("No todos!");
            }
        },
        error: function () {
            $("#profile_todos_body").append("No todos! (error)");
        },
        dataType: "json"
    });
    /*getTasksForUser(id, function (data) {
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
    });*/
}