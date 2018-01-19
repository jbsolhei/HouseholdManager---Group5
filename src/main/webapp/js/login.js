/**
 * Created by Camilla Velvin on 11.01.2018.
 */
function login(email,password){
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
                window.localStorage.setItem("sessionToken",response.sessionToken);
                window.localStorage.setItem("userId",response.userId);
                inviteCheck();
            }
        },
        error: function(result) {
            showLoadingScreen(false);
            console.log(result);
            alert("Wrong email or password");
        }
    });
}


//Set show to true if you want to show loadingscreen and false if you want to hide it
function showLoadingScreen(show) {
    if (show) {
        $("#coverScreen").css('display', 'block');
    } else {
        $("#coverScreen").css('display', 'none');
    }
}

function inviteCheck() {
    var urlParams = window.localStorage.getItem("invite");
    var token = urlParams.split("?invite=")[1];
    if (token!==undefined) {
        ajaxAuth({
            url: "res/household/invited/" + token,
            type: "POST",
            data: window.localStorage.getItem("userId"),
            contentType: 'text/plain',
            success: function (response) {
                if (!response.success) {
                    alert("Invalid or expired invite token");
                } else {
                    var hid = {"houseId":response.houseId};
                    window.localStorage.setItem("welcome",JSON.stringify(hid))
                }
                setCurrentUser(window.localStorage.getItem("userId"));
            },
            error: function () {
                alert("Invalid or expired invite token");
                setCurrentUser(window.localStorage.getItem("userId"));
            }
        });
    } else {
        setCurrentUser(window.localStorage.getItem("userId"));
    }
}