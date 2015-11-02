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
iframe {
   width: 0px;
   height: 0px;
   border: none;
}
</style>
</head>

<div class="list-icon">
	 <div class="head">Configure <s:property value="%{filed_name}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Editor</div> 
</div>	
<s:hidden id="idVal" value="%{id}"/>
<s:hidden id="filed_name" value="%{filed_name}"/>
<div class="border" style="overflow: scroll;height: 500px;width: 89%; margin-left: 1%;">
<div style=" float:left; padding:-20px 5%; width:50%;">

<div id="accordion">
<div class="accordion_block">
<h3 class="ui-accordion-header" id="<s:property value="%{level1Name}"/>" onclick="tom(${content1});"><s:property value="%{level1Name}"/></h3>
<div class="ui-accordion-content" style="display:block; float:left;" id="<s:property value="%{content1}"/>">
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone%{id}" name="formone" theme="simple" target="iframename"  windowState="data_result"  namespace="/view/CloudApps"  action="changeConfSetting" method="post"enctype="multipart/form-data" >
				<s:hidden id="mappedTableConf" name="mappedTableConf" value="%{commonMappedtablele}"/> 
				<s:hidden name="mapedtable" value="%{mappedtablelevel1}"/>
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration">		
				 	<tr >
				 	<td align="center">
				 	   <s:textfield id="sequence%{id}" name="sequence%{id}" value="%{sequence}" cssClass="textField" cssStyle="width:66px;    text-align: -webkit-center;"></s:textfield>
				 	</td>
				 	<td align="center">
				 		<s:if test="%{add_via=='Manual'}">
				 	    	<sj:div href="#" onclick="OnlickEdite('%{id}','%{mandatory}','%{field_name}','%{mappedtablelevel1}','update','%{commonMappedtablele}','%{colType}','%{field_length}');"  id="%{id}"><font color="blue" ><s:property value="%{field_name}"/></font></sj:div>
				 	     </s:if>
				 	     <s:else>
				 	     	 <s:textfield id="field_name%{id}" name="field_name%{id}" value="%{field_name}" cssClass="textField" cssStyle="width:105px;    text-align: -webkit-center;"></s:textfield>
				 	     </s:else>
				 	</td>
				 	<td align="center">
				 	      <s:if test="%{add_via=='Manual'}">
						 	       		<s:hidden name="activeType%{id}" id="activeType%{id}" value="%{actives}"/>
				                  		<s:checkbox   cssStyle="margin-left: 32%;" name="activeTypeCheck%{id}" id="activeTypeCheck%{id}" onchange="changeValue('activeType%{id}');" value="%{actives}"  cssClass="form_menu_inputtext sub_select" />
								  </s:if>
								  <s:else>
				                 	<s:checkbox onclick="OnlickEditeee(this,'%{mappedtablelevel1}','%{mandatory}');" cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
								  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	        <s:select list="#{'false':'Yes','true':'No'}" name="hideOrShow%{id}" id="hideOrShow%{id}" cssStyle="margin-left: 32%;" cssClass="form_menu_inputtext sub_select" ></s:select>
				 	</td>
				 	</tr>	
				 	</s:iterator>	
				 </table>
				   	<div class="fields" style="width:100%; padding:0;">
							<ul>
								<li class="submit">
									<input type="button" value="New Field" id="" name="" class="submit" onclick="OnlickEdite('','%{mandatory}','','<s:property value='%{mappedtablelevel1}'/>','insert','<s:property value='%{commonMappedtablele}'/>','','');">
								</li>
								<li class="submit">
								 <input type="submit" value="Preview" class="submit" onclick="submitFn('formone<s:property value='%{id}'/>','<s:property value='%{id}'/>','<s:property value='%{filed_name}'/>')"/>
							   </li>									
							</ul>
			  	  </div>
		</s:form>
		<iframe id="iframeid" name="iframename" style="" onload="onLoadFn()"></iframe>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</div>
