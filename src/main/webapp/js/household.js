function loadHousehold(){
    $("#hh_name").html(getCurrentHousehold().name);
    $("#hh_address").html(getCurrentHousehold().address);
//    buildAdminTable();
    buildMemberTable();
    buildOtherInfoHousehold();
}

function buildMemberTable(){
    var members = getCurrentHousehold().residents;
    var admins = getCurrentHousehold().admins;
    console.log(getCurrentHousehold());

    for (var i = 0; i<members.length; i++) {
        var found = false;
        for(var j=0; j<admins.length; j++) {
            if(members[i].userId === admins[j].userId) {
                var user = members[i];
                $("#members").append(
                    "<tr onclick=\"showMiniProfile(" + i + ")\">\n" +
                    "<td><i data-toggle='tooltip' data-placement='bottom' title='Administrator for this household' class='glyphicon glyphicon-star-empty'></i></td>" +
                    "<td><div class=\"img-circle\"><img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\"></div></td>\n" +
                    "<td>"+user.name+"</td>\n" +
                    "<td>"+user.email+"</td>\n" +
                    "<td>"+user.telephone+"</td>\n" +
                    "</tr>");
                found = true;
            }
        }
        if(found === false) {
            var user = members[i];
            $("#members").append(
                "<tr onclick=\"showMiniProfile(" + i + ")\">\n" +
                "<td></td>" +
                "<td><div class=\"img-circle\"><img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\"></div></td>\n" +
                "<td>" + user.name + "</td>\n" +
                "<td>" + user.email + "</td>\n" +
                "<td>" + user.telephone + "</td>\n" +
                "</tr>");
        }
    }
}
function buildOtherInfoHousehold() {
    $("#householdAddress").html("");
    $("#householdAddress").append("<p>"+getCurrentHousehold().address+"</p>");
}

function showMiniProfile(index){
    var members = getCurrentHousehold().residents;
    $("#members").fadeOut(500);
    setTimeout(function(){
        document.getElementById("miniProfile-name").innerHTML = '<p id="miniProfile-name">' + members[index].name + '</p>';
        document.getElementById("miniProfile-email").innerHTML = '<p id="miniProfile-email">' + members[index].email + '</p>';
        document.getElementById("miniProfile-telephone").innerHTML = '<p id="miniProfile-telephone">' + members[index].telephone + '</p>';
        $("#miniProfile").removeClass("hide");

    }, 500);
}

function hideMiniProfile(){
    $("#members").fadeIn(500);
    $("#miniProfile").addClass("hide");
}

/*
 function buildAdminTable() {
 var admins = getCurrentHousehold().admins;

 for (var i = 0; i<admins.length; i++) {
 var user = admins[i];
 $("#admins").append(
 "<tr>\n" +
 "<td><div class=\"img-circle\"><img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\"></div></td>\n" +
 "<td>"+user.name+"</td>\n" +
 "<td>"+user.email+"</td>\n" +
 "<td>"+user.telephone+"</td>\n" +
 "</tr>");
 }
 }*/
