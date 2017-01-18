function filltable(data) {
    var json = data;
    var tr = [];
    $.each(json, function (i, item) {
        tr = $('<tr/>');
        tr.append("<td>" + item.username + "</td>");
        tr.append("<td>" + item.name + "</td>");
        tr.append("<td>" + item.surname + "</td>");
        tr.append("<td>" + item.email + "</td>");
        tr.append("<td>" + item.birth_date + "</td>");
        tr.append("<td>" + item.admin + "</td>");
        $('table').append(tr);
    })
}

$(function () {
    retrieveJSON("/api/admin/users.json", {}, filltable)
});