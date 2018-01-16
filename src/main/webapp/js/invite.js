function inviteCheck() {
    var urlParams = window.location.search;
    var token = urlParams.split("invite=")[1];

    if (token!==undefined) {
        ajaxAuth({
            url: "res/household/invited/" + token,
            type: "POST",
            data: window.localStorage.getItem("userId"),
            contentType: 'text/plain',
            success: function (response) {
                if (!response.success){
                    alert("Invalid or expired invite token");
                }
            },
            error: function () {
                alert("Invalid or expired invite token");
            }
        });
    }

    setCurrentUser(window.localStorage.getItem("userId"));
}