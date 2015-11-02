<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<style type="text/css">
#accordion{
	float:left; width:862px!important;
}
.accordion_block{
	float:left; width:100%; padding:0px 0px 1px 0px;
}
#accordion h3.ui-accordion-header{
	float:left; width:73.7%; padding:0.5em 0 0.5em 0!important; text-indent:25px; background:url(images/block_heading.png) repeat;
	border:1px solid #D6D3CF; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px; -o-border-radius:5px; color:#ffffff;
	cursor:pointer; font-weight:normal; font-size:13px;
}
.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{
	background:url(images/tabs_bg_active.png) repeat-x!important;
}
#accordion div.ui-accordion-content{
	float:left; width:71.5%; padding:0; background:url(/images/content_bg.png); padding:1%; border:1px solid #D6D3CF;
}
</style>
</head>
<div id="accordion">
<div class="accordion_block">
<h3 class="ui-accordion-header" id="<s:property value="%{level1Name}"/>" onclick="tom(b${content1}b);"><s:property value="%{level1Name}"/></h3>
<s:if test= "level1Name!=null">
<div class="ui-accordion-content" style="display:block; float:left;" id="b<s:property value="%{content1}"/>b">
 <div class="form_inner" id="form_reg">
				<div class="page_form">
			<s:form id="abc" name="formone" theme="simple"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
            
				 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration1">		
				 	<tr >
				 	<td align="center">
				 	<s:property value="%{sequence}"/>
				 	</td>
				 	<td align="center">
				 	<s:property value="%{field_name}"/>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
		                  	<s:checkbox   cssStyle="margin-left: 32%;"  name="activeType%{id}" id="activeType%{id}"  value="true"  cssClass="form_menu_inputtext sub_select" />
						  </s:if>
						  <s:else>
		                 	<s:checkbox  cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
						  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	<s:if test="%{hideShow}">
				 		No
				 	</s:if>
				 	<s:else>
				 		Yes
				 	</s:else>
				 	</td>
				 	</tr>	
				 		
				 	</s:iterator>	
				 		
				 		
				 </table>
				 	</s:form>
</div>
</div>
</div>
</s:if>
<s:if test= "level2Name!=null">
<h3 class="ui-accordion-header" id="<s:property value="%{level2Name}"/>" onclick="tom(b${content2}b);"><s:property value="%{level2Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="b<s:property value="%{content2}"/>b">
                  <div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="def" name="formtwo" theme="simple"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
    
				<s:hidden name="mapedtable" value="%{mappedtablelevel2}"/>
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration2">		
				 		<tr >
				 	<td align="center">
				 	<s:property value="%{sequence}"/>
				 	</td>
				 	<td align="center">
				 	<s:property value="%{field_name}"/>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
		                  	<s:checkbox   cssStyle="margin-left: 32%;"  name="activeType%{id}" id="activeType%{id}"  value="true"  cssClass="form_menu_inputtext sub_select" />
						  </s:if>
						  <s:else>
		                 	<s:checkbox  cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
						  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	      <s:if test="%{hideShow}">
				 		No
				 	</s:if>
				 	<s:else>
				 		Yes
				 	</s:else>
				 	</td>
				 	</tr>	
				 		
				 	</s:iterator>	
				 </table>
				 </s:form>
				 </div>
				 </div>
</div>
</s:if>
<s:if test= "level3Name!=null">
<h3 class="ui-accordion-header" id="%{level3Name}" onclick="tom(b${content3}b);"><s:property value="%{level3Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="b<s:property value="%{content3}"/>b">
           <div class="form_inner" id="form_reg">
				<div class="page_form">
				<s:form id="ghi" name="formthree" theme="simple"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
              
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration3">		
				 		<tr >
				 	<td align="center">
				 	<s:property value="%{sequence}"/>
				 	</td>
				 	<td align="center">
				 	<s:property value="%{field_name}"/>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
		                  	<s:checkbox   cssStyle="margin-left: 32%;"  name="activeType%{id}" id="activeType%{id}"  value="true"  cssClass="form_menu_inputtext sub_select" />
						  </s:if>
						  <s:else>
		                 	<s:checkbox  cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
						  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				  	<td align="center">
				 	      <s:if test="%{hideShow}">
				 		No
				 	</s:if>
				 	<s:else>
				 		Yes
				 	</s:else>
				 	</td>
				 	</tr>	
				 		
				 	</s:iterator>	
				 </table>
				 </s:form>
				 </div>
				 </div>
</div>
</s:if>

<!-- Level4 -->
<s:if test= "level4Name!=null">
<h3 class="ui-accordion-header"  id="%{level4Name}" onclick="tom(b${content4}b);"><s:property value="%{level4Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="b<s:property value="%{content4}"/>b">
        <div class="form_inner" id="form_reg">
		<div class="page_form">
		<s:form id="jkl" name="formfour" theme="simple"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
           
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="ceui-accordion-contentnter"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration4">		
				 	<tr >
				 	<td align="center">
				 	<s:property value="%{sequence}"/>
				 	</td>
				 	<td align="center">
				 	<s:property value="%{field_name}"/>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
		                  	<s:checkbox   cssStyle="margin-left: 32%;"  name="activeType%{id}" id="activeType%{id}"  value="true"  cssClass="form_menu_inputtext sub_select" />
						  </s:if>
						  <s:else>
		                 	<s:checkbox  cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
						  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	 	<td align="center">
				 	      <s:if test="%{hideShow}">
				 		No
				 	</s:if>
				 	<s:else>
				 		Yes
				 	</s:else>
				 	</td>
				 	</tr>	
				 		
				 	</s:iterator>	
				 </table>
				 </s:form>
				 </div>
				 </div>
</div>

</s:if>
<!-- Level5 -->
<s:if test= "level5Name!=null">
<h3 class="ui-accordion-header"  id="%{level5Name}" onclick="tom(b${content5}b);"><s:property value="%{level5Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="b<s:property value="%{content5}"/>b">
   	<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="mno" name="formone" theme="simple"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration5">		
				 	<tr >
				 	<td align="center">
				 	<s:property value="%{sequence}"/>
				 	</td>
				 	<td align="center">
				 	<s:property value="%{field_name}"/>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
		                  	<s:checkbox   cssStyle="margin-left: 32%;"  name="activeType%{id}" id="activeType%{id}"  value="true"  cssClass="form_menu_inputtext sub_select" />
						  </s:if>
						  <s:else>
		                 	<s:checkbox  cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
						  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	 	<td align="center">
				 	      <s:if test="%{hideShow}">
				 		No
				 	</s:if>
				 	<s:else>
				 		Yes
				 	</s:else>
				 	</td>
				 	</tr>	
				 	</s:iterator>	
				 </table>
		 </s:form>
	</div>
</div>
</div>
</s:if>
</div>
</div>

</html>