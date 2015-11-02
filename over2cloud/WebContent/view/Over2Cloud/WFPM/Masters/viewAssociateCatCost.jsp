<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

	<center>
	<s:url id="viewGrid" namespace="/view/Over2Cloud/wfpm/masters" action="fetchAssociateCatCost" >
		<s:param name="id" value="%{id}"></s:param>
	</s:url>
	<s:url id="viewModifyGrid" namespace="/view/Over2Cloud/wfpm/masters" action="editAssociateCatCost" />
	<sjg:grid 
			  id="gridForAssociateCatCost"
	          caption="%{mainHeaderName}"
	          href="%{viewGrid}"
	          gridModel="masterViewList"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          rowList="15,20,25"
	          rownumbers="-1"
	          viewrecords="true"       
	          pager="true"
	          pagerButtons="true"
	          pagerInput="false"   
	          multiselect="true"  
	          loadingText="Requesting Data..."  
	          rowNum="5"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorSearchOptions="{sopt:['eq','cn']}"
	          editurl="%{viewModifyGrid}"
	          navigatorEditOptions="{reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          width="525"
	          >
			<s:iterator value="masterViewProp" id="masterViewProp" >  
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
			searchoptions="{sopt:['eq','cn']}"
			/>
			</s:iterator>  
	</sjg:grid>
	</center>
<script type="text/javascript">
	toggle_visibility('content3','tab3');
	function toggle_visibility(id1,id2) {
	if(document.getElementById(id1).style.display == 'block'){
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"inactive";
		sub1.style.display	=	"none";
	}else{
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"active";
		sub1.style.display	=	"block";
	}
}
</script>
