<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#patientaddition").fadeIn(); }, 10);
          setTimeout(function(){ $("#patientaddition").fadeOut();closeDialog(); }, 4000);
        });

function closeDialog(){
	$("#contactPersonDetailsDialog").dialog('close');
	viewPage();
}

function viewPage()
{
	
	var referralSubType=$("#referralSubType").val();
	var referralStatus=$("#referralStatus").val();
	var tabel=$("#tabel").val();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	if(tabel=='corporate_master')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/WFPM/Patient/viewCorporateHeader.action?data_status=active",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/wfpmReferral/beforeReferralView.action?referralSubType="+referralSubType,
		    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
		            alert("error");
		        }
		 });
	}
}

</script>
</head>
<body>

<s:if test="statusmap.size()>0">
 <table width="100%" border="1" style="border-collapse: collapse;" align="center">
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Requested By'}">
                    <td align="center" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%"> 
                    <s:if test="%{ value == '-1'}">NA</s:if>
                    <s:else> <s:property value="%{value}"/></s:else></td>
            </s:if>
        </s:iterator>
        </tr>
         <tr style="height: 25px">
         <s:iterator value="%{statusmap}" status="status" >
            <s:if test="%{key=='Requested On'}">
                    <td align="center" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="center" width="30%" ><s:property value="%{value}"/></td>
            </s:if>
        </s:iterator> 
        </tr>
        
</table>
<s:hidden id="tabel" value="%{tabel}"></s:hidden>
<s:hidden id="referralStatus" value="%{referralStatus}"></s:hidden>
<s:hidden id="referralSubType" value="%{referralSubType}"></s:hidden>
	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/wfpmReferral" action="statusAction" theme="css_xhtml"  method="post"enctype="multipart/form-data">
    <div class="menubox" style="
    margin-left: 59px;
">
    <s:hidden id="id" name="id" value="%{id}"></s:hidden>
    <s:hidden id="tabel" name="tabel" value="%{tabel}"></s:hidden>
    <div class="newColumn" style="
    margin-left: -50px;
">
			      <div class="leftColumn" style="
    margin-left: -87px;
    margin-top: 29px;
">Action:&nbsp;</div>
			            <div class="rightColumn" style="
    margin-left: 68px;
    margin-top: -40px;
    width: 82%;
"><span class="needed"></span>
					        <s:select  
								id="action_status"
								name="action_status"
						          list="#{'Approved':'Approved','Unapproved':'Unapproved'}"
						        headerKey="-1"
						        headerValue="Select Action" 
						        cssClass="select"
								cssStyle="width:82%"
						        >
					        </s:select>
			        </div>
			    </div>
    
    <div class="newColumn">
	       <div class="leftColumn" style="
    margin-top: 30px;
    margin-left: -47px;
">Comments:&nbsp;</div>
	       <div class="rightColumn" style="
    margin-left: 102px;
    margin-top: -38px;
">
	       <s:textarea name="comments" rows="1" id="comments"  style="
    width: 144%;
" placeholder="Enter Data" cssClass="textarea"/>
	       </div>
	       </div>
    
    <!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
         <sj:submit 
            targets="patientresult" 
            clearForm="true"
            value="Take Action" 
            effect="highlight"
            effectOptions="{color : '#222222'}"
            effectDuration="5000"
            button="true"
            onCompleteTopics="level1"
            cssClass="submit"
            cssStyle="margin-left: 150px;margin-top: 16px;"
            indicator="indicator2"
            onBeforeTopics="validate1"
          />
    </div>
	    
	
	<sj:div id="patientaddition"  effect="fold">
           <div id="patientresult"></div>
        </sj:div>
	</s:form>
 
</s:if>

</body>
</html>