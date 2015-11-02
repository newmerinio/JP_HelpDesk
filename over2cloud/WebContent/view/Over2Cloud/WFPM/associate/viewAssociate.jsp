<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/lead/LeadCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="js/associate/associate.js"/>"></script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<s:if test="%{fromDashboard == null}">
			<%-- <div class="head"><s:property value="%{mainHeaderName}"/> </div> --%>
			<div id="Aid" class="head">15-05-2015-1 Prospective Associate</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View</div>
		</s:if>
		<s:else>
			<%-- <div class="head"><s:property value="%{fromDashboard}"/> </div> --%>
			<div id="Aid" class="head">15-05-2015-2 Associate</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View cjkkkkkkkkkk</div>
		</s:else>
	</div>
	
	<div style=" float:left; padding:10px 1%; width:98%;">
		<s:url id="viewAssociate" action="viewAssociateGrid?isExistingAssociate=%{isExistingAssociate}&associateStatus=%{associateStatus}" />
		<s:url id="modifyAssociate" action="viewModifyAssociate" />
		<s:url id="viewAssociateContact" action="viewAssociateContactGrid" />
		<s:url id="modifyAssociateContact" action="viewModifyAssociateContact" />
		
		<div class="clear"></div>
		<s:if test="%{isExistingAssociate == 0}">
		<div class="rightHeaderButton">
		<%-- <%if(userRights.contains("prcl_ADD")){ %>
			<input class="btn createNewBtn" value="Create Pros. Associate " type="button" 
			       onclick="addProspectiveAssociate();">
		<%} %> --%>
		</div>
		</s:if>
		
		<%-- <div class="leftHeaderButton">
			<s:select id="selectstatus" list="#{'-1':'Not Authorised'}"  cssClass="button" onchange="searchshowassocaitedata(this.value);" />
		</div> --%>
		<div class="clear"></div>
		<div id="alphabeticalLinks"></div>
			<div class="listviewBorder">
				<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<%-- <div id="editButtonDiv" style="float: left;"><sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a></div> --%>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					<%-- <div id="delButtonDiv" style="float: left;"><sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a><!--</div>
					--><sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="refreshRow()"></sj:a>
					
				<s:select id="selectstatus" list="#{'-1':'Not Authorised'}"  cssClass="button" onchange="searchshowassocaitedata(this.value);" 
					cssStyle="margin-top: -27px;margin-left: 2px;height: 26px;"/>
					
		<s:select
			id="industryID" 
			name="industry" 
			list="industryList" 
			headerKey="-1"
			headerValue="Select Industry" 
            theme="simple"
            cssStyle="height: 26px;"
            onchange="fillName(this.value,'subIndustryID')"
			cssClass="button"
            />
		<s:select
			id="subIndustryID" 
			name="subIndustry" 
			list="#{'-1':'Select Sub-Industry'}" 
            cssStyle="height: 26px;"
            theme="simple" 
            onchange="searchResult('','','','');"
			cssClass="button"
            />
         <s:select 
			id="starRating" 
			name="starRating"
			list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
			headerKey="-1" 
			headerValue="Select Rating"
			cssStyle="height: 26px;"
            theme="simple" 
            onchange="searchResult('','','','');"
			cssClass="button"
			/>
		 <s:select
			id="sourceName" 
			name="sourceName" 
			list="sourceList" 
			headerKey="-1"
			headerValue="Select Source" 
			cssStyle="height: 26px;"
			theme="simple"
			onchange="searchResult('','','','');"
			cssClass="button"
			/>
		<s:select
			id="locationID" 
			name="location" 
			list="locationList"
			headerKey="-1"
			headerValue="Select Location" 
			cssStyle="height: 26px;"
			theme="simple"
			onchange="searchResult('','','','');"
			cssClass="button"
			/>
			<s:select
			id="associateTypeId" 
			name="associateType" 
			list="associateTypeMap"
			headerKey="-1"
			headerValue="Select Type" 
			cssStyle="height: 26px;"
			theme="simple"
			onchange="searchResult('','','','');"
			cssClass="button"
			/>
			<s:select
			id="associateCategoryID" 
			name="associateCategory" 
			list="associateCategoryMap"
			headerKey="-1"
			headerValue="Select Category" 
			cssStyle="height: 26px;"
			theme="simple"
			onchange="searchResult('','','','');"
			cssClass="button"
			/>
			Associate Name: <s:textfield name="associateName"  id="associateNameId" theme="simple" onkeyup="searchResult('','','','');" cssClass="textField" placeholder="Enter Data" style="margin: 0px 0px 9px; width: 12%;"/>
				</td></tr></tbody></table>
				</td>
				<td class="alignright printTitle">
					<%-- <sj:a  button="true" cssClass="button" 
					buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a> --%>
	<s:if test="true ||  #session.userRights.contains('pras_ADD')">					 
		<sj:a  button="true" 
	           cssClass="button" 
	           cssStyle="height:25px; width:32px"
	           title="Download"
	           buttonIcon="ui-icon-arrowstop-1-s" 
	           onclick="getCurrentColumn('downloadExcel','associateDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" />
	           
		<sj:a  button="true" 
	           cssClass="button" 
               cssStyle="height:25px; width:32px"
               title="Upload"
	           buttonIcon="ui-icon-arrowstop-1-n" 
	           onclick="excelUpload();" />
      
        <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Add"
	            buttonIcon="ui-icon-plus"
				onclick="addProspectiveAssociate();"
				>Add</sj:a>   
	</s:if>			       
	                <!--<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
				--></td>   
				</tr></tbody></table></div>
			</div>
			</div>
