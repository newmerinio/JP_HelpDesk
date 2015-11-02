<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
	<div style="overflow: scroll; height: 400px;">
	<s:url id="viewRemainAsset" action="viewRemainAsset">
		<s:param name="outletId" value="%{outletId}"/>
	</s:url>
		<sjg:grid 
	          id="gridedittable111"
	          href="%{viewRemainAsset}"
	          gridModel="masterViewList"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="false"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          editinline="false"
	          rowList="14,100,200"
	          rowNum="14"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          rownumbers="-1"
	          pagerInput="false"   
	          multiselect="true"
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyAsset}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
          	>
			<s:iterator value="masterViewProp" id="masterViewProp" >
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="177"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
			</s:iterator>    
		</sjg:grid>
		</div>
</html>