var notifications = [];
/**
 * Used to add a notification to the database. DateTime automatically added.
 * @param userId The id of the user that the notification is going to.
 * @param houseId The house that the notification is connected to.
 * @param message The message in the notification.
 */
function addNotification(userId, houseId, message) {

var currentdate = new Date();
var dateTime =    currentdate.getFullYear() + "-"
                +(currentdate.getMonth()+1)  + "-"
                + currentdate.getDate() + " "
                + currentdate.getHours() + ":"
                + currentdate.getMinutes() + ":"
                + currentdate.getSeconds();

    var notification = {
        userId: userId,
        houseId: houseId,
        message: message,
        dateTime: dateTime
    };

    ajaxAuth({
        url: "res/notification",
        type: "POST",
        data: JSON.stringify(notification),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            console.log("Notification added: " + result);
        },
        error: function (e) {
            console.log(e);
        }
    })
}

/**
 * Used to delete a notification from the database.
 * @param notificationId The id of the notification that you want to update.
 */
function deleteNotification(notificationId) {
    ajaxAuth({
        url: "res/notification/" + notificationId,
        type: "DELETE",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            console.log("Notification deleted: " + result);
        },
        error: function (e) {
            console.log(e);
        }
    })
}

/**
 * Get all the user's notifications.
 * @param userId The id of the user.
 */
function getNotifications(userId) {

    ajaxAuth({
        url: "res/user/"+ userId +"/notifications",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            notifications = result;
            updateNotificationDropdown();
            updateNotificationBell();
            return result;
        },
        error: function (e) {
            console.log(e);
        }
    })
}

/**
 * Inserts the user's notifications in to the dropdown.
 * @param notifications A Notification object.
 */
function updateNotificationDropdown() {
    var noteboi = $("#notifyDropdownListId");
    var noteboi2 = $("#notifyDropdownListId1");
    noteboi.html("");
    noteboi2.html("");
    if(notifications.length > 0) {
        $("#removeAllNotificationsButton").html("hehr");
        $("#removeAllNotificationsButton1").show();
    }
    for (i = 0; i < notifications.length; i++) {
        var dateTime = notifications[i].dateTime;
        if (dateTime !== null) dateTime = dateTime.slice(0, dateTime.length - 2);
        else dateTime = "";
        var message = notifications[i].message;
        var id = notifications[i].notificationId;
        var houseName = notifications[i].houseName;
        if (houseName == null) houseName = "";

        noteboi.prepend("<li id='notifId"+id+"' class='noti notificationElement list-group-item'><p class='notifyMessageId'>"+ message +"</p><p class='noti notifyDateTimeId'>"+dateTime+"<span class='noti notifyHousehold'>"+houseName+"</span></p></li>");
        noteboi2.prepend("<li id='notifId"+id+"' class='noti notificationElement list-group-item'><p class='notifyMessageId'>"+ message +"</p><p class='noti notifyDateTimeId'>"+dateTime+"<span class='noti notifyHousehold'>"+houseName+"</span></p></li>");
    }
}

/**
 * Counts the number of notifications in the dropdown.
 * @returns {jQuery} Returns the number of notifications.
 */
function countNotifications() {
    var notifications = $('#notifyDropdownListId').children('li').length;
    notifications = $('#notifyDropdownListId1').children('li').length;
    return notifications;
}

/**
 * Updates the notifications bell to the color white if there are some notifications left in the dropdown.
 */
function updateNotificationBell() {
    var number = countNotifications();
    var numbers = $("#notificationValue");
    var numbers1 = $("#notificationValue1");
    if (countNotifications() > 0) {
        $("#panelForNotifications").removeClass("hide");
        $("#notifyBellId").css('color', 'white');
        numbers.html("");
        numbers.addClass("numberCircle");
        numbers.append(""+number+"");
        $("#notifyBellId1").css('color', 'white');
        numbers1.html("");
        numbers1.addClass("numberCircle");
        numbers1.append(""+number+"");
    } else {
        $("#panelForNotifications").addClass("hide");
        $("#notifyBellId").css('color', '#436470');
        numbers.removeClass("numberCircle")
        numbers.html("");
        numbers1.html("");
        numbers1.removeClass("numberCircle");
    }

}

//Set to true when the dropdown is opened, and false if closed.
var opened = false;


/**
 * When a notifications element is clicked it will be removed and sett to "read" in the database.
 * If there are no more notifications left in the dropdown the bell is turned back to white.
 */
$(document).on('click', '.notificationElement', function () {
    var id = $(this).attr('id');
    id = id.slice(7);
    deleteNotification(id);
    $(this).remove();

    $("#notificationValue").html("");
    $("#notificationValue").append(""+countNotifications()+"");
    $("#notificationValue1").html("");
    $("#notificationValue1").append(""+countNotifications()+"");

    if (countNotifications() <= 0) {
        $("#notifyBellId").css('color', '#436470');
        $('#notifyBellId').parent().removeClass('open');
        $("#notificationValue").html("");
        $("#notificationValue").removeClass("numberCircle");
        $("#notifyBellId1").css('color', '#436470');
        $('#notifyBellId1').parent().removeClass('open');
        $("#panelForNotifications").html("heipådeg");
        $("#panelForNotifications1").addClass("collapse");

    }
});

/**
 * Removes all notifications to a user.
 */
$(document).on('click', '#removeAllNotificationsButton', function () {
    var num = countNotifications();
    console.log("ant not: " +  num);
    for(var i = 0; i < num; i++){
        console.log("nr deleted: " + notifications[i].notificationId);
        deleteNotification(notifications[i].notificationId);
        $('#notifyDropdownListId').children('li').remove();
        $('#notifyDropdownListId1').children('li').remove();
    }
    console.log("ant not after del: " + countNotifications());
    updateNotificationBell();
});


/**
 * Used to make the dropdown close when pressed outside the dropdown.
 */
$(document).on('click', 'body', function (e) {
    var a = e.target.parentElement;
    b = $(a).attr('class');

    if (b == undefined && a !== null || b.slice(0, 4) !== "noti" && opened) {
        $('#notifyBellId').parent().removeClass('open');
        $('#notifyBellId1').parent().removeClass('open');

        opened = false;
    } else if (b == "dropdown-toggle" && countNotifications() > 0) {
        opened = true;
        $('#notifyBellId').parent().toggleClass('open');
        $('#notifyBellId1').parent().toggleClass('open');
    }
});