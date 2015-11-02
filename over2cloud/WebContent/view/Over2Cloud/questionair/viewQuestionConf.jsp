
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>

	<script type="text/javascript">
		function addQuestionair()
		 {
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/createQuestionConf.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
			 
		}
		
		
		function viewQuestionnaire(val)
		 {
			$("#reviewForms").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/patientActivity/reviewQuestionnaireForms.action?pageNo=1&done=0&positionPage=1&type="+val,
			    success : function(subdeptdata) {
		       $("#"+"reviewForms").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
			 
		}
		
		
		/* function viewQuestionnaire(val){
			var url="http://115.112.236.225:9090/over2cloud/view/feedbackforms/feedbackformSetA1.jsp?traceid=0000&type=";
			
			if(val === 'M'){
				window.open(url+'M','_blank');
			}else{
				window.open(url+'F','_blank');
			} 
		}
		*/
		
		
		
	</script>




</head>
<body>

<div class="list-icon">
	 <div class="head">Questionnaire </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
<s:select 
                                      id="viewQuestionnairJsp"
                                      name="viewQuestionnairJsp" 
                                      list="#{'M':'Male','F':'Female'}"
                                      headerKey="-1"
                                      headerValue="Select Gender"
                                      cssClass="select"
                                      cssStyle=" width: 12%; height: 29px; margin-left: 13px; margin-top: 4px;"
                                      theme="simple"
                                      title="Select Gender"
                                      onchange="viewQuestionnaire(this.value);"
                                      >
                          </s:select>
                          </div>
<div style=" float:left; width:100%;">

<%-- div id="time_picker_div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" timepicker="true" timepickerOnly="true"   timepickerGridHour="4"  timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<div id="date_picker_div" style="display:none">
 <sj:datepicker name="date1" id="date1" value="datetoday" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
 </div>
  <s:url id="viewQuestions" action="questionsView"> </s:url>
  <s:url id="viewSubQuestions" action="subQuestionsView" />
 
  <div class="clear"></div>
  <div class="clear"></div>
   
    <div class="listviewBorder" style=" display:none; margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
   		<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
		<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
		<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
		<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
					    
	
			
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addQuestionair();">Add</sj:a>
	</td>   
	</tr></tbody></table>
	</div>
	</div>
	 --%>
	 <div id="reviewForms" >
	 
	 </div>
	
    <%--  <div style="overflow: scroll; height: 430px;">
       <sjg:grid 
		  id="gridedittable22"
          href="%{viewQuestions}"
          gridModel="viewList"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="true"
          editurl="%{modifyTaskRegisData}"
          navigatorDelete="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          onSelectRowTopics="rowselect"
          >
           <sjg:grid 
		  id="gridedittable22222"
          href="%{viewSubQuestions}"
          gridModel="viewList2"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="true"
          editurl="%{modifyTaskRegisData}"
          navigatorDelete="true"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="15"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="gridProps2" id="taskTypeViewProp" status="test" >  
			 <sjg:gridColumn 
			    name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				search="%{search}"
				hidden="%{hideOrShow}"
				formatter="%{formatter}"
				sortable="true"
				/>
		</s:iterator> 
		
</sjg:grid>
		<s:iterator value="gridProps" id="taskTypeViewProp" status="test" >  
			 <sjg:gridColumn 
			    name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				search="%{search}"
				hidden="%{hideOrShow}"
				formatter="%{formatter}"
				sortable="true"
				/>
		</s:iterator> 
		
</sjg:grid>
</div> 
</div> --%>  </div>
</body>
</html>