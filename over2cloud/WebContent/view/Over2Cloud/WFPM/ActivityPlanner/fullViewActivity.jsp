<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<body>
<s:url id="activity_view" action="viewActivityFull" escapeAmp="false">
<s:param name="fdate" value="%{fdate}"></s:param>
<s:param name="tdate" value="%{tdate}"></s:param>
<s:param name="country" value="%{country}"></s:param>
<s:param name="state" value="%{state}"></s:param>
<s:param name="city" value="%{city}"></s:param>
<s:param name="territory" value="%{territory}"></s:param>
<s:param name="type" value="%{type}"></s:param>
<s:param name="subtype" value="%{subtype}"></s:param>
<s:param name="emp" value="%{emp}"></s:param>
<s:param name="month" value="%{month}"></s:param>
</s:url>

<sjg:grid 
		            id="acti"
          			href="%{activity_view}"
         			gridModel="masterViewList"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="false"
          			navigatorSearch="false"
          			rowList="100,200,500"
          			rownumbers="-1"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="true"  
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
          				<s:iterator value="masterViewProp" id="masterViewProp" > 
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
									formatter="%{formatter}"
									edittype="select"
									editoptions="{value:'active:Active;Inactive:Inactive'}"
									/>
							
							</s:if>
							<s:else>
								<sjg:gridColumn 
								name="%{colomnName}"
								index="%{colomnName}"
								title="%{headingName}"
								width="%{width}"
								formatter="%{formatter}"
								align="%{align}"
								editable="%{editable}"
								search="%{search}"
								hidden="%{hideOrShow}"
								/>
							</s:else>
					</s:iterator>
 					</sjg:grid> 
</body>
</html>