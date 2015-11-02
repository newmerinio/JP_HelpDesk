<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="floorname1" 
          name="floorname1" 
          list="subFloorList"
          headerKey="-1"
          headerValue="Select Location"
          cssClass="select"
          cssStyle="width:82%"
          />
    </body>
</html>