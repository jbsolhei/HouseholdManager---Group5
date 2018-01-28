var house_name;
var house_address;
var household;
var admins = [];
var admin;
var newUserEmails = [];

//Changes focus to name input when modal loads
$(document).on('shown.bs.modal', function (e) {
    $("#householdName").focus();
});

/*
    Takes the info from the input fields for name and address and puts them in to variables.
    Adds the user that is trying to make a new household to the admins array.
    Calls the confirm() function.
*/
function addNewHoushold() {
    house_name = $("#householdName").val();
    house_address = $("#householdAddress").val();
    admin = getCurrentUser();
    admins.push(admin);
    confirm();
};

//Removes an email from the email list if clicked.
$(document).on('click', '.newUserListElement', function(){
    $(this).remove();
});

// Makes it possible to hit enter instead of pressing buttons onscreen
$(document).keypress(function(e) {
    if (e.keyCode == 13 && $("#addUserInput").is(":focus")) {
        addNewUserToList();
    } else if(e.keyCode == 13 && $("#householdName").is(":focus")){
        $("#householdAddress").focus();
    } else if(e.keyCode == 13 && $("#householdAddress").is(":focus")) {
        $("#addUserInput").focus();
    }
});

// Adds a new user to the html list. Checks if it is a valid email with regex first.
function addNewUserToList() {
    var email = $("#addUserInput").val();

    //regex for checking valid email
    var expr = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (expr.test(email)) {
        $("#newUsersList").prepend("<li class='newUserListElement'><a>" + email + "</a></li>");
        $("#addUserInput").val("").focus();
    }
}

//Checks if some fields are empty.
function confirm() {
//if some of the forms are not filled in
    if (house_name == "" || house_address == "") {
        $("#alertbox").html('<div style="text-align: left" class="alert alert-danger">' +
            '<strong>Failed to create Household.</strong> Please fill in all the forms. </div>');


        $(".alert-danger").fadeTo(5000, 500).slideUp(500, function(){
            $(".alert-danger").slideUp(500);
        });

    } else {

        $("#alertbox").html('<div style="text-align: left" class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a Household.</div>');

        $(".alert-success").fadeTo(3000, 500).slideUp(500, function(){
            $(".alert-danger").slideUp(500);
            $(function () {
                $('#theModal').modal('toggle');
            });
        });

        $('#newUsersList li').each(function (i) {
            var text = $(this).text();
            newUserEmails.push(text);
        });

        household = {"name": house_name, "address": house_address, "admins": admins};
        addHousehold(household);

    }
}

//Adds a household with ajax
function addHousehold(household) {
    ajaxAuth({
        url: "res/household",
        type: "POST",
        data: JSON.stringify(household),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {

            if (result>0) {
                sendInviteToUsers(result, newUserEmails);
                setCurrentHousehold(result);
            } else {
                alert("Error adding household")
            }
        }
    });
}

//Sends invites with ajax
function sendInviteToUsers(houseId, emails) {
    if (emails.length>0) {
        ajaxAuth({
            url: "res/household/" + houseId + "/users/invite",
            type: "POST",
            data: JSON.stringify(emails),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json'
        })
    }
    setCurrentHousehold(houseId);
    window.location.reload();
}
