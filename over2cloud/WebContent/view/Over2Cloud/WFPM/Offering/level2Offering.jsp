<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="menubox">
<div class="newColumn">
<div class="leftColumn1"><s:property value="%{offeringLevel3Name}"/>:</div>
<div class="rightColumn1">
<span class="needed"></span>
<s:select 
           id="offeringnameID"
           name="offeringname" 
           list="offeringLevel2"
           headerKey="-1"
           headerValue="--Select--"  
           cssClass="textField"
           >
</s:select>
</div>
</div>
</div>