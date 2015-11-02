
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.sortable {
height:15px;
margin:5px;
padding:10px;
text-align: left;
}
</style>

<script type="text/javascript">
$.subscribe('complete_close',function(event,data){
	$("#downloaddailcontactdetails").dialog('close');
});
</script>
<script type="text/javascript">
function okdownload()
{
	var checkStatus=false;
	for (var i = 0; i < document.getElementById('download').elements.length; i++)
	{
		if(document.getElementById('download').elements[i].checked==true)
		{
			checkStatus=true;
		}
	} 
	var diaId=$('#dialogId').val();
	if(checkStatus==false)
	{
		selectAll();
		$('#'+diaId).dialog('close');
	}
	else
	{
		
		$('#download').submit();
		$('#'+diaId).dialog('close');
		
	}
}
</script>
<script type="text/javascript">
var a=0;
function selectAll()
{
	if(a==0){
	  	for(var i=0; i < document.forms.titles.length; i++){
			if(!document.forms.titles[i].checked)
				document.forms.titles[i].checked=true;		}
				a=1;
			}
	else{
		for(var i=0; i < document.forms.titles.length; i++){
				document.forms.titles[i].checked=false;
			}
		a=0;
		}
}
</script>
</head>
<body topmargin="0" leftmargin="0">
<s:bean name="com.Over2Cloud.ctrl.feedback.FeedbackCommonPropertyMap" var="propertyMap">
<s:param name="key">productdetails</s:param>
</s:bean>


<s:form action="%{#parameters.downloadactionurl}" id="download" theme="css_xhtml" name="forms">

    <input type="hidden" name="fromDate" id="fromDate" value="<s:property value="fromDate"/>" >
	<input type="hidden" name="toDate" id="toDate" value="<s:property value="toDate"/>" >
	<input type="hidden" name="level" id="level" value="<s:property value="Level"/>" >
	<input type="hidden" name="tableName" id="tableName" value="<s:property value="tableName"/>" >
	<input type="hidden" name="fbtype" id="fbtype" value="<s:property value="fbtype"/>" >
	<input type="hidden" name="patType" id="patType" value="<s:property value="patType"/>" >
	<input type="hidden" name="spec" id="spec" value="<s:property value="spec"/>" >
	<input type="hidden" name="docName" id="docName" value="<s:property value="docName"/>" >
	<input type="hidden" name="feedBack" id="feedBack" value="<s:property value="feedBack"/>" >
	
<s:hidden name="downloadType" value="%{#parameters.downloadType}"/>
<sj:div id="sortable" sortable="true" sortableOpacity="0.8" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" cssStyle="width: 300px;">
 <div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all" >
 <s:checkbox  labelposition="right" label="Select ALL" name="Select ALL" onclick="selectAll();" /></div>
 <s:iterator value="#propertyMap.valueMap" status="rowstatus">
  <div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all">
  <s:if test="%{mandatory}">
 <s:checkbox  labelposition="right" label="%{value}" value="true" name="titles" fieldValue="%{key}" required="true"/>
 </s:if>
 <s:else>
 <s:checkbox  labelposition="right" label="%{value}" name="titles" fieldValue="%{key}" />
 </s:else>
 </div>
 </s:iterator>
  </sj:div>
<br>
  
</s:form>
           
</body>
</html>