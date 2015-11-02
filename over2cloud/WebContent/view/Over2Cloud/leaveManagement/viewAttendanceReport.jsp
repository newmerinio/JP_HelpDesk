<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>


 <div style=" float:left; padding:5px 0%; width:100%;">
 <div class="border">
<s:form id="attendance" name="attendance" namespace="/view/Over2Cloud/leaveManagement" action="reportDataSaveAction" theme="simple"  method="post" enctype="multipart/form-data" >
	     	 <s:param name="empname" value="%{empname}" />
             <s:param name="fdate" value="%{fdate}" />
             <s:param name="tdate" value="%{tdate}" />
		     	 	
		     	<s:hidden id="empname" name="empname" value="%{empname}"></s:hidden>
		     	<s:hidden id="tdate" name="tdate" value="%{tdate}"></s:hidden>
		     	<s:hidden id="fdate" name="fdate" value="%{fdate}"></s:hidden>	
		     		
		     	  <s:iterator value="reportColumnText">
		     	         <div class="form_menubox">
			     	 		 <s:if test="%{key == 'twdays'}">
			                  	<div class="user_form_text" ><s:property value="%{value}" />:</div>
			                  	<div class="user_form_input"><s:textfield id="%{Key}" name="%{Key}" value="%{noOfTotalWorkingDays}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
		                      	</div>
	                         </s:if>
	                         <s:if test="%{key == 'cf'}">
	                      		<div class="user_form_text" ><s:property value="%{value}" />:</div>
		                 	 	<div class="user_form_input"><s:textfield id="%{Key}" name="%{Key}"  value="%{carryForward}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
	                       	    </div>
	                        </s:if>
                        </div>
                    
                         <div class="form_menubox">
			     	 		 <s:if test="%{key == 'extraworking'}">
			                 	 <div class="user_form_text" ><s:property value="%{value}" />:</div>
			                  	 <div class="user_form_input"><s:textfield id="%{Key}" name="%{Key}"  value="%{extraWorking}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
		                         </div>
	                        </s:if>
	                        <s:if test="%{key == 'totalDeduction'}">
	                     		 <div class="user_form_text" ><s:property value="%{value}" />:</div>
		                  		 <div class="user_form_input"><s:textfield id="%{Key}" name="%{Key}"  value="%{totalDeduction}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
	                       		 </div>
	                        </s:if>
                        </div>
                        <div class="form_menubox">
			     	 		 <s:if test="%{key == 'lnextmonth'}">
			                     <div class="user_form_text" ><s:property value="%{value}" />:</div>
			                     <div class="user_form_input"><s:textfield id="%{Key}" name="%{Key}" value="%{carryForwar4Month}" cssClass="form_menu_inputtext" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
		                        </div>
	                        </s:if>
                        </div>
                   </s:iterator>
                    
	         NOTE: Only two Casual Leave (CL) are allowed for permanent employee while during training / probationary period
             	                     no Casual Leave (CL) are allowed. Refer Leave Policy for more details.      
             	     
             	             
             	<br>
             	<div class="clear"></div>
             	<div class="clear"></div>
             	<div class="clear"></div>
             	<br>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<center>
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value=" Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
	             </li>
				</ul>
				</center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
           </div>
           
	</s:form>	
</div>
</div>
</html>