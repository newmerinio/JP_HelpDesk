<%@ taglib prefix="s" uri="/struts-tags" %>
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
	sel_id=$("#gridFbDraftExcel").jqGrid('getGridParam','selarrrow');
	$.ajax({
		 type : "post",
		 url : "view/Over2Cloud/feedback/bedMapping/saveBedMappingExcel.action?selectedId="+sel_id,
		 success : function(saveData) {
				$("#ExcelDialog").dialog({height:80,width:500, title:'Success Message'});
				$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#fbDraftExcelResult").html(saveData);
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
<div id="fbDraftExcelResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="bedMappingShow_URL" action="showBedMappingExcel" />
<center>
<sjg:grid  id="gridBedMappingExcel"
           caption="Bed Mapping Detail"
           gridModel="gridBedMappingExcelModel"
           href="%{bedMappingShow_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="true" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="100,200,300"
	       rowNum="100" 
	       rownumbers="false" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="500"
	      >
	      <sjg:gridColumn   name="emp_contact_id"
                            index="emp_contact_id"
                            title="Emp Id"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="200"
                            align="center">
           </sjg:gridColumn>
	      
           <sjg:gridColumn  name="bed_name"
                            index="bed_name"
                            title="Bed"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="300"
                            align="center">
           </sjg:gridColumn>
          
           
       </sjg:grid>
<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
</center>
</div>
</div>
</div>
</div>
</div>
</body>
</html>