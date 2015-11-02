<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


</script>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<div class="page_title"><h1>Customer Details >> Registration</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Customer Details >> Add" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/feedback" action="addCustomerData" theme="simple"  method="post"enctype="multipart/form-data" >
				
				<div class="form_menubox">
				<s:if test="ddTrue">
                 <div class="user_form_text"><s:property value="%{title}"/>:</div>
                  <div class="user_form_input"><s:select 
                              id="title"
                              name="title" 
                              list="#{'Mr':'Mr','Mrs':'Mrs','ms':'ms'}"
                              headerKey="-1"
                              headerValue="Select Title " 
                              cssClass="form_menu_inputselect"
                              >
                   
                  </s:select>
                  </div>
                 </s:if>
                 
                 
                
                </div>
                  
				 <s:iterator value="customerTextBox">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
				 </div>
				 <%}
				  temp++;
				  %>
				 </s:iterator>
				 
				<s:iterator value="customerCalender">
					 <div class="user_form_text"><s:property value="%{value}"/>:</div>
			          <div class="user_form_input">
			          
			          <sj:datepicker name="dob" id="dob" changeMonth="true" changeYear="true" yearRange="1970:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select DOB"/></div>
		     	</s:iterator>             	
             	
             	
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>

</sj:accordion>
</div>
</div>
</html>