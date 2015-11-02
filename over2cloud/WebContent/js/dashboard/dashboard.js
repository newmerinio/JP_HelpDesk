function showDetailsPie(dataBlockDiv,graphBlockDiv)
{
	
	$("#"+graphBlockDiv).show();
	$("#"+dataBlockDiv).hide();
}

function showDetailsPie1(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"85%","float":"left"});
	$("#"+graphBlockDiv1).show();
	$("#"+graphBlockDiv2).show();
}

function restoreBoard(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"100%"});
	$("#"+graphBlockDiv1).hide();
	$("#"+graphBlockDiv2).hide();
	if(middleDiv=='jqxChart'){
	 countnext=0;
	 countprev=0;
	}else if(middleDiv=='levelChart'){
		countnext1=0;
		 countprev1=0;
	}
}
function showDetailsData(dataBlockDiv,graphBlockDiv)
{
	$("#"+dataBlockDiv).show();
	$("#"+graphBlockDiv).hide();
}

function getCategoryData(Id)
{
	$("#maxmizeCatgDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getCatgDetail.action?id="+Id,
	    success : function(data) 
	    {
			$("#maximizeCatgDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function maximizeDiv(divBlock,height,width,blockHeading)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getDashboardData.action?maximizeDivBlock="+divBlock,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}
//Maximise for 1st dashbaord
function beforeMaximise(divBlock,height,width,blockHeading,dataFor){
	
	if(maxDivId1=='1stTable'){
		maximizeDiv4Analytics('ticket_table',270,900,'Ticket Count Status','T');
	}else if(maxDivId1=='1stStackedBar'){
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		
	}else if(maxDivId1=='1stColumnBar'){
			maximizeDiv4Analytics('ticket_columgraph',height,width,blockHeading,dataFor);
		
	}else if(maxDivId1=='1stLine'){
		maximizeDiv4Analytics('ticket_Linegraph',height,width,blockHeading,dataFor);
	
    }else if(maxDivId1=='1stBubble'){
		maximizeDiv4Analytics('ticket_Bubblegraph',height,width,blockHeading,dataFor);
	
    }else if(maxDivId1=='1stPendingPie'){
		maximizeDiv4Analytics('ticket_PendingPiegraph',400,width,blockHeading,dataFor);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}
//maximize for 2nd dashbaord
function beforeMaximise1(divBlock,height,width,blockHeading,dataFor){
	
	
     if(maxDivId2=='2ndTable'){
		maximizeDiv4Analytics('level_table',height,width,blockHeading,dataFor);
	
    } else if(maxDivId2=='2ndStackedBar'){
		maximizeDiv4Analytics('level_StackedBar',height,width,blockHeading,dataFor);
	
    } else if(maxDivId2=='2ndColumn2Axes'){
		maximizeDiv4Analytics('level_Column2Axes',height,width,blockHeading,dataFor);
	
    }else if(maxDivId2=='LineLevel'){
		maximizeDiv4Analytics('level_LineChart',height,width,blockHeading,dataFor);
	
    }else if(maxDivId2=='BubbleLevel'){
		maximizeDiv4Analytics('level_BubbleChart',height,width,blockHeading,dataFor);
	
    }else if(maxDivId2=='2ndPendingPie'){
		maximizeDiv4Analytics('level_PiePendingChart',400,width,blockHeading,dataFor);
	
    }
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}

//maximize for 3rd dashbaord
function beforeMaximise2(divBlock,height,width,blockHeading,dataFor){
	
	
     if(maxDivId3=='3rdTable'){
		maximizeDiv4Analytics('catg_table',270,900,'Category Analysis','T');
	
    } else if(maxDivId3=='PieCateg'){
    	maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
	
    } else if(maxDivId3=='DoughnutCateg'){
    	maximizeDiv4Analytics('categ_doughnutChart',height,width,blockHeading,dataFor);
	
    }
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor);
		}
	
}
function maximizeDiv4Analytics(divBlock,height,width,blockHeading,dataFor)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/maximizeDiv.action?maximizeDivBlock="+divBlock+"&status_for="+dataFor,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}


function maximizeDiv4Level(divBlock,height,width,blockHeading,dataFor)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getLevelAnalytics.action?maximizeDivBlock="+divBlock+"&status_for="+dataFor,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 });
}

function ShowTicketData(id,status_for)
 {
	if(status_for=='T')
	{$("#ticketDialog").dialog({title: 'Ticket Detail'});}
	else
	{$("#ticketDialog").dialog({title: 'Feedback By Detail'});}
   $.ajax( {
	type :"post",
	url :"view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/ticketInfo.action?ticket_id="+id+"&status_for="+status_for,
	success : function(ticketdata) {
	$("#ticketsInfo").html(ticketdata);
	},
	error : function() {
		alert("error");
	}
});
$("#ticketDialog").dialog('open');
 }


function getData(id,status,flag,dataForId,level)
{
	var dataFor=$("#"+dataForId).val();
	$("#confirmEscalationDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status+"&dashFor="+dataFor+"&d_subdept_id="+id+"&dataFlag="+flag+"&location="+level,
	    success : function(feedbackdata) {
     $("#confirmingEscalation").html(feedbackdata);
	},
	   error: function() {
          alert("error");
      }
	 });
}

var countnext=0;
var countprev=0;
function next() {
	$("jqxChart").html("");
	if(countnext==0){
		showPieStatus('HighPriority','High Priority Ticket Status','For All Departments');
		
		countnext++;
	}else if(countnext==1){
		showPieStatus('Snooze','Snooze Ticket Status','For All Departments');
		countnext++;
	}else if(countnext==2){
		showPieStatus('Resolved','Resolved Ticket Status','For All Departments');
		countnext++;
	}
}

function prev() {
	$("jqxChart").html("");
	if(countnext==1){
		showPieStatus('Pending','Pending Ticket Status','For All Departments');
		countnext--;
	}else if(countnext==2){
		showPieStatus('HighPriority','High Priority Ticket Status','For All Departments');
		
		countnext--;
	}else if(countnext==3){
		showPieStatus('Snooze','Snooze Ticket Status','For All Departments');
		countnext--;
	}
}

var countnext1=0;
var countprev1=0;
function next1() {
	$("levelChart").html("");
	if(countnext1==0){
		showPieLevel('HighPriority','High Priority Ticket Status','For All Levels');
		
		countnext1++;
	}else if(countnext1==1){
		showPieLevel('Snooze','Snooze Ticket Status','For All Levels');
		countnext1++;
	}else if(countnext1==2){
		showPieLevel('Resolved','Resolved Ticket Status','For All Levels');
		countnext1++;
	}
}

function prev1() {
	$("levelChart").html("");
	if(countnext1==1){
		showPieLevel('Pending','Pending Ticket Status','For All Levels');
		countnext1--;
	}else if(countnext1==2){
		showPieLevel('HighPriority','High Priority Ticket Status','For All Levels');
		
		countnext1--;
	}else if(countnext1==3){
		showPieLevel('Snooze','Snooze Ticket Status','For All Levels');
		countnext1--;
	}
}