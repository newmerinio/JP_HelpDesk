<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
{
  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
  setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
 
});
$.subscribe('close', function(event,data)
   {
		document.getElementById("indicator2").style.display="none";
   });

       
</script>
</head>
<body>
<div class="clear"></div>
 <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
	   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="krTakeAction" theme="simple" method="post" enctype="multipart/form-data">
		<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>	

			<s:hidden name="id" value="%{id}"/>
			<s:hidden name="frequency" value="%{frequency}"/>
			
			<div class="clear"></div>
			
			<table width="100%" border="1">
    		  <tr  bgcolor="lightgrey" style="height: 25px">
			    			<td align="left" ><strong>From Department:</strong></td>
							<td align="left" ><s:property value="%{#parameters.fromDept}"/></td>
						    <td align="left" ><strong>Group:</strong></td>
							<td align="left" ><s:property value="%{#parameters.group}"/></td>
		    </tr>
		    <tr  bgcolor="white" style="height: 25px">
			  				<td align="left" ><strong>Sub Group:</strong></td>
							<td align="left" ><s:property value="%{#parameters.subGroup}"/></td>
						    <td align="left" ><strong>To Department:</strong></td>
							<td align="left" ><s:property value="%{#parameters.todept}"/></td>
		    </tr>
		    <tr  bgcolor="lightgrey" style="height: 25px">
			   <td align="left" ><strong>To Contact:</strong></td>
							<td align="left" width="25%" ><s:property value="%{empName}"/></td>
						    <td align="left" ><strong>Tags:</strong></td>
							<td align="left" ><s:property value="%{#parameters.tags}"/></td>
		    </tr>
		     <tr  bgcolor="white" style="height: 25px">
			  				<td align="left" ><strong>Access Type:</strong></td>
							<td align="left" ><s:property value="%{#parameters.accesType}"/></td>
						    <td align="left" ><strong>Frequency:</strong></td>
							<td align="left" ><s:property value="%{#parameters.frequency}"/></td>
		    </tr>
		    <tr  bgcolor="lightgrey" style="height: 25px">
			   <td align="left" ><strong>Share Date:</strong></td>
							<td align="left" ><s:property value="%{#parameters.shareDate}"/></td>
						    <td align="left" ><strong>Action Date:</strong></td>
							<td align="left" ><s:property value="%{#parameters.actionDate}"/></td>
		    </tr>
     </table>
				
				<s:if test="%{commentFlag}">
				<div class="newColumn">
				<div class="leftColumn1">Comment:</div>
				  <span id="normalSubdept" class="pIds" style="display: none; ">krComment#Comment#T#a#,</span>
					<div class="rightColumn1">
					<s:textarea name="comments" id="comments" cssClass="textField" placeholder="Enter Data" 
						cssStyle=" width: 50%;height: 50px;" /></div>
				</div>
			    </s:if>
			    
				<s:if test="%{ratingFlag}">
				<div class="newColumn">
							<div class="leftColumn1">Rating:</div>
							   <span id="normalSubdept" class="pIds" style="display: none; ">krRating#Rating#D#,</span>
								<div class="rightColumn1" >
				                 <s:select 
				                 
				                    id="rating1"
									name="rating" 
									list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}" 
									headerKey="-1"
									headerValue="Select Rating" 
									cssClass="select"
								    cssStyle="width:82%"
									>
								</s:select> 
								
								<!-- <div id='jqxRating'></div> -->
							</div>
							
							
			<!-- 				<div id='jqxWidget' style="font-size: 13px; font-family: Verdana;">
        <div id='rating'></div>
        <div style='margin-top:10px;'>
           <div style='float: left;'></div> <div style='float: left;' id='rating'></div>
        </div>
  </div> -->
							
						</div>
						</s:if>
			<s:if test="%{accessType=='Editable'}">
				<div class="newColumn">
					<div class="leftColumn1">Upload:</div>
					   <span id="normalSubdept" class="pIds" style="display: none; ">krUpload#Upload#D#,</span>
						<div class="rightColumn1">
		               <s:file name="krUpload" id="krUpload" cssClass="textField"></s:file>
					</div>
				</div>
			</s:if>
			
			
			
			<!-- Buttons --><br><br><br><br><br>
							<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="Result" 
								     clearForm="true"
								     value="  Save  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="level1"
								     cssClass="button"
								     indicator="indicator2"
								     onBeforeTopics="validate"
							     />
							      <sj:div id="foldeffect"  effect="fold">
                                  <div id="Result"></div>
               </sj:div>
							    
							</div>
						</div>
						<div class="clear"></div>
				  
			</s:form></div>
			</div>
</html>