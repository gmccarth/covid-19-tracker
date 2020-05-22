var dropdown = $('#state-dropdown');
var countyDropdown = $('#county-dropdown');
dropdown.empty();
dropdown.append('<option selected="true" disabled>Choose US State</option>');
dropdown.prop('selectedIndex',0);
clearCountyDropdown();
var statesUrl='reports/states';
var countiesUrl='reports/counties/'

$.getJSON(statesUrl,function (data) {
	$.each(data, function(key, entry){
//		console.log(entry.name);
		dropdown.append($('<option></option>').attr('value', entry.name.toLowerCase()).text(entry.name));	
		})
});


$('#state-dropdown').change(function(){
	clearCountyDropdown();
	loadCounties();
    sendRequest("/reports/states/combined/"+$(this).val(),"GET", $(this).val());
    //+$(this).val(),"GET");
});
$('#county-dropdown').change(function(){
    sendRequest("/reports/states/combined/"+$('#state-dropdown').val()+"/"+$(this).val(),"GET", $(this).val()+", "+$('#state-dropdown').val());
    //+$(this).val(),"GET");
});

function sendRequest(url, method, reportType) {
  $.ajax({
    url: url,
    async: false,
    
    success: function(response) {
      console.log("Successful >> url:" + url);
//      let results = response.split(", ").map(Number);
//      console.log(response);
      var jsonObj = JSON.parse(response);
//      console.log("confirmed=" +JSON.stringify(jsonObj.confirmed));
      loadChart(jsonObj.confirmed, jsonObj.deaths, reportType);
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

function loadCounties(){
	console.log("in loadCounties");
	console.log("state: " + $('#state-dropdown').val());
	$.getJSON(countiesUrl +$('#state-dropdown').val(),function (data) {
		$.each(data, function(key, entry){
			console.log(entry.name);
			countyDropdown.append($('<option></option>').attr('value', entry.name.toLowerCase()).text(entry.name));	
			})
	})
}
function clearCountyDropdown() {
	countyDropdown.empty();
	countyDropdown.append('<option selected="true" disabled>Choose county</option>');
	countyDropdown.prop('selectedIndex',0);

}

//------------------------------
// Chart to display predictions
//------------------------------
var chart = "";

function loadChart(confirmed, deaths, state) {
	var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: "COVID-19 confirmed cases over time for " + state.toUpperCase()              
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



/**
 * 
 */