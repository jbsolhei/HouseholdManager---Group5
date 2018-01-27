function login(email,password){
    var loginPerson = {"name": "", "email": email, "telephone": "", "password": password};
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
        error: function(xhr) {
            showLoadingScreen(false);
            if (xhr.status === 401) {
                $("#wrong-credentials-message").clearQueue().fadeIn().delay(2000).fadeOut();
                $("#emailInputId").clearQueue().shake(2, 7, 300);
                $("#passwordInputId").clearQueue().shake(2, 7, 300);
            }
        }
    });
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