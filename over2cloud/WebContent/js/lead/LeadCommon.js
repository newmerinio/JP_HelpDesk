
function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$("#"+dialogId).dialog('open');
	$("#"+dataDiv).html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

$.subscribe('validate1', function(event,data)
		{
			validate(event,data,"mIds");
		});
		$.subscribe('validate2', function(event,data)
		{
			validate(event,data,"pIds");
		});

function validate(event,data, spanClass)
		{	
			var mystring=$("."+spanClass).text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		            }
		            else if(colType =="T"){
		            if(validationType=="n"){
		            var numeric= /^[0-9]+$/;
		            if(!(numeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="ans"){
		             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		             else if(validationType =="w"){
		            
		            
		            
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		            else if(validationType=="mg"){
		             
		             
		             }   
		            }
		            else if(colType=="Time"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             } 
		        }
		    }       
		}


function fillLeadData(divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/fetchAllLead.action",
		success : function(data) {
			data = data.jsonArray;
			if(data != null){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Lead'));
				$(data).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			}
		},
	   error: function() {
            alert("error");
        }
	 });
}

$.subscribe('level1', function(event, data) {
	setTimeout( function() {
		$("#leadgntion").fadeIn();
	}, 10);
	setTimeout( function() {
		$("#leadgntion").fadeOut();
	}, 4000);
	$('select').find('option:first').attr('selected', 'selected');

	//fill lead dropdown
	fillLeadData('leadNameID');
});

$.subscribe('leadContactSuccess', function(event, data) {
	setTimeout( function() {
		$("#pAssociate").fadeIn();
	}, 10);
	setTimeout( function() {
		$("#pAssociate").fadeOut();
	}, 4000);
	$('select').find('option:first').attr('selected', 'selected');	
	fillLeadData('leadNameID');
});

function viewLead()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=1&lostLead=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}

function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
}

function deletediv(divID)
{
  document.getElementById(divID).style.display="none";
}

function fillName(val,divId)
{
	var id = val;
	if(id != null && id != '-1')
	{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/fetchSubIndustry.action",
		data: "id="+id, 
		success : function(data) {  
			data = data.jsonArray;
			if(data != null){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Sub-Industry'));
				$(data).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			}
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	//fetch records in grid
	searchResult('','','','');
}

function fetchReferredName(val,divId,param)
{
	/*
	param is either 1 or 0
	1: for fetching name on both key and value place
	0: for fetching id on key and name on value place
	*/
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	 var refdata=$("#referedBy").val(); 
	
     if(refdata != null && refdata != '-1' )
     {
         document.getElementById("refNameHideShow").style.display="block";
     }else
     {
        document.getElementById("refNameHideShow").style.display="none";

     }
	
	var id = val;
	if(id == null || id == '-1')
	return;


	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/fetchReferredName.action",
		data: "id="+id+"&param="+param, 
		success : function(data) {   
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select'));
			$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		},
		   error: function() {
	            alert("error");
	        }
	 });
}

/************** viewLeadGeneration.jsp ***********************************************/

function refreshRow(rowid, result) {
	  $("#gridedittable").trigger("reloadGrid"); 
	}

function leadHistory(userId){
	id=userId;
	$("#leaddialogid").load("view/Over2Cloud/WFPM/Lead/leadHistoryAction.action?id=" + userId);
	$("#leaddialogid").dialog('open');
	}

 /* * Format a Column as Image */
function formatImage(cellvalue, options, row) {
	cellvalue="Search";
 	var retValue = '&nbsp&nbsp<a href="#"><img title="Full View" src="images/WFPM/fullView.jpg" onClick="fullViewLead('+row.id+',\''+row.leadName+'\');" /></a>'+
	'&nbsp&nbsp<a href="#"><img title="Mail and SMS"  src="images/mail-message-new.png" onClick="sendmailandsms('+row.id+');" /></a>';
 	if($("#selectstatus").val() == '0' || $("#selectstatus").val() == '3')
 	{
 		retValue += '&nbsp&nbsp<a href="#"><img title="Take Action" src="images/tasks.png" onClick="openDialogfortakeaction('+row.id+');" /></a>';
 	}
 	else if($("#selectstatus").val() == '4')
 	{
 		retValue += '&nbsp&nbsp<a href="#"><img title="Take Action" src="images/tasks.png" onClick="openDialog('+row.id+');" /></a>';
 	}
 	return retValue;
}

var idLost;
function openDialog(userId) {
	idLost=userId;
	$("#leadLostDialog").dialog("open");
}
function newLeadOkButton()
{
	$.ajax({
	    type : "post",
		url : 'view/Over2Cloud/WFPM/Lead/updateLostLead.action?id='+idLost
	 });
	NewLeadCancelButton1();
	$("#gridedittable").trigger("reloadGrid"); 	
}
function NewLeadCancelButton1() {
	$('#leadLostDialog').dialog('close');
}
function formatLink(cellvalue, options, rowObject) {
	  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }

	 
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});

