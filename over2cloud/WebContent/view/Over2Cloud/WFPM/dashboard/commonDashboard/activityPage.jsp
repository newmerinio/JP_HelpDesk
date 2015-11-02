<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/struts-jquery-tags" prefix="sj"%>

<div id="activityDiv${count}" class="justForRemovingIt">
<div class="clear"></div> 
<div class="newColumn">
	<div class="leftColumn1">Next Activity:</div>
	<div class="rightColumn1"><span class="needed"></span>
	     <s:select 
	     	id="statusIdOther%{count}"
	     	name="statusIdOther"
	     	list="statusMap"
	     	headerKey="-1"
	     	headerValue=" Select Activity "
	     	cssClass="textField"
	     	></s:select>
	</div>
</div>  
<div class="newColumn">
	<div class="leftColumn1">Offering:</div>
	<div class="rightColumn1"><span class="needed"></span>
	     <s:select 
	     	id="offeringIdOther%{count}"
	     	name="offeringIdOther"
	     	list="OfferingMap"
	     	headerKey="-1"
	     	headerValue=" Select Activity "
	     	cssClass="textField"
	     	></s:select>
	</div>
</div>  

<div class="clear"></div> 
<div class="newColumn">
	<div class="leftColumn1">Due Date:</div>
	<div class="rightColumn1"><span class="needed"></span>
	     <sj:datepicker name="maxDateTimeOther" id="maxDateTimeOther%{count}" minDate="0" changeMonth="true" changeYear="true" 
	    	 yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date" 
	    	 timepicker="true" />
	</div>
</div>   
<div class="newColumn">
	<div class="leftColumn1">Comment:</div>
	<div class="rightColumn1"><span class="needed"></span>
	     <s:textfield name="commentOther"  id="commentOther%{count}" cssClass="textField" placeholder="Enter Comment" 
		     cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
</div>  
<div class="clear"></div> 
</div> 

<div class="newColumnChk" id="chkContainer${count+1}">
	<div class="leftColumn1">Other Offering Activity ${count+1}:</div>
	<div class="rightColumn1"><span class="needed"></span>
	     <s:checkbox name="chkOther" fieldValue="%{count+1}" onclick="createOtherOfferingActivity(%{count+1},this.checked)"></s:checkbox>
	</div>
</div>   

