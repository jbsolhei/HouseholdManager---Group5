function inviteCheck() {
    var urlParams = window.location.search;
    var token = urlParams.split("invite=")[1];

    if (token!==undefined) {
        ajaxAuth({
            url: "res/household/invited/" + token,
            type: "POST",
            data: JSON.stringify(currentUser),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function () {
            }
        });
    }

}