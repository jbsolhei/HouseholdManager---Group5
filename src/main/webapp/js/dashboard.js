/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

//TODO: Vurdere bruken av lokalt lagrede brukere under opplisting av todos og handlelister mtp på autoriseringsproblemer.

function loadDashboard(){
    var houseId = getCurrentHousehold().houseId;
    printShoppingListsToDashboard(houseId);
    printHouseholdTodosToDashboard(houseId);
}

function printHouseholdTodosToDashboard(householdId){
    getTaskinHousehold(householdId, function(data){
        $.each(data, function(i,val){
            var inputString = "<tr>\n" +
                "<td>" + val.description + "</td>" +
                "<td>" + val.date + "</td>" +
                "<td>" + getUserFromId(val.userId, function(data){return data.name}) + "</td>"+
                "</tr>";
            $("#dashboard_todos_table_body").append(inputString);
        })
    })
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