<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<script type="text/javascript">
function viewPartitaion(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getPartitaion("+rowObject.id+")'>View</a>";
}

function getPartitaion(rowId) 
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/viewPartitaion.action?machineId="+rowId,
	    success : function(data) {
			$("#partitaionDialog").dialog('open');
       		$("#partitaionDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function viewSoftwareInventory(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
	return "<a href='#' title='View' onClick='getSoftwareInventory("+rowObject.id+")'>View</a>";
}

function getSoftwareInventory(rowId) 
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/viewSoftwareInventory.action?machineId="+rowId,
	    success : function(data) {
			$("#partitaionDialog").dialog('open');
       		$("#partitaionDiv").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function searchResult(searchField, searchString, searchOper)
{
	
	//alert( searchOper);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewMachineDetails.action",
	    data : "&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	   
	    
}
   
function fillAlphabeticalLinks()
{
	
	var val = "";
	val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'\',\'\',\'\')">All</a>&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\'machine_id\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}
fillAlphabeticalLinks();


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Machine Details</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<div id="alphabeticalLinks"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10"> 
					    
					    <!--   Search data -->
					    <s:iterator value="assetDropMap"  status="status">
					            <s:if test="key=='assettype'"> 
					            
	                              <s:select 
	                              id="%{key}"
	                              list="assetTypeList"
	                              headerKey="-1"
	                              headerValue="Select Asset Type" 
	                              cssClass="button"
	                              onchange="searchResult('id',this.value,'eq');enableField();"
	                              cssStyle="margin-top: -5px;;"
	                               >
	                             </s:select>
					             </s:if>
            			 </s:iterator>
            			 
            			   <s:iterator value="assetDropMap"  status="status">
			             <s:if test="key=='serialno'"> 
                                <s:select 
                                id="%{key}"
                                list="{'No data'}"
                                headerKey="-1"
                                headerValue="Select Code" 
                                cssClass="button"
                                onchange="getDocu('assetDetailsDiv');searchResult('id',this.value,'eq');disableFields('serialno');"
                                cssStyle="margin-top: -30px;margin-right: 1px;margin-left: 146px;"
                                 >
                                </s:select>
			           </s:if>
			            </s:iterator>
			            
			            <s:iterator value="assetDropMap"  status="status">
				           <s:if test="key=='assetname'"> 
                                <s:select 
                                id="%{key}1"
                                list="{'No data'}"
                                headerKey="-1"
                                headerValue="Select Asset Name" 
                                cssClass="button"
                                onchange="getDocu('assetDetailsDiv');searchResult('id',this.value,'eq');disableFields('assetname1');"
                                cssStyle="margin-top: -30px;margin-right: 1px;margin-left: 266px;"

                                 >
                                </s:select>
				           </s:if>
			            </s:iterator>
			            
			             <s:iterator value="assetDropMap"  status="status">
				           <s:if test="key=='mfgserialno'"> 
                               <s:select 
                               id="%{key}"
                               list="{'No data'}"
                               headerKey="-1"
                               headerValue="Select Serial No" 
                               cssClass="button"
                               onchange="getDocu('assetDetailsDiv');searchResult('id',this.value,'eq');disableFields('mfgserialno');"
                               cssStyle="margin-top: -30px;margin-right: 1px;margin-left: 415px;"
                                >
                               </s:select>
                    
				           </s:if>
			           </s:iterator>
						     Search : <s:textfield
			                   name="search" 
			                   id="search" 
			                    onchange="searchResult('Search',this.value,'eq');getDocu('assetDetailsDiv');" 
			                   maxlength="50"  
			                   placeholder="Enter Data"  
			                   cssClass="button"
			                   cssStyle="margin-top: -30px;margin-right: -180px;margin-left: 592px;width: 45px;;"
			                   />
					      
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download"  buttonIcon="ui-icon-arrowstop-1-s" onclick="getCurrentColumn('downloadExcel','totalHarwareDetail','downloadColumnAllModDialog','downloadAllModColumnDiv');" />
				     <!--<%if(userRights.contains("taty_ADD")) {%>-->
				     		   <sj:a id="viewProcessRestriction"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="getViewData('ProcessRestriction');">View Restricted Exe</sj:a>
							   <!--<sj:a id="viewButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="viewNetMonitorLog();">Download</sj:a>
	      			--><!--<%}%>
				  --></td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 400px;">
<s:url id="viewMachine" action="ViewMachineDetails" escapeAmp="false" >
<s:param name="searchField" value="%{searchField}"></s:param>
<s:param name="searchString" value="%{searchString}"></s:param>
<s:param name="searchOper" value="%{searchOper}"></s:param>
</s:url>
<sjg:grid 
		 
          id="gridedittable"
          href="%{viewMachine}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          editinline="false"
          rowList="14,50,100"
          rowNum="14"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			width="165"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		</s:iterator>  
</sjg:grid>
</div>
</div>
</div>
</div>
<sj:dialog 
		              			id="downloadColumnAllModDialog" 
		              			modal="true" 
		              			effect="slide" 
		              			autoOpen="false" 
		              			width="300" 
		              			height="500" 
		              			title="Asset " 
		              			loadingText="Please Wait" 
		              			overlayColor="#fff" 
		              			overlayOpacity="1.0" 
		              			position="['center','center']" >
								<div id="downloadAllModColumnDiv"></div>
								</sj:dialog>
</body>
<sj:dialog
          id="partitaionDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="View Partitaion Data"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
          <div id="partitaionDiv"></div>
</sj:dialog>

</html>