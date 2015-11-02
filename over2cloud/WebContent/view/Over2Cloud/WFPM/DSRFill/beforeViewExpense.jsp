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
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="overflow: scroll; height: 430px;">
<s:url id="dsr_expense_view" action="dsrExpenseView">
<s:param name="activityId" value="%{activityId}"></s:param>
</s:url>

<sjg:grid 
		            id="dsr_view"
          			href="%{dsr_expense_view}"
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
								name="expense"
								index="expense"
								title="Expense"
							    width="400"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="value"
								index="value"
								title="Value"
							    width="400"
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