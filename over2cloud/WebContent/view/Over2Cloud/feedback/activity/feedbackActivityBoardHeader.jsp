<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<STYLE>
.textfieldbgcolr {
	background-color: #E8E8E8;
}
</style>
<SCRIPT type="text/javascript">

var escalationColorPending=[];
var colorClosed=[];
var escColorL2=[];

$.subscribe('colorEscalation',function(event,data)
{
	//console.log(escalationColorPending);
	for(var i=0;i<escalationColorPending.length;i++){
		$("#"+escalationColorPending[i]).css('background','rgb(255, 216, 242)');
	}
	
	for(var i=0;i<colorClosed.length;i++){
		$("#"+colorClosed[i]).css('background','rgb(191, 255, 169)');
	}
	
	for(var i=0;i<escColorL2.length;i++){
		$("#"+escColorL2[i]).css('background','rgb(255, 255, 128)');
	}
	escalationColorPending=[];
	escColorL2=[];
	colorClosed=[];
}); 


 function viewStatus(cellvalue, options, row)
 {
		if(row.stage=='2')
		{	
		 	if(row.fstatus=='Pending')
			{
				escalationColorPending.push(row.id);
			}
			else if(row.fstatus=='Resolved' && row.level=='Level1')
			{
				colorClosed.push(row.id);
			}
			if(row.level=='Level1')
			{
			}
			else 
			{
				escColorL2.push(row.id);
			}	
		 }	
	return '<a href="#" onClick="viewStatusDetails('+row.feedStatId+','+row.id+','+row.ratingFlag+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
 }
 
 function viewStatusDetails(feedStatId,id,activityFlag)
 {
	 var ticketNo  = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
	 var clientId  = jQuery("#gridedittable").jqGrid('getCell',id,'clientId');
	 var patientId  = jQuery("#gridedittable").jqGrid('getCell',id,'patientId');
	 var patientName  = jQuery("#gridedittable").jqGrid('getCell',id,'clientName');
	 patientName=$(patientName).text();
	 var dept  = jQuery("#gridedittable").jqGrid('getCell',id,'dept');
	 var cat  = jQuery("#gridedittable").jqGrid('getCell',id,'cat');
	 var subCat  = jQuery("#gridedittable").jqGrid('getCell',id,'subCat');
	 var brief  = jQuery("#gridedittable").jqGrid('getCell',id,'brief');
	 var date  = jQuery("#gridedittable").jqGrid('getCell',id,'dateTime');
	 var centerCode  = jQuery("#gridedittable").jqGrid('getCell',id,'centerCode');
	 var status  = jQuery("#gridedittable").jqGrid('getCell',id,'fstatus');
     var conP = "<%=request.getContextPath()%>";
 	 $("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
     $.ajax({
         type : "post",
         url : conP+"/view/Over2Cloud/feedback/feedback_Activity/getActivityStatusDetails.action?dataFor=status&feedStatId="+feedStatId+"&clientId="+clientId+"&patientId="+patientId+"&patientName="+patientName+"&dept="+dept+"&cat="+cat+"&subCat="+subCat+"&brief="+brief+"&date="+date+"&centerCode="+centerCode+"&status="+status,
         success : function(subdeptdata) {
 	     $("#feedbackHistoryView1").html(subdeptdata);
     },
        error: function() {
            alert("error");
         }
      }); 
   	 $("#mybuttondialog").dialog({title:'Ticket cycle of '+ticketNo,width:1000,height: 550});
     	
     $("#mybuttondialog").dialog('open');
 }

 		$.subscribe('getActionDialog', function(event,data)
		{
				var feedId    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
				var subcatg  = jQuery("#gridedittable").jqGrid('getCell',feedId,'subCatId');
				var clientId  = jQuery("#gridedittable").jqGrid('getCell',feedId,'clientId');
				var ticketNo  = jQuery("#gridedittable").jqGrid('getCell',feedId,'ticket_no');
				ticketNo=$(ticketNo).text();
				var feedStatId  = jQuery("#gridedittable").jqGrid('getCell',feedId,'feedStatId');
				var activityFlag  = jQuery("#gridedittable").jqGrid('getCell',feedId,'ratingFlag');
				var patientId  = jQuery("#gridedittable").jqGrid('getCell',feedId,'patientId');
				var feedbackDataId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'feedDataId');
				var visitType = jQuery("#gridedittable").jqGrid('getCell', feedId, 'visitType');
				var patientName  = jQuery("#gridedittable").jqGrid('getCell',feedId,'clientName');
				alert(feedbackDataId);
				if(feedId!=null)
				{
					 openDialog10(feedId,subcatg,clientId,ticketNo,feedStatId,activityFlag,patientId,feedbackDataId,patientName,visitType);
				}
				else
					 alert("Please Select Row....!!!!!!");
		});
 
