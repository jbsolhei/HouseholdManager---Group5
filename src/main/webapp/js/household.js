function loadHousehold(){
    $("#hh_name").html(getCurrentHousehold().name);
    $("#hh_address").html(getCurrentHousehold().address);
    buildAdminTable();
    buildMemberTable();
}

function buildMemberTable(){
    var members = getCurrentHousehold().residents;

    for (var i = 0; i<members.length; i++) {
        var user = members[i];
        $("#members").append(
            "<tr>\n" +
            "<td><div class=\"img-circle\"><img class=\"img-responsive img-pic\" src=\"http://www.hf.uio.no/imv/personer/vit/midlertidig/mervea/akca_photo-copy.jpg\"></div></td>\n" +
            "<td>"+user.name+"</td>\n" +
            "<td>"+user.email+"</td>\n" +
            "<td>"+user.telephone+"</td>\n" +
            "</tr>");
    }
}

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
}