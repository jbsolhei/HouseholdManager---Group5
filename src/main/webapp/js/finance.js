var income = [];
var debt = [];
var name = "";
var amount = 0;
var payOrAlert = 0;
var index = 0;


function getDebt(){

    ajaxAuth({
        url: 'res/user/' + getCurrentUser().userId + '/debt/',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(debts){ //debts er av typen Debt
            console.log("getDebt():");
            console.log(debts);
            var sum = 0;
            debt = [];
            for(var i = 0; i < debts.length; i++){
                sum += debts[i].amount;
                debt[i] = debts[i];
            }
            $("#debtSumOutgoing").replaceWith('<div class="col-xs-3 nopadding debt-sum" id="debtSumOutgoing">' + sum + ',-</div>');
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
            income = [];
            for(var i = 0; i < incomes.length; i++){
                sum += incomes[i].amount;
                income[i] = incomes[i];
            }
            $("#debtSumIncoming").replaceWith('<div class="col-xs-3 nopadding debt-sum" id="debtSumIncoming">' + sum + ',-</div>');
            console.log("getIncome():");
            console.log(income);
        },
        error: function(data) {
            console.log("Error in getIncomes");
            console.log(data);
        },
        dataType: "json"
    });
}

function loadFinanceTables(){
    console.log("loadFinanceTables()");
    console.log(debt);
    console.log(income);

    $("#debtTable").html("<tbody id=\"debtTable\"></tbody>");
    $("#incomeTable").html("<tbody id=\"incomeTable\"></tbody>");

    var members = getCurrentHousehold().residents;
    for(var i = 0; i < debt.length; i++){
        for(var j = 0; j < members.length; j++){
            if(debt[i].toUser.userId == members[j].userId){
                debt[i].toUser = members[j];
                j = members.length;
            }
        }
        $("#debtTable").append('<tr id="debt' + i + '" data-target="#theModal" data-toggle="modal" onclick="payMoney(' + i + ')">\n' +
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
        $("#incomeTable").append('<tr id="income' + i + '" data-target="#theModal" data-toggle="modal" onclick="sendPaymentRequest(' + i + ')">\n' +
            '                                <td>' + income[i].toUser.name + '</td>\n' +
            '                                <td>' + income[i].amount + ',-</td>\n' +
            '                            </tr>'
        );

    }
}

function payMoney(i) {
    index = i;
    payOrAlert = 0;
    callModal("modals/financeModal.html");
    name = debt[i].toUser.name;
    amount = debt[i].amount;

}

function sendPaymentRequest(i) {
    index = i;
    payOrAlert = 1;
    callModal("modals/financeModal.html");
    name = income[i].toUser.name;
    amount = income[i].amount;
}

function loadFinanceModal() {
    if(payOrAlert == 0){
        document.getElementById("payMoneyText").innerHTML = '<p id="payMoneyText">Do you confirm that you have payed ' + name + ' ' + amount + ',- ?</p>';
    } else {
        $("#financeModalTitle").replaceWith('<h4 id="financeModalTitle" class="modal-title">Send payment alert</h4>');
        document.getElementById("payMoneyText").innerHTML = '<p id="payMoneyText">Do you want to send ' + name + ' an alert to pay the ' + amount + ',- that they owe you?</p>';
        $("#confirmPaymentButton").replaceWith('<button id="confirmSendAlertPaymentButton" type="button" class="btn btn-primary" onclick="confirmSendAlertPayment()">Send alert</button>');
    }

}


function confirmPayment(){
    ajaxAuth({
        url: "res/user/"+ getCurrentUser().userId +"/debt/" + debt[index].toUser.userId,
        type: "DELETE",
        data: debt[index].toUser.userId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function () {
            console.log("Debt deleted.");
            console.log(debt);
            $("#closeFinanceModalButton").click();
            $("#debt" + index).remove();
        },
    });
    addNotification(debt[index].toUser.userId, getCurrentHousehold().houseId, getCurrentUser().name + " have payed you the " + debt[index].amount + ",- they owed you. If this is not correct, please contact " + getCurrentUser().name + ".");
}

function confirmSendAlertPayment() {
    addNotification(income[index].toUser.userId, getCurrentHousehold().houseId, getCurrentUser().name + " asks you to pay the " + income[index].amount + ",- that you owe.");
    $("#closeFinanceModalButton").click();
}