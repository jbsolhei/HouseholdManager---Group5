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
                    "<tr>\n" +
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
                "<tr>\n" +
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
