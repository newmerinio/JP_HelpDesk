<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<SCRIPT type="text/javascript"
	src="<s:url value="/js/userHierarchy/userHierarchy.js"/>"></SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="div_main" style="padding: 3px;">
		<div id="div_header"
			style="height: 28px; margin: .2%; cursor: pointer;">
			<div style="float: left; width: 15%; margin: .2%;"></div>
			<div id="div_1"
				style="margin: 2px; height: 20px; border: 1px solid black; cursor: pointer; float: left; padding-right: 3px; font-weight: bold;"
				onclick="moduleChosen('Lead','div_1','<s:property value='userHierarchyList.size'/>');">&nbsp;&nbsp;&nbsp;
				Lead &nbsp;&nbsp;&nbsp;</div>
			<div id="div_2"
				style="margin: 2px; height: 20px; border: 1px solid black; cursor: pointer; float: left; padding-right: 3px; font-weight: bold;"
				onclick="moduleChosen('Client','div_2','<s:property value='userHierarchyList.size'/>');">&nbsp;&nbsp;&nbsp;
				Client &nbsp;&nbsp;&nbsp;</div>
			<div id="div_3"
				style="margin: 2px; height: 20px; border: 1px solid black; cursor: pointer; float: left; padding-right: 3px; font-weight: bold;"
				onclick="moduleChosen('Associate','div_3','<s:property value='userHierarchyList.size'/>');">&nbsp;&nbsp;&nbsp;
				Associate &nbsp;&nbsp;&nbsp;</div>
			<div id="div_4"
				style="margin: 2px; height: 20px; border: 1px solid black; cursor: pointer; float: left; padding-right: 3px; font-weight: bold;"
				onclick="moduleChosen('kpi_Report','div_4','<s:property value='userHierarchyList.size'/>');">&nbsp;&nbsp;&nbsp;
				KPI Report &nbsp;&nbsp;&nbsp;</div>
			<div id="div_5"
				style="margin: 2px; height: 20px; border: 1px solid black; cursor: pointer; float: left; padding-right: 3px; font-weight: bold;"
				onclick="moduleChosen('offering_Report','div_5','<s:property value='userHierarchyList.size'/>');">&nbsp;&nbsp;&nbsp;
				Offering Report &nbsp;&nbsp;&nbsp;</div>
		</div>
		<div id="div_content">
			<div id="div_content_left"
				style="float: left; width: 15%; margin: .2%; height: 450px;">
				<s:iterator value="userHierarchyList" status="status">
					<div id="div_content_left_<s:property value='#status.count'/>"
						style="padding: 2px; font-weight: bold; margin: 2px; height: 20px; border: 1px solid black; cursor: pointer;"
						onclick="showData('<s:property/>','<s:property value='#status.count'/>','<s:property value='userHierarchyList.size'/>');">
						<s:property />
					</div>
				</s:iterator>
			</div>
			<div id="div_content_right"
				style="float: left; width: 83.8%; margin: .2%; border: 1px solid black; height: 450px;">
			</div>
		</div>
	</div>
</html>