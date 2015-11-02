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



function setDownloadType(downloadType){
	var conP = "<%=request.getContextPath()%>";
 var downloadactionurl="downloadfeedbackreportPaper";


 var ticketNo = document.getElementById("ticketNo").value;
	$("#downloaddailcontactdetails").dialog('open');
	$("#datapart").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/feedback/searchedFeedbackTicketprogress.action?ticketNo="+ticketNo,
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
<s:hidden id="ticketNo" name="ticketNo" value="%{ticketNo}" />
<sj:dialog 
      	id="downloaddailcontactdetails" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Select Fields To Download"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="300"
        height="350"
    >
    <div id="datapart"></div>
    </sj:dialog>
<div class="list-icon">
	 <div class="head">Searched Data</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					        <sj:a cssClass="btn createNewBtnFeedbck" button="true" onclick="serachAgain();" cssClass="button" title="Back" value="Back" label="Back">Back</sj:a> 
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  	<sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" title="Download" cssStyle="height:25px; width:32px" onclick="setDownloadType('downloadExcel')"  ></sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>

<s:url id="viewPatientsReport" action="completDetailsView" escapeAmp="false">
<s:param name="ticketNo" value="%{ticketNo}"></s:param>
</s:url>
<div style="overflow: scroll; height: 430px;">
		 	<sjg:grid 
				  id="gridedittable"
		          href="%{viewPatientsReport}"
		          gridModel="masterViewList"
		          groupField="['status']"
		          groupText="['<b>{0} - {1} Records</b>']"
                  groupColumnShow="[false]"
		          groupColumnShow="false"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="false"
		          navigatorEdit="false"
		          navigatorSearch="true"
		          rowList="15,30,45,60"
		          rownumbers="-1"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
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
		</div></div>
</body>
</html>