/**
 * Created by Camilla Velvin on 11.01.2018.
 */
$(document).ready(function () {
    $("#submitBtn").click(function () {
       var email = $("#email1").val();
       if(email != "") {
           sendEmail();
       }
    });
});
function sendEmail() {
    $.ajax({

    })
}