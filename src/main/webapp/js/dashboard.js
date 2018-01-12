/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

$(document).ready(function() {
    printShoppingListsToDashboard(1);
});
function printShoppingListsToDashboard(householdId){
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