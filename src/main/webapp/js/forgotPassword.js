/**
 * Created by Camilla Velvin on 11.01.2018.
 */
function submitForgotPass() {
    var email = $("#email1").val();
    console.log(email);
    if(email != "") {
        sendEmail(email);
    }
}

function sendEmail(email) {
    $.ajax({
        url: "res/user/pwReset",
        type: 'POST',
        data: email,
        contentType: "text/plain",
        success: function(response) {
            console.log(response);
            changeView()
        },
        error: function(result) {
            console.log(result);
        }
    });
}
function changeView() {
    $("#forgottenModule").html("");
    $("#forgottenModule").append("<div><p>If the user exist, an email is sent</p></div>");
}