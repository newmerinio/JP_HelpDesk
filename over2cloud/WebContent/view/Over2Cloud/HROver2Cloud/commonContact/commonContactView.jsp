<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<SCRIPT type="text/javascript" src="js/department/department.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadGrid();
	    }});
		
	}
}

function deleteRow()
{
	 var status = jQuery("#gridedittable").jqGrid('getCell',row,'flag');
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
			$("#gridedittable").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
				reloadGrid();
		    }});
			
		}
	
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadGrid()
{
	$.ajax({
	    type : "post",
	    //url : "/jbmportal/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addNewSubGroup() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeContactCreate.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function uploadContact() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeUploadContact.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function viewContactDoc(cellvalue, options, row)
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='contactDocDownload("+row.id+")'><img src='"+ context_path +"/images/docDownlaod.jpg' alt='Download'></a>";
}


function contactDocDownload(contactId) 
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/contactDocDownload.action?contId="+contactId,
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

function viewEmpImage(cellvalue, options, row) {

	return "<a href='#' onClick='javascript:openDialog10("+row.id+ ")'>" + cellvalue + "</a>";
}


function openDialog10(rowId)
{
    id=rowId;
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/hr/contactImageView.action?id="+ rowId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
function getOnChangePrimaryData(searchFields,SearchValue)
{
	$.ajax({
	    type : "post",
	    //url : "/jbmportal/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactView.action?searchFields="+searchFields+"&SearchValue="+SearchValue,
	    success : function(subdeptdata) {
       $("#result_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
getOnChangePrimaryData('contct.status','0');
</SCRIPT>
</head>
<body>
<div class="list-icon">
	 <div class="head">View Primary Contactxs </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					    <%if(userRights.contains("empl_MODIFY")) {%>
					    	<sj:a id="editButton" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onclick="editRow()"></sj:a>
							<%} %>
							   <%if(userRights.contains("empl_DELETE")) {%>
					    	 <sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							
							<%} %>
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
						         <s:if test="%{mgtStatus}">
				  			 <s:select 
		                              id="office"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Country Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getheadoffice(this.value,'headOfficeId');"
		                              >
		                  </s:select>
						  <s:select 
		                              id="headOfficeId"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Head Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getbranchoffice(this.value,'regLevel')"
		                              >
		                   </s:select>
		                   </s:if>
		                     <s:if test="%{hodStatus}">
		                  		<s:select 
		                              id="regLevel"
		                              name="regLevel"
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Branch Office" 
		                              cssClass="button"
		                              theme="simple"
		                              onchange="getGroupNamesForMappedOffice('groupType',this.value);"
		                              >
		                      </s:select>
		                      </s:if>
						       <s:select  
						    		id					=		"groupType"
						    		name				=		"groupType"
						    		list				=		"groupMap"
						      		headerKey           =       "-1"
							        headerValue         =       "Select Contact Type"
							       cssClass             =      "button"
							      cssStyle            =        "height: 26px;margin-top: -29px;margin-left:3px;width: 155px;"
							      theme                 =       "simple"
						      	 onchange			   =		"getDeptBySubGroup('contactSubType',this.value);getOnChangePrimaryData('dpt.contact_type_id',this.value)"
						      	 >
						      	 </s:select>
						       
						        <s:select  
							    		id					=		"contactSubType"
							    		name				=		"contactSubType"
							    		list				=		"{'No Data'}"
							      	    headerKey           =       "-1"
							            headerValue         =       "Select Contact Sub-Type"
							            cssClass            =      "button"
							            cssStyle            =        "height: 26px;margin-top: -29px;margin-left:3px;width: 180px;"
							            theme               =       "simple"
							      	 	onchange			=		"getOnChangePrimaryData('contct.sub_type_id',this.value) "
						      	 >
						      	 </s:select>
						      	  <s:select  
							    		id					=		"status"
							    		name				=		"status"
							    		list				=		"#{'0':'Active','1':'Inactive','-1':'All'}"
							            cssClass            =      "button"
							          cssStyle            =        "height: 26px;margin-top: -29px;margin-left:3px;width: 155px;"
							            theme               =       "simple"
							      	 	onchange			=		"getOnChangePrimaryData('contct.status',this.value) "
						      	 >
						      	 </s:select>
						      	   
					       </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  
				      <%if(true || userRights.contains("empl_ADD")) 
					{%>
						<sj:a id="uploadButton"    button="true"  buttonIcon="ui-icon-arrowstop-1-n" cssClass="button" cssStyle="height:25px; width:32px" title="Upload"  onclick="uploadContact();"></sj:a>
						<sj:a cssClass="btn createNewBtnFeedbck" buttonIcon="ui-icon-plus" button="true" onclick="addNewSubGroup();" cssClass="button" title="Add">Add</sj:a>
					<%}
						%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div id="result_part"></div>
</div></div>
</body>
</html>