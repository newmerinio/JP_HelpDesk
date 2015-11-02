<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="js/referral/referral.js"/>"></script>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<script type="text/javascript">
loadingFunction();
function loadingFunction()
{
	var date = $("#date").val();
	var country=$("#country").val();
	var state=$("#state").val();
	var city=$("#city").val();
	var territory=$("#territory").val();
	var type=$("#rel_type").val();
	var subtype=$("#rel").val();
	var emp=$("#emp").val();
	$.ajax
	({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityPlannerFullViewMain.action?month="+date+"&country="+country+"&state="+state+"&city="+city+"&territory="+territory+"&type="+type+"&subtype="+subtype+"&emp="+emp,
	    async:false,
	    success : function(subdeptdata) 
	    {
		    $("#"+"result").html(subdeptdata);
	    },
	    error: function()
	    {
	        alert("error");
	    }
	 });
}
function setRelType(subType)
{
	var subt=$("#"+subType+' option:selected').text();
	$("#rel").val(subt);
}
function approveActivity()
{
	var id= $("#acti").jqGrid('getGridParam','selarrrow'); 
	var date =  jQuery("#acti").jqGrid('getCell',id,'for_month');
	var datev= date.split("-")[2]+"-"+date.split("-")[1]+"-"+date.split("-")[0];
	$('#abcd').dialog('open');
	$('#abcd').dialog({title: 'Activity Approval',height: '450',width:'1250'});
    $("#activityy").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeApproveActivity.action?date="+datev+"&activityId="+id,
	    success : function(subdeptdata) 
	   {
			$("#"+"activityy").html(subdeptdata);
	   },
	   error: function() 
	   {
           alert("error");
      }
	 });
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="clear"></div>
<s:hidden id="emp" value="%{emp}"/>

<s:hidden id="date" value="%{month}"/>
<div class="listviewBorder">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody><tr></tr>
	<tr><td></td></tr>
	<tr>
	    <td>
		    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
		    <tbody><tr><td class="pL10">
                       <s:select 
	                       id="country"
	                       list="commonMap"
	                       headerKey="-1"
	                       headerValue="Select Country" 
	                       cssStyle="width: 119px;float:left"
	                       onchange="fetchState(this.value,'state');loadingFunction();"
	                       theme="simple"
	                       cssClass="button"
	                       >
                     </s:select> 
                        <s:select 
	                       id="state"
	                       list="{}"
	                       headerKey="-1"
	                       theme="simple"
	                       headerValue="Select State" 
	                       cssClass="button"
	                       cssStyle="width: 119px;float:left"
	                       onchange="fetchCity(this.value,'city');loadingFunction();"
	                       >
                     </s:select>  
                      <s:select 
	                       id="city"
	                       list="{}"
	                       headerKey="-1"
	                       headerValue="Select City"
	                       cssStyle="width: 119px;float:left" 
	                       onchange="fetchTerritory(this.value,'territory');loadingFunction();"
	                       theme="simple"
	                       cssClass="button"
	                       >
                       </s:select>
                        <s:select 
                            id="territory"
                            list="{}"
                            cssStyle="width: 119px;float:left"
                            headerKey="-1"
                            headerValue="Select Territory" 
                            onchange="loadingFunction();"
                            theme="simple"
                            cssClass="button"
                              >
                        </s:select> 
                        <s:select 
                             id="rel_type"
                             list="relationMap"
                             headerKey="-1"
                             headerValue="Select Relationship Type" 
                             cssStyle="width: 119px;float:left"
                             onchange="fetchRelationSubType(this.value,'rel_sub_type');loadingFunction();"
                             theme="simple"
                             cssClass="button"
                                  >
                        </s:select>   
                        <s:select 
	                         id="rel_sub_type"
	                         list="{'No Data'}"
	                         headerKey="-1"
	                         headerValue="Select Relationship Sub Type" 
	                         cssStyle="width: 119px;float:left"
	                         onchange="setRelType('rel_sub_type');loadingFunction();"
	                         theme="simple"
	                         cssClass="button"
                         >
                        </s:select> 
                        
                          <s:hidden name="rel_sub_type" id="rel"></s:hidden>   
		    </td></tr></tbody>
		    </table>
	    </td>
        <td class="alignright printTitle">
           <sj:a id="act"  button="true"  buttonIcon="ui-icon-plus" cssStyle="width: 126px;" cssClass="button" onclick="approveActivity();">Approve Activity</sj:a>
       </td>
	</tr>
	</tbody>
	</table>
	</div>
	</div>
<div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<div id="result"></div>
</div></div>
<sj:dialog
          id="abcd"
          showEffect="slide" 
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="1200"
          position="center"
          height="450"
          draggable="true"
    	  resizable="true"
          >
        <center>  <div id="activityy"></div> </center>
</sj:dialog>
</body>
</html>