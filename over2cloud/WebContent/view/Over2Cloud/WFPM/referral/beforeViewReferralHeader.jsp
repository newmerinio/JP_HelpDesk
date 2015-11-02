<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<STYLE type="text/css"></STYLE>
</head>
<script type="text/javascript" src="<s:url value="js/referral/referral.js"/>"></script>
<script type="text/javascript">

</script>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		
	<div id="Aid" class="head">Referral</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div><div class="head">View For </div>
	<div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div>
	<div class="head"><s:select
			id="referralSubType" 
			name="referralSubType" 
			list="#{'Individual':'Individual','Institutional':'Institutional'}" 
            theme="simple"
            cssStyle="height: 26px;margin-top: 4px;"
            onchange="getViewAdd(this.value)"
			cssClass="button"
            />
	</div>
	<div class="head"><img alt="" src="images/forward.png" style="margin-top:8px; float: left;"></div>
	<div class="head"><s:select
			id="referralStatus" 
			name="referralStatus" 
			list="#{'Approved':'Approved Referral','Unapproved':'Unapproved Referral'}" 
            theme="simple"
            cssStyle="height: 26px;margin-top: 4px;"
          onchange="getViewAdd(this.value)"
			cssClass="button"
            />
	</div>
	
	<div style="float:right;margin: 4px;"> <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Adhoc"
	          onclick="adhoc()"
				>Adhoc</sj:a>   
	</div>
	
	<!--indivisual Header---------------------------------------------------------->
	<div id="individual" style=" float:left; padding:10px 1%; width:98%;">
	
		<div class="clear"></div>
		
		
		<div id="alphabeticalLinks"></div>
			<div class="listviewBorder">
				<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"><!-- 
					 <div id="editButtonDiv" style="float: left;"><sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a></div> --%>
					--><sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					<%-- <div id="delButtonDiv" style="float: left;"><sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a></div>
					<!--<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="searchRow()"></sj:a>
					--><sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="refreshRow()"></sj:a>
					
				From Date:<sj:datepicker cssStyle="height: 16px; width: 70px; margin-left: 4px;" cssClass="button"  id="from_date"  name="from_date" size="20" maxDate="0"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" onchange="searchResult('','','','');" />
		     	To Date:<sj:datepicker cssStyle="height: 16px; width: 70px;margin-left: 4px;" cssClass="button" value="today" id="to_date" name="to_date" size="20"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
				
					      Source:<s:select 
		                              id="from_source"
		                              name="from_source" 
		                              list="sourceMap" 
		                              headerKey="-1"
    								  headerValue="Select Source" 
    		                          theme="simple"
		                              cssClass="button"
		                              cssStyle="height: 28px; width: 13%;"
		                              onchange="searchResult('','','','');"
		                              >
		             		   </s:select>
				
				Data Status:<s:select
									id="data_status" 
									name="data_status" 
							   	  list="#{'All Status':'All Status','0':'Active','1':'Inactive'}"
									cssStyle="height: 28px; width: 13%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							    />
				Search Any Value:<s:textfield 
	    	   					 id="searchParameter" 
	            				 name="searchParameter" 
	           					 onkeyup="searchResult('','','','');" 
	           					 theme="simple" cssClass="button" 
	           				     cssStyle="margin-top: -3px;height: 15px;width:8%;margin-left: 4px;"
	          					 Placeholder="Search Any Value" 
	          	                />
	          	       
	          	                
				</td></tr></tbody></table>
				</td>
				
				
				<td class="alignright printTitle">
					<%-- <sj:a  button="true" cssClass="button" 
					buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a> --%>
	<s:if test="true ||  #session.userRights.contains('pras_ADD')">					 
		 <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Action"
	          onclick="action()"
				>Action</sj:a>   
		
		
        <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Add"
	            buttonIcon="ui-icon-plus"
				onclick="addRefIndivisual();"
				>Add</sj:a>   
	</s:if>			       
	              </td>   
				</tr></tbody></table></div>
			</div>
			</div>

	<div id="datapart">
		<!-- Grid will come dynamically here from page 'viewsearchAssociate.jsp' -->
	</div>

		
		</div>
		
		<!--institutional Header---------------------------------------------------------->
		
		<div id="institutional" style=" float:left; padding:10px 1%; width:98%;display: none;">
	
		<div class="clear"></div>
		
		
		<div id="alphabeticalLinks1"></div>
			<div class="listviewBorder">
				<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<%-- <div id="editButtonDiv" style="float: left;"><sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a></div> --%>
					<sj:a id="editButton1" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true" onclick="editRow()"></sj:a>
					<%-- <div id="delButtonDiv" style="float: left;"><sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a> --%>
					<sj:a id="delButton1" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="deleteRow()"></sj:a></div>
					<!--<sj:a id="searchButton1" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="searchRow()"></sj:a>
					--><sj:a id="refButton1" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" 
						button="true"  onclick="refreshRow()"></sj:a>
					
								From Date:<sj:datepicker cssStyle="height: 16px; width: 70px; margin-left: 4px;"  cssClass="button" id="from_date1"  name="from_date1" size="20" maxDate="0"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" onchange="searchResult('','','','');" />
		                     	To Date:<sj:datepicker cssStyle="height: 16px; width: 70px;margin-left: 4px;" value="today" cssClass="button" id="to_date1" name="to_date1" size="20"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
				
					       Source: <s:select 
		                              id="from_source1"
		                              name="from_source" 
		                              list="sourceMap" 
		                              headerKey="-1"
    								  headerValue="Select Source" 
    		                          theme="simple"
		                              cssClass="button"
		                              cssStyle="height: 28px; width: 13%;"
		                              onchange="searchResult('','','','');"
		                              >
		             		   </s:select>
				
				  Data Status:<s:select
									id="data_status1" 
									name="data_status" 
							   	  list="#{'All Status':'All Status','0':'Active','1':'Inactive'}"
									cssStyle="height: 28px; width: 13%;"
									theme="simple"
									cssClass="button"
									onchange="searchResult('','','','');"
							    />
				Search Any Value:<s:textfield 
	    	   					 id="searchParameter1" 
	            				 name="searchParameter" 
	           					 onkeyup="searchResult('','','','');" 
	           					 theme="simple" cssClass="button" 
	           				     cssStyle="margin-top: -3px;height: 15px;width:8%;margin-left: 4px;"
	          					 Placeholder="Search Any Value" 
	          	                />
	          	               
				
				</td></tr></tbody></table>
				</td>
				<td class="alignright printTitle">
					<%-- <sj:a  button="true" cssClass="button" 
					buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="excelUpload();">Import</sj:a> --%>
	<s:if test="true ||  #session.userRights.contains('pras_ADD')">					 
		
		
		 <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Action"
	       
				onclick="action()"
				>Action</sj:a>   

        <sj:a
				button="true"
				cssClass="button" 
				cssStyle="height:25px;"
                title="Add"
	            buttonIcon="ui-icon-plus"
				onclick="addRefIndivisual()"
				>Add</sj:a>   
	</s:if>			       
	     
				</td>   
				</tr></tbody></table></div>
			</div>
			</div>

	<div id="datapart1">
		<!-- Grid will come dynamically here from page 'viewsearchAssociate.jsp' -->
	</div>

		
		</div>
		<br><br>
<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		<div id="divFullHistory" style="float:left; width:97%; padding-left: 1%;"> </div>
</center>
<sj:dialog
	id="contactPersonDetailsDialog"
	autoOpen="false"
	modal="true"
></sj:dialog>
</body>
</html>