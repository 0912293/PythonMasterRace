/**
 * Created by jesse on 26-1-2017.
 */


$(document).ready(function () {
    console.log("Check1");
    updateData();
});

function updateData() {
    var stdURL = "/api/order_history.json";
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
        //tr.append("<input type='hidden' id='" + item.games_id + "' value='" + item.games_id + "'/>");
        tr.append("<td>" + item.order_id + "</td>");
        tr.append("<td>" + item.order_pd + "</td>");
        tr.append("<td>" + item.order_osc + "</td>");

        $('table').append(tr);
    });
}