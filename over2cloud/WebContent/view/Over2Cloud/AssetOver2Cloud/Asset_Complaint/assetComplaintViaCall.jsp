<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/assetComplaint.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Call</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Ticket Lodge</div> 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="secHead">Complainant Detail</div>
<div class="newColumn">
	 <div class="leftColumn"></div>
        <div class="rightColumn">
           <s:radio list="#{'asset':' Asset','employee':' User'}" id="test" name="test" value="%{'asset'}" onclick="showHideBlock(this.value);"></s:radio>
        </div>
</div>
<s:form id="formone" name="formone" theme="css_xhtml"  method="post" action="/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/confirmAssetComplaintEsc.jsp"enctype="multipart/form-data" >
<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  
    <s:hidden name="viaFrom" value="Call"></s:hidden>
	<s:hidden name="dataFor" value="ASTM"></s:hidden>
	<s:hidden name="bydept" id="bydept"></s:hidden>
	<s:hidden name="assetid" id="assetid"></s:hidden>
	<div class="clear"></div>
	 <div id="asset_block" style="display: block">
	 <s:iterator value="assetFieldList" status="status" begin="5" end="5">
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:textfield name="asset_assetserialno"  id="asset_assetserialno" onchange="getDetail4CallOrOnline('asset_assetserialno','asset','asset_call');"  cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 </s:iterator>
	 <s:iterator value="assetFieldList" status="status" begin="4" end="4">
	   <div class="newColumn">
		  <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	            <div class="rightColumn">
	                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                <s:textfield name="asset_assetid"  id="asset_assetid" cssClass="textField" placeholder="Enter Data" />
	            </div>
       </div>
       </s:iterator>
	 </div>
	 <s:iterator value="assetFieldList" status="status" begin="0" end="3">
	  <s:if test="%{mandatory}">
			     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
	 <s:if test="#status.odd">
	 <div class="newColumn">
		  <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	            <div class="rightColumn">
	                		     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                 <s:textfield name="%{key}"  id="%{key}" onblur="getDetail4CallOrOnline('%{key}','emp','emp_call'); getAssetDetail('%{key}','emp_assetid','Select Asset');" maxlength="10" cssClass="textField" placeholder="Enter Data" />
	             </div>
      </div>
	 </s:if>
	 <s:else>
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 </s:else>
	 </s:iterator>
	 
	 <div id="emp_block" style="display: none">
	  <s:iterator value="assetFieldList" status="status" begin="4" end="4">
	   <div class="newColumn">
		  <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	            <div class="rightColumn">
	                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                <s:select  
	                           id="emp_assetid"
                               name="emp_assetid"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Asset" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="getDetail4CallOrOnline(this.id,'asset','emp_call_asset')"
                               >
				   </s:select>
	            </div>
       </div>
       </s:iterator>
       <s:iterator value="assetFieldList" status="status" begin="5" end="5">
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				   <s:textfield name="emp_assetserialno"  id="emp_assetserialno"  cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 </s:iterator>
	 </div>
	 <div class="secHead">Service Department</div>
	 
	 <s:iterator value="assetFieldList" status="status" begin="6" end="9">
	  <s:if test="%{mandatory}">
			     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
	 <s:if test="key=='to_dept'">
	 <div class="newColumn">
		  <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	            <div class="rightColumn">
	                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                <s:select  
	                           id="%{key}"
                               name="%{key}"
                               list="serviceDeptList"
                               headerKey="-1"
                               headerValue="Select Department" 
                               cssClass="select"
	                           cssStyle="width:82%"
                               onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','CASTM','fbType','ASC','feedType','Select Feedback Type');">
				   </s:select>
	            </div>
      </div>
	 </s:if>
	 <s:elseif test="key=='feedType'">
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="%{key}"
                               name="%{key}"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Feedback Type" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','category','Select Category');"
                              >
				   </s:select>
				</div>
		 </div>
	 </s:elseif>
	 <s:elseif test="key=='category'">
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="%{key}"
                               name="%{key}"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Category" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="commonSelectAjaxCall('feedback_subcategory','id','subCategoryName','categoryName',this.value,'','','subCategoryName','ASC','subCatg','Select Sub-Category');">
				   </s:select>
				</div>
		 </div>
	 </s:elseif>
	 <s:elseif test="key=='subCatg'">
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:select  
	                           id="%{key}"
                               name="%{key}"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="Select Sub Category" 
                               cssClass="select"
	                           cssStyle="width:82%"
	                           onchange="getFeedBreifViaAjax(this.value);"
                              >
				   </s:select>
				</div>
		 </div>
	 </s:elseif>
	 </s:iterator>
	 
	 <s:iterator value="assetFieldList" status="status" begin="10">
	  <s:if test="%{mandatory}">
			     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
	 <s:if test="#status.odd">
	 <div class="newColumn">
		  <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	            <div class="rightColumn">
	                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		            <s:textfield name="%{key}"  id="%{key}" onblur="getDetail('%{key}')" maxlength="10" cssClass="textField" placeholder="Enter Data" />
	             </div>
      </div>
	 </s:if>
	 <s:else>
	      <div class="newColumn">
                <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
				     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 </s:else>
	 </s:iterator>
	 
	<div class="newColumn">
	     <div class="leftColumn"></div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
    </div>

    <div class="clear"></div>
	 <br>
	
	 <span id="normalSubdept" class="pIds" style="display: none; ">recvSMS#recvSMS#C#CB,recvEmail#recvEmail#C#CB,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">recvSMS#recvSMS#C#CB,recvEmail#recvEmail#C#CB,</span>
               
		 <div class="fields" align="center">
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
                        targets="confirmingEscalation" 
                        clearForm="true"
                        value="Register" 
                        button="true"
                        onBeforeTopics="validate"
                        onCompleteTopics="beforeFirstAccordian"
                        cssStyle="margin-left: -40px;"
				        />
			            <sj:a cssStyle="margin-left: 200px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
			            <sj:a cssStyle="margin-left: 7px;margin-top: -45px;" button="true" href="#" onclick="viewForm();">View</sj:a>
	        </div>
	        </li>
		 </ul>
		 </div>
	     
	     <div align="center">
			<sj:dialog id="printSelect" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Call" width="750" height="450" showEffect="slide" hideEffect="explode" position="['center','top']">
                     <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div>
                     <div id="confirmingEscalation"></div>
            </sj:dialog>
         </div>
</s:form>
</div>
</div>
</body>
</html>
