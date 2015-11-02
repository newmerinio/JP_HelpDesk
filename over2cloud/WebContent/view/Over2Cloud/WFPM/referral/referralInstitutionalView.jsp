<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>



	<s:url id="viewReferralCor" action="viewReferralCorGridData" escapeAmp="false">
		<s:param name="isExistingAssociate" value="%{isExistingAssociate}"></s:param>
		<s:param name="associateStatus" value="%{associateStatus}"></s:param>
		<s:param name="industry" value="%{industry}"></s:param>
		<s:param name="subIndustry" value="%{subIndustry}"></s:param>
		<s:param name="referralStatus" value="%{referralStatus}"></s:param>
		<s:param name="starRating" value="%{starRating}"></s:param>
		<s:param name="sourceName" value="%{sourceName}"></s:param>
		<s:param name="location" value="%{location}"></s:param>
		<s:param name="associateCategory" value="%{associateCategory}"></s:param>
		<s:param name="associateType" value="%{associateType}"></s:param>
		<s:param name="associate_Name" value="%{associate_Name}"></s:param>
		<s:param name="searchField" value="%{searchField}"></s:param>
		<s:param name="searchString" value="%{searchString}"></s:param>
		<s:param name="searchOper" value="%{searchOper}"></s:param>
		
		<s:param name="fromdate1" value="%{fromdate1}"></s:param>
	    <s:param name="todate1" value="%{todate1}"></s:param>
	    <s:param name="from_source1" value="%{from_source1}"></s:param>
	      <s:param name="searchParameter1" value="%{searchParameter1}"></s:param>
	      <s:param name="data_status" value="%{data_status}"></s:param>
	</s:url>
	
	<s:url id="modifyAssociate" action="viewModifyAssociate" />
	<s:url id="viewReferralCorContact" action="viewReferralCorContact" />
	<s:url id="modifyAssociateContact" action="viewModifyAssociateContact" />
		

<div style="overflow: scroll; height: 450px;">
<sjg:grid 
			  id="gridBasicDetails3"
	          href="%{viewReferralCor}"
	          gridModel="materViewProp"
	          navigator="true"
	          navigatorEdit="false"
	          navigatorAdd="false"
	          navigatorView="false"
	          navigatorDelete="false"
	          navigatorSearch="false"
	          navigatorRefresh="true"
	          rowList="10,15,20,25"
	          viewrecords="true"  
	          pager="true" 
	          pagerButtons="true" 
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rownumbers="false"
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
		          href="%{viewReferralCorContact}"
		          gridModel="masterViewListForContact"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="false"
		          navigatorDelete="false"
		          navigatorRefresh="true"
		          navigatorEdit="false"
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
		     
			<s:iterator value="masterViewProp" id="masterViewProp" >  
			<s:if test="%{colomnName=='data_status'}">
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
					editoptions="{value:'0:Active;1:Inactive'}"
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
		