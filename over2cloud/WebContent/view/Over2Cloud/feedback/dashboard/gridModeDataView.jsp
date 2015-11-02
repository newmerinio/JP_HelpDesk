<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">


</SCRIPT>
</head>
<body>
 <div class="clear"></div>
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
					
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
 <div class="clear"></div>
 
 <div style="overflow: scroll; height: 430px;">
	<s:url id="viewData" action="viewGridDataMode" escapeAmp="false">
	<s:param name="mode"  value="%{mode}" />
	<s:param name="searchField"  value="%{searchField}" />
	<s:param name="fromDate"  value="%{fromDate}" />
	<s:param name="toDate"  value="%{toDate}" />
	<s:param name="searchString"  value="%{searchString}" />
	
	
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
          filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >
		<s:if test="colomnName=='id'">  
			<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
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
							width="%{width}"
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
