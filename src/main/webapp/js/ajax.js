// HOW TO
// make functions like this in index.js
function alertUser(id){
    getUserFromRest(id,function (user) {
        alert(user.name)
    })
}
// only use in-site if you have to
// example:
// <button onclick='getUserFromRest(1,function (user){alert(user.name)})'> alert user </button>
function getHouseholdsForUser(userId, handleData){
    $.ajax({
        url:"res/user/hh/" + userId,
        type: "GET",
        contentType: "application/json; charser=utf-8",
        success: function(data){
            handleData(data);
        },
        dataType: "json"
    });
}

function getTasksForUser(userId, handleData){
    $.ajax({
        url:"res/user/tasks/" + userId,
        type: "GET",
        contentType: "application/json; charser=utf-8",
        success: function(data){
            handleData(data);
        },
        dataType: "json"
    });
}

function getUsersInHousehold(householdId, handleData) {
    $.ajax({
        url:"res/household/"+householdId+"/users",
        type: "GET",
        contenType: "application/json; charser=utf-8",
        success: function(data){
            handleData(data);
        },
        dataType:"json"
    })
}
function getHouseholdFromRest(id,handleData) {
    $.ajax({
        url: "res/household/"+id,
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function(data){
            handleData(data);
        },
        dataType: "json"
    });
}