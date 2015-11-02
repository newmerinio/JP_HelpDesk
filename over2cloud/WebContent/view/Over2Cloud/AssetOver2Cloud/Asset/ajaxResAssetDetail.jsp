<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<s:select 
			                    cssClass="select"
					            cssStyle="width:82%"
			                    id="assetId"
			                    name="assetId" 
			                    list="assetDetail" 
			                    headerValue="Select Asset Name"
			                    headerKey="-1"
			                    onchange="getAssetDetail(this.value,'1','0','0','0');"
                    	 />
</body>
</html>