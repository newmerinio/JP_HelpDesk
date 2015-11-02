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
<script src="<s:url value="js/lead/LeadCommon.js"/>"></script>
<script type="text/javascript">
function counter(param)
{
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/WFPM/Lead/counter.action?counter="+param,
        success : function(subdeptdata) {
       $("#"+"counterdiv").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });

    
}

</script>
</head>
<body>

<sj:dialog 
    id="leaddialogid" 
    title="Lead History" 
    autoOpen="false" 
    modal="true" 
    height="370" width="700" 
    showEffect="drop">
    
</sj:dialog>
<div id=""></div>
    <div class="clear"></div>
    <div class="list-icon">
    <s:if test="%{param==4}">
    <div id="Lid" class="head">Lost Lead</div><div class="head">
    <img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View</div>
    <!-- <div class="head">Lost Management</div> -->
    </s:if>
    <s:else>
    <!-- <div class="head">Lead Management</div><img alt="" src="images/forward2.png" style="width: 2%; float: left;margin: 8px"><div class="head">View</div> -->
    <div id="Hid" class="head">New Lead</div><div class="head">
    <img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">Total: </div><div id="counterdiv" class= "head"><s:property value="count1"/></div>
    
    <!-- <div class="head">Lead Management</div> -->
    </s:else>
    </div>
    <div style=" float:left; padding:10px 1%; width:98%;">
    <div class="rightHeaderButton">
    <%-- <%if(userRights.contains("leac_ADD")){ %>
        <input class="btn createNewBtn" value="Create New Lead " type="button" onclick="createLead();">
        <span class="normalDropDown"> 
        </span>
    <%} %> --%>
    </div>
    <%-- <div class="leftHeaderButton">
        <s:select id="selectstatus" list="#{'0':'All New Lead','1':'Prosspective Client','2':'Prosspective Associate','3':'Snooze Lead','4':'Lost Lead'}"  cssClass="button" onchange="searchshowleaddata(this.value);"/>
    </div> --%>
     <div class="clear"></div>
     <div>
      <div id="alphabeticalLinks"></div>
     <div class="listviewBorder">
         <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
        <div class="pwie">
        <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
        <tr><td></td></tr>
        <tr><td> 
        <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody><tr>
        <td class="pL10" va>
        <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" 
            onclick="editRow()"></sj:a>
        
        <%-- <sj:a id="delButton" title="Delete" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a> --%>
        <!-- <input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="deleteRow();"> -->
        <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true" 
            onclick="deleteRow()"></sj:a>
        
        <!-- <input name="cvDelete" id="cvDelete" class="button" value="Search" type="button" onclick="searchData();"> -->
        <!--<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" 
            onclick="searchData()"></sj:a>
        
        --><sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  
            onclick="refreshRow()"></sj:a>
        Lead Name: <s:textfield name="leadNmae"  id="leadNmaeWild" theme="simple" onkeyup="searchResult('','','','');count1('','','')" cssClass="textField" placeholder="Enter Data" style="margin: 0px 0px 9px; width: 20%;"/>
        
        </td></tr></tbody></table>
        
        
        <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody><tr>
        <td class="pL10" va>
        <!-- <input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button" onclick="editRow();"> -->
        
        
        <sj:a
            button="true"
            cssClass="button" 
            cssStyle="height: 23px; margin-bottom: 2px; margin-left: 4px;" 
            title="History"
            onclick="historyviewMe();"
            >History</sj:a>  
        <s:select 
            id="selectstatus" 
            list="#{'0':'All New Lead','1':'Prosspective Client','2':'Prosspective Associate','3':'Snooze Lead','4':'Lost Lead'}"  
            onchange="searchshowleaddata(this.value); counter(this.value);" 
            theme="simple"
            cssStyle="height: 26px;"
            cssClass="button"
            />
        <s:select
            id="industryID" 
            name="industry" 
            list="industryList" 
            headerKey="-1"
            headerValue="Select Industry" 
            theme="simple"
            cssStyle="height: 26px;"
            onchange="fillName(this.value,'subIndustryID')"
            cssClass="button"
            />
        <s:select
            id="subIndustryID" 
            name="subIndustry" 
            list="#{'-1':'Select Sub-Industry'}" 
            cssStyle="height: 26px;"
            theme="simple" 
            onchange="searchResult('','','','');"
            cssClass="button"
            />
         <s:select 
            id="starRating" 
            name="starRating"
            list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
            headerKey="-1" 
            headerValue="Select Rating"
            cssStyle="height: 26px;"
            theme="simple" 
            onchange="searchResult('','','','');"
            cssClass="button"
            />
         <s:select
            id="sourceName" 
            name="sourceName" 
            list="sourceList" 
            headerKey="-1"
            headerValue="Select Source" 
            cssStyle="height: 26px;"
            theme="simple"
            onchange="searchResult('','','','');"
            cssClass="button"
            />
        <s:select
            id="locationID" 
            name="location" 
            list="locationList"
            headerKey="-1"
            headerValue="Select Location" 
            cssStyle="height: 26px;"
            theme="simple"
            onchange="searchResult('','','','');"
            cssClass="button"
            />
        <!--<sj:a
            button="true"
            cssStyle="height: 26px;"
            cssClass="button"
            title="OK"
            onchange="searchResult('','','','');"
            >OK</sj:a>
            
        --></td></tr></tbody></table>
        
        </td>
        <td class="alignright printTitle">
    <%--  <input class="btn" id="downloadExceProc" value="Export Excel" type="button"  onclick="getCurrentColumn('downloadExcel','leadDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')">  
     <input class="btn importNewBtn" value="Import Excel" type="button"  onclick="excelUpload();"> 
    --%>
    
    <s:if test="'true' || #session.userRights.contains('leac_ADD')">
    
    <sj:a  button="true"  
                       cssClass="button" 
                       cssStyle="height:25px; width:32px"
                       title="Download"
                       buttonIcon="ui-icon-arrowstop-1-s" 
                       onclick="getCurrentColumn('downloadExcel','leadDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" />
    <sj:a  button="true" 
                           cssClass="button" 
                           cssStyle="height:25px; width:32px"
                           title="Upload"
                           buttonIcon="ui-icon-arrowstop-1-n" 
                           onclick="excelUpload();" />
      
         <sj:a
                            button="true"
                            cssClass="button" 
                            cssStyle="height:25px;"
                            title="Add"
                            buttonIcon="ui-icon-plus"
                            onclick="createLead();"
                            >Add</sj:a>  
    </s:if>
        </td>   
        </tr></tbody></table></div></div>
        
        <s:if test="'true' || #session.userRights.contains('leac_VIEW') || #session.userRights.contains('leac_MODIFY') || #session.userRights.contains('leac_DELETE')">
        <div id="datapart">
        <!-- lead data will come dynamically *******************************************************************-->
        </div>
        </s:if>
     </div>
     </div>
     </div>

