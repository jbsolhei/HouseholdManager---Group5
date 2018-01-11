/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    function getInfo(id){
        $(document).ready(function() {
            $.ajax({
                url: "res/user/" + id,
                type: "GET",
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    console.log("OK!");
                    printInfo((data));
                },
                error: function (res) {
                    console.log("Kukskalle");
                },
                dataType: "json"
            });
        });
        function printInfo(data){
            $("#profile_information_list_name").html(data.name);
            $("#profile_information_list_email").html(data.email);
            $("#profile_information_list_phone").html(data.phone);
        }
    }
    function getUserTodos(userId){

    }
});


