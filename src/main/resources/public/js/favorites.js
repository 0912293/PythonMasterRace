$(document).ready(function () {
    console.log("Check1")
    updateData();
});

function updateData() {
    var stdURL = "/api/favorites.json";
    getJson(stdURL);// + "?id=" + searchAppend);
}

function getJson(url) {
    //console.log(url);
    $.ajax({
        type: 'POST',
        url: url,
        dataType: "json",
        success: filltable,
        error: function () {
            console.log("Incorrect or missing JSON")
        }
    });
}



function filltable(data) {
    var json = data;
    var tr = [];
    $.each(json, function (i, item) {
        tr = $('<tr/>');
        tr.append("<input type='hidden' id='" + item.og_id + "' value='" + item.og_id + "'/>");
        tr.append("<td>" + item.og_info_games_name + "</td>");
        tr.append("<td>" + item.og_info_games_price + "</td>");
        tr.append("<td>" + item.og_info_games_platform+ "</td>");

        $('table').append(tr);
    })
}