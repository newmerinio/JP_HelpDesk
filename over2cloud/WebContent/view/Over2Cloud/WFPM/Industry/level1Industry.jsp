<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="menubox">
<div class="newColumn">
	  <div class="leftColumn1"><s:property value="%{industryDDHeadingName}"/>:</div>
	  <div class="rightColumn1">
	 <span class="needed"></span>
	         <s:select 
	                     id="industry"
	                     name="industry" 
	                     list="industryLevel1"
	                     headerKey="-1"
	                     headerValue="Select Industry" 
	                     cssClass="select"
                       	 cssStyle="width:82%"
	                     >
	         </s:select>
	</div>
	</div>
</div>