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
                if (response.success){
                    setCurrentUser();
                    window.localStorage.setItem("houseId", response.houseId);
                    setCurrentHousehold(response.houseId);
                    window.location.replace("index.html")
                } else {
                    alert("Invalid or expired invite token");
                    window.location.replace("index.html");
                }
            },
            error: function () {
                alert("Invalid or expired invite token");
                window.location.replace("index.html");
            }
        });
    } else {
        window.location.replace("index.html");
    }
}