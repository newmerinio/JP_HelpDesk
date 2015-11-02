<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
<style type="text/css">
	.tabClass 
	{
	     background: #f9f9f9 url(images/setupHeaderBg.jpg) repeat-x left bottom;
	    -moz-border-radius-topleft: 5px;
	    -webkit-border-top-left-radius: 5px;
	    -moz-border-radius-topright: 5px;
	    -webkit-border-top-right-radius: 5px;
	     padding: 3px 2px 0px 15px;
	}
	#calTable  tr td
	{
	   width:100px;
	}
	table, th, td 
	{
      border: 1px solid rgba(132, 132, 132, 0.25);
      border-radius:3px; 
    }
</style>
<script type="text/javascript">
var holidays,activity,eventData,deadline;
getHolidayList();
activityMethod('');
function getHolidayList()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/getHolidayes.action",
	    async:false,
	    success : function(subdeptdata) 
	    {
		//console.log(subdeptdata);
			holidays= subdeptdata;
	    },
	   error: function() {
	            alert("error");
	        }
	 });
} 
function activityMethod(val)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/fetchActivityEvent.action?status="+val,
	    async:false,
	    success : function(subdeptdata) 
	    {
		//	console.log(subdeptdata);
			activity= subdeptdata[0];
			eventData= subdeptdata[1];
			//console.log(event);
	    },
	   error: function() {
	            alert("error");
	        }
	 });
} 
function getDateFormat(date,monthName,year,chk)
{
	//alert("getDateFormat"+date + monthName + year+chk);
	var datestr=date.toString();
	//console.log(holidays);
	if(datestr.length=='1')
	{
		datestr="0"+date;
	}
	var date1;
	if(chk=='Num')
	{
		if(monthName.length=='1')
		{
			monthName="0"+monthName;
		}
		date1=datestr+"-"+monthName+"-"+year;
	}
	else
	{
		
		date1=datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;	
		
	}
	return date1;
}
$.subscribe('backCalender', function(event,data)
{
	calendarView();  
});
function checkOtherActivity(date,monthName,year)
{
	//alert("Activirttyy ::: ");
	var date1=getDateFormat(date,monthName,year,'');
	var act='';
	for (var i =0;i<activity.length;i++)
	{
		if(activity[i].month==date1)
		{
			if(activity[i].status=='Approved')
			{
				if(activity[i].flag!='0' && activity[i].flag!='1')
				{
					if(activity[i].dsr!='No')
					{
						if(activity[i].dsr_status=='Approved')
						{
							act+='<a href="#" onclick="showdsrDisapproved('+date+','+getMonthNumber(monthName)+','+year+','+activity[i].id +');"><img src="images/check.jpg" title="DCR Approve" height="12" width="12" /><img src="images/check.jpg" title="DCR Approve" height="12" width="12" /></a>';
						}
						else if(activity[i].dsr_status=='Disapproved')
						{
							act+='<a href="#" onclick="showdsrDisapproved('+date+','+getMonthNumber(monthName)+','+year+','+activity[i].id +');"><img src="images/red.png" title="DCR Disapprove" height="12" width="12" /><img src="images/red.png" title="DCR Disapprove" height="12" width="12" /></a>';
						}
						else
						{
							act+='<a href="#" onclick="showdsrFill('+date+','+getMonthNumber(monthName)+','+year+','+activity[i].id +');"><img src="images/black.png" title="Fill DCR" height="12" width="12" /><img src="images/black.png" title="Fill DCR" height="12" width="12" /></a>';
						}
					}
					else
					{
						act+='<a href="#" onclick="getdsrFill('+date+','+getMonthNumber(monthName)+','+year+','+activity[i].id +');"><img src="images/black.png" title="Fill DCR" height="12" width="12" /></a>&nbsp;&nbsp;';
					}
				}
			}
			else
			{
				act+='<a href="#"><img title="Disapprove"  src="images/close.gif"  /></a>&nbsp;&nbsp;&nbsp;';
			}
			act+= '&nbsp;&nbsp;<font size="1">'+activity[i].activity_for+': '+activity[i].Activity+'</font>';
			act+='<br>';
		}
	}
//	alert("Activity ::: "+act);
	return act;
}

