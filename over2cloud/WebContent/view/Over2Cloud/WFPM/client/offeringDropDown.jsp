<%@ taglib prefix="s" uri="/struts-tags" %>

	<s:select 
	            id="verticalname"
	            name="verticalname" 
	            list="verticalMap"
	            headerKey="-1"
	            headerValue="Select Name" 
	            cssClass="form_menu_inputselect"
	            onchange="fetchLevel2(this,'div2','1')"
	            >
	 
	</s:select>
