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
					Region	
				</th>
				<th>
					Number Of Records				
				</th>				
				</tr>
				<tr>
				<td>TROPICAL</td>
				<td>${TROPICAL} </td>
				</tr>
				<tr>
				<td>DRY</td>
				<td>${DRY}</td>
				</tr>
				<tr>
				<td>TEMPERATE</td>
				<td>${TEMPERATE}</td>
				</tr>
				<tr>
				<td>CONTINENTAL</td>
				<td>${CONTINENTAL}</td>
				</tr>	
				<tr>
				<td>POLAR</td>
				<td>${POLAR}</td>
				</tr>	
				</table>
			</div>  
        </div>
        
    	<div class="content_wrapper">
            
        </div>
        <div class="clear"></div>
    </div>
</body>
</html>