<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="js/client/viewClientsupportType.js"/>"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
			<div class="list-icon">
		<div class="head">
		 Client Support Type</div><img alt="" src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">View</div> 
	    </div>
	</div>
	
	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="clear"></div>	 	
	 	<div class="listviewBorder" style="height: 43px;">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" 
					width="100%"><tbody>
					<tr>
					 <td>
					<s:if test="#session.userRights.contains('prcl_MODIFY')">
						<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
						</s:if>
					<%-- <div id="delButtonDiv" style="float: left;"><sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" 
						button="true"  onclick="deleteRow()">Delete</sj:a></div> --%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px; " 
						cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" 
						cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						
							</td></tr>
						<tr><td> <table class="floatL" border="0" cellpadding="0" 
									cellspacing="0"><tbody>
						
						<tr>
						<td class="pL10"> 
		
				</td></tr></tbody></table>
			</td>
			
			<td class="alignright printTitle">
			
			<s:if test="'true' || #session.userRights.contains('prcl_ADD')"> 
				<div style="margin-top: -6%;">
			
				 <sj:a
					button="true"
					cssClass="button" 
					cssStyle="height:25px;"
	                title="Add"
		            buttonIcon=""
					onclick="addClientSupportType();"
					>Add</sj:a>   
				</div>
				
           </s:if>
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>

	<s:if test="'true' || #session.userRights.contains('prcl_VIEW') || #session.userRights.contains('prcl_MODIFY') 
			|| #session.userRights.contains('prcl_DELETE')
			|| #session.userRights.contains('excl_VIEW') || #session.userRights.contains('excl_MODIFY') 
			|| #session.userRights.contains('excl_DELETE')
			|| #session.userRights.contains('locl_VIEW') || #session.userRights.contains('locl_MODIFY') 
			|| #session.userRights.contains('locl_DELETE')
			">
 	<div id="clientsupportTypepart">
		<!-- Client support type details grid will be put here dynamically -->		
	</div>
	</s:if>

	</div>
	
	
</body>
	<script type="text/javascript">
	searchShowClientSupportType();
	</script>
</html>