</body>
<div id="mailandsmsdiv"></div>
<sj:dialog id="downloadColumnAstDialog" buttons="{'Cancel':function() { },}" modal="true" effect="slide" autoOpen="false" width="300" 
    height="500" title="Lead Generation Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" 
    position="['left','top']" >
<div id="downloadAstColumnDiv"></div>
</sj:dialog>
<sj:dialog modal="true" draggable="true" resizable="true" autoOpen="false"  width="470" showEffect="slide"  hideEffect="explode" height="50" 
    onCloseTopics="modclosed" onBeforeTopics="openingmodel" autoOpen="false" id="lostLeadDialog" formIds="frm" 
    title="Lead >>Lost Lead, Please Submit to Convert This Lead to New Lead." buttons="{  'Submit':function() { lostLeadOkButton(); }, 'Cancel':function() { lostLeadCancelButton1(); } }" ></sj:dialog>
    <sj:dialog 
        id="downloadColumnAllModDialog"  
        modal="true" 
        effect="slide" 
        autoOpen="false" 
        width="300" 
        height="500" 
        title="Lead Column Names To Choose" 
        loadingText="Please Wait" 
        overlayColor="#fff" 
        overlayOpacity="1.0" 
        position="['center']"
        
        >
<div id="downloadAllModColumnDiv"></div>
</sj:dialog>
<sj:dialog
    id="leadFullViewDialog"
    title="Full View"
    modal="true"
    autoOpen="false"
    width="900"
    height="400"    
    ></sj:dialog>

<sj:dialog
        modal="true"
        draggable="true"
        resizable="true"
        autoOpen="false" 
        width="500"
        showEffect="slide" 
        hideEffect="explode"
        height="150"
        autoOpen="false"
        id="leadLostDialog"
        title="Confirmation"
        buttons="{ 
            'Submit':function() { newLeadOkButton(); },
            'Cancel':function() { NewLeadCancelButton1(); } 
            }"
    >
    Click Submit button to convert this Lead to New Lead.
    </sj:dialog>    

<script type="text/javascript">
//var mainHeaderNameTemp='${mainHeaderName}';
var tempHeaderName = '${mainHeaderName}';
var lStatus='${status}';
function leadDataView() {
    $("#selectstatus").val(lStatus);
    searchshowleaddata(lStatus);
}
    

leadDataView();
</script>
</html>