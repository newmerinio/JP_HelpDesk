<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/KPI/KPIMapping.js"/>"></script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div id="head" class="head"><s:property value="%{moduleName}"/></div><div class="head">
		<img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
		<div class="head">View</div> 
	</div>
	
	<!-- Level 1 to 2 Data VIEW URL CALLING Part Starts HERE -->
	<s:url id="viewLevel1" action="viewLevel1" />
	
	
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
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" 
							cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" 
							button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" 
							button="true"  onclick="refreshRow()"></sj:a>
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
	 <sj:a
							button="true"
							cssClass="button" 
							cssStyle="height:25px;"
			                title="Add"
				            buttonIcon="ui-icon-plus"
							onclick="viewAddPage();"
							>Add</sj:a>          
				</td>   
				</tr></tbody></table></div></div> </div>


<s:url id="viewKraKpiMapping" action="viewKraKpiMappingAction"/>
<s:url id="editKraKpiMapping" action="editKraKpiMappingAction" />
	
<div style="overflow: scroll; height: 450px;">
	<sjg:grid 
		  id="gridedittable"
          href="%{viewKraKpiMapping}"
          gridModel="masterViewList"
          groupField="['empName']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0}: {1}</b>']"
          navigator="true"
          navigatorEdit="false"
          navigatorDelete="true"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorAdd="false"
          navigatorView="true"
          rowList="25,500,1000,5000"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"
          loadingText="Requesting Data..."  
          rowNum="500"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{editKraKpiMapping}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          onSelectRowTopics="rowselect"
          shrinkToFit="false"
          autowidth="true"
          >
         <sjg:gridColumn 
            name="id"
            name="id"
            index="id"
     		title="Id"
     		key="true"
     		editable="false"     		sortable="false"    		align="center"    		width="100"    		hidden="true"
    		/>
         <sjg:gridColumn 
            name="empName"
            name="empName"
            index="empName"
     		title="Employee" 
     		editable="false"     		sortable="false"    		align="center"    		width="100"    		hidden="false"
    		/>
         <sjg:gridColumn 
            name="kra"
            name="kra"
            index="kra"
     		title="KRA"
     		editable="false"     		sortable="false"    		align="center"    		width="660"    		hidden="false"
    		/>
         <sjg:gridColumn 
            name="kpi"
            name="kpi"
            index="kpi"
     		title="KPI"
     		editable="false"     		sortable="false"    		align="center"    		width="650"    		hidden="false"
    		/>
	
	</sjg:grid>
</div>
	</div>
</body>
</html>
