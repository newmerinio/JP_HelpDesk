<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:form id="formtwovbvbv"  action="insertemplatemsgurl" theme="css_xhtml"  method="post" >
 <div style="width:500px;margin-left: 139px;">
 <s:hidden id="tempId" name="tempId" value="%{id}"></s:hidden>
 <s:hidden id="templateData" name="templateData" value="%{StoredTemp}"></s:hidden>
 <s:textfield id="lengthrange" name="lengthrange" value="%{lengthOfTextfield}"></s:textfield>

 <div  id="xxxxxxxxx">
<s:iterator value="tempList" status="status" var="index">
  <div>
 <s:if test="%{key == 'textfield'}">
<s:textfield name="textfield%{#status.count}"  id="%{key}"  class="textField" onchange='putValues(this);'     
	placeholder="(Enter  Dynamic Message)"  cssClass="textField" cssStyle="margin: 0px 0px 10px; width: 400px; height: 30px;" 
	maxlength="%{default_value}"  />
<div  id="DIVtextfield<s:property value="%{#status.count}"/>" style="display: none;"></div>
 </s:if>
 <s:else>
 <s:property value="%{key}"/>
 </s:else>
 </div>
</s:iterator>
</div>

</div>
</s:form>
</body>
</html>