<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    <%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
    <%@ page import="java.io.BufferedReader"%>
    <%@ page import="java.io.DataInputStream"%>
    <%@ page import="java.io.FileInputStream"%>
    <%@ page import="java.io.InputStreamReader"%>
    <%String path = getServletContext().getRealPath("/");
    FileInputStream filename = new FileInputStream(path+"//visitorimagepath.txt");
    DataInputStream in = new DataInputStream(filename);
    BufferedReader readfilename = new BufferedReader(new InputStreamReader(in));
    String photo  = readfilename.readLine();
    //String photopath = path+"visitorsnap\\"+photo;
    String photopath = path+photo;
    System.out.println("photoname"+photopath);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vehicle Pass</title>
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
<script>
function printpass()
{
var tem=confirm("Are You Sure Want to Take Print?");
if(tem)
{
window.print();
window.location = "view/Over2Cloud/VAM/Vehicle/vehiclepass.jsp";
window.close();
}
else
{
    /* $.ajax({
        type : "post",
        url : "view/Over2Cloud/VAM/master/beforeVisitorEntryRecords.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
     
    window.close(); */
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
<body>
<table id="vehiclePass" border="1"  rules="groups" width="100%" style="width: 100%;">
    <tr >
        <td>
            <table border="0" width="80%" id="header" align="center">
                <tr>
                    <td><img style="background-color: #000000;" border="0" src="images/index.jpg" align="left" alt="JBM Logo"></td>
                    <td align="center"><font face="Calibri " size="3"><b><u>Vehicle Pass,&nbsp;JBM Group</u></b></font> <br>
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
                    <td><font face="Calibri"  size="2"><b>ID No.:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.sr_number}"/></b></font></td>
                    <td><img border="0" src="visitorsnap/<%=photo%>" valign="middle" width="70"  height="70"></td>
                </tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>Driver Name:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.driver_name}"/></b></font></td>
                     
                    <td><font face="Calibri"  size="2"><b>Driver Mobile:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.driver_mobile}"/></b></font></td>
                </tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>Owner Name:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.vehicle_owner}"/></b></font></td>
                     
                    <td><font face="Calibri"  size="2"><b>Owner Mobile:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.vh_owner_mob}"/></b></font></td>
                </tr>
                 
                <tr>
                    <td><font face="Calibri"  size="2"><b>Vehicle Model:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.vehicle_model}"/></b></font></td>
                     
                    <td><font face="Calibri"  size="2"><b>Vehicle Number:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.vehicle_number}"/></b></font></td>
                     
                     
                </tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>Material:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.material_details}"/></b></font></td>
                     
                    <td><font face="Calibri"  size="2"><b>Quantity:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.quantity}"/></b></font></td>
                     
                     
                </tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>No Of Bill:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.no_of_bill}"/></b></font></td>
                    <td><font face="Calibri"  size="2"><b>Department.:</b></font></td>
                        <s:if test="dept != null">
                    <td><font face="Calibri"  size="2"><b><s:property value="%{dept}"/></b></font></td>
                    </s:if>
                    <s:else>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.deptName}"/></b></font></td>
                    </s:else>
                </tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>Vehicle Entry Date:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.entry_date}"/></b></font></td>
                    <td><font face="Calibri"  size="2"><b>Time In:</b></font></td>
                    <td><font face="Calibri"  size="2"><b><s:property value="%{#parameters.entry_time}"/></b></font></td>
                </tr>
                <tr></tr><tr></tr>
                <tr>
                    <td><font face="Calibri"  size="2"><b>Security Signature:</b></font></td>
                    <td><font face="Calibri"  size="2"><b></b></font></td>
                    <td><font face="Calibri"  size="2"><b>Driver/Owner Signature</b></font></td>
                    <td><font face="Calibri"  size="2"><b>Date</b></font></td>
                </tr>
                <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
            </table>
        </td>
    </tr>
</table>
----------------------------------------------------------------------------------------------------------------------
<br/>
<sj:a button="true"  onclick="printDiv('vehiclePass')">Print</sj:a>
</body>
</html>
