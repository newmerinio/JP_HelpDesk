<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
 <div style="overflow: scroll; height: 430px;">
<s:url id="viewCountryData" action="viewCountryData" > </s:url>
 <s:url id="viewStateData" action="viewStateData" > </s:url>
 <s:url id="viewCityData" action="viewCityData"> </s:url>
<s:url id="viewModifyCountry" action="editCountryDataGrid" /> 
<s:url id="viewModifyState" action="viewModifyState" /> 
<s:url id="viewModifyCity" action="viewModifyCity" /> 
<sjg:grid 
		 
          id="gridCountry"
          href="%{viewCountryData}"
          gridModel="viewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          editinline="false"
          rowList="100,200,500"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="true"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{viewModifyCountry}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
          
			    <sjg:grid 
			          id="gridState"
			          href="%{viewStateData}"
			          gridModel="viewList"
			          navigator="false"
			          navigatorAdd="false"
			          navigatorView="true"
			          navigatorDelete="true"
			          navigatorEdit="true"
			          navigatorSearch="true"
			          editinline="false"
			          rowList="100,200,500"
			          rowNum="100"
			          viewrecords="true"       
			          pager="true"
			          pagerButtons="true"
			          rownumbers="true"
			          pagerInput="false"   
			          multiselect="false"
			          navigatorSearchOptions="{sopt:['eq','cn']}"  
			          loadingText="Requesting Data..."  
			          navigatorEditOptions="{height:230,width:400}"
			          editurl="%{viewModifyState}"
			          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
			          shrinkToFit="false"
			          autowidth="true"
			          loadonce="true"
			          onSelectRowTopics="rowselect"
			          onEditInlineSuccessTopics="oneditsuccess"
			          >
          
									<sjg:grid 
								          id="gridCity"
								          href="%{viewCityData}"
								          gridModel="viewList"
								          navigator="false"
								          navigatorAdd="false"
								          navigatorView="true"
								          navigatorDelete="true"
								          navigatorEdit="true"
								          navigatorSearch="true"
								          editinline="false"
								          rowList="100,200,500"
								          rowNum="100"
								          viewrecords="true"       
								          pager="true"
								          pagerButtons="true"
								          rownumbers="true"
								          pagerInput="false"   
								          multiselect="false"
								          navigatorSearchOptions="{sopt:['eq','cn']}"  
								          loadingText="Requesting Data..."  
								          navigatorEditOptions="{height:230,width:400}"
								          editurl="%{viewModifyKRAType}"
								          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
								          shrinkToFit="false"
								          autowidth="true"
								          loadonce="true"
								          onSelectRowTopics="rowselect"
								          onEditInlineSuccessTopics="oneditsuccess"
								          >
											<s:iterator value="masterViewProp3" id="masterViewCityHead" >  
											<sjg:gridColumn 
											name="%{colomnName}"
											index="%{colomnName}"
											title="%{headingName}"
											width="%{width}"
											align="%{align}"
											editable="%{editable}"
											search="%{search}"
											hidden="%{hideOrShow}"
											/>
											</s:iterator>  
								</sjg:grid>
		
						<s:iterator value="masterViewProp2" id="masterViewStateHead" >  
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
						</s:iterator>  
				</sjg:grid>
		
		<s:iterator value="masterViewProp1" id="masterViewCountryHead" >  
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
	</s:iterator>  

</sjg:grid>
</div>

</body>


</html>