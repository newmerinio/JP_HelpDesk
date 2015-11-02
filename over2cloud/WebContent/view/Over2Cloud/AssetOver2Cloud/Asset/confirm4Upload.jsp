<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {

	var sel_id;
	var upload4=$('#uploadModule').val();
	sel_id=$("#uploadDataGrid").jqGrid('getGridParam','selarrrow');
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/saveExcelData.action?selectedId="+sel_id+"&upload4="+upload4,
		 success : function(saveData) {
				$("#assetExcelResult").html(saveData);
			},
			error: function() {
			            alert("error");
			}
			});
	
	});
</script>
</head>
<body>
<div id="assetExcelResult">
<s:hidden id="uploadModule" value="%{upload4}" />
<s:url id="viewUploadExcelData" action="viewUploadExcelGridData">
</s:url>
<sjg:grid 
		  id="uploadDataGrid"
          caption="Upload Data"
          href="%{viewUploadExcelData}"
          gridModel="gridDataList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="100,500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          width="780"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		<s:iterator value="columnNameMap" id="columnNameMap">
				<sjg:gridColumn 
				name="%{key}"
				index="%{key}"
				title="%{value}"
				width="150"
				align="left"
				editable="false"
				search="false"
				hidden="false"
				/>
		</s:iterator>  
</sjg:grid>
<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
</div>
</body>
</html>