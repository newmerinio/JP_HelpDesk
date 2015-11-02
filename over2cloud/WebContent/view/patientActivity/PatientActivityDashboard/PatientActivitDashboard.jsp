<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">

     
    <script src="amcharts/amcharts.js"></script>
	<script src="amcharts/serial.js"></script>
	<script src="amcharts/themes/light.js"></script>
	<script src="amcharts/amstock.js"></script>
	<script src="amcharts/pie.js"></script>
    <script  src="amcharts/plugins/export/export.js"></script>
  
<style type="text/css">
.ui-datepicker-month,.ui-datepicker-year{
color: rgb(22, 66, 118);
}
#dashDataPart ul.nav_links li div.dropdown ul li:HOVER {
  display: block;
  float: none;
  position: relative;
  font-size: 12px;
  text-shadow: none;
  background-color: #F0F0F0;
}

#dashDataPart ul.nav_links li:hover div.dropdown ul li {
  display: block;
  float: none;
  position: relative;
  font-size: 12px;
  text-shadow: none;
  text-align: center;
  border-bottom: 1px solid #f2f2f2;
}

	
#dashDataPart ul.nav_links li div.profile_dropdown{
width: 150px;
top: 24px;
}
#dashDataPart .wrap_nav{

float: right;
  height: auto;
   width: 6%; 
  padding-top: 0px;
  /* margin-right: 1%; */
}



.colorme{
color:black;
}
.title{
		color:#FFFFFF; 
		background:#F04E2F url("images/ui-bg_highlight-soft_15_F04E2F_1x100.png") 50% 50% repeat-x; 
		font-size:11px; 
		font-family:Verdana, Arial, Helvetica, sans-serif;
		}
			.titleData
		{
		color: #EAEAEA ;
		background:#ffffff url("images/ui-bg_flat_75_ffffff_40x100.png") 50% 50% repeat-x; 
		font-size:11px; 
		font-family:Verdana, Arial, Helvetica, sans-serif;
		}
		
		
		#dashboard1 td:HOVER {
	background:#EAEAEA;
}

.headdingtest{
font-weight: bold;
line-height: 30px;
margin-left: 30px; 
}
.datePart{
margin: 0px 5px 3px 293px;
}
.threeLiner{
 
   width: 29px;
  height: 21px;
  padding: 0;
  background-repeat: no-repeat;
  background-image: url(images/threeline.png;);
  background-color: #fff;
  background-position: center;
  -webkit-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  -moz-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
  margin: 0px -5px 0 0px;
  display: block;
  background-size: 21px;
}
.threeLiner:HOVER{
 
   width: 29px;
  height: 21px;
  padding: 0;
  background-repeat: no-repeat;
  background-image: url(images/threeline.png;);
  background-color: #e1e1e1;
  background-position: center;
  -webkit-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  -moz-box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  box-shadow: 1px 1px 3px 0px rgba(0,0,0,0.5);
  border-radius: 2px;
  margin: 0px -5px 0 0px;
  display: block;
  background-size: 21px;
}
</style>


<title>WFPD Dashboard</title>
<style type="text/css">
.setupHeading
{

    font-size: 37px;
    font-family: "Open Sans light", sans-serif;
    color: #dedede;
    text-shadow: 0 1px 0 #fff;
    text-align: right;
    padding-right: 20px;
    margin-top: 20px;
}
.mB10 {
    margin-bottom: 10px !important;
}
.mT25 {
    margin-top: 25px;
}
.f13 {
    font-size: 13px!important;
}
.selec, .selec a {
    color: #666 !important;
    background-color: #f7f6f4;
    margin: 0;
}

.setupleftul li {
    padding-left: 0;
    margin: 0;
    line-height: normal;
    background-image: none;
    text-align: right;
    font-size: 13px;
}

.setupleftul a {
    color: #666;
    text-decoration: none;
    display: block;
    padding: 10px 20px;
    text-shadow: 0 1px 0 #fff;
    white-space: nowrap;
}
.rwobwop, .leadbox {
    border-width: 1px\9;
    border-style: solid\9;
    border-color: #e6e6e6 #cdcdcd #aaa #cdcdcd\9;
}
.tabClass {
    background: #f9f9f9 url(images/setupHeaderBg.jpg) repeat-x left bottom;
    -moz-border-radius-topleft: 5px;
    -webkit-border-top-left-radius: 5px;
    -moz-border-radius-topright: 5px;
    -webkit-border-top-right-radius: 5px;
   
   }
   .setupSelectedHeading {
    color: #aaa;
    font-size: 20px;
    font-family: "Open Sans light", sans-serif;
    font-weight: normal;
    padding: 0px 0px 5px 14px;
    text-shadow: 0 1px 0 #fff;

}
.clearB {
    clear: both;
}
p {
    line-height: 18px;
}
.commentabmenu {
    margin: 0;
    padding: 0;
    display: block;
}
    

