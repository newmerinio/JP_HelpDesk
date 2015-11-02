<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
						<s:select 
                              id="spare_name"
                              name="spare_name" 
                              list="spareNameMap"
                              headerKey="-1"
                              headerValue="--Select Spare Name--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  		</s:select>	
</body>
</html>