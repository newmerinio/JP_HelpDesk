<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    <%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id=$("#uploadDataGrid").jqGrid('getGridParam','selarrrow');

	$.ajax({
		 type : "post",
		 url : "view/Over2Cloud/wfpm/target/saveExcelData.action?selectedId="+sel_id,
		 success : function(saveData) {
				$("#assetExcelResult").html(saveData);
				 setTimeout(function(){ $("#assetExcelResult").fadeIn(); }, 10);
			     setTimeout(function(){ $("#assetExcelResult").fadeOut(); }, 4000);
			},
			error: function() {
		      alert("error");
			}
		});
	});
</script>
</head>
<body>
	
	<s:url id="viewUploadExcelData" action="viewUploadExcelGridData"/>
	<sjg:grid 
		  id="uploadDataGrid"
          href="%{viewUploadExcelData}"
          gridModel="gridDataList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="100,500"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="100"
          autowidth="true"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          >
		<s:iterator value="columnNameMap" id="columnNameMap">
				<sjg:gridColumn 
				name="%{key}"
				index="%{key}"
				title="%{value}"
				width="100"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
		</s:iterator>  
</sjg:grid>
<br><br>
<center>
	<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"/>
</center>
</body>
</html>