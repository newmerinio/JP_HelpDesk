<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript">
	function ownerShipConfig()
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/OwnerShip/beforeOwnershipConfig.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}
	function wingsSetting()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/CreateFloorSetting.action?flag=CreateWings",
	    success : function(data) {
	   		$("#data_part").html(data);
		},
	    error: function() {
	        alert("error");
	    }
	 	});
	}
	
	
	
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Ownership</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	<tr></tr>
	<tr><td></td></tr>
	<tr>
	    <td>
		    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
		    <tbody><tr><td class="pL10"></td></tr></tbody>
		    </table>
	    </td>
	     
	    <td class="alignleft printTitle">
	    </td>
	    
	    <td class="alignright printTitle">
         <sj:a id="mappingButton1"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="ownerShipConfig();">Add</sj:a>
	    </td>   
	</tr>
	</tbody>
	</table>
	</div>
	</div>
<div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<s:url id="gridMappedLevel" action="gridMappedLevel"/>
<s:url id="gridMappedEmpl" action="gridMappedEmpl"/>
<sjg:grid 
		            id="mappedLevelGrid1"
          			href="%{gridMappedLevel}"
         			gridModel="masterViewList"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="false"
          			navigatorSearch="false"
          			rowList="100,200,500"
          			rownumbers="-1"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="false"  
          			loadonce="true"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
          		    filter="true"
          			filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
                    >
          
          			<sjg:grid 
		            			id="mappedEmpGrid0"
          						href="%{gridMappedEmpl}"
         						gridModel="masterViewList0"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="false"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="false"  
          						loadonce="true"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
          		    			filter="true"
         						filterOptions="{searchOnEnter: false, defaultSearch: 'cn'}"
                    			>
 							   
 							   <sjg:gridColumn 
										name="id"
										index="id"
										title="id"
									    width="100"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="true"
									/>
 							   
 							   <sjg:gridColumn 
										name="emp"
										index="emp"
										title="Employee"
									    width="500"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
										
								 <sjg:gridColumn 
										name="fromdept"
										index="fromdept"
										title="From Department"
									    width="600"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
										
	        						
 									</sjg:grid> 
 							   
        						<sjg:gridColumn 
										name="id"
										index="id"
										title="id"
									    width="100"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="true"
									/>
          
          							<sjg:gridColumn 
										name="department"
										index="department"
										title="For Department"
									    width="600"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
          
		          					<sjg:gridColumn 
										name="level"
										index="level"
										title="Ownership Level"
									    width="500"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
	        						
 									</sjg:grid> 
</div>
</div>
</div>
</body>
</html>