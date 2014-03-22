<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored ="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mine Data</title>
<link rel="stylesheet" type="text/css" href="/static/style.css">
</head>
<body>
<form action="/mine-clusetred-data">
<div class="filterTable">
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <strong>Select Criterion for Mining : </strong>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Rain</span>
		  <span class="radioWrapper">
		  	Max <input type="radio" value="max" name="rain"> || 
		  	Min <input type="radio" value="min" name="rain">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Snow</span>
		  <span class="radioWrapper">
		  	Max <input type="radio" value="max" name="snow"> || 
		  	Min <input type="radio" value="min" name="snow">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Temperature</span>
		  <span class="radioWrapper">
		  	Max <input type="radio" value="max" name="temp"> || 
		  	Min <input type="radio" value="min" name="temp">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Humidity</span>
		  <span class="radioWrapper">
		  	Max <input type="radio" value="max" name="humidity"> || 
		  	Min <input type="radio" value="min" name="humidity">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Wind Speed</span>
		  <span class="radioWrapper">
		  	Max <input type="radio" value="max" name="windSpeed"> || 
		  	Min <input type="radio" value="min" name="windSpeed">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <span class="label">Wind Direction</span>
		  <span class="radioWrapper">
		  	East to West <input type="radio" value="e2w" name="windDir"> || 
		  	West to East <input type="radio" value="w2e" name="windDir">
		  </span>
		</div>
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		   <span class="label">Start Date</span><input type="date" name="startDate" value="2014-01-01">
		</div>		
	</div>
	<div class="filterTableRow">
		<div class="filterTableCell">
		   <span class="label">End Date</span><input type="date" name="endDate" value="2014-01-31">
		</div>		
	</div>	
	<div class="filterTableRow">
		<div class="filterTableCell">
		  <input type="submit" value="Submit">
		</div>
		<div class="filterTableCell">
		  
		</div>
	</div>
</div>
</form>


</body>
</html>