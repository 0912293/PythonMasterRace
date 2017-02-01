$(function () {
    updateData();
});

function updateData() {
    var stdURL = "/api/product/info";
    retrieveJSON(stdURL, {}, fillform);
}

function fillform(data) {
    var json = data;
    var content = [];
    var itemContent = $('#ItemContent');
    itemContent.empty();

    $.each(json, function (i, item) {
        content = $("<div id='ItemContent' class='col-md-8'></div>");
        content.append("<h4 id='ProductName'>" + item.games_name + "</h4><img style='max-width:50px;max-height:50px;' id='ProductImage' src='" + item.games_image  + "'>");
        content.append("<form><div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Naam</label>"+
            "<input id='g_name' type='text' required='required' value='"+item.games_name+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Image</label>"+
            "<input id='g_image' type='text' required='required' value='"+item.games_image+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Price</label>"+
            "<input id='g_price' type='text' required='required' value='"+item.games_price+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Genre</label>"+
            "<input id='g_genre' type='text' required='required' value='"+item.games_genre+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Platform</label>"+
            "<input id='g_platform' type='text' required='required' value='"+item.games_platform+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Publisher</label>"+
            "<input id='g_publisher' type='text' required='required' value='"+item.games_publisher+"'>"+
            "</div>");
        content.append("<div class='form-group row'>"+
            "<label class='col-xs-3 col-form-label'>Stock</label>"+
            "<input id='g_stock' type='text' required='required' value='"+item.games_stock+"'>"+
            "</div></form>");
        content.append("<button class='btn btn-success' onclick='updateGame()'>Submit</button>")
        itemContent.append(content);
    })
}

function updateGame() {
    var dict = {
        'url' : location.pathname,
        'gname' : $('#g_name').val(),
        'gprice' : $('#g_price').val(),
        'ggenre' : $('#g_genre').val(),
        'gplatform' : $('#g_platform').val(),
        'gpublisher' : $('#g_publisher').val(),
        'gstock' : $('#g_stock').val(),
        'gimage' : $('#g_image').val()
    };
    post('/admin/product/edit/game', dict, function (data) {
        if(data !== undefined){
            SpawnNotification('Admin - game: ', data, '/img/infoIcon.png', 5000);
        }
        reload();
    });
    return false;
}