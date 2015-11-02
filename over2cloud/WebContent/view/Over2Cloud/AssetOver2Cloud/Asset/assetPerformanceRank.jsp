<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<B>Asset Type Performance Rank</B>
<br>
<br>
<%int i=1; %>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
    <tr>
        <td valign="bottom">
            <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
             	  <tr bgcolor="#DAA520">
             			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rank</b></font></td>
                        <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Asset&nbsp;Type</b></font></td>
                        <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Score</b></font></td>
             	  </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top" style="padding-top: 5px" align="center">
        <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="5" direction="up" >
	          <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                  <s:iterator id="assetList2"  status="status" value="%{assetRankForDashboard.assetList}" > 
					  <tr bordercolor="#ffffff">
                		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');" href="#"><%=i%><b></b></a></font></td>
                   	    <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');" href="#"><s:property value="#assetList2.assetType" /></a></font></td>
                        <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList2.departId"/>','performanceRank','<s:property value="#assetList2.assetid"/>');"href="#"><s:property value="#assetList2.assetScore" /></a></font></td>
                     </tr>
                     <%i++;%>
             	   </s:iterator>
             </table>
        </marquee>
        </td>
    </tr>
</table>
</body>
</html>