<%@ taglib prefix="s" uri="/struts-tags" %>
<s:select 
                        id="deptname"
                        listKey="%{key}"
                        listValue="%{value}"
                        list="deptList"
                        headerKey="-1"
                        headerValue="--Select Spare Name--" 
                        >
</s:select>	
