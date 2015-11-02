<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
function displayOptionsVal(val,divId)
{
	if(val=='Text Only')
	{
		$("#"+divId).hide();
	}
	else
	{
		$("#"+divId).show();
	}	
}
function changeSubQues(val,divId)
{
	//alert("divId ::: "+divId);
	if(val=='No')
	{
		$("#"+divId).hide();
	}
	else
	{
		$("#"+divId).show();
	}	
}
function viewQuestionair()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/questionairOver2Cloud/viewQuestionConf.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
</script>
</head>
<body>
	<div class="list-icon">
	 <div class="head">Questionnaire
</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Create</div> 
</div>
   <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
       <s:form id="questions" name="questions" namespace="/view/Over2Cloud/questionairOver2Cloud" action="questionAdd" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		    <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                 <div id="errZone" style="float:left; margin-left: 7px">
                 </div> 
           </div>
              <div class="newColumn">
					<div class="leftColumn">Questionnaire:</div>
					<div class="rightColumn">
                          <s:select 
                            name="questionSet"
                            id="questionSet"
                             headerKey="-1"
			                headerValue="Select Questionnaire" 
                            list="{'A','B','C','D','E'}"  
                            cssClass="select"
                            cssStyle="width:82%" 
                            >
                            </s:select>
                            
		            </div>
		     </div>  
		     <div class="clear"></div>
		       	<div class="newColumn" >
			  		<div class="leftColumn">Question 1:</div>
	           		<div class="rightColumn">
				         	<s:textfield  name="question"  cssStyle="width:82%" id="question"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
		        </div>
		        <div class="newColumn">
					<div class="leftColumn">Answer:</div>
					<div class="rightColumn">
                          <s:select 
                            name="answer"
                            id="answer"
                            list="{'Text Only','Radio Only','Checkbox Only','Radio & Text','Checkbox & Text'}" 
                            headerKey="-1"
			                headerValue="Select Answer" 
                            cssClass="select"
                            cssStyle="width:82%" 
                            onchange="displayOptionsVal(this.value,'optionsDiv');"
                            >
                            </s:select>
                            
		            </div>
		     </div> 
		       <div id="optionsDiv" style="display: none;">
		         <div class="clear"></div>
					     <div class="newColumn">
				  					 <div class="leftColumn">Option 1:</div>
		            		 		 <div class="rightColumn">
					              			<s:textfield cssStyle="width: 92%" name="options"  id="options"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					        		 </div>
			            		 </div>
			             <div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+++++" onclick="adddiv('200')" button="true">+</sj:a></div>

		    <s:iterator begin="200" end="205" var="deptIndx1" status="status">
				<div class="clear"></div>
				
				<div id="<s:property value="%{#deptIndx1}"/>" style="display: none;">
			          		     <div class="newColumn">
				  					 <div class="leftColumn">Option <s:property value="#status.count+1"/>:</div>
		            		 		 <div class="rightColumn">
					              			<s:textfield cssStyle="width: 92%" name="options"  id="options%{#deptIndx1}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					        		 </div>
			            		 </div>
			            		  
	           		 	<s:if test="#deptIndx1==205">
	           		 	    <div id="newDiv" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx1}')" button="true">-</sj:a></div>
		                </s:if>
		           		<s:else>
		           		    <div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+" onclick="adddiv('%{#deptIndx1+1}')" button="true">+</sj:a></div>
		                  	<div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx1}')" button="true">-</sj:a></div>
	           		   </s:else>
           		 <div class="clear"></div>
			   </div>
			</s:iterator>		 
			  </div>    
		      <div class="clear"></div>
		         <div class="newColumn">
					<div class="leftColumn">Upload:</div>
					<div class="rightColumn">
                          <s:select 
                            name="upload"
                            id="upload"
                            list="{'Yes','No'}" 
                            headerKey="-1"
			                headerValue="Select Upload" 
                            cssClass="select"
                            cssStyle="width:82%" 
                            >
                            </s:select>
                            
		            </div>
		     </div>  
		       <div class="newColumn">
					<div class="leftColumn">Mandatory:</div>
					<div class="rightColumn">
                          <s:select 
                            name="mandatory"
                            id="mandatory"
                            headerKey="-1"
			                headerValue="Select Mandatory"
                            list="{'Yes','No'}"  
                            cssClass="select"
                            cssStyle="width:82%" 
                            >
                            </s:select>
                            
		            </div>
		     </div>  
		      <div class="newColumn">
					<div class="leftColumn">Create Sub Question:</div>
					<div class="rightColumn">
                          <s:select 
                            name="subQuestion"
                            id="subQuestion"
                            headerKey="-1"
			                headerValue="Select Sub Question"
                            list="{'Yes','No'}"  
                            cssClass="select"
                            cssStyle="width:82%" 
                            onchange="changeSubQues(this.value,'subQuesDiv')"
                            >
                            </s:select>
                            
		            </div>
		     </div> 
		     
		     <div id="subQuesDiv" style="display: none;">
		       <div class="clear"></div>
		      	<div class="newColumn" >
			  		<div class="leftColumn">Question 1.1:</div>
	           		<div class="rightColumn">
				         	<s:textfield  name="questionSub"  cssStyle="width:82%" id="questionSub"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				    </div>
		        </div>
		        <div class="newColumn">
					<div class="leftColumn">Answer:</div>
					<div class="rightColumn">
                          <s:select 
                            name="answerSub"
                            id="answerSub"
                            list="{'Text Only','Radio Only','Checkbox Only','Radio & Text','Checkbox & Text'}" 
                            headerKey="-1"
			                headerValue="Select Answer" 
                            cssClass="select"
                            cssStyle="width:82%" 
                            onchange="displayOptionsVal(this.value,'optionsDiv1');"
                            >
                            </s:select>
                            
		            </div>
		     </div> 
		       <div id="optionsDiv1" style="display: none;">
		         <div class="clear"></div>
					     <div class="newColumn">
				  					 <div class="leftColumn">Option 1:</div>
		            		 		 <div class="rightColumn">
					              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="optionsSub"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					        		 </div>
			            		 </div>
			             <div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+++++" onclick="adddiv('800')" button="true">+</sj:a></div>

		    <s:iterator begin="800" end="805" var="deptIndx1" status="status">
				<div class="clear"></div>
				
				<div id="<s:property value="%{#deptIndx1}"/>" style="display: none;">
			          		     <div class="newColumn">
				  					 <div class="leftColumn">Option <s:property value="#status.count+1"/>:</div>
		            		 		 <div class="rightColumn">
					              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="optionsSub%{#deptIndx1}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					        		 </div>
			            		 </div>
			            		  
	           		 	<s:if test="#deptIndx1==805">
	           		 	    <div id="newDiv" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx1}')" button="true">-</sj:a></div>
		                </s:if>
		           		<s:else>
		           		    <div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+" onclick="adddiv('%{#deptIndx1+1}')" button="true">+</sj:a></div>
		                  	<div id="newDiv1" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx1}')" button="true">-</sj:a></div>
	           		   </s:else>
           		 <div class="clear"></div>
			   </div>
			</s:iterator>		 
			  </div>    
		      <div class="clear"></div>
		         <div class="newColumn">
					<div class="leftColumn">Upload:</div>
					<div class="rightColumn">
                          <s:select 
                            name="uploadSub"
                            id="uploadSub"
                            list="{'Yes','No'}" 
                            headerKey="-1"
			                headerValue="Select Upload" 
                            cssClass="select"
                            cssStyle="width:82%" 
                            >
                            </s:select>
                            
		            </div>
		     </div>  
		       <div class="newColumn">
					<div class="leftColumn">Mandatory:</div>
					<div class="rightColumn">
                          <s:select 
                            name="mandatorySub"
                            id="mandatorySub"
                            headerKey="-1"
			                headerValue="Select Mandatory"
                            list="{'Yes','No'}"  
                            cssClass="select"
                            cssStyle="width:82%" 
                            >
                            </s:select>
                            
		            </div>
		     </div>  
		      <div id="newDivSub" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+++++" onclick="adddiv('100')" button="true">+</sj:a></div>

		    <s:iterator begin="100" end="109" var="deptIndx" status="status">
				<div class="clear"></div>
				
				<div id="<s:property value="%{#deptIndx}"/>" style="display: none;">
			          		     <div class="newColumn">
				  					 <div class="leftColumn">Question 1.<s:property value="#status.count+1"/>:</div>
		            		 		 <div class="rightColumn">
					              			<s:textfield cssStyle="width: 92%" name="questionSub"  id="questionSub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					        		 </div>
			            		 </div>
			            		  <div class="newColumn">
								<div class="leftColumn">Answer:</div>
								<div class="rightColumn">
			                          <s:select 
			                            name="answerSub"
			                            id="answerSub%{#deptIndx}"
			                            headerKey="-1"
			                            headerValue="Select Answer"
			                            list="{'Text Only','Radio Only','Checkbox Only','Radio & Text','Checkbox & Text'}"  
			                            cssClass="select"
			                            cssStyle="width:82%" 
			                             onchange="displayOptionsVal(this.value,'optionsDivSub%{#deptIndx}');"
			                            >
			                            </s:select>
			                            
					            </div>
					     </div>  
					       <div id="optionsDivSub<s:property value="%{#deptIndx}"/>" style="display: none;">
					         <div class="clear"></div>
								     <div class="newColumn">
							  					 <div class="leftColumn">Option 1:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options1Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 2:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options2Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 3:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options3Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 4:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options4Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 5:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options5Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 6:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options6Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 7:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options7Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						            		  <div class="newColumn">
							  					 <div class="leftColumn">Option 8:</div>
					            		 		 <div class="rightColumn">
								              			<s:textfield cssStyle="width: 92%" name="optionsSub"  id="options8Sub%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
								        		 </div>
						            		 </div>
						        
						  </div>  
						    
					      <div class="clear"></div>
					         <div class="newColumn">
								<div class="leftColumn">Upload:</div>
								<div class="rightColumn">
			                          <s:select 
			                            name="uploadSub"
			                            id="uploadSub%{#deptIndx}"
			                            headerKey="-1"
			                            headerValue="Select Upload"
			                            list="{'Yes','No'}"  
			                            cssClass="select"
			                            cssStyle="width:82%" 
			                            >
			                            </s:select>
			                            
					            </div>
					     </div>  
					       <div class="newColumn">
								<div class="leftColumn">Mandatory:</div>
								<div class="rightColumn">
			                          <s:select 
			                            name="mandatorySub"
			                            id="mandatorySub%{#deptIndx}"
			                            headerKey="-1"
			                            headerValue="Select Mandatory"
			                            list="{'Yes','No'}"  
			                            cssClass="select"
			                            cssStyle="width:82%" 
			                            >
			                            </s:select>
			                            
					            </div>
					     </div>  
					     
	           		 	<s:if test="#deptIndx==109">
	           		 	    <div id="newDiv" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
		                </s:if>
		           		<s:else>
		           		    <div id="newDiv" style="float: left;width: 7%;margin-top: 7px; margin-left: -1%;"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
		                  	<div id="newDiv" style="float: left;width: 7%;margin-top: 7px; margin-left: -4%;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
	           		   </s:else>
           		 <div class="clear"></div>
			   </div>
			</s:iterator>
		     </div>
             	 <div class="clear"></div>
             	 	 <div class="clear"></div>
             	 	 	 <div class="clear"></div>
				
	    <div class="fields" align="center">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                        targets="target1" 
	                        id="buttonid"
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#FFFFFF'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        onBeforeTopics="validate"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        cssStyle="margin-left: -40px;"
	                        />
	                       
						
						
				<sj:a cssStyle="margin-left: 181px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtaskType');">Reset</sj:a>
				<sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewQuestionair();">Back</sj:a>  
	        </div>
	        </li>
		  </ul>
	      </div>
	      <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>
