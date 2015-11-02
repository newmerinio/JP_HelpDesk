<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
{
	$("#empL2").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#empL3").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#empL4").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#empL5").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	
});


function changeinNextEsc1(data, div, sId, sName, nId, nName){
	
	var l1 = $("#empL1").val();
	var l2 = $("#empL2").val();
	var l3 = $("#empL3").val();
	var l4= $("#empL4").val();

	
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/WFPM/Patient/nxxtLevelEscLst.action?l1="+l1+"&l2="+l2+"&l3="+l3+"&l4="+l4+"&div="+div+"&sId="+sId+"&sName="+sName+"&nId="+nId+"&nName="+nName,
		success : function(data)
		{
			$('#'+div).html(data);
	    },
	    error : function () {
			alert("Somthing is wrong to get get Next excalation Level");
		}
	});
}
</script>
</head>
<body>
			<s:select
			id="%{sId}"
			name="%{sName}"
			list="escL5EmpNxtLevel"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:5%"
		   onchange="changeinNextEsc1(this.value, 'l4esc' ,'empL3', 'l4', 'empL4', 'l5')"
		   	
            >
		    </s:select>

</body>
</html>