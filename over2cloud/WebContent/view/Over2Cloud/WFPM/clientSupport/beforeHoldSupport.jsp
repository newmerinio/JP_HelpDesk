<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lost Opportunity On Action</title>

<script type="text/javascript">
 function showDiv()
 	{
	 	//var data=$("#lostId").val();
	 	var data=$("#lostId option:selected").text();
	       if(data=="Other")
	       {
	        document.getElementById("showotherreason").style.display="block";
	       }
	       else
	       {
	    	   document.getElementById("showotherreason").style.display="none";
	       }
 	}
</script>
</head>
<body>
			
				<div class="newColumn">
				<div class="leftColumn">Comments:</div>
					<div class="rightColumn">
						 <s:textfield  id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
					</div>
				</div>
		<s:hidden name="actionType" value="%{actionType}" id="actionType" /> 	
</body>
</html>