<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="menubox">
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{offeringLevel5Name}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
         id="offeringId"
         name="offeringId" 
         list="offeringLevel5"
         headerKey="-1"
         headerValue="--Select--" 
         cssClass="textField"
         >
</s:select>
</div>
</div>
</div>