<s:if test="globalLevel>1">
<h3 class="ui-accordion-header" id="<s:property value="%{level2Name}"/>" onclick="tom(${content2});"><s:property value="%{level2Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content2}"/>">
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo%{id}" name="formTwo"  target="iframename"  windowState="data_result" namespace="/view/CloudApps" action="changeConfSetting" theme="simple"  method="post"enctype="multipart/form-data" >
		 		<s:hidden id="mappedTableConf" name="mappedTableConf" value="%{commonMappedtablele1}"/> 
				<s:hidden name="mapedtable" value="%{mappedtablelevel2}"/>
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
					 		  <td align="center"><b>Sequence</b></td>
					 		  <td align="center"><b>Field</b></td>
					 		  <td align="center"><b>Select</b></td>
					 		  <td align="center"><b>Add</b></td>
					 		  <td align="center"><b>View</b></td>
				 		 <tr>
						 <s:iterator value="listconfiguration2">		
							 	<tr>
								 	<td align="center">
								 	   <s:textfield id="sequence%{id}" name="sequence%{id}" value="%{sequence}" cssClass="textField" cssStyle="width:66px;    text-align: -webkit-center;"></s:textfield>
								 	</td>
								 	<td align="center">
								 		<s:if test="%{add_via=='Manual'}">
								 	    	<sj:div href="#" onclick="OnlickEdite('%{id}','%{mandatory}','%{field_name}','%{mappedtablelevel2}','update','%{commonMappedtablele1}','%{colType}','%{field_length}');"  id="%{id}"><font color="blue" ><s:property value="%{field_name}"/></font></sj:div>
								 	     </s:if>
								 	     <s:else>
								 	     	 <s:textfield id="field_name%{id}" name="field_name%{id}" value="%{field_name}" cssClass="textField" cssStyle="width:105px;    text-align: -webkit-center;"></s:textfield>
								 	     </s:else>
								 	</td>
								 	<td align="center">
								 	       <s:if test="%{add_via=='Manual'}">
						 	       		<s:hidden name="activeType%{id}" id="activeType%{id}" value="%{actives}"/>
				                  		<s:checkbox   cssStyle="margin-left: 32%;" name="activeTypeCheck%{id}" id="activeTypeCheck%{id}" onchange="changeValue('activeType%{id}');" value="%{actives}"  cssClass="form_menu_inputtext sub_select" />
								  </s:if>
								  <s:else>
				                 	<s:checkbox onclick="OnlickEditeee(this,'%{mappedtablelevel1}','%{mandatory}');" cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
								  </s:else>
								 	</td>
								 	<td align="center" >
								 		  <s:property value="%{add_via}"/>
								 	</td>
								 	<td align="center">
								 	      <s:select list="#{'false':'Yes','true':'No'}" name="hideOrShow%{id}" id="hideOrShow%{id}" cssStyle="margin-left: 32%;" cssClass="form_menu_inputtext sub_select" ></s:select>
								 	</td>
							 	</tr>	
						 </s:iterator>	
				 </table>
				 <div class="fields" style="width:100%; padding:0;">
							<ul>
								<li class="submit">
									<input type="button" value="New Field" id="" name="" class="submit" onclick="OnlickEdite('','%{mandatory}','','<s:property value='%{mappedtablelevel2}'/>','insert','<s:property value='%{commonMappedtablele1}'/>','','');">
								</li>
								<li class="submit">
								 <input type="submit" value="Preview" class="submit" onclick="submitFn('formTwo<s:property value='%{id}'/>','<s:property value='%{id}'/>','<s:property value='%{filed_name}'/>')"/>
								</li>											
							</ul>
			  			 </div>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</div>
