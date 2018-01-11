/**
 * Created by Camilla Velvin on 11.01.2018.
 */
var name;
var phone;
var email;
var password;
var testPassword;
var person;

$(document).ready(function () {
    $("#submitBtn").click(function () {
        name = $("#name").val();
        phone = $("#telephone").val();
        email = $("#email1").val();
        password = $("#pwd").val();
        testPassword = $("#pwd2").val();
        console.log(name + phone + email + password + testPassword +"");

        confirm();

    });

});

function confirm() {
    //some of the forms is not filled in
    if(name == "" || phone == "" ||
        email == "" || password == "" ||
        testPassword == ""){
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Failed to create user.</strong> Please fill in all the forms. </div>';

        //passwords do not match
    } else if (password != testPassword){
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Your two passwords does not match.</strong> Please fill in password again. </div>';

        //success!
    } else {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a user.</div>';
        person = {"name": name, "email": email, "phone": phone, "password": password};

        addUser();

        $('#modal').modal('hide');
    }
}
function addUser() {
    $.ajax({
        url: "res/user",
        type: "POST",
        data: JSON.stringify(person),
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