<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
var datePicker = function(elem) {
    $(elem).datepicker({dateFormat:'mm-yy'});
    $('#date_picker_dob').css("z-index", 2000);
    
};
</SCRIPT>

<s:url id="viewAchievementURL" action="viewAchievement" namespace="/view/Over2Cloud/WFPM/report/DSR">
	<s:param name="userName" value="%{userName}"></s:param>
</s:url> 
<s:url id="viewKPIAchievementURL" action="viewKPIAchievement" namespace="/view/Over2Cloud/WFPM/report/DSR">
</s:url> 
<div id="date_picker_dob" style="display:none">
  <sj:datepicker name="dob" id="doba" value="dob" cssStyle="margin:0px 0px 10px 0px"   changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" />
</div>
 
<sjg:grid 
		  id="gridAchievementReport"
          caption="Target Vs. Achievement Report"
          href="%{viewAchievementURL}"
          gridModel="objectKPIReportTotalPojoLISTS"
          navigator="true"
          navigatorSearch="true"
          navigatorAdd="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorRefresh="true"
          rowList="15,25,50,100"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="false"
          >
          
          <sjg:grid 
				  id="gridKPIAchievementReport"
		          caption="KPI Target Vs. Achievement Report"
		          href="%{viewKPIAchievementURL}"
		          gridModel="objectKPIReportKPIPojoLISTS"
		          navigator="true"
		          navigatorSearch="true"
		          navigatorAdd="false"
		          navigatorDelete="false"
                  navigatorEdit="false"
		          rowList="15,25,50,100"
		          navigatorRefresh="true"
		          rownumbers="-1"
		          viewrecords="true"       
		          pager="true"
		          pagerButtons="true"
		          pagerInput="false"   
		          multiselect="true"  
		          loadingText="Requesting Data..."  
		          rowNum="10"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="false"
		          autowidth="true"
		          loadonce="false"
		          >
		          
		  			<sjg:gridColumn 
						name="id"
						index="id"
						title="ID"
						align="center"
						editable="false"
						search="false"
						hidden="true"
					/>
					<sjg:gridColumn 
						name="kpi"
						index="kpi"
						title="KPI"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="targetAlloted"
						index="targetAlloted"
						title="Target Alloted"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="achieved"
						index="achieved"
						title="Achieved"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="achievedPercentage"
						index="achievedPercentage"
						title="Achieved (%)"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="remaining"
						index="remaining"
						title="Remaining"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="remainingPercentage"
						index="remainingPercentage"
						title="Remaining (%)"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="incentive"
						index="incentive"
						title="Incentive"
						align="center"
						editable="false"
						search="false"
						hidden="false"
					/>
					<sjg:gridColumn 
						name="month"
						index="month"
						title="Month"
						align="center"
						editable="false"
						search="true"
						hidden="false"
					/>        
          </sjg:grid>
          
          
          
          
          
          
          
	<sjg:gridColumn 
		name="id"
		index="id"
		title="ID"
		align="center"
		editable="false"
		search="false"
		hidden="true"
	/>
	<sjg:gridColumn 
		name="empName"
		index="empName"
		title="Employee"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="totalTarget"
		index="totalTarget"
		title="Total Target"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="achieved"
		index="achieved"
		title="Achieved"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="achievedPercentage"
		index="achievedPercentage"
		title="Achieved (%)"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="remaining"
		index="remaining"
		title="Remaining"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="remainingPercentage"
		index="remainingPercentage"
		title="Remaining (%)"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="incentive"
		index="incentive"
		title="Incentive"
		align="center"
		editable="false"
		search="false"
		hidden="false"
	/>
	<sjg:gridColumn 
		name="month"
		index="month"
		searchoptions="{sopt:['eq'], dataInit:datePicker}"
		title="Month"
		align="center"
		editable="true"
		search="true"
		hidden="false"
	/>
</sjg:grid>
