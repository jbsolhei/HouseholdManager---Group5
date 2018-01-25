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
                    viewShoppingTrips(data);
                }
            }
        },
        error: function(result) {
        }
    });
}

function deleteShoppingTrip(){
    ajaxAuth({
        type: "DELETE",
        url: "res/shoppingtrip/"+activeSHT.shoppingTripId,
        success:function(){
            console.log("List #" + activeSHT.shoppingTripId + " deleted.");
            getShoppingTrips();
        }
    })
}

function viewShoppingTrips(data) {
    $("#shoppingtrips").html("");
    for(var i=0; i<data.length; i++) {
        $("#shoppingtrips").append("<li class='' onclick='viewInformation("+data[i].shoppingTripId+", "+i+")' id='tab-"+i+"'><a>"+data[i].name+"</a></li>");
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
            activeSHT.shoppingTripId = shoppingTripId;
            console.log(activeSHT);
            updateInformation(result)
        },
        error: function (result) {
        }
    });
    $("#tab-" + activeTab).removeClass("active");
    console.log(i);
    $("#tab-" + i).addClass("active");
    activeTab = i;
}

function updateInformation(result) {
    $("#headingShoppingTrip").html("<h4>" + result.name + "</h4>")
    $("#userAndDateShoppingtrip").html("<p><small>Registered by " +result.userName+"   on   "+result.shoppingDate.dayOfMonth+"-" +result.shoppingDate.month+"-"+result.shoppingDate.year+"</small></p>");
    if (result.comment == "") $("#comments").html("<p class='STHeaderText'><b>Comment:</b></p><p id='noCommentID'>No Comments</p><br>");
    else $("#comments").html("<p class='STHeaderText'><b>Comment:</b></p><p>"+result.comment+"</p><br>");
    $("#sumShoppingList").html("<p class='pull-right'>"+result.expence+",-</p>")
    if(result.shopping_listName === null) {
        $("#shoppinglist").html("<p class='STHeaderText'><b>No attached shopping list</b></p>");
    } else {
        $("#shoppinglist").html("<p class='STHeaderText'><b>Attached shopping list:</b></p><p>" + result.shopping_listName + "</p>");
    }

    //result.setExpencePerPerson();


    $("#list").html("");
    for(var i=0; i<result.contributors.length; i++) {
        $("#list").append("<li>"+result.contributors[i].name+" (" + result.expencePerPerson + ",-)</li>");
    }
}