function checkOtherEvent(date,monthName,year)
{
	//alert("Event ::: ");
		var date1=getDateFormat(date,monthName,year,'');
		var act='';
		for (var i =0;i<eventData.length;i++)
		{
			if(eventData[i].month==date1)
			{
				if(eventData[i].status=='Approve')
				{
					if(eventData[i].flag!='0' && eventData[i].flag!='1')
					{
						if(eventData[i].dsr_status == 'Yes')
						{
							if(eventData[i].dsr_flag == '0' )
							{
								act+='<a href="#" onclick="showdsrFilledEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');"><img src="images/red.png" title="Filled DCR" height="12" width="12" /><img src="images/red.png" title="Filled DCR" height="12" width="12" /></a>';
							}
							else if(eventData[i].dsr_flag == '1')
							{
								act+='<a href="#" ><img src="images/check.jpg" title="Pending DCR Approve" height="12" width="12" /><img src="images/check.jpg" title="DCR Approved" height="12" width="12" /></a>';
							}
						}
						else
						{
							if( eventData[i].flag == '0' )
							{
								act+='<a href="#" ><img src="images/black.png" title="Pending DCR Approve" height="12" width="12" /></a>';
							}
							else
							{
								act+='<a href="#"><img src="images/black.png" title="Waiting Approve Event" height="12" width="12" /></a>&nbsp;&nbsp;';
							}
						}
					}
				}
				else if(eventData[i].status == 'Disapproved')
				{
					act+='<a href="#"><img title="Event Disapproved" src="images/close.gif"  /></a>&nbsp;&nbsp;&nbsp;';
				}
				else if(eventData[i].status=='Approved')
				{
					if(eventData[i].dsr_status == 'Yes' )
					{
						if(eventData[i].dsr_flag == '0' )
						{
							act+='<a href="#" onclick="showdsrFilledEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');" ><img src="images/black.png" title="Pending DCR Approve" height="12" width="12" /><img src="images/black.png" title="Pending DCR Approve" height="12" width="12" /></a>';
						}
						else if(eventData[i].dsr_flag == '1')
						{
							act+='<a href="#" showdsrFilledEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');"  ><img src="images/black.png" title="Waiting DCR Approve" height="12" width="12" /><img src="images/black.png" title="Waiting DCR Approve" height="12" width="12" /></a>';
						}
					} 
					else if(eventData[i].dsr_status=='Approved' )
					{
						act+='<a href="#" onclick="showdsrFilledEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');"  ><img src="images/check.jpg" title="DCR Approved" height="12" width="12" /><img src="images/check.jpg" title="DCR Approved" height="12" width="12" /></a>';			
					}
					else if(eventData[i].dsr_status=='Disapproved' )
					{
						act+='<a href="#" showdsrFilledEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');"  ><img src="images/red.png" title="DCR Dispproved" height="12" width="12" /><img src="images/red.png" title="DCR Dispproved" height="12" width="12" /></a>';			
					}
					else
					{
						act+='<a href="#" onclick="getdsrFillEvent('+date+','+getMonthNumber(monthName)+','+year+','+eventData[i].id +');" ><img src="images/black.png" title="Fill DCR" height="12" width="12" /></a>';
					}
				}
				else
				{
					act+='<a href="#"><img title="Disapproved" src="images/close.gif"  /></a>&nbsp;&nbsp;&nbsp;';
				}
				act+= '&nbsp;&nbsp;<font size="1">'+eventData[i].Activity+': '+eventData[i].Title+'</font>';
				act+='<br>';
			}
		}
		return act;
	}

