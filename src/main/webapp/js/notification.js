/**
 * Used to add a notification to the database.
 * @param userId The id of the user that the notification is going to.
 * @param houseId The house that the notification is connected to.
 * @param message The message in the notification.
 * @param dateTime The date and time of the notification.
 */
function addNotification(userId, houseId, message, dateTime) {
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
            console.log(result);
            updateNotificationDropdown(result);
            return result;
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function updateNotificationDropdown(notifications) {
    for (i = 0; i < notifications.length; i++) {
        $("#notifyDropdownListId").prepend("<li id='notifId"+notifications[i].notificationId+"' class='notificationElement list-group-item'><p class='notifyMessageId'>"+ notifications[i].message +"</p><p class='notifyDateTimeId'>"+notifications[i].dateTime+"</p></li>");
    }
}













