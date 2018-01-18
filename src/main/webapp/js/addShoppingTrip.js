/**
 * Created by Camilla Velvin on 15.01.2018.
 */
var result;

function createPage() {
    console.log(getCurrentHousehold());
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/users",//Må byttes ut med currentHousehold!!!!
        type: 'get',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            console.log(data);
            result = data;
            addMembers(data);
        },
        error: function(result) {
        }
    });
    ajaxAuth({
        url: "res/household/"+getCurrentHousehold().houseId+"/shopping_lists",//Må byttes ut med currentHousehold!!!!
        type: 'get',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            console.log(data);
            addShoppinglists(data);
        },
        error: function(result) {
        }
    });
}
function addMembers(data) {
    $("#members").html("");
    for(var i=0; i<data.length; i++) {
        $("#members").append("<label style='float: left; margin-left: 5px'><input type='checkbox' id=check-"+i+" value=''>"+data[i].name+"</label><br>");
    }
}
function addShoppinglists(data) {
    $("#sel1").html("");
    for(var i=0; i<data.length; i++) {
        console.log(data[i].shoppingListId);
        $("#sel1").append("<option id="+data[i].shoppingListId+">"+data[i].name+"</option>");
    }
}

function addShoppingTrip() {
    var name = $("#tripname").val();
    var comment = $("#trip-comments").val();
    var sum = $("#trip-sum").val();
    var contributors = [];
    var shoppingList = $("#sel1").val();
    var id = $("#sel1 option:selected").attr('id');

    for(var i=0; i<result.length; i++) {
        if($("#check-" + i).is(":checked")) {
            contributors.push({
                "userId" : result[i].userId,
                "name" : result[i].name
            })
        }
    }

    if(name === "" || comment == "" || sum == "" ||
        shoppingList == "" || id == "" ||
        contributors.length == 0) {
        document.getElementById("alertbox").innerHTML = '<div class="alert alert-danger">' +
            '<strong>Failed to create user.</strong> Please fill in all the forms. </div>';

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
                    '<strong>Success!</strong> You have now created a user.</div>';
                $(".alert-success").fadeTo(3000, 500).slideUp(500, function(){
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