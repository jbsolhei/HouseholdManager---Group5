function getDebt(){
    ajaxAuth({
        url: 'res/user/' + getCurrentUser().userId + '/debt/',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function(debts){ //debts er av typen Debt

            /* KODE HER FOR HVA MAN GJØR MED ARRAYLISTEN MED GJELD */


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

            /* KODE HER FOR HVA MAN GJØR MED ARRAYLISTEN MED INCOME (DET ANDRE PERSONER SKYLDER USER) */


        },
        error: function(data) {
            console.log("Error in getIncomes");
            console.log(data);
        },
        dataType: "json"
    });
}