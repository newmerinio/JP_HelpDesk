<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<title>Insert title here</title>
<script>
function uploadContact() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeUploadContact.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
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
	 Outlet </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(userRights.contains("ouet_ADD")) {%>
				    	<sj:a id="uploadButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="uploadContact();">Upload</sj:a>
 				  <%}%>
 				</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
	<div style="overflow: scroll; height: 400px;">
	<s:url id="mappedOutletGrid_URL" action="viewOutletAction">
	</s:url>
	<s:url id="gridMappedEmp_URL" action="gridMappedEmp"/>
	<sjg:grid 
		            id="mappedOutletGrid"
          			href="%{mappedOutletGrid_URL}"
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
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
          
          			<sjg:grid 
		            			id="mappedEmpGrid"
          						href="%{gridMappedEmp_URL}"
         						gridModel="masterViewList1"
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
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
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
										name="gridEmpName"
										index="gridEmpName"
										title="Mapped Person"
									    width="200"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
          
		          					<sjg:gridColumn 
										name="gridEmpContact"
										index="gridEmpContact"
										title="Contact No"
									    width="136"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
									
									<sjg:gridColumn 
										name="gridEmpEmail"
										index="gridEmpEmail"
										title="Email Id"
									    width="300"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpDesignation"
										index="gridEmpDesignation"
										title="Designation"
									    width="220"
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
							name="outletName"
							index="outletName"
							title="Outlet Name"
						    width="300"
							align="center"
							editable="false"
  							search="true"
  							hidden="false"
					/>
				
					<sjg:gridColumn 
							name="address"
							index="address"
							title="Address"
						    width="603"
							align="center"
							editable="false"
  							search="true"
  							hidden="false"
						/>
			</sjg:grid>
	</div>
		</div>
		</div>
		</div>
	
</body>
</html>