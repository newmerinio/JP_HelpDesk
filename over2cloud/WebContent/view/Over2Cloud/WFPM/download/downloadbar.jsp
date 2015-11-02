
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.sortable {
height:12px;
width:180px;
margin:3px;
padding:5px;
text-align: left;
}
</style>

<script type="text/javascript">
$.subscribe('complete_close',function(event,data){
	$("#downloaddailcontactdetails").dialog('close');
});
</script>
<script type="text/javascript">
function closedownload(){
	$("#downloaddaildetails").dialog('close');
}
function okdownload(){
     $("#download").submit();
}
</script>
<script type="text/javascript">
var a=0;
function selectAll()
{
	if(a==0){
	for(var i=0; i < document.forms.titles.length; i++){
		if(!document.forms.titles[i].checked)
			document.forms.titles[i].checked=true;}
	a=1;}
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
<s:bean name="com.Over2Cloud.ctrl.wfpm.excel.CommonPropertyMap" var="propertyMap">
<s:param name="key">customerdetails</s:param>
</s:bean>
<s:property  value="%{#parameters.downloadactionurl}"/>
<s:form action="%{#parameters.downloadactionurl}" id="download" theme="css_xhtml" name="forms">
<s:hidden name="id" value="%{#parameters.id}"/>
<sj:div id="sortable" sortable="true" sortableOpacity="0.8" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" cssStyle="width: 50px;">
 <div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all" >
 <s:checkbox  labelposition="right" label="Select ALL" name="Select ALL" onclick="selectAll();" /></div>
 <s:iterator value="#propertyMap.valueMap" status="rowstatus">
 <div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all">
  <s:if test="%{mandatory}">
 <s:checkbox  labelposition="right" label="%{value}" value="true" name="titles" fieldValue="%{key}" required="true" />
 </s:if>
 <s:else>
 <s:checkbox  labelposition="right" label="%{value}" name="titles" fieldValue="%{key}" />
 </s:else>
 </div>
 </s:iterator>
  </sj:div>

</s:form>
           
</body>
</html>