$(document).ready(function() {
    // Log in
    $("#analyzeForm").submit(function(event) {
        event.preventDefault();
        $.ajax({
            url: 'webresources/sentiment',
            type: 'POST',
            data: $("#inputText").val(),
            contentType: 'text/plain; charset=utf-8',
            dataType: 'json',
            complete: function(xhr) {
                switch (xhr.status) {
                    case 200:
                        $("#emoticon").attr("src", "images/sentiment" + xhr.responseText + ".png");
                        $("#emoticon").removeClass("hide");
                        break;
                    default:
                        $("#emoticon").addClass("hide");
                        $("#errorAlert").removeClass("hide");
                        break;
                }
            }
        });
    });
});