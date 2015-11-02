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
<script type="text/javascript">

	function checkForDocName(value)
	{
		if(value=='Referred by Doctor')
		{
			$("#docDiv").show();
		}	
		else
		{
			$("#docDiv").hide();
		}	
	}

</script>
</head>
<body>
<table width="100%" bordercolor="lightgray" border="0">
    <tr class="color"><td width="98%" align="center">
            <strong>Admission</strong>
        </td></tr>
    <tr >
        <td valign="top">
            <table border="0" width="80%">
                <tr>
                <td align="left">Promptness/courtesy&nbsp;of&nbsp;admission&nbsp;staff:</td>
                     <td align="left">
                         <s:select 
                             id="q1"
                             name="q1" 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td align="left">Waiting&nbsp;time&nbsp;for&nbsp;admission&nbsp;(in minutes):</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'>40','2':'30-40','3':'20-30','4':'10-20','5':'<10'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q2"
                            name="q2"
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
    
       <tr class="color"><td width="100%" align="center">
            <strong>Corporate&nbsp;HelpDesk</strong>
        </td></tr>
    <tr >
        <td valign="top">
            <table border="0" width="80%">
                <tr>
                <td align="left">Handling&nbsp;and&nbsp;Explanation&nbsp;of&nbsp;insurance&nbsp;process:</td>
                     <td align="left">
                         <s:select 
                             id="q3"
                             name="q3" 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td align="left">Promptness&nbsp;in&nbsp;handling&nbsp;the&nbsp;insurance/corporate&nbsp;related&nbsp;process:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q4"
                            name="q4"
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
    
    <tr class="color"><td align="center">
            <strong>Doctors</strong>
        </td></tr>
    <tr>
        <td valign="top"  >
            <table border="0" width="80%">
                <tr>
                    <td align="left">Explanation&nbsp;regarding&nbsp;condition&nbsp;&&nbsp;treatment&nbsp;plan:</td>
                    <td align="left">
                        <s:select 
                            id="q5"
                            name="q5"
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td align="left">Attitude&nbsp;and&nbsp;courtesy&nbsp;of&nbsp;the&nbsp;doctors:</td>
                    <td align="left">
                        <s:select 
                            id="q6"
                            name="q6" 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td align="left">Responsiveness&nbsp;in&nbsp;providing&nbsp;medical&nbsp;services:</td>
                    <td align="left">
                        <s:select 
                            id="q7"
                            name="q7"
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr class="color"><td  align="center">
            <strong>Diagnostics</strong>
        </td></tr>
    <tr>
        <td valign="top"  >
            <table  border="0" width="80%">
                <tr>
                    <td align="left">Explanation&nbsp;of&nbsp;prescribed&nbsp;tests:</td>
                    <td align="left">
                        <s:select 
                       		 id="q8"
                       		 name="q8"
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                        >
                        </s:select>
                    </td>
                    <td align="left">Helpful&nbsp;&&nbsp;courteous&nbsp;staff:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q9"
                            name="q9"
                        >
                        </s:select>
                    </td>
                    <td align="left">Information&nbsp;on&nbsp;report&nbsp;delivery&nbsp;time&nbsp;&&nbsp;collection:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q10"
                            name="q10"
                        >
                        </s:select>
                    </td>
                    <td align="left">Quality&nbsp;of&nbsp;Phlebotomy:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q11"
                            name="q11"
                        >
                        </s:select>
                    </td>
                </tr>
                <tr>
                <td align="left">Waiting&nbsp;time&nbsp;for&nbsp;Sample&nbsp;Collection&nbsp;(in&nbsp;minutes):</td>
                     <td align="left">
                         <s:select 
                            list="#{'1':'>40','2':'30-40','3':'20-30','4':'10-20','5':'<10'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q12"
                            name="q12"
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
    <tr class="color"><td  align="center">
            <strong>Diagnostics&nbsp;-&nbsp;Lab&nbsp;Investigations</strong>
        </td></tr>
    <tr>
        <td valign="top" width="100%">
            <table  border="0" width="80%">
                <tr>
                    <td align="left">Explanation&nbsp;of&nbsp;prescribed&nbsp;tests:</td>
                    <td>
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q13"
                            name="q13"
                        >
                        </s:select>
                    </td>
                    <td align="left">Helpful&nbsp;&&nbsp;courteous&nbsp;staff:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q14"
                            name="q14"
                        >
                        </s:select>
                    </td>
                    <td align="left">Information&nbsp;on&nbsp;report&nbsp;delivery&nbsp;time&nbsp;&&nbsp;collection:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q15"
                            name="q15"
                        >
                        </s:select>
                    </td>
                    <td align="left">Quality&nbsp;of&nbsp;Phlebotomy:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q16"
                            name="q16"
                        >
                        </s:select>
                    </td>
                    </tr>
                    <tr>
                    <td align="left">Waiting&nbsp;time&nbsp;for&nbsp;Sample&nbsp;Collection&nbsp;(in&nbsp;minutes):</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'>40','2':'30-40','3':'20-30','4':'10-20','5':'<10'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q17"
                            name="q17"
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
    <tr class="color"><td valign="top" width="100%" align="center">
            <strong>Support&nbsp;Services</strong>
        </td></tr>
    <tr>
        <td valign="top"  >
            <table   border="0" width="80%">
                <tr>
                    <td align="left">Quality,&nbsp;quantity&nbsp;and&nbsp;timeliness&nbsp;of&nbsp;food&nbsp;served:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q18"
                            name="q18"
                        >
                        </s:select>
                    </td>
                    <td align="left">Cleanliness&nbsp;of&nbsp;room/washroom:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q19"
                            name="q19"
                        >
                        </s:select>
                    </td>
                    <td align="left">Facilitation&nbsp;for&nbsp;diagnostics:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q20"
                            name="q20"
                        >
                        </s:select>
                    </td>
                    <td align="left">Maintenance&nbsp;of&nbsp;in&nbsp;room&nbsp;amenities:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q21"
                            name="q21"
                        >
                        </s:select>
                    </td>
                    </tr>
                    <tr>
                    <td align="left">Quality&nbsp;of&nbsp;linen/upholstery/uniform:</td>
                    <td align="left">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q22"
                            name="q22"
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
        <td valign="top" width="100%" align="center">
            <strong>Nursing&nbsp;Care</strong>
        </td>
    </tr>
    <tr>
        <td valign="top"  >
            <table  border="0" width="80%">
                <tr>
                    <td align="left">Behaviour&nbsp;and&nbsp;courtesy&nbsp;of&nbsp;nursing&nbsp;staff:</td>
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
                    <td align="left">Prompt&nbsp;response&nbsp;and&nbsp;coordination&nbsp;by&nbsp;nursing&nbsp;staff:</td>
                    <td align="left" valign="top">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q24"
                            name="q24"
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
            <strong>Discharge&nbsp;&&nbsp;Billing</strong>
        </td></tr>
    <tr>
        <td valign="top"  >
            <table  border="0" width="80%">
                <tr>
                    <td align="left">Promptness&nbsp;of&nbsp;staff&nbsp;in&nbsp;handling&nbsp;billing&nbsp;queries:</td>
                    <td align="left" valign="top">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q25"
                            name="q25"
                        >
                        </s:select>
                    </td>
                    <td align="left">Timeliness&nbsp;of&nbsp;completion&nbsp;of&nbsp;discharge&nbsp;formalities:</td>
                    <td align="left"valign="top">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q26"
                            name="q26"
                        >
                        </s:select>
                    </td>
                    <td align="left">Explanation&nbsp;regarding&nbsp;post&nbsp;discharge&nbsp;&&nbsp;follow&nbsp;up&nbsp;care:</td>
                    <td align="left" valign="top">
                        <s:select 
                            list="#{'1':'Very Poor','2':'Poor','3':'Fair','4':'Good','5':'Excellent'}"
                            headerKey="0"
                            headerValue="NA" 
                            theme="simple"
                            id="q27"
                            name="q27"
                        >
                        </s:select>
                    </td>
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
                            id="q28"
                            name="q28"
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
        <td valign="top" colspan="2">
        <table  border="0" width="80%">
                <tr>
                    <td valign="top" align="left">
                        Would&nbsp;you&nbsp;like&nbsp;to&nbsp;mention&nbsp;any&nbsp;hospital&nbsp;staff&nbsp;for&nbsp;his/her&nbsp;commendable&nbsp;service&nbsp;?:
                    </td>
                    <td valign="top" align="left">
                        <s:textfield id="comments1"   theme="simple" placeholder="Enter Name" theme="simple" cssClass="textField"/>
                    </td>
                    <td valign="top" align="left">
                        <s:textfield id="comments" theme="simple" placeholder="Enter Department" theme="simple" cssClass="textField"/>
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
        <td valign="top" >
            <table  border="0" width="80%">
                <tr>
                    <td align="left" valign="top">
                        How&nbsp;did&nbsp;you&nbsp;come&nbsp;to&nbsp;know&nbsp;about&nbsp;Jaypee&nbsp;Hospital:<font size="+2" color="#FF1111">|</font>
                    </td>
                    <td align="left" valign="top">
                        <s:select 
                            id="choseHospital"
                            name="chose_hospital" 
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