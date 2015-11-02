<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<% 
int id;
Object front=null;
Object appointment=null;
Object helpfullness=null;
Object registration=null;
Object medical=null;
Object waitingTime=null;
Object properExplanation=null;
Object Nursing=null;
Object courtsy=null;
Object overallNursing=null;
Object diagnostics=null;
Object courtesyD=null;
Object timelyReports=null;
Object pharmacy=null;
Object avaMedicine=null;
Object instruction=null;
Object otherF=null;
Object wArea=null;
Object parking=null;
Object security=null;
Object cleaHosp=null;
Object cafeteria=null;
Object overAllServi=null;
Object overAllE=null;
Object refertoOther=null;
id=Integer.parseInt(request.getParameter("id"));

System.out.println("id at view subfield1>>>>>>"+id);
int uid=id;
FeedbackActionControl feedbackActionControl=new FeedbackActionControl();
List<Object> getValue=feedbackActionControl.getValue(uid);
for (Iterator iterator = getValue.iterator(); iterator.hasNext();)
{
	Object object[] = (Object[]) iterator.next();
	front=object[6];
	appointment=object[7];
	helpfullness=object[8];
	registration=object[9];
	medical=object[10];
	waitingTime=object[11];
	properExplanation=object[12];
	Nursing=object[13];
	courtsy=object[14];
	overallNursing=object[15];
	diagnostics=object[16];
	courtesyD=object[17];
	timelyReports=object[18];
	pharmacy=object[19];
	avaMedicine=object[20];
	instruction=object[21];
	otherF=object[22];
	wArea=object[23];
	parking=object[24];
	security=object[25];
	cleaHosp=object[26];
	cafeteria=object[27];
	overAllServi=object[28];
	overAllE=object[29];
	refertoOther=object[30];
	
	
	}




%>



<%@page import="com.Over2Cloud.ctrl.feedback.FeedbackActionControl"%><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />




</head>
<body style="margin-left: 200px; margin-top: 50px;">





<div style="width: 800px">
	<input type="hidden" name="id" value="<%= uid %>">
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Front Office</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= front%><input type="hidden" name="actionName" value="<%= front%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Ease of Contacting & Making appointment</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= appointment%><input type="hidden" name="actionName" value="<%= appointment%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;"> Courtesy of Front office Staff</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= helpfullness%><input type="hidden" name="actionName" value="<%= helpfullness%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Registration & Billing experience</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= registration%><input type="hidden" name="actionName" value="<%= registration%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Medical Care</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= medical%><input type="hidden" name="actionName" value="<%= medical%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Waiting time for the doctor</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= waitingTime%><input type="hidden" name="actionName" value="<%= waitingTime%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Proper Explanation and time given by doctor</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= properExplanation%><input type="hidden" name="actionName" value="<%= properExplanation%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Nursing Care</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= Nursing%><input type="hidden" name="actionName" value="<%= Nursing%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Promptness & courtesy of staff</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= courtsy%><input type="hidden" name="actionName" value="<%= courtsy%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Over All nursing care</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= overallNursing%><input type="hidden" name="actionName" value="<%= overallNursing%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Diagnostics-Cardiology</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= diagnostics%><input type="hidden" name="actionName" value="<%= diagnostics%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Promptness & courtesy of staff</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= courtesyD%><input type="hidden" name="actionName" value="<%= courtesyD%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Timely availability of reports</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= timelyReports%><input type="hidden" name="actionName" value="<%= timelyReports%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Pharmacy</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= pharmacy%><input type="hidden" name="actionName" value="<%= pharmacy%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Availability of prescribed medicine</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= avaMedicine%><input type="hidden" name="actionName" value="<%= avaMedicine%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Instruction given by staff</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= instruction%><input type="hidden" name="actionName" value="<%= instruction%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Other facilities</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= otherF%><input type="hidden" name="actionName" value="<%= otherF%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Waiting Areas</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= wArea%><input type="hidden" name="actionName" value="<%= wArea%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Parking & valet services</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= parking%><input type="hidden" name="actionName" value="<%= parking%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Security</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= security%><input type="hidden" name="actionName" value="<%= security%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Cleanliness of hospital</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= cleaHosp%><input type="hidden" name="actionName" value="<%= cleaHosp%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Cafeteria</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= cafeteria%><input type="hidden" name="actionName" value="<%= cafeteria%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style="width: 300px;">Over All services</div>
			  <div class="user_form_input" style="margin-top: 9px;">
				<label class="lablecell"><%= overAllServi%><input type="hidden" name="actionName" value="<%= overAllServi%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Over All Experience</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= overAllE%><input type="hidden" name="actionName" value="<%= overAllE%>"> </label>
        	</div>
     </div>
	<div class="form_menubox">
			  <div class="user_form_text" style=" width: 300px; margin-top: -11px;margin-left: 33px;">Refer to others</div>
			  <div class="user_form_input" style="margin-top: 1px;">
				<label class="lablecell"><%= refertoOther%><input type="hidden" name="actionName" value="<%= refertoOther%>"> </label>
        	</div>
     </div>
	<br>
   </div>
</body>