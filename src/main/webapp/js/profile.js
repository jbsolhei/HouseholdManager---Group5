/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */


$(document).ready(function() {

    $.ajax({
        url: "res/user",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function(data){

        },
        dataType: "json"
    });
});