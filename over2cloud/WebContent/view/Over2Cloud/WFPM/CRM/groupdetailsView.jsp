<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>


<s:url id="viewGroupDetails" action="viewCRMGroupDataGrid" escapeAmp="false"> 
	<s:param name="groupname" value="%{groupname}"></s:param> 
	<s:param name="status" value="%{status}"></s:param> 	
</s:url> 
<s:hidden id="records" value="%{records}"></s:hidden> 
<s:url id="viewModifyGroupDetails" action="viewModifyGroupDetails"/>  
<div style="overflow: scroll; height: 450px;"> 
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewGroupDetails}"
          gridModel="groupdataViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorEdit="true"
          navigatorDelete="false"
          navigatorSearch="true"
          navigatorRefresh="true"
          navigatorSearch="true"
          rowList="15,20,250"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          editurl="%{viewModifyGroupDetails}"
          rowNum="15"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true,height:230,width:400}"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reloadRow();
	      }}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false" 
          autowidth="true"    
          loadonce="false"
          onSelectRowTopics="rowselect"
          >
          
          <s:iterator value="crmgroupViewProp" id="crmgroupViewProp" >  
			<s:if test="%{colomnName=='active_deactive'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{178}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					/>
			</s:if>
		<s:elseif test="%{colomnName=='view_type'}">
		<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{178}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Public:Public;Private:Private'}"
					/>
		
		</s:elseif>	
		<s:elseif test="%{colomnName=='type'}">
		<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{178}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Auto:Auto;Manual:Manual'}"
					/>
		</s:elseif>		
		<s:else>
		<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}" 
			title="%{headingName}"
			width="%{178}"
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