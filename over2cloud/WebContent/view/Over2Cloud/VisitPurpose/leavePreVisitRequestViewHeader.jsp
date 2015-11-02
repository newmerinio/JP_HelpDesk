<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/visit/visitrequest.js"/>"></script>
<SCRIPT type="text/javascript">

var row=0;
$.subscribe('rowselect', function(event, data) {

	row = event.originalEvent.id;


});	
function gridActivityData()
{
	
	
	
	}



function historyFullView(cellvalue, options, rowObject) 
{

	return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewHistoryOnClick(id) 
{
	
	
	var module = jQuery("#gridedittable11").jqGrid('getCell',id,'moduleName');

	$("#takeActionGrid1").dialog({title: ' History for Module '+module,width: 400,height: 400});
	$("#takeActionGrid1").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid1").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/leaveManagement/HistoryViewpage.action?ID="+id,
		success : function(data)
		{
			$("#takeActionGrid1").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function clientFullView(cellvalue, options, rowObject) 
{

	return "<a href='#' title='View Data' onClick='clientHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function clientHistoryOnClick(id) 
{
	
	var module = jQuery("#gridedittable11").jqGrid('getCell',id,'moduleName');
	
	$("#takeActionGrid2").dialog({title: ' History for Module '+module,width: 400,height: 400});
	$("#takeActionGrid2").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid2").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/leaveManagement/beforeHistoryViewpage.action?ID="+id,
		success : function(data)
		{
			$("#takeActionGrid2").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function requestedByFullView(cellvalue, options, rowObject) 
{

	return "<a href='#' title='View Data' onClick='requestedByOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function requestedByOnClick(id) 
{
	
	var module = jQuery("#gridedittable11").jqGrid('getCell',id,'moduleName');
	
	$("#takeActionGrid3").dialog({title: ' History for Module '+module,width: 400,height: 400});
	$("#takeActionGrid3").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid3").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/DAROver2Cloud/beforeHistoryViewpage.action?idH="+id,
		success : function(data)
		{
			$("#takeActionGrid3").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function loadGrid1()
{

	//$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	
	$.ajax({
	    type : "post",
      url : "view/Over2Cloud/leaveManagement/leavePreVisitRequestViewGrid.action ",
	    success : function(subdeptdata) {
       $("#result_partss").html(subdeptdata);

	},
	
	   error: function() {
            alert("error");
        }
	 });
}


loadGrid1();

</SCRIPT>
</head>
<body>

<div class="clear"></div>

<sj:dialog
          id="takeActionGrid1"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"

          >
</sj:dialog>
<div class="clear"></div>

<sj:dialog
          id="takeActionGrid2"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="client"
          modal="true"
          closeTopics="closeEffectDialog"
        
   >
</sj:dialog>
<div class="clear"></div>
<sj:dialog
          id="takeActionGrid3"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="request"
          modal="true"
          closeTopics="closeEffectDialog"
        
  >
</sj:dialog>

<div class="list-icon">
	 <div class="head">Pre Visit Request </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"><s:iterator value="totalCount">
	<s:property value="%{key}"/>: <s:property value="%{value}"/>   
</s:iterator>
</div> 
</div> 
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
					     <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>	
						     <sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
					          Visit on : <sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="date" name="tdate" size="20" maxDate="0" onchange="onChangeDate();forCounter(); " value="today" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/> 
			                <s:select  
                              id                    =        "relationship"
                             name                =        "relationship"
                             list                      =        "#{'Relationship':'RelationShip Type','Prospective Client':'Prospective Client ','Existing Client':'Existing Client'}"
                             theme                =        "simple"
                            cssClass            =        "button"
                            cssStyle            =        "height: 28px;width: 150px;"
                           onchange="gridActivityData();"
                           >
                          </s:select>
                          <s:select  
                              id                    =        "purpose"
                               name                =        "purpose"
                               headerKey      =            "All"
                               headerValue=             "All Purpose"
               
                             list                      =        "officeMap"
                             theme                =        "simple"
                            cssClass            =        "button"
                            cssStyle            =        "height: 28px;width: 150px;"
                           onchange="gridActivityData();"
                           >
                          </s:select>
                              <s:select  
                              id                    =        "requested_by"
                             name                =        "requested_by"
                              headerKey      =            "Employee"
                           headerValue   =             "All Employee"
                              list                      =        "officeMap1"
                             theme                =        "simple"
                            cssClass            =        "button"
                            cssStyle            =        "height: 28px;width: 150px;"
                           onchange           =      "gridActivityData();"
                           >
                          </s:select>
       
                          
                </td>
                </tr>
                </tbody>
			</table>
			 </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
       
            <sj:a id="sendButton111Download" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadAction();"></sj:a>
            <sj:a  button="true" cssStyle="height:25px; width:32px"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClickTopics="getPrintData"></sj:a>
            <sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
           <sj:a id="action" cssClass="button" button="true" title="Action" onClick="complianceAction();">Action</sj:a>
            <sj:a id="expense" cssClass="button" button="true" title="expense" onClick="expense();">Expenses</sj:a>
            <sj:a cssClass="btn createNewBtnFeedbck" buttonIcon="ui-icon-plus" button="true" onclick="openPreVisitRequestAddForm();" cssClass="button" title="Add">Add</sj:a>
		

				  
				  </td>
			</tr>
		
		    <tr>
        
        <td> 
            <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="alignright printTitle"> 
        					          Requested on : <sj:datepicker cssStyle="height: 16px; width: 78px;" cssClass="button" id="date1" name="rdate" size="20" maxDate="0" onchange="onChangeDate();forCounter(); " value="today" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Update Date"/> 

                       <td class="alignright printTitle">
                         <s:textfield 
                           id="searching" 
                             name="searching" 
                             onclick="gridActivityData();" 
                             theme="simple" cssClass="button" 
                             cssStyle="margin-left:-7px;margin-top: 5px;height: 15px;width:70px;"
                             Placeholder="Client Name" onchange="getSearchData(this.value)"/>
        </td>
             </tr>
         </tbody>
        </table>
        </tr>
    
		
		</tbody>
	</table>
</div>
</div>
<div id="result_partss"></div>
		</div></div>
</body>
</html>