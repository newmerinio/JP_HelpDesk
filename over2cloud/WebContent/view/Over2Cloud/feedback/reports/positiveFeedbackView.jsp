<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<SCRIPT type="text/javascript">

function takeAction(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:showActionPage("+row.id+ ","+row.feedTicketId+ ")'>" + cellvalue + "</a>";
}

function showActionPage(rowId,feedTicketId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforeActionOnPosFeedback.action?id="+rowId+"&feedTicketId="+feedTicketId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function feedbackDetails(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:showFeedDetails("+row.id+ ")'>" + cellvalue + "</a>";
}

function showFeedDetails(feedId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/getAllFeedDetails.action?id="+feedId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

</SCRIPT>
</head>
<body>
<s:url id="viewFeedbackInGrid" action="viewPositiveCustomerInGrid" escapeAmp="false">
<s:param name="flage" value="%{flage}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="mode" value="%{mode}"></s:param>
<s:param name="patType" value="%{patType}"></s:param>
<s:param name="docName" value="%{docName}"></s:param>
<s:param name="spec" value="%{spec}"></s:param>
<s:param name="actionStatus" value="%{actionStatus}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable212"
          href="%{viewFeedbackInGrid}"
          gridModel="custDataList"
          groupField="['mode']"
          groupColumnShow="[false]"
          groupText="['<b>{0}: {1}</b>']"
          groupCollapse="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          navigatorRefresh="true"
          rowList="15,30,45,60"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          rownumbers="true"
          >
		<s:iterator value="masterViewProp" id="CRMColumnNames" >  
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
</body>
</html>