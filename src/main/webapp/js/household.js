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
            "<tr onclick=\"showMiniProfile(" + i + ")\">\n" +
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

    $("span.remove[data-toggle='confirm']").confirmation({
        rootSelector: "span.remove[data-toggle='confirm']",
        popout: true,
        singleton: true,
        title: "Really remove user from household?",
        btnOkClass: "btn-sm btn-danger",
        btnOkIcon: "glyphicon glyphicon-trash",
        btnCancelClass: "btn-sm btn-default",
        onConfirm: function () {
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
            },
            error: function (xhr) {
                if (xhr.status === 403) {
                    console.log("Du har ikke tilgang til å gjøre dette. FY SKAMME SEG!");
                }
            }
        });
    }
}

function showMiniProfile(index){
    var members = getCurrentHousehold().residents;
    $("#members").fadeOut(500);
    setTimeout(function(){
        if(members[index].userId === getCurrentUser().userId){
            $("#toEditProfileButton").show();
            //document.getElementById("#toEditProfileButton").innerHTML = '';
        }else {
            $("#toEditProfileButton").hide();
        }
        document.getElementById("miniProfile-name").innerHTML = '<p id="miniProfile-name">' + members[index].name + '</p>';
        document.getElementById("miniProfile-email").innerHTML = '<p id="miniProfile-email">' + members[index].email + '</p>';
        document.getElementById("miniProfile-telephone").innerHTML = '<p id="miniProfile-telephone">' + members[index].telephone + '</p>';
        if(members[index].gender != "" && members[index].gender != null){
            document.getElementById("miniProfile-gender").innerHTML = '<p id="miniProfile-gender">' + members[index].gender + '</p>';
            $("#miniProfile-label-gender").show();
        } else {
            document.getElementById("miniProfile-gender").innerHTML = '<p id="miniProfile-gender"></p>';
            $("#miniProfile-label-gender").hide();
        }

        if(members[index].relationship != "" && members[index].relationship != null) {
            document.getElementById("miniProfile-relationship").innerHTML = '<p id="miniProfile-relationship">' + members[index].relationship + '</p>';
            $("#miniProfile-label-relationship").show();
        } else {
            document.getElementById("miniProfile-relationship").innerHTML = '<p id="miniProfile-relationship"></p>';
            $("#miniProfile-label-relationship").hide();
        }

        if(members[index].bio != "" && members[index].bio != null) {
            document.getElementById("miniProfile-bio").innerHTML = '<p id="miniProfile-bio">' + members[index].bio + '</p>';
            $("#miniProfile-label-bio").show();
        }else {
            document.getElementById("miniProfile-bio").innerHTML = '<p id="miniProfile-bio"></p>';
            $("#miniProfile-label-bio").hide();
        }

        $("#hideMiniProfileButton").removeClass("hide");
        $("#miniProfile").removeClass("hide");

    }, 500);
}

function hideMiniProfile(){
    $("#members").fadeIn(500);
    $("#miniProfile").addClass("hide");
}

function editProfile(){
    swapContent(profile);
    $("#profile-footer").click();
    //document.getElementById("profile-footer").click();
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
