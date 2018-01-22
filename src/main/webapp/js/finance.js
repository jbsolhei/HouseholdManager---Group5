function getDebt(){

    ajaxAuth({
        url: 'res/user/' + getCurrentUser().userId + '/debt/',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(debts){ //debts er av typen Debt
            var sum = 0;
            for(var i = 0; i < debts.length; i++){
                sum += debts[i].amount;
            }
            $("#debtSumOutgoing").replaceWith('<div class="col-xs-3 nopadding debt-sum" id="debtSumOutgoing">' + sum + ' kr</div>');

        },
        error: function(data) {
            console.log("Error in getDebts");
            console.log(data);
        },
        dataType: "json"
    });
}


function getIncome(){
    ajaxAuth({
        url: 'res/user/' + getCurrentUser().userId + '/income/',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(incomes){ //incomed er av typen Debt
            var sum = 0;
            for(var i = 0; i < incomes.length; i++){
                sum += incomes[i].amount;
            }
            $("#debtSumIncoming").replaceWith('<div class="col-xs-3 nopadding debt-sum" id="debtSumIncoming">' + sum + ' kr</div>');
        },
        error: function(data) {
            console.log("Error in getIncomes");
            console.log(data);
        },
        dataType: "json"
    });
}