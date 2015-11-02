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




<s:url id="viewVisit1" action="leavePreVisitRequestViewRecord" escapeAmp="false">

<s:param name="moduleFlag" value="%{moduleFlag}" />
<s:param name="id" value="%{id}" />
</s:url>
<div style="overflow: scroll; height: 430px;">

			<sjg:grid 
				 

				   id="gridedittable11"
		          href="%{viewVisit1}"
		          gridModel="leaveVisitlist"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="true"
		          navigatorEdit="true"
		          navigatorSearch="true"
		          rowList="15,30,45,60"
		           loadonce="true"
                   rownumbers="-1"
                   viewrecords="true"       
		          multiselect="true"  
		          loadingText="Requesting Data..."  
		          rowNum="15"
		          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {reloadPage();}}"
		          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {reloadPage();}}"
		          navigatorSearchOptions="{sopt:['eq','cn']}"
		          editurl="%{viewModifyGroup}"
		          shrinkToFit="false"
		          autowidth="true"
		          pager="true"
		          rownumbers="true"
		          onSelectRowTopics="rowselect"
		    
        
		          >

				<s:iterator value="viewPageColumns" id="masterViewProp" > 
				
				<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					formatter="%{formatter}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					formatter="%{formatter}"
					/>
				</s:else>
				
				</s:iterator>  
		</sjg:grid>
		</div>
		
</body>
</html>