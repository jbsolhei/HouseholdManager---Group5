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
                debt[i].toUser = members[j];
                j = members.length;
            }
        }
        $("#debtTable").append('<tr data-target="#theModal" data-toggle="modal" onclick="payMoney(' + i + ')">\n' +
            '                                <td>' + debt[i].toUser.name + '</td>\n' +
            '                                <td>' + debt[i].amount + ',-</td>\n' +
            '                            </tr>'
        );
    }

    for(var i = 0; i < income.length; i++){
        for(var j = 0; j < members.length; j++){
            if(income[i].toUser.userId == members[j].userId){
                income[i].toUser = members[j];
                j = members.length;
            }
        }
        $("#incomeTable").append('<tr onclick="sendPaymentRequest(' + i + ')">\n' +
            '                                <td>' + income[i].toUser.name + '</td>\n' +
            '                                <td>' + income[i].amount + ',-</td>\n' +
            '                            </tr>'
        );
    }
}

function payMoney(userId) {
    console.log("hei");
    callModal("modals/payMoney.html");
}

function sendPaymentRequest() {
    callModal("modals/payMoney.html");
}

function confirmPayment(){
    var amount = document.getElementById("paymentAmount").value;
    if(amount != "" && amount != null && amount > 0){

        document.getElementById("alertboxPayment").innerHTML = '<div id="alertboxPayment" class="alert alert-success">\n' +
            '  <strong>Success!</strong> Your payment is now registered.\n' +
            '</div>';
    } else {
        document.getElementById("alertboxPayment").innerHTML = '<div id="alertboxPayment" class="alert alert-danger">\n' +
            '  <strong>Failed!</strong> You have to put in the amount you have payed. <br> (no letters or negative numbers)' +
            '</div>';
    }
}