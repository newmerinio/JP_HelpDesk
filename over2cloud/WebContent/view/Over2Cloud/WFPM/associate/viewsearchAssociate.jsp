<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>



	<s:url id="viewAssociate" action="viewAssociateGrid" escapeAmp="false">
		<s:param name="isExistingAssociate" value="%{isExistingAssociate}"></s:param>
		<s:param name="associateStatus" value="%{associateStatus}"></s:param>
		<s:param name="industry" value="%{industry}"></s:param>
		<s:param name="subIndustry" value="%{subIndustry}"></s:param>
		<s:param name="starRating" value="%{starRating}"></s:param>
		<s:param name="sourceName" value="%{sourceName}"></s:param>
		<s:param name="location" value="%{location}"></s:param>
		<s:param name="associateCategory" value="%{associateCategory}"></s:param>
		<s:param name="associateType" value="%{associateType}"></s:param>
		<s:param name="associate_Name" value="%{associate_Name}"></s:param>
		<s:param name="searchField" value="%{searchField}"></s:param>
		<s:param name="searchString" value="%{searchString}"></s:param>
		<s:param name="searchOper" value="%{searchOper}"></s:param>
	</s:url>
	
	<s:url id="modifyAssociate" action="viewModifyAssociate" />
	<s:url id="viewAssociateContact" action="viewAssociateContactGrid" />
	<s:url id="modifyAssociateContact" action="viewModifyAssociateContact" />
		

<div style="overflow: scroll; height: 450px;">
<sjg:grid 
			  id="gridBasicDetails3"
	          href="%{viewAssociate}"
	          gridModel="masterViewList"
	          navigator="true"
	          navigatorEdit="true"
	          navigatorAdd="false"
	          navigatorView="false"
	          navigatorDelete="true"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          rowList="10,15,20,25"
	          viewrecords="true"  
	          pager="true" 
	          pagerButtons="true" 
	          pagerInput="false"   
	          multiselect="false"  
	          loadingText="Requesting Data..."  
	          rownumbers="true"
	          rowNum="15"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
	          editurl="%{modifyAssociate}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
          	>
          
	          <sjg:grid 
				  id="gridContactDetails1"
		          caption="%{mainHeaderName}"
		          href="%{viewAssociateContact}"
		          gridModel="masterViewListForContact"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="%{deleteFlag}"
		          navigatorEdit="%{modifyFlag}"
		          navigatorSearch="false"
		          rowList="10,15,20,25"
		          viewrecords="true"       
		          multiselect="false"  
		          loadingText="Requesting Data..."  
		          rowNum="10"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
		          editurl="%{modifyAssociateContact}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
	          >
	          
	          <%--<sjg:gridColumn 
	            name="imagename"
	            name="imagename"
	     		title="Actions"
	     		editable="false"
	     		sortable="false"
	    		align="center"
	    		width="50"
	    		formatter="contactTakeactionLink"
	    		searchoptions="{sopt:['eq','cn']}"
	    		/>
				--%>
				
				<s:iterator value="masterViewPropForContact" id="masterViewPropForContact" >  
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
		     
		     <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Actions"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="70"
    		formatter="formatImage"
    		searchoptions="{sopt:['eq','cn']}"
    		/>     
			<s:iterator value="masterViewProp" id="masterViewProp" >  
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