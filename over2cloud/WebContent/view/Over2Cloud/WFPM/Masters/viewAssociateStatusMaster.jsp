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
function associateHyperlink(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openAssociateCatView("+row.id+")'>" + cellvalue + "</a>";
}
function openAssociateCatView(id)
{
	$("#categoryOfferingDiv").html("");
	$("#categoryOfferingDiv").html("<br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#categoryOfferingDiv").load("view/Over2Cloud/wfpm/masters/beforeFetchAssociateCatCost.action?id="+id);
}

function addClientStatus()
{
	var table = $("#tablename").val();
	var urls;
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(table == "client_status")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_client_status_master&mapSubTable=client_status_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	
	else if(table == "weightagemaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_weightage_master&mapSubTable=weightage_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "forcastcategarymaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_forcastcategary_master&mapSubTable=forcastcategary_master_configuration&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "salesstagemaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_salesstage_master&mapSubTable=salesstage_master_configuration&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "associatestatus")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_associate_status_master&mapSubTable=associate_status_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "associatetype")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_associate_type_master&mapSubTable=associate_type_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "associatecategory")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_associate_category_master&mapSubTable=associate_category_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "sourcemaster")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "lostoportunity")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_lost_master&mapSubTable=lost_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "email_configuration_data")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_email_configuration&mapSubTable=email_configuration&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "location")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_location_master&mapSubTable=location_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	else if(table == "sourcemaster")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusAdd.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=${mainHeaderName}&dataTable="+table;
	}
	$.ajax({
	    type : "post",
	    url : encodeURI(urls),
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function commSearch(){
	var status=$("#status").val();
	var table = $("#tablename").val();
	var urls;
	if(table == "client_status")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_client_status_master&mapSubTable=client_status_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	
	else if(table == "weightagemaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_weightage_master&mapSubTable=weightage_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "forcastcategarymaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_forcastcategary_master&mapSubTable=forcastcategary_master_configuration&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "salesstagemaster")
	{
      urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_salesstage_master&mapSubTable=salesstage_master_configuration&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "associatestatus")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_status_master&mapSubTable=associate_status_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "associatetype")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_type_master&mapSubTable=associate_type_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "associatecategory")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_associate_category_master&mapSubTable=associate_category_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "sourcemaster")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "lostoportunity")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_lost_master&mapSubTable=lost_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "email_configuration_data")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_email_configuration&mapSubTable=email_configuration&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "location")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_location_master&mapSubTable=location_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	else if(table == "sourcemaster")
	{
		urls = "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable=mapped_source_master&mapSubTable=source_master&moduleName=${mainHeaderName}&dataTable="+table+"&status="+status;
	}
	//var searchParameter=$("#searchParameter").val();
//		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : encodeURI(urls),
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
		        alert("error");
		    }
		 });
  }


function formatImage(cellvalue, options, row) 
{
	 var context_path = '<%=request.getContextPath()%>';
	 cellvalue="Search";
	 return "&nbsp&nbsp<a href='#mailandsmsdiv'><img title='Mapped Offering' src='images/WFPM/fullView.jpg' onClick='openAssociateCatView("+row.id+");' /></a>";
}

