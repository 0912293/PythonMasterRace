$(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/order_history.json";
    retrieveJSON(stdURL, {}, filltable);// + "?id=" + searchAppend);
}

function filltable(data) {
    var json = data;
    var tr = [];
    var trMeta = [];
    var rownr = 0;
    $.each(json, function (i, order) {
        var info = order.info;

        tr = $('<tr class="rowHead"/>');
        tr.append("<td><span id='exp_col_icon"+rownr+"' class='glyphicon glyphicon-plus'></button>");
        tr.append("<td> <a href=/invoice?uid="+info.order_id+">" + info.order_id + "</a></td>");
        tr.append("<td>" + info.order_pd + "</td>");
        tr.append("<td>" + info.orderstatus_descr + "</td>");
        $('table').append(tr);

        $('#exp_col_icon'+rownr++).click(function (){
            var row = this.closest('tr.rowHead');
            toggleRowMeta(row)
        });


        $.each(order.products, function (j, product) {
            trMeta = $('<tr class="metaData" bgcolor="#e9e9e9" style="display: none"/>');
            trMeta.append('<td/>');
            trMeta.append('<td colspan="2">'+product.product_name+'</td>');
            trMeta.append('<td>&euro;'+product.product_price+'</td>');
            $('table').append(trMeta);
        });
    });
}

function toggleRowMeta(row){
    var rowHeads = $('.rowHead');
    if (row !== undefined) {
        $(row).find('span').attr('class', function (_, value) {
            return value == 'glyphicon glyphicon-minus' ? 'glyphicon glyphicon-plus' : 'glyphicon glyphicon-minus'
        });
        $(row).nextUntil('tr.rowHead').slideToggle(100, function () {
        });
    } else {
        rowHeads.find('span').attr('class', function (_, value) {
            return value == 'glyphicon glyphicon-minus' ? 'glyphicon glyphicon-plus' : 'glyphicon glyphicon-minus'
        });
        rowHeads.nextUntil('tr.rowHead').slideToggle(100, function () {
        });
    }
}