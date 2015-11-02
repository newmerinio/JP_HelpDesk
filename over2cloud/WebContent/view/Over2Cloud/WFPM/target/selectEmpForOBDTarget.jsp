<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	function showAllotTarget()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
			type  : "post",
			url : "view/Over2Cloud/wfpm/target/beforeConfigOBDTarget.action?headerName=Configure OBD Target",
			success : function(data){
		$("#"+"data_part").html(data);
			},
			error:function()
			{
				alert("ERROR");
			}
		});
	}
</script>
	  
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head"><s:property value="headerName"/> </div>
	</div>
	<div style=" float:left; padding:20px 1%; width:98%;">
	
	<div class="border">
		<div class="secHead">Allot Target</div>
		<div class="menubox">
			<div class="newColumn">
					<div class="leftColumn1">Target on :</div>
					<div class="rightColumn1">
						<s:select 
	                       cssClass="textField"
	                       id="empName"
	                       name="empName" 
	                       list="empDataList" 
	                       headerKey="-1"
	                       headerValue="--Select Employee Name--" 
	                       onchange="mapTarget(this.value);"
	                       theme="simple"
	                       > 
	                  	</s:select>
					</div>
				</div>
		</div>
		<div class="allotTarget"></div>
	</div>
</body>
</html>