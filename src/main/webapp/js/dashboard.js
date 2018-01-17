/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

//TODO: Vurdere bruken av lokalt lagrede brukere under opplisting av todos og handlelister mtp på autoriseringsproblemer.

function loadDashboard(){
    var house = getCurrentHousehold();
    if (house!==undefined) {
        printShoppingListsToDashboard(house.houseId);
        printHouseholdTodosToDashboard(house.houseId);
    }
}

function printHouseholdTodosToDashboard(id){
    ajaxAuth({
        url: "res/household/" + id + "/tasks",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data !== null) {
                for (var i = 0; i < data.length; i++) {
                    var current = data[i];
                    if (current.user === undefined || current.user === null) {
                        var name = "None";
                    } else {
                        var name = current.user.name;
                    }
                    var inputString = "<tr>\n" +
                        "<td>" + current.description + "</td>" +
                        "<td>" + current.date + "</td>" +
                        "<td>" + name + "</td>" +
                        "</tr>";
                    $("#dashboard_todos_table_body").append(inputString);
                }
            }
        }
    });
}

function printShoppingListsToDashboard(id) {
    ajaxAuth({
        url: "res/household/"+id+"/shopping_lists",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data!==null) {
                var house = JSON.parse(window.localStorage.getItem("house"));
                house.shoppingLists = data;
                window.localStorage.setItem("house",JSON.stringify(house));
                console.log(window.localStorage.getItem("house"));
                for (var i = 0; i < data.length; i++) {
                    var current = data[i];
                    var inputString = "<tr>\n" +
                        "<td onclick='navToShoppingList(" + i + ")'>" + current.name + "</td>\n" +
                        "<td>" + current.items.length + "</td>\n" +
                        "<td>" + current.users.length + "</td>\n" +
                        "</tr>";
                    //TODO: the onClick() navigates to the shoppingList body, but doesn't load the selected shoppingList.
                    $("#dashboard_shopping_list_table_body").append(inputString);
                }
            }
        },
        dataType: "json"
    });

}