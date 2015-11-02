<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>

<html>
<head>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

</script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
</head>
<body>
 <div style=" float:left;width:100%;">
 <div class="border">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/leaveManagement" action="responseAddAction" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		     	<s:hidden name="id" id="id" value="%{#parameters.id}" ></s:hidden>
		     	<s:hidden name="validate" id="validate" value="%{#parameters.validate}" ></s:hidden>
		     		    
		     		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: black;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px; display: none;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
                      
    <br>
    <br>
    <p>Dear <s:property value="%{#parameters.validate}"/>,</p>
    <p> Just FYI,following action has already been done as</p>
    
			      <table width="40%" align="center" border="0">
			    	<tr style="background-color: #D8D8D8" >
						<th colspan="2">
							<h3>
                           Leave Action Status
                           </h3>
						</th>    	
			    	</tr>
			    	
			    	
			    	
			    	   <s:iterator value="responseColumnText">
                   <s:if test="%{key == 'name'}">
	                 <tr  style="background-color: #eeeeee" align="center">
								<td>Requested By</td>
								<td> <s:property value="%{empname}"/>
								<s:hidden name="empname" value="%{empname}"></s:hidden>
                  </td>
                   </tr>
                   </s:if>

                  </s:iterator>
                  
                  <s:iterator value="responseColumnText">
                   <s:if test="%{key == 'odate'}">
	                 <tr  style="background-color: #eeeeee" align="center">
								<td><s:property value="%{value}"/></td>
								<td> <s:property value="%{odatetemp}"/>
								<s:hidden name="odatetemp" value="%{odatetemp}"></s:hidden>
                  </td>
                   </tr>
                   </s:if>
              </s:iterator>
              
                  <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'ftime'}">
	                 <tr  style="background-color: #eeeeee" align="center">
								<td>From Time</td>
								<td> <s:property value="%{ftimetemp}"/>
								<s:hidden name="ftimetemp" value="%{ftimetemp}"></s:hidden>
                  </td>
                   </tr>
                   </s:if>

                  </s:iterator>
                  
                  <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'ttime'}">
	                 <tr  style="background-color: #eeeeee" align="center">
								<td><s:property value="%{value}"/></td>
								<td> <s:property value="%{ttimetemp}"/>
								<s:hidden name="ttimetemp" value="%{ttimetemp}"></s:hidden>
                  </td>
                   </tr>
                   </s:if>

                  </s:iterator>
                  
                     <s:iterator value="responseColumnText">
                   <s:if test="%{key == 'reason'}">
                   <tr  style="background-color: #eeeeee" align="center">
								<td><s:property value="%{value}"/></td>
                 <td> <s:property value="%{reason}"/>
                 <s:hidden name="reason" value="%{reason}"></s:hidden>
                  </td>
                   </tr>	
                  </s:if>
                  </s:iterator>
			   
		     		 
                  
                  <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'approveBy'}">
                  
	               <tr  style="background-color: #eeeeee" align="center">
								<td><s:property value="%{value}"/></td>
                  <td> <s:property value="%{approveBy}"/>
                  <s:hidden name="approveBy" value="%{approveBy}"></s:hidden>
                  </td>
                  </tr>
                  </s:if>
                  </s:iterator>
		   
				 <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'comment'}">
                  
	               <tr  style="background-color: #eeeeee" align="center">
								<td><s:property value="%{value}"/></td>
                  <td> <s:property value="%{comments}"/>
                  <s:hidden name="comments" value="%{comments}"></s:hidden>
                  </td>
                  </tr>
                  </s:if>
                  </s:iterator>
                
				<s:iterator value="responseColumnText">
                  <s:if test="%{key == 'date1'}">
                  
	               <tr  style="background-color: #eeeeee" align="center">
								<td>Request On</td>
                  <td> <s:property value="%{date1}"/>
                  <s:hidden name="date1" value="%{date1}"></s:hidden>
                  </td>
                  </tr>
                  </s:if>
                  </s:iterator>
                  
             	</table>
        
               
	</s:form>	
</div>
</div>
</body>
</html>