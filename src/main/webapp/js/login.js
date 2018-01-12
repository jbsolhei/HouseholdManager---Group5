/**
 * Created by Camilla Velvin on 11.01.2018.
 */
$(document).ready(function () {

    $("#submit").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        console.log(email + " pass " + password);

        var loginPerson = {"name": "", "email": email, "telephone": "", "password": password};
        console.log(JSON.stringify(loginPerson));
        $.ajax({
            url: "res/user/login",
            type: 'post',
            data: JSON.stringify(loginPerson),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                if(response.success === true) {
                    window.sessionStorage.setItem("sessionToken",response.sessionToken);
                    window.sessionStorage.setItem("userId",response.userId);
                    window.location.replace("index.html")
                }
            },
            error: function(result) {
                console.log(result);
                alert("Wrong email or password");
            }
        });

    });
});