<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>



	<s:url id="viewReferral" action="viewReferralGridData" escapeAmp="false">
	    <s:param name="referralStatus" value="%{referralStatus}"></s:param>
	    <s:param name="fromdate" value="%{fromdate}"></s:param>
	    <s:param name="todate" value="%{todate}"></s:param>
	    <s:param name="from_source" value="%{from_source}"></s:param>
	    <s:param name="searchParameter" value="%{searchParameter}"></s:param>
	    
	      <s:param name="data_status" value="%{data_status}"></s:param>
	    <s:param name="searchField" value="%{searchField}"></s:param>
		<s:param name="searchString" value="%{searchString}"></s:param>
		<s:param name="searchOper" value="%{searchOper}"></s:param>
	</s:url>

<s:url id="modifyReferral" action="viewModifyReferral" />
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
			  id="gridBasicDetailsReferral"
	          href="%{viewReferral}"
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
	          editurl="%{modifyReferral}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect1"
          	>
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
		</div>-->