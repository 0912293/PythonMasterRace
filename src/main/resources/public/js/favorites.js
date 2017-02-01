$(document).ready(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/favorites.json";
    getJson(stdURL);// + "?id=" + searchAppend);
}

function getJson(url) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: "json",
        success: filltable,
        error: function () {
        }
    });
}



function filltable(data) {
    var json = data;
    var tr = [];
    $.each(json, function (i, item) {
        tr = $('<tr/>');
        if (item.og_id != -1) {
            tr.append("<input type='hidden' id='" + item.og_id + "' value='" + item.og_id + "'/>");
            tr.append("<td>" + item.games_name + "</td>");
            tr.append("<td>" + item.games_price + "</td>");
            tr.append("<td>-</td>");
            tr.append("<td>" + item.games_platform+ "</td>");
        } else{
            tr.append("<input type='hidden' id='" + item.opl_id + "' value='" + item.opl_id + "'/>");
            tr.append("<td>" + item.platform_name + "</td>");
            tr.append("<td>" + item.platform_price + "</td>");
            tr.append("<td>" + item.platform_colour+ "</td>");
            tr.append("<td>-</td>");
        }


        if (item.og_id != -1) {
            tr.append("<td><input type = 'checkbox'  value='" + item.games_id + "' id = 'chk'" + item.games_id + "'/></td>");
        }else{
            tr.append("<td><input type = 'checkbox'  value='" + item.platform_id + "' id = 'chk'" + item.platform_id + "'/></td>");
        }

        $('table').append(tr);
    })
}

function deleterows2() {
    var table = $('table');
    var dict = {};
    var i = 0;
    $('[id = chk]:checked').each(function () {
        $(this).closest('tr').remove();
        var key = i.toString();
        dict[key] = ($(this).val());
        i++
    });
    deleteSQL(dict)
}

function deleteSQL(dict) {
    var dict = dict;
    $.ajax({
        type: "POST",
        url: "/favorites/delete",
        data: dict,
        success: function (data) {
            SpawnNotification('Favorieten - Actie: ', data, '/img/deleteIcon.png', 5000);
        }
    });
}