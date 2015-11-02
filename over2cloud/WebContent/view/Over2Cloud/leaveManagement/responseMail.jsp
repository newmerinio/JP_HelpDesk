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
			      <table width="40%" align="center" border="0">
			    	<tr style="background-color: #D8D8D8" >
						<th colspan="2">
							<h3>
                           Leave Response Action Via Mail
                           </h3>
						</th>    	
			    	</tr>
			   
		     		 <s:iterator value="responseColumnText">
					  <s:if test="%{key == 'name'}">
	                 <tr  style="background-color: #eeeeee">
								<td><s:property value="%{value}"/></td>
								<td> <s:property value="%{empname}"/>
								<s:hidden name="empname" value="%{empname}"></s:hidden>
                  </td>
                   </tr>
                   </s:if>
                   <s:if test="%{key == 'email'}">
                   <tr  style="background-color: #eeeeee">
								<td><s:property value="%{value}"/></td>
                 <td> <s:property value="%{empemail}"/>
                 <s:hidden name="empemail" value="%{empemail}"></s:hidden>
                  </td>
                   </tr>	
                  </s:if>
                  </s:iterator>
                  
                  <s:iterator value="responseColumnText">
                  <s:if test="%{key == 'mobno'}">
                  
	               <tr  style="background-color: #eeeeee">
								<td><s:property value="%{value}"/></td>
                  <td> <s:property value="%{empmob}"/>
                  <s:hidden name="empmob" value="%{empmob}"></s:hidden>
                  </td>
                  </tr>
                  </s:if>
                  </s:iterator>
		   
				 <s:iterator value="responseColumnDropdown">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'status'}">
	              <tr  style="background-color: #eeeeee">
								<td><s:property value="%{value}"/></td>
								<td>
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
	                              id="%{key}"
	                              name="%{key}" 
	                              list="{'Approve','Disapprove'}"
	                              headerKey="-1"
	                              headerValue="Select Status" 
	                              cssClass="select"
                                  cssStyle="width:55%"
	                              >
                               </s:select>
                              </td>
                  </tr> 
                      </s:if> 
                 </s:iterator>  
                
				 <s:iterator value="responseColumnText">
				         <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
				 	<s:if test="%{key == 'comment'}">
	                <tr  style="background-color: #eeeeee">
								<td><s:property value="%{value}"/></td>
								<td>
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                    <s:textarea id="%{Key}" name="%{Key}"  placeholder="Enter Data" cssClass="textField" theme="simple" cssStyle="width:53%" style="background-color: #E9F4FF"></s:textarea>
			                    </td>
			          </tr>            
			         </s:if>
                  </s:iterator>
                  
             	</table>
            
             	<br>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div>
				 <center> 
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value=" OK " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
	                  
	               </div>
	               </center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               
	</s:form>	
</div>
</div>
</body>
</html>