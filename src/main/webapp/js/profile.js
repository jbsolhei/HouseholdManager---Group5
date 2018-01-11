/**
 * Created by Simen Moen Storvik on 11.01.2018.
 */
$(document).ready(function(){
    function getInfo(id){
        $.ajax({
            url: "res/user/" + id,
            type: "GET",
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log("getInfo()");
                printInfo((data));
            },
            error: function (res) {
                console.log("Kukskalle, getInfo()");
            },
            dataType: "json"
        });
        function printInfo(data){
            $("#profile_information_list_name").html(data.name);
            $("#profile_information_list_email").html(data.email);
            $("#profile_information_list_phone").html(data.telephone);
        }
    }
    function getUserHouseholds(userId){
        $.ajax({
            url: "res/user/hh/" + userId,
            type: "GET",
            contentType: "application/json; charset = utf-8",
            success: function(data){
                console.log("getUserHouseholds()");
                printHouseholds(data);
            },
            error: function(){
                console.log("Kukskalle, getUserHouseholds()");
                printHouseholds(data);
            },
            dataType:"json"
        });
        function printHouseholds(data){
            var inputString = "";
            $.each(data, function(val){
               /* var householdName = val.name;
                var householdAdress = val.adress;
                inputString += "<tr><td>" + householdName + "</td><td>" + householdAdress + "</td><td>isAdmin?</td></tr>"*/
            });
            $("#profile_households_table_body").html(inputString);
        }
    }
    getInfo(1);
    getUserHouseholds(1);
});


