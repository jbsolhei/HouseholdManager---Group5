var name;
var phone;
var email;
var password;
var testPassword;
var person;
var picture;


/*function updatePicture() {
    var filesSelected = document.getElementById("profile_picture").files;

     /!*   var objurl = window.URL.createObjectURL(fileData);
     $("#show_profile_pic").html("<img src='"+objurl+"' style='width: 100%'>");

     /!*  var img = new Image();
     img.src = objurl;
     img.onload = function() {

     // do something with your image
     }*!/

    if (filesSelected.length > 0)
    {
        var fileToLoad = filesSelected[0];

        if (fileToLoad.type.match("image.*"))
        {
            var fileReader = new FileReader();
            fileReader.onload = function(fileLoadedEvent)
            {
                picture = fileLoadedEvent.target.result;
            };
        }
    }
}*/

function submitNewUser() {
    name = $("#name").val();
    phone = $("#telephone").val();
    email = $("#email1").val();
    password = $("#pwd").val();
    testPassword = $("#pwd2").val();
    picture = $("#profile_picture").val();

    /*   var objurl = window.URL.createObjectURL(fileData);
     $("#show_profile_pic").html("<img src='"+objurl+"' style='width: 100%'>");

     /*  var img = new Image();
     img.src = objurl;
     img.onload = function() {

     // do something with your image
     }*/

    /*if (filesSelected.length > 0)
    {
        var fileToLoad = filesSelected[0];

        if (fileToLoad.type.match("image.*"))
        {
            var fileReader = new FileReader();
            fileReader.onload = function(fileLoadedEvent)
            {
                picture = fileLoadedEvent.target.result;
            };
        }
    }*/

    confirm();
}

function confirm() {
    //some of the forms is not filled in
    if(name === "" || phone === "" ||
        email === "" || password === "" ||
        testPassword === ""){
        $("#alertbox").html('<div class="alert alert-danger">' +
            '<strong>Failed to create user.</strong> Please fill in all the forms. </div>');

        //passwords do not match
    } else if (password !== testPassword){
        $("#alertbox").html('<div class="alert alert-danger">' +
            '<strong>Your two passwords does not match.</strong> Please fill in password again. </div>');

        // Password too short
    } else if (password.length<8){
        $("#alertbox").html('<div class="alert alert-danger">' +
            '<strong>Password is too short!</strong><br>Passwords need to be at least 8 characters. </div>');

        //success!
    } else {


        /*document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
            '<strong>Success!</strong> You have now created a user.</div>';*/
        person = {"name": name, "email": email, "telephone": phone, "password": password, "profileImage" : picture};
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
            if(result === true) {
                $("#alertbox").html('<div class="alert alert-success">' +
                    '<strong>Success!</strong> You have now created a user. </div>');
                $(".alert-success").fadeTo(1000, 500).slideUp(500, function(){
                    login(person.email,person.password);
                });
            } else {
                $("#alertbox").html('<div class="alert alert-danger">' +
                    '<strong>The email or phone number already exist</strong></div>');
            }
        },
        error: function (e) {
            console.log("error");
            console.log(e);
        }
    })
}


$("#pwd2").keyup(function(event){
    if(event.keyCode === 13){
        $("#confirmCreateUserButton").click();
    }
});