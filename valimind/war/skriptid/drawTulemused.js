function drawTulemused() {
	
	while(var i<10000){
		i=i+1;
	}
	$.getJSON('//StatisticServlet', function(data) {
	var tabel = new google.visualization.DataTable();       
	tabel.addColumn('string', 'ID');
	tabel.addColumn('string', 'eesnimi');
	tabel.addColumn('string', 'perenimi');
	tabel.addColumn('string', 'Erakond');
	tabel.addColumn('string', 'Regioon');
	tabel.addColumn('string', 'Valimised');
	
        
	for (var i in data.length-1;i>=0;i--) {        		         
		tabel.addRow([data[i].KandidaatId,
		data[i].nimi,  
		data[i].perenimi, 
		data[i].partei,
		data[i].regioon,
		data[i].valimised]);}
          		
		var tulemusedTabel = new google.visualization.Table(document.getElementById('table_div'));
		tulemusedTabel.draw(tabel, {showRowNumber: false});
	});
}