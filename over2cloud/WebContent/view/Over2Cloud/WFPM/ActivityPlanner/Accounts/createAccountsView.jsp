<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="clear"></div>
<div class="middle-content">

<s:url id="accounts_view" action="accountViewData" escapeAmp="false">
<s:param name="fdate" value="%{fdate}"></s:param>
<s:param name="tdate" value="%{tdate}"></s:param>
<s:param name="emp_id" value="%{emp_id}"></s:param>
</s:url>

<sjg:grid 
		            id="accounts"
          			href="%{accounts_view}"
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
          			multiselect="true"  
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			navigatorEditOptions="{height:250,width:450}"
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
								name="mappedEmpName"
								index="mappedEmpName"
								title="Name"
							    width="300"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="month"
								index="month"
								title="Month"
							    width="250"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="total_due"
								index="total_due"
								title="Total Due"
							    width="250"
							    formatter="breakUpTotal"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="advance"
								index="advance"
								title="Advance"
							    width="240"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="balance"
								index="balance"
								title="Balance"
							    width="250"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="autoby"
								index="autoby"
								title="autoby"
							    width="250"
								align="center"
								editable="false"
    							search="true"
    							hidden="true"
								/>
								
								<sjg:gridColumn 
								name="autodate"
								index="autodate"
								title="autodate"
							    width="250"
								align="center"
								editable="false"
    							search="true"
    							hidden="true"
								/>
								
 					</sjg:grid> 
</div>
</body>
</html>