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
            updateInformation(result)
        },
        error: function (result) {
        }
    });
    $("#tab-" + activeTab).removeClass("active");
    $("#tab-" + i).addClass("active");
    activeTab = i;
}

function updateInformation(result) {
    $("#headingShoppingTrip").html("<h4>" + result.name + "</h4>");
    $("#userAndDateShoppingtrip").html("<p><small>Registered by " +result.userName+"   on   "+result.shoppingDate.dayOfMonth+"-" +result.shoppingDate.month+"-"+result.shoppingDate.year+"</small></p>");
    if (result.comment == "") $("#comments").html("<p class='STHeaderText'><b>Comment:</b></p><p id='noCommentID'>No Comments</p><br>");
    else $("#comments").html("<p class='STHeaderText'><b>Comment:</b></p><p>"+result.comment+"</p><br>");
    $("#sumShoppingList").html("<p class='pull-right'>"+result.expence+",-</p>");
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

function switchShoppingtripContent(num) {
    if(num == 0){
        $("#shoppingtripRightPanelSecondPanel").addClass("hide");
        $("#shoppingtripRightFirstPanel").removeClass("hide");
        console.log("har nå skiftet skjerm");
    } else if (num == 1){
        $("#shoppingtripRightFirstPanel").addClass("hide");
        $("#shoppingtripRightPanelSecondPanel").removeClass("hide");

        createPageAddShoppingTrip();
    }

}



/* HER KOMMER MASSE FOR Å ADD SHOPPING LIST */


var result;

function createPageAddShoppingTrip() {
    if (getCurrentHousehold() !== null || getCurrentHousehold() !==  undefined) {
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/users",
            type: 'get',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                result = data;
                addMembers(data);
            },
            error: function (result) {
            }
        });
        $("#sel1").html(
            $("<option>").attr("id", "trip-0").data("trip-id", 0).text("-None-")
        );
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/shopping_lists/user/" + getCurrentUser().userId,//Må byttes ut med currentHousehold!!!!
            type: 'get',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                addShoppinglists(data);
            },
            error: function (result) {
            }
        });
    }
    $("#tripname").keyup(function(){
        if($("#tripname").val().length>40){
            $("#tripname").val($("#tripname").val().substring(0,40));
        }
    });
}
function addMembers(members) {
    $("#members").empty();
    for(var i=0; i<members.length; i++) {
        if(members[i].userId == getCurrentUser().userId){
            $("#members").append(
                "<div class='shopping-list-member-line'>" +
                "<label style='margin-left: 5px'>" +
                "<input type='checkbox' id='check-" + i + "' checked>" + members[i].name + "</label>" +
                "</div>"
            );
        } else {
            $("#members").append(
                "<div class='shopping-list-member-line'>" +
                "<label style='margin-left: 5px'>" +
                "<input type='checkbox' id='check-" + i + "'>" + members[i].name + "</label>" +
                "</div>"
            );
        }


    }
}
function addShoppinglists(data) {
    for(var i=0; i<data.length; i++) {
        $("#sel1").append(
            $("<option>")
                .attr("id", "trip-" + data[i].shoppingListId)
                .data("trip-id", data[i].shoppingListId)
                .text(data[i].name)
        );
    }
}

function addShoppingTrip() {
    var name = $("#tripname").val();
    var comment = $("#trip-comments").val();
    var sum = $("#trip-sum").val();
    var contributors = [];
    var shoppingList = $("#sel1").val()-1;
    var id = $("#sel1 option:selected").data("trip-id");

    if (result !== undefined) {
        for(var i=0; i<result.length; i++) {
            if($("#check-" + i).is(":checked")) {
                contributors.push({
                    "userId" : result[i].userId,
                    "name" : result[i].name
                })
            }
        }
    }

    if(name === "" || sum === "" ||
        shoppingList === "" || id === "" ||
        contributors.length === 0) {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Please fill in all the forms. </div>';

    } else {
        var data = {"name" : name, "expence" : sum, "comment" : comment, "userId" : getCurrentUser().userId,
            "userName" : getCurrentUser().name, "contributors" : contributors,
            "houseId" : getCurrentHousehold().houseId, "shopping_listId" : id, "shopping_listName" : shoppingList};

        ajaxAuth({
            url: "res/shoppingtrip",
            type: 'post',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (response) {
                if(response == true){
                    for (i = 0; i < contributors.length; i++) {
                        addNotification(contributors[i].userId, getCurrentHousehold().houseId, "You have been added to Shopping Trip, \""+name+"\", by " + getCurrentUser().name);
                    }
                    getShoppingTrips();

                    document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
                        '<strong>Success!</strong> Shoppingtrip added.</div>';

                    $(".alert-success").fadeTo(500, 500).slideUp(500, function(){
                        switchShoppingtripContent(0);
                    });



                } else {
                    document.getElementById("alertbox").innerHTML = '<div id="alertbox" class="alert alert-danger">' +
                        '<strong>Failed!</strong>Something went wrong. Please try again.</div>';
                }

            },
            error: function (response) {
                console.log("error");
                console.log(response);
            }

        });
    }
}
