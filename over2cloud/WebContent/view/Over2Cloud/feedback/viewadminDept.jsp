<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
         
        });


$.subscribe('getselectedids', function(event,data) {
	var s;
	s = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	alert('Selected Rows : '+s);
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',s,'id');
	alert(">>>>>>>>>>>>>>>>>>>>>>"+ticketNo);

	$("#Result1").load("view/Over2Cloud/feedback/beforeDepartment?idList="+ticketNo);
    return true;
	});
	
</script>

<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
</head>
<body>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="list-icon"><div class="clear"></div><div class="head"><s:property value="%{headerName}"/></div></div>
<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">
<!--<div class="page_title"><h1><s:property value="%{headerName}"/> </h1></div>
-->
<!-- Dept Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewDeptData" action="viewDeptData1" />
<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->

<!-- Dept Data Modification URL CALLING Part Starts HERE -->
<!-- Level 1 to 5 Data Modification URL CALLING Part ENDS HERE -->
<div class="clear"></div>
<div class="rightHeaderButton">
<span class="normalDropDown"> 

 </span>
 </div>
 
 <div class="clear"></div>
 <div class="border" style="overflow:auto; height:380px;">
<div class="listviewBorder"><div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 

<sj:a id="gridUser_editbutton" cssClass="button" cssStyle="button" onclick="pageisNotAvibale();" button="true" >Edit</sj:a>
<sj:a id="gridUser_deleteButton" cssClass="button" cssStyle="button" onclick="pageisNotAvibale();" button="true" >Delete</sj:a>

</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a></td>   


</tr></tbody></table></div></div>
<sjg:grid 
		  id="gridedittable"
          href="%{viewDeptData}"
          gridModel="deptDataViewShow"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="20,30,40"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          rowNum="20"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="true"
         
          >
          
          
		<s:iterator value="level1ColmnNames" id="level1ColmnNames" > 
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>
</div><br><br>
<div class="type-button">
       <center><sj:submit 
         	targets="Result1" 
     		clearForm="true"
            value="  Add  " 
            effect="highlight"
            effectOptions="{ color : '#222222'}"
            effectDuration="5000"
            button="true"
            onClickTopics="getselectedids"
            cssClass="submit"
            indicator="indicator2"
          /></center>
          
          <sj:div id="foldeffect1" >
                    <div id="Result1"></div>
               </sj:div>
 </div>
</div>
</div>
</body>
</html>