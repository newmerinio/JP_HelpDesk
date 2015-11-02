<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
String id=request.getParameter("fbDataId");
String feedTicketId=null;
if(request.getParameter("feedTicketId")!=null)
{
	feedTicketId=request.getParameter("feedTicketId");
}
else
{
	feedTicketId="NA";
}


%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/lodgefeedbackvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<style type="text/css">
#categoryDiv3 td{
	padding-left:0px!important;
}
</style>
<script type="text/javascript">

$.subscribe('validate1', function(event,data)
		{
			
		    var mystring = $(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		          
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D")
					{
		            	if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            	{
		            	 	errZone11.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            		setTimeout(function(){ $("#errZone11").fadeIn(); }, 10);
		            		setTimeout(function(){ $("#errZone11").fadeOut(); }, 2000);
		            		$("#"+fieldsvalues).focus();
		            		$("#"+fieldsvalues).css("background-color","#ff701a");
		            		event.originalEvent.options.submit = false;
		            		break;    
		             	}
		            }
		            else
			        {
				        $("#confirmEscalationDialog").dialog('open');
				    	$("#confirmEscalationDialog").dialog({title:'Ticket Opening Status'});
			        }
		        }
		    }  
		   
		});

</script>
</head>

<body>
		<s:form id="formone" name="formone" action="feedbackViaOnlineTicket"  theme="css_xhtml"  method="post" enctype="multipart/form-data" namespace="/view/Over2Cloud/feedback">
		<s:hidden name="viaFrom" value="online"></s:hidden>
		<s:hidden name="tosubdept" id="tosdId"></s:hidden>
		<input type="hidden" name="feedDataId" id="feedDataId" value="<%= id %>">
		<input type="hidden" name="feedTicketId" id="feedTicketId" value="<%= feedTicketId %>">
              <s:iterator value="subDept_DeptLevelName" status="counter">
              <s:if test="#counter.count%2 !=0">
               <div class="newColumn">
								<div class="leftColumn1">From&nbsp;Department:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
              	                <s:textfield value="%{feedByDept}" readonly="true" cssClass="textField" ></s:textfield>
              	</div>
              	</div>
               </s:if>
               <s:else>
               <span id="mandatoryFields" class="pIds" style="display: none; ">subdept#To Department#D#</span>
               <div class="newColumn">
               	<div class="leftColumn1" style="text-indent:10px;">To&nbsp;Department:</div>
              	<div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
              		<div id="subDeptDiv3">
                 	 <s:select 
                      id="subdept" 
			          name="subdept" 
			          list="serviceSubDeptList"
			          headerKey="-1"
			          headerValue="Select Department"
			          cssClass="select"
			          cssStyle="width:82%"
			          onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','FM','fbType','ASC','feedType','Select Feedback Type');"
         			 />
                  </div>
                  </div></div>
                  </s:else>
              </s:iterator>
		    <s:iterator value="feedbackDDColumns" status="status">
	  		<s:if test="%{mandatory}">
             	<s:if test="key=='feedType'">
             		<span id="mandatoryFields" class="pIds" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="mandatoryFields" class="pIds" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>	
             	</s:if>
             	<s:elseif test="key=='category'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="pIds" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:elseif>
             	<s:else>
             		<span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             
             	</s:else>
           </s:if>
      <div class="newColumn">
	      <s:if test="#status.odd">
	    	<div class="leftColumn1" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
		        <div class="rightColumn1">
		             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select  
	                  			  id="%{key}"
	                              name="feedbackType"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Feedback Type" 
	                              cssClass="select"
		                          cssStyle="width:82%"
		                          onchange="commonSelectAjaxCall2('feedback_category','id','categoryName','fbType',this.value,'hide_show','Active','categoryName','ASC','category','Select Category');"
	                              >
	                  </s:select>
	             </div>
	       </s:if>
	       <s:else>
		      <div class="leftColumn1"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn1">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="category"
	                              name="category" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Category" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall2('feedback_subcategory','id','subCategoryName','categoryName',this.value,'hide_show','Active','subCategoryName','ASC','subCategory3','Select Sub-Category');">
	                  </s:select>
	             </div>
	            
	      </s:else>
	      </div>
     </s:iterator>
       
       <div class="newColumn">
       <s:iterator value="feedbackDTimeColumns" status="counter" begin="0" end="1">
       	<span id="mandatoryFields" class="pIds" style="display: none; ">subCategory3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        <span id="mandatoryFields" class="pIds" style="display: none; ">subCategory3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             
       <s:if test="#counter.count%2 !=0">
	   
                <div class="leftColumn1" style="text-indent:10px;"><s:property value="%{value}"/>:</div>
                <div class="rightColumn1"><span class="needed"></span>
            <div id="subCategoryDiv3">
                 <s:select 
                              id="subCategory3"
                              name="subCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getFeedBreifViaAjax(this.value);"
                              >
                 </s:select>
            </div>
            </div>
            
            </s:if>
       
        </s:iterator>
	  
	   <s:iterator value="feedbackDTimeColumns" status="counter" begin="1">
	    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#D#,</span>
	   
	       <div class="leftColumn1" style="text-indent:10px;"><s:property value="%{value}"/>:</div>
            <div class="rightColumn1"><span class="needed"></span>
            <s:textfield readonly="true" name="%{key}" id="%{key}"maxlength="50" placeholder="Ticket Resolution Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
        </s:iterator>
        </div>
          	
          	<s:iterator value="feedbackTextColumns" status="status">
          	 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#Complaint Details#D#,</span>
                 <s:if test="#status.odd==true">
                 <div class="newColumn">
                       <div class="leftColumn1" style="text-indent:10px;">Complaint&nbsp;Details:</div>
                       <div class="rightColumn1"><span class="needed"></span><s:textarea name="%{key}" id="%{key}"maxlength="50"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" maxLength="200"/><s:if test="key=='feed_brief'"><div id="errorfeedbrief" class="errordiv"/></s:if></div>
          	    </div>	
          	     </s:if>
          	     <s:else>
          	     <div class="newColumn">
          	           <div class="leftColumn"><s:property value="%{value}"/>:</div>
                       <div class="rightColumn"><span class="needed"></span><s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" /><s:if test="key=='location'"><div id="errorfeedlocation" class="errordiv"/></s:if></div>
		        </div>	
		         </s:else>
          	 </s:iterator>
          	 
          	  <div class="newColumn">
						<div class="leftColumn">SMS:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvSMS" id="recvSMS" theme="simple"></s:checkbox></div>
						<div class="rightColumn">
              Email:&nbsp;&nbsp;&nbsp;<s:checkbox name="recvEmail" id="recvEmail" theme="simple"></s:checkbox>
              </div></div>
                  <div class="clear"></div> 
                  <div class="clear"></div> 
				 <div class="fields" align="center">
				 <ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <center><sj:submit 
	                        targets="confirmingEscalation" 
	                        clearForm="true"
	                        value="  Register  " 
	                        button="true"
	                        cssClass="submit"
	                        cssStyle="margin-left: -10px;margin-top: -10px;"
	                        onBeforeTopics="validate1"
	                        />
	                        &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 156px;margin-top: -41px;"
		                     onclick="resetForm('formone');"
			            />&nbsp;&nbsp;
					</center>
	               </div>
	               </li>
	               
				</ul>
				</div>
				<div>
				<b>NOTE: <i>Tickets will be alloted as per the roaster and holidays of the concerned department.</i></b>
				</div>
			    <center>
			         <sj:dialog loadingText="Please wait..."  id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Online" width="450" cssStyle="overflow:hidden;" height="200" showEffect="slide" hideEffect="explode" >
                           <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div><div id="confirmingEscalation"></div>
                     </sj:dialog>
                </center>
				<sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
            
	    </s:form>
</body>
</html>