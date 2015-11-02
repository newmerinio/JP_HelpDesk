<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function supportAction()
{
	var valuepassed;
	valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(valuepassed=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	$("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/AssetOver2Cloud/Asset/beforeTakeAction?assetReminderID="+valuepassed);
	$("#takeActionGrid").dialog('open');
	
	   return false;
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function getActionData(freq,dueD,moduleName)
{
	var frequency=$("#"+freq).val();
	var dueDate=$("#"+dueD).val();
	var module=$("#"+moduleName).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName="+module+"&frequency="+frequency+"&dueDate="+dueDate,
	    success : function(data) 
	    {
       		$("#data_part").html(data);
		},
	    error: function() 
	    {
            alert("error");
        }
	 });
}


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<s:if test="moduleName=='Support'">
<div class="list-icon">
	 <div class="head">
	 Support</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Activity</div> 
</div>
</s:if>
<s:if test="moduleName=='Preventive'">
<div class="list-icon">
	 <div class="head">
	 Preventive Maintenance</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Activity </div> 
</div>
</s:if>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
								<sj:a id="action" cssClass="button" button="true" title="Action" onclick="supportAction();">Action</sj:a>
	                          </td>
					      </tr>
					     </tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>
<s:url id="viewReminder" action="viewClickedReminder" escapeAmp="false">
<s:param name="moduleName"  value="%{moduleName}" />
<s:param name="parameterName"  value="%{parameterName}" />
<s:param name="reminderId"  value="%{reminderId}" />
</s:url>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewReminder}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          editinline="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp"  status="test"> 
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="165"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator> 
</sjg:grid> 
</div>

<div id="datapart">

		<!-- support data will come dynamically *******************************************************************-->
</div>

</div>
</div>
</div>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
    	  onCloseTopics="closeDialog"
          autoOpen="false"
          title="Asset Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<sj:dialog 
		              			id="downloadColumnAllModDialog" 
		              			modal="true" 
		              			effect="slide" 
		              			autoOpen="false" 
		              			width="300" 
		              			height="500" 
		              			title="Asset " 
		              			loadingText="Please Wait" 
		              			overlayColor="#fff" 
		              			overlayOpacity="1.0" 
		              			position="['center','center']" >
								<div id="downloadAllModColumnDiv"></div>
								</sj:dialog>
		
</body>
</html>