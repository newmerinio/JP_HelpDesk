<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="js/WFPM/ActivityPlanner/activity_planner.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function reset(formId)
{
    $('#'+formId).trigger("reset");
    setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}
$.subscribe('level1', function(event,data)
{
   setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
   setTimeout(function(){ $("#complTarget").fadeOut();cancelButton(); }, 12000);
   $('#completionResult').dialog('open');
});
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 viewGroup() ;
}
function viewGroup() 
{
	 $('#abc').dialog('close');
}
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
function changeDiv(status,div)
{
	//alert(status);
	
	if(status=='Change in plan')
	{
		$("#"+div).hide();
		 $('#abc').dialog('close');
         var datev=$("#dateVal").val();
         $('#abcd').dialog('open');
     	$('#abcd').dialog({title: 'Schedule Activity',height: '650',width:'1250'});
         $("#activityy").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     	$.ajax({ 
     	    type : "post",
     	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityPlanner.action?date="+datev+"&status=changePlan",
     	    success : function(subdeptdata) 
     	   {
     			$("#"+"activityy").html(subdeptdata);
     	   },
     	   error: function() 
     	   {
                alert("error");
           }
     	 });
		
	}
	else if(status=='No Activity' || status=='Office work')
	{
		$("#expenseDiv").hide();
		$("#"+div).show();
	}
	else if(status=='As per plan')
	{
		$("#"+div).hide();
		$("#expenseDiv").show();
	}
}
</script>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="complTarget"></div>
</sj:dialog>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/ActivityPlanner" action="addDsr" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
	          <center>
	            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:center; margin-left: 7px"></div>        
	            </div>
	          </center>
	          <s:hidden id="dateVal" value="%{date}"></s:hidden>
	          <s:hidden id="activityId" name="activityId" value="%{activityId}"/>
		               <table width="100%" align="center">
							<tr bgcolor="#D8D8D8"  style="height: 25px;">
								<td><b>Month:</b></td>
								<td><s:property value="%{date}" /></td>
								<td><b>Activity For:</b></td>
								<td><s:property value="%{activity_for}" /></td>
							</tr>
							<tr style="height: 25px;">
								<td><b>Activity Type:</b></td>
								<td><s:property value="%{activityType}" /></td>
								<td><b>Budget Amount:</b></td>
								<td><s:property value="%{amount}" /></td>
							</tr>
							<tr bgcolor="#D8D8D8"  style="height: 25px;">
								<td><b>Schedule Date:</b></td>
								<td><s:property value="%{sch_date}" /></td>
								<td><b>Comment:</b></td>
								<td><s:property value="%{comm}" /></td>
							</tr>
						</table>
	                <s:iterator value="dropDown" status="counter" begin="0" end="1">
		                 <s:if test="%{key=='activity_status'}">
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                               <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		 <s:select 
				                                      id="%{key}"
				                                      name="%{key}"
				                                      list="{'As per plan','Change in plan','No Activity','Office work'}"
				                                      headerKey="-1"
				                                      headerValue="Select %{value}" 
				                                      cssClass="textField"
				                                      onchange="changeDiv(this.value,'notComplDiv');"
				                                      >
	                                            </s:select>                
			                                </div>
			                  </div>
		                 </s:if>
		                 <s:elseif test="%{key=='reason'}">
			                <div id="notComplDiv" style="display: none;">
			                	  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                               <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		 <s:select 
				                                      id="%{key}"
				                                      name="%{key}"
				                                      list="reasonMap"
				                                      headerKey="-1"
				                                      headerValue="Select %{value}" 
				                                      cssClass="textField"
				                                      >
	                                            </s:select>                
			                                </div>
			                      </div>
			                </div>
		                 </s:elseif>
	                 </s:iterator>
	                 
	                   <s:iterator value="textBox" status="counter" begin="3" >
		                  <s:if test="%{mandatory}">
		                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		                  </s:if>
		                  <s:if test="#counter.count%2 !=0">
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		<s:textfield name="%{key}" id="%{key}1"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
			                                </div>
			                  </div>
		                  </s:if>
		                  <s:else>
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
			                         			<s:textfield name="%{key}" id="%{key}1"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
			                  				</div>
			                  </div>
		                 </s:else>
	                 </s:iterator>
	                 <div class="clear"></div>
	             <div id="expenseDiv" style="display: block;">
	             	   <s:iterator value="dropDown" status="counter" begin="2" >
		                 <s:if  test="%{key=='expenses'}">
			                  <div class="newColumn" >
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                               <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		 <s:select 
				                                      id="%{key}"
				                                      name="%{key}"
				                                      list="expenseMap"
				                                      headerKey="-1"
				                                      headerValue="Select %{value}" 
				                                      cssClass="textField"
				                                      >
	                                            </s:select>                
			                                </div>
			                  </div>
			                  
		                 </s:if>
	                 </s:iterator>
	                   <s:iterator value="textBox" status="counter" begin="2" end="2">
		                  <s:if test="%{mandatory}">
		                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		                  </s:if>
			                  <div class="newColumn">
			                    <s:if test="#counter.count%2 !=0">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
			                                </div>
			                       </s:if>
				                  <s:else>
					                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
					                                <div class="rightColumn1">
					                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
					                         			<s:textfield name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
					                  				</div>
				                 </s:else>           
			                  </div>
	                 </s:iterator>
	                  <div class="clear"></div>
	                  <s:iterator value="file" status="counter">
		                  <s:if test="%{mandatory}">
		                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		                  </s:if>
		                  <s:if test="#counter.count%2 !=0">
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		<s:file name="%{key}" id="%{key}"   cssClass="textField" placeholder="Enter Data"/>                
			                                </div>
			                  </div>
		                  </s:if>
		                  <s:else>
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
			                         			<s:file name="%{key}" id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
			                  				</div>
			                  </div>
		                 </s:else>
	                 </s:iterator>
	                    <sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 1021px;margin-top: -37px;">+</sj:a>
	                     <s:iterator begin="100" end="105" var="deptIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#deptIndx}"/>" style="display: none">
                             <s:iterator value="dropDown" status="counter" begin="2" >
				                 <s:if  test="%{key=='expenses'}">
					                  <div class="newColumn" >
					                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
					                               <div class="rightColumn1">
					                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                             		 <s:select 
						                                      id="%{key}%{#deptIndx}"
						                                      name="%{key}"
						                                      list="expenseMap"
						                                      headerKey="-1"
						                                      headerValue="Select %{value}" 
						                                      cssClass="textField"
						                                      >
			                                            </s:select>                
					                                </div>
					                  </div>
				                 </s:if>
			                 </s:iterator>
                             <s:iterator value="textBox" status="counter" begin="2" end="2">
                            <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		<s:textfield name="%{key}" id="%{key}%{#deptIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>                
			                                </div>
                            </div>
                            </s:iterator>
                             <div class="clear"></div>
                             <s:iterator value="file" status="counter">
		                  <s:if test="%{mandatory}">
		                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		                  </s:if>
		                  <s:if test="#counter.count%2 !=0">
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                             		<s:file name="%{key}" id="%{key}%{#deptIndx}"   cssClass="textField" placeholder="Enter Data"/>                
			                                </div>
			                     <div style="margin-top: -37px;margin-left: 438px;">
			                            <s:if test="#deptIndx==105">
			                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
			                            </s:if>
			                               <s:else>
			                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
			                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
			                               </s:else>
                               </div>
			                  </div>
		                  </s:if>
		                  <s:else>
			                  <div class="newColumn">
			                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
			                                <div class="rightColumn1">
			                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
			                         			<s:file name="%{key}" id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
			                  				</div>
			                  </div>
		                 </s:else>
	                 </s:iterator>
                        </div>
                    </s:iterator>
	             </div>
                  <div class="clear"></div>
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="complTarget" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#FFFFFF'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
             </s:form>     
             <sj:dialog
          id="abcd"
          showEffect="slide" 
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="1200"
          position="center"
          height="450"
          draggable="true"
    	  resizable="true"
    	  onCloseTopics="backCalender"
          >
        <center>  <div id="activityy"></div> </center>
</sj:dialog>     
</div>
</div>
</body>
</html>

