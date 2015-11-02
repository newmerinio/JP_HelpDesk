<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>


<script type="text/javascript">
function getCallPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?feedStatus=call&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
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
<div class="middle-content">
<div class="list-icon">
	<div class="head">Ticket Lodge</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Online</div> 
	
	 <sj:a style="margin-top: 4px;float:right;" cssClass="button" button="true"   onclick="viewActivityBoard();">Activity Board</sj:a>
	 <sj:a button="true" cssClass="button" buttonIcon="ui-icon-plus"  style="margin-top:4px; float: right;" onclick="getCallPage()">Call Mode</sj:a>
	         	 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:300px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone" action="FeedbackViaOnline"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
<s:hidden name="viaFrom" value="online"></s:hidden>
<s:hidden name="pageFor"  value="SD"></s:hidden>
<s:hidden name="subCategory_extra" id="test"></s:hidden>
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
        </center>    
        
        <s:iterator value="pageFieldsColumns" begin="0" end="1" status="status">
			  <s:if test="%{mandatory}">
					     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		      </s:if>
		        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" >To Department:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  id="%{key}"
				                              name="todept"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','subdeptname','Select Sub-Department');Reset('.pIds');"
				                              >
				                  </s:select>
                           </div>
                     </div>
                  </s:if>
                  <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}"
                              				  name="tosubdept"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','HDM','fbType','ASC','feedType','Select Feedback Type');Reset('.pIds');">
				                  </s:select>
				           </div>
	                  </div>
	              </s:else>
     </s:iterator>
     
      <s:iterator value="pageFieldsColumns" begin="6" end="7" status="status">
	  <s:if test="%{mandatory}">
             	<s:if test="key=='feedType'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>	
             	</s:if>
             	<s:elseif test="key=='category'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:elseif>
      </s:if>
	      <s:if test="#status.odd">
	      <div class="newColumn">
	    	<div class="leftColumn" ><s:property value="%{value}"/>:&nbsp;</div>
		        <div class="rightColumn">
		             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select  
	                  			  id="%{key}"
	                              name="feedbackType"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Feedback Type" 
	                              cssClass="select"
		                          cssStyle="width:82%"
		                          onchange="commonSelectAjaxCall2('feedback_category','id','categoryName','fbType',this.value,'hide_show','Active','categoryName','ASC','category','Select Category');Reset('.pIds');"
	                              >
	                  </s:select>
	             </div>
	         </div>
	       </s:if>
	       <s:else>
	          <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="category"
	                              name="category" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Category" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall2('feedback_subcategory','id','subCategoryName','categoryName',this.value,'hide_show','Active','subCategoryName','ASC','subCatg','Select Sub-Category');Reset('.pIds');">
	                  </s:select>
	             </div>
	            </div>
	      </s:else>
     </s:iterator>
     
     <s:iterator value="pageFieldsColumns" begin="8" end="9" status="status">
	  <s:if test="%{mandatory}">
	   		 <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       		 <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      <s:if test="#status.odd">
      <div class="newColumn">
    	<div class="leftColumn" ><s:property value="%{value}"/>:&nbsp;</div>
	        <div class="rightColumn">
	             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  <s:select  
                  			  id="%{key}"
                              name="subCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="getFeedBreifViaAjax(this.value);Reset('.pIds');"
                              >
                  </s:select>
             </div>
         </div>
       </s:if>
       <s:else>
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			<div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
      </s:else>
     </s:iterator>
     
      <s:iterator value="pageFieldsColumns" begin="11" end="11">
	  <s:if test="%{mandatory}">
       		 <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      
           
     </s:iterator>
     
     
     <s:div>
     <span id="normalSubdept" class="pIds" style="display: none; ">floorid#Floor#D#,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">floorid#Floor#D#,</span>
	 <div class="newColumn">
         <div class="leftColumn">Floor:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"></span>
                  <s:select 
                              id="floorid"
                              name="floor"
                              list="#{'-1' : 'Select Floor','Basement' : 'Basemnet', 'Grd Flr' : 'Ground Floor', '1st Flr' : 'First Floor', '2nd Flr' : 'Second Floor', '3rd Flr' : 'Third Floor', '4th Flr' : 'Fourth Floor', '5th Flr' : 'Fifth Floor', '6th Flr' : 'Sixth Floor', '7th Flr' : 'Seventh Floor'}"
                              cssClass="select"
                                 cssStyle="width:82%"
                                 onchange="Reset('.pIds');">
                  </s:select>
			</div>
	 </div>
	 </s:div>
     
     
     <s:iterator value="pageFieldsColumns" begin="10" end="10">
	  <s:if test="%{mandatory}">
       		 <span id="ddSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			<div class="rightColumn">
			<s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
     </s:iterator>
     
     
     	<div class="newColumn">
	     <div class="leftColumn">Self Acknowledge:&nbsp;</div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
    </div>
     
     <div class="clear"></div>
	 <div class="fields" align="center">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
					<div id="ButtonDiv">
	                <sj:submit 
	                        id="onlineSubmitId"
	                        targets="confirmEscalationDialog" 
	                        clearForm="true"
	                        value="Register" 
	                        button="true"
	                        disabled="false"
	                        onBeforeTopics="validateOnline"
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssStyle="margin-left: 53px;"
				            />
				    </div>
				            <sj:a cssStyle="margin-left: 224px;margin-top: -28px;" button="true" href="#" onclick="resetForm('formone');Reset('.pIds'); ">Reset</sj:a>
				      </div>
	               </li>
				</ul>
				</div>
				
			    <center>
			         <sj:dialog id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Success Message" width="700" height="100" showEffect="slide" hideEffect="explode" position="['center','top']"></sj:dialog>
                </center>
</s:form>
</div>
</div>
</body>
</html>