</s:if>
<s:if test="globalLevel>2">
<!-- Level3 -->
<h3 class="ui-accordion-header" id="%{level3Name}" onclick="tom(${content3});"><s:property value="%{level3Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content3}"/>">
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree%{id}" name="formThree" target="iframename"  windowState="data_result" namespace="/view/CloudApps" action="changeConfSetting"  theme="simple"  method="post"enctype="multipart/form-data" >
                  
                <s:hidden id="mappedTableConf" name="mappedTableConf" value="%{commonMappedtablele2}"/> 
				<s:hidden name="mapedtable" value="%{mappedtablelevel3}"/>
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
				 	   <s:textfield id="sequence%{id}" name="sequence%{id}" value="%{sequence}" cssClass="textField" cssStyle="width:66px;    text-align: -webkit-center;"></s:textfield>
				 	</td>
				 	<td align="center">
				 		<s:if test="%{add_via=='Manual'}">
				 	    	<sj:div href="#" onclick="OnlickEdite('%{id}','%{mandatory}','%{field_name}','%{mappedtablelevel3}','update','%{commonMappedtablele2}','%{colType}','%{field_length}');"  id="%{id}"><font color="blue" ><s:property value="%{field_name}"/></font></sj:div>
				 	     </s:if>
				 	     <s:else>
				 	     	 <s:textfield id="field_name%{id}" name="field_name%{id}" value="%{field_name}" cssClass="textField" cssStyle="width:105px;    text-align: -webkit-center;"></s:textfield>
				 	     </s:else>
				 	</td>
				 	<td align="center">
				 	      <s:if test="%{add_via=='Manual'}">
						 	       		<s:hidden name="activeType%{id}" id="activeType%{id}" value="%{actives}"/>
				                  		<s:checkbox   cssStyle="margin-left: 32%;" name="activeTypeCheck%{id}" id="activeTypeCheck%{id}" onchange="changeValue('activeType%{id}');" value="%{actives}"  cssClass="form_menu_inputtext sub_select" />
								  </s:if>
								  <s:else>
				                 	<s:checkbox onclick="OnlickEditeee(this,'%{mappedtablelevel1}','%{mandatory}');" cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
								  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	        <s:select list="#{'false':'Yes','true':'No'}" name="hideOrShow%{id}" id="hideOrShow%{id}" cssStyle="margin-left: 32%;" cssClass="form_menu_inputtext sub_select" ></s:select>
				 	</td>
				 	</tr>	
				 	</s:iterator>	
				 </table>
				 	<div class="fields" style="width:100%; padding:0;">
							<ul>
								<li class="submit">
									<input type="button" value="New Field" id="" name="" class="submit" onclick="OnlickEdite('','%{mandatory}','','<s:property value='%{mappedtablelevel3}'/>','insert','<s:property value='%{commonMappedtablele2}'/>','','');">
								</li>	
									<li class="submit">
									 <input type="submit" value="Preview" class="submit" onclick="submitFn('formThree<s:property value='%{id}'/>','<s:property value='%{id}'/>','<s:property value='%{filed_name}'/>')"/>
									</li>										
							</ul>
			  			 </div>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</div>
</s:if>
<s:if test="globalLevel>3">
<!-- Level4 -->
<h3 class="ui-accordion-header"  id="%{level4Name}" onclick="tom(${content4});"><s:property value="%{level4Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content4}"/>">
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFour%{id}" name="formFour" target="iframename"  windowState="data_result"  namespace="/view/CloudApps" action="changeConfSetting"  theme="simple"  method="post"enctype="multipart/form-data" >
                  	  	 <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel4}"/>	</span>
					</div>
           
  <s:hidden id="mappedTableConf" name="mappedTableConf" value="%{commonMappedtablele3}"/> 
				<s:hidden name="mapedtable" value="%{mappedtablelevel4}"/>
                 <table  width="100%" align="center" border="0" >
				 		<tr bordercolor="black" align="center" valign="top" bgcolor="lightgreen">
				 		  <td align="center"><b>Sequence</b></td>
				 		  <td align="center"><b>Field</b></td>
				 		  <td align="center"><b>Select</b></td>
				 		  <td align="center"><b>Add</b></td>
				 		  <td align="center"><b>View</b></td>
				 		</tr>
				  <s:iterator value="listconfiguration4">		
				 	<tr >
				 	<td align="center">
				 	   <s:textfield id="sequence%{id}" name="sequence%{id}" value="%{sequence}" cssClass="textField" cssStyle="width:66px;    text-align: -webkit-center;"></s:textfield>
				 	</td>
				 	<td align="center">
				 		<s:if test="%{add_via=='Manual'}">
				 	    	<sj:div href="#" onclick="OnlickEdite('%{id}','%{mandatory}','%{field_name}','%{mappedtablelevel4}','update','%{commonMappedtablele3}','%{colType}','%{field_length}');"  id="%{id}"><font color="blue" ><s:property value="%{field_name}"/></font></sj:div>
				 	     </s:if>
				 	     <s:else>
				 	     	 <s:textfield id="field_name%{id}" name="field_name%{id}" value="%{field_name}" cssClass="textField" cssStyle="width:105px;    text-align: -webkit-center;"></s:textfield>
				 	     </s:else>
				 	</td>
				 	<td align="center">
				 	     <s:if test="%{add_via=='Manual'}">
						 	       		<s:hidden name="activeType%{id}" id="activeType%{id}" value="%{actives}"/>
				                  		<s:checkbox   cssStyle="margin-left: 32%;" name="activeTypeCheck%{id}" id="activeTypeCheck%{id}" onchange="changeValue('activeType%{id}');" value="%{actives}"  cssClass="form_menu_inputtext sub_select" />
								  </s:if>
								  <s:else>
				                 	<s:checkbox onclick="OnlickEditeee(this,'%{mappedtablelevel1}','%{mandatory}');" cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
								  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	        <s:select list="#{'false':'Yes','true':'No'}" name="hideOrShow%{id}" id="hideOrShow%{id}" cssStyle="margin-left: 32%;" cssClass="form_menu_inputtext sub_select" ></s:select>
				 	</td>
				 	</tr>	
				 	</s:iterator>	
				 </table>
				    	<div class="fields" style="width:100%; padding:0;">
							<ul>
								<li class="submit">
									<input type="button" value="New Field" id="" name="" class="submit" onclick="OnlickEdite('','%{mandatory}','','<s:property value='%{mappedtablelevel4}'/>','insert','<s:property value='%{commonMappedtablele3}'/>','','');">
								</li>
									<li class="submit">
									 <input type="submit" value="Preview" class="submit" onclick="submitFn('formFour<s:property value='%{id}'/>','<s:property value='%{id}'/>','<s:property value='%{filed_name}'/>')"/>
									 </li>											
							</ul>
			  			 </div>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</div>
