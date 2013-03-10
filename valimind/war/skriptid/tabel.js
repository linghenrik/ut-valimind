function drawTable() {
	$.getJSON('../json/candidates.json', function(data) {
	var tabel = new google.visualization.DataTable();       
	tabel.addColumn('string', 'Nimi');
	tabel.addColumn('string', 'Erakond');
	tabel.addColumn('string', 'ID');
        
	for (var i in data.candidates) {        		         
		tabel.addRow([data.candidates[i].person.name,  
		data.candidates[i].party.name, 
		data.candidates[i].id]);}
          		
		var erakonnaTabel = new google.visualization.Table(document.getElementById('table_div'));
		erakonnaTabel.draw(tabel, {showRowNumber: true});
	});
}

function valitabel() {
	var x=document.getElementById("tabelmenu").selectedIndex;
	if (document.getElementsByTagName("option")[x].value == "bar") {
		drawChart()
	} if (document.getElementsByTagName("option")[x].value == "pie") {
		drawPieChart()
	}
}

function drawChart() {
	var data = google.visualization.arrayToDataTable([
		['Erakond', 'Haaletajaid'],
		['Keskerakond',  1000],
		['Reformierakond',  1170],
		['SDE',  660]
		]);

	var options = {
		title: 'H‰‰letuste jaotus',
		vAxis: {title: 'Erakond',  titleTextStyle: {color: 'red'}}
	};

	var chart = new google.visualization.BarChart(document.getElementById('chart'));
	chart.draw(data, options);
}

function drawPieChart() {
        var data = google.visualization.arrayToDataTable([
          ['Erakond', 'Riikigogu kohti'],
          ['SDE',     5],
          ['Reformierakond',      2],
          ['Selver',  2],
          ['FC Flora', 2],
          ['UT valimind',    7]
        ]);

        var options = {
          title: 'Riigikogu kohti'
        };

        var chart = new google.visualization.PieChart(document.getElementById('chart'));
        chart.draw(data, options);
      }