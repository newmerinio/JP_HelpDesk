<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<STYLE type="text/css">

	td {
	padding: 5px;
	padding-left: 10px;
}

td.poor
{
	color: #FF0000;
}
td.average
{
	color: #FFCC00;
}
td.good
{
	color: #66FF66;
}

td.vgood
{
	color: #00CC00;
}

td.excellent
{
	color: #006600;
}
tr.color
{
	background-color: #E6E6E6;
}





</STYLE>
</head>
<body>
<table width="100%" bordercolor="lightgray" border="1">
	<tr class="color">
		<td>
			<strong>Front&nbsp;Office&nbsp;Staff</strong>
		</td>
		<td valign="top" width="100%">
			<table width="80%" border="0">
				<tr>
					<td>Clear&nbsp;answers&nbsp;and&nbsp;instructions:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q1"
							name="q1"
					    >
						</s:select>
					</td>
					<td>Cordial&nbsp;&&nbsp;helpful:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q2"
							name="q2"
					    >
						</s:select>
					</td>
					<td>Waiting&nbsp;time&nbsp;for&nbsp;registration&nbsp;(in&nbsp;minutes)&nbsp;<10,&nbsp;20-30,&nbsp;>30:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q3"
							name="q3"
					    >
						</s:select>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<strong>Billing</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>Promptness&nbsp;&&nbsp;courtesy&nbsp;of&nbsp;staff&nbsp;handling&nbsp;billing&nbsp;queries:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;margin-left: -70px;"
							id="q4"
							name="q4"
					    >
						</s:select>
					</td>
					
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="color">
		<td valign="top">
			<strong>Doctors</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>Timely&nbsp;availability:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA"
							theme="simple"
							cssStyle="width: 71px;"
							id="q5"
							name="q5"
					    >
						</s:select>
					</td>
					<td>Explanation&nbsp;of&nbsp;reports&nbsp;&&nbsp;results&nbsp;in&nbsp;a&nbsp;way&nbsp;you&nbsp;could&nbsp;understand:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q6"
							name="q6"
					    >
						</s:select>
					</td>
					<td>Doctor's&nbsp;approach&nbsp;in&nbsp;explaining&nbsp;your&nbsp;treatment&nbsp;plan:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA"
							theme="simple"
							cssStyle="width: 71px;"
							id="q7"
							name="q7"
					    >
						</s:select>
					</td>
					
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<strong>Diagnostics</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>Explanation&nbsp;of&nbsp;prescribed&nbsp;tests:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q8"
							name="q8"
					    >
						</s:select>
					</td>
					<td>Helpful&nbsp;&&nbsp;courteous&nbsp;staff:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q9"
							name="q9"
					    >
						</s:select>
					</td>
					<td>Information&nbsp;on&nbsp;report&nbsp;delivery&nbsp;time&nbsp;&&nbsp;collection:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q10"
							name="q10"
					    >
						</s:select>
					</td>
					<td>Quality&nbsp;of&nbsp;Phlebotomy:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q11"
							name="q11"
					    >
						</s:select>
					</td>
					<td>Waiting&nbsp;time&nbsp;for&nbsp;Sample&nbsp;Collection&nbsp;(in&nbsp;minutes)&nbsp;<10,&nbsp;10-20,&nbsp;>20:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q12"
							name="q12"
					    >
						</s:select>
					</td>
					<td></td><td></td>
					<td></td><td></td>
					<td></td><td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="color">
		<td valign="top">
			<strong>Diagnostics&nbsp;-&nbsp;Lab&nbsp;Investigations</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>Explanation&nbsp;of&nbsp;prescribed&nbsp;tests:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q13"
							name="q13"
					    >
						</s:select>
					</td>
					<td>Helpful&nbsp;&&nbsp;courteous&nbsp;staff:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q14"
							name="q14"
					    >
						</s:select>
					</td>
					<td>Information on report delivery time & collection:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q15"
							name="q15"
					    >
						</s:select>
					</td>
					<td>Quality of Phlebotomy:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q16"
							name="q16"
					    >
						</s:select>
					</td>
					<td>Waiting time for Sample Collection (in minutes) <10, 10-20, >20:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q17"
							name="q17"
					    >
						</s:select>
					</td>
				</tr>
				<tr>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="color">
		<td valign="top">
			<strong>Support Services</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>How would you rate our pharmacy services:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA"
							theme="simple"
							cssStyle="width: 71px;"
							id="q18"
							name="q18"
					    >
						</s:select>
					</td>
					<td>The support staff's respect and concern for your privacy & comfort:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q19"
							name="q19"
					    >
						</s:select>
					</td>
					<td>Cafeteria services:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA"
							theme="simple"
							cssStyle="width: 71px;"
							id="q20"
							name="q20"
					    >
						</s:select>
					</td>
					
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
		<tr class="color">
		<td valign="top">
			<strong>Facility</strong>
		</td>
		<td valign="top" width="100%">
			<table width="100%" border="0">
				<tr>
					<td>Cleanliness of toilets:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA"
							theme="simple"
							cssStyle="width: 71px;"
							id="q21"
							name="q21"
					    >
						</s:select>
					</td>
					<td>Overall cleanliness of hospital:</td>
					<td>
						<s:select 
							list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
							headerKey="0"
							headerValue="NA" 
							theme="simple"
							cssStyle="width: 71px;"
							id="q22"
							name="q22"
					    >
						</s:select>
					</td>
					
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	 <tr class="color"><td valign="top" width="100%" align="center">
            <strong>Overall Experience</strong>
        </td></tr>
    <tr>
        <td valign="top"  >
            <table  border="0" width="80%">
                <tr>
                    <td align="left">How&nbsp;would&nbsp;you&nbsp;rate&nbsp;overall&nbsp;experience&nbsp;with&nbsp;our&nbsp;hospital:</td>
                    <td align="left" valign="top">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q23"
                            name="q23"
                        >
                        </s:select>
                    </td>
                    <td align="left">Would&nbsp;recommend&nbsp;our&nbsp;hospital&nbsp;to&nbsp;your&nbsp;family&nbsp;and&nbsp;friends:</td>
                    <td align="left" valign="top">
                         <s:radio id="targetOn" theme="simple" name="targetOn" list="{'Yes','No'}" ></s:radio>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    
      <tr>
        <td valign="top" >
            <table  border="0" width="80%">
                <tr>
                    <td align="left" valign="top">
                        How&nbsp;did&nbsp;you&nbsp;come&nbsp;to&nbsp;know&nbsp;about&nbsp;Jaypee&nbsp;Hospital:<font size="+2" color="#FF1111">|</font>
                    </td>
                    <td align="left" valign="top">
                        <s:select 
                            id="choseHospital"
                            name="choseHospital" 
                            list="#{'Friends/Relatives':'Friends/Relatives','Advertisement':'Advertisement','Media/TV/Radio':'Media/TV/Radio','Internet':'Internet','Referred by Doctor':'Referred by Doctor','Others':'Others'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            cssClass="select"
                            onchange="checkForDocName(this.value)"
                        >
                        </s:select>
                    </td>
                     <td valign="top" align="left">
                     	<div id="docDiv" style="display: none;"><s:textfield id="nameEmp" name="nameEmp"  theme="simple" placeholder="Enter Doctor Name" theme="simple" cssClass="textField"/></div>
                    </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </td>
    </tr>
    
    <tr>
        <td valign="top" colspan="2">
        <table  border="0" width="80%">
                <tr class="color">
                    <td valign="top" align="left">
                        Suggestions:
                    </td>
                    <td valign="top" align="left">
                        <s:textarea id="comments" name="comments" cols="50" rows="5" theme="simple"></s:textarea>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>