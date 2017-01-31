function addgame() {
    console.log("Game added")
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
    post('/admin/product/add/game', dict, function (data) {
        if(data !== undefined){
            SpawnNotification('Admin - Product: ', data, '/img/infoIcon.png', 5000);
        }
        reload();
    });
    return false;
}

function addplatform() {
    console.log("Platform added")
    var dict = {
        'url' : location.pathname,
        'pname' : $('#p_name').val(),
        'pcolor' : $('#p_color').val(),
        'pmemory' : $('#p_memory').val(),
        'pmanufact' : $('#p_manufacturer').val(),
        'pprice' : $('#p_price').val(),
        'pstock' : $('#p_stock').val()
    };
    post('/admin/product/add/platform', dict, function (data) {
        if(data !== undefined){
            SpawnNotification('Admin - platform: ', data, '/img/infoIcon.png', 5000);
        }
        reload();
    });
    return false;
}