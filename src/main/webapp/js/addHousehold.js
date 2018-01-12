var house_name;
var house_address;
var household;
var adminId;

$(document).ready(function () {
    $("#submitHHBBtn").click(function () {
        house_address = $("#householdName").val();
        house_address = $("#householdAddress").val();
        adminId = currentUser.userId;

        confirm();
    });
});

function confirm() {
    //some of the forms is not filled in
    if (house_name == "" || house_address == "") {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Failed to create Household.</strong> Please fill in all the forms. </div>';

    } else {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a Household.</div>';
        household = {"name": house_name, "address": house_address};

        addHousehold(household);

        $('#modal').modal('hide');
    }
}

function addAdminToHousehold(adminId) {

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