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
        url: "res/user/pwReset/"+email,
        type: 'put',
        contentType: "application/json; charset=utf-8",
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