function totalActivity(date,monthName,year)
{
	var date1=getDateFormat(date,monthName,year,'');
	
	var a='';
	var act=0;
	//console.log("sajgdhsfghf   ::: "+activity.length);
	for (var i =0;i<activity.length;i++)
	{
		if(activity[i].month==date1)
		{
			if(typeof activity[i].flag!='undefined' && activity[i].flag=='0')
			{
				a = 'Planned Activity: ';
			}
			else if(typeof activity[i].flag!='undefined' && activity[i].flag=='1')
			{
				a = 'Pending Activity: ';
			}
			else
			{
				a = 'Approved Activity: ';
			}
			act= act+1;
		}
	}
	a = a+act;
	return a;
}
function totalEvent(date,monthName,year)
{
	var date1=getDateFormat(date,monthName,year,'');
	var act=0;
	var a='';
	for (var i =0;i<eventData.length;i++)
	{
		if(eventData[i].month==date1)
		{
			if(typeof eventData[i].flag!='undefined' && eventData[i].flag=='0')
			{
				a = 'Planned Event: ';
			}
			else if(typeof eventData[i].flag!='undefined' &&  eventData[i].flag=='1' )
			{
				a = 'Pending Event: ';
			}
			else if(typeof eventData[i].flag!='undefined' )
			{
				a = 'Approved Event: ';
			}
			act= act+1;
		}
	}
	a = a+act;
	return a;
}
function checkActivity(date,monthName,year)
{
	var datestr = date.toString();
	if(datestr.length=='1')
	{
		datestr = "0"+date;
	}
	var date1 = datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;
	//console.log(activity+"     :::::::::;activity");
	for(var i=0;i<activity.length;i++)
	{
		var data = activity[i].month;
		if(data == date1)
		{
			return i+1;
			break;
		}
	}
	return 0;
}
function checkEvent(date,monthName,year)
{
	console.log("data::: "+eventData);
	var datestr=date.toString();
	if(datestr.length=='1')
	{
		datestr="0"+date;
	}
	var date1 =datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;
	//console.log(event[0]+"     :::::::::;event.length");
	//console.log(event+"     :::::::::;event");
	for(var i=0;i<eventData.length;i++)
	{
		var data =eventData[i].month;
	   // alert("data::: "+data+"  >>>>>data1 :::: "+date1);
		if(data==date1)
		{
			return i+1;
			break;
		}
	}
	return 0;
}
function checkHoliday(date,monthName,year)
{
	var datestr=date.toString();
	if(datestr.length=='1')
	{
		datestr="0"+date;
	}
	var date1 =datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;
	for(var i=0;i<holidays.length;i++)
	{
		var data =holidays[i].date;
		if(data==date1)
		{
			return i;
			break;
		}
	}
	return 0;
   }
	setCal();

	function getTime() 
	{
		var now = new Date();
		var hour = now.getHours();
		var minute = now.getMinutes();
		now = null;
		var ampm = "" ;
	
		if (hour >= 12) 
		{
			hour -= 12;
			ampm = "PM";
		} 
		else
		ampm = "AM";
		hour = (hour == 0) ? 12 : hour;
	
		if (minute < 10)
		minute = "0" + minute ;
	
		// return time string
		return hour + ":" + minute + " " + ampm;
	}
	function leapYear(year) 
	{
		if (year % 4 == 0) // basic rule
		return true ;// is leap year
		/* else */ // else not needed when statement is "return"
		return false; // is not leap year
	}

	function getDays(month, year) {
	var ar = new Array(12)
	ar[0] = 31 ;// January
	ar[1] = (leapYear(year)) ? 29 : 28; // February
	ar[2] = 31; // March
	ar[3] = 30 ;// April
	ar[4] = 31 ;// May
	ar[5] = 30 ;// June
	ar[6] = 31 ;// July
	ar[7] = 31; // August
	ar[8] = 30 ;// September
	ar[9] = 31; // October
	ar[10] = 30 ;// November
	ar[11] = 31 ;// December

	return ar[month];
	}

	function getMonthName(month) 
	{
		var ar = new Array(12);
		ar[0] = "January";
		ar[1] = "February";
		ar[2] = "March";
		ar[3] = "April";
		ar[4] = "May";
		ar[5] = "June";
		ar[6] = "July";
		ar[7] = "August";
		ar[8] = "September";
		ar[9] = "October";
		ar[10] = "November";
		ar[11] = "December";

		return ar[month];
	}
	function getMonthNumber(month) {
		var ar ='';
		if(month=='January'){
			ar='01';
		}
		else if(month=='February')
		{
			ar='02';	
		}
		else if(month=='March')
		{
			ar='03';	
		}
		else if(month=='April')
		{
			ar='04';	
		}
		else if(month=='May')
		{
			ar='05';	
		}
		else if(month=='June')
		{
			ar='06';	
		}
		else if(month=='July')
		{
			ar='07';	
		}else if(month=='August')
		{
			ar='08';	
		}
		else if(month=='September')
		{
			ar='09';	
		}
		else if(month=='October')
		{
			ar='10';	
		}
		else if(month=='November')
		{
			ar='11';	
		}
		else if(month=='December')
		{
			ar='12';	
		}
		
		return ar;
		}
	
	function setCal() 
	{
		// standard time attributes
		var now = new Date();
		var year = now.getYear();
		if (year < 1000)
		year+=1900;
		var month = now.getMonth();
		var monthName = getMonthName(month);
		var date = now.getDate();
		now = null
	
		// create instance of first day of month, and extract the day on which it occurs
		var firstDayInstance = new Date(year, month, 1);
		var firstDay = firstDayInstance.getDay();
		firstDayInstance = null
	
		// number of days in current month
		var days = getDays(month, year);
	
		// call function to draw calendar
		drawCal(firstDay + 1, days, date, monthName, year,month);
	}
	function setCalfromdropdown()
	{
		//alert(val);
		var val= $("#yearVal").val();
		if(val!='-1')
		{
			var month=$("#monthVal").val();
			//alert(month);
			if(month!='-1')
			{
				//activityMethod(val);
				month=parseInt(month)-1;
				var now = new Date();
				var date = now.getDate();
				var year=val;
				// create instance of first day of month, and extract the day on which it occurs
				var firstDayInstance = new Date(year, month, 1);
				var firstDay = firstDayInstance.getDay();
				firstDayInstance = null;

				// number of days in current month
				var days = getDays(month, year);

				// call function to draw calendar
				drawCal(firstDay + 1, days, date, getMonthName(month), year,month);
				
			}else
			{
				alert("Please Select Month");
			}
		}else
		{
			alert("Please Select Year");
		}
		
	}
	var status = '';
	function changeStatus(val)
	{
		status = val;
		var month=$("#monthVal").val();
		var yr=$("#yearVal").val();
		if(month!='-1' && year!='-1')
		{
			activityMethod(val);
			month=parseInt(month)-1;
			var now = new Date();
			var date = now.getDate();
			
			var year=yr;
			// create instance of first day of month, and extract the day on which it occurs
			var firstDayInstance = new Date(year, month, 1);
			var firstDay = firstDayInstance.getDay();
			firstDayInstance = null

			// number of days in current month
			var days = getDays(month, year);
			drawCal(firstDay + 1, days, '', getMonthName(month), year,month);
		}
		else
		{
			alert("Please Select Month");
		}
	}
	function deadLineDisplay()
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/ActivityPlanner/deadLineDisplay.action",
		    async:false,
		    success : function(subdeptdata) 
		    {
			   // console.log(subdeptdata);
				deadline= subdeptdata;
		    },
		    error: function() 
		    {
		        alert("error");
		    }
		 });
	}
	function deletescript()
	{
		$(".ui-multiselect-menu ").remove();
	}
	function changeButton(val)
	{
		if(val=='AA')
		{
			$("#activity_show").show();
			$("#Event").hide();
			$("#b3").hide();
			$("#b4").hide();
		}
		else if(val=='AE')
		{
			$("#activity_show").hide();
			$("#Event").show();
			$("#b3").hide();
			$("#b4").hide();
		}
		else if(val=='DA')
		{
			$("#activity_show").hide();
			$("#Event").hide();
			$("#b3").show();
			$("#b4").hide();
		}
		else if(val=='DE')
		{
			$("#activity_show").hide();
			$("#Event").hide();
			$("#b3").hide();
			$("#b4").show();
		}
		else
		{
			$("#activity_show").hide();
			$("#Event").hide();
			$("#b3").hide();
			$("#b4").hide();
		}
	}
	function drawCal(firstDay, lastDate, date, monthName, year,months) 
	{
		console.log(event);
		deletescript();
		deadLineDisplay();
		var now = new Date();
		var month = now.getMonth();
		var m=month;
		m=(parseInt(m)+1);
		if(m<10)
		{
			m="0"+m;
		}
		
		var today=getDateFormat(now.getDate(),m, (now.getYear()+1900),'Num');
		$("#calendar").empty();
	    // constant table settings
		var headerHeight = 1 ;// height of the table's header cell
		var border = 2; // 3D height of table's border
		var cellspacing = 2; // width of table's border
		var headerColor = "midnightblue"; // color of table's header
		var headerSize = "+1" ;// size of tables header font
		var colWidth =10; // width of columns in table
		var dayCellHeight = 25; // height of cells containing days of the week
		var dayColor = "darkblue"; // color of font representing week days
		var cellHeight = 40 ;// height of cells representing dates in the calendar
		var todayColor = "red" ;// color specifying today's date in the calendar
		var timeColor = "purple"; // color of font representing current time
		var tableWidth="98%";
		var center="left";
		var monthDay = new Array(12);
		monthDay[0] = "January";
		monthDay[1] = "February";
		monthDay[2] = "March";
		monthDay[3] = "April";
		monthDay[4] = "May";
		monthDay[5] = "June";
		monthDay[6] = "July";
		monthDay[7] = "August";
		monthDay[8] = "September";
		monthDay[9] = "October";
		monthDay[10] = "November";
		monthDay[11] = "December";
		var ab;
		for(var i =0;i<monthDay.length;i++)
		{
			if(monthDay[i]==monthName)
			{
				ab=i+1;
			}
		}
		var options='<option value="'+ab+'">'+monthName+'</option>';
		
		for(var i =0;i<monthDay.length;i++)
		{
			if(monthDay[i]!=monthName)
			{
				options+='<option value="'+(parseInt(i)+1)+'">'+monthDay[i]+'</option>';
			}
		}
		var yearList='<option value="'+year+'">'+year+'</option>';
		for(var i =2015;i<=2050;i++)
		{
			if(i!=year)
			{
				yearList+='<option value="'+i+'">'+i+'</option>';
			}
		}
	//	alert(" >>>>>>.   "+status);
		var optionsstatus='';
		if(status=='-1')
		{
			optionsstatus='<option value="-1">Total View</option>';
		    optionsstatus+='<option value="Approve">Approve</option>';
			optionsstatus+='<option value="Disapprove">Disapprove</option>';
		}
		else if(status=='Approve')
		{
			optionsstatus='<option value="Approve">Approve</option>';
			optionsstatus+='<option value="-1">Total View</option>';
			optionsstatus+='<option value="Disapprove">Disapprove</option>';
		}
		else if(status=='Disapprove')
		{
			optionsstatus='<option value="Disapprove">Disapprove</option>';
			optionsstatus+='<option value="-1">Total View</option>';
		    optionsstatus+='<option value="Approve">Approve</option>';
		}
		else
		{
			 optionsstatus='<option value="-1">Total View</option>';
			 optionsstatus+='<option value="Approve">Approve</option>';
			 optionsstatus+='<option value="Disapprove">Disapprove</option>';
		}

		var buttonL;
		buttonL='<option value="-1">Select Action</option>';
		buttonL+='<option value="AA">Approval Activity</option>';
		buttonL+='<option value="AE">Approval Event</option>';
		buttonL+='<option value="DA">DCR Approval</option>';
		buttonL+='<option value="DE">Event DCR Approval</option>';
		var text = "" ;// initialize accumulative variable to empty string
		text += '<CENTER>';
		text += '<TABLE id="calTable" align="center" width='+tableWidth+' height="580" BORDER=' + border + ' CELLSPACING=' + cellspacing + '>'; // table settings
		text += '<tr HEIGHT="5" style="background-color: rgb(228, 228, 228);"><TD COLSPAN=7 HEIGHT="5" align="center">'; // create table header cell
		text += '<div style="margin-top: -9px;margin-bottom: -5px;float:left;">';
		text +=	'<select id="monthVal" style="background-color: white;" onchange="setCalfromdropdown()" >'+options+'</select>&nbsp;&nbsp;<select id="yearVal" style="background-color: white;"  onchange="setCalfromdropdown()">'+yearList+'</select>&nbsp;&nbsp;<select id="statusList" style="background-color: white;" onchange="changeStatus(this.value);">'+optionsstatus+'</select>&nbsp;&nbsp;<select id="buttonList" style="background-color: white;" onchange="changeButton(this.value);">'+buttonL+'</select></div>'; // close table header's font settings
        text += '<div style="margin-top: -4px;margin-bottom: -19px;margin-right: 225px;"><FONT COLOR="' + headerColor + '" SIZE="3"><b>Self Activity for ' ;// set font for table header
		text += monthName + ' ' + year ;
		text += '</b></FONT></div>';
		text +='<div style="float:right; padding: 0px 17px 0px 0px; "><button id="activity_show" style="width: 114px;display:none;" onclick="approveRequest(this.id);">Approval Activity</button> <button id="Event" style="width: 114px;display:none; " onclick="approveEventRequest(this.id);">Approval Event</button>';  // close header cell
		text +='<button id="b3" style="width: 114px;display:none;" onclick="approvedsrRequest();">DCR Approval</button> <button id="b4" style="width: 114px;display:none; " onclick="approveDCRRequest();">Event DCR Approval</button> ';
		text +='</div>';
		text +='</TD></tr>';
		var openCol = '<TD align='+center+' WIDTH=' + colWidth + ' HEIGHT="1">';
		openCol += '<center><FONT COLOR="' + dayColor + '"><b>';
		var closeCol = '</b></FONT></center></TD>';
	    ab='';
		// create array of abbreviated day names
		var weekDay = new Array(7);
		weekDay[0] = "Sun";
		weekDay[1] = "Mon";
		weekDay[2] = "Tues";
		weekDay[3] = "Wed";
		weekDay[4] = "Thu";
		weekDay[5] = "Fri";
		weekDay[6] = "Sat";
	
		// create first row of table to set column width and specify week day
		text += '<TR ALIGN="left" VALIGN="middle" HEIGHT="1">';
		for (var dayNum = 0; dayNum < 7; ++dayNum) 
		{
			text += openCol + weekDay[dayNum] + closeCol ;
		}
		text += '</TR>';
	
		// declaration and initialization of two variables to help with tables
		var digit = 1;
		var curCell = 1;
		var addbutton='<a class="various" href="#" onclick="getReferralAddPage();"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top:-2px !important"></span></button></a>'
			
		for (var row = 1; row <= Math.ceil((lastDate + firstDay - 1) / 7); ++row) 
		{
			text += '<TR ALIGN="left" VALIGN="top">';
			for (var col = 1; col <= 7; ++col) 
			{
				if (digit > lastDate)
				break;
				if (curCell < firstDay) 
				{
					text += '<TD></TD>';
					curCell++;
				} 
				else 
				{
				//	alert("All dates::: "+getDateFormat(digit,monthName, year,'Str')+"  ::::::::: Today :::: "+today);
					var activityCheck=checkActivity(digit,monthName, year);
					var eventCheck=checkEvent(digit,monthName, year);
					var holidayCheck=checkHoliday(digit,monthName, year);
					if (holidayCheck>0) 
					{ // checking for holiday
						text += '<TD HEIGHT=' + cellHeight + ' width='+colWidth+'  >';
						text += '<FONT COLOR="' + todayColor + '"><b>';
						text += digit;
						text += '</b><a id='+digit+'   class="various" href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a></FONT><BR>';
						text += '<FONT COLOR="' + timeColor + '" SIZE=2>';
						if(activityCheck>0)
						{
							text += '<b>';
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');">'+totalActivity(digit,monthName, year)+'</a> </FONT>';
							text += '</b>';
							text += '<div style="overflow:auto; height:80%; width:100%;float:left"><LEFT>'+ checkOtherActivity(digit,monthName, year); + '</LEFT></div>';
						}
						if(eventCheck>0)
						{
							text += '<b>';
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');"> '+totalEvent(digit,monthName, year)+'</a> </FONT>';
							text += '</b>';
							text += '<div style="overflow:auto; height:80%; width:100%;">' + checkOtherEvent(digit,monthName, year);+ '</div>';
						}
						text += '<li><ul><div style="overflow:auto;height:80%;"><CENTER>' + holidays[holidayCheck].holiday + '</CENTER></div></ul></li>';
						text += '</FONT>';
						text += '</TD>';
					} 
					else if (getDateFormat(digit,monthName, year,'Str') == today)
					{ // current cell represent today's date
						//alert("Inside Today date");
						text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+'  style="background-color: rgb(228, 228, 228);">';
						text += '<FONT COLOR="blue"><b>';
						text += digit;
						text += '</b>';
						text += '<a class="various" id='+digit+' style="float:right;" href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a></FONT>';
						if(activityCheck>0)
						{
							text += '<b>';
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');">'+totalActivity(digit,monthName, year)+'</a> </FONT>';
							text += '</b>';
							text += '<div style="overflow:auto; height:80%; width:100%;float:left"><LEFT>'+ checkOtherActivity(digit,monthName, year); + '</LEFT></div>';
						}
						if(eventCheck>0)
						{
							text += '<b>';
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');"> '+totalEvent(digit,monthName, year)+'</a> </FONT>';
							text += '</b>';
							text += '<div style="overflow:auto; height:80%; width:100%;"><LEFT>' + checkOtherEvent(digit,monthName, year);+ '</LEFT></div>';
						}
						
						text += '</TD>';
					} 
					else if (col== 1) 
					{ // current cell represent other date
						text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+' >';
						text += '<FONT COLOR="' + todayColor + '"><b>';
						text += digit;
						if(eventCheck>0)
						{
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalEventView('+digit+','+months+','+year+');">'+totalEvent(digit,monthName, year)+'</a> </FONT>';
						}
						if(activityCheck>0)
						{
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');">'+totalActivity(digit,monthName, year)+'</a> </FONT>';
						}
						if(activityCheck>0)
						{
							text += '<div style="overflow:auto; height:80%; width:100%;float:left"><LEFT>'+ checkOtherActivity(digit,monthName, year); + '</LEFT></div>';
						}
						if(eventCheck>0)
						{
							text += '<div style="overflow:auto; height:80%; width:100%;"><LEFT>' + checkOtherEvent(digit,monthName, year);+ '</LEFT></div>';
						}
						text += '<a class="various" id='+digit+' style="float:right;" href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a></FONT><BR>'
						text += '</b></FONT>';
						text += '</TD>';
					} 
					else
					{
						if (activityCheck>0) 
						{ // checking for Activity
							text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+'   >';
							text += '<FONT COLOR="' + todayColor + '"><b>';
							text += digit;
							text += '<FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');">'+totalActivity(digit,monthName, year)+'</a> </FONT>';
							text += '</FONT>';
							text += '</b><a class="various" id='+digit+'  href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a>'
							text += '<FONT COLOR="' + timeColor + '" SIZE=2>';
							text += '<div style="overflow:auto; height:80%; width:100%;float:left"><LEFT>'+ checkOtherActivity(digit,monthName, year); + '</LEFT></div>';
							if(eventCheck>0)
							{
								text += '<b><FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalEventView('+digit+','+months+','+year+');">'+totalEvent(digit,monthName, year)+'</a> </FONT><b>';
								text += '<div style="overflow:auto; height:80%; width:100%;"><LEFT>' + checkOtherEvent(digit,monthName, year);+ '</LEFT></div>';
							}
							text += '</FONT>';
							text += '</TD>';
						}
						else if(eventCheck>0)
						{// checking for event
							text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+' >';
							text += '<FONT COLOR="' + todayColor + '"><b>';
							text += digit;
							text += ' <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalEventView('+digit+','+months+','+year+');"> '+totalEvent(digit,monthName, year)+'</a> </FONT>';
							text += '</b><a class="various" id='+digit+'  href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a></FONT><BR>'
							text += '<FONT COLOR="' + timeColor + '" SIZE=2>';
							text += '<div style="overflow:auto; height:80%; width:100%;"><LEFT>' + checkOtherEvent(digit,monthName, year);+ '</LEFT></div>';
							if(activityCheck>0)
							{
							    text += '<b> <FONT COLOR="Blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="totalActivityView('+digit+','+months+','+year+');">'+totalActivity(digit,monthName, year)+'</a> </FONT></b>';
								text += '<div style="overflow:auto; height:80%; width:100%;float:left"><LEFT>'+ checkOtherActivity(digit,monthName, year); + '</LEFT></div>';
							}
							text += '</FONT>';
							text += '</TD>';
						}
						else
						{
							text += '<TD  align='+center+' HEIGHT=' + cellHeight + '  width='+colWidth+'  ><b>' + digit + '</b><a class="various" href="#" onclick="getReferralAddPage('+digit+','+months+','+year+');"><button type="button" class="btn btn-default btn-sm" style="float: right;width: 20px;height: 19px;"><span class="glyphicon glyphicon-plus" style="top: -4px !important;left: -4px;"></span></button></a></TD>';
						} 
					}
					digit++;
				}
			}
			text += '</TR>';
		}
		text += '</TABLE>';
		text += '<div style="margin-left: 26px;float:left;"><b>Note:</b> <img src="images/black.png" title="DSR Fill" height="12" width="12" />: Approve &nbsp;&nbsp;';
		text += '<img src="images/black.png" title="DSR Fill" height="12" width="12" /><img src="images/black.png" title="DSR Fill" height="12" width="12" />: DCR Fill &nbsp;&nbsp;';
		text += '<img title="Disapprove"  src="images/close.gif"  />: Activity&nbsp;Not&nbsp;Approved &nbsp;&nbsp;';
		text += '<img title="DCR Approved"  src="images/check.jpg"  height="12" width="12" /><img title="DCR Approved"  src="images/check.jpg"  height="12" width="12" />: DCR Approved &nbsp;&nbsp;';
		text += '<img title="DCR Disapprove"  src="images/red.png" height="12" width="12"  /><img title="DCR Disapprove"  height="12" width="12" src="images/red.png"  />: DCR Disapproved</div>';
		text += '</CENTER>';

		text += '</br>';
		text += '<div style="margin-left: 10px;float:right;margin-top: -17px;">';
		text += '<font  color="red"><b>Deadline For: </b></font>';
		for(var k=0;k<deadline.length;k++)
		{
			text += ''+deadline[k].configureFor+', '+deadline[k].date;
			text += ', ';
		}	
		text += '</div>';
		$("#calendar").append(text);
	}
	function getReferralAddPage(date,month,year)
	{
		if(month<12)
		{
			month=(parseInt(month)+1);
		}
		if(month<10)
		{
			month="0"+month;
		}
		if(date<10)
		{
			date="0"+date;
		}
		var datev=year+'-'+month+'-'+date;
        $('#abc').dialog('open');
    	$('#abc').dialog({title: 'Schedule Activity',height: '650',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityPlanner.action?date="+datev+"&status=same",
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"activity").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
	}
	function getdsrFill(date,month,year,id)
	{
		if(month<10)
		{
			month="0"+month;
		}
		if(date<10)
		{
			date="0"+date;
		}
		var datev=year+'-'+month+'-'+date;
		$('#abc').dialog('open');
		$('#abc').dialog({title: 'Submit DCR for '+date+'-'+(month)+'-'+year,height: '450',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeDSRFillAdd.action?date="+datev+"&activityId="+id,
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"activity").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
	}
	function totalActivityView(date,month,year)
	{
		if(month<12)
		{
			month=(parseInt(month)+1);
		}
		if(month<10)
		{
			month="0"+month;
		}
		if(date<10)
		{
			date="0"+date;
		}
		var datev=year+'-'+(month)+'-'+date;
		$('#abc').dialog('open');
		$('#abc').dialog({title: 'Approved Activity for '+date+'-'+(month)+'-'+year,height: '450',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeActivityTotalGrid.action?date="+datev,
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"activity").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
	}
	function totalEventView(date,month,year)
	{
		if(month<12)
		{
			month=(parseInt(month)+1);
		}
		if(month<10)
		{
			month="0"+month;
		}
		if(date<10)
		{
			date="0"+date;
		}
		var datev=year+'-'+(month)+'-'+date;
		$('#abc').dialog('open');
		$('#abc').dialog({title: 'Approved Activity for '+date+'-'+(month)+'-'+year,height: '450',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/patientCRMMaster/viewEventPlanDetails.action?date="+datev,
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"activity").html(subdeptdata);
    	   },
    	   error: function() 
    	   {
               alert("error");
          }
    	 });
	}
	function showdsrFill(date,month,year,id)
	{
		
		if(month<10)
		{
			month="0"+month;
		}
		if(date<10)
		{
			date="0"+date;
		}
		var datev=year+'-'+month+'-'+date;
		//alert("Date Is ::: "+datev);
		//alert("Id ::: "+id);
		$('#abc').dialog('open');
		$('#abc').dialog({title: 'View DCR for '+date+'-'+(month)+'-'+year,height: '450',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeDSRFillView.action?date="+datev+"&activityId="+id,
    	    success : function(subdeptdata) 
    	   {
    			$("#"+"activity").html(subdeptdata);
    	  },
    	   error: function() 
    	   {
               alert("error");
          }
    	});
   	}
  	function showdsrDisapproved(date,month,year,id)
 	{
 		if(month<10)
 		{
 			month="0"+month;
 		}
 		if(date<10)
 		{
 			date="0"+date;
 		}
 		var datev=year+'-'+month+'-'+date;
 		$('#abc').dialog('open');
 		$('#abc').dialog({title: 'Manager DCR Status',height: '450',width:'1250'});
        $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     	$.ajax({
     	    type : "post",
     	    url : "view/Over2Cloud/WFPM/ActivityPlanner/beforeDSRDisapprove.action?date="+datev+"&activityId="+id,
     	    success : function(subdeptdata) 
     	   {
     			$("#"+"activity").html(subdeptdata);
     	   },
     	   error: function() 
     	   {
                alert("error");
           }
     	 });
    }
  	function approveRequest(data)
	{
		var month=$("#monthVal").val();
		if(month<10)
		{
			month="0"+parseInt(month);
		}
		var yr=$("#yearVal").val();
		var val= yr+'-'+month;
		$('#abc').dialog('open');
 		$('#abc').dialog({title: 'Approval Result',height: '150',width:'250',position:'center'});
        $("#activity").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/ActivityPlanner/approveRequest.action?date="+val+"&status="+data,
		    async:false,
		    success : function(subdeptdata) 
		    {
			    $("#"+"activity").html(subdeptdata);
		    },
		   error: function() {
		            alert("error");
		        }
		 });
	}
  	function approvedsrRequest()
	{
		var month=$("#monthVal").val();
	//	alert("Month ::: "+month);
		if(month<10)
		{
			month="0"+parseInt(month);
		}
		var yr=$("#yearVal").val();
		var val= yr+'-'+month;
		//alert(">>>>>>  "+val);
		var status ="act";
		//alert("Status :::: "+status);
		$('#abc').dialog('open');
 		$('#abc').dialog({title: 'Approval Result',height: '150',width:'250',position:'center'});
        $("#activity").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/ActivityPlanner/approvedsrRequest.action?date="+val+"&status="+status,
		    async:false,
		    success : function(subdeptdata) 
		    {
			    $("#"+"activity").html(subdeptdata);
		    },
		   error: function() {
		            alert("error");
		        }
		 });
	}


  	 function showdsrFilledEvent(date,month,year,id)
  	  	{
  	  		
  	  		if(month<10)
  	  		{
  	  			month="0"+month;
  	  		}
  	  		if(date<10)
  	  		{
  	  			date="0"+date;
  	  		}
  	  	    var date2=date+'-'+month+'-'+year;
  	  		var datev=year+'-'+month+'-'+date;
  	  		//alert("Date Is ::: "+datev);
  	  		//alert("Id ::: "+id);
  	  		$('#abc').dialog('open');
  	  		$('#abc').dialog({title: 'View DCR for '+date2,height: '450',width:'1250'});
  	  	    $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  	  		$.ajax({
  	  		    type : "post",
  	  		    url : "view/Over2Cloud/patientCRMMaster/filledDCRValues.action",
  	  		    data:"id="+id ,
  	  		    success : function(subdeptdata) 
  	  		   {
  	  				$("#"+"activity").html(subdeptdata);
  	  		  },
  	  		   error: function() 
  	  		   {
  	  	           alert("error");
  	  	      }
  	  		});
  	  	}
  	  	function getdsrFillEvent(date,month,year,id)
  	  	{
  	  		
  	  		if(month<10)
  	  		{
  	  			month="0"+month;
  	  		}
  	  		if(date<10)
  	  		{
  	  			date="0"+date;
  	  		}
  	  	var date2=date+'-'+month+'-'+year;
  	  		var datev=year+'-'+month+'-'+date;
  	  		//alert("Date Is ::: "+datev);
  	  		//alert("Id ::: "+id);
  	  		$('#abc').dialog('open');
  	  		$('#abc').dialog({title: 'Submit DCR for '+date2,height: '450',width:'1250'});
  	  	    $("#activity").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  	  		$.ajax({
  	  		    type : "post",
  	  		    url : "view/Over2Cloud/patientCRMMaster/fillDCRForEvent.action",
  	  		    data:"id="+id ,
  	  		    success : function(subdeptdata) 
  	  		   {
  	  				$("#"+"activity").html(subdeptdata);
  	  		  },
  	  		   error: function() 
  	  		   {
  	  	           alert("error");
  	  	      }
  	  		});
  	  	}
  	function approveEventRequest(id)
  	{
  		var month=$("#monthVal").val();
  		if(month<10)
  		{
  			month="0"+parseInt(month);
  		}
  		var yr=$("#yearVal").val();
  		var val= yr+'-'+month;
  		
  		$('#abc').dialog('open');
  			$('#abc').dialog({title: 'Approval Result',height: '150',width:'250',position:'center'});
  	    $("#activity").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
  		$.ajax({
  		    type : "post",
  		    url : "view/Over2Cloud/patientCRMMaster/approveEventRequest.action?date="+val+"&status="+id,
  		    async:false,
  		    success : function(subdeptdata) 
  		    {
  			    $("#"+"activity").html(subdeptdata);
  		    },
  		   error: function() {
  		            alert("error");
  		        }
  		 });
  	}
  	function approveDCRRequest()
  	{
  		var month=$("#monthVal").val();
  		if(month<10)
  		{
  			month="0"+parseInt(month);
  		}
  		var yr=$("#yearVal").val();
  		var val= yr+'-'+month;
  		
  		$('#abc').dialog('open');
  			$('#abc').dialog({title: 'Approval Result',height: '150',width:'250',position:'center'});
  	    $("#activity").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
  		$.ajax({
  		    type : "post",
  		    url : "view/Over2Cloud/patientCRMMaster/approveDCRRequest.action?date="+val,
  		    async:false,
  		    success : function(subdeptdata) 
  		    {
  			    $("#"+"activity").html(subdeptdata);
  		    },
  		   error: function() {
  		            alert("error");
  		        }
  		 });
  	}
</script>
</head>
<body>
<sj:dialog
          id="abc"
          showEffect="slide" 
          autoOpen="false"
          title="Create Activity"
          modal="true"
          width="1200"
          position="center"
          height="450"
          draggable="true"
    	  resizable="true"
    	  onCloseTopics="backCalender"
          >
        <center>  <div id="activity"></div> </center>
</sj:dialog>
<div id="calendar"></div>
</body>
</html>