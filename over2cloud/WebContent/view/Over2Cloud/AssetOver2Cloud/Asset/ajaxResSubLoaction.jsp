<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="sublocation%{destination}" 
          name="sublocation" 
          list="subfloorList"
          headerKey="-1"
          headerValue="Select Sub Location"
          cssClass="select"
		  cssStyle="width:82%"
		  onchange="getLoactionViaAjax11(this.value);"
          />
    </body>
</html>