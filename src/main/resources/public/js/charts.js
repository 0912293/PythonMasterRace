google.charts.load('current', {packages: ['corechart']});

var options = {
    'legend':'left',
    'title':'Stock per platform',
    'is3D':true,
    'width':400,
    'height':300
};

var options2 = {
    'legend':'left',
    'title':'Games per platform',
    'is3D':false,
    'width':400,
    'height':300
};

var options3 = {
    'legend':'left',
    'title':'Amount of users per type',
    'is3D':false,
    'width':400,
    'height':300
};

function drawChart(json) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name');
    data.addColumn('number', 'Orders');
    $.each(json, function (i, item) {
        data.addRows([
            [item.games_platform, parseInt(item.stock)]
        ]);
    });
    var chart = new google.visualization.ColumnChart(document.getElementById('testChart'));
    chart.draw(data, options);
}

function drawChart2(jsn) {
    var data2 = new google.visualization.DataTable();
    data2.addColumn('string', 'Name');
    data2.addColumn('number', 'Amount');
    $.each(jsn, function (i, item) {
        data2.addRows([
            [item.games_platform, parseInt(item.game_count)]
        ]);
    });
    var chart2 = new google.visualization.PieChart(document.getElementById('testChart2'));     //google.visualization. CHART TYPE()
    chart2.draw(data2, options2);
}

function drawChart3(jsn) {
    var data3 = new google.visualization.DataTable();
    var usertype = '';
    data3.addColumn('string', 'Name');
    data3.addColumn('number', 'Amount');
    $.each(jsn, function (i, item) {
        if(parseInt(item.admin) == 1){
            usertype = 'admins'
        }
        else{
            usertype = 'users'
        }
        data3.addRows([
            [usertype, parseInt(item.ucount)]
        ]);
    });
    var chart3 = new google.visualization.PieChart(document.getElementById('testChart3'));     //google.visualization. CHART TYPE()
    chart3.draw(data3, options3);
}

google.charts.setOnLoadCallback(retrieveJSON("/api/admin/chart1.json", {}, drawChart));
google.charts.setOnLoadCallback(retrieveJSON("/api/admin/chart2.json", {}, drawChart2));
google.charts.setOnLoadCallback(retrieveJSON("/api/admin/chart3.json", {}, drawChart3));