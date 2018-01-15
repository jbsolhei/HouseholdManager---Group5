/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

var houseId = 1;
$(document).ready(function() {
    loadDashboard();
});

//TODO: Vurdere bruken av lokalt lagrede brukere under opplisting av todos og handlelister mtp på autoriseringsproblemer.

function loadDashboard(){
    printShoppingListsToDashboard(houseId);
    printHouseholdTodosToDashboard(houseId);
    console.log(getCurrentUser().userId);
}

function printHouseholdTodosToDashboard(householdId){
    console.log("printHouseholdTodosToDashboard()");
    getTaskinHousehold(householdId, function(data){
        console.log(data);
        console.log(JSON.stringify(data));
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

function printShoppingListsToDashboard(householdId){
    console.log("PrintShoppingListsToDashboard()")
    getShoppingListsInHousehold(householdId, function(data){
        $.each(data, function(i,val){
            var inputString = "<tr>\n" +
                "<td onclick='navToShoppingList("+val.shoppingListId+")'>"+val.name+"</td>\n" +
                "<td>"+val.items.length+"</td>\n" +
                "<td>"+val.participants.length+"</td>\n" +
                "</tr>";
            $("#dashboard_shopping_list_table_body").append(inputString);
        })
    })
}