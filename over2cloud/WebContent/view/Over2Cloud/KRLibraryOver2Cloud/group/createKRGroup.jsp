<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/KRValidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('completeData', function(event,data)
 {
   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
   setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
   $('select').find('option:first').attr('selected', 'selected');
	fetchGroup('groupNameNew');
 });

$.subscribe('level2', function(event,data)
 {
   setTimeout(function(){ $("#orglevel2Div").fadeIn(); }, 10);
   setTimeout(function(){ $("#orglevel2Div").fadeOut(); }, 4000);
   $('select').find('option:first').attr('selected', 'selected');
 });
function viewDept()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/groupHeaderKR.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	 
}
</SCRIPT>
</head>
<body>

<div class="clear"></div>
	<div class="list-icon">
	 <div class="head">KR Group </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div> 
<div class="clear"></div>
 <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
<sj:accordion id="accordion" autoHeight="false">
<s:if test="groupLevel > 0">
<sj:accordionItem title="%{groupLevelName}" id="oneId">  
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="addKrGroup" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
<center>
	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
					<div id="errZone" style="float:center; margin-left: 7px"></div>
	</div>
</center>							
							<s:iterator value="KRGroupDropDown">
							<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
							   	    <div class="newColumn">
					  	            <div class="leftColumn1"><s:property value="%{value}"/>:</div>
					                <div class="rightColumn1">
					                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                <s:select  
					                             id		    ="%{key}"
					                             name		="%{key}"
					                             headerValue="Select Contact Sub Type"
					                             list		="deptName"
					                             headerKey	="-1"
					                             cssClass="select"
												 cssStyle="width:82%;"
					                             >
					                   </s:select>
					                </div>
					                </div>
							</s:iterator>
							
							<s:iterator value="KRGroupTextBox" status="counter">
							<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 			<s:if test="#counter.odd">
	                 				<div class="newColumn">
	                 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	                 					<div class="rightColumn1">
             								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	   										<s:textfield name="%{key}"  id="%{key}" value="" cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
										</div>
	                 				</div>
	                 			</s:if>
	                 			<s:else>
	                 				<div class="newColumn">
		                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
										<div class="rightColumn1">  
											<s:if test="%{mandatory}"><span class="needed"></span></s:if>
											<s:textfield name="%{key}" id="%{key}" value="" cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</div>
									</div>
	                 			</s:else>
							</s:iterator>  
							
							<!-- Buttons -->
							
							<div class="clear" >
						<div style="padding-bottom: 10px;margin-left:-80px" align="center">
						<sj:submit 
				               targets="Result" 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="completeData"
								     cssClass="button"
								     indicator="indicator2"
								     onBeforeTopics="validateFirst"
			            />
			            &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 139px;margin-top: -40px;"
		                    onclick="resetTaskName('formone');"
			            />&nbsp;&nbsp;
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="viewDept();" cssStyle="margin-left: 266px;margin-top: -74px;" 
						>Back
					</sj:a>
					</div>
               </div>
			   <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
						<div class="clear"></div>
</s:form>
</sj:accordionItem>
</s:if>
<s:if test="groupLevel > 1">
<sj:accordionItem title="%{subGroupLevelName}" id="twoId">  
<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="addKrSubGroup" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="rserrZone" style="float:center; margin-left: 7px"></div></div></center>	 
		<!-- Drop down -->
		
		    <div class="newColumn">
				<div class="leftColumn1">Department:</div><span id="gggggg" class="rspIds" style="display: none; ">deptName#Department#D#,</span>
				<div class="rightColumn1">
					<span class="needed"></span>
			          <s:select 
                              id="deptName"
                              list="deptName"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchGroup(this.value,'groupNameNew')"
                              
                              >
                  		</s:select>
				</div>
			</div>
			<div class="newColumn">
				<div class="leftColumn1">Group:</div><span id="gggggg" class="rspIds" style="display: none; ">groupNameNew#Group#D#,</span>
				<div class="rightColumn1">
					<span class="needed"></span>
			          <s:select 
                              id="groupNameNew"
                              name="group_name" 
                              list="#{'-1':'Select'}"
                              headerKey="-1"
                              headerValue="Select Group" 
                              cssClass="select"
                              cssStyle="width:82%"
                              
                              >
                  		</s:select>
				</div>
			</div>
		   <s:iterator value="KRGroupTextBox1" status="counter">
						<s:if test="%{mandatory}">
                     			<span id="mandatoryFields" class="rspIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
                		</s:if>
             			<s:if test="#counter.odd">
             			<s:if test="%{key=='start_kr_id'}">
             			       <div class="newColumn">
                				<div class="leftColumn1"><s:property value="%{value}" />:</div>
							<div class="rightColumn1"><s:if test="%{mandatory}">
              						<span class="needed"></span>
              					   </s:if>
								<s:textfield name="%{key}" id="%{key}"  cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							</div>
               			</div>
             			</s:if>
             			<s:else>
             				<div class="newColumn">
             					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
             					<div class="rightColumn1">
             						<s:if test="%{mandatory}">
              						<span class="needed"></span>
              					   </s:if>
									<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
								</div>
             				</div>
             			
             			</s:else>
             			</s:if>
               			<s:else>
               			<s:if test="%{key=='kr_starting_id'}">
               				<div class="newColumn">
                				<div class="leftColumn1"><s:property value="%{value}" />:</div>
							 <div class="rightColumn1"><s:if test="%{mandatory}">
              						<span class="needed"></span>
              					   </s:if>
								<s:textfield name="%{key}" id="%{key}" onclick="getAutoStatingId();" maxlength="%{field_length}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							</div>
						</div>
               			
               			</s:if>
               			<s:elseif test="%{key=='start_kr_id'}">
               			   <div class="newColumn">
                				<div class="leftColumn1"><s:property value="%{value}" />:</div>
							<div class="rightColumn1"><s:if test="%{mandatory}">
              						<span class="needed"></span>
              					   </s:if>
								<s:textfield name="%{key}" id="%{key}" cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							</div>
               			</div>
               			
               			</s:elseif>
               			<s:else>
               				<div class="newColumn">
                				<div class="leftColumn1"><s:property value="%{value}" />:</div>
							<div class="rightColumn1"><s:if test="%{mandatory}">
              						<span class="needed"></span>
              					   </s:if>
								<s:textfield name="%{key}" id="%{key}" cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							</div>
						</div>
               			
               			
               			</s:else>
               			
               			</s:else>
		</s:iterator>  
		
		
			<div class="clear" >
						<div style="padding-bottom: 10px;margin-left:-80px" align="center">
						<sj:submit 
				             targets="orglevel2" 
						     clearForm="true"
						     value="  Add  " 
						     effect="highlight"
						     effectOptions="{ color : '#222222'}"
						     effectDuration="5000"
						     button="true"
						     onCompleteTopics="level2"
						     cssClass="button"
						     indicator="indicator2"
						     onBeforeTopics="secondValidate"
			            />
			            &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 139px;margin-top: -40px;"
		                    onclick="resetTaskName('formtwo');"
			            />&nbsp;&nbsp;
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="viewDept();" cssStyle="margin-left: 266px;margin-top: -74px;" 
						>Back
					</sj:a>
					</div>
               </div>
	 				<div class="clear"></div>
   							
					<sj:div id="orglevel2Div"  effect="fold">
		                    <div id="orglevel2"></div>
		            </sj:div>
</s:form>
</sj:accordionItem>
</s:if>
</sj:accordion>
</div>
</div>
</body>
</html>