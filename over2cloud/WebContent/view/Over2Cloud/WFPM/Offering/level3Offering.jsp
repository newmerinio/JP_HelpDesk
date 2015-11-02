<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="menubox">
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{offeringLevel4Name}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
         id="subofferingnameId"
         name="subofferingname" 
         list="offeringLevel3"
         headerKey="-1"
         headerValue="--Select Data--" 
         cssClass="textField"
         >
</s:select>
</div>
</div>
</div>