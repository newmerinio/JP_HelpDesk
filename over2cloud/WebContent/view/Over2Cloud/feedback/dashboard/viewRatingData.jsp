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


</SCRIPT>
</head>
<body>



 <div class="clear"></div>
 <div id="downloadLoc"></div>
 <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
 
 <div class="clear"></div>
 	
 <div style="overflow: scroll; height: 430px;">
	<s:url id="viewData" action="viewGridDataRatingTicket" escapeAmp="false">
	<s:param name="status"  value="%{status}" />
	<s:param name="category"  value="%{category}" />
	<s:param name="rating"  value="%{rating}" />
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
          filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
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
