function getForm(form) {
	var arr = new Array();
	var nrSearched = 0;

	var name = form.search_name.value;
	if (name != ""){arr.push("name:" + name); nrSearched += 1;}

	var party = form.search_party.value;
	if (party != "") {arr.push("party:" + party); nrSearched += 1;}

	var region = form.search_region.value;
	if (region != "") {arr.push("region:" + region); nrSearched += 1;}

	jQuery("#myTable").empty();
	jQuery("#kekeke").empty();
	var gotStuff = 0;

	//currently 1 check per file
	if (nrSearched == 1 && name != "") {
		gotStuff = 1;
		jQuery.getJSON("../json/candidate.json", function(result) {
			var table_obj = jQuery('#myTable');
			table_obj.append(jQuery('<tr><td><strong>Erakond</strong></td><td><strong>Piirkond</strong></td><td><strong>Kandidaat</strong></td></tr><tr>'));
			jQuery.each(result, function(index, item) {
				if (index != "id") {
					table_obj.append(jQuery('<td>' + item.name + '</td>'));
				}
			});
			table_obj.append(jQuery('</tr>'));
		});
	}

	if (nrSearched == 1 && party != "") {
		gotStuff = 1;
		jQuery.getJSON("../json/findCandidatesByParty.json", function(result){
			var table_obj = jQuery('#myTable');
			table_obj.append(jQuery('<tr><td><strong>Piirkond</strong></td><td><strong>Kandidaat</strong></td></tr>'));
			jQuery.each(result, function(index, item) {
				if (index != "id") {
					jQuery.each(item, function(key, val) {
						if (key != "id"){
							table_obj.append(jQuery('<tr><td>' + val.region.name + '</td><td>' + val.person.name + '</td></tr>'));
						}
					});
				}
			});
		});
	}

	if (nrSearched == 1 && region != "") {
		gotStuff = 1;
		jQuery.getJSON("../json/findCandidatesByRegion.json", function(result){
			var table_obj = jQuery('#myTable');
			table_obj.append(jQuery('<tr><td><strong>Erakond</strong></td><td><strong>Kandidaat</strong></td></tr>'));
			jQuery.each(result, function(index, item) {
				if (index != "id") {
					jQuery.each(item, function(key, val) {
						if (key != "id"){
							table_obj.append(jQuery('<tr><td>' + val.party.name + '</td><td>' + val.person.name + '</td></tr>'));
						}
					});
				}
			});
		});
	}

	if (nrSearched == 2 && region != "" && party != "") {
		gotStuff = 1;
		jQuery.getJSON("../json/findCandidatesByPartyAndRegion.json", function(result){
			var table_obj = jQuery('#myTable');
			table_obj.append(jQuery('<tr><td><strong>Kandidaat</strong></td></tr>'));
			jQuery.each(result, function(index, item) {
				if (index != "id") {
					jQuery.each(item, function(key, val) {
						if (key != "id"){
							table_obj.append(jQuery('<tr><td>' + val.person.name + '</td></tr>'));
						}
					});
				}
			});
		});
	}

	if (gotStuff == 0) {
		var myDiv = jQuery('#kekeke');
		myDiv.append(jQuery('<h3>P&auml;ringule vastused puuduvad!</h3>'));
	}
}