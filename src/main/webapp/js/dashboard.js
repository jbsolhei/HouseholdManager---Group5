/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

var houseId = 1;
$(document).ready(function() {
    loadDashboard();
});

function loadDashboard(){
    printShoppingListsToDashboard(houseId);
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