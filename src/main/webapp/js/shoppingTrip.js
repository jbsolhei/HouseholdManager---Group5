/**
 * Created by Camilla Velvin on 15.01.2018.
 */
var activeTab =0;
var numberOfItems = 0;
var activeSHT;

function getShoppingTrips() {
    ajaxAuth({
        url: "res/shoppingtrip/"+getCurrentHousehold().houseId,//Må byttes ut med currentHousehold!!!!
        type: 'get',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            console.log(data);
            if (data!==null&&data!==undefined) {
                numberOfItems = data.length;
                if (numberOfItems!==0) {
                    SHT = data;
                    viewShoppingTrips(data);
                }
            }
        },
        error: function(result) {
        }
    });
}

function deleteShoppingTrip(at){
    ajaxAuth({
        type: "DELETE",
        url: "res/shoppingtrip/"+at,
        success:function(){
            console.log("List #" + at + " deleted.");
        }
    })
}

function viewShoppingTrips(data) {
    $("#shoppingtrips").html("");
    for(var i=0; i<data.length; i++) {
        $("#shoppingtrips").append("<li onclick='viewInformation("+data[i].shoppingTripId+", "+i+")' id="+i+"><a>"+data[i].name+"</a></li>");
    }
    $("#"+activeTab).addClass("active");
    viewInformation(data[0].shoppingTripId, 0);
}
function viewInformation(shoppingTripId, i) {
    ajaxAuth({
        url: "res/shoppingtrip/"+shoppingTripId+"/trip",
        type: 'get',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            activeSHT = result;
            console.log(activeSHT);
            updateInformation(result)
        },
        error: function (result) {
        }
    });
    $("#" + activeTab).removeClass("active");
    $("#" + i).addClass("active");
    activeTab = i;
}
function updateInformation(result) {
    $("#heading").html("");
    $("#heading").append("<h4>Registered by: "
        +result.userName+"</h4><small>--"+result.shoppingDate.dayOfMonth+". "
        +result.shoppingDate.month+" "+result.shoppingDate.year+"--</small>");
    $("#comments").html("");
    $("#comments").append("<h5><b>Comment:</b></h5><p>"+result.comment+"</p><br>");
    $("#sum").html("");
    $("#sum").append("<h4>Sum: "+result.expence+" kr.</h4>")
    $("#shoppinglist").html("");
    if(result.shopping_listName === null) {

    } else {
        $("#shoppinglist").append("<h5><b>Attached shopping list:</b></h5><p>" + result.shopping_listName + "</p>");
    }

    $("#list").html("");
    for(var i=0; i<result.contributors.length; i++) {
        $("#list").append("<li>"+result.contributors[i].name+"</li>");
    }
}
