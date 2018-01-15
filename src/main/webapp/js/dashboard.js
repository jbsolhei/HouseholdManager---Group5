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
}

function printHouseholdTodosToDashboard(householdId){
    console.log("printHouseholdTodosToDashboard()");
    getTaskinHousehold(householdId, function(data){
        $.each(data, function(i,val){
            var inputString = "<tr>\n" +
                "<td>" + val.description + "</td>" +
                "<td>" + val.date + "</td>" +
                "<td>" + getUserFromId(val.userId).name + "</td>";
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