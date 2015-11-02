<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<title>Insert title here</title>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">

$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	        // backToProfilePage();
	       });

function backToProfilePage()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Patient/beforeprofileView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });

}


</SCRIPT>
</head>
<body>
<s:form theme="simple" name="profileModifyTable" method="post" namespace="/view/Over2Cloud/WFPM/Patient" action="modifyProfileTable">
<table align="left" width="60%" border="0">
							<tr >
								<td height="20px" width="30%">
									<div class="heading">First Name:</div>
								</td>
								<td width="70%">
									<s:textfield theme="simple" size="30" id="first_name" name="first_name" value="%{first_name}" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td height="20px">
									<div class="heading">Last Name:</div>
								</td>
								<td >
									<s:textfield  theme="simple"  id="last_name" size="30" name="last_name" value="%{last_name}"  cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">DOB :</div>
								</td>
								<td >
									
									<sj:datepicker name="age" id="age" readonly="true" value="%{age}"  changeMonth="true" value="today" changeYear="true" yearRange="2013:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" cssStyle="margin:0px 0px 10px 0px" placeholder="Select To date"/>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">Address:</div>
								</td>
								<td >
								<s:textfield  size="30" theme="simple"  id="address" name="address" value="%{address}"  cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField"></s:textfield>
									<%-- <s:property value="%{orgCountry}" /> --%>
								</td>
							</tr>
							<tr>
								<td >
									<div class="heading">Email:</div>
								</td>
								<td  >
									<s:textfield  theme="simple" size="30" id="email" name="email" value="%{email}" cssStyle="margin:0px 0px 10px 0px" readonly="false" cssClass="textField" ></s:textfield>
								</td>
							</tr>
							<tr>
								<td  >
									<div class="heading">Mobile:</div>
								</td>
								<td  >
									<s:textfield  readonly="false"  theme="simple" size="30" id="mobile" name="mobile" value="%{mobile}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField"></s:textfield>
								</td>
							</tr>
							<tr>
								<td  >
									<div class="heading">Gender:</div>
								</td>
								<td  >
									
									
									 <s:select 
                              			id="gender"
                              			name="gender"
                              			list="#{'Male':'Male','Female':'Female'}" 
                              			headerKey="-1"
                              			headerValue="Select gender" 
                              			cssClass="select"
                              			cssStyle="width:82%"
                              			>
                                </s:select>
									
								</td>
							</tr>
							<tr>
								<td  >
									<div class="heading">Nationality:</div>
								</td>
								<td  >
									
									
							<s:select 
                              id="nationality"
                              name="nationality" 
                              value="%{nationality}"
                              list="countryMap"
                              headerKey="-1"
                              headerValue="Select Nationality" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  			 </s:select>
									
								</td>
							</tr>
							
							<tr>
								<td  >
									<div class="heading">Patient Type:</div>
								</td>
								<td  >
									
									
							<s:select 
                              id="patient_type"
                              name="patient_type" 
                              list="#{'Corporate':'Corporate','Referral':'Referral','Walk-in':'Walk-in'}"
                              headerKey="-1"
                              headerValue="Select Source" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  			 </s:select>
									
								</td>
							</tr>
							
							
							<tr>
								<td  >
									<div class="heading">Patient Category:</div>
								</td>
								<td  >
									
									
							 <s:select 
                              id="patient_category"
                              name="patient_category" 
                              list="#{'Standard':'Standard','VVIP':'VVIP','Priority':'Priority','Others':'Others'}"
                              headerKey="-1"
                              headerValue="Select Patient Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
								</td>
							</tr>
							
							
							<tr>
								<td  >
									<div class="heading">Blood Group:</div>
								</td>
								<td  >
									
									
							 <s:select 
                             id="blood_group"
                              name="blood_group" 
                              list="#{'A Positive':'A+','O Positive':'O+','B Positive':'B+','AB Positive':'AB+','A Negative':'A-','O Negative':'O-','B Negative':'B-','AB Negative':'AB-'}"
                              headerKey="-1"
                              headerValue="Select Group" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
							
							
							
							
							<tr>
								<td colspan="2">
				<center><img id="indicator2"src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<sj:submit 
			 	value="Update" 
			 	button="true"
			 	 effect="highlight"
            	effectOptions="{ color : '#222222'}"
            	effectDuration="5000"
			 	cssStyle="margin-left: 115px;margin-top: 11px;"
			 	 targets="level123"
			 	 clearForm="true"
			 	onCompleteTopics="level1"
				/>
				<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left:5px;margin-top: 10px;"
		                     onclick="resetForm('orgModify');"
			            />
			            <sj:a
	                        cssStyle="margin-left: 5px;margin-top: 10px;" 
						button="true" href="#" value="View" 
						onclick="backToProfilePage();"
					>
						Back
					</sj:a>
								</td>
							</tr>
							<tr>
			<td colspan="2">
				<sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
			</td>
		</tr>
						</table>
						</s:form>
</body>
</html>