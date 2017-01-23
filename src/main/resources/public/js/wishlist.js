$(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/wishlist.json";
    retrieveJSON(stdURL, {}, filltable);// + "?id=" + searchAppend);
}

function deleteRows() {
    var table = $('table');
    var dict = {};
    var i = 0;
    $('[id = chk]:checked').each(function () {
        console.log($(this).val());
        $(this).closest('tr').remove();
        //$('[id = deleteList]').append($(this).val());
        alert("You checked this box");
        var key = i.toString();
        console.log("The key = " + key);
        console.log("The value = " + $(this).val());
        dict[key] = ($(this).val());
        i++
    });
    post("/wishlist/delete", dict, function (data) {
        if (data !== undefined)
            alert(data);
    });
}

function filltable(data) {
    var json = data;
    var tr = [];
    $.each(json, function (i, item) {
        tr = $('<tr/>');
        tr.append("<input type='hidden' id='" + item.games_id + "' value='" + item.games_id + "'/>");
        tr.append("<td>" + item.games_name + "</td>");
        tr.append("<td>" + item.games_price + "</td>");
        tr.append("<td>" + item.games_platform+ "</td>");
        tr.append("<td><input type = 'checkbox'  value='"+ item.games_id + "' id = 'chk'" + item.games_id + "'/></td>");
        $('table').append(tr);
    })
}