function editRow()
{
	if(row == 0)
	{
		alert('Please Select Any Row First !');
		return;
	}
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeLeadEdit.action",
	    data: "id="+row,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function fullViewLead(id,leadNameValue) 
{
	$("#leadFullViewDialog").dialog({title: leadNameValue});
	$("#leadFullViewDialog").html("<center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#leadFullViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/WFPM/Lead/leadFullView.action",
	    data : "id="+id+"&status="+lStatus,
	    success : function(subdeptdata) {
		$("#leadFullViewDialog").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

var id;
var lostLead;

function lostLeadCancelButton1() {
	$('#lostLeadDialog').dialog('close');
};

function lostLeadOkButton()
{

	if(confirm("Are you sure you want to convert this Lead!")){
	$.ajax({
	    type : "post",
		url : 'view/Over2Cloud/WFPM/Lead/updateLostLead.action?idLost='+row
	 });
$('#lostLeadDialog').dialog('close');
}}

 function historyviewMe() {
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load("view/Over2Cloud/WFPM/Lead/leadHistoryAction.action");
}
 function sendmailandsms(id) {
	 var emailidtwo= "services@dreamsol.biz";
	 //Fetch email
	 var emailid;
	 $.ajax({
		 type : "post",
		 url : "view/Over2Cloud/WFPM/Lead/fetchLeadEmail.action",
		 data: "id="+id,
		 success : function(data) {
		 emailid = data.email;
	 },
	 error: function() {
		 alert("error");
	 }
	 });

	 if(emailid == null || emailid == "") emailid = "NA";
	 //var emailid = jQuery("#gridedittable").jqGrid('getCell',id, 'email');
	 var subject= "Information";
	 var leadType = $("#selectstatus").val();
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	 $("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Lead/leadmailandsms.action?status="+leadType+"&id="+id+"&subject="+subject
			 +"&emailidone="+emailid+"&emailidtwo="+emailidtwo+"&mainHeaderName="+tempHeaderName));
	 
 } 

function createLead111(){
	
	$("#form_reg").load("view/Over2Cloud/WFPM/Lead/beforeleadAdd.action");}

function lostLead2()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=0&lostLead=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

}

function createLead()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

}
function searchshowleaddata(param)
{
	changeHeader(param);
	searchResult(param,'','','');
}
//var tempHeaderName;
function changeHeader(param){
	lStatus = param;
	if(param==0)
	{
		tempHeaderName = 'All New Lead';
		$("#Hid").html(tempHeaderName);
	}
	else if(param==1)
	{
		tempHeaderName = 'Prospective Client';
		$("#Hid").html(tempHeaderName);
	}
	else if(param==2)
	{
		tempHeaderName = 'Prospective Associate';
		$("#Hid").html(tempHeaderName);
	}
	else if(param==3)
	{
		tempHeaderName = 'Snooze Lead';
		$("#Hid").html(tempHeaderName);
	}
	else if(param==4)
	{
		tempHeaderName = 'Lost Lead';
		$("#Hid").html(tempHeaderName);
	}
}
function lostLead(){
	$("#form_reg").load("view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=0&lostLead=1");
	}

function openDialogfortakeaction(userId) {
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Lead/returnLeadAction.action?id="+userId+"&status="+lStatus+"&mainHeaderName="+tempHeaderName));
}

function excelUpload()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/excelUpload.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	}

var click=0;
function moreaction(ob, div)
{
	if(click==0)
	{
		$('#'+div).show();
		click=1;
	}
	else
	{
		$('#'+div).hide();
		click=0;
	}
	
} 

function listMASMail(){
	    var s;
	    var myArray = [];
	    var mymobileArray = [];
	    s = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	    for(var i=0; i<s.length; i++){
        myArray.push(jQuery("#gridedittable").jqGrid('getCell',s[i], 'email'));
        mymobileArray.push(jQuery("#gridedittable").jqGrid('getCell',s[i], 'mobileNo'));
       }

	    $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Lead/leadmasmailaction.action?id="+s+"&email="+myArray+"&mobileNo="+mymobileArray,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}



function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
}
function searchData()
{

	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}

function searchResult(param, searchField, searchString, searchOper)
{
	if(param == null || param == '')
	{
		param = $("#selectstatus").val();
	}
	var industry = $("#industryID").val();
	var subIndustry = $("#subIndustryID").val();
	var starRating = $("#starRating").val();
	var sourceName = $("#sourceName").val();
	var location = $("#locationID").val();
	//alert(industry+"  "+subIndustry+"  "+starRating+"  "+sourceName+"  "+location);
	$("#mailandsmsdiv").html("");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/WFPM/Lead/searshowbeforeleadView.action",
	    data : "status="+param+"&mainHeaderName=Lead&industry="+industry+"&subIndustry="+subIndustry+"&starRating="
	    		+starRating+"&sourceName="+sourceName+"&location="+location
	    		+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper,
	    success : function(subdeptdata) {
       $("#"+"datapart").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
   

function fillAlphabeticalLinks()
{
	param = $("#selectstatus").val();
	var val = "";
	val += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'\',\'\',\'\')">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	for(var i=65; i<=90; i++)
	{
		val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'leadName\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}
fillAlphabeticalLinks();

