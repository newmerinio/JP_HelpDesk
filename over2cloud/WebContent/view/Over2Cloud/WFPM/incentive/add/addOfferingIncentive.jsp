<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
.leftColumn{
text-align: right;
padding: 6px;
line-height: 32px;
float: left;
width: 36%;
}
.rightColumn{
padding: 6px;
line-height: 9px;
float: left;
text-align: left;
width: 46%;
}
</style>
<html>
<head>
<script src="<s:url value="js/WFPM/incentive/incentive.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
</head>

<body>
<div class="list-icon">
<div class="head">Allot Incentive</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div><div class="head">Offering</div>
</div>
<div class="container_block">
<div style="float: left; padding: 20px 1%; width: 98%;">
<div class="form_inner" id="form_reg" style="margin-top: 10px;">
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/WFPM/incentive" action="addOfferingIncentive" theme="simple" method="post" 
	enctype="multipart/form-data">
	<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float: left; margin-left: 7px"></div>
	</div>
	<br/>
			
		<div class="menubox">		
		<div class="newColumn">
		<div class="leftColumn1">Allot Incentive For:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:select
						id="incofferingId" 
						name="incoffering" 
						list="incOfferingMap" 
						headerKey="-1"
						headerValue="Select Offering" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="fillKpi2222(this.value)"
                        >
				</s:select>
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn1">Applicable For:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:select
						id="empId" 
						name="empId" 
						list="empMap" 
						headerKey="-1"
						headerValue="Select Employee" 
						cssClass="select"
                        cssStyle="width:82%"
                        onchange="fillKpi2222(this.value)"
                        >
				</s:select>
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn1">Incentive In:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:radio list="#{'0':'Value','1':'Percent (%)'}" id="incentive_in" name="incentive_in" theme="simple"></s:radio>  
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn1">Applicable From:</div>
		<div class="rightColumn1"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		  <sj:datepicker name="applicableFrom" id="applicableFrom" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  
		    changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="mm-yy" cssClass="textField" 
	     	placeholder="Select Date"/>
		</div>
		</div>
		
		<div style="float: left; width: 33%">
		<div class="leftColumn">Slab From:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="slabFrom" id="slabFrom"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Date"/>
		</div>
		</div>
		
		<div style="float: left; width: 33%">
		<div class="leftColumn">Slab To:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="slabTo" id="slabTo"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Date"/>  
		</div>
		</div>
		
		<div style="float: left; width: 33%">
		<div class="leftColumn">Incentive:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="incentive" id="incentive"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Incentive"/>  
		</div>
		</div>
		<div class="clear"></div>
		
		
	<div id="newDiv" style="float: right;margin-top: -45px; margin-right: 50px;"><sj:a value="+" onclick="adddiv('110')" button="true"> + </sj:a></div>
	<div class="clear"></div>
	<s:iterator begin="110" end="113" var="deptIndx">
	<div id="<s:property value="%{#deptIndx}"/>" style="display: none;">

	<div style="float: left; width: 33%">
		<div class="leftColumn">Slab From:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="slabFrom" id="slabFrom"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Date"/>
		</div>
		</div>
		
		<div style="float: left; width: 33%">
		<div class="leftColumn">Slab To:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="slabTo" id="slabTo"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Date"/>  
		</div>
		</div>
		
		<div style="float: left; width: 33%">
		<div class="leftColumn">Incentive:</div>
		<div class="rightColumn"><s:if test="%{'true'}"><span class="needed"></span></s:if>
		     <s:textfield name="incentive" id="incentive"   cssStyle="margin:0px 0px 10px 0px" showOn="focus"  cssClass="textField" placeholder="Select Incentive"/>  
		</div>
		</div>
		<div class="clear"></div>
	
	<div style="float: right; margin-top: -45px;">	
		<s:if test="#deptIndx == 113">
	    	<div style="float: right; margin-right: 50px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
        </s:if>
       	<s:else>
     	  	<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
           	<div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
         </s:else>
	</div>
	</div>
	</s:iterator>
		
		
		<div class="clear"></div>
		 
		<!-- Automatically KPIs mapped with employee will come to this div on selection of employee dropdown -->
		<div id="kpiincentiveListDiv"></div>
		
	</div>	

	<br>		
	<div class="clear"></div>
	<div class="fields">
	<div style="width: 100%; text-align: center; padding-bottom: 10px;">
		<sj:submit 
		     targets="KpiIncentiveResult" 
		     clearForm="true"
		     value="Save" 
		     effect="highlight"
		     effectOptions="{ color : '#222222'}"
		     effectDuration="5000"
		     button="true"
		     onCompleteTopics="level1"
		     cssClass="submit" 
		     indicator="indicator2"
		     onBeforeTopics="validate"
	     />
	     <sj:a 
	     	name="Reset"  
			href="#" 
			cssClass="submit" 
			button="true"
			onclick="resetFormKPI('formone');"
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
			onclick="back('%{incentiveType}')" 
			style="margin-left:4px;"
		>
		  	Back
		</sj:a>
	</div>
	</div>
	<div class="clear"></div>
	<br>
	</s:form>
	<center>
		<sj:div id="KpiIncentiveResultOuter"  effect="fold">
 	       <div id="KpiIncentiveResult"></div>
        </sj:div>
   </center>
	</div>
</div>
</div>

</body>
</html>
