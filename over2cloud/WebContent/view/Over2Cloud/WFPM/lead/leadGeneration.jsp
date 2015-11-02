<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
</style>
<html>
<head>
<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>
<SCRIPT type="text/javascript">
 

</script>
<script type="text/javascript">
function hide0rShow()
{
     var data=$("#"+"targetID").val();
              
          if(data == "corporate")
          {
               $("#leadTypeValue").val(data);
    	      document.getElementById("A_corporate").style.display="block";
    	      document.getElementById("B_individual").style.display="none"
    	  }
          else  if(data == "individual")
          {    $("#leadTypeValue").val(data);
               document.getElementById("B_individual").style.display="block";
               document.getElementById("A_corporate").style.display="none";
          }
          else
          {
        	 $("#leadTypeValue").val("");
      
        	 document.getElementById("A_corporate").style.display="none";
    	     document.getElementById("c_corporate").style.display="none";
    	     document.getElementById("B_individual").style.display="none"
    	   }
 }

</script>
</head>
<body>
<div class="list-icon">
<div class="head"><s:property value="%{mainHeaderName}"/>New Lead</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Add</div>
</div>
   
   <div style=" float:left; padding:0px 0%; width:100%;">
     <div class="border" style="overflow-x:hidden;">
         <div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		    <div id="errZone" style="float: left; margin-left: 7px"></div>
	     </div>
	     <br/>
         <div style="width: 100%; text-align: center; padding-top: 0px;">	
   	         <div class="menubox">
	            <div class="newColumn" id="targetOption">
			    <div class="leftColumn1">Lead Type:</div>
			    <div class="rightColumn1">
			    <span class="needed"></span>
			        <s:select 
						id="targetID" 
						name="targetName"
						list="#{'corporate':'Corporate','individual':'Individual'}"
						headerKey="-1" 
						headerValue="Select"
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="hide0rShow();"
						>
					</s:select>
			     </div>
                 </div>	
             </div>	
         </div>
         <div class="clear"></div>    	
 <div id="A_corporate" style="display: none;"> 		
<sj:accordion id="accordion" autoHeight="false">
         <!-- Lead Basic Data ************************************************************************************ -->
