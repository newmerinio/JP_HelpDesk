<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<script>
function getFeedbackByPaper()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforeFeedbackView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Feedback History</div><img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">View</div> 
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
					        </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getFeedbackByPaper();">Back</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="negFeedback">
	<s:url id="href_URL" action="viewPatAllTicketDetails" escapeAmp="false" namespace="/view/Over2Cloud/feedback/report" >
<s:param name="clientId" value="%{clientId}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URL}"
          gridModel="feedbackList"
          groupField="['feedtype']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0} - {1} Tickets</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="15,30,45"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          rownumbers="true"
          >

		  <s:iterator value="feedbackColumnNames" id="feedbackColumnNames" >  
		  <sjg:gridColumn 
		      			   name="%{colomnName}"
		      			   index="%{colomnName}"
		      			   title="%{headingName}"
		      			   width="%{width}"
		      			   align="%{align}"
		      			   editable="%{editable}"
		      			   formatter="%{formatter}"
		      			   search="%{search}"
		      			   hidden="%{hideOrShow}"
		      			   frozen="%{frozenValue}"
		      			   
		 				   />
		   </s:iterator>  
</sjg:grid>
</div>
</div>
</div><br><br>
</div>
</body>
</html>