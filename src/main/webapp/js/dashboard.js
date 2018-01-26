/**
 * Created by Simen Moen Storvik on 12.01.2018.
 */

//TODO: Vurdere bruken av lokalt lagrede brukere under opplisting av chores og handlelister mtp på autoriseringsproblemer.

function loadDashboard(){

    var house = getCurrentHousehold();
    if (house!==undefined) {
        printShoppingListsToDashboard(house);
        printHouseholdChoresToDashboard(house.houseId);
        printNewsToDashboard();
    }
    if (!householdsLoaded) addHouseholdsToList(getCurrentUser().userId);

    getDebt();
    getIncome();
}

function printHouseholdChoresToDashboard(id){
    ajaxAuth({
        url: "res/household/" + id + "/chores",
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            var today = new Date;
            if (data !== null && data !==undefined) {
                for (var i = 0; i < data.length; i++) {
                    var current = data[i];
                    if((today.getFullYear()===toJSDate(current.time).getFullYear()&&
                            today.getMonth()===toJSDate(current.time).getMonth()&&
                            today.getDate()===toJSDate(current.time).getDate()&&!current.done)){
                        if (current.user === undefined || current.user === null) {
                            var name = "None";
                        } else {
                            var name = current.user.name;
                        }
                        var inputString = "<tr>" +
                            "<td>" + current.title + "</td>" +
                            "<td>" + pad(current.time.dayOfMonth) + "."+pad(current.time.monthValue)+"." + current.time.year  + " " + pad(current.time.hour)+":"+pad(current.time.minute)+ "</td>" +
                            "<td>" + name + "</td>" +
                            "</tr>";
                        $("#dashboard_chores_table_body").append(inputString);
                    }
                }
            }else{
                $("#dashboard_chores_table_body").append("There are no chores for this household.");
            }
        }
    });
}

function printShoppingListsToDashboard(house) {
    if (house.shoppingLists!==null&&house.shoppingLists!==undefined) {
        for (var i = 0; i < house.shoppingLists.length; i++) {
            if(!(house.shoppingLists[i].isArchived)){
                var current = house.shoppingLists[i];
                var inputSting = "<li class='list-group-item'>" + current.name + "</li>";
                $("#dashboard_shopping_list_unordered_list").append(inputSting);
            }
        }
    }
}
function checkIfEnter(e) {
    var keycode = (e.keyCode ? e.keyCode : e.which);
    if (keycode == '13') {
        postNewsOnDashboard();
    }
}

function postNewsOnDashboard() {
    var post = $("#newPost").val();
    $("#newPost").val("");
    postNews(post, printNewsToDashboard);
}

function printNewsToDashboard(){
    getNews(function (data) {
        var loops = data.length;
        if (loops>6)loops=6;
        $(".messages-container").html("");
        for (var i = 0;i<loops;i++) {
            var html = "";
            var post = data[i];
            var time = post.time.dayOfMonth+"."+post.time.monthValue+" "+post.time.hour+":"+post.time.minute;
            var decoded = he.unescape(post.message);
            if (decoded.length<34){
                html = "<div class=\"well well-sm\">\n" +
                    "                            <div class=\"message-heading\">\n" +
                    "                                <b>"+post.user.name+"</b>\n" +
                    "                                <div style=\"float: right\">\n" +
                    "                                    <small>"+time+"</small>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                            <div style=\"word-wrap: break-word\" class=\"message-total\">"+post.message+"</div>\n" +
                    "                        </div>";
                $(".messages-container").append(html);
            } else {
                html = "<div class=\"well well-sm clickable\" onclick=\"toggleTeaser($(this))\">\n" +
                    "                            <div class=\"message-heading\">\n" +
                    "                                <b>"+post.user.name+"</b>\n" +
                    "                                <div style=\"float: right\">\n" +
                    "                                    <small>"+time+"</small>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                            <div style=\"word-wrap: break-word\" class=\"message-teaser\" id=\"msgt_"+i+"\"></div>\n" +
                    "                            <div style=\"word-wrap: break-word\" class=\"message-body\">"+post.message+"</div>\n" +
                    "                        </div>";
                $(".messages-container").append(html);
                $("#msgt_"+i).text(decoded.substring(0,34)+"...(show more)");
            }
        }
        $('.message-body').css('display','none');
    });
}

function toggleTeaser(msg){
    var daddy = msg.parent();

    var teaser = msg.find('.message-teaser');
    var body = msg.find('.message-body');

    daddy.find('.message-body').not(body).css('display','none');
    daddy.find('.message-teaser').not(teaser).css('display','block');

    if (teaser[0].style.display === "none"){
        teaser.css('display','block');
        body.css('display','none');
    } else {
        teaser.css('display','none');
        body.css('display','block');
    }
}