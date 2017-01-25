var sortOption = null;
var filterOption = null;
var input = document.querySelector('input[type= text][id = "search"]');

$(function () {
    $('.form-filter').on("submit", function(e) {
        e.preventDefault();
        updateTable($('#search').val())
    });
    $('#sortoption').change(function(){ checkSort() });
    $('#filteroption').change(function () { checkFilter() });
    updateDropdowns();
    checkSort();
    updateTable();
});

function checkSort() {
    var optObj = $('#sortoption').find(":selected");
    var opt = optObj.val();
    if (opt == "None") {
        sortOption = null;
    } else if (opt == "P_ASC") {
        sortOption = "games.games_price";
    } else if (opt == "P_DESC") {
        sortOption = "games.games_price DESC";
    } else if (opt == "N_ASC") {
        sortOption = "games.games_name";
    } else if (opt == "N_DESC") {
        sortOption = "games.games_name DESC";
    }
    updateTable()
}

function checkFilter() {
    var optObj = $('#filteroption').find(":selected");
    var opt = optObj.val();
    if (opt == "None") {
        filterOption = null;
    } else if (optObj.parent().attr('label') == "Genre") {
        filterOption = "games.games_genre=" + "'" + opt + "'";
    } else if (optObj.parent().attr('label') == "Platform") {
        filterOption = "games.games_platform=" + "'" + opt + "'";
    }
    updateTable();
}

function updateDropdowns() {
    checkSort();
    var dict = { 'selector': 0};
    retrieveJSON("/api/product/filtering.json", dict, fillDropdownsOuter(0));
    dict = { 'selector': 1};
    retrieveJSON("/api/product/filtering.json", dict, fillDropdownsOuter(1));
}

function fillDropdownsOuter(selector) {
    function fillDropdowns(json) {
        var dropdown = $('#filteroption');
        var group = null;
        if (selector == 0) {
            group = dropdown.find("optgroup[label='Genre']")
        } else if (selector == 1) {
            group = dropdown.find("optgroup[label='Platform']")
        }
        group.empty();
        console.log(json);
        $.each(json, function (i, item) {
            if (selector == 0) {
                group.append("<option>" + item.games_genre + "</option>")
            } else if (selector == 1) {
                group.append("<option>" + item.games_platform + "</option>")
            }
        })
    }
    return fillDropdowns
}

function updateTable(searchAppend) {
    var stdURL = "/api/product/games.json";
    var searchDict = {
        'search': (searchAppend !== undefined) ? searchAppend : null,
        'order': sortOption,
        'filter': filterOption
    };
    retrieveJSON(stdURL, searchDict, filltable);
}

function filltable(json) {
    var productContainer = $('#ProductContainer');

    productContainer.empty();
    $.each(json, function (i, item) {
        productContainer.append(ConstructProductbox(item.games_name, item.games_price, item.games_image, item.games_id));
        $('#productListCartButton' + item.games_id).click(getCartActionFunc(item.games_id, item.games_name, item.games_image, 0))
    })
}

function ConstructProductbox(name, price, image, id) {
    var content;
        content = $("<div class='col-md-3 column productbox col-md-offset-0 col-xs-offset-3' id='productbox'>");
        content.append("<div><a href='/games/bekijken?id="+ id +"'><img id='GamesPageImg' src='" + image + "'  class='img-responsive'>");
        content.append("<div class='caption'>");
        content.append("<h5 id='game_name'>" + name + "</h5>");
        content.append("<p>Prijs: &euro;" + price + "</p></a>");
        content.append("<div class='row'><div class='col-xs-7'><a href='/games/bekijken?id="+ id +"' class='btn btn-success btn-block' role='button' id='button1'>Bekijken</a></div></div> " +
                "<div class='row'><div class='col-xs-7'><a href='#' class='btn btn-primary btn-block' role='button' id='productListCartButton" + id + "'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span> Toevoegen</a>" +
                "</div></div>");
        content.append("</div></div></div>");
    return content;
}