<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

function takeAction(cellvalue, options, row)
{
	var context_path = '<%=request.getContextPath()%>';
	return "<img title='Feedback Rating' src='"+ context_path +"/images/paperimage.png' onClick='javascript:showPccActionPage("+row.id+ ");' />"+cellvalue
	// return "<a href='#' onClick='javascript:showPccActionPage("+row.id+ ")'>" + cellvalue + "</a>";
}

function showPccActionPage(rowId)
{
	var mrdNo = $("#mrdNo").val();
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/feedback/beforePccActionOnPaperFeedback.action?id="+rowId+"&clientId="+mrdNo,
        success : function(subdeptdata) {
       $("#"+"viewpaperratingdata").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
      $("#viewpaperratingdata").dialog('open');
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
<s:url id="viewPccFeedbackInGridPaper" action="viewPccFeedbackInGridPaper" escapeAmp="false">
<s:param name="compType" value="%{#parameters.compType}"></s:param>
<s:param name="clientId" value="%{#parameters.clientId}"></s:param>
</s:url>
<s:hidden name = "clientId" id="clientId" value="%{#parameters.clientId}" />
		 	<sjg:grid 
				  id="gridedittableid"
		          href="%{viewPccFeedbackInGridPaper}"
		          gridModel="masterViewList"
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
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="true"
		          pager="true"
		          rownumbers="true"
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
				formatter="%{formatter}"
				/>
				</s:iterator>  
		</sjg:grid>
</body>
</html>