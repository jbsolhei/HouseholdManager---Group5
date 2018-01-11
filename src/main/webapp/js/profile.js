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
            $("#profile_information_list_phone").html(data.phone);
        }
    }
    function getUserTodos(userId){
        $.ajax({
            url: "res/user/???/" + userId,
            type: "GET",
            contentType: "application/json; charset = utf-8",
            success: function(data){
                console.log("getUserTodos()");
                printTodos(data);
            },
            error: function(){
                console.log("Kukskalle, getUserTodos()");
            },
            dataType:"json"
        });
        function printTodos(data){
            $.each(data, function(val){
                var houseTodos = val.todos;
                $.each(houseTodos, function(val){
                    $("#profile_todos_container").append(val.name);
                });
            });
        }
    }
    $.ajax({
        url: "res/user/login",
        type: "GET",
        contentType: "application/json; charset = utf-8",
        data: {
            email: "camilve@stud.ntnu.no",
            password: "123"
        },
        success: function(data){
            console.log("OK!");
            console.log(data.responseText);
        },
        error: function(){
            console.log("Kukskalle, getUserTodos()");
        },
        dataType:"json"
    });
});


