function toggle() {
	var chartContainer = document.getElementById('chartContainer');
	var graph = document.getElementById('graph');
	var dataPoints = '${dataPoints}';
	if (chartContainer.style.display == 'block') {
		chartContainer.style.display = 'none';
		graph.value = "Show Graph";

	} else {
		chartContainer.style.display = 'block';
		graph.value = "Hide Graph";

		alert($("#dataPoints").val());
		var dataPoints1 = $("#dataPoints").val();
		alert('dataPoints1 =' + $("#dataPoints").val());	
		var dataPoints2 = [ {
			x : new Date(2012, 00, 15),
			y : 450
		} ];
		
		
		var dp = {
				theme : "theme2",
				title : {
					text : "Price Movement"
				},
				animationEnabled : true,
				axisX : {
					valueFormatString : "DD-MMM",
					interval : 3,
					intervalType : "day"

				},
				axisY : {
					includeZero : false

				},
				data : [ {
					type : "line",
					// lineThickness: 3,
					dataPoints : dataPoints1
				} ]
			};
		
		alert(dp);
		//var json = JSON.stringify(eval("(" + dp + ")"));
		
		showGraph(dp);
	}
}
function showGraph(json) {
	var chart = new CanvasJS.Chart("chartContainer",json);

	chart.render();
}
