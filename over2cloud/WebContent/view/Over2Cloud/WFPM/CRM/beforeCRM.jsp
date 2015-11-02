<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">
.user_form_input{
    margin-bottom:10px;
}

.leftColumn{
text-align: right;
padding: 6px;
line-height: 9px;
float: left;
width: 25%;
}

.rightColumn{
padding: 6px;
line-height: 9px;
float: left;
text-align: left;
width: 68%;
}

.paramClass{
width: 125px;
float: left;
}
</style>
<html>
<head>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="js/WFPM/CRM/CRMComm.js"/>"></script>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">CRM Group</div>
		<div class="head"><img alt="" src="images/forward.png" style="margin-top:60%; float: left;"></div>
	<div class="head">Manage Parameters</div>
	</div>

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/client" action="addClient" 
	theme="css_xhtml"  method="post" enctype="multipart/form-data" >
        
    <div class="menubox">
     <div style="width: 100px; float: left; text-align: right;">Group Name:</div>
			<div class="paramClass" >
				<s:textfield id="groupName"  name="groupName" placeholder="Enter Group Name" 
					cssStyle="height: 20px; width:115px; border: 1px solid #c0c0c0; "></s:textfield>
			</div>
	 		 <div style="width: 100px; float: left; text-align: right;">Brief:</div>
			<div class="paramClass" >
				<s:textfield id="comment"  name="comment" placeholder="Enter Brief" 
					cssStyle="height: 20px; width:115px; border: 1px solid #c0c0c0; "></s:textfield>
			</div>
			<div style="width: 100px; float: left; text-align: right;">Create Type:</div>
	 		<div class="paramClass" >
				<s:select 
		                     id="createType"
		                     name="createType" 
			                 list="#{'-1':'Create Type','Auto':'Auto','Manual':'Manual'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
		                     >
		         </s:select>
		    </div> 
		    <div style="width: 100px; float: left; text-align: right;">View Type:</div>
	 		<div class="paramClass" >
				<s:select 
		                     id="viewType"
		                     name="viewType" 
			                 list="#{'-1':'View Type','Private':'Private','Public':'Public'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
		                     >
		         </s:select>
		    </div>
		    <div class="clear"></div> 
		    <br>
	<div style="width: 100px; float: left; text-align: right; vertical-align: bottom;">Relationship Type:</div>
	<div class="paramClass">
	         <s:select 
	                     id="entityType"
	                     name="entityType" 
	                     list="#{'3':'Select Relationship','0':'Lead','1':'Client','2':'Associate'}"
	                     cssClass="button"
                       	 cssStyle="width:120px; height:25px;"
                       	 onchange="fetchEntityData(this.value);"
                       	 theme="simple"
	                     >
	         </s:select>
	</div>
	<div class="paramClass" id="subType" style="display: none;">
	         <s:select 
	                     id="relationshipSubType"
	                     name="relationshipSubType" 
	                     list="#{'-1':'All Sub-Type'}"
	                     cssClass="button"
                       	 cssStyle="width:120px; height:25px;"
                       	 theme="simple"
                       	 onchange="beforeFetchCRMDataView();"
	                     >
	         </s:select>
	</div>
	<div class="clear"></div>
	<div style="height: 5px;"></div>	
	<!-- Parameters fields -->
	<div id="parameterFields" style="display: none ; margin-left: -52px;">
	<div style="width: 150px; float: left; text-align: right;">Organization Search:</div>
	
		<div class="paramClass" >
			<s:select
				id="industry" 
				name="industry" 
                list="#{'-1':'All Industry'}"
	            theme="simple"
	            cssStyle="width:120px; height:25px;"
	            onchange="fillName(this.value,'subIndustry');"
				cssClass="button"
            />
		</div>
		<div class="paramClass" >
			<s:select
				id="subIndustry" 
				name="subIndustry" 
                list="#{'-1':'All Sub-Industry'}"
	            cssStyle="width:120px; height:25px;"
	            theme="simple" 
	            onchange="beforeFetchCRMDataView();"
				cssClass="button"
            />
		</div>
		<div class="paramClass" >
			<s:select 
	                    id="rating" 
						name="rating"
		                list="#{'-1':'All Rating','1':'1','2':'2','3':'3','4':'4','5':'5'}"
						cssStyle="width:120px; height:25px;"
			            theme="simple" 
			            onchange="beforeFetchCRMDataView();"
						cssClass="button"
	                     >
	         </s:select>
		</div>
		<div class="paramClass" >
			<s:select 
	                    id="source" 
						name="source" 
		                list="#{'-1':'All Source'}"
						cssStyle="width:120px; height:25px;"
						theme="simple"
						onchange="beforeFetchCRMDataView();"
						cssClass="button"
	                     >
	         </s:select>
		</div>
		
		<div class="paramClass" >
			<s:select 
	                    id="location" 
						name="location" 
		                list="#{'-1':'All Location'}"
						cssStyle="width:120px; height:25px;"
						theme="simple"
						onchange="beforeFetchCRMDataView();"
						cssClass="button"
	                     >
	         </s:select>
		</div>
		<div class="paramClass" >
			<s:select 
	                     id="employee"
	                     name="employee" 
		                 list="#{'-1':'All Account Manager'}"
						 cssClass="button"
                       	 cssStyle="width:120px; height:25px;"
                       	 theme="simple"
                       	 onchange="beforeFetchCRMDataView();"
	                     >
	         </s:select>
		</div>
		
		<div id="forAssociateDiv" style="display: none">
			<div class="paramClass" >
				<s:select 
		                     id="refBy"
		                     name="refBy" 
			                 list="#{'-1':'All Refered By','Associate':'Associate','Client':'Client'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="fillNameNew(this.value,'refName',0)"
		                     >
		         </s:select>
			</div>
			<div class="paramClass" >
				<s:select 
		                     id="refName"
		                     name="refName" 
			                 list="#{'-1':'All Refered Name'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
			</div>
		</div>
		<div class="clear"></div>
		<div style="height: 5px;"></div>	
		
		<!-- Contact person **************************************************************************************** -->
		<div style="width: 150px; float: left; text-align: right;">Contact Search:</div>
		<div class="paramClass" >
				<s:select 
		                     id="department"
		                     name="department" 
			                 list="#{'-1':'All Department'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
			</div>
			<div class="paramClass" >
				<s:select 
		                     id="designation"
		                     name="designation" 
			                 list="#{'-1':'All Designation'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
	         </div>
			<div class="paramClass" >
				<s:select 
		                     id="influence"
		                     name="influence" 
			                 list="#{'-1':'All Influence','1':'1','2':'2','3':'3','4':'4','5':'5'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
		    </div>    
		    
		    <div class="paramClass" >
				<s:select 
		                     id="age"
		                     name="age" 
			                 list="#{'-1':'All Ages','20 Plus':'20 +Plus','30 Plus':'30 +Plus','40 Plus':'40 +Plus','50 Plus':'50 +Plus','60 Plus':'60 +Plus'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
		    </div>    
		    
		    <div class="paramClass" >
				<s:select 
		                     id="sex"
		                     name="sex" 
			                 list="#{'-1':'All Gender','Male':'Male','Female':'Female'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
		    </div>    
		    
		    <div class="paramClass" >
		         <s:select 
		                     id="allergic_to"
		                     name="allergic_to" 
			                 list="#{'-1':'All Allergic To'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
		    </div>    
		    
		    <div class="paramClass" >
				<s:select 
		                     id="blood_group"
		                     name="blood_group" 
			                 list="#{'-1':'All Groups','A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
							 cssClass="button"
	                       	 cssStyle="width:120px; height:25px;"
                       	 	 theme="simple"
                       	 	 onchange="beforeFetchCRMDataView();"
		                     >
		         </s:select>
		    </div>     
		    <div class="clear"></div>
		    <div style="height: 5px;"></div>	     
		         
		    <!-- Birthday -->     
	        <div style="width: 150px; float: left; text-align: right;">Birthday:</div>
			<div class="paramClass" >
				<s:checkbox  id="chkBirthday" name="chkBirthday" theme="simple" onchange="beforeFetchCRMDataView()" />
			</div>
			<div class="paramClass" >
				<sj:datepicker name="birthdayFrom" id="birthdayFrom" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  changeYear="true"  
					value="today" yearRange="1890:2020" showOn="focus" title="From" displayFormat="MM" cssClass="button"
					 cssStyle="width:87px;" onchange="birthdayBeforeFetchCRMDataView();" />
			</div>
		
		    <!-- Anniversary -->     
	        <div class="paramClass" >Anniversary:</div>
			<div class="paramClass" >
				<s:checkbox  id="chkAnniversary" name="chkAnniversary" theme="simple" onchange="beforeFetchCRMDataView()" />
			</div>
			<div class="paramClass" >
				<sj:datepicker name="anniversaryFrom" id="anniversaryFrom" changeMonth="true"  cssStyle="margin:0px 0px 10px 0px"  changeYear="true" 
					value="today" yearRange="1890:2020" showOn="focus" title="From" displayFormat="MM" cssClass="button" cssStyle="width:87px;" 
					onchange="anniversaryBeforeFetchCRMDataView();" />
			</div>
		
		<div class="clear"></div>
		<div style="height: 5px;"></div>
		<!--	     
		         
		     Group      
	        <div style="width: 150px; float: left; text-align: right;">Create Auto Group?:</div>
			<div class="paramClass" >
				<s:checkbox  id="chkGroup" name="chkGroup" theme="simple" onclick="showGroup(this,'groupName');"  />
			</div>
			<div class="paramClass" style="height: 20px;">
				<s:textfield id="groupName"  name="groupName" placeholder="Enter Group Name" 
					cssStyle="display:none; height: 20px; width:115px; border: 1px solid #c0c0c0; "></s:textfield>
			</div>
	-->
	 		
			
	
	</div>
	<!-- menubox -->	
	</div>
		
	<div class="clear"></div>
	 <sj:a 
	     	name="communicate"  
			href="#" 
			cssClass="submit" 
			button="true" 
			onclick="communicateMe();"
		>
		  	Send SMS
		</sj:a>	
		 <sj:a 
	     	name="communicateviamail"  
			href="#" 
			cssClass="submit" 
			button="true" 
			onclick="communicateViaMail();"
		>
		  	Send Mail
		</sj:a>
		 <sj:a 
	     	name="back"  
			href="#" 
			cssClass="submit" 
			button="true" 
			onclick="backToMainView();"
		>
		  	Back
		</sj:a>
		
	<!-- Dynamic Lead/ Client/ Associate will come here -->	
	<div id="dynamicGridData" style="width: 100%; height: 350px; float: left;">
	</div>		
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
</s:form>	
</div></div>
</body>
	<sj:dialog 
		id="sendPageDialog"  
		modal="true" 
		effect="slide" 
		autoOpen="false" 
	    width="1000" 
	    height="470" 
	    title="Send Page" 
	    loadingText="Please Wait..." 
	    >
</sj:dialog>

<SCRIPT type="text/javascript">
beforeFetchCRMDataView();
</SCRIPT>
</html>