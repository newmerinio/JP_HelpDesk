<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/productivityEvaluation/kaizenProductivity.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 1000);
  });
 function resetTaskName(formId)
 {
 	$('#'+formId).trigger("reset");
 }
 function showDoc(div)
 {
 	$("#"+div).show();
 }
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 <s:property value="%{moduleName}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">

  <s:form id="improvedData" name="improvedData" action="actionAddImproved" namespace="/view/Over2Cloud/ProductivityEvaluationOver2Cloud" theme="simple"  method="post" enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
	<s:hidden id="indexVal" name="indexVal" value="1"/>
	<s:hidden id="moduleName" name="moduleName" value="%{moduleName}"/>
		  <s:iterator value="productivityTxtBox" status="status" >
			 <s:if test="#status.odd==true">
			             <div class="newColumn">
  	            		 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                		 <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<s:textfield name="%{field_value}" id="%{field_value}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </div>
			             </div>
			 </s:if>
			 <s:else>
			           <div class="newColumn">
  	            	   <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                	   <div class="rightColumn">
				            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				            	<s:textarea name="%{field_value}" id="%{field_value}"  maxlength="50"  cssClass="textField" placeholder="Enter Data"/>
				       </div>
			           </div>
			 </s:else>
		  </s:iterator>
		  
		    <s:iterator value="productivityDate" status="status" >
			 <s:if test="%{field_value=='implementedIn'}">
			             <div class="newColumn">
			               <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                		   <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<sj:datepicker id="%{field_value}"  name="%{field_value}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="mm-yy" Placeholder="Select Month"/>
				         </div>
			             </div>
			 </s:if>
		  </s:iterator>
		  
		   <s:iterator value="productivityFile" status="status" begin="0" end="0">
             <div class="newColumn">
            		 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
             		 <div class="rightColumn">
	              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	              		<s:file name="%{field_value}" id="%{field_value}" maxlength="50"  placeholder="Enter Data" cssClass="textField" onchange="showDoc('doc2')"/>
	         </div>
             </div>
		  </s:iterator>
		  
		  <div id="doc2" style="display: none;">
		    <s:iterator value="productivityFile" status="status" begin="1" end="1">
	             <div class="newColumn">
	            		 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
              		 <div class="rightColumn">
		              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		              		<s:file name="%{field_value}" id="%{field_value}" maxlength="50"  placeholder="Enter Data" cssClass="textField" onchange="showDoc('doc3')"/>
		         </div>
	             </div>
		   </s:iterator>
		  </div>
		  
		   <div id="doc3" style="display: none;">
		    <s:iterator value="productivityFile" status="status" begin="2" end="2">
	             <div class="newColumn">
	            		 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
              		 <div class="rightColumn">
		              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		              		<s:file name="%{field_value}" id="%{field_value}" maxlength="50"  placeholder="Enter Data" cssClass="textField" onchange="showDoc('')"/>
		         </div>
	             </div>
		  </s:iterator>
		  </div>
		  
		   <s:iterator value="productivityDropDown" status="status">
          <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          </s:if>
          <s:if test="%{field_value=='otherOG'}">
           	    <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}"
                             name		="%{field_value}"
                             list		="OGList"
                             headerKey	="-1"
                             cssClass="select"
							 cssStyle="width:82%;height:60px;"
							 multiple="true"
							 onchange="getPlantValue('otherOG','otherPlant')"
                             >
                   </s:select>
                </div>
                </div>
          </s:if>
          <s:elseif test="%{field_value=='otherPlant'}">
          	   <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}"
                             name		="%{field_value}"
                             list		="{'NO Data'}"
                             headerKey	="-1"
                             headerValue="Select Plant" 
                             cssClass="select"
							 cssStyle="width:82%;height:60px;"
							 multiple="true"
                             >
                   </s:select>
                </div>
                </div>
          </s:elseif>
        </s:iterator>
               
        <s:iterator value="productivityDropDown" status="status">    
           <s:if test="%{field_value=='byReview'}">
          	   <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}"
                             name		="%{field_value}"
                             list		="reviewBy"
                             headerKey	="-1"
                             headerValue="Select Review By" 
                             cssClass="select"
							 cssStyle="width:82%"
							 onchange="getReviewNames(this.value,'1')"
                             >
                   </s:select>
                </div>
                </div>
          </s:if>
           <s:elseif test="%{field_value=='reviewName'}">
          	   <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}1"
                             name		="%{field_value}"
                             list		="{'NO Data'}"
                             headerKey	="-1"
                             headerValue="Select Review Name" 
                             cssClass="select"
							 cssStyle="width:82%"
                             >
                   </s:select>
                </div>
                </div>
          </s:elseif>
          </s:iterator>
          
		  <s:iterator value="productivityDate" status="status" >
			 <s:if test="%{field_value=='reviewDate'}">
			             <div class="newColumn">
			               <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                		   <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<sj:datepicker id="%{field_value}"  name="%{field_value}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Month"/>
				         </div>
				          <sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 549px;margin-top: -35px;">+</sj:a>
			             </div>
			 </s:if>
		  </s:iterator>
		 
           <s:iterator begin="100" end="120" var="deptIndx">
						<div class="clear"></div>
						 <div id="<s:property value="%{#deptIndx}"/>" style="display: none;">
        <s:iterator value="productivityDropDown" status="status">    
           <s:if test="%{field_value=='byReview'}">
          	   <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}"
                             name		="%{field_value}"
                             list		="reviewBy"
                             headerKey	="-1"
                             headerValue="Select Review By" 
                             cssClass="select"
							 cssStyle="width:82%"
							 onchange="getReviewNames(this.value,'%{#deptIndx}')"
                             >
                   </s:select>
                </div>
                </div>
          </s:if>
           <s:elseif test="%{field_value=='reviewName'}">
          	   <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}%{#deptIndx}"
                             name		="%{field_value}"
                             list		="{'NO Data'}"
                             headerKey	="-1"
                             headerValue="Select Review Name" 
                             cssClass="select"
							 cssStyle="width:82%"
                             >
                   </s:select>
                </div>
                </div>
          </s:elseif>
          </s:iterator>
          
		  <s:iterator value="productivityDate" status="status" >
			 <s:if test="%{field_value=='reviewDate'}">
			             <div class="newColumn">
			               <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                		   <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<sj:datepicker id="%{field_value}%{#deptIndx}"  name="%{field_value}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Month"/>
				         </div>
				           <div style="margin-top: -37px;">
							<s:if test="#deptIndx==113">
								<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
							</s:if>
           					<s:else>
          	 					<div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
          						<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
       						</s:else>
       						</div>
			             </div>
			 </s:if>
		  </s:iterator>
						</div>
						
					</s:iterator>
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
     		  	  />
     		  	   <sj:a 
						button="true" 
						href="#"
						onclick="resetTaskName('improvedData');"
						>
						Reset
					</sj:a>
     		  	  <sj:a 
						button="true" 
						href="#"
						onclick="viewImproved('moduleName');"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 
		 </center>
		 <br>
		 <br>
		 	 <center><sj:div id="complTarget"  effect="fold"> </sj:div></center>
		 </div>
   </s:form>
</div>
</div>
</div>
</div>
</body>
</html>