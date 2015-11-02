<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    <%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">Mapped KRA-KPI View</div>
	</div>
	<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
				<input id="sendMailButton" class="button"  value="Edit" type="button" onclick="editRow()">
				<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="deleteRow()">		
				<!--<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button"></span> 
				--></td></tr></tbody></table>
				<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="#"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   
				</tr></tbody></table></div>
			</div>
	 </div>
	 	<sjg:grid 
				  id="gridedittable1"
		          href="%{incentiveRecords}"
		          gridModel="incentiveData"
		          navigator="true"
		          navigatorAdd="false"
		          navigatorView="true"
		          navigatorDelete="%{deleteFlag}"
		          navigatorEdit="%{modifyFlag}"
		          navigatorSearch="false"
		          rowList="10,15,20,25"
		          rownumbers="-1"
		          viewrecords="true"       
		          pager="true"
		          pagerButtons="true"
		          pagerInput="false"   
		          multiselect="true"  
		          loadingText="Requesting Data..."  
		          rowNum="10"
		          navigatorEditOptions="{height:230,width:400}"
		          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
		          editurl="%{viewModifyIncentive}"
		          navigatorEditOptions="{reloadAfterSubmit:true}"
		          shrinkToFit="true"
		          autowidth="true"
		          onSelectRowTopics="rowselect"
		          >
		        
				<s:iterator value="viewIncentive" id="viewIncentive" >  
				<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				formatter="%{formatter}"
				search="%{search}"
				hidden="%{hideOrShow}"
				/>
				</s:iterator>  
		</sjg:grid>
	 
</body>
</html>