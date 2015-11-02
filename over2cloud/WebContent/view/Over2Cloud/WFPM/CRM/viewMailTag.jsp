<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<!--<script src="<s:url value="js/lead/LeadCommon.js"/>"></script>
--><script src="<s:url value="js/WFPM/CRM/viewMailTag.js"/>"></script>
<title>Mail Tag</title>
</head>
<body>
<sj:dialog
          id="takeActionGridwwwwww"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Data Description"
          modal="true"
          width="800"
          height="250"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
   <div class="clear"></div>
	<div class="list-icon">
	 <div class="head">Dynamic Mail Tag</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div> 
	<div style=" float:left; padding:10px 1%; width:98%;">
	<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody><tr></tr><tr><td></td></tr>
					<tr>
					<td> <table class="floatL" border="0" cellpadding="0" 
						cellspacing="0"><tbody><tr><td class="pL10">
						
						<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " cssClass="button" button="true"  onclick="searchRow()"></sj:a>
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						<%-- <sj:datepicker id="fromDate" name="fromDate" value="%{fromDate}" cssClass="button" cssStyle="width: 111px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 24px;margin-top: -16px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
						<sj:datepicker id="toDate" name="toDate" cssClass="button" onchange="searchMailTagResult('','');" value="today" cssStyle="width: 111px;border: 1px solid #e2e9ef;border-top: 1px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 24px;margin-top: -16px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/> --%>
						
						      	 
				
					</td></tr></tbody></table>
				</td>
				
				<td class="alignright printTitle">
			<s:if test="'true' || #session.userRights.contains('prcl_ADD')"> 
				
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;"
	                title="Add"
		            buttonIcon="ui-icon-plus"
					onclick="addMailTag();"
					>Add</sj:a>   
           </s:if>
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
	
	<div id="mailtagdatapart">
		<!-- User Action History grid will be put here dynamically -->		
	</div>
	
	</div>
</body>
	<script type="text/javascript">
	 		
	searchMailTagResult('','');
	</script>
</html>