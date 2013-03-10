function KontrolliSisend()
{
	var lisa = true
	var error1 = false
	var error2 = false
	if (document.getElementById("nimi").value.length<3) {
			document.getElementById("nimiviga").innerHTML="Nimi peab olema vÃ¤hemalt 3 tÃ¤hte pikk";
			lisa = false
			error1 = true
		} if ( document.getElementById("perenimi").value.length<3) {
			document.getElementById("perenimiviga").innerHTML="Nimi peab olema vÃ¤hemalt 3 tÃ¤hte pikk";
			lisa = false
			error2 = true
		} if (!error1){
			document.getElementById("nimiviga").innerHTML="";
		} if (!error2){
			document.getElementById("perenimiviga").innerHTML="";
		} if (checkdate(document.getElementById("Bday"))){
			document.getElementById("kuuviga").innerHTML="";	
		} if (checkdate(document.getElementById("Bday")) == true && lisa == true) {
		alert('Teie kanditatuur on edukalt lisatud');
	}
}


function checkdate(input){
var validformat=/^(\d{4})-(\d{1,2})-(\d{1,2})$/ //Basic check for format validity
var returnval=false
if (!validformat.test(input.value))
document.getElementById("kuuviga").innerHTML="Sünnipäev peab olema formaadis AAAA-KK-PP"
else{ //Detailed check for valid date ranges
var yearfield=input.value.split("-")[0]
var monthfield=input.value.split("-")[1]
var dayfield=input.value.split("-")[2]
var dayobj = new Date(yearfield, monthfield-1, dayfield)
if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
document.getElementById("kuuviga").innerHTML="Sisesta korrektne kuupäev"
else
returnval=true
}
if (returnval==false) input.select()
return returnval
}
