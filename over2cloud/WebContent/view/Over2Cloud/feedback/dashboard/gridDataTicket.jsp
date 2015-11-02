<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<SCRIPT type="text/javascript">
function resolveremark(cellvalue, options, rowObject){
	return "<a href='#' id='resformatter' style='color:blue' title='Download' onClick='showcapa("+rowObject.id+")'>"+cellvalue+"</a>";
}
function rcaformatter(cellvalue, options, rowObject){
	return "<a href='#' id='resorceformatter' style='color:blue' title='Download' onClick='showrca("+rowObject.id+")'>"+cellvalue+"</a>";
}
function resolveDoc(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='downloadAction("+rowObject.id+",1)'><img src='"+ context_path +"/images/downloadresolve.jpg' alt='Download' height='20'></a>";
}
function resolveDoc1(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='Download' onClick='downloadAction("+rowObject.id+",2)'><img src='"+ context_path +"/images/downloadresolve.jpg' alt='Download' height='20'></a>";
}
function downloadAction(valuepassed,valueFor) 
{
	   $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/feedback/beforeDocumentresolveDownload.action?feedId="+valuepassed+"&type="+valueFor,
		    success : function(data) 
		    {
		    	$("#downloadDialog").dialog('open');
				$("#downloadDialog").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
		 });
}
function showcapa(id){


	
	$("#downloadDialog").dialog({title:"CAPA Details",width:600,height:200});
	$("#downloadDialog").dialog('open');
	$("#downloadDialog").html('<center><font size="5" style="color:#BFB7B7;"><b>'+$("#resformatter").text()+'</b></font></center>');
}
function showrca(){
	$("#downloadDialog").dialog({title:"RCA Details",width:600,height:200});
	$("#downloadDialog").dialog('open');
	$("#downloadDialog").html('<center><font size="5"  style="color:#BFB7B7;"><b>'+$("#resorceformatter").text()+'</b></font></center>');
}


</SCRIPT>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop"></sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Compliance Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>

<sj:dialog
          id="downloadDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Download Document"
          modal="true"
          closeTopics="closeEffectDialog"
          width="200"
          height="100"
          >
</sj:dialog>

 <div class="clear"></div>
 <div id="downloadLoc"></div>
 <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
 
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					    <tr>
					    <td class="pL10"> 
					<%--     <s:if test="%{status == 'Resolved'}">
					    </s:if>
					    <s:else>
					     <sj:a id="action" cssClass="button" button="true" title="Action" onClick="complianceAction();">Action</sj:a>
					    </s:else> --%>
					      </td></tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
					<%-- <sj:a id="sendButton111" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadDashData();"></sj:a> --%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
 <div class="clear"></div>
 	
 <div style="overflow: scroll; height: 430px;">
	<s:url id="viewData" action="viewGridDataTicket" escapeAmp="false">
	<s:param name="deptId"  value="%{deptId}" />
	<s:param name="status"  value="%{status}" />
	<s:param name="type"  value="%{type}" />
	<s:param name="feedId"  value="%{feedId}" />
	<s:param name="fromDate"  value="%{fromDate}" />
	<s:param name="toDate"  value="%{toDate}" />
	</s:url>

	    <sjg:grid 
		  id="gridedittable"
          href="%{viewData}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="15,20,25,30"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          filter="true"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >
		<s:if test="colomnName=='id'">  
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="200"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="true"
							key="true"
		/>
		</s:if>
		<s:else>
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="100"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:else>
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</body>
</html>
