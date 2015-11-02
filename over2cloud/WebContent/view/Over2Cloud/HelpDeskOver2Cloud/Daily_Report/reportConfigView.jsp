<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="report_URL" action="viewReportData" escapeAmp="false">
<s:param name="searchField" value="%{searchField}" />
<s:param name="searchString" value="%{searchString}" />
<s:param name="searchOper" value="%{searchOper}" />
</s:url>
<s:url id="modifyReport_URL" action="modifyReport" /> 
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="yyyy-mm-dd"/>
</div>
        <sjg:grid 
		  id="gridreport"
          href="%{report_URL}"
          gridModel="masterViewList"
          groupField="['emp_id']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0}: {1} </b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,150,200"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="50"
          autowidth="true"
          editurl="%{modifyReport_URL}" 
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {reloadPage();}}"
		  navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {reloadPage();}}"
          navigatorViewOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
		         	<s:iterator value="masterViewProp" id="masterViewProp" >  
							<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					/>
				
				</s:if>
				<s:else>
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
			
					</s:iterator> 		
		 			
		 																		
	</sjg:grid>
</body>
</html>