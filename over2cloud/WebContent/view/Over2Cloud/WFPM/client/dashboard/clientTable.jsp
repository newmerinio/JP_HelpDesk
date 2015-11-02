<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/client/clientDashboard.js"></script>
</head>
<body>
 
 <sj:dialog 
    	id="ratingDataId" 
    	autoOpen="false" 
    	modal="true" 
    	title="Client Type Status Data"
		width="950"
		height="350"
    >
    <div id='showRowDataId'> </div>
    </sj:dialog>
<table align="center" style="width: 100%;font-size: 15px;padding: 0px; border:0px solid #000000">
<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
		
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font><b> Client Type </b></font>
		</td>
		
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b> Rating&nbsp;1</b></font>
		</td>
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font > <b>Rating&nbsp;2</b></font>
		</td>
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b>Rating&nbsp;3</b></font>
		</td>
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b>Rating&nbsp;4</b></font>
		</td>
		<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font ><b>Rating&nbsp;5</b></font>
		</td>
		
	</tr>
	<s:iterator id="first" status="status" value="%{clientRatingWiseData}">
	<tr bgcolor="#FFFFFF">
	
	<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<font color="black"><b><s:property value="%{clientname}"/></b></font>
	</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#" onclick="showRatingOneData('rat1','<s:property value="%{clientname}"/>')"><b><s:property value="%{rat1}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#" onclick="showRatingOneData('rat2','<s:property value="%{clientname}"/>')"><b><s:property value="%{rat2}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#" onclick="showRatingOneData('rat3','<s:property value="%{clientname}"/>')"><b><s:property value="%{rat3}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#" onclick="showRatingOneData('rat4','<s:property value="%{clientname}"/>')"><b><s:property value="%{rat4}"/></b> </a> </font>
		</td>
		<td align="center" bgcolor="" class="sortable" width="" >
			<font color="black"><a style="cursor: pointer;text-decoration: none;margin-left: 10px;" href="#" onclick="showRatingOneData('rat5','<s:property value="%{clientname}"/>')"><b><s:property value="%{rat5}"/></b> </a> </font>
		</td>
	</tr>
	</s:iterator>
	<tr  bgcolor="#EDDFFB" >
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b>Total</b></font></a></td>
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalRat1}"/></b></font></a></td>
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalRat2}"/></b></font></a></td>
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalRat3}"/></b></font></a></td>
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalRat4}"/></b></font></a></td>
		<td align="center" width="5%" height="40%"><a style="cursor: pointer;text-decoration: none;"  href="#"><font color="black"><b><s:property value="%{totalRat5}"/></b></font></a></td>
	</tr>

</table>

</body>
</html>