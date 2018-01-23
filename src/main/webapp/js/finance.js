var income = [];
var debt = [];


function getDebt(){

    ajaxAuth({
        url: 'res/user/' + getCurrentUser().userId + '/debt/',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(debts){ //debts er av typen Debt
            var sum = 0;
            for(var i = 0; i < debts.length; i++){
                sum += debts[i].amount;
                debt[i] = debts[i];
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
                income[i] = incomes[i];
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

function loadFinanceTables(){
    var members = getCurrentHousehold().residents;
    for(var i = 0; i < debt.length; i++){
        for(var j = 0; j < members.length; j++){
            if(debt[i].toUser.userId == members[j].userId){
                debt[i].toUser.setName(members[j].name);
                j = members.length;
            }
        }
        $("#debtTable").append('<tr>\n' +
            '                                <td>' + debt[i].toUser.name + '</td>\n' +
            '                                <td>' + debt[i].amount + ',-</td>\n' +
            '                                <td>*pay*</td>\n' +
            '                            </tr>'
        );
    }

    for(var i = 0; i < income.length; i++){
        $("#incomeTable").append('<tr>\n' +
            '                                <td>' + income[i].toUser.name + '</td>\n' +
            '                                <td>' + income[i].amount + ',-</td>\n' +
            '                            </tr>'
        );
    }
}