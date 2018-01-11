/**
 * Created by Camilla Velvin on 11.01.2018.
 */
$(document).ready(function () {

    $("#submit").click(function () {
        var email = $("#email").val();
        var password = $("#password").val();
        console.log(email + " pass " + password);

        //fungerer ikke!!
        $.ajax({
            url: "res/user/login",
            type: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                if(response == "") {
                    console("tomt");
                }
                console.log("gikk nbr");
            },
            error: function(result) {
                //Do Something to handle error
                if(result.responseText === "") {
                    alert("Wrong")
                } else{
                    //Hva som skjer ved riktig passord
                }
            }
        });

    });
});
