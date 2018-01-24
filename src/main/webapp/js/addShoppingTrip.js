var result;

function createPage() {
    if (getCurrentHousehold() !== null || getCurrentHousehold() !==  undefined) {
        ajaxAuth({
            url: "res/household/" + getCurrentHousehold().houseId + "/users",//Må byttes ut med currentHousehold!!!!
            type: 'get',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                console.log(data);
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
            url: "res/household/" + getCurrentHousehold().houseId + "/shopping_lists",//Må byttes ut med currentHousehold!!!!
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
}
function addMembers(members) {
    $("#members").empty();
    for(var i=0; i<members.length; i++) {
        $("#members").append(
            "<div class='shopping-list-member-line'>" +
            "<label style='margin-left: 5px'>" +
            "<input type='checkbox' id='check-" + i + "'>" + members[i].name + "</label>" +
            "</div>"
        );
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

    for(var i=0; i<result.length; i++) {
        if($("#check-" + i).is(":checked")) {
            contributors.push({
                "userId" : result[i].userId,
                "name" : result[i].name
            })
        }
    }

    for (i = 0; i < contributors.length; i++) {
        addNotification(contributors[i].userId, getCurrentHousehold().houseId, getCurrentUser().name + " have added you to a new Shopping Trip, " +name);
    }

    if(name === "" || comment === "" || sum === "" ||
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
                console.log(response);
                getShoppingTrips();
                document.getElementById("alertbox").innerHTML = '<div class="alert alert-success">' +
                    '<strong>Success!</strong> Shoppingtrip added.</div>';
                $(".alert-success").fadeTo(500, 500).slideUp(500, function(){
                    $(".alert-danger").slideUp(500);
                    $(function () {
                        $('#theModal').modal('toggle');
                    });
                });
            },
            error: function (response) {
                console.log("error");
                console.log(response);
            }

        });
    }
}