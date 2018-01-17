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