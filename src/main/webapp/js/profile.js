/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
var currUs;
function loadUser(){
    currUs = getCurrentUser();
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
                    console.log(val);
                    var inputString = "<tr><td>" + val.name + "</td><td>" + val.address + "</td></tr>";
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

function editUserInfo() {
    $("#edit_profile_name").html("");
    $("#edit_profile_name").html("<h3><input class='form-control' type='text' id='edit_profile_information_name'" +
        "value='"+currUs.name+"' placeholder='Name'></h3>");

    $("#edit_profile_email").html("");
    $("#edit_profile_email").html("<input class='form-control' type='email' id='edit_profile_information_email'" +
        "value='"+currUs.email+"' placeholder='Email'>");

    $("#edit_profile_phone").html("");
    $("#edit_profile_phone").html("<input class='form-control' type='number' id='edit_profile_information_phone' " +
        "value='"+currUs.telephone+"' placeholder='Telephone'>");

    $("#changePassword").html("<label>Change password:</label><div class='form-group'><label class='control-label col-md-3 profile_labels' for=''>Current password:</label>"+
        "<div class='col-md-9'>" +
        "<input class='form-control' type='password' id='currentPassword'></div></div>" +
        "<div class='form-group'><label class='control-label col-md-3 profile_labels' for=''>New password:</label>"+
        "<div class='col-md-9'>" +
        "<input class='form-control' type='password' id='newPassword'></div></div>" +
        "<div class='form-group'><label class='control-label col-md-3 profile_labels' for=''>Repeat password:</label>"+
        "<div class='col-md-9'>" +
        "<input class='form-control' type='password' id='repeatPassword'></div></div>");

    $("#edit_button").html("");
    $("#edit_button").html("<div onclick='changePassword()' class='panel-footer cursorPointer' id='profile-footer'>Save information</div>");

}

function changePassword() {
    var oldPassword = $("#currentPassword").val();
    var newPassword = $("#newPassword").val();
    var repeatPassword = $("#repeatPassword").val();

    console.log(oldPassword);

    if(oldPassword == "") {
        saveInformation();
    } else {
        if(newPassword.length < 8) {
            document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
                '<strong>Weak password! </strong> Use at least 8 characters</div>';
        } else {
            if (newPassword === repeatPassword) {

                ajaxAuth({
                    url: "res/user/"+getCurrentUser().userId+"/checkPassword",
                    type: 'POST',
                    contentType: 'text/plain; charset=utf-8',
                    data: oldPassword,
                    success: function (res) {
                        console.log(res);
                        if (res == "true") {
                            updatePassword(newPassword);
                           // saveInformation();
                        } else {
                            document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
                                '<strong>Current password does not match</strong></div>';
                        }
                    }
                });
            } else {
                document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
                    '<strong>New password does not match</strong></div>';
            }
        }
    }
}

function updatePassword(password) {
    var data = {"name": "", "email": "", "telephone": "", "password": password};
    ajaxAuth({
        url: "res/user/"+getCurrentUser().userId+"/updatePassword",
        type: 'PUT',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function (res) {
            console.log(res);
            saveInformation();
        },
        error: function (res) {
            console.log(res);
        }
    });
}

function saveInformation() {
    var newName = $("#edit_profile_information_name").val();
    var newEmail = $("#edit_profile_information_email").val();
    var newPhone = $("#edit_profile_information_phone").val();


    if(newEmail == "" || newPhone == "" || newName == "") {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Please fill in all the information</strong></div>';
    } else {
        var editUser = {"name": newName, "email": newEmail, "telephone": newPhone};
        ajaxAuth({
            url: "res/user/" + getCurrentUser().userId,
            type: 'PUT',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(editUser),
            success: function (result) {
                if (result == "true") {
                    document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
                        '<strong>Your profile is changed</strong></div>';
                    setCurrentUser(getCurrentUser().userId);
                } else {
                    document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
                        '<strong>Email already exists</strong></div>';
                }

            },
            error: function (result) {
            }
        });
    }

}