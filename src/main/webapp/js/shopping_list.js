function ajax_updateUsers (userIds, shoppingListId) {
    console.log('ajax_updateUsers()');
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