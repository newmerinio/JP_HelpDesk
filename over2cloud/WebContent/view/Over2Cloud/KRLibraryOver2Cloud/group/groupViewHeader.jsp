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
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) 
{
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:150, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
}
function deleteRow()
/* {
	$("#gridBasicDetails").jqGrid('delGridRow',row,  {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
		reloadPage();
    }});
} */

{
	var status = jQuery("#gridBasicDetails").jqGrid('getCell',row,'active');
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	}
	else
	{
		$("#gridBasicDetails").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}
function searchRow()
{
	 $("#gridBasicDetails").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	/*  grid reload change by manab */
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/groupHeaderKR.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

</script>
<script type="text/javascript">
function createGrp()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/brforeKRGroupAdd.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function viewGroupData()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/kRViewGroup.action?fieldName=status&fieldvalue=Active",
	    success : function(data) 
	    {
			$("#resultDiv1").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
viewGroupData();
function onChangeSubGroup(searchString,searchField)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/kRViewGroup.action?searchStr="+searchString+"&searchFields="+searchField,
	    success : function(data) 
	    {
			$("#resultDiv1").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function getSearchData(searchingData)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/kRViewGroup.action?searchingData="+searchingData,
	    success : function(data) 
	    {
			$("#resultDiv1").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

/* for active and inactive changes by manab  */

 function getOnChangePrimaryData(fieldName1,fieldvalue1)
 {
 	//alert("field name::"+fieldName1+" and  "+fieldvalue1);
 	$.ajax({
 	    type : "post",
 	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/kRViewGroup.action?fieldName="+fieldName1+"&fieldvalue="+fieldvalue1,
 	   success : function(data) 
	    {
			$("#resultDiv1").html(data);
	    },
 	   error: function() {
             alert("error");
         }
 	 });
 	
 }
</script>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	 <div class="head">KR Group </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View&nbsp;&nbsp;&nbsp;<s:iterator value="countMap">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>
</s:iterator></div> 
</div> 
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">  
					    <%if(userRights.contains("group_MODIFY")){ %>
					     <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
						<%} %>
						<%if(userRights.contains("group_DELETE")){ %>
						 <sj:a id="delButton" title="Inactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
					   <%} %>
					   	<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
						<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a>
					     
					    	<s:select
								id="deptName" 
								list="deptName"
								headerKey="-1"
								headerValue="Select Department"
								onchange="fetchGroup(this.value,'groupName');onChangeSubGroup(this.value,'dept.id');"
					            theme="simple"
					            cssStyle="height: 26px;"
								cssClass="button"
					            />
							<s:select
								id="groupName" 
								list="{'No Data'}"
								headerKey="-1"
								headerValue="Select Group"
							    onchange="getSubGrp(this.value,'subGroupName');onChangeSubGroup(this.value,'kr.id');"
					            cssStyle="height: 26px;"
					            theme="simple" 
								cssClass="button"
					            />
					       
								<s:textfield id="searching" name="searching" cssClass="button" cssStyle="margin-top: -27px;height: 14px;width:84px;margin-left: 3px;" Placeholder="Enter Search" onchange="getSearchData(this.value)"/>
							 <s:select  
						    		id					=		"status"
						    		name				=		"status"
						    		list				=		"#{'Active':'Active','In active':'In active','-1':'All'}"
							       cssClass             =      "button"
							       cssStyle             =      "height: 26px;"
							      theme                 =       "simple"
						      	   onchange			    =		"getOnChangePrimaryData('status',this.value) "
						      	 >
						      	 </s:select>			      
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  <%if(userRights.contains("group_ADD")){ %>
						<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="createGrp();">Add</sj:a>
				 <%} %>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>		
<div style="overflow: scroll; height: 430px;">
<div id="resultDiv1"></div>
</div>
</div>
</body>
</html>