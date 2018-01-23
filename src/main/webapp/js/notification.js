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

    console.log(dateTime);

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
 * Used to set the notification status to read, so that it does not show up anymore for the user.
 * @param notificationId The id of the notification that you want to update.
 */
function updateNotification(notificationId) {
    ajaxAuth({
        url: "res/notification/" + notificationId + "/updateStatus",
        type: "PUT",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            console.log("Notification updated: " + result);
        },
        error: function (e) {
            console.log(e);
        }
    })
}

/**
 * Get all the notifications that hasn't been read by a user.
 * @param userId The id of the user.
 */
function getNotifications(userId) {
    ajaxAuth({
        url: "res/user/"+ userId +"/notifications",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            updateNotificationDropdown(result);
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
function updateNotificationDropdown(notifications) {
    for (i = 0; i < notifications.length; i++) {
        var dateTime = notifications[i].dateTime;
        dateTime = dateTime.slice(0, dateTime.length - 2);
        var message = notifications[i].message;
        var id = notifications[i].notificationId;
        var houseName = notifications[i].houseName;
        if (houseName == null) houseName = "";

        $("#notifyDropdownListId").prepend("<li id='notifId"+id+"' class='noti notificationElement list-group-item'><p class='notifyMessageId'>"+ message +"</p><p class='noti notifyDateTimeId'>"+dateTime+"<span class='noti notifyHousehold'>"+houseName+"</span></p></li>");
    }
}

/**
 * Counts the number of notifications in the dropdown.
 * @returns {jQuery} Returns the number of notifications.
 */
function countNotifications() {
    var notifications = $('#notifyDropdownListId').children('li').length;
    return notifications;
}

/**
 * Updates the notifications bell to the color orange if there are some notifications left in the dropdown.
 */
function updateNotificationBell() {
    if (countNotifications() > 0) {
        $("#notifyBellId").css('color', 'orange');
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
    updateNotification(id);
    $(this).remove();

    if (countNotifications() <= 0) {
        $("#notifyBellId").css('color', 'white');
        $('#notifyBellId').parent().removeClass('open');
    }
});


/**
 * Used to make the dropdown close when pressed outside the dropdown.
 */
$(document).on('click', 'body', function (e) {
    var a = e.target.parentElement;
    b = $(a).attr('class');

    if (b === undefined && a !== null || b.slice(0, 4) !== "noti" && opened) {
        $('#notifyBellId').parent().removeClass('open');

        opened = false;
    } else if (b == "dropdown-toggle" && countNotifications() > 0) {
        opened = true;
        $('#notifyBellId').parent().toggleClass('open');
    }
});