<s:if test="true ||  #session.userRights.contains('pras_VIEW') || #session.userRights.contains('pras_MODIFY') 
			|| #session.userRights.contains('pras_DELETE')
			|| #session.userRights.contains('exas_VIEW') || #session.userRights.contains('exas_MODIFY') 
			|| #session.userRights.contains('exas_DELETE')
			|| #session.userRights.contains('loas_VIEW') || #session.userRights.contains('loas_MODIFY') 
			|| #session.userRights.contains('loas_DELETE')
			">		
	<div id="datapart">
		<!-- Grid will come dynamically here from page 'viewsearchAssociate.jsp' -->
	</div>
</s:if>
		
		</div>
		<br><br>
<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		<div id="divFullHistory" style="float:left; width:97%; padding-left: 1%;"> </div>
</center>

</body>
<!--<sj:dialog id="downloadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Associate Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="downloadAstColumnDiv"></div>
</sj:dialog>
-->
<sj:dialog 
	id="downloadColumnAllModDialog"  
	modal="true" 
	effect="slide" 
	autoOpen="false" 
    width="300" 
    height="500" 
    title="Associate Column Names To Choose" 
    loadingText="Please Wait" 
    overlayColor="#fff" 
    overlayOpacity="1.0" 
    position="['center']">
		<div id="downloadAllModColumnDiv"></div>
</sj:dialog>

<SCRIPT type="text/javascript">
/*
fillAssociateTypeDD('<%= (userRights.contains("pras_VIEW") || userRights.contains("pras_MODIFY") || userRights.contains("pras_DELETE"))? "1":"0" %>'
		,'<%= (userRights.contains("exas_VIEW") || userRights.contains("exas_MODIFY") || userRights.contains("exas_DELETE"))? "1":"0" %>'
		,'<%= (userRights.contains("loas_VIEW") || userRights.contains("loas_MODIFY") || userRights.contains("loas_DELETE"))? "1":"0" %>',"selectstatus");
*/
fillAssociateTypeDD("1","1","1","selectstatus");

function fillAssociateTypeDD(a,b,c,divId)
{
	//alert(a+"\n"+b+"\n"+c+"\n"+divId);
	$('#'+divId+' option').remove();
	if(a == "1")
	{
	$('#'+divId).append($("<option></option>").attr("value","0").text("Prospective Associate"));
	}
	if(b == "1")
	{
	$('#'+divId).append($("<option></option>").attr("value","1").text("Existing Associate"));
	}
	if(c == "1")
	{
	$('#'+divId).append($("<option></option>").attr("value","2").text("Lost Associate"));
	}
}
</SCRIPT>
<script type="text/javascript">
var isExistingAssociate='${isExistingAssociate}';
function ExistLostAssociate() {
	if(isExistingAssociate != '0' || isExistingAssociate != '1' || isExistingAssociate != '2')
	{
		isExistingAssociate = '0';
	}
	$("#selectstatus").val(isExistingAssociate);
	searchshowassocaitedata(isExistingAssociate);
}
ExistLostAssociate();
fillAlphabeticalLinks();
</script>
</html>
