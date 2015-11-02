<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/WFPM/industry/targetMap/targetMap.js"/>"></script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	    <div class="head">View</div><div class="head">Target Segment</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Total Records: <s:property value="%{countTargetSegment}"/></div>
		</div>
	
	<!-- Level 1 to 2 Data VIEW URL CALLING Part Starts HERE -->
	<s:url id="viewWeightage" action="viewWeightage" escapeAmp="false" >
	<s:param name="status" value="%{#parameters.status}"></s:param>
	</s:url>
	<!-- Level 1 to 2 Data VIEW URL CALLING Part Ends HERE -->
	
	<!-- Level 1 to 2 Data Modification URL CALLING Part Starts HERE -->
	<s:url id="viewModifyLevel1" action="viewModifyLevel1" />
	<!-- Level 1 to 2 Data Modification URL CALLING Part ENDS HERE -->
	
	<div class="rightHeaderButton">
	</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
				
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<s:if test="%{status=='Active'}">
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					</s:if>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				
						<s:select
									id="status" 
									name="status" 
									list="{'Active','Inactive'}"
									headerKey="-1"
									headerValue="Select Status" 
									cssStyle="height: 28px; width: 34%;"
									theme="simple"
									cssClass="button"
									onchange="searchDept();"
							  />
				
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
	 <s:if test="true || #session.userRights.contains('indu_ADD')">
	     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="MapIndustry();">Add</sj:a>
     </s:if>         
				</td>   
				</tr></tbody></table></div></div> </div>
				<div style="overflow: scroll; height:450px;">
			<sjg:grid 
			  id="gridedittable"
	          href="%{viewWeightage}"
	          gridModel="weightageDataList"
	          groupField="['offeringId']"
	    	  groupColumnShow="[false]"
	    	  groupCollapse="true"
	    	  groupText="['<b>{0}: {1}</b>']"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          rowList="50,100,500,1000"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rowNum="100"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{viewModifyLevel1}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          >
	        <sjg:gridColumn id="id" name="id" index="id" title="ID" width="230" formatter="" sortable="false" hidden="true"/>
			<sjg:gridColumn id="offeringId" name="offeringId" width="230" index="offeringId" title="Offering" formatter="" align="center" sortable="false"/>
			<sjg:gridColumn id="targetSegment" name="targetSegment" width="530" index="targetSegment" title="Target-Segment" align="center" sortable="true"/>
	    	<sjg:gridColumn id="deptName" name="deptName" width="525" index="deptName" title="Department" align="center" sortable="false"/>
	    	<sjg:gridColumn id="weightage" name="weightage" width="80" index="weightage" title="Weightage" align="center" sortable="false"/>
	    	<sjg:gridColumn id="userName" name="userName" width="80" index="userName" title="Created By" align="center" sortable="false"/>
	    	<sjg:gridColumn id="date" name="date" width="80" index="date" title="Created On" align="center" sortable="false"/>
			</sjg:grid>
			
			</div>
	</div>
</body>
</html>
