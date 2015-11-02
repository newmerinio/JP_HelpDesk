<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<script type="text/javascript">
totalRecord("totalData");
</script>
<s:url id="viewClient" action="viewClientGrid" escapeAmp="false">
	<s:param name="isExistingClient" value="%{isExistingClient}" />
	<s:param name="clientStatus" value="%{clientStatus}" />
	<s:param name="industry" value="%{industry}"></s:param>
	<s:param name="subIndustry" value="%{subIndustry}"></s:param>
	<s:param name="starRating" value="%{starRating}"></s:param>
	<s:param name="sourceName" value="%{sourceName}"></s:param>
	<s:param name="location" value="%{location}"></s:param>
	<s:param name="client_Name" value="%{client_Name}"></s:param>
	<s:param name="clientstatus" value="%{clientstatus}"></s:param> 
	<s:param name="searchField" value="%{searchField}"></s:param>
	<s:param name="searchString" value="%{searchString}"></s:param>
	<s:param name="searchOper" value="%{searchOper}"></s:param>
	<s:param name="opportunity_name" value="%{opportunity_name}"></s:param>
	<s:param name="acManager" value="%{acManager}"></s:param>
	<s:param name="salesstages" value="%{salesstages}"></s:param>
	<s:param name="forecastCategary" value="%{forecastCategary}"></s:param>
	<s:param name="fromDateSearch" value="%{fromDateSearch}"></s:param>	
	
</s:url>
<s:url id="modifyClient" action="viewModifyClient" />
<s:url id="viewClientContact" action="viewClientContactGrid" />
<s:url id="modifyClientContact" action="viewModifyClientContact" />
<s:hidden id="totalData" value="%{totalRecord}"></s:hidden>
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewClient}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyClient}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          footerrow = "false"
          userDataOnFooter="true"
          >
          
          <sjg:grid 
		  id="gridContactDetails"
          caption="%{mainHeaderName}"
          href="%{viewClientContact}"
          gridModel="masterViewListForContact"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyClientContact}"
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
    		width="30"
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