</script>
<script type="text/javascript">
function reloadPage()
	{ 

     var mapped_master_table=$("#mappingTableReload").val();
     var master_configuration_table=$("#mapSubTableReload").val();
     var moduleName=$("#moduleName").val();
     var table=$("#tablename").val();
        $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/wfpm/masters/beforeAssociateStatusView.action?mappingTable="+mapped_master_table+"&mapSubTable="+master_configuration_table+"&moduleName="+moduleName+"&dataTable="+table,
			success : function(subdeptdata) {
			$("#"+"data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	}
</script>

</head>

<body>
	<div class="clear"></div>
	<div class="list-icon">
	          <div class="head">View</div><div class="head"><s:property value="%{mainHeaderName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Total Records: <s:property value="%{countSource}"/>&nbsp&nbsp&nbsp &nbsp<sj:a id="refButtonPage" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 571px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a>
	          </div>
	</div>
	          <div style=" float:left; padding:10px 1%; width:98%;">
		                           <s:url id="viewGrid" action="viewAssociateGrid" escapeAmp="false">
			                              <s:param name="status" value="%{#parameters.status}"/>   
		                                  <s:param name="mappingTable" value="%{mappingTable}"/>   
		                                  <s:param name="mapSubTable" value="%{mapSubTable}"/>   
		                                  <s:param name="moduleName" value="%{moduleName}"/>  
		                                  <s:param name="dataTable" value="%{dataTable}"/>   
		                           </s:url>
		                           <s:url id="viewModifyGrid" action="viewModifyAssociate" escapeAmp="false">
		                                  <s:param name="mappingTable" value="%{mappingTable}"/>   
		                                  <s:param name="mapSubTable" value="%{mapSubTable}"/>   
		                                  <s:param name="moduleName" value="%{moduleName}"/>  
		                                  <s:param name="dataTable" value="%{dataTable}"/>   
		                           </s:url>

		                           <s:url id="viewAssociateSubType" action="viewAssociateSubTypeGrid">
		                           </s:url>
		                           <s:url id="modifyAssociateSubType" action="modifyAssociateSubTypeGrid">
		                           </s:url>
	                               <s:hidden name="tablename" value="%{dataTable}"></s:hidden>
	                               <!-- use for reload page -->
	                               <s:hidden id="mappingTableReload" value="%{mappingTable}"/>
	                               <s:hidden id="mapSubTableReload" value="%{mapSubTable}"/>
	                               <s:hidden id="moduleName" value="%{moduleName}"/>
	                               
		                           <div class="rightHeaderButton">
		                           </div>
                                   <div class="clear"></div>	 	
	 	
                                           <div class="listviewBorder">
                                                     <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
                                                            <div class="pwie">
                                                                       <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
                                                                              <tbody><tr></tr><tr><td></td></tr><tr><td> 
                                                                                                      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
                                                                              <tbody><tr><td class="pL10"> 

<%-- <s:if test="%{(mainHeaderName == 'Lost Opportunity' && #session['userRights'].contains('lost_MODIFY'))
		   || (mainHeaderName == 'Client Status' && #session['userRights'].contains('clst_MODIFY'))
		   || (mainHeaderName == 'Associate Status' && #session['userRights'].contains('asst_MODIFY'))
		   || (mainHeaderName == 'Associate Type' && #session['userRights'].contains('asty_MODIFY'))
		   || (mainHeaderName == 'Associate Category' && #session['userRights'].contains('asca_MODIFY'))
		   || (mainHeaderName == 'Email configuration' && #session['userRights'].contains('emai_MODIFY'))
		   || (mainHeaderName == 'Location Master' && #session['userRights'].contains('loca_MODIFY'))
		   || (mainHeaderName == 'Source Master' && #session['userRights'].contains('sour_MODIFY'))
		   
		   	}">
		   	<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
</s:if>

<s:if test="%{(mainHeaderName == 'Lost Opportunity' && #session['userRights'].contains('lost_DELETE'))
		   || (mainHeaderName == 'Client Status' && #session['userRights'].contains('clst_DELETE'))
		   || (mainHeaderName == 'Associate Status' && #session['userRights'].contains('asst_DELETE'))
		   || (mainHeaderName == 'Associate Type' && #session['userRights'].contains('asty_DELETE'))
		   || (mainHeaderName == 'Associate Category' && #session['userRights'].contains('asca_DELETE'))
		   || (mainHeaderName == 'Email configuration' && #session['userRights'].contains('emai_DELETE'))
		   || (mainHeaderName == 'Location Master' && #session['userRights'].contains('loca_DELETE'))
		   || (mainHeaderName == 'Source Master' && #session['userRights'].contains('sour_DELETE'))
		   	}">
		   	<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
</s:if> --%>

		                                                                     <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
		                                                             	 <s:if test="%{status =='Active'}"> 
		                                                                     <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
		                                                             	</s:if>
		                                                                     <sj:a id="cvDelete" name="cvDelete" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
		                                                                     <sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>   	
																		<s:if test="{dataTable=='sourcemaster' || dataTable=='associatetype' || dataTable=='associatecategory' || dataTable=='client_status' || dataTable=='weightagemaster' || dataTable=='forcastcategarymaster' || dataTable=='salesstagemaster' || dataTable=='associatestatus' || dataTable=='lostoportunity' || dataTable=='location' }">			
																			  <s:select
																				id="status" 
																				name="status" 
																				list="{'Active','Inactive'}"
																				cssStyle="height: 28px; width: 34%;"
																				theme="simple"
																				cssClass="button"
																				onchange="commSearch();"
							  												  />
																		</s:if>
																	          </td></tr></tbody></table>
                                                                              </td>
                                                                              <td class="alignright printTitle">
	                                                                              <s:if test="%{ 'true' || (mainHeaderName == 'Lost Opportunity' && #session['userRights'].contains('lost_ADD'))
		                                                                                                || (mainHeaderName == 'Client Status' && #session['userRights'].contains('clst_ADD'))
		                                                                                                || (mainHeaderName == 'Associate Status' && #session['userRights'].contains('asst_ADD'))
		                                                                                                || (mainHeaderName == 'Associate Type' && #session['userRights'].contains('asty_ADD'))
		                                                                                                || (mainHeaderName == 'Associate Category' && #session['userRights'].contains('asca_ADD'))
		                                                                                                || (mainHeaderName == 'Email configuration' && #session['userRights'].contains('emai_ADD'))
		                                                                                                || (mainHeaderName == 'Location Master' && #session['userRights'].contains('loca_ADD'))
		                                                                                                || (mainHeaderName == 'Source Master' && #session['userRights'].contains('sour_ADD'))
		   	                                                                                   }">
		   	                                                                      </s:if>             
    		                                                                      <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" 
    			                                                                                                                            onclick="addClientStatus();">Add</sj:a>
                                                                                  
	                                                                               <!--<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
                                                                                   --></td> 
                                                                                  </tr></tbody></table></div></div></div>

		<div style="overflow: scroll; height:450px;">
		         <s:if test="%{dataTable == 'lostoportunity'}">  	
	 	               <sjg:grid 
		                        id="gridedittable"
                                href="%{viewGrid}"
                                gridModel="masterViewList"
                                groupField="['lostOpportunityFor']"
    	                        groupColumnShow="[false]"
    	                        groupCollapse="true"
    	                        groupText="['<b>{0}: {1}</b>']"
                                navigator="true"
                                caption="Lost Opportunity"
                                navigatorAdd="false"
                                navigatorView="true"
                                navigatorDelete="true"
                                navigatorEdit="true"
                                navigatorSearch="true"
                                rowList="25,500,1000"
                                rownumbers="-1"
                                viewrecords="true"       
                                pager="true"
                                pagerButtons="true"
                                pagerInput="false"   
                                multiselect="false"  
                                loadingText="Requesting Data..."  
                                rowNum="500"
                                navigatorEditOptions="{height:230,width:400}"
                                navigatorSearchOptions="{sopt:['eq','cn']}"
                                editurl="%{viewModifyGrid}"
                                navigatorEditOptions="{reloadAfterSubmit:true}"
                                shrinkToFit="false"
                                autowidth="true"
                                onSelectRowTopics="rowselect"
                                >
          
	         <s:if test="isAssociateSubTypeExists">
	          <sjg:grid 
					  id="gridAssociateSubTypeDetails"
			          caption="%{mainHeaderNameForAssociateSubType}"
			          href="%{viewAssociateSubType}"
			          gridModel="masterViewListForAssociateSubType"
			          navigator="true"
			          navigatorAdd="false"
			          navigatorView="true"
			          navigatorDelete="true"
			          navigatorEdit="true"
			          navigatorSearch="true"
			          rowList="25,500,1000"
			          rownumbers="-1"
			          viewrecords="true"       
			          pager="true"
			          pagerButtons="true"
			          pagerInput="false"   
			          multiselect="false"  
			          loadingText="Requesting Data..."  
			          rowNum="500"
			          navigatorEditOptions="{height:230,width:400}"
			          navigatorSearchOptions="{sopt:['eq','cn']}"
			          editurl="%{modifyAssociateSubType}"
			          navigatorEditOptions="{reloadAfterSubmit:true}"
			          shrinkToFit="false"
			          autowidth="true"
			          >
			          
	          
			 <s:iterator value="masterViewPropForAssociateSubType" id="masterViewPropForAssociateSubType" >  
				<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
				</s:iterator>
			 </sjg:grid>
	     </s:if>
          
          
          
         <s:if test="%{dataTable == 'associatecategory'}">
              <sjg:gridColumn 
                      name="imagename"
                      name="imagename"
     		          title="Actions"
     		          editable="false"
     		          sortable="false"
    		          align="center"
    		          width="50"
    		          formatter="formatImage"
    		          searchoptions="{sopt:['eq','cn']}"
    		 />
   		 </s:if>
		 <s:iterator value="masterViewProp" id="masterViewProp" >  
		  	<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
			</s:iterator>  
		</sjg:grid>
		</s:if>      <!-- else part grid start here -->
        <s:else>
	 	<sjg:grid 
		  id="gridedittable"
          href="%{viewGrid}"
          gridModel="masterViewList"
          caption="%{mainHeaderNameForAssociateType}"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="25,500,1000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="500"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
          
	          <s:if test="isAssociateSubTypeExists">
	          <sjg:grid 
					  id="gridAssociateSubTypeDetails"
			          caption="%{mainHeaderNameForAssociateSubType}"
			          href="%{viewAssociateSubType}"
			          gridModel="masterViewListForAssociateSubType"
			          navigator="true"
			          navigatorAdd="false"
			          navigatorView="true"
			          navigatorDelete="true"
			          navigatorEdit="true"
			          navigatorSearch="true"
			          rowList="25,500,1000"
			          rownumbers="-1"
			          viewrecords="true"       
			          pager="true"
			          pagerButtons="true"
			          pagerInput="false"   
			          multiselect="false"  
			          loadingText="Requesting Data..."  
			          rowNum="500"
			          navigatorEditOptions="{height:230,width:400}"
			          navigatorSearchOptions="{sopt:['eq','cn']}"
			          editurl="%{modifyAssociateSubType}"
			          navigatorEditOptions="{reloadAfterSubmit:true}"
			          shrinkToFit="false"
			          autowidth="true"
			          >
			          
	          
				<s:iterator value="masterViewPropForAssociateSubType" id="masterViewPropForAssociateSubType" >  
			<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
					</s:iterator>  
				</sjg:grid>
	         </s:if>
          
          
          
         <s:if test="%{dataTable == 'associatecategory'}">
         <sjg:gridColumn 
            name="imagename"
            name="imagename"
     		title="Actions"
     		editable="false"
     		sortable="false"
    		align="center"
    		width="50"
    		formatter="formatImage"
    		searchoptions="{sopt:['eq','cn']}"
    		/>
   		</s:if>
   		
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
		</s:iterator>  
		</sjg:grid>
        </s:else>      <!-- else END part -->
		<br><br>
		    <div id="categoryOfferingDiv">
		    </div>
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
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:true});
	}
	
	function deleteRow()
	{
		row = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
	function searchData()
	{
		jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	/* function refreshRow()
	{
		$("#gridedittable").jqGrid('refGrid',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	} */
	
	function refreshRow(rowid, result) {
		  $("#gridedittable").trigger("reloadGrid"); 
		}
	
</script>
</html>