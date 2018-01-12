var house_name;
var house_address;
var household;
var admins = [];
var admin;


function addNewHoushold() {
    house_name = $("#householdName").val();
    house_address = $("#householdAddress").val();
    admin = currentUser;
    admins.push(admin);
    confirm();
};

function addNewUserToList() {
    var email = $("#addUserInput");

    $("#header ul").append('<li><a href="/user/messages"><span class="tab">Message Center</span></a></li>');
    $("#newUsersList").append('<li><a>' + email + '</a></li>');
}

function confirm() {
//some of the forms is not filled in
    if (house_name == "" || house_address == "") {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Failed to create Household.</strong> Please fill in all the forms. </div>';

    } else {
        /*
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a Household.</div>';
           */
        household = {"name": house_name, "address": house_address, "admins": admins};
        addHousehold(household);

        //$('#modal').modal('hide');

    }
}

function addHousehold(household) {
    $.ajax({
        url: "res/household",
        type: "POST",
        data: JSON.stringify(household),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            console.log(result);
        },
        error: function (e) {
            console.log(e);
        }
    })
}
