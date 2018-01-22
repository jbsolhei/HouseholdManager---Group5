/**
 * Ajax-method to create a new Shopping List given a shopping list name
 *
 * @param shoppingListName, the shopping list name
 * @param userIds, an array of users you want to be associated with the new list
 * @success returns the generated shopping list ID, and updates users
 */
function ajax_createNewList(shoppingListName, userIds) {
    console.log('ajax_createNewList()');
    console.log('data:' + shoppingListName);
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/',
        data: shoppingListName,
        contentType: 'text/plain',
        success: function (shoppingListId) {
            console.log("success: ajax_createNewList(). data: " + shoppingListId);
            ajax_updateUsers(userIds, shoppingListId);
        },
        error: function () {
            console.log("error: ajax_createNewList()");
        }
    })
}

/**
 * Ajax-method to update users associated with a shopping list
 *
 * @param userIds, an array of user IDs that are associated with the shopping list
 * @param shoppingListId, the shopping list ID
 */
function ajax_updateUsers (userIds, shoppingListId) {
    console.log('ajax_updateUsers()');
    console.log(JSON.stringify(userIds));
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId + '/users',
        data: JSON.stringify(userIds),
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("success: ajax_updateUsers()");
        },
        error: function () {
            console.log("error: ajax_updateUsers()");
        }
    })
}

/**
 * Ajax-method to get a shopping list given it's shopping list ID
 *
 * @param shoppingListId, the shopping list ID
 */
function ajax_getShoppingList(shoppingListId) {
    console.log('ajax_getShoppingList()');
    ajaxAuth({
        type: 'GET',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            console.log("success: ajax_getShoppingList()");
            console.log(data);
        },
        error: function (data) {
            console.log("error: ajax_getShoppingList()");
            console.log(data);
        }
    })
}

/**
 * Ajax-method that updates checkedBy to an Item given the item ID
 * @param itemId, the item ID
 */
function ajax_updateCheckedBy(itemId) {
    console.log('ajax_updateCheckedBy()');
    console.log('json:' + JSON.stringify(getCurrentUser().userId));
    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/items/' + itemId + '/user/',
        data: JSON.stringify(getCurrentUser().userId),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("success: ajax_updateCheckedBy()");
        },
        error: function (result) {
            console.log("error: ajax_updateCheckedBy()");
            console.log(result);
        }
    })
}

/**
 * Deletes a shopping list given the shopping list ID
 *
 * @param shoppingListId, the shopping list ID
 */
function ajax_deleteShoppingList(shoppingListId) {
    console.log('ajax_deleteShoppingList()');
    ajaxAuth({
        type: 'DELETE',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        success: function () {
            console.log("success: ajax_deleteShoppingList()");
        },
        error: function (result) {
            console.log("error: ajax_deleteShoppingList()");
            console.log(result);
        }
    })
}

/**
 * Ajax-method to update the archived status of a shopping list
 *
 * @param shoppingListId, the shopping list ID
 * @param archived, the wanted value of archived
 */
function ajax_updateArchived(shoppingListId, archived){
    console.log('ajax_updateArchived()');
    ajaxAuth({
        type: 'PUT',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_lists/' + shoppingListId,
        data: archived,
        contentType: 'text/plain',
        success: function () {
            console.log("success: ajax_updateArchived()");
        },
        error: function (result) {
            console.log("error: ajax_updateArchived()");
            console.log(result);
        }
    })
}

function updateUsersAjax(shoppingListId, users) {
    console.log("!!! - shoppingListId: " + shoppingListId +  " houseId: " + getCurrentHousehold().houseId);
    console.log(JSON.stringify({'userids': users}));

    ajaxAuth({
        type: 'POST',
        url: 'res/household/' + getCurrentHousehold().houseId + '/shopping_list/' + shoppingListId + '/users',
        data: JSON.stringify(users),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("Shopping List successfully added to database");
            navToShoppingList(activeSHL);
        },
        error: function (result) {
            console.log(result);
        }
    });

}