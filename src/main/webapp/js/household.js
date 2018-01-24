function loadHousehold(){
    $("#hh_name").html(getCurrentHousehold().name);
    $("#hh_address").html(getCurrentHousehold().address);
    buildMemberTable();
    buildOtherInfoHousehold();
}

function buildMemberTable(){
    $("table#members tbody").empty();

    if (getCurrentHousehold() === null || getCurrentUser() === null) {
        return;
    }

    var currentUser = getCurrentUser();
    var members = getCurrentHousehold().residents;
    var admins = getCurrentHousehold().admins;

    var currentUserIsAdmin = admins.find(function (admin) {
        return currentUser.userId === admin.userId
    }) !== undefined;

    for (var i = 0; i < members.length; i++) {
        var user = members[i];
        var isAdmin = (function (user) {
            return admins.find(function (admin) {
                return user.userId === admin.userId
            }) !== undefined;
        })(user);

        var adminTd = "<td></td>";
        if (isAdmin) {
            adminTd = "<td><i data-toggle='tooltip' data-placement='bottom' " +
                "title='Administrator for this household' class='glyphicon glyphicon-star-empty'></i></td>";
        }

        var removeTd = "<td></td>";
        if (user.userId === currentUser.userId) {
            removeTd = "<td><span class='glyphicon glyphicon-remove remove' " +
                "data-toggle='confirm' data-remove='self'></span></td>";
        }
        else if (currentUserIsAdmin) {
            removeTd = "<td><span class='glyphicon glyphicon-remove remove' " +
                "data-toggle='confirm' data-remove='" + user.userId + "'></span></td>";
        }

        $("table#members tbody").append(
            "<tr data-user-index=\"" + i + "\">\n" +
            adminTd + "\n" +
            "<td><div class=\"img-circle\">" +
            "<img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\">" +
            "</div></td>\n" +
            "<td>" + user.name + "</td>\n" +
            "<td>" + user.email + "</td>\n" +
            "<td>" + user.telephone + "</td>\n" +
            removeTd + "\n" +
            "</tr>"
        );
    }

    $("table#members tr").on("click", function (event) {
        if (!$(event.target).is("span[data-toggle='confirm']")) {
            showMiniProfile($(event.delegateTarget).data("user-index"));
        }
    });

    $("span.remove[data-toggle='confirm']").confirmation({
        rootSelector: "span.remove[data-toggle='confirm']",
        popout: true,
        singleton: true,
        title: "Really remove user from household?",
        btnOkClass: "btn-sm btn-danger",
        btnOkIcon: "glyphicon glyphicon-trash",
        btnCancelClass: "btn-sm btn-default",
        onConfirm: function () {
            showLoadingScreen(true);
            var remove = $(this).data("remove");
            console.log("Confirm clicked! Removing " + remove);
            if (remove === "self") {
                removeMyselfFromHousehold();
            }
            else {
                removeUserFromHousehold(remove);
            }
        }
    });
}

function buildOtherInfoHousehold() {
    $("#householdAddress").html("<p>" + getCurrentHousehold().address + "</p>");
}

function removeMyselfFromHousehold() {
    if (getCurrentHousehold() !== null) {
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/user",
            method: "DELETE",
            dataType: "json",
            success: function (response) {
                if (response.success === true) {
                    window.localStorage.removeItem("house");
                    window.location.reload();
                }
            }
        });
    }
}

function removeUserFromHousehold(userId) {
    if (getCurrentHousehold() !== null) {
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/users/" + userId,
            method: "DELETE",
            success: function (response) {
                if (response.success === true) {
                    updateCurrentHousehold(undefined, buildMemberTable);
                }
            }
        }).always(function() {
            showLoadingScreen(false)
        });
    }
}

function showMiniProfile(index){
    var members = getCurrentHousehold().residents;
    $("#members").fadeOut(500);
    setTimeout(function(){
        $("#miniProfile-name").html('<p id="miniProfile-name">' + members[index].name + '</p>');
        $("#miniProfile-email").html('<p id="miniProfile-email">' + members[index].email + '</p>');
        $("#miniProfile-telephone").html('<p id="miniProfile-telephone">' + members[index].telephone + '</p>');
        if(members[index].gender != "" && members[index].gender != null){
            $("#miniProfile-gender").html('<p id="miniProfile-gender">' + members[index].gender + '</p>');
            $("#miniProfile-label-gender").show();
        } else {
            $("#miniProfile-gender").html('<p id="miniProfile-gender"></p>');
            $("#miniProfile-label-gender").hide();
        }

        if(members[index].relationship != "" && members[index].relationship != null) {
            $("#miniProfile-relationship").html('<p id="miniProfile-relationship">' + members[index].relationship + '</p>');
            $("#miniProfile-label-relationship").show();
        } else {
            $("#miniProfile-relationship").html('<p id="miniProfile-relationship"></p>');
            $("#miniProfile-label-relationship").hide();
        }

        if(members[index].bio != "" && members[index].bio != null) {
            $("#miniProfile-bio").html('<p id="miniProfile-bio">' + members[index].bio + '</p>');
            $("#miniProfile-label-bio").show();
        }else {
            $("#miniProfile-bio").html('<p id="miniProfile-bio"></p>');
            $("#miniProfile-label-bio").hide();
        }

        $("#miniProfile").removeClass("hide");

    }, 500);
}

function hideMiniProfile(){
    $("#members").fadeIn(500);
    $("#miniProfile").addClass("hide");
}

/*
 function buildAdminTable() {
 var admins = getCurrentHousehold().admins;

 for (var i = 0; i<admins.length; i++) {
 var user = admins[i];
 $("#admins").append(
 "<tr>\n" +
 "<td><div class=\"img-circle\"><img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\"></div></td>\n" +
 "<td>"+user.name+"</td>\n" +
 "<td>"+user.email+"</td>\n" +
 "<td>"+user.telephone+"</td>\n" +
 "</tr>");
 }
 }*/
