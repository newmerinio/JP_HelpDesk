.<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id=$("#gridExcel").jqGrid('getGridParam','selarrrow');
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/leaveManagement/saveAttendanceExcel.action?selectedId="+sel_id,
		 success : function(saveData) {
				$("#ExcelDialog").dialog({height:80,width:500, title:'Success Message'});
				$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#excelResult").html(saveData);
				setTimeout(function(){ $("#ExcelDialog").dialog('close'); }, 2000);
			},
			error: function() {
			            alert("error");
			}
			});
	});
</script>
</head>

<body>
<div id="excelResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">

<s:url id="excelShow_URL" action="showExcel" />
<center>
<sjg:grid  id="gridExcel"
           gridModel="gridModelList"
           href="%{excelShow_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="true" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="800,850,900"
	       rowNum="800" 
	       rownumbers="false" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="950"
	      >
           <sjg:gridColumn  name="empname"
                            index="empname"
                            title="Employee ID"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="date"
                            index="date"
                            title="Date"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            
         
          
           <sjg:gridColumn  name="intime"
                            index="intime"
                            title="In Time"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="outtime"
                            index="outtime"
                            title="Out Time"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="status"
                            index="status"
                            title="Status"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="attendance"
                            index="attendance"
                            title="Attendance"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="comment"
                            index="comment"
                            title="Comment"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="70"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           
           <sjg:gridColumn  name="extraworking"
                            index="extraworking"
                            title="Extra Working"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           <sjg:gridColumn  name="clientvisit"
                            index="clientvisit"
                            title="Client Visit"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           <sjg:gridColumn  name="lupdate"
                            index="lupdate"
                            title="Leave Update Via"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           <sjg:gridColumn  name="lupdateto"
                            index="lupdateto"
                            title="Leave Update To"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
       </sjg:grid>
       <br>
       
<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
</center>
</div>
</div>
</div>
</body>
</html>