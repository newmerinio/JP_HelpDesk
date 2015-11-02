
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:if test="%{dataFrom=='KR'}">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</s:if>

<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/KRValidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<link href="js/TagsJS/jquery.tagsinput.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/TagsJS/jquery.tagsinput.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/TagsJS/jquery.tagsinput.min.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('completeData', function(event,data)
 {
   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
   setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
   $('select').find('option:first').attr('selected', 'selected');
   $.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=KR",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
           alert("error");
       }
	 });
 });

</SCRIPT>
<script type="text/javascript">
$('#tag_search').tagsInput();
$('#tag_search100').tagsInput();
$('#tag_search101').tagsInput();
$('#tag_search102').tagsInput();
$('#tag_search103').tagsInput();

</script>
<script type="text/javascript">
function formReset()
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action",
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

</script>
</head>
<body>

<div class="clear"></div>
<s:if test="%{dataFrom=='KR'}">
	<div class="list-icon">
	 <div class="head">KR Library</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div> 
</s:if>
<div class="clear"></div>
 <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="uploadKRdata" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
							<div id="errZone" style="float:center; margin-left: 7px"></div>
			</div>
			</center>
				<s:hidden id="indexVal" name="indexVal" value="1"/>	
			    <div class="newColumn">
				<div class="leftColumn1">Contact Sub Type:</div><span id="gggggg" class="pIdsKR" style="display: none; ">deptName#Department#D#,</span>
				<div class="rightColumn1">
					<span class="needed"></span>
			          <s:select 
                              id="deptName"
                              list="deptName"
                              headerKey="-1"
                              headerValue="Select Contact Sub Type" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchGroup(this.value,'groupNameNew')"
                              
                              >
                  		</s:select>
				</div>
			</div>
			
									
			<s:iterator value="KRUploadTextBox" begin="1" end="2">
              			<s:if test="%{key=='group_name'}">
              				<div class="newColumn">
              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
              					<div class="rightColumn1">
              					<span id="mandatoryFields" class="pIdsKR" style="display: none; ">groupNameNew#<s:property value="%{value}"/>#D#a,</span>
         								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
         								   <s:select 
					                              id="groupNameNew"
					                              list="{'No Data'}"
					                              headerKey="-1"
					                              headerValue="Select Group" 
					                              cssClass="select"
					                              cssStyle="width:82%"
					                              onchange="getSubGrp(this.value,'sub_group_name');"
					                              
					                              >
					                  		</s:select>
         								 
						</div>
              				</div>
              			</s:if>
              			<s:elseif test="%{key=='sub_group_name'}" >
              			<span id="mandatoryFields" class="pIdsKR" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              				<div class="newColumn">
               				<div class="leftColumn1"><s:property value="%{value}" />:</div>
							<div class="rightColumn1">  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
								  <s:select 
	                              id="%{key}"
	                              name="%{key}" 
	                              list="#{'-1':'Select'}"
	                              headerKey="-1"
	                              headerValue="Select Sub Group" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              
	                              >
	                  		</s:select>
							</div>
					</div>
              			</s:elseif>
              		
			</s:iterator>  
			<div class="clear"></div>

				<s:iterator value="KRUploadTextBox" status="counter" begin="0" end="0">
				<span id="mandatoryFields" class="pIdsKR" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              			<s:if test="%{key=='kr_name'}">
              			<div class="newColumn">
              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
              					<div class="rightColumn1">
         							  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
						</div>
              				</div>
              			</s:if>
			</s:iterator>
			
			<s:iterator value="KRUploadFile" status="counter">
			   <span id="mandatoryFields" class="pIdsKR" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              			<s:if test="#counter.odd">
              				<div class="newColumn">
              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
              					<div class="rightColumn1">
         							  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										<s:file name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
						       </div>
              				</div>
              			</s:if>
              			<s:else>
              				<div class="newColumn">
               				<div class="leftColumn1"><s:property value="%{value}" />:</div>
						    <div class="rightColumn1">  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							<s:file name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
						</div>
					</div>
              			</s:else>
			</s:iterator>
			
				<s:iterator value="KRUploadTextBox" status="counter" begin="4">
					<span id="mandatoryFields" class="pIdsKR" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              			<s:if test="#counter.odd">
              			<s:if test="%{key=='tag_search'}">
              			<div class="newColumn">
              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
              					<div class="rightColumn1">
         								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										<s:textfield name="%{key}"  id="%{key}" maxlength="%{field_length}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
						</div>
              				</div>
              			</s:if>
              			<s:else>
              			<div class="newColumn">
              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
              					<div class="rightColumn1">
         								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
										<s:textarea name="%{key}"  id="%{key}" maxlength="%{field_length}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
						</div>
              				</div>
              			</s:else>
              				
              			</s:if>
              			<s:else>
              				<div class="newColumn">
               				<div class="leftColumn1"><s:property value="%{value}" />:</div>
						  <div class="rightColumn1">  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							<s:textarea name="%{key}" id="%{key}" cols="2" rows="2"   cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
						    <s:radio list="#{'Public':'Public','Private':'Private' }" name="access_type" id="accessType" value="%{'Public'}"></s:radio>
						
						</div>
						<sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 543px;margin-top: -45px;">+</sj:a>
					     </div>
              			</s:else>
			</s:iterator>
			 
			     <s:iterator begin="100" end="103" var="deptIndx">
						<div class="clear"></div>
						 <div id="<s:property value="%{#deptIndx}"/>" style="display: none;">
						 
						 		<s:iterator value="KRUploadTextBox" status="counter" begin="0">
						              			<s:if test="%{key=='kr_name'}">
						              			<div class="newColumn">
						              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						              					<div class="rightColumn1">
												    <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" maxlength="%{field_length}" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
												</div>
					              				</div>
						              			</s:if>
						              		
							</s:iterator>
					      	<s:iterator value="KRUploadFile" status="counter">
					              			<s:if test="#counter.odd">
					              				<div class="newColumn">
					              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					              					<div class="rightColumn1">
															<s:file name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
											</div>
					              		    </div>
					              			</s:if>
					              			<s:else>
					              				<div class="newColumn">
					               				<div class="leftColumn1"><s:property value="%{value}" />:</div>
											<div class="rightColumn1">
												<s:file name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
											</div>
										</div>
					              			</s:else>
								</s:iterator>
								<s:iterator value="KRUploadTextBox" status="counter" begin="4">
					              			<s:if test="#counter.odd">
					              			<s:if test="%{key=='tag_search'}">
					              			<div class="newColumn">
					              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					              					<div class="rightColumn1">
															<s:textarea name="%{key}"  id="%{key}%{#deptIndx}"  maxlength="%{field_length}" cssClass="textField" placeholder="Enter Data1111" cssStyle="margin:0px 0px 10px 0px;"/>
													</div>
					              				   </div>
					              			</s:if>
					              			<s:else>
					              			<div class="newColumn">
					              					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					              					<div class="rightColumn1">
					              					<span id="mandatoryFields" class="pIdsKR" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#T#a,</span>
					         								 <span class="needed"></span>
															<s:textarea name="%{key}"  id="%{key}" maxlength="%{field_length}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
											     </div>
					              				</div>
					              			</s:else>
					              			</s:if>
					              			<s:else>
					              				<div class="newColumn">
					               				<div class="leftColumn1"><s:property value="%{value}" />:</div>
											   <div class="rightColumn1">
												<s:textarea name="%{key}" id="%{key}%{#deptIndx}2" maxlength="%{field_length}" cols="2" rows="2"   cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
											    <s:radio list="#{'Public':'Public','Private':'Private' }" name="access_type%{#deptIndx}" id="accessType%{#deptIndx}" value="%{'Private'}"></s:radio>
											
											</div>
											<div style="margin-top: -37px;margin-left: 429px;">
												<s:if test="#deptIndx==113">
													<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
												</s:if>
					           					<s:else>
					          	 					<div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
					          						<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
					       						</s:else>
											 </div>
										     </div>
					              			</s:else>
							      </s:iterator>
						</div>
						   
					</s:iterator>
				
			<!-- Buttons -->
			<div class="clear"></div>
			 <center><img id="indicator33" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div class="fields">
			<div style="width: 100%; text-align: center; padding-bottom: 10px;">
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
				     indicator="indicator33"
				     onBeforeTopics="validateUpload"
				     cssStyle="margin-left: -212px;margin-bottom: -29px;"
			     />
				   <sj:a 
						button="true" href="#"
						onclick="resetTaskName('formone');"
						>
						Reset
					</sj:a>
					<s:if test="%{dataFrom=='KR'}">
     		  	 		 <sj:a 
							button="true" href="#"
							onclick="viewUploadKR();"
							>
							Back
						</sj:a>
					</s:if>
					
	 <sj:div id="foldeffect"  effect="fold">
                <div id="Result"></div>
           </sj:div>
			    
			</div>
		</div>
</s:form>
</div>
</div>
</body>
</html>
