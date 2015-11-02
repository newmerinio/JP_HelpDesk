<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
	<%@ page import="java.io.BufferedReader"%>
	<%@ page import="java.io.DataInputStream"%>
	<%@ page import="java.io.FileInputStream"%>
	<%@ page import="java.io.InputStreamReader"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%String path = getServletContext().getRealPath("/");
	FileInputStream filename = new FileInputStream(path+"//visitorimagepath.txt");
	DataInputStream in = new DataInputStream(filename);
	BufferedReader readfilename = new BufferedReader(new InputStreamReader(in));
	String photo  = readfilename.readLine();
	String photopath = path+photo;
	System.out.println("photoname"+photopath);
	%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
@media print {
.header, .hide { visibility: hidden }
}

body 
{
      background-color:#FFFFFF; 
      border: solid 0px black ;
      margin: 0px;  /* this affects the margin on the content before sending to printer */
      top: 0px;
}
</style>
<title>Visitor Pass</title>
<script>

function printpass()
{
var tem=confirm("Are You Sure Want to Take Print?");
if(tem)
{
window.print();
window.location = "view/Over2Cloud/VAM/Visitor/visitorpass.jsp";
window.close();
}
else
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	
	window.close();
}
}

function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#printSelect").dialog('close');
    var myWindow = window.open("","myWindow","width=600,height=400"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}
</script>
</head>
<body >
<div id="visitorPass">
<table  border="1"  rules="groups" width="100%" style="width: 100%;">
	<tr >
		<td>
			<table border="0" width="80%" id="header" align="center">
				<tr>
					<td><img style="background-color: #000000;" border="0" src="images/index.jpg" align="left" alt="JBM Logo"></td>
					<td align="center"><font face="Calibri " size="3"><b><u>Visitor Pass,&nbsp;JBM Group</u></b></font> <br>
					<font face="Calibri " size="3"><b>Sector-36, Pace City-II, Gurgaon - 122004</b></font>
					</td>
					<td colspan="2">
					<img id="img_barcode" border="0" src="<s:url value='/images/barCodeImage/%{vpassId}.gif'/>" align="right" width="50"  height="50"/>
						<!-- <img border="0" src="Test?srnum=srnumber" align="right" width="50"  height="50"> -->
					</td>
				</tr>
			</table>
		</td>
		
	</tr>
	<tr>
		<td>
			<table border="0" width="90%" cellpadding="9px;">
				<tr>
					<td><font face="Calibri"  size="2"><b>Card No.:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.sr_number}"/></b></font></td>
					<td><img border="0" src="visitorsnap/<%=photo%>" valign="middle" width="70"  height="70"></td>
				</tr>
				<tr>
					<td><font face="Calibri"  size="2"><b>Visitor:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.visitor_name}"/></b></font></td>
					
					<td><font face="Calibri"  size="2"><b>Company:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.visitor_company}"/></b></font></td>
				</tr>
				<tr>
					<td><font face="Calibri"  size="2"><b>Mobile No.:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.visitor_mobile}"/></b></font></td>
					<td><font face="Calibri"  size="2"><b>Department.:</b></font></td>
					<s:if test="dept != null">
					<td><font face="Calibri"  size="2"><b><s:property value="%{dept}"/></b></font></td>
					</s:if>
					<s:else>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.deptName}"/></b></font></td>
					</s:else>
				</tr>
				<tr>
					<td><font face="Calibri"  size="2"><b>Address:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.address}"/></b></font></td>
					<td><font face="Calibri"  size="2"><b>To Meet:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.visited_person}"/></b></font></td>
				</tr>
				<tr>
					<td><font face="Calibri"  size="2"><b>Date</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{visit_date}"/></b></font></td>
					<td><font face="Calibri"  size="2"><b>Time:</b></font></td>
					<td><font face="Calibri"  size="2"><b><s:property value="%{time_in}"/></b></td>
				</tr>
				<tr>
					<td><font face="Calibri"  size="2"><b>Signature:</b></font></td>
					<td><font face="Calibri"  size="2"><b>Visitor Signature</b></font></td>
					<td><font face="Calibri"  size="2"><b>Visited Person Signature</b></font></td>
					<td><font face="Calibri"  size="2"><b>Date</b></font></td>
				</tr>
				<!--<tr>
					<td><font face="Calibri " size="1">(only if material is deposited)</font></td>
				</tr>
				--><tr>
				<td><font face="Calibri " size="2"><b>Restricted Item in Possession:</b></font></td>
				<td><font face="Calibri " size="2"> <b><s:property value="%{#parameters.possession}"/></b></font></td>
				</tr>
			</table>
		</td>
	</tr>
	
</table>
</div>
<br/>
<sj:a button="true"  onclick="printDiv('visitorPass')">Print</sj:a>
</body>
</html>