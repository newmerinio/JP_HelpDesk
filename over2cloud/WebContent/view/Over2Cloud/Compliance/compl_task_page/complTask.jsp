<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut();
	 cancelButton();
	 resetTaskName('complTask');}, 5000);
  });
 function cancelButton(){
	    $('#completionResult').dialog('close');
	    viewTaskNameMaster();
	   }
 function viewTaskNameMaster()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action?modifyFlag=1&deleteFlag=0&status=Active",
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
 function addInKR()
 {
 	
 	$('#openKRDialog').dialog('open');
 	$('#openKRDialog').dialog({height: '450',width:'1250',position:'center'});
 	$("#krview").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	    type : "post",
 	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=Other",
 	    success : function(data) 
 	    {
 			$("#"+"krview").html(data);
 	    },
 	    error: function() 
 	    {
             alert("error");
         }
 	 });
 }
 function fromKRAdd()
 {
	 $("#krBlock").show();
 }
 function resetTaskName(formId)
 {
 	$('#'+formId).trigger("reset");
 }
 function getKRName(subGroupId)
 {
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/getKRNameAction.action?subGroupId="+subGroupId,
		    success : function(data) 
		    {
				$("#krNameDiv").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
 }

 function hideBudgetDiv()
 {
	 if($('#accountingNo').is(':checked'))
	 {
		 $("#budgetDiv").hide();
		 $("#budgetary").val('');
		 
	 }
	 else if($('#accountingYes').is(':checked'))
	 {
		 $("#budgetDiv").show();
	 }
	 
 }

 
</SCRIPT>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#krname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
</script>
</head>
<body>
<sj:dialog
          id="openKRDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Upload"
          modal="true"
          width="1200"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="krview"></div>
</sj:dialog>
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
          cssStyle="overflow:hidden;"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <center>
	 	  <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	      </center>
          <sj:div id="complTarget" cssStyle="width:90%"  effect="fold"> </sj:div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Task Name</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div>
<div class="border">

  <s:form id="complTask" name="complTask" action="complTaskAction" namespace="/view/Over2Cloud/Compliance/compl_task_page" theme="simple"  method="post"enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
    
    <s:if test="%{allDeptName.size()==0}">
	   	  		<div class="newColumn">
			  			 <div class="leftColumn">Contact Sub Type:</div>
	            		 <div class="rightColumn">
				              <span class="needed"></span>
				              		<s:textfield id="deptName"  maxlength="50" value="%{userDeptName}" cssClass="textField" disabled="true" placeholder="Enter Data" cssStyle="width: 92%"/>
				              		<s:hidden id="deptId" name="departName" maxlength="50" value="%{userDeptId}" cssClass="textField" placeholder="Enter Data"/>
				         </div>
			     </div>
	   	  </s:if>
	   	  <s:else>
	   	  <span id="complSpan" class="pIds" style="display: none; ">deptIdDD#Contact Sub Type#D#,</span>
	   	  		<div class="newColumn">
			  			 <div class="leftColumn">Contact Sub Type:</div>
	            		 <div class="rightColumn">
				              <span class="needed"></span>
				              <s:select 
				                   cssClass="select"
								   cssStyle="width:95%"
				                   id="deptIdDD"
				                   name="sub_type_id" 
				                   list="allDeptName" 
				                   headerKey="-1"
				                   headerValue="Select Contact Sub Type"
				                   onchange="commonSelectAjaxCall2('otm_task_type','id','task_type','sub_type_id',this.value,'status','Active','task_type','ASC','task_type','Select Task Type'); commonSelectAjaxCall('kr_group_data','id','group_name','sub_type_id',this.value,'','','group_name','ASC','kr_group','Select KR Group');"
                    		>
        					</s:select>
				          </div>
			             </div>
	   	  </s:else>
    
          <s:iterator value="taskTypeList" status="status">
          <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          </s:if>
          <s:if test="#status.odd">
           	    <div class="newColumn">
  	            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                <div class="rightColumn">
                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                <s:select  
                             id		    ="%{field_value}"
                             name		="task_type"
                             list		="taskTypeDropDown"
                             headerKey	="-1"
                             headerValue="Select Task Type" 
                             cssClass="select"
							 cssStyle="width:95%"
                             >
                   </s:select>
                </div>
                </div>
          </s:if>
          </s:iterator>
	
		  <s:iterator value="complTaskTxtBox" status="status" begin="0" end="2" >
		     <s:if test="%{mandatory}">
		     	<s:if test="field_value=='task_name' || field_value=='msg' || field_value=='task_brief'">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
             </s:if>
			 <s:if test="#status.odd">
			             <div class="newColumn">
  	            		 <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                		 <div class="rightColumn">
				              <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				              		<s:textfield cssStyle="width: 92%" name="%{field_value}" maxlength="%{field_length}" id="%{field_value}"  cssClass="textField" placeholder="Enter Data"/>
				         </div>
			             </div>
			 </s:if>
			 <s:else>
			           <div class="newColumn">
  	            	   <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
                	   <div class="rightColumn">
				            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				            	<s:textfield cssStyle="width: 92%" name="%{field_value}" id="%{field_value}"  maxlength="%{field_length}"  cssClass="textField" placeholder="Enter Data"/>
				       </div>
			           </div>
			 </s:else>
		  </s:iterator>
		     <sj:a cssStyle="margin-left: 83px;margin-top: 9px;" button="true" href="#" onclick="fromKRAdd();">From KR</sj:a>
		    <sj:a cssStyle="margin-left: 0px;margin-top: 9px;" button="true" href="#" onclick="addInKR();">To KR</sj:a>
		    
		  <div id="krBlock" style="display: none;">
		    <div class="clear"></div>
		    <div class="newColumn">
          	<div class="leftColumn">KR Group:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<s:select  
                        id		    ="kr_group"
                        list		="krGroupList"
                        headerKey	="-1"
                        headerValue="Select KR Group" 
                        cssClass="select"
		 				cssStyle="width:95%"
		 				onchange="commonSelectAjaxCall('kr_sub_group_data','id','sub_group_name','group_name',this.value,'','','sub_group_name','ASC','kr_subgroup','Select Sub-Group Name');"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">Sub-Group Name:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<s:select  
                        id		    ="kr_subgroup"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select Sub-Group Name" 
                        cssClass="select"
		 				cssStyle="width:95%"
		 				onchange="getKRName(this.value);"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">KR Name:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<div id="krNameDiv">
           	<s:select  
                        id		    ="krname"
                        name		="krname"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select KR Name" 
                        cssClass="textField"
		 				cssStyle="width:5%"
		 				multiple="true"
                        >
            </s:select>
            </div>
           	</div>
          	</div>
		  
		  </div>
		  
		<s:iterator value="complTaskTxtBox" status="status" begin="3" end="4">
		 <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		<s:if test="#status.odd">
			<div class="newColumn">
            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
            <div class="rightColumn">
             	<s:radio list="accountingMap" name="%{field_value}" id="%{field_value}" value="%{'No'}" onclick="hideBudgetDiv();" />
            </div>
            </div>
        </s:if>
        <s:else>
        	<div id="budgetDiv" style="display: none">
            <div class="newColumn">
            <div class="leftColumn"><s:property value="%{field_name}"/>:</div>
            <div class="rightColumn">
	            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	            <s:textfield cssStyle="width: 92%" name="%{field_value}" id="%{field_value}"   maxlength="%{field_length}"  cssClass="textField" placeholder="Enter Data"/>
	        </div>
            </div>
            </div>
		</s:else>
		</s:iterator>  
		
		
		<s:iterator value="complTaskTxtBox" status="status" begin="5" end="5" >
			    <div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          		<s:textfield cssStyle="width: 92%" name="%{field_value}"  id="%{field_value}" maxlength="%{field_length}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
          		<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -14px;margin-top: 47px;">+</sj:a></div>
          		</div>
         	    </div>
		  </s:iterator>
		  
		  <s:iterator begin="100" end="108" var="compTipIndx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#compTipIndx}"/>" style="display: none">
	      		<s:iterator value="complTaskTxtBox" status="status" begin="5" end="5" >
	      		<div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          			<sj:textfield name="%{field_value}" id="%{field_value}" maxlength="%{field_length}"  placeholder="Enter Data" cssClass="textField" />
          			<div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%">	
					<s:if test="#compTipIndx==108">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#compTipIndx+1}')" button="true" cssStyle="margin-left: -12px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')"  button="true">-</sj:a></div>
	       			</s:else>
       			</div>
          		</div>
         	    </div>
          		
	      		</s:iterator>
	      </div>
	      </s:iterator>
		
		  
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
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
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"1000"
                 		onCompleteTopics=	"makeEffect"
                        onBeforeTopics	=	"validateTaskType"
     		  	  />
     		  	 <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('complTask');resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewTaskNameMaster();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 
		 </center>
		 <br>
		 <br>
   </s:form>
</div>
</div>
</body>
</html>