<s:if test="leadBasicDetails == 1">
   <sj:accordionItem title="Lead Basic Details" id="leadBasicAccId">
           	
               <div class="form_inner" id="form_reg" style="margin-top: 10px;">
                    <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/Lead" action="leadGenerationAdd" theme="simple" method="post" enctype="multipart/form-data">
                    
	                       <div class="menubox" style="margin-bottom: 5px;margin-top: 10px">		
                                  <s:iterator value="leadBasicControls" status="counter">
	 			                     <s:if test="%{mandatory}">
                                           <span id="mandatoryField" class="mIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                                     </s:if>  
	                                 <s:if test='%{colType == "T"}'>
		                                <div class="newColumn">
		                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                                <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                                <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		                                </div>
		                                </div>
	                                 </s:if>
	                              </s:iterator>	      
	          
	              <s:if test="%{industryExit != null}">
		                  <div class="newColumn">
			                     <div class="leftColumn1"><s:property value="%{industry}"/>:</div>
			                     <div class="rightColumn1">
			                     <s:if test="%{industryExit == 'true'}">
			                           <span id="mandatoryFieldss" class="mIds" style="display: none; ">industryID#<s:property value="%{industry}"/>#D#,</span>
			                           <span class="needed"></span>
			                     </s:if>      
			                     <s:select
							          id="industryID" 
							          name="industry" 
							          list="industryList" 
							          headerKey="-1"
							          headerValue="Select Industry" 
							          cssClass="select"
                                      cssStyle="width:82%"
                                      onclick="fillName(this.value,'subIndustryID')"
                                      >
				                 </s:select>
			                     </div>
			              </div>	
		          </s:if>
		         
		          <s:if test="%{subindustryExit != null}">
		                         
	                    <div class="newColumn">
			            <div class="leftColumn1"><s:property value="%{subIndustry}"/>:</div>
			            <div class="rightColumn1">
			                  <s:if test="%{subindustryExit == 'true'}">
			                        <span id="mandatoryFieldss" class="mIds" style="display: none; ">subIndustryID#<s:property value="%{subIndustry}"/>#D#,</span>
			                        <span class="needed"></span>
			                  </s:if>      
			            <s:select
							id="subIndustryID" 
							name="subIndustry" 
							list="#{'-1':'Select Sub-Industry'}" 
							cssClass="select"
                            cssStyle="width:82%">
				        </s:select>
			           </div>
			           </div>	
		         </s:if>
	             <s:if test="%{sourceExit != null}">
		            <div class="newColumn">
			        <div class="leftColumn1"><s:property value="%{sourceName}"/>:</div>
			        <div class="rightColumn1">
			            <s:if test="%{sourceExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">sourceName#<s:property value="%{sourceName}"/>#D#,</span>
			                <span class="needed"></span>
			            </s:if>
			            <s:select
							id="sourceName" 
							name="sourceName" 
							list="sourceList" 
							headerKey="-1"
							headerValue="Select Source" 
							cssClass="select"
                            cssStyle="width:82%">
				        </s:select>
			        </div>
			        </div>	
		        </s:if>
	            <s:if test="%{referedByExit != null}">
         	       <div class="newColumn">
	               <div class="leftColumn1"><s:property value="%{referedBy}"/>:</div>
	               <div class="rightColumn1">
	                       <s:if test="%{referedByExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">referedBy#<s:property value="%{referedBy}"/>#D#,</span>
	                            <span class="needed"></span>
	                       </s:if>
	                  <s:select 
		                 id="referedBy"	
		                 name="referedBy" 
		                 list="#{'Client':'Opportunity','Existing_Client':'Existing Client','Associate':'Prospective Associate','Existing_Associate':'Existing Associate'}"
		                 headerKey="-1"
		                 headerValue="Select" 
		                 cssClass="select"
	                     cssStyle="width:82%"
	                     onchange="fetchReferredName(this.value,'refName',0)"
	                      >
	                  </s:select>
	               </div>
	               </div>
              </s:if>
              <div id="refNameHideShow" style="display: none;">
              <s:if test="%{refNameExit != null}">
                     <div class="newColumn">
	                 <div class="leftColumn1"><s:property value="%{refName}"/>:</div>
	                 <div class="rightColumn1">
	                      <s:if test="%{refNameExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">refName#<s:property value="%{refName}"/>#D#,</span>
	                            <span class="needed"></span>
	                      </s:if>
	                      <s:select 
                               id="refName"
                               name="refName" 
                               list="#{'-1':'Select'}"
                               cssClass="select"
                               cssStyle="width:82%"
                              >
                          </s:select>
	                 </div>
	                 </div>
	     
	          </s:if>
	          </div>   
              <s:if test="%{starRatingExit != null}">
		            <div class="newColumn">
			        <div class="leftColumn1"><s:property value="%{starRating}"/>:</div>
			        <div class="rightColumn1">
			             <s:if test="%{starRatingExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">starRating#<s:property value="%{starRating}"/>#D#,</span>
			                 <span class="needed"></span>
			             </s:if>                                    
			           <s:select 
						 id="starRating" 
						 name="starRating"
						 list="#{'1':'1-Low Potential','2':'2-Normal Potential','3':'3-Medium Potential','4':'4-High Potential','5':'5-Very High Potential'}"
				         headerKey="-1" 
						 headerValue="Select Rating"
						 cssClass="select"
                         cssStyle="width:82%"
						>
				      </s:select>
			       </div>
			       </div>	
		     </s:if>
	         <s:if test="%{locationExit != null}">
		           <div class="newColumn">
			       <div class="leftColumn1"><s:property value="%{name}"/>:</div>
			       <div class="rightColumn1">
			       <s:if test="%{locationExit == 'true'}"> <span id="mandatoryFieldss" class="mIds" style="display: none; ">locationID#<s:property value="%{name}"/>#D#,</span>
			       <span class="needed"></span>
			       </s:if>
			          <s:select
							id="locationID" 
							name="name" 
							list="locationList"
							headerKey="-1"
							headerValue="Select Location" 
							cssClass="select"
                            cssStyle="width:82%">
				     </s:select>
			     </div>
			     </div>	
		    </s:if> 
	          
	
