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
			<div class="leftColumn">Lost Reason:</div>
			<div class="rightColumn"><span class="needed"></span>
				<s:select
					id="lostId"
					name="lostId" 
					list="lostStatusMAP"
					headerKey="-1"
					headerValue=" Select Reason"
					cssClass="textField"
					onchange="showDiv();"
					></s:select>
			</div>
			</div>   
			<div id="showotherreason" style="display: none; ">
				<div class="newColumn">
				<div class="leftColumn">Lost Reason:</div>
					<div class="rightColumn">
						<s:textfield name="otherlostreason"  id="otherlostreason" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					</div>
				</div>
			</div>
				<div class="newColumn">
					<div class="leftColumn">RCA:</div>
					<div class="rightColumn">
					     <s:textfield name="RCA"  id="RCA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					</div>
				</div>
			
			<div class="newColumn">
				<div class="leftColumn">CAPA:</div>
				<div class="rightColumn">
				     <s:textfield name="CAPA"  id="CAPA" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				</div>
			</div>
</body>
</html>