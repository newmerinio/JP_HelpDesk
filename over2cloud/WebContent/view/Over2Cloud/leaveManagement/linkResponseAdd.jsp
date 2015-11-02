<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet"/>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<title>:: Leave Response ::</title>

</head>
<body>
<sj:accordion id="accordion" autoHeight="false" active="0">
<sj:accordionItem title="Leave Response" > 
<div class="sct">
 <div class="sct_left">
  <div class="sct_right">
   <div class="sct_left">
	<div class="sct_right">
	 <s:form id="report" name="mainpage" action="responseAddAction" theme="css_xhtml" method="post" >
	 <s:hidden name="id" id="id" value="%{#parameters.id}" ></s:hidden>
	 <input type="hidden" value="<%=request.getContextPath()%>" id="leaveResponse1" name="leaveResponse1">
		      <div class="type-text">
			     <div class="form_menubox">
			       <div class="form_menubox">
			          <div class="user_form_text">Name:</div>
			          <div class="user_form_input">
                      <s:textfield id="name" name="name" value='%{empname}' cssClass="form_menu_inputselect"/></div>
			          <div class="user_form_text1">Mobile No:</div>
			          <div class="user_form_input">
			          <s:textfield id="mobile" name="mobile" value='%{empmob}' cssClass="form_menu_inputselect"></s:textfield>
			          </div>
			          </div>
			    
			          <div class="form_menubox">
			          <div class="user_form_text">Email Id:</div>
			          <div class="user_form_input">
			          <s:textfield id="email" name="email" value='%{empemail}' cssClass="form_menu_inputselect"></s:textfield>
			          </div>
			          <div class="user_form_text1">Status:</div>
			          <div class="user_form_input">
			                 <s:select 
                             id="status"
                             name="status" 
                             list="#{'1':'Accept','0':'Decline'}"
                             headerKey="-1"
                             headerValue="--Select Status--"
                             cssClass="form_menu_inputselect"
                             cssStyle="margin:0px 0px 10px 0px"
                             ></s:select>
			          </div>
			          </div>
			          <div class="form_menubox">
			          <div class="user_form_text">Comment:</div>
			          <div class="user_form_input">
			          <s:textarea id="comment" name="comment" cssClass="form_menu_inputselect"></s:textarea>
			          </div>
			     </div>
			     <center>
			     <div id="submitButton">
                 <sj:submit 
	                  targets="Result1" 
	                  clearForm="true"
	                  value="OK" 
	                  button="true"
	                  onCompleteTopics="complete1"
	                  onBeforeTopics="validateForm"
	                  />
                 </div>
			     </center>
			     <sj:div id="foldeffect1"  effect="fold">
			     <div id="Result1"></div>
			     </sj:div>
     		</div>
     		</div>
     		</s:form>
     		</div>
     
            </div></div></div>
            </div>
 
</sj:accordionItem>
</sj:accordion>
</body>
</html>