<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WSM</title>
<meta name="keywords" content="secured theme, free template, templatemo, red layout" />
<meta name="description" content="Secured Theme is provided by templatemo.com" />
<link href="/static/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="/static/style.css" />
<script type="text/javascript" src="/static/js/jquery.min.js" ></script>
<script type="text/javascript" src="/static/js/jquery-ui.min.js" ></script>
<script type="text/javascript" src="/static/js/jsapi.js" ></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#featured > ul").tabs({fx:{opacity: "toggle"}}).tabs("rotate", 5000, true);
	});
</script>

<link rel="stylesheet" type="text/css" href="/static/css/ddsmoothmenu.css" />

<script type="text/javascript" src="/static/js/ddsmoothmenu.js">

/***********************************************
* Smooth Navigational Menu- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "templatemo_menu", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

function clearText(field)
{
    if (field.defaultValue == field.value) field.value = '';
    else if (field.value == '') field.value = field.defaultValue;
}

</script>

<link rel="stylesheet" href="/static/css/slimbox2.css" type="text/css" media="screen" /> 
<script type="text/JavaScript" src="/static/js/slimbox2.js"></script> 
<style type="text/css">

th{
border-left: 1px solid;
border-bottom: 1px solid;
}
td{
border-left: 1px solid;
border-bottom: 1px solid;
}
tr{
border-bottom: 1px solid;
}
</style>

</head>
<body>

<div id="templatemo_wrapper">
	<div id="templatemo_header">
    	<div id="site_title"><a rel="nofollow" href="">WSM (XML Mining)</a></div>
        
    </div> <!-- END of header -->


         <div id="templatemo_menu" class="ddsmoothmenu">
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="">Analysis</a>
                    <ul>
                        <li><a href="/graphs">Graphical</a></li>   
                        <li><a href="/tables">Tabular</a></li>                                     
                  </ul>
                </li>
                <li><a href="">Upload</a>
                    <ul>
                        <li><a href="/upload-new">Upload New Xml</a></li>                                                                                             
                  </ul>
                </li>
                <li><a href="/mining" class="selected">Mining</a></li>   
                <li><a href="/settings">Settings</a></li>   
                <li><a href="/convert">Convert Dataset</a></li>          
            </ul>
            <br style="clear: left" />
        </div> <!-- end of templatemo_menu -->
        
    <div id="templatemo_main">
    	<div class="content_wrapper content_mb_30">
	<div class="resultTable">
		<table style="border: 1px solid;">
		<tr>
		<th>
			Parameter	
		</th>
		<th>
			Mining From DBSCAN Clusters
		
		</th>
		<th>
		Mining From K-Medoid Data
		</th>
		</tr>
		<tr>
		<td>Time Taken</td>
		<td>${clustredtakenTime} ms</td>
		<td>${kMedoidClustredtakenTime} ms</td>
		</tr>				
		</table>
</div>     
<br /><br />
<div>
<strong>Mining From Clustered data  : </strong><br/>
<strong>Time Taken : </strong>${clustredtakenTime}<br/>
<textarea style="width: 800px; min-height: 500px;">${clusteredXmlResult}</textarea>

<br /><br />
<strong>Mining From K Medoid Clustered data  : </strong><br/>
<strong>Time Taken : </strong>${kMedoidClustredtakenTime}<br/>
<textarea style="width: 800px; min-height: 500px;">${kMedoidClusteredData}</textarea>
</div>   	
        </div>
    	<div class="content_wrapper">
            <div id="chart_div" style="width: 900px; height: 700px;"></div>
            <br/>
            <br/>
            <div id="chart_div2" style="width: 900px; height: 700px;"></div>
        </div>
        <div class="clear"></div>
    </div>

	<div id="templatemo_footer">
    	
        <div class="clear"></div>
    </div> <!-- END of templatemo_footer -->
</div> <!-- END of templatemo_wrapper -->
<div id="templatemo_copyright_wrapper">
    <div id="templatemo_copyright">
        Developed By <a href="#"></a> 
    </div>
</div>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
      function drawChart() {
    	  
    	  var dataArray=[];
    		var headerArray=[];
    		headerArray.push("Time");
    		headerArray.push("DBScanRecall");
    		headerArray.push("KMedoidRecall");
    		dataArray.push(headerArray);	

    	  
    	  $.ajax({		
    			type : "GET",
    			url : "/recall-data",			
    			data : "type=TROPICAL",
    			dataType:"json",
    			success : function(data) {    				 
				
    				$.each(data,function(key,value){
    					var innerArray=[];    	
    					innerArray.push(value.date);    					
    					innerArray.push(parseInt(value.dbTime));
    					innerArray.push(parseInt(value.kTime));
    					dataArray.push(innerArray);
    				});
    				
    			//	alert(JSON.stringify(dataArray));
    				var data = google.visualization.arrayToDataTable(dataArray);

    		        var options = {
    		    			'width':800,'height':600,'vAxis': {'title': 'TimeTaken(ms)'},hAxis: {
    		    		        slantedText:true,
    		    		        slantedTextAngle:90,// here you can even use 180
    		    		        'title': 'Date'
    		    		    },title: "DBSCAN with KL vs K-Medoid with KL"
    		    	};
    		        
    		        

    		        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    		        chart.draw(data, options);
    			},
    			error : function(e) {
    				alert('Error while Ajax');
    			}		
    		});
    	  
    	  drawChart2()
      }
      
function drawChart2() {
    	  
    	  var dataArray=[];
    		var headerArray=[];
    		headerArray.push("Time");
    		headerArray.push("DBScanWithBoosting");
    		headerArray.push("DBScanWithoutBoosting");
    		dataArray.push(headerArray);	

    	  
    	  $.ajax({		
    			type : "GET",
    			url : "/cluster-time-data",			
    			data : "type=TROPICAL",
    			dataType:"json",
    			success : function(data) {    				 
				
    				$.each(data,function(key,value){
    					var innerArray=[];    	
    					innerArray.push(value.date);    					
    					innerArray.push(parseInt(value.withBoosting));
    					innerArray.push(parseInt(value.withoutBoosting));
    					dataArray.push(innerArray);
    				});
    				
    			//	alert(JSON.stringify(dataArray));
    				var data = google.visualization.arrayToDataTable(dataArray);

    		        var options = {
    		    			'width':800,'height':600,'vAxis': {'title': 'TimeTaken(ms)'},hAxis: {
    		    		        slantedText:true,
    		    		        slantedTextAngle:90,// here you can even use 180
    		    		        'title': 'Date'
    		    		    },title: "DBSCAN with boosting vs without boosting"
    		    	};
    		        
    		        

    		        var chart = new google.visualization.LineChart(document.getElementById('chart_div2'));
    		        chart.draw(data, options);
    			},
    			error : function(e) {
    				alert('Error while Ajax');
    			}		
    		});
      }      

</script>
</body>

</html>