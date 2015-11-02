<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
timePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}

var datePick = function(elem) {
    $(elem).datepicker({dateFormat: 'yy-mm-dd'});
    $('#ui-datepicker-div').css("z-index", 2000);
}
</script>
</head>
<body>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>

<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
<s:url id="viewAllotment" action="viewAllotmentData">
<s:param name="id" value="%{id}" />
</s:url>
<s:url id="modifyAllotment" action="modifyAllotmentData" />
<sjg:grid 
		  id="allotmentGrid"
          caption="%{mainHeaderName}"
          href="%{viewAllotment}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifyAllotment}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" status="test">
		<s:if test="colomnName=='issueon'">  
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			formatoptions="{newformat : 'Y-m-d', srcformat : 'Y-m-d'}"
			editoptions="{dataInit:datePick}"
			/>
		</s:if>
		<s:if test="colomnName=='issueat">
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			editoptions="{dataInit:timePick}"
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
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:else>
		</s:iterator>  
</sjg:grid>
</body>
</html>