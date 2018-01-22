var months = ["January", "February", "March", "April", "May", "June", "July",
    "August", "September", "October", "November", "December"];

months = fixData(months);

var userStats;
var householdStats;

function fixData(innData) {
    var d = new Date();
    var startMonth = d.getMonth() + 1;

    var a = innData.slice(startMonth);
    var b = innData.slice(0, startMonth)
    Array.prototype.push.apply(a, b);

   return a;
}

function drawExpenseGraph() {
    ajaxAuth({
        url: "res/household/" + getCurrentHousehold().houseId + "/stats/expenses",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            householdStats = result;
            householdStats = fixData(householdStats);
            console.log(householdStats);
            showExpenseStats();
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function showExpenseStats() {
    var ctx = document.getElementById('myChart').getContext('2d');
    var chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',
        // The data for our dataset
        data: {
            labels: months,
            datasets: [{
                label: 'Money Spent Per Month',
                backgroundColor: 'rgba(255, 99, 132, 0.1)',
                borderColor: 'rgb(255, 99, 132)',
                data: householdStats,

                //no curves when 0
                lineTension: 0.1

            }]
        },

        // Configuration options go here
        options: {
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Money Spent'
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Month'
                    }
                }],
            }
        }
    });
}

function drawChoreStats() {
    ajaxAuth({
        url: "res/household/" + getCurrentHousehold().houseId + "/stats/tasks",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (result) {
            userStats = result;
            for (i = 0; i < userStats.length; i++) {
                userStats[i].tasks = fixData(userStats[i].tasks);
            }
            showChoreStats(userStats);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function showChoreStats(data) {

    var sets = [];

    for (i = 0; i < data.length; i++) {
        var newSet = {
            label: data[i].userName,
            backgroundColor: 'rgba(0,0,0,0)',
            borderColor: '#'+Math.floor(Math.random()*16777215).toString(16),

            data: data[i].tasks,

            lineTension: 0.2
        };

        sets.push(newSet);
    }

    var ctx = document.getElementById('myChart').getContext('2d');
    var chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',

        // The data for our dataset
        data: {
            labels: months,
            datasets: sets
        },

        // Configuration options go here
        options: {
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Chores Done'
                    }
                }],
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Month'
                    }
                }],
            }
        }
    });
}