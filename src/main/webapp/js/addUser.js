/**
 * Created by Camilla Velvin on 11.01.2018.
 */
var name;
var phone;
var email;
var password;
var testPassword;
var person;


function submitNewUser() {
    name = $("#name").val();
    phone = $("#telephone").val();
    email = $("#email1").val();
    password = $("#pwd").val();
    testPassword = $("#pwd2").val();
    confirm();
}

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


        /*document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a user.</div>';*/
        person = {"name": name, "email": email, "telephone": phone, "password": password};
        addUser();
    }
}

function addUser() {
    $.ajax({
        url: "res/user/",
        type: "POST",
        data: JSON.stringify(person),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            console.log(result);
            if(result === true) {
                document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
                    '<strong>Success!</strong> You have now created a user.</div>';
                $(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
                    login(person.email,person.password);
                });
            } else {
                document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
                    '<strong>The email or phone number already exist</strong></div>';
            }
        },
        error: function (e) {
            console.log("error");
            console.log(e);
        }
    })
}


$("#pwd2").keyup(function(event){
    if(event.keyCode == 13){
        $("#confirmCreateUserButton").click();
    }
});