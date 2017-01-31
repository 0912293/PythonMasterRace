google.charts.load('current', {packages: ['corechart'], callback: DrawCharts});

var options = {
    'legend':'left',
    'title':'Orders per dag',
    'is3D':true,
    'width':600,
    'height':300
};

var options2 = {
    'legend':'left',
    'title':'Top 10 games toegevoegd aan wenslijst',
    'is3D':false,
    'width':600,
    'height':300
};

var options3 = {
    'legend':'left',
    'title':'Omzet per dag',
    'is3D':false,
    'width':600,
    'height':300
};

function DrawCharts() {
    retrieveJSON("/api/admin/chart1.json", {}, drawChart);
    retrieveJSON("/api/admin/chart2.json", {}, drawChart2);
    retrieveJSON("/api/admin/chart3.json", {}, drawChart3);
}

function drawChart(json) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'order_pd');
    data.addColumn('number', 'ord');
    $.each(json, function (i, item) {
        data.addRows([
            [item.order_pd, parseInt(item.ord)]
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
            [item.games_name, parseInt(item.added)]
        ]);
    });
    var chart2 = new google.visualization.BarChart(document.getElementById('testChart2'));     //google.visualization. CHART TYPE()
    chart2.draw(data2, options2);
}

function drawChart3(jsn) {
    var data3 = new google.visualization.DataTable();
    var usertype = '';
    data3.addColumn('string', 'Name');
    data3.addColumn('number', 'Amount');
    $.each(jsn, function (i, item) {
        data3.addRows([
            [item.order_pd, parseInt(item.total)]
        ]);
    });
    var chart3 = new google.visualization.ColumnChart(document.getElementById('testChart3'));     //google.visualization. CHART TYPE()
    chart3.draw(data3, options3);
}