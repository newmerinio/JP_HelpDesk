<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%-- <s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
 --%>
 <script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<script type="text/javascript">
/* $(document).ready(function()
		{
			$("#clientContactMOM").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
			});

			$("#employeeMOM").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
			});
		});
 */
$(document).ready(function(){
	$("#clientContactMOM").multiselect({
		                              show: ["bounce", 200],
		                              hide: ["explode", 1000]
		   	                       });
	$("#employeeMOM").multiselect({
        show: ["bounce", 200],
        hide: ["explode", 1000]
        });
	//$("#offeringname1").multiselect();
	//$("#verticalname").multiselect({
	 //   show: ["bounce", 200],
	  //  hide: ["explode", 1000]
	   // });

	  }
	);

</script>
</head>
<body>
<br>
		<div class="newColumn">
			<div class="leftColumn1">Organization:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:label name="orgNameMOM"  id="orgNameMOM" cssClass="textField" value="%{jsonObject.ADDRESS}"
			                  cssStyle="margin:0px 0px 10px 0px; background-color:#F5F3F0;"/>
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Date:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <sj:datepicker name="dateMOM" id="dateMOM" changeMonth="true" changeYear="true" placeholder="Enter Date" 
			     	yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date" />
			</div>
		</div>   
		
		<div class="clear"></div>
		<div class="newColumn">
			<div class="leftColumn1">From Time:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <sj:datepicker id="fromTimeMOM" name="fromTimeMOM" showOn="focus" cssClass="textField" label="Select a Time" placeholder="Select Time"
			     	timepicker="true" timepickerOnly="true"/>
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">To Time:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <sj:datepicker id="toTimeMOM" name="toTimeMO" showOn="focus" cssClass="textField" label="Select a Time" 
			     timepicker="true" placeholder="Select Time" timepickerOnly="true"/>
			</div>
		</div>   
		<div class="clear"></div>
		
		<div class="newColumn">
			<div class="leftColumn1">Client Contact:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:select list="jsonArray"
			     		   listKey="ID"
			     		   listValue="NAME"
			     		   id="clientContactMOM"
			     		   name="clientContactMOM"
			     		   multiple="true"	
			     		   cssClass="select"
			     		   cssStyle="width:24%"
			     		   >
			     	</s:select>
			</div>
		</div>   
		<div class="newColumn">
			<div class="leftColumn1">Employees:</div>
			<div class="rightColumn1"><span class="needed"></span>
			     <s:select list="jsonArrayOther"
			               listKey="ID"
			     		   listValue="NAME"
			     		   id="employeeMOM"
			     		   name="employeeMOM"
			     		   multiple="true"	
			     		   cssClass="select"
			     		   cssStyle="width:24%"
			     		   >
			     	</s:select>
			</div>
		</div>   
		
		
	<div style="float: left; width: 100%;">
			<div class="leftColumn2" style="margin-right: 942px;margin-left: 56px;margin-top: 14px;">Meeting Agenda:</div>
			<div class="rightColumn2"><span class="needed" style="margin-left: 151px;margin-top: -19px;"></span>
			     <s:textarea name="agendaMOM"  id="agendaMOM" cssClass="textField2" cssStyle="margin-left: -2px;margin-top:-19px;width: 340px;"/>
			</div>
		</div>   
		
		<div style="float: left; width: 100%;">
			<div class="leftColumn2" style="
    margin-left: 684px;
    margin-top: -24px;
">Discussion:</div>
			<div class="rightColumn2"><span class="needed" style="
    margin-left: 750px;
    margin-top: -20px;
"></span>
			     <s:textarea name="discussionMOM" cssClass="textField2" cssStyle="margin:0px 0px 10px 0px;margin-left: 759px;margin-top: -31px;width: 333px;"/>
			</div>
		</div> 
		
		
		<div id="newDiv" style="float: right;width: 19%;">
			<sj:a value="+" onclick="adddiv('2')" button="true" style=" margin-left: 50px;">+</sj:a></div>
	 	<s:iterator begin="2" end="10" var="deptIndx">
	 		<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
<div style="float: left; width: 100%;">
			<div class="leftColumn2" style="
    margin-right: 937px;
    margin-left: 83px;
    margin-top: -18px;
">Discussion:</div>
			<div class="rightColumn2"><span class="needed" style="
    margin-top: -20px;
    margin-left: 146px;
"></span>
			     <s:textarea name="discussionMOM" cssClass="textField2" cssStyle="margin:0px 0px 10px 0px;margin-right: 670px;margin-top: -19px;width: 340px;"/>
			</div>
		</div>  
 					<div class="clear"></div>
	 			
				<s:if test="#deptIndx==10">
	 			<div style="float: right;width: 12%;">
						<div class="user_form_button2" style="margin-left: 10px;">
						<sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
				</div>
				</s:if>
				<s:else>
				
				<div style="float: right;width: 15.5%;">
          	 				<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
          					<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" 
          						 onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
				</div>
				</s:else>
				
	 		</div>
	 	</s:iterator>
		
		<div class="clear"></div>
		
		<div style="float: left; width: 100%;" id="futureActionId1">
			<div class="leftColumn2" style="
    margin-right: 951px;
    margin-left: 68px;
    margin-top: -19px;
">Future Action:</div>
			<div class="rightColumn2"><span class="needed" style="
    margin-left: 150px;
    margin-top: -19px;
"></span>
			     <s:textarea name="futureActionMOM"  id="futureActionMOM1" cssClass="textField2" cssStyle="margin: 0px 0px 10px; width: 342px; height: 31px;margin-right: 656px;margin-left: -1px;margin-top: -19px;"/>
			</div>
		</div> 
		
		
		
		<!--<input type="button" class="button" value="+">  
		-->
		
		
		<br>
		
		<!--<div class="buttonStyle">
	          <sj:a 
	          
							     	name="Reset"  
									href="#" 
									cssClass="button" 
									button="true" 
									onclick="resetForm('actionForm');"
								>
								  	Save
								</sj:a>
			<sj:a 
	          
				     	name="Reset"  
						href="#" 
						cssClass="button" 
						button="true" 
					>
					  	Reset
					</sj:a>
	          <sj:a
	     	    name="Cancel"  
				href="#" 
				cssClass="button" 
				indicator="indicator" 
				button="true" 
				onclick="cancel()"
	
	>
	  	Back
	</sj:a>
	    </div>
		
--></body>
<SCRIPT type="text/javascript">
fillMomFields();
</SCRIPT>
</html>