$(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/product/view.json";
    var searchAppend = getCurretUrlParam("id");
    console.log(searchAppend);

    var searchDict = {
        'id': (searchAppend != null || searchDict !== undefined) ? searchAppend : null
    };
    retrieveJSON(stdURL, searchDict, filldata);
}

function getCurretUrlParam(param) {
    var results = new RegExp('[\?&]' + param + '=([^&#]*)').exec(window.location.href);
    if (results==null){
        return null;
    }
    else{
        return results[1] || 0;
    }
    return null;
}

function filldata(json) {
    $.each(json, function (i, item) {
        $("#ProductName").html(item.games_name);
        $("#ProductImage").attr('src', item.games_image);
        //$('#ProductOmschrijving').html(...); Had to be implemented yet.
        //$('#BuyButton').attr('href', ...); Had to be implemented yet
    })
}
