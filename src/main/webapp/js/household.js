function loadHousehold(){
    $("#hh_name").html(getCurrentHousehold().name);
    $("#hh_address").html(getCurrentHousehold().address);
    buildMemberTable();
    buildOtherInfoHousehold();
}

function editAdmins() {
    $("table#members tbody").empty();

    $("#makeAdminField").html("<p style='font-size: 12px'>Choose<br>admins</p>");
    if (getCurrentHousehold() === null || getCurrentUser() === null) {
        return;
    }

    var currentUser = getCurrentUser();
    var member = getCurrentHousehold().residents;
    var admins = getCurrentHousehold().admins;

    var currentUserIsAdmin = admins.find(function (admin) {
            return currentUser.userId === admin.userId
        }) !== undefined;

    if(currentUserIsAdmin) {
        $("#editAdminsSymbol").html("<span data-toggle='tooltip' data-placement='bottom' " +
            "title='Edit administrators' class='glyphicon glyphicon-edit pull-right clickable'></span>")
    }
    for (var i = 0; i < member.length; i++) {
        var user = member[i];
        var isAdmin = (function (user) {
            return admins.find(function (admin) {
                    return user.userId === admin.userId
                }) !== undefined;
        })(user);

        var adminTd = "<td></td>";
        if (isAdmin) {
            adminTd = "<td><input type='checkbox' checked id='admin-"+i+"'></td>"
        } else {
            adminTd = "<td><input type='checkbox' id='admin-"+i+"'></td>";
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
        var image;
        if(user.profileImage != "" && user.profileImage != null) {
            image = user.profileImage;
        } else {
            image = "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png";
        }
        $("#panelFooterHouseholdOverview").html("<button class='btn' id='buttonEditAdmins' onclick='updateAdminsInHousehold()'><span class=\"glyphicon glyphicon-refresh\"></span> Update admministrators</button>")

        $("table#members tbody").append(
            "<tr data-user-index=\"" + i + "\">\n" +
            adminTd + "\n" +
            "<td><div class=\"img-circle\">" +
            "<img alt='profile picture' class=\"img-responsive img-pic\" src='"+image+"'>" +
            "</div></td>\n" +
            "<td>" + user.name + "</td>\n" +
            "<td>" + user.telephone + "</td>\n" +
            removeTd + "\n" +
            "</tr>"
        );
    }
}

function updateAdminsInHousehold() {
    var member = getCurrentHousehold().residents;
    var admins = getCurrentHousehold().admins;

    var count =0;

    for(var i=0; i< member.length; i++) {
        if($("#admin-"+i).is(':checked')) {
            count++;
        }
    }
    if(count>0) {
        for(var i=0; i< member.length; i++) {
            if($("#admin-"+i).is(':checked')) {
                var isAlreadyAdmin = false;
                for(var j=0; j<admins.length; j++) {
                    if(admins[j].userId === member[i].userId) {
                        isAlreadyAdmin = true;
                    }
                }
                if(!isAlreadyAdmin) {
                    var id = member[i].userId;
                    var data = {"userId": id,
                        "name": "",
                        "email": "",
                        "telephone": "",
                        "relationship": "",
                        "bio": "",
                        "gender": "",
                        "profileImage" : ""
                    };
                    ajaxAuth({
                        type: 'PUT',
                        url: "res/household/"+getCurrentHousehold().houseId+"/admin",
                        data: JSON.stringify(data),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function (result) {
                            console.log("kommer hit?")
                            addNotification(id, getCurrentHousehold().houseId, "Congratulations you have been made administrator :-)")
                        },
                        error: function (e) {
                            console.log(e);
                        }

                    });
                }
            } else {
                for(var j=0; j<admins.length; j++) {
                    if(member[i].userId === admins[j].userId) {
                        var data = {"userId": member[i].userId,
                            "name": "",
                            "email": "",
                            "telephone": "",
                            "relationship": "",
                            "bio": "",
                            "gender": "",
                            "profileImage" : ""
                        };
                        ajaxAuth({
                            type: 'PUT',
                            url: "res/household/"+getCurrentHousehold().houseId+"/unmakeAdmin",
                            data: JSON.stringify(data),
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8',
                            success: function (result) {
                                console.log("Admin made non-admin " + result);
                            },
                            error: function (e) {
                                console.log(e);
                            }

                        });
                    }
                }
            }
        }
        updateCurrentHousehold(household, loadHousehold());
    } else {
        document.getElementById("alertBackToUser").innerHTML = '<div style="text-align: left" class="alert alert-danger">' +
            '<strong>You must choose one administrator</strong></div>';
    }
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

    if(currentUserIsAdmin) {
        $("#editAdminsSymbol").html("<span data-toggle='tooltip' onclick='editAdmins()' data-placement='bottom' " +
            "title='Edit administrators' class='glyphicon glyphicon-edit pull-right clickable'></span>")
    }
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
            removeTd = "<td  title='Leave this household'><span class='glyphicon glyphicon-remove remove' " +
                "data-toggle='confirm' data-remove='self'></span></td>";
        }
        else if (currentUserIsAdmin) {
            removeTd = "<td title='Remove this user from household'><span class='glyphicon glyphicon-remove remove' " +
                "data-toggle='confirm' data-remove='" + user.userId + "'></span></td>";
        }
        var image;
        if(user.profileImage != "" && user.profileImage != null) {
            image = user.profileImage;
        } else {
            image = "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png";
        }

        $("table#members tbody").append(
            "<tr data-user-index=\"" + i + "\" id='membersTableInput'>\n" +
            adminTd + "\n" +
            "<td><div class=\"img-circle\">" +
            "<img alt='profile picture' class=\"img-responsive img-pic\" src='"+image+"'>" +
            "</div></td>\n" +
            "<td>" + user.name + "</td>\n" +
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
        title: "Remove user from household? Confirm",
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
    var admins = getCurrentHousehold().admins;
    if (getCurrentHousehold() !== null) {
        console.log(admins.length);
        if(admins.length == 1 && admins[0].userId == getCurrentUser().userId) {
            document.getElementById("alertBackToUser").innerHTML = '<div style="text-align: left" class="alert alert-danger">' +
                '<strong>You must choose another administrator! </strong> An household must have an administrator</div>';
            showLoadingScreen(false);
        } else {
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
}

function removeUserFromHousehold(userId) {
    if (getCurrentHousehold() !== null) {
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/users/" + userId,
            method: "DELETE",
            success: function (response) {
                if (response.success === true) {
                    updateCurrentHousehold(household, buildMemberTable);
                }
            }
        }).always(function() {
            showLoadingScreen(false)
        });
    }
}

function showMiniProfile(index){
    console.log(index);
    if(index != undefined) {
        var members = getCurrentHousehold().residents;
        $("#members").fadeOut(500);
        setTimeout(function () {
            $("#panel-heading-text").replaceWith("<span id=\"panel-heading-button\" onclick=\"hideMiniProfile()\"><span class=\"glyphicon glyphicon-chevron-left\"></span> Back</span>");

            if (members[index].userId === getCurrentUser().userId) {
                $("#toEditProfileButton").show();
                //document.getElementById("#toEditProfileButton").innerHTML = '';
            } else {
                $("#toEditProfileButton").hide();
            }
            var image;
            if (members[index].profileImage != "" && members[index].profileImage != null) {
                image = members[index].profileImage;
            } else {
                image = "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png";
            }
            $("#place_for_profile_pic").html("<img alt='profile picture' src='" + image + "' id='profile-img'>");
            $("#miniProfile-name").html('<p id="miniProfile-name">' + members[index].name + '</p>');
            $("#miniProfile-email").html('<p id="miniProfile-email">' + members[index].email + '</p>');
            $("#miniProfile-telephone").html('<p id="miniProfile-telephone">' + members[index].telephone + '</p>');

            if (members[index].gender != "" && members[index].gender != null) {
                $("#miniProfile-gender").html('<p id="miniProfile-gender">' + members[index].gender + '</p>');
                $("#miniProfile-label-gender").show();
            } else {
                $("#miniProfile-gender").html('<p id="miniProfile-gender"></p>');
                $("#miniProfile-label-gender").hide();
            }

            if (members[index].relationship != "" && members[index].relationship != null) {
                $("#miniProfile-relationship").html('<p id="miniProfile-relationship">' + members[index].relationship + '</p>');
                $("#miniProfile-label-relationship").show();
            } else {
                $("#miniProfile-relationship").html('<p id="miniProfile-relationship"></p>');
                $("#miniProfile-label-relationship").hide();
            }

            if (members[index].bio != "" && members[index].bio != null) {
                $("#miniProfile-bio").html('<p id="miniProfile-bio">' + members[index].bio + '</p>');
                $("#miniProfile-label-bio").show();
            } else {
                $("#miniProfile-bio").html('<p id="miniProfile-bio"></p>');
                $("#miniProfile-label-bio").hide();
            }

            $("#hideMiniProfileButton").removeClass("hide");
            $("#miniProfile").removeClass("hide");

        }, 500);
    }
}

function hideMiniProfile(){
    $("#panel-heading-button").replaceWith("<span id=\"panel-heading-text\">Members</span>");
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
