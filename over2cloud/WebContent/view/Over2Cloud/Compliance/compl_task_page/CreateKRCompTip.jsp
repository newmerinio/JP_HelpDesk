<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#krname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});

$("#viewtaskname").click(function() {
    $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compl_task_page/beforeViewComplTaskAction.action",
        success : function(subdeptdata) {
            $("#" + "data_part").html(subdeptdata);
        },
        error : function() {
            alert("error");
        }
    });
});
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); }, 1000);
			 $.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/ViewTaskName4AddKR.action",
				    success : function(data) 
				    {
					$("#data_part").html(data);
					},
				    error: function()
				    {
				        alert("error");
				    }
				 });
		  });

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Un-Mapped KR </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div>
<div class="border">
<s:form id="complKRComplTipTask" name="complKRComplTipTask" action="addKRComplTipAction" namespace="/view/Over2Cloud/Compliance/compl_task_page" theme="simple"  method="post"enctype="multipart/form-data" >
 		 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
		 <s:if test="%{commonMap.size()>0}">
		     <table width="70%" border="1" align="center">
		    		  <tr  bgcolor="lightgrey" style="height: 25px">
			    			<s:iterator value="commonMap" status="counter">
			    			<s:if test="%{key=='Department' || key=='Task Type' || key=='Task Name' || key=='Task Brief'}">
					    			<td align="left" width="12%"><strong><s:property value="%{key}"/>:</strong></td>
									<td align="left" width="12%"><s:property value="%{value}"/></td>
							</s:if>
							</s:iterator>
				    </tr>
		    </table>
		</s:if>         
     <div class="clear"></div>     
       <br>   
 <span id="mandatoryFields" class="pIds" style="display: none; ">kr_group#Kr Group#D#,</span>
    <span id="mandatoryFields" class="pIds" style="display: none; ">kr_subgroup#Kr Sub Group#D#,</span>
     <s:hidden name="taskId" value="%{taskId}" />
	<div class="newColumn">
          	<div class="leftColumn">KR Group:</div>
           	<div class="rightColumn">
           <span class="needed"></span>
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
           <span class="needed"></span>
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
          	
          	<s:iterator value="complTaskTxtBox" status="status">
			    <div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          		<s:textfield cssStyle="width: 92%" name="%{field_value}"  id="%{field_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
          		<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -14px;margin-top: 47px;">+</sj:a></div>
          		</div>
         	    </div>
		  </s:iterator>
		  
		  <s:iterator begin="100" end="108" var="compTipIndx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#compTipIndx}"/>" style="display: none">
	      		<s:iterator value="complTaskTxtBox" status="status">
	      		<div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          			<sj:textfield name="%{field_value}" id="%{field_value}" placeholder="Enter Data" cssClass="textField" />
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
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
     		  	    <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('complKRComplTipTask'); resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						id="viewtaskname"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		  <center><sj:div id="complTarget"  effect="fold"> </sj:div></center>
		 </center>
</s:form>
</div>
</div>
</body>
</html>