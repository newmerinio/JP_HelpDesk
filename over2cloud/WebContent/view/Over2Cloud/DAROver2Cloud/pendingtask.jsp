<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>

</head>
<body>
<div class="list-icon">
	 <div class="head">Pending Task  </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div>

 <div style=" float:left; padding:10px 1%; width:98%;">
<div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<sj:a id="addButton"  button="true"   cssClass="button" onclick="createTaskType();">Back</sj:a>
	</td></tr></tbody></table>
	</td>
	<td class="alignright printTitle">
	</td>   
	</tr></tbody></table></div></div>
    </div>
<s:url id="pendingView" action="pendingView"></s:url>
<div style="overflow: scroll; height: 430px;">
<sjg:grid 
		  id="gridedittable22"
          href="%{pendingView}"
          gridModel="viewListForPending"
          navigatorEdit="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          rowNum="5"
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:100}"
          >
          
		<s:iterator value="taskTypeViewProp" id="taskTypeViewProp" >  
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>
</div>



</div>
</body>
</html>