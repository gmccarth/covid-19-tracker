var dropdown = $('#country-dropdown');
dropdown.empty();
dropdown.append('<option selected="true" disabled>Choose country</option>');
dropdown.prop('selectedIndex',0);
var countriesUrl='reports/countries';

$.getJSON(countriesUrl,function (data) {
	$.each(data, function(key, entry){
		console.log(entry.name);
		dropdown.append($('<option></option>').attr('value', entry.name.toLowerCase()).text(entry.name));	
		})
});

$('#country-dropdown').change(function(){
    sendRequest("/reports/combined/"+$(this).val(),"GET");
    //+$(this).val(),"GET");
});

function sendRequest(url, method) {
  $.ajax({
    url: url,
    async: false,
    
    success: function(response) {
      console.log("Successful >> url:" + url);
//      let results = response.split(", ").map(Number);
      console.log(response);
      var jsonObj = JSON.parse(response);
      console.log("confirmed=" +JSON.stringify(jsonObj.confirmed));
      loadChart(jsonObj.confirmed, jsonObj.deaths, document.getElementById("country-dropdown").value);
    },
//    error: function(response) {
//      console.log("Error");

//    },
    error: function(xhr, textStatus, errorThrown){
    	console.log("error");
    	console.log(xhr.responseText);
    },
    type: method,
    headers: {
 //     Accept: 'application/json;charset=utf-8',
      'Content-Type': 'text/plain'
    },
    dataType: 'text'
  });
 }




//------------------------------
// Chart to display predictions
//------------------------------
var chart = "";

function loadChart(confirmed, deaths, country) {
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: "COVID-19 confirmed cases over time for " + country.toUpperCase()              
		},
		axisY: {
			title: "Confirmed Cases",
			lineColor: "#4F81BC",
			tickColor: "#4F81BC",
			labelFontColor: "#4F81BC"
		},
		axisY2: {
			title: "Deaths",
			lineColor: "#C0504E",
			tickColor: "#C0504E",
			labelFontColor: "#C0504E"
		},
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
			name: "Confirmed Cases",
			showInLegend: true,
			dataPoints: confirmed
		},
		{
			type: "line",
			name: "Deaths",
			axisYType: "secondary",
			showInLegend: true,
			dataPoints: deaths
		}
		]
	});
	chart.render();
}

//----------------------------
// display chart with updated
// drawing from canvas
//----------------------------



