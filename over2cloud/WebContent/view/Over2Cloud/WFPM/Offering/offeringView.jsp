<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<style type="text/css">

</style>
<SCRIPT type="text/javascript">
	function addOffering()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function excelUpload()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingUpload.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function excelUploadNew()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingUploadNew.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


	function offeringSearch()
	{
		var status=$("#status").val();
		//$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/offering/beforeOfferingView.action?modifyFlag=0&deleteFlag=0&status="+status,
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}
	
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">View</div><div class="head">Offering</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Vertical: <s:property value="%{countVertical}"/>,</div><div class="head">Offering: <s:property value="%{countOffering}"/>,</div><div class="head">Sub-Offering: <s:property value="%{countSubOffering}"/></div>  
	</div>
	
	<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
	<s:url id="viewLevel1" action="viewLevel1" namespace="/view/Over2Cloud/wfpm/offering" escapeAmp="false" >
	  <s:param name="status" value="%{#parameters.status}"></s:param>
	</s:url>
	<s:url id="viewLevel2" action="viewLevel2" namespace="/view/Over2Cloud/wfpm/offering" escapeAmp="false" >
	  <s:param name="status" value="%{#parameters.status}"></s:param>
	</s:url>
	<s:url id="viewLevel3" action="viewLevel3" namespace="/view/Over2Cloud/wfpm/offering" escapeAmp="false" >
	  <s:param name="status" value="%{#parameters.status}"></s:param>
	</s:url>
	<s:url id="viewLevel4" action="viewLevel4" />
	<s:url id="viewLevel5" action="viewLevel5" />
	
	<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->
	<!-- Level 1 to 5 Data Modification URL CALLING Part Starts HERE -->
	<s:url id="viewModifyLevel1" action="viewModifyLevel1" />
	<s:url id="viewModifyLevel2" action="viewModifyLevel2" />
	<s:url id="viewModifyLevel3" action="viewModifyLevel3" />
	<s:url id="viewModifyLevel4" action="viewModifyLevel4" />
	<s:url id="viewModifyLevel5" action="viewModifyLevel5" />
	<!-- Level 1 to 5 Data Modification URL CALLING Part ENDS HERE -->
	<div class="rightHeaderButton">
		</div>
	<div class="clear"></div>
	<div style=" float:left; padding:10px 1%; width:98%;">
		<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<s:if test="%{status=='Active'}">
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					</s:if>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
						<s:select
									id="status" 
									name="status" 
									list="{'Active','Inactive'}"
									headerKey="-1"
									headerValue="Select Status" 
									cssStyle="height: 28px; width: 43%;margin-top: 1px;"
									theme="simple"
									cssClass="button"
									onchange="offeringSearch();"
							  />
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
	 <!--<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
     <sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px" title="Upload" buttonIcon="ui-icon-arrowstop-1-n" 
     		onclick="excelUploadNew();" />
	-->
     <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addOffering();">Add</sj:a>
                    <!--<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
				-->
				</td>   
				</tr></tbody></table></div></div> </div>
				<div style="overflow: scroll; height: 450px;">
			<sjg:grid 
			  id="gridedittable"
	          href="%{viewLevel1}"
	          gridModel="level1DataObject"
	          caption="%{offeringLevel1Name}"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          navigatorRefresh="true"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="false"  
	          loadingText="Requesting Data..."  
	          rowNum="5"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{viewModifyLevel1}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          onSelectRowTopics="rowselect"
	          
	          >
	          
		          <s:if test="levelOfOffering>1">
			          <sjg:grid 
					          id="gridedittable1"
					          caption="%{offeringLevel2Name}"
					          href="%{viewLevel2}"
					          gridModel="level1DataObject"
					          navigator="true"
					          navigatorAdd="false"
					          navigatorView="true"
					          navigatorDelete="false"
	          				  navigatorEdit="false"
					          navigatorSearch="true"
					          rowList="15,20,25"
					          rownumbers="-1"
					          viewrecords="true"       
					          pager="true"
					          pagerButtons="true"
					          pagerInput="false"   
					          multiselect="false"  
					          loadingText="Requesting Data..."  
					          rowNum="5"
					          navigatorSearchOptions="{sopt:['eq','cn']}"
					          editurl="%{viewModifyLevel2}"
					          navigatorEditOptions="{reloadAfterSubmit:true}"
					          shrinkToFit="false"
					          autowidth="true"
			          >
			          
				          <s:if test="levelOfOffering>2">
					          <sjg:grid 
					          id="gridedittable2"
					          caption="%{offeringLevel3Name}"
					          href="%{viewLevel3}"
					          gridModel="level1DataObject"
					          navigator="true"
					          navigatorAdd="false"
					          navigatorView="true"
					          navigatorDelete="true"
	         				  navigatorEdit="true"
					          navigatorSearch="true"
					          rowList="10,15,20,25"
					          rownumbers="-1"
					          viewrecords="true"       
					          pager="true"
					          pagerButtons="true"
					          pagerInput="false"   
					          multiselect="false"  
					          loadingText="Requesting Data..."  
					          rowNum="5"
					          navigatorSearchOptions="{sopt:['eq','cn']}"
					          editurl="%{viewModifyLevel3}"
					          navigatorEditOptions="{reloadAfterSubmit:true}"
					          shrinkToFit="false"
					          autowidth="true"
					          >
					          
					          <s:if test="levelOfOffering>3">
						          <sjg:grid 
						          id="gridedittable3"
						          caption="%{offeringLevel4Name}"
						          href="%{viewLevel4}"
						          gridModel="level1DataObject"
						          navigator="true"
						          navigatorAdd="false"
						          navigatorView="true"
						          navigatorDelete="true"
	         					  navigatorEdit="true"
						          navigatorSearch="true"
						          rowList="15,20,25"
						          rownumbers="-1"
						          viewrecords="true"       
						          pager="true"
						          pagerButtons="true"
						          pagerInput="false"   
						          multiselect="false"  
						          loadingText="Requesting Data..."  
						          rowNum="5"
						          navigatorSearchOptions="{sopt:['eq','cn']}"
						          editurl="%{viewModifyLevel4}"
						          navigatorEditOptions="{reloadAfterSubmit:true}"
						          shrinkToFit="false"
						          autowidth="true"
						          >
					          
						          <s:if test="levelOfOffering>4">
							          <sjg:grid 
							          id="gridedittable4"
							          caption="%{offeringLevel5Name}"
							          href="%{viewLevel5}"
							          gridModel="level1DataObject"
							          navigator="true"
							          navigatorAdd="false"
							          navigatorView="true"
							          navigatorDelete="true"
	          						  navigatorEdit="true"
							          navigatorSearch="true"
							          rowList="10,15,20,25"
							          rownumbers="-1"
							          viewrecords="true"       
							          pager="true"
							          pagerButtons="true"
							          pagerInput="false"   
							          multiselect="false"  
							          loadingText="Requesting Data..."  
							          rowNum="5"
							          navigatorSearchOptions="{sopt:['eq','cn']}"
							          editurl="%{viewModifyLevel5}"
							          navigatorEditOptions="{reloadAfterSubmit:true}"
							          shrinkToFit="false"
							          autowidth="true"
							          >
						          
						           	<s:iterator value="level5ColmnNames" id="level5ColmnNames" >  
						            <sjg:gridColumn 
						            	name="%{colomnName}"
						                index="%{colomnName}"
						                title="%{headingName}"
						                width="%{width}"
						                align="%{align}"
						                editable="%{editable}"
						                search="%{search}"
						                hidden="%{hideOrShow}"
						                searchoptions="{sopt:['eq','cn']}"
						                />
										</s:iterator>  
						             </sjg:grid>
						            </s:if>
					                        
					           				<s:iterator value="level4ColmnNames" id="level4ColmnNames" >  
					                          <sjg:gridColumn 
					                            name="%{colomnName}"
					                            index="%{colomnName}"
					                            title="%{headingName}"
					                            width="%{width}"
					                            align="%{align}"
					                            editable="%{editable}"
					                            search="%{search}"
					                            hidden="%{hideOrShow}"
					                            searchoptions="{sopt:['eq','cn']}"
					                            />
					                          </s:iterator>  
					                            
					          	</sjg:grid>
					          </s:if>
					          
					           <s:iterator value="level3ColmnNames" id="level3ColmnNames" >  
					                          <sjg:gridColumn 
					                            name="%{colomnName}"
					                            index="%{colomnName}"
					                            title="%{headingName}"
					                            width="%{width}"
					                            align="%{align}"
					                            editable="%{editable}"
					                            search="%{search}"
					                            hidden="%{hideOrShow}"
					                            searchoptions="{sopt:['eq','cn']}"
					                            />
					                          
					          </s:iterator>  
					          </sjg:grid>
				          </s:if>
			           <s:iterator value="level2ColmnNames" id="level2ColmnNames" >  
			                 <sjg:gridColumn 
				                 name="%{colomnName}"
				                 index="%{colomnName}"
				                 title="%{headingName}"
				                 width="%{width}"
				                 align="%{align}"
				                 editable="%{editable}"
				                 search="%{search}"
				                 hidden="%{hideOrShow}"
				                 searchoptions="{sopt:['eq','cn']}"
			                 />
							</s:iterator>  
			                            
			          </sjg:grid>
			          
		          </s:if>
	          
			<s:iterator value="level1ColmnNames" id="level1ColmnNames" >  
			<sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}" 
			hidden="%{hideOrShow}"
			width="%{width}"
			searchoptions="{sopt:['eq','cn']}"
			/>
			</s:iterator>  
			</sjg:grid>
			</div>
	</div>
</body>
<script type="text/javascript">
	var row=0;
	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});	
	function editRow()
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchRow()
	{
		$("#gridedittable").jqGrid('searchGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function refreshRow()
	{
		$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
</script>
</html>
