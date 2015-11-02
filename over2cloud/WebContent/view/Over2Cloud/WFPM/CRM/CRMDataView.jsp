<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="s"  uri="/struts-tags"%>

<s:url id="viewCRMAction" action="viewCRMData"  escapeAmp="false">
	<s:param name="entityType" value="%{entityType}" />
	<s:param name="relationshipSubType" value="%{relationshipSubType}" />
	<s:param name="industry" value="%{industry}" />
	<s:param name="subIndustry" value="%{subIndustry}" />
	<s:param name="rating" value="%{rating}" />
	<s:param name="source" value="%{source}" />
	<s:param name="location" value="%{location}" />
	<s:param name="department" value="%{department}" />
	<s:param name="designation" value="%{designation}" />
	<s:param name="influence" value="%{influence}" />
	<s:param name="chkBirthday" value="%{chkBirthday}" />
	<s:param name="birthdayFrom" value="%{birthdayFrom}" />
	<s:param name="chkAnniversary" value="%{chkAnniversary}" />
	<s:param name="anniversaryFrom" value="%{anniversaryFrom}" />
	<s:param name="age" value="%{age}" />
	<s:param name="sex" value="%{sex}" />
	<s:param name="allergic_to" value="%{allergic_to}" />
	<s:param name="blood_group" value="%{blood_group}" />
	<s:param name="count" value="%{count}" />
</s:url>
<div style="overflow: scroll; height: 450px;">
<sjg:grid 
		  id="CRMview"
          caption="Records: %{count}"
          href="%{viewCRMAction}"
          gridModel="crmDataList"
          navigator="false"
          navigatorAdd="false"
          rowList="10,100,500,1000,1500,2000,2500"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="%{showMultiselect}"  
          loadingText="Requesting Data..."  
          rowNum="10"
          shrinkToFit="false"
          width="1320"
          >
	<sjg:gridColumn name="id" title="Id" editable="false" sortable="false" index="id"
		align="center" width="150" hidden="true"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="name" title="Name" editable="false" index="name"
		sortable="false" align="center" width="220"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="comName" title="Com. Name" editable="false" index="comName"
		sortable="false" align="center" width="100"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="influence" title="Influence" editable="false" index="influence"
		sortable="false" align="center" width="50"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="mobile" title="Mobile" editable="false" index="mobile"
		sortable="false" align="center" width="100"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="email" title="Email" editable="false" index="email"
		sortable="false" align="center" width="200"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="department" title="Department" editable="false" index="department"
		sortable="false" align="center" width="130"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="designation" title="Designation" editable="false" index="designation"
		sortable="false" align="center" width="130"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="organization" title="Organization" editable="false" index="organization"
		sortable="false" align="center" width="130"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="star" title="Star" editable="false" index="star"
		sortable="false" align="center" width="50"
		searchoptions="{sopt:['eq','cn']}" />
	<sjg:gridColumn name="industry" title="Industry" editable="false" index="industry"
		sortable="false" align="center" width="100"
		searchoptions="{sopt:['eq','cn']}" />
		
</sjg:grid>
</div>