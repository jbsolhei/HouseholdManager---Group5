function inviteMembersToHousehold() {
    addEmailToInviteList();

    var inviteEmails = [];
    $("#inviteMembersToHouseholdList").find("li").each(function () {
        inviteEmails.push($(this).data("invite-email"));
    });

    if (inviteEmails.length > 0) {
        if (getCurrentHousehold() !== undefined || getCurrentHousehold() !== null) {
            $("#invite-members-status").show();
            ajaxAuth({
                url: "res/household/" + getCurrentHousehold().houseId + "/users/invite",
                type: "POST",
                data: JSON.stringify(inviteEmails),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (result) {
                    $("#invite-members-status").text("Invites sent!");
                    setTimeout(function() {
                        $("#theModal").modal("hide");
                    }, 2000);
                }
            });
        }
        else {
            $("#theModal").modal("hide");
        }
    }
}

function addEmailToInviteList() {
    var email = $("#inviteEmail").val().trim();
    var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (emailRegex.test(email)) {
        $("#inviteMembersToHouseholdList").append(
            $("<li>")
                .attr("class", "newUserListElement")
                .data("invite-email", email)
                .text(email)
        );
        $("#inviteEmail").val("").focus();
    }
    else if (email !== "") {
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

$(document).keypress(function(e) {
    if (e.keyCode === 13 && $("#inviteEmail").is(":focus")) {
        addEmailToInviteList();
    }
});