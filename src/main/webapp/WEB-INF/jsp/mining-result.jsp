<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mining Result</title>
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
<div class="resultTable">
	<table style="border: 1px solid;">
	<tr>
	<th>
		Parameter	
	</th>
	<th>
		Mining From Clusters
	
	</th>
	<th>
	Mining From K-Medoid-Clustered Data
	</th>
	</tr>
	<tr>
	<td>Time Taken</td>
	<td>${clustredtakenTime} ms</td>
	<td>${kMedoidClustredtakenTime} ms</td>
	</tr>
	
	</table>
</div>


<div>
<strong>Mining From Clustered data  : </strong><br>
<strong>Time Taken : </strong>${clustredtakenTime}<br>
<textarea style="width: 800px; min-height: 500px;">${clusteredXmlResult}</textarea>


<strong>Mining From Non-Clustered data  : </strong><br>
<strong>Time Taken : </strong>${nonClustredtakenTime}<br>
<textarea style="width: 800px; min-height: 500px;">${nonClusteredData}</textarea>
</div>

</body>
</html>