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

function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}

function reloadGrid()
{
	var grid = $("#gridedittable");
	grid.trigger("reloadGrid",[{current:true}]);
}


function serachAgain()
{
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/beforeFeedDetailsSearch.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

$.subscribe('getStatusFormAction', function(event,data)
		  {
			  var connection="<%=request.getContextPath()%>";
			  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			  var ticketNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticketNo');
			  if (ticketNo=="")
				{
					alert("Please Select a Row !!!");
				}
			  else
			  {

				  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				    $.ajax({
				        type : "post",
				        url : connection+"/view/Over2Cloud/feedback/beforeCompleteDetails.action?ticketNo="+ticketNo,
				        success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
				    },
				       error: function() {
				            alert("error");
				        }
				     });
			  }
	 			
			  
		  });

function setDownloadType(downloadType){
	var conP = "<%=request.getContextPath()%>";
 var downloadactionurl="downloadfeedbackreportPaper";
 var mode = document.getElementById("mode").value;
	var type=document.getElementById("type").value;
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();


	$("#downloaddailcontactdetails").dialog('open');
	$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/feedback/searchedFeedbackprogress.action?downloadType="+type+"&downloadactionurl="+downloadactionurl+"&mode="+mode+"&type="+type+"&startDate="+startDate+"&endDate="+endDate,
	    success : function(data) 
	    {
 			$("#datapart").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });

}
</SCRIPT>
</head>
<body>
<s:url id="viewPatients" action="viewSearchData" escapeAmp="false">
<s:param name="mode" value="%{mode}"></s:param>
<s:param name="type" value="%{type}"></s:param>
<s:param name="startDate" value="%{startDate}"></s:param>
<s:param name="endDate" value="%{endDate}"></s:param>
</s:url>
<div style="overflow: scroll; height: 430px;">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewPatients}"
		          gridModel="masterViewList"
		          groupField="['mode']"
		          groupText="['<b>{0} - {1} Records</b>']"
                  groupColumnShow="[false]"
		          groupColumnShow="false"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          rowList="500,100"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="500"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{viewModifyFeedback}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          >
				<s:iterator value="masterViewProp" id="masterViewProp" >  
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
				</s:iterator>  
		</sjg:grid>
</div>		
</html>