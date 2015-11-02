<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="height: 28px;" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td cssStyle="height:25px; width:32px">
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle" cssStyle="height:25px; width:32px">
				       </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<s:url id="href_URL" action="viewFeedbackActionDetailFeedback" escapeAmp="false">
<s:param name="id" value="%{id}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable123"
          href="%{href_URL}"
          gridModel="feedbackList"
    	  groupCollapse="true"
    	  groupText="['<b>{0} - {1} Tickets</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
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
          shrinkToFit="false"
          autowidth="true"
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
<div id="ab"></div>
</body>
</html>