</div>	
	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="leadresult" 
		     clearForm="true"
		     value="Save" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level1"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate1"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetForm('formone');"
		>
		  	Reset
		</sj:a> 
	     <sj:a 
	     	name="Cancel"  
			href="#" 
			targets="result" 
			cssClass="submit" 
			indicator="indicator"
			button="true" 
			onclick="viewLead()" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	          <div class="clear"></div>
	          <center>
		      <sj:div id="leadgntion"  effect="fold">
                  <div id="leadresult"></div>
              </sj:div>
              </center>
	<br>
	
	</s:form>
  </div>
</sj:accordionItem>
</s:if>

<!-- Lead Contact Data *********************************************************************************** -->
<sj:accordionItem title="Lead Contact Details" id="leadContactAccId">
<s:if test="leadContacDetails == 1">
<s:form id="formThree" name="formThree" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/Lead" 
	                                          action="addLeadContacts" theme="simple"  method="post" enctype="multipart/form-data" >
	     
       <s:hidden id="leadTypeValue" name="leadTypeValue"/>
    <div class="menubox"> 
             <s:if test="%{mandatory}">    
                          <span id="mandatoryField" class="pIds" style="display: none; ">leadNameID#Lead Name#D#a,</span>
             </s:if>                
	           <div class="newColumn">
			   <div class="leftColumn1"><s:property value="%{leadNameValue}"/>:</div>
			   <div class="rightColumn1"><span class="needed"></span>
			       <s:select 
			     	  id="leadNameID"
			     	  name="leadName"
			     	  list="#{'-1':' Select Lead '}"
			     	  cssClass="textField"
			     	  cssStyle="width:82%"
			          >
			       </s:select>
			   </div>
	           </div>
	           <div class="clear"></div>
	           
	           <s:iterator value="leadContactControls" status="status">
	               <s:if test="%{mandatory}">
                            <span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	               </s:if>
	
	              <s:if test="%{key == 'personName' }">
		             <div class="newColumn" style="  margin-left: 530px;  margin-top: -43px;">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:if>
	              <s:elseif test="%{key == 'designation' }" >
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:elseif test="%{key == 'contactNo' }" >
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:elseif test="%{key == 'emailOfficialContact'}">
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:else>
	              </s:else>
	        </s:iterator>	
  	
         <s:iterator value="leadContactControls" status="status">	
	         <s:if test="%{key == 'department'}">
	              <s:if test="%{mandatory}">
		                <span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
			      <div class="newColumn">
			      <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			      <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
			        <s:select 
			     	   id="department"
			     	   name="department"
			     	   list="deptMap"
			     	   headerKey="-1"
	                   headerValue="Select Department" 
			     	   cssClass="textField"
			     	   cssStyle="width:82%"
			     	   >
			        </s:select>
			      </div>
			      </div>	
		      </s:if>
	     </s:iterator>
         <s:iterator value="leadContactControls" status="status">
              <s:if test="%{key == 'notes' }">
		            <div class="newColumn" style=" margin-right: 10px;">
		            <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		            <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		            <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		            </div>
	                </div>
	          </s:if>
         </s:iterator>	
         <div class="clear"></div>	
         <s:iterator value="leadContactControls" status="status">
            <s:if test="%{key == 'otherInfo' }">
		    <div class="newColumn" style=" margin-left: 531px; margin-top: -52px;">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:if>
         </s:iterator>	
         
         <s:iterator value="leadContactControls" status="status">
            <s:if test="%{key == 'patient_age' }">
		    <div class="newColumn">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:if>
            <s:elseif test="%{key == 'pateint_sex' }">
		    <div class="newColumn">
		    <div class="leftColumn1">Gender:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:select 
			     	   id="%{key}"
		               name="%{key}" 
			           list="#{'Male':'Male','Female':'Female'}"
			     	   headerKey="-1"
	                   headerValue="Select Gender" 
			     	   cssClass="textField"
			     	   cssStyle="width:82%"
			     	   >
			        </s:select>
		         
		         <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    -->
		    </div>
	        </div>
	        </s:elseif>
	        
	        <s:elseif test="%{key == 'allergic_to' }">
		    <div class="newColumn" style="  margin-top: 10px;  margin-left: -529px;">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:elseif>
         
            <s:elseif test="%{key=='blood_group'}">
		    <div class="newColumn" style="  margin-top: -54px;  margin-left: 530px;">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:select 
			     	   id="%{key}"
		               name="%{key}" 
			           list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
			     	   headerKey="-1"
	                   headerValue="Select Gender" 
			     	   cssClass="textField"
			     	   cssStyle="width:82%"
			     	   >
			        </s:select>
		    </div>
	        </div>
	        </s:elseif>
         
         </s:iterator>	
         
         <br>
          <div class="clear"></div>
                   <div id="newDiv" style="float: right;margin-top: -35px;margin-right: 30px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
         <!-- changes by manab -->
         <s:iterator begin="100" end="103" var="deptIndx">
    <div class="clear"></div>
    <div id="<s:property value="%{#deptIndx}"/>" style="display: none">
    

               <div class="clear"></div>
               
               <s:iterator value="leadContactControls" status="status">
                   <s:if test="%{mandatory}">
                            <span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
    
                  <s:if test="%{key == 'personName' }">
                     <div class="newColumn">
                     <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                     <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
                     </div>
                     </div>
                  </s:if>
                  <s:elseif test="%{key == 'designation' }" >
                     <div class="newColumn">
                     <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                     <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
                     </div>
                     </div>
                  </s:elseif>
                  <s:elseif test="%{key == 'contactNo' }" >
                     <div class="newColumn">
                     <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                     <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
                     </div>
                     </div>
                  </s:elseif>
                  <s:elseif test="%{key == 'emailOfficialContact'}">
                     <div class="newColumn">
                     <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                     <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
                     </div>
                     </div>
                  </s:elseif>
                  <s:else>
                  </s:else>
            </s:iterator>    
                <s:iterator value="leadContactControls" status="status">    
             <s:if test="%{key == 'department'}">
                  <s:if test="%{mandatory}">
                        <span id="mandatoryField" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <div class="newColumn">
                  <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                  <div class="rightColumn1"><s:if test="%{mandatory}"><span class="needed"></span></s:if>
                    <s:select 
                        id="department"
                        name="department"
                        list="deptMap"
                        headerKey="-1"
                       headerValue="Select Department" 
                        cssClass="textField"
                        cssStyle="width:82%"
                        >
                    </s:select>
                  </div>
                  </div>    
              </s:if>
         </s:iterator>
         <s:iterator value="leadContactControls" status="status">
              <s:if test="%{key == 'notes' }">
                    <div class="newColumn">
                    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                    <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
                    </div>
                    </div>
              </s:if>
         </s:iterator>    
         
   
         
         <div class="clear"></div>    
         <s:iterator value="leadContactControls" status="status">
            <s:if test="%{key == 'otherInfo' }">
            <div class="newColumn">
            <div class="leftColumn1"><s:property value="%{value}"/>:</div>
            <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
            </div>
            </div>
            </s:if>
         </s:iterator>    
         
          <s:iterator value="leadContactControls" status="status">
            <s:if test="%{key == 'patient_age' }">
		    <div class="newColumn">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:if>
            <s:elseif test="%{key == 'pateint_sex' }">
		    <div class="newColumn">
		    <div class="leftColumn1">Gender:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:select 
			     	   id="%{key}"
		               name="%{key}" 
			           list="#{'Male':'Male','Female':'Female'}"
			     	   headerKey="-1"
	                   headerValue="Select Gender" 
			     	   cssClass="textField"
			     	   cssStyle="width:82%"
			     	   >
			        </s:select>
		         
		         <!--<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    -->
		    </div>
	        </div>
	        </s:elseif>
	        
	        <s:elseif test="%{key == 'allergic_to' }">
		    <div class="newColumn" style="  margin-top: 2px;">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:elseif>
         
            <s:elseif test="%{key=='blood_group'}">
		    <div class="newColumn" style="  margin-top: -8px;  margin-left: 0px;">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:select 
			     	   id="%{key}"
		               name="%{key}" 
			           list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
			     	   headerKey="-1"
	                   headerValue="Select Gender" 
			     	   cssClass="textField"
			     	   cssStyle="width:82%"
			     	   >
			        </s:select>
		    </div>
	        </div>
	        </s:elseif>
         
         </s:iterator>		
         
               <div style="float: right;margin-top: -35px;margin-right: -21px;">    
         <s:if test="#deptIndx==103">
            <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
        </s:if>
           <s:else>
               <div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
               <div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
         </s:else>
    </div>
         
    </div>
    </s:iterator>
    
         
         
         <!-- changes end -->
	     <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	     <div class="buttonStyle">
	         <sj:submit 
	           		   targets="assoresult1"
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="leadContactSuccess"
                       cssClass="submit"
                       indicator="indicator4"
                       onBeforeTopics="validate2"
	          />
	          
	          <sj:a 
		     	name="Reset"  
				href="#" 
				button="true"
				cssClass="submit" 
				onclick="resetForm('formThree');"
				>
				  	Reset
				</sj:a>
	          <sj:a
	     	    name="Cancel"  
				href="#" 
				button="true"
				cssClass="submit" 
				indicator="indicator" 
				onclick="viewLead()"
				style="margin-left:4px;"
	         >
	  	     Back
	    </sj:a>
	    </div>
	    <center>
	    <sj:div id="pAssociate"  effect="fold">
    	     <div id="assoresult1"></div>
        </sj:div>
        </center>
        <br>
   </div>	<!-- End menu box -->    
</s:form>
</s:if>  
</sj:accordionItem>
</sj:accordion>
</div>
<!--**************************Individual************************-->
 
<div id="B_individual" style="display: none;">
<div class="border" style="overflow-x:hidden;"> 
<div class="clear"></div> 
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Individual Contact Details" id="leadContactAccId">
<s:if test="leadContacDetails == 1">
<s:form id="formFour" name="formFour" cssClass="cssn_form" namespace="/view/Over2Cloud/WFPM/Lead" 
	                                          action="addLeadContacts" theme="simple"  method="post" enctype="multipart/form-data" >
	             <s:hidden id="leadTypeValue" name="leadTypeValue"></s:hidden>
    <div class="menubox"> 
             <s:if test="%{mandatory}">    
                          <span id="mandatoryField" class="pIds" style="display: none; ">leadNameID#Lead Name#D#a,</span>
             </s:if>                
	           
	           <div class="clear"></div>
	           
	           <s:iterator value="leadContactControls" status="status"><!--
	               <s:if test="%{mandatory}">
                            <span id="mandatoryField" class="qIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	               </s:if>
	
	              --><s:if test="%{key == 'personName' }">
	                 <s:if test="%{mandatory}">
                        <span id="mandatoryField" class="qIds" style="display: none; ">personNameId#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
		             <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="personNameId"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	                 </s:if>
	              <s:elseif test="%{key == 'designation' }" >
	                 <s:if test="%{mandatory}">
                        <span id="mandatoryField" class="qIds" style="display: none; ">designationId#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="designationId"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:elseif test="%{key == 'contactNo' }" >
	                 <s:if test="%{mandatory}">
                        <span id="mandatoryField" class="qIds" style="display: none; ">contactId#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="contactId"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:elseif test="%{key == 'emailOfficialContact'}">
	                 <s:if test="%{mandatory}">
                        <span id="mandatoryField" class="qIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
	                 <div class="newColumn">
		             <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		             <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		                         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		             </div>
	                 </div>
	              </s:elseif>
	              <s:else>
	              </s:else>
	        </s:iterator>	
	        
	          <s:if test="%{starRatingExit != null}">
		            <div class="newColumn">
			        <div class="leftColumn1"><s:property value="%{starRating}"/>:</div>
			        <div class="rightColumn1">
			             <s:if test="%{starRatingExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">starRating#<s:property value="%{starRating}"/>#D#,</span>
			                 <span class="needed"></span>
			             </s:if>                                    
			           <s:select 
						 id="starRating" 
						 name="star"
						 list="#{'1':'1-Low Potential','2':'2-Normal Potential','3':'3-Medium Potential','4':'4-High Potential','5':'5-Very High Potential'}"
				         headerKey="-1" 
						 headerValue="Select Rating"
						 cssClass="select"
                         cssStyle="width:82%"
						>
				      </s:select>
			       </div>
			       </div>	
		     </s:if>
		     <s:if test="%{sourceExit != null}">
		            <div class="newColumn">
			        <div class="leftColumn1"><s:property value="%{sourceName}"/>:</div>
			        <div class="rightColumn1">
			            <s:if test="%{sourceExit == 'true'}"><span id="mandatoryFieldss" class="mIds" style="display: none; ">sourceName#<s:property value="%{sourceName}"/>#D#,</span>
			                <span class="needed"></span>
			            </s:if>
			            <s:select
							id="sourceName" 
							name="source" 
							list="sourceList" 
							headerKey="-1"
							headerValue="Select Source" 
							cssClass="select"
                            cssStyle="width:82%">
				        </s:select>
			        </div>
			        </div>	
		     </s:if>
	         <s:if test="%{locationExit != null}">
		           <div class="newColumn">
			       <div class="leftColumn1"><s:property value="%{name}"/>:</div>
			       <div class="rightColumn1">
			       <s:if test="%{locationExit == 'true'}"> <span id="mandatoryFieldss" class="mIds" style="display: none; ">locationID#<s:property value="%{name}"/>#D#,</span>
			       <span class="needed"></span>
			       </s:if>
			          <s:select
							id="locationID" 
							name="location" 
							list="locationList"
							headerKey="-1"
							headerValue="Select Location" 
							cssClass="select"
                            cssStyle="width:82%">
				     </s:select>
			     </div>
			     </div>	
		    </s:if> 
         <s:iterator value="leadContactControls" status="status">
            <s:if test="%{key == 'otherInfo' }">
		    <div class="newColumn">
		    <div class="leftColumn1"><s:property value="%{value}"/>:</div>
		    <div class="rightColumn1"><s:if test="%{mandatory == true}"><span class="needed"></span></s:if>
		         <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
		    </div>
	        </div>
	        </s:if>
         </s:iterator>	
         <br>
	     <div class="clear"></div>
	     <center><img id="indicator4" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	     <div class="buttonStyle">
	         <sj:submit 
	           		   targets="assoresult12"
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level12"
                       cssClass="submit"
                       indicator="indicator4"
                       onBeforeTopics="validate3"
	          />
	          
	          <sj:a 
		     	name="Reset"  
				href="#" 
				button="true"
				cssClass="submit" 
				onclick="resetForm('formFour');"
				>
				  	Reset
				</sj:a>
	          <sj:a
	     	    name="Cancel"  
				href="#" 
				button="true"
				cssClass="submit" 
				indicator="indicator" 
				onclick="viewLead()"
				style="margin-left:4px;"
	         >
	  	     Back
	    </sj:a>
	    </div>
	    <center>
	    <sj:div id="pAssociate12"  effect="fold">
    	     <div id="assoresult12"></div>
        </sj:div>
        </center>
        <br>
   </div>	<!-- End menu box -->    
</s:form>
</s:if>  
</sj:accordionItem>
</sj:accordion>
</div></div>
<!-- End individual -->
	
</div>
</div>
<SCRIPT type="text/javascript">
       fillLeadData('leadNameID');
</SCRIPT>
</body>
</html>