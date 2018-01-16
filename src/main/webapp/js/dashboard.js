/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

//TODO: Vurdere bruken av lokalt lagrede brukere under opplisting av todos og handlelister mtp på autoriseringsproblemer.

function loadDashboard(){
    var house = getCurrentHousehold();
    console.log(house);
    if (house!==undefined) {
        printShoppingListsToDashboard(house);
        printHouseholdTodosToDashboard(house);
    }
}

function printHouseholdTodosToDashboard(house){
    for (var i=0;i<house.todoList.length;i++){
        var current = house.todoList[i];
        if (current.user===undefined||current.user===null){
            var name = "None";
        } else {
            var name = current.user.name;
        }
        var inputString = "<tr>\n" +
            "<td>" + current.description + "</td>" +
            "<td>" + current.date + "</td>" +
            "<td>" + name + "</td>"+
            "</tr>";
        $("#dashboard_todos_table_body").append(inputString);
    }
}

function printShoppingListsToDashboard(householdId) {
    householdSHL = getCurrentHousehold().shoppingLists;
    $.each(householdSHL, function (i, val) {
        var inputString = "<tr>\n" +
            "<td onclick='navToShoppingList(" + i + ")'>" + val.name + "</td>\n" +
            "<td>" + val.items.length + "</td>\n" +
            "<td>" + val.participants.length + "</td>\n" +
            "</tr>";
        //TODO: the onClick() navigates to the shoppingList body, but doesn't load the selected shoppingList.
        $("#dashboard_shopping_list_table_body").append(inputString);
    })
}