.commentabmenu td {
   margin: 0;
    font-size: 13px;
    background: url(images/setupSep.gif) no-repeat right bottom;
    padding: 5px 15px 6px;
    cursor: pointer;
    float: left;
}
.pL25 {
    padding-left: 25px!important;
}
.fbtxt {
    color: #808080;
}
.cart {
	background: white;
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
	overflow: auto;
}
.bullet{
background: rgba(89, 22, 197, 0.26);
    padding: 8px 10px 2px 11px;
    margin: 0px 13px 4px 10px;
    color: #fff;
    font-weight: bold;
}
.bullet1{
background: rgba(22, 197, 176, 0.57);
    padding: 8px 10px 2px 11px;
    margin: 0px 13px 4px 10px;
    color: #fff;
    font-weight: bold;
}
.slider{
background: url(images/right.png) no-repeat ;
}
</style>
<SCRIPT type="text/javascript">
function getblock(blockId,blockName){
	highlight(blockId,blockName);
	if(blockId=='1')
		{
		$("#todayClk").attr('onclick','getBlockDatapart('+blockId+',"Today");');
		$("#weekClk").attr('onclick','getBlockDatapart('+blockId+',"Weekly");');
		$("#monthClk").attr('onclick','getBlockDatapart('+blockId+',"Monthly");');
		getBlockDatapart(blockId,'Today');
		}
	else if(blockId=='2')
		{
		$("#todayClk").attr('onclick','getBlockLead('+blockId+',"Today");');
		$("#weekClk").attr('onclick','getBlockLead('+blockId+',"Weekly");');
		$("#monthClk").attr('onclick','getBlockLead('+blockId+',"Monthly");');
		
		getBlockLead(blockId,'Monthly');
		}
	
}  
getblock('2','');
//getblock('1','Patient Activity');
function highlight(blockId,blockName)
{
	$("#conful li").attr('class','');
	$("#"+blockId).attr('class','selec');
	$(".setupSelectedHeading").text(blockName);
}
function getBlockDatapart(blockId,duration){
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getDashboardBlockData.action?duration="+duration,
	    success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata).show('slow');
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getBlockLead(blockId,duration){
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/PatientActivityDashboard/getDashboardLead.action?duration="+duration,
	    success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata).show('slow');
	},
	   error: function() {
            alert("error");
        }
	 });
}
hideshow('configBlock');
function hideshow(val){
	$("#"+val).hide('slow');
	
}
function showhide(val){
	$("#"+val).show('slow');
}
</SCRIPT>
</head>
<body class="dashbody">

<div class="middle-content">

<table cellpadding="0" cellspacing="0" width="99.5%" >
	<tbody>
    	<tr>
   			<td width="225" valign="top" id="configBlock">
		   			<div class="setupHeading"style="cursor:pointer;" onclick="hideshow('configBlock');">Dashboard</div>
		   			<p class="clearB mB10"></p>
		   			
		   			<ul class="setupleftul f13 mT25 mB10" id="conful">
		   			<s:iterator value="dashboardConfigMap" status="count">
		   			<li id='<s:property value="%{key}"/>' onclick="getblock('<s:property value="%{key}"/>','<s:property value="%{value}"/>');">
		   			<a href="#" ><s:property value="%{value}"/></a></li>
		   			</s:iterator>
		   			
		   			</ul>
   			</td>
   			<td valign="top" class="rwobwop">
		   			<div class="tabClass" id="personalLinks" style="display: block;">
		   			<div class="setupSelectedHeading" style="cursor:pointer;"onclick="showhide('configBlock');"></div>
		   			<p class="clearB"></p>
		   			<table class="commentabmenu">
		   				<tbody>
		   					<tr>
		   						<td nowrap="nowrap" class="sel">
		   						<a href="#" id="todayClk" onclick="getBlockDatapart('Today')"> Today</a>
		   							<div class="sarr" style="postion:absolute;left:359.5px; top:225px;">
		   							</div>
		   						</td>    
		   						<td nowrap="nowrap" id="CustomizeName" >
		   							<a href="#" id="weekClk" onclick="getBlockDatapart('Weekly')">Weekly</a></td> 
								<td nowrap="nowrap" >
								<a href="#" id="monthClk" onclick="getBlockDatapart('Monthly')">Monthly</a></td> 
								<td nowrap="nowrap" >
								<s:select id="stage123" list="#{'1':'Lead','2':'Prospect','4':'Closed'}" theme="simple" cssClass="button" cssStyle="  height: 24px;margin: -6px -7px 0px 0px;" onchange="getBlockLead(2,'Monthly');"></s:select> </td> 
								</tr>
								</tbody>
								</table></div>
					   			<div class="setupdatadiv botLftRhtcurv bbLR whiteBg">
						   			<div id="result" class="p10" style="overflow: auto !important;height: 510px;" >
						   				
						   			</div>
					   			</div>
   			</td>
		
		</tr>
	</tbody>
</table>

</div>

</body>
</html>