function openDialog10(feedId,subCat,clientId,ticketNo,feedStatId,activityFlag,patientId,feedbackDataId,patientName,visitType)
{
	alert(feedbackDataId);
    var conP = "<%=request.getContextPath()%>";
	$("#feedbackHistoryView2").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/feedback_Activity/feedbackFromActivitiy.action?feedDataId="+feedId+"&subCat="+subCat+"&clientId="+clientId+"&ticket_no="+ticketNo+"&feedStatId="+feedStatId+"&activityFlag="+activityFlag+"&patientId="+patientId+"&feedbackDataId="+feedbackDataId+"&visitType="+visitType,
        success : function(subdeptdata) {
	     $("#feedbackHistoryView2").html(subdeptdata);
    },
       error: function() {
           alert("error");
        }
     }); 
    if(clientId=='' || clientId=='NA')
    {
  	  $("#mybuttondialog1").dialog({title:'Take Action of '+patientName,width: 1000,height: 460});
    }	
    else
    {	
    	$("#mybuttondialog1").dialog({title:'Take Action For MRD No '+clientId,width: 1000,height: 460});
    }
    $("#mybuttondialog1").dialog('open');
}
 
function patView(cellvalue, options, row)
{
	/* $("#"+mygrid).jqGrid('setRowData',rows[i],false, {  color:'white',weightfont:'bold',background:'blue'}); */ 
	if(row.visitType=='Visitor')
	{
		//$("#gridedittable").jqGrid('setRowData', row.id, false, { color: 'red' });
		return '<a href="#" onClick="viewPatientFeedbackDetails('+row.feedStatId+','+row.id+');" ><b><font color="green">'+cellvalue+'</font></b></a>';
	}
	else
	{	
 	   return '<a href="#" onClick="viewPatientFeedbackDetails('+row.feedStatId+','+row.id+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
	}
}
 function viewPatientFeedbackDetails(id,feedId)
 {
	 var patientId  = jQuery("#gridedittable").jqGrid('getCell',feedId,'patientId'); 
	 var clientId  = jQuery("#gridedittable").jqGrid('getCell',id,'clientId');
	 var feedbackDataId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'feedDataId');
	 var patientName  = jQuery("#gridedittable").jqGrid('getCell',feedId,'clientName');
	 
	 var conP = "<%=request.getContextPath()%>";
		$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewPatHistory.action?id="+id+"&clientId="+clientId+"&patientId="+patientId+"&feedbackDataId="+feedbackDataId,
	        success : function(subdeptdata) {
	        	$("#feedbackHistoryView1").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	      if(clientId=='' || clientId=='NA')
	      {
	    	  $("#mybuttondialog").dialog({title:'Feedback Details of '+patientName,width: 1100,height: 200});
	      }	
	      else
	      {
	    	  $("#mybuttondialog").dialog({title:'Feedback Details for UHID '+clientId,width: 1100,height: 200});
	      }	  
  		  $("#mybuttondialog").dialog('open');
 }
 
 function fullView(cellvalue, options, row)
 {
	 if(cellvalue=='Paper' || cellvalue=='SMS')
	 {
		 return '<a href="#" onClick="viewFeedbackModeDetails('+row.id+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
	 }	 
	 else
	 {
		 return ''+cellvalue+'';
	 }	
 }

 function viewFeedbackModeDetails(id)
 {
	 var patientId  = jQuery("#gridedittable").jqGrid('getCell',id,'patientId');
	 var clientId  = jQuery("#gridedittable").jqGrid('getCell',id,'clientId');
	 var patientName  = jQuery("#gridedittable").jqGrid('getCell',id,'clientName');
	 var mode  = jQuery("#gridedittable").jqGrid('getCell',id,'mode').split('"blue">')[1].split("</font>")[0].split(",");
	 var feedbackDataId = jQuery("#gridedittable").jqGrid('getCell', id, 'feedDataId');
	 var conP = "<%=request.getContextPath()%>";
		$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewPatModeHistory.action?id="+id+"&clientId="+clientId+"&patientId="+patientId+"&feedbackDataId="+feedbackDataId+"&dataFor="+mode,
	        success : function(subdeptdata) {
	        	$("#feedbackHistoryView1").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	      if(clientId=='' || clientId=='NA')
	      {
	    	  $("#mybuttondialog").dialog({title:'Feedback Details of '+patientName,width: 1100,height: 590});
	      }	
	      else
	      {
	    	  if(mode=='SMS')
	    	  {
	    		  $("#mybuttondialog").dialog({title:'All Visit Details for UHID '+clientId,width: 1100,height: 500});
	    	  }	  
	    	  else
	    	  {
	    		  $("#mybuttondialog").dialog({title:'Feedback Details for UHID '+clientId,width: 1100,height: 590}); 
	    	  }	  
	      }	  
	    
	    $("#mybuttondialog").dialog('open');
 }
 
 function allotedInfo(cellvalue, options, row)
 {
 	if((row.allot_to)=="NA"){
 		return "NA";
 	}
 	else{
    	return '<a href="#" onClick="viewEmpDetails('+row.feedStatId+','+row.ratingFlag+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
 	}	 
 }
 
 function viewEmpDetails(id,ratingFlag)
 {
	 var conP = "<%=request.getContextPath()%>";
		$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewEmpDetail.action?id="+id+"&activityFlag="+ratingFlag,
	        success : function(subdeptdata) {
	        	$("#feedbackHistoryView1").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	    $("#mybuttondialog").dialog({title:'Allot To Details',width: 400,height: 200});
	    $("#mybuttondialog").dialog('open');
 }
 
 function openByDetail(cellvalue, options, row)
 {
 	if((row.feedRegBy)=="NA"){
 		return "NA";
 	}
 	else{
    		 return '<a href="#" onClick="viewOpenByDetails('+row.feedStatId+','+row.ratingFlag+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
 	}	 
 }
 
 function viewOpenByDetails(id,ratingFlag)
 {
	 var conP = "<%=request.getContextPath()%>";
		$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewOpenByDetail.action?id="+id+"&activityFlag="+ratingFlag,
	        success : function(subdeptdata) {
	        	$("#feedbackHistoryView1").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	    $("#mybuttondialog").dialog({title:'Open By Details',width: 400,height: 200});
	    $("#mybuttondialog").dialog('open');
 }
 
 function viewTAT(cellvalue, options, row)
 {
 	 if((row.level)=="NA"){
 			return "NA";
 	}
 	else{
     	return '<a href="#" onClick="viewOpendTATDetails('+row.feedStatId+','+row.id+','+row.ratingFlag+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
 	}
 }
 
 function viewOpendTATDetails(feedId,id,ratingFlag)
 {
	/*  var level  = jQuery("#gridedittable").jqGrid('getCell',id,'level');
	 var status  = jQuery("#gridedittable").jqGrid('getCell',id,'fstatus'); */
	 var ticketNo  = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
	 ticketNo=$(ticketNo).text();
	 //var openOn  = jQuery("#gridedittable").jqGrid('getCell',id,'dateTime'); 
	 var conP = "<%=request.getContextPath()%>";
		$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	      $.ajax({
	        type : "post",
	        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/viewOpenedTATDetails.action?id="+feedId+"&dataFor=open&ticketNo="+ticketNo+"&activityFlag="+ratingFlag,
	        success : function(subdeptdata) {
	        	$("#feedbackHistoryView1").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });  
	      $("#mybuttondialog").dialog({title: ' Expected TAT & Escalation Matrix',width: 750,height: 350});
	    $("#mybuttondialog").dialog('open');
 }

function viewHistory(cellvalue, options, row)
{
	if(row.history=='0'){
		 return ''+cellvalue+'';
	}else{
		 return '<a href="#" onClick="viewPatientFeedHistory('+row.id+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
	}
} 

 function viewPatientFeedHistory(id)
{
	 var clientId  = jQuery("#gridedittable").jqGrid('getCell',id,'clientId');
	 var mobNo  = jQuery("#gridedittable").jqGrid('getCell',id,'mobNo');
	 var patientName  = jQuery("#gridedittable").jqGrid('getCell',id,'clientName');
	var conP = "<%=request.getContextPath()%>";
	$("#feedbackHistoryView").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url :  conP+"/view/Over2Cloud/feedback/feedback_Activity/beforePatAllTicketDetailsActivity.action?clientId="+clientId+"&mobNo="+mobNo,
        success : function(subdeptdata) {
        	$("#feedbackHistoryView1").html(subdeptdata);
	    },
	       error: function() {
	            alert("error");
	        }
	     });
    if(clientId=='' || clientId=='NA')
    {
    	$("#mybuttondialog").dialog({title:'Feedback History of '+patientName,width: 1100,height: 250});
    }	
    else
    {
    	$("#mybuttondialog").dialog({title:'Feedback History for UHID '+clientId,width: 1100,height: 250});	
    }	
    $("#mybuttondialog").dialog('open');
} 

	$.subscribe('closeDialog',function(event, data) 
	{
		viewSearchdData();
	});
	
function viewSearchdData()
{
		var fromDate=$("#fromDate").val();
		var toDate=$("#toDate").val();
		var status=$("#status1").val();
		var feedBack=$("#feedBack").val();
		var mode=$("#mode").val();
		var ticket_no=$("#ticket_no").val();
		var feedBy=$("#feedBy").val();
		var dept=$("#dept").val();
		var category=$("#cat").val();
		var subCat=$("#subCat").val();
	    var wildsearch=$("#unamewild").val();
	    var mrdNo=$("#mrdNo").val();
	    var patType=$("#patType").val();
	    var dataFor=$("#dataFor").val();
	 /* 	var yearRange=$("#yearRange").val();
	   alert(yearRange); */
	    if(status=='Resolved')
  		{
	    	document.getElementById("takeAction").style.display="none";
	    	document.getElementById("afterResActionDiv").style.display="block";
  		}
	    else
	    {
	    	document.getElementById("takeAction").style.display="block";
	    	document.getElementById("afterResActionDiv").style.display="none";
	    }
  		
	var conP = "<%=request.getContextPath()%>";
	$("#resultDiv1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/feedback/feedback_Activity/viewActivityPage.action?fromDate="+fromDate+"&toDate="+toDate+"&status="+status.split(" ").join("%20")+"&feedBack="+feedBack.split(" ").join("%20")+"&mode="+mode.split(" ").join("%20")+"&ticket_no="+ticket_no.split(" ").join("%20")+"&feedBy="+feedBy.split(" ").join("%20")+"&dept="+dept.split(" ").join("%20")+"&wildsearch="+wildsearch.split(" ").join("%20")+"&catg="+category.split(" ").join("%20")+"&subCat="+subCat.split(" ").join("%20")+"&clientId="+mrdNo.split(" ").join("%20")+"&patType="+patType+"&dataFor="+dataFor,
        async:false,
        success : function(subdeptdata) {
        	if($("#dataFor").val()=='report')
        	{
		    	$.ajax
				({
					type : "post",
					url : conP+"/view/Over2Cloud/feedback/feedback_Activity/viewCountOfActivityPage.action?fromDate="+fromDate+"&toDate="+toDate+"&status="+status.split(" ").join("%20")+"&feedBack="+feedBack.split(" ").join("%20")+"&mode="+mode.split(" ").join("%20")+"&ticket_no="+ticket_no.split(" ").join("%20")+"&feedBy="+feedBy.split(" ").join("%20")+"&dept="+dept.split(" ").join("%20")+"&wildsearch="+wildsearch.split(" ").join("%20")+"&catg="+category.split(" ").join("%20")+"&subCat="+subCat.split(" ").join("%20")+"&clientId="+mrdNo.split(" ").join("%20")+"&patType="+patType,
					success : function(testdata)
					{
						$("#pendingDivId1").val(testdata.Pending);
						$("#snoozeDivId1").val(testdata.Snooze);
						$("#resolveDivId1").val(testdata.Resolved);
						$("#ticketDivId1").val(testdata.TicketOpened);
						$("#reassignDivId1").val(testdata.Reassign);
						$("#notedDivId1").val(testdata.Noted);
						$("#nfaDivId1").val(testdata.NoFurtherAction);
						$("#ingoreDivId1").val(testdata.Ignore);
						$("#totalDivId1").val(testdata.total);
					},
					error : function()
					{
						alert("Error on data fetch");
					} 
				}); 
        	}
        	
      		$("#"+"resultDiv1").html(subdeptdata);
   		 },
       error: function() {
            alert("error");
        }
     });
}

viewSearchdData();

function viewAddPage()
{
	var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/beforeFeedbackAddViaPaper.action",
			success : function(subdeptdata)
			{
				$("#" + "data_part").html(subdeptdata);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	function getPccPage(type)
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/beforePccFeedbackView.action?visitType="+type,
			success : function(subdeptdata)
			{
				$("#" + "data_part").html(subdeptdata);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	function getStatsPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/beforeFeedDetailsSearch.action",
			success : function(subdeptdata)
			{
				$("#" + "data_part").html(subdeptdata);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	$.subscribe('getActionDialogDept', function(event, data)
	{

		var feedId = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');

		if (feedId == null)
		{
			alert("Please Select a Row !!!");
		} else
		{
			/* var status = jQuery("#gridedittable").jqGrid('getCell', feedId, 'fstatus'); */
			var clientId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'clientId');
			var ticketNo = jQuery("#gridedittable").jqGrid('getCell', feedId, 'ticket_no');
			ticketNo=$(ticketNo).text();
			/* var feedStatId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'feedStatId'); */
			var openDate = jQuery("#gridedittable").jqGrid('getCell', feedId, 'dateTime');
			var subCat = jQuery("#gridedittable").jqGrid('getCell', feedId, 'subCat');
			/* var allot_to = jQuery("#gridedittable").jqGrid('getCell', feedId, 'allot_to'); */
			var feedbackBy = jQuery("#gridedittable").jqGrid('getCell', feedId, 'feedRegBy');
			var feedbrief = jQuery("#gridedittable").jqGrid('getCell', feedId, 'brief');
			
			/* var clientName = jQuery("#gridedittable").jqGrid('getCell', feedId, 'clientName'); */
			$("#feedbackHistoryView2").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/feedActionActivity.action?ticketNo=" + ticketNo.split(" ").join("%20") + "&openDate=" + openDate.split(" ").join("%20") + "&feedbackBy=" + feedbackBy.split(" ").join("%20") + "&feedBreif=" + feedbrief.split(" ").join("%20") + "&subCatg=" + subCat.split(" ").join("%20"),
				success : function(subdeptdata)
				{
					$("#" + "feedbackHistoryView2").html(subdeptdata);
				},
				error : function()
				{
					alert("error");
				}
			});
			$("#mybuttondialog1").dialog({
				title : 'Take Action For UHID ' + clientId,
				width : 1000,
				height : 400
			});
			$("#mybuttondialog1").dialog('open');
		}
	});

	function getProductivty(moduleName, dataFor)
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report/getAnalytics.action?dataFor=" + dataFor + "&moduleName=" + moduleName,
			success : function(data)
			{
				$("#" + "data_part").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	// for Excel Download Starts

	function setDownloadType()
	{
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var status = $("#status1").val();
		var feedBack = $("#feedBack").val();
		var mode = $("#mode").val();
		var ticket_no = $("#ticket_no").val();
		var feedBy = $("#feedBy").val();
		var dept = $("#dept").val();
		var category = $("#cat").val();
		var subCat = $("#subCat").val();
		var wildsearch = $("#unamewild").val();
		var mrdNo = $("#mrdNo").val();

		$("#downloadExcelActivity").dialog({
			title : 'Check Column',
			width : 350,
			height : 600
		});
		$("#downloadExcelActivity").dialog('open');
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/feedback_Activity/activityGetFields4Download.action?fromDate=" + fromDate + "&toDate=" + toDate + "&status=" + status.split(" ").join("%20") + "&feedBack=" + feedBack.split(" ").join("%20") + "&mode=" + mode.split(" ").join("%20") + "&ticket_no=" + ticket_no.split(" ").join("%20") + "&feedBy=" + feedBy.split(" ").join("%20") + "&dept=" + dept.split(" ").join("%20") + "&category=" + category.split(" ").join("%20") + "&subCat="
					+ subCat.split(" ").join("%20") + "&mrdNo=" + mrdNo.split(" ").join("%20") + "&wildsearch=" + wildsearch.split(" ").join("%20"),

			success : function(data)
			{
				$("#downloadExcelActivity").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	function setRatingDownload()
	{
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var status = $("#status1").val();
		var feedBack = $("#feedBack").val();
		var mode = $("#mode").val();
		var ticket_no = $("#ticket_no").val();
		var feedBy = $("#feedBy").val();
		var dept = $("#dept").val();
		var category = $("#cat").val();
		var subCat = $("#subCat").val();
		var wildsearch = $("#unamewild").val();
		var mrdNo = $("#mrdNo").val();

		$("#downloadExcelActivity").dialog({
			title : 'Check Column',
			width : 350,
			height : 600
		});
		$("#downloadExcelActivity").dialog('open');
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/feedback/feedback_Activity/activityGetRatingFieldsToDownload.action?fromDate=" + fromDate + "&toDate=" + toDate + "&status=" + status.split(" ").join("%20") + "&feedBack=" + feedBack.split(" ").join("%20") + "&mode=" + mode.split(" ").join("%20") + "&ticket_no=" + ticket_no.split(" ").join("%20") + "&feedBy=" + feedBy.split(" ").join("%20") + "&dept=" + dept.split(" ").join("%20") + "&category=" + category.split(" ").join("%20")
					+ "&subCat=" + subCat.split(" ").join("%20") + "&mrdNo=" + mrdNo.split(" ").join("%20") + "&wildsearch=" + wildsearch.split(" ").join("%20"),

			success : function(data)
			{
				$("#downloadExcelActivity").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	//getStatusFormResolved
	$.subscribe('getStatusFormResolved', function(event, data)
	{
		var feedId = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
		var subcatg = jQuery("#gridedittable").jqGrid('getCell', feedId, 'subCatId');
		var clientId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'clientId');
		var ticketNo = jQuery("#gridedittable").jqGrid('getCell', feedId, 'ticket_no');
		var feedStatId = jQuery("#gridedittable").jqGrid('getCell', feedId, 'feedStatId');
		var activityFlag = jQuery("#gridedittable").jqGrid('getCell', feedId, 'ratingFlag');

		if (feedId == null || ticketNo == "")
		{
			alert("Please Select a Row !!!");
		} else
		{
			$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/feedActionResolved.action?feedStatus=Resolved&feedId=" + feedStatId.split(" ").join("%20") + "&ticketNo=" + ticketNo.split(" ").join("%20") + "&activityFlag=" + activityFlag.split(" ").join("%20"),
				success : function(subdeptdata)
				{
					$("#" + "feedbackHistoryView1").html(subdeptdata);
				},
				error : function()
				{
					alert("error");
				}
			});
			$("#mybuttondialog").dialog({title : 'Update Action For MRD No ' + clientId,width : 1000,height : 400});
			$("#mybuttondialog").dialog('open');
		}
		
	});

	$.subscribe('getResolvedAckPage', function(event, data)
	{
		var feedId = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
		var activityFlag = jQuery("#gridedittable").jqGrid('getCell', feedId, 'ratingFlag');

		if (feedId == null)
		{
			alert("Please Select a Row !!!");
		} else
		{
			$("#feedbackHistoryView1").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/feedback/report/beforePatAck.action?&feedId=" + feedId.split(" ").join("%20") + "&activityFlag=" + activityFlag.split(" ").join("%20"),
				success : function(subdeptdata)
				{
					$("#" + "feedbackHistoryView1").html(subdeptdata);
				},
				error : function()
				{
					alert("error");
				}
			});
			$("#mybuttondialog").dialog({title : 'Update Action For MRD No ',width : 1000,height : 400});
			$("#mybuttondialog").dialog('open');
		}
		
	});
</SCRIPT>
</head>
<body>
<s:hidden name="dataFor" value="%{dataFor}"></s:hidden>
	<sj:dialog id="downloadExcelActivity" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Select Fields" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" modal="true" width="390" height="300" />

	<sj:dialog id="mybuttondialog" autoOpen="false" modal="true"
		width="400" height="200" resizable="false">
		<div id="feedbackHistoryView1"></div>
	</sj:dialog>

	<sj:dialog id="mybuttondialog1" autoOpen="false" modal="true"
		width="400" height="200" resizable="false" onCloseTopics="closeDialog">
		<div id="feedbackHistoryView2"></div>
	</sj:dialog>

	<div class="clear"></div>
	<div class="list-icon">
		<div class="head">Patient Delight</div>
		<div class="head">
			<img alt="" src="images/forward.png"
				style="margin-top: 50%; float: left;">
		</div>
		<s:if test="%{dataFor=='report'}">
		
		<div class="head">Activity Pending:<s:textfield id="pendingDivId1" cssClass="textfieldbgcolr"cssStyle="width: 31px;" disabled="true" />
			Snooze:<s:textfield id="snoozeDivId1" cssClass="textfieldbgcolr"cssStyle="width: 21px;" disabled="true" />
			Resolved:<s:textfield id="resolveDivId1" cssClass="textfieldbgcolr"cssStyle="width: 31px;" disabled="true" />
			Noted:<s:textfield id="notedDivId1" cssClass="textfieldbgcolr"cssStyle="width: 31px;" disabled="true" />
			Re-Assign:<s:textfield id="reassignDivId1" cssClass="textfieldbgcolr"cssStyle="width: 21px;" disabled="true" />
			Ticket Opened:<s:textfield id="ticketDivId1" cssClass="textfieldbgcolr"cssStyle="width: 21px;" disabled="true" />
			N.F.A.:<s:textfield id="nfaDivId1" cssClass="textfieldbgcolr"cssStyle="width: 21px;" disabled="true" />
			Ignore:<s:textfield id="ingoreDivId1" cssClass="textfieldbgcolr"cssStyle="width: 21px;" disabled="true" />
			Total:<s:textfield id="totalDivId1" cssClass="textfieldbgcolr"cssStyle="width: 41px;" disabled="true" />
		</div>
	</s:if>
	<s:else>
		<div class="head">Activity</div> 
	</s:else>
		<div align="right">
			<!-- <a href='#' onclick="getProductivty('FM', 'Employee');"><img src="images/productivity.jpg" width="32px" height="25px"style="margin-top: 6px; margin-right: 3px;" /></a> -->
			<sj:a buttonIcon="ui-icon-person" button="true"onclick="getPccPage('PCS');" title="Add Feedback" cssClass="button"cssStyle="width: 80px;margin-right: 3px;margin-top: 4px;;height:25px">Other</sj:a>
			<sj:a buttonIcon="ui-icon-person" button="true"onclick="getPccPage('PCC');" title="PCC Action Page" cssClass="button"cssStyle="width: 80px;margin-right: 3px;margin-top: 4px;;height:25px">Rounds</sj:a>
			<sj:a buttonIcon="ui-icon-plus" button="true"onclick="viewAddPage();" title="Add New Feedback" cssClass="button"cssStyle="width: 80px;margin-right: 3px;margin-top: 4px;height:25px">Paper</sj:a>
		</div>

	</div>
	<div class="clear"></div>
	<div class="listviewBorder"
		style="margin-top: 10px; width: 98%; margin-left: 20px;height: 98%;"
		align="center">
		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
			<div class="pwie">
				<table class="lvblayerTable secContentbb" border="0" cellpadding="0"
					cellspacing="0" width="100%">
					<tbody>
						<tr></tr>
						<tr>
							<td class="pL10"></td>
						</tr>
						<tr>
							<td class="pL10">
								<table class="floatL" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td class="alignright printTitle">
												<%-- <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px;margin-left: 3px" button="true"  onclick="searchRow()"></sj:a>
									<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="reloadRow()"></sj:a> --%>
												<sj:datepicker cssStyle="height: 16px; width: 60px;"
													cssClass="button" id="fromDate" name="fromDate" size="20"
													maxDate="0" value="%{fromDate}" readonly="true"
													yearRange="2013:2050" showOn="focus"
													displayFormat="dd-mm-yy" Placeholder="From Date" />
												<sj:datepicker
													cssStyle="height: 16px; width: 60px;" cssClass="button"
													id="toDate" name="toDate" onblur="viewSearchdData();"
													onchange="viewSearchdData();" size="20" value="%{toDate}"
													readonly="true" yearRange="2013:2050" showOn="focus"
													displayFormat="dd-mm-yy" Placeholder="To Date" /> 
													<s:select
													id="feedBack" name="feedBack"
													list="{'Positive','Negative'}" headerKey="-1"
													headerValue="Feedback Type" cssClass="button"
													cssStyle="margin-left: 3px;margin-top:-31px;width: 130px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
													 <s:select
													id="mode" name="mode"
													list="{'Paper','SMS','Admin Round','Ward Rounds','By Hand','Email','Verbal'}"
													headerKey="-1" headerValue="Mode" cssClass="button"
													cssStyle="margin-left: 3px;margin-top:-31px;width: 100px;height:27px"
													theme="simple" onchange="viewSearchdData();" /> 
													<s:select
													id="ticket_no" name="ticket_no" list="{'No Data'}"
													headerKey="-1" headerValue="Ticket No" cssClass="button"
													cssStyle="width: 120px;margin-left: 3px;margin-top:-31px;height:27px"
													theme="simple" onchange="viewSearchdData();" /> 
													<s:select
													id="feedBy" name="feedBy" list="{'No Data'}" headerKey="-1"
													headerValue="Patient Name" cssClass="button"
													cssStyle="width: 130px;margin-left: 3px;margin-top:-31px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
													
											</td>
										</tr>
									</tbody>
								</table>
							<td class="alignright printTitle">
								<div id="takeAction" style="display: none">
								<%
									if(userRights.contains("posfeed_VIEW") ){
								%>
									<sj:a button="true" onClickTopics="getActionDialogDept" title="Action" cssClass="button"
										cssStyle="margin-right: 3px;margin-top:-1px;height:25px">Dpt&nbsp;Action</sj:a>
								<%}
									if(userRights.contains("negfeed_VIEW") ){
								%>		
									<sj:a button="true" onClickTopics="getActionDialog" title="Action" cssClass="button"
										cssStyle="margin-right: 3px;margin-top:-1px;height:25px">Action</sj:a>
										
								<%} %>
									 <s:textfield readonly="true" cssStyle="background-color:#FFFFFF;width:20px;height:20px;margin-right:3px;margin-top:-1px;" theme="simple" title="On Coordinator Level"></s:textfield>
									 <s:textfield readonly="true" cssStyle="background-color:#BFFFA9;width:20px;height:20px;margin-right:3px;margin-top:-1px;" theme="simple" title="Resolved without Escalation"></s:textfield>
		    						 <s:textfield readonly="true" cssStyle="background-color:#FFD8F2;width:20px;height:20px;margin-right:3px;margin-top:-1px;" theme="simple" title="Pending on Department Level"></s:textfield>
		    						 <s:textfield readonly="true" cssStyle="background-color:#FFFF96;width:20px;height:20px;margin-right:3px;margin-top:-1px;" theme="simple" title="Escalated to L2 and above"></s:textfield>	
								</div>
								<div id="afterResActionDiv" style="display: none">
									<sj:submit cssStyle="height:27px;" value="Update Action"
										button="true" onClickTopics="getStatusFormResolved"
										cssClass="button" />
									<sj:submit cssStyle="height:27px;" value="Communicate"
										button="true" onClickTopics="getResolvedAckPage"
										cssClass="button" />
								</div> 
									
							</td>
						</tr>
						<tr>
							<td style="padding-top: 5px; padding-left: 5px;">
								<table class="floatL" border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td class="alignright printTitle"><s:select id="status1"
													name="status1"
													list="{'All','Pending','Resolved','Snooze','High Priority','Noted','Ignore','Ticket Opened','No Further Action Required','Acknowledged','Unacknowledged'}"
													headerKey="-1" headerValue="Status" cssClass="button"
													cssStyle="margin-left: 3px;margin-top:-31px;width: 130px;height:27px"
													theme="simple" onchange="viewSearchdData();" /> 
													<s:select
													id="dept" name="dept" list="department" headerKey="-1"
													headerValue="Department" cssClass="button"
													cssStyle="width: 120px;margin-left: 3px;margin-top:-1px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
													 <s:select
													id="cat" name="cat" list="{'No Data'}" headerKey="-1"
													headerValue="Category" cssClass="button"
													cssStyle="width: 120px;margin-left: 3px;margin-top:-1px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
													 <s:select
													id="subCat" name="subCat" list="{'No Data'}" headerKey="-1"
													headerValue="Sub Category" cssClass="button"
													cssStyle="width: 130px;margin-left: 3px;margin-top:-1px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
													
													<s:select
													id="patType" name="patType" list="{'IPD','OPD'}" headerKey="-1"
													headerValue="Patient Type" cssClass="button"
													cssStyle="width: 130px;margin-left: 3px;margin-top:-1px;height:27px"
													theme="simple" onchange="viewSearchdData();" />
										</tr>
									</tbody>
								</table>
							<td class="alignright printTitle">
								<s:textfield
									theme="simple" id="unamewild" placeholder="Patient Name"cssClass="button"
									cssStyle="height:15px;margin-top:-1px;margin-right: 3px;width:80px" onBlur="viewSearchdData();" /> 
								<s:textfield theme="simple"id="mrdNo" placeholder="MRD No" cssClass="button"
									cssStyle="height:15px;margin-top:-1px;margin-right: 3px;width:60px" onBlur="viewSearchdData();" />
									
								<sj:a id="downloadButton" button="true"buttonIcon="ui-icon-arrowstop-1-s" cssClass="button"
									title="Download" cssStyle="height:25px; width:32px" onclick="setDownloadType()"></sj:a>		
								<sj:a id="downloadRatingButton" button="true" buttonIcon="ui-icon-arrowstop-1-s" cssClass="button"
									title="Download Rating" cssStyle="height:25px; width:32px" onclick="setRatingDownload()"></sj:a>		
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div style="overflow: scroll; height: 480px;">
			<div id="resultDiv1"></div>
		</div>
	</div>
</body>
</html>