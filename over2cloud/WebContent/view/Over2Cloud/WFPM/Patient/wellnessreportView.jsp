<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:url id="viewWellnessReportDataGrid" action="viewWellnessReportDataGrid" escapeAmp="false">
<s:param name="isExistingClient" value="%{isExistingClient}" />
<s:param name="from_date" value="%{from_date}" />
<s:param name="to_date" value="%{to_date}" />
<s:param name="uh_id" value="%{uh_id}" />
<s:param name="mobile" value="%{mobile}" />
<s:param name="searchParameter" value="%{searchParameter}" />
<s:param name="patient_name" value="%{patient_name}" />
<s:param name="status" value="%{status}" />

</s:url>
<s:url id="modifyClient" action="viewModifyClient" />
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewWellnessReportDataGrid}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="false"
          navigatorRefresh="true"
          navigatorSearch="false"
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