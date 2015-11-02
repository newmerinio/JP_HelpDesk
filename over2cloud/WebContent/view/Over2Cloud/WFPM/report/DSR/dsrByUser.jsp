<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<body>
DSR BY USER
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="currentmonth_url" action="currentmonth"></s:url>
<s:url id="dsrRecord1" action="dSRByUser" >
	<s:param name="id" value="id"></s:param>
</s:url>
	<sjg:grid
			id="gridedittable1"
			caption="%{month}" 
			dataType="json"
			href="%{dsrRecord1}" 
			pager="true" 
			navigator="true" 
			navigatorSearch="true"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="false"
			gridModel="gridModelList"
			rowList="10,15,20,25,30" 
			rowNum="10" 
			multiselect="true"
			draggable="true" 
			loadingText="Requesting Data..." 
			shrinkToFit="false"
			width="850"
			footerrow="true"
			userDataOnFooter="true"
			>

			<sjg:gridColumn
					name="id" index="id" title="ID" formatter="integer" editable="true" edittype="text" sortable="true" 
					search="false" hidden="true" />
    		<sjg:gridColumn 
					name="kpiname" index="kpiname" title="KPI Name" editable="true" edittype="text" sortable="true" search="false" width="120"
					 align="left"/>
    		<sjg:gridColumn 
					name="targetvalue" index="targetvalue" title="Total Target" editable="true" edittype="text" sortable="true" search="false" align="center" width="120"/>
    		<sjg:gridColumn 
    				name="totalsale" index="totalsale" title="Achievement Target" editable="true" edittype="text" sortable="true" align="center" search="false"  width="120"/>
    		<sjg:gridColumn 
    				name="remainng_target" frozen="true" index="remainng_target" title="Remaining Target" align="center" width="120"
    				editable="true" edittype="text" sortable="true" search="false" />
    	
    		<s:iterator value="listfistweekdays">
				<sjg:gridColumn name="%{fistweekdatename}" index="%{fistweekdatename}" title="%{fistweekdays}" sortable="true" align="center"
					editable="true" sortable="true" search="false" width="120"/>
			</s:iterator>
	
			<s:iterator value="listSecondweekdays">
				<sjg:gridColumn name="%{secondweekdatename}" index="%{secondweekdatename}" title="%{secondweekdays}" align="center" width="120"
					editable="true"  search="false" />
			</s:iterator>
	
			<s:iterator value="listThirdweekdays">
				<sjg:gridColumn name="%{thirdweekdatename}" index="%{thirdweekdatename}" title="%{thirdtweekdays}" align="center" width="120"
					editable="true" edittype="text" search="false" />
			</s:iterator>
	
			<s:iterator value="listFourthweekdays">
				<sjg:gridColumn name="%{fourthweekdatename}" index="%{fourthweekdatename}" title="%{fourthweekdays}" align="center" width="120"
					editable="true" edittype="text" search="false" />
			</s:iterator>
		
			<sjg:gridColumn name="currentmonth" index="currentmonth" title="Current Month" editable="true" 
			searchtype="select" searchoptions="{sopt:['eq','ne'],dataUrl: '%{currentmonth_url}'}" edittype="text" sortable="true" 
			searchtype="select" edittype="text"/>
			
	</sjg:grid>
	
</div></div>
</center>
</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready( function() {
		jQuery("#gridedittable1").jqGrid('setGroupHeaders', {
			useColSpanStyle :false,
			groupHeaders : [ {
				startColumnName :'targetvalue',
				numberOfColumns :3,
				titleText :'<em>Target Detail</em>'
			}, {
				startColumnName :'one',
				numberOfColumns :7,
				titleText :'First - Week'
			}, {
				startColumnName :'eight',
				numberOfColumns :7,
				titleText :'<em>Second - Week</em>'
			}, {
				startColumnName :'fifteen',
				numberOfColumns :7,
				titleText :'<em>Third - Week</em>'
			}, {
				startColumnName :'twentytwo',
				numberOfColumns :10,
				titleText :'<em>Fourth - Week</em>'
			}, ]
		});
	});

</script>
</html>