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
    var dict = {
        'search': (searchAppend !== undefined) ? searchAppend : null,
        'order': sortOption,
        'filter': filterOption
    };
    retrieveJSON(stdURL, dict, filltable);
}

function filltable(json) {
    var productContainer = $('#ProductContainer');

    productContainer.empty();
    $.each(json, function (i, item) {
        productContainer.append(ConstructProductbox(item.name, item.price, item.image, item.gameId));
        $('#productListCartButton' + item.gameId).click(getCartActionFunc(item.gameId, 0))
    })
}

function ConstructProductbox(name, price, image, id) {
    var content;
    content = $("<div class='col-md-3 column productbox' id='productbox'>");
    content.append("<div><img id='GamesPageImg' src='" + image + "'  class='img-responsive'>");
    content.append("<div class='caption'>");
    content.append("<h5 id='game_name'>" + name + "</h5>");
    content.append("<p>Prijs: &euro;" + price + "</p>");
    content.append("<p><a href='/games/bekijken?id="+ id +"' class='btn btn-success' role='button' id='button1'>Bekijken</a>");
    content.append("<a class='btn btn-primary' role='button' id='productListCartButton"+ id +"'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span> toevoegen aan<br>winkelwagen</a></p>");
    content.append("</div></div></div>");
    return content;
}