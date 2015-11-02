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
	#calTable  td
	{
	   width:100px;
	   
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
			activity= subdeptdata[0];
			eventData= subdeptdata[1];
	    },
	   error: function() {
	            alert("error");
	        }
	 });
} 
function getDateFormat(date,monthName,year,chk)
{
	var datestr=date.toString();
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
	$("#calenderDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/referral/calendarMini.jsp",
	    success : function(subdeptdata) {
       $("#"+"calenderDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});
function checkActivity(date,monthName,year)
{
	var datestr = date.toString();
	if(datestr.length=='1')
	{
		datestr = "0"+date;
	}
	var date1 = datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;
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
	var datestr=date.toString();
	if(datestr.length=='1')
	{
		datestr="0"+date;
	}
	var date1 =datestr+"-"+getMonthNumber(monthName).trim()+"-"+year;
	for(var i=0;i<eventData.length;i++)
	{
		var data =eventData[i].month;
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
	function deletescript()
	{
		$(".ui-multiselect-menu ").remove();
	}
	function drawCal(firstDay, lastDate, date, monthName, year,months) 
	{
		console.log(event);
		deletescript();
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
		var tableWidth="50%";
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
		var text = "" ;// initialize accumulative variable to empty string
		text += '<CENTER>';
		text += '<TABLE id="calTable" align="center" width='+tableWidth+' height="300" BORDER=' + border + ' CELLSPACING=' + cellspacing + '>'; // table settings
		text += '<tr HEIGHT="32px" style="background-color: rgb(228, 228, 228);"><TD COLSPAN=7 HEIGHT="7" align="center">'; // create table header cell
		text += '<div style="margin-top: -9px;margin-bottom: -5px;float:left;">';
		text +=	'<select id="monthVal" style="background-color: white;" onchange="setCalfromdropdown()" >'+options+'</select>&nbsp;&nbsp;<select id="yearVal" style="background-color: white;"  onchange="setCalfromdropdown()">'+yearList+'</select></div>'; // close table header's font settings
        text += '<div style="margin-top: -4px;margin-bottom: -31px;margin-right: 310px;"><FONT COLOR="' + headerColor + '" SIZE="2"><b>' ;// set font for table header
		//text += monthName + ' ' + year ;
		text += '</b></FONT></div>';
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
					var activityCheck=checkActivity(digit,monthName, year);
					var eventCheck=checkEvent(digit,monthName, year);
					var holidayCheck=checkHoliday(digit,monthName, year);
					if (holidayCheck>0) 
					{ // checking for holiday
						text += '<TD HEIGHT=' + cellHeight + ' width='+colWidth+'  >';
						text += '<FONT COLOR="grey"><b>';
						text += digit;
						text += '</b><BR>';
						text += '<FONT COLOR="' + timeColor + '" SIZE=2>';
						//text += '<div style="overflow:auto;height:80%;"><CENTER>' + holidays[holidayCheck].holiday1 + '</CENTER></div>';
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
						text += '</TD>';
					} 
					else if (col== 1) 
					{ // current cell represent other date
						text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+' >';
						text += '<b>';
						text += digit;
						text += '</b>';
						text += '</TD>';
					} 
					else
					{
						if (activityCheck>0) 
						{ // checking for Activity
							text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+'   >';
							text += '<FONT COLOR="' + todayColor + '"><a href="#" onclick="getReferralAddPage('+digit+','+months+','+year+')"><b>';
							text += digit;
							text += '</b></a></FONT>';
							text += '</TD>';
						}
						else if(eventCheck>0)
						{// checking for event
							text += '<TD HEIGHT=' + cellHeight + '  width='+colWidth+' >';
							text += '<FONT COLOR="' + todayColor + '"><b>';
							text += digit;
							text += '</b></FONT>';
							text += '</TD>';
						}
						else
						{
							text += '<TD  align='+center+' HEIGHT=' + cellHeight + '  width='+colWidth+'  ><b>' + digit + '</b></TD>';
						} 
					}
					digit++;
				}
			}
			text += '</TR>';
		}
		text += '</TABLE>';
	    text += '</CENTER>';
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
<div id="calendarbreak"></div>
</body>
</html>