/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

$(document).ready(function(){
    function printShoppingListsToDashboard(householdId){
        getShoppingListsInHousehold(householdId, function(data){
            $.each(data, function(i,val){
                var inputString = "<tr>\n" +
                    "<td onclick='swapContent(\"shoppinglist.html\")'>"+val.name+"</td>\n" +
                    "<td>"+val.items.length+"</td>\n" +
                    "<td>"+val.participants.length+"</td>\n" +
                    "</tr>";
                $("#shopping_list_table_body").append(inputString);
            })
        })
    }
    printShoppingListsToDashboard(1);
});