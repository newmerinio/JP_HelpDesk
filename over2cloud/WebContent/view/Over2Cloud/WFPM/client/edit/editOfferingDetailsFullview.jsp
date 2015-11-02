<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/WFPM/editoffering.js"/>"></script>

<!-- 
<script type="text/javascript">
$("#refName").fadeOut();
var div = document.getElementById('refId');
div.style.visibility='hidden';
</script>
 -->
<script type="text/javascript">

function cancelButton()
{
$('#completionResult').dialog('close');
$('#clientofferingDialog').dialog('close');
	}  
</script>

</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
<sj:div id="orglevel33"  effect="fold"><div id="assOfferingid"></div></sj:div>
  </sj:dialog>
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

			<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
       		 </div>	
        		<br>
<!-- Client Offering Mapping ************************************************************************* -->
<s:if test="clientForOfferingDetails>0">
	<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/wfpm/client" action="addOffering" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
    <div class='clear'></div>
    <span id="form2MandatoryFields" class="qIds" style="display: none; ">clientN#Client#D#,</span>
    <div class="newColumn">
	<div class="leftColumn1">Opportunity Name:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                   id="clientN"
                   name="clientName" 
                   list="#{'-1':'Select'}"
                   headerKey="-1"
                   headerValue="Select Name" 
                   cssClass="select"
                   cssStyle="width:82%"
                  >
       
    </s:select>
	</div>
	</div>
	
	<s:if test="OLevel1">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">verticalnameoffering#<s:property value="%{OLevel1LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="verticalnameoffering"
                              name="verticalname" 
                              list="verticalMap"
                              headerKey="-1"
                              headerValue="Select Name" 
                               cssClass="select"
                              cssStyle="width:82%" 
                              onchange="fetchLevelData(this,'offeringnameiDD','1')"
                              >
                   
                  </s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel2">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">offeringnameiDD#<s:property value="%{OLevel2LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="offeringnameiDD"
                              name="offeringname" 
                              list="#{'-1':'Select'}"
                               cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchLevelData(this,'subofferingnameIDDD','2')"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel3">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">subofferingnameIDDD#<s:property value="%{OLevel3LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="subofferingnameIDDD"
                              name="subofferingname" 
                              list="#{'-1':'Select'}"
                               cssClass="select"
                              cssStyle="width:82%"
                              onchange="fetchLevelData(this,'variantnameID','3')"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	<div class="clear"></div>
	
	<s:if test="OLevel4">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">variantnameID#<s:property value="%{OLevel4LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel4LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
                              id="variantnameID"
                              name="variantname" 
                               cssClass="select"
                              cssStyle="width:82%"
                              list="#{'-1':'Select'}"
                              onchange="fetchLevelData(this,'subvariantsizeIDD','4')"
                              >
                  	</s:select>
	</div>
	</div>
	</s:if>
	
	<s:if test="OLevel5">
	<span id="form2MandatoryFields" class="qIds" style="display: none; ">subvariantsizeIDD#<s:property value="%{OLevel5LevelName}"/>#D#,</span>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
	<div class="rightColumn1">
	<span class="needed"></span>
	<s:select 
	                             id="subvariantsizeIDD"
	                             name="subvariantsize" 
	                             list="#{'-1':'Select'}"
	                              cssClass="select"
                              cssStyle="width:82%"                        
	                             >
	                 	</s:select>
	                 	
	</div>
	</div>
	</s:if>
	
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Brief:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_name"  id="opportunity_name1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Value:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_value"  id="opportunity_value1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	
	<div class="newColumn">
	<div class="leftColumn1">Closure Date:</div>
	<div class="rightColumn1">
	<sj:datepicker name="closure_date" id="closure_date1" showOn="focus"	displayFormat="dd-mm-yy" yearRange="2014:2020" 	readonly="true" cssClass="textField"  cssStyle="margin: 0px 6px 10px; width: 82%;" placeholder="Enter Data"></sj:datepicker>
	</div>
	</div>
	
	<div class="newColumn">
	<div class="leftColumn1">Expectency:</div>
	<div class="rightColumn1">
	  <s:select  
	   id="expectency"
	   name="expectency" 
	   list="#{'High':'High','Medium':'Medium','Low':'Low'}"
	   headerKey="-1"
	   headerValue="Select" 
	   cssClass="select"
	   cssStyle="width:82%"
	  >
	 </s:select>
	</div>
	</div>
	
	<div id="newDiv" style="float: right;margin-top: -35px;margin-right: 50px;"><sj:a value="+" onclick="adddiv('100')" button="true">+</sj:a></div>
	
	<s:iterator begin="100" end="103" var="deptIndx">
	<div class="clear"></div>
	<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
	<s:if test="OLevel1">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel1LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                             id="verticalname"
	                             name="verticalname" 
	                             list="verticalMap"
	                             headerKey="-1"
	                             headerValue="Select Name" 
	                              cssClass="select"
                              cssStyle="width:82%"
	                             onchange="fetchLevelData(this,'%{#deptIndx}2','1')"
	                             >
	                 </s:select>
	</div>
	</div>
	</s:if>
	<s:if test="OLevel2">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel2LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}2"
	                              name="offeringname" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                   cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}3','2')"
	                              >
	                             </s:select>
	</div>
	</div>
	</s:if>
	
	<s:if test="OLevel3">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel3LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}3"
	                              name="subofferingname" 
	                              list="#{'-1':'Select'}"
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}4','3')"
	                              />
	</div>
	</div>
	</s:if>
	<s:if test="OLevel4">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel4LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}4"
	                              name="variantname" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                   cssStyle="width:82%"
	                              onchange="fetchLevelData(this,'%{#deptIndx}5','4')"
	                              />
	</div>
	</div>
	</s:if>
	<s:if test="OLevel5">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{OLevel5LevelName}"/>:</div>
	<div class="rightColumn1">
	<s:select 
	                              id="%{#deptIndx}5"
	                              name="subvariantsize" 
	                              list="#{'-1':'Select'}"
	                               cssClass="select"
                                    cssStyle="width:82%"
	                              />
	</div>
	</div>
	</s:if>
	
	<div style="float: right;margin-top: -35px;">	
		<s:if test="#deptIndx==103">
	    	<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
        </s:if>
       	<s:else>
     	  	<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
           	<div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
         </s:else>
	</div>
	
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Brief:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_name"  id="opportunity_name1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Opportunity Value:</div>
	<div class="rightColumn1">
	<s:textfield name="opportunity_value"  id="opportunity_value1"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
	</div>
	</div>
	<div class="newColumn">
	<div class="leftColumn1">Closure Date:</div>
	<div class="rightColumn1">
	<sj:datepicker name="closure_date" id="closure_date%{#deptIndx}" showOn="focus"	displayFormat="dd-mm-yy" yearRange="2014:2020" 	readonly="true" cssClass="textField"  cssStyle="margin: 0px 6px 10px; width: 82%;" placeholder="Enter Data"></sj:datepicker>
	</div>
	</div>
	
	<div class="newColumn">
	<div class="leftColumn1">Expectency:</div>
	<div class="rightColumn1">
	  <s:select  
	   id="expectency%{#deptIndx}"
	   name="expectency" 
	   list="#{'High':'High','Medium':'Medium','Low':'Low'}"
	   headerKey="-1"
	   headerValue="Select" 
	   cssClass="select"
	   cssStyle="width:82%"
	  >
	 </s:select>
	</div>
	</div>
	
	</div>
	</s:iterator>
	<div class="clear"></div>
	<br>
	<!-- Buttons -->
	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="buttonStyle" style="text-align: center;margin-left: -100px;">
	         <sj:submit 
	           targets="assOfferingid" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="form2"
                       cssClass="submit"
                       indicator="indicator3"
                       onBeforeTopics="validate2"
	          />
	        
	    </div>
	
	</s:form>  
</s:if>
</div>
</div>
<SCRIPT type="text/javascript">
$("#clientName").val("");
fillName('Client','clientN',0);
</SCRIPT>
</body>
</html>