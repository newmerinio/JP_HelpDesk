<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url id="viewPatientBasicId" action="viewPatientBasicGrid" escapeAmp="false">

<s:param name="sent_questionair" value="%{sent_questionair}" />
<s:param name="searchField" value="%{searchField}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="searchOper" value="%{searchOper}"></s:param>
<s:param name="searchParameter" value="%{searchParameter}"></s:param>
<s:param name="gender" value="%{gender}"></s:param>
<s:param name="patient_category" value="%{patient_category}"></s:param>

<s:param name="type" value="%{type}"></s:param>
<s:param name="patient_type" value="%{patient_type}"></s:param>

<s:param name="status_patient" value="%{status_patient}"></s:param>
</s:url>
<s:url id="modifyPatient" action="modifyPatientGrid" />
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewPatientBasicId}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="true"
          navigatorSearch="false"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          filter="true"
          filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyPatient}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          footerrow = "false"
          userDataOnFooter="true"
          >
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