</s:if>
<s:if test="globalLevel>4">
<!-- Level5 -->
<h3 class="ui-accordion-header"  id="%{level5Name}" onclick="tom(${content5});"><s:property value="%{level5Name}"/></h3>
	<div class="ui-accordion-content" style="display:none;" id="<s:property value="%{content5}"/>">
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive%{id}" name="formFive" target="iframename"   windowState="data_result"  namespace="/view/CloudApps" action="changeConfSetting"  theme="simple"  method="post"enctype="multipart/form-data" >
                  		 <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel5}"/>	</span>
					</div>
               <s:hidden id="mappedTableConf" name="mappedTableConf" value="%{commonMappedtablele4}"/> 
				<s:hidden name="mapedtable" value="%{mappedtablelevel5}"/>
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
				 	   <s:textfield id="sequence%{id}" name="sequence%{id}" value="%{sequence}" cssClass="textField" cssStyle="width:66px;    text-align: -webkit-center;"></s:textfield>
				 	</td>
				 	<td align="center">
				 		<s:if test="%{add_via=='Manual'}">
				 	    	<sj:div href="#" onclick="OnlickEdite('%{id}','%{mandatory}','%{field_name}','%{mappedtablelevel5}','update','%{commonMappedtablele4}','%{colType}','%{field_length}');"  id="%{id}"><font color="blue" ><s:property value="%{field_name}"/></font></sj:div>
				 	     </s:if>
				 	     <s:else>
				 	     	 <s:textfield id="field_name%{id}" name="field_name%{id}" value="%{field_name}" cssClass="textField" cssStyle="width:105px;    text-align: -webkit-center;"></s:textfield>
				 	     </s:else>
				 	</td>
				 	<td align="center">
				 	       <s:if test="%{add_via=='Manual'}">
						 	       		<s:hidden name="activeType%{id}" id="activeType%{id}" value="%{actives}"/>
				                  		<s:checkbox   cssStyle="margin-left: 32%;" name="activeTypeCheck%{id}" id="activeTypeCheck%{id}" onchange="changeValue('activeType%{id}');" value="%{actives}"  cssClass="form_menu_inputtext sub_select" />
								  </s:if>
								  <s:else>
				                 	<s:checkbox onclick="OnlickEditeee(this,'%{mappedtablelevel1}','%{mandatory}');" cssStyle=" margin-left: 32%; " name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select"  value="true"  disabled="true"/>
								  </s:else>
				 	</td>
				 	<td align="center" >
				 		  <s:property value="%{add_via}"/>
				 	</td>
				 	<td align="center">
				 	        <s:select list="#{'false':'Yes','true':'No'}" name="hideOrShow%{id}" id="hideOrShow%{id}" cssStyle="margin-left: 32%;" cssClass="form_menu_inputtext sub_select" ></s:select>
				 	</td>
				 	</tr>	
				 	</s:iterator>	
				 </table>
				 	<div class="fields" style="width:100%; padding:0;">
					<ul>
						<li class="submit">
							<input type="button" value="New Field" id="" title="" name="" class="submit" onclick="OnlickEdite('','%{mandatory}','','%{mappedtablelevel5}','insert','%{commonMappedtablele4}','','');">
						</li>	
						<li class="submit">
						 <input type="submit" value="Preview" class="submit" onclick="submitFn('formFive<s:property value='%{id}'/>','<s:property value='%{id}'/>','<s:property value='%{filed_name}'/>')"/>
						</li>										
					</ul>
			   </div>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</div>
</s:if>
</div>
</div>
</div>
<div id="data_result<s:property value='%{id}'/>" style=" float:left; padding:-20px 5%; width:50%;"></div>
</div>
</html>