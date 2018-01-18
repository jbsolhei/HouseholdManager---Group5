function inviteMembersToHousehold() {
    var inviteEmails = [];
    $("#inviteMembersToHouseholdList").find("li").each(function () {
        inviteEmails.push($(this).data("inviteEmail"));
    });

    if (getCurrentHousehold() != null) {
        sendInviteToUsers(getCurrentHousehold().houseId, inviteEmails);
    }
    else {
        console.log("getCurrentHousehold() == null !");
    }
}

$(document).keypress(function(e) {
    if (e.keyCode === 13 && $("#inviteEmail").is(":focus")) {
        addEmailToInviteList();
    }
});

function addEmailToInviteList() {
    var email = $("#inviteEmail").val();
    var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (emailRegex.test(email)) {
        $("#inviteMembersToHouseholdList").append(
            $("<li>").attr("class", "newUserListElement").data("inviteEmail", email).text(email)
        );
        $("#inviteEmail").val("").focus();
    }
    else {
        $("#inviteEmail").clearQueue()
        .animate({
            backgroundColor: "#fbb"
        }, 500)
        .delay(2000)
        .animate({
            backgroundColor: "#fff"
        }, 500);
    }
}