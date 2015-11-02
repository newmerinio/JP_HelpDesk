<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<STYLE type="text/css">
th{
		padding:2px;
		border-right: 1px solid #e7e9e9;
		margin:1px;
		text-align:center;
	}
td{
		padding:2px;
		margin:1px; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center;
}	

</STYLE>
</head>
<body>
	<div class="clear"></div>

	<div class="middle-content">

		
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
						
						<div id="DataDiv" style="display: block ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto; "  class="container_block" >
    <center>
    <table style="border: 1px solid;">
    <thead>
    <tr>
    <th >Sr. No.</th>
    <th>Name</th>
    <th>Entered Value</th>
    </tr>
    </thead>
    <tbody>
    <s:property value="parameterMap.size"/> 
    <s:iterator id="parameterMap"  value="mapParamRoi" status="status">
    <tr>
    <th><s:property value="#status.count"/>.</th>
    <th><s:property value="%{key}"/></th>
    <th><s:property value="%{value}"/></th>
    </tr>
    </s:iterator>
    </tbody>
    </table>
</center>    
                         </div> 
				</div>
			</div>
		</div>
	</div>

</body>
</html>