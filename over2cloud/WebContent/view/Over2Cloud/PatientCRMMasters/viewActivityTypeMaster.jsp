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
<s:url id="viewActivityTypeMasterData" action="viewActivityTypeMasterData" > </s:url>
<s:url id="viewActivityReasonData" action="viewActivityReasonData" ></s:url>
<s:url id="editActivityTypeMasterDataGrid" action="editActivityTypeMasterDataGrid" />

<sjg:grid 
		  id="gridActivityTypeMaster"
          href="%{viewActivityTypeMasterData}"
          gridModel="viewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          rownumbers="-1"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editActivityTypeMasterDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
           <sjg:grid 
								          id="gridActvityReason"
								          href="%{viewActivityReasonData}"
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
								          editurl="%{viewModifyReason}"
								          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
								          shrinkToFit="false"
								          autowidth="true"
								          loadonce="true"
								          onSelectRowTopics="rowselect"
								          onEditInlineSuccessTopics="oneditsuccess"
								          caption="Activity Reason"
								          >
											<s:iterator value="masterViewProp2" id="masterViewActReason" >  
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
					/>
				</s:else>
			</s:iterator>  
				</sjg:grid>
									
          
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
						edittype="select"
						editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
					/>
				</s:else>
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>