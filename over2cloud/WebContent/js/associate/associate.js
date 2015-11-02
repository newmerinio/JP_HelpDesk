$.subscribe('level1',function(event,data)
{
	$('select').find('option:first').attr('selected', 'selected');
});
//===========================================================================================================================================================
function fetchLevelData(val,selectId,Offeringlevel)
{
	//alert(val.value);
	//alert(Offeringlevel);
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchOfferingLevelData.action?offeringId="+val.value+"&Offeringlevel="+Offeringlevel,
	    success : function(data) {
		    
		$('#'+selectId+' option').remove();
		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
    	$(data).each(function(index)
		{
    		//alert(this.ID +" "+ this.NAME);
		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
//===================================================================================================================================================================

function fillName(val,divId,param)
{
	//	param is either 1 or 0
	//	1: for fetching name on both key and value place
	//	0: for fetching id on key and name on value place
	
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	var id = val;
	if(id == null || id == '-1')
		return;

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchReferredName.action",
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

//======================================================================================================================================================================

function fillInDdn(selectId, data)
{
	$('#'+selectId+' option').remove();
	$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select'));
   	$(data).each(function(index)
	{
   		//alert(this.ID +" "+ this.NAME);
	   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
}
//====================================================================================================================================================================

function fetchLocation(stateId, selectId)
{
	var tempVariable =  stateId;
	$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/WFPM/Associate/fetchLocation.action",
		    data : "tempVariable="+tempVariable,
		    success : function(data) {
			fillInDdn(selectId, data);			
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
//====================================================================================================================================================================

/* * Format a Column as Image */
function formatImage(cellvalue, options, row) {
	 //var context_path = '<%=request.getContextPath()%>';
	 //cellvalue="Search";
	//return "&nbsp&nbsp<a href='#mailandsmsdiv'><img title='Print Lead' src='"+ context_path +"/images/printer.png' onClick='downloadDOC("+row.id+");' /></a>"+
	return "&nbsp&nbsp<a href='#referPage'><img title='Mail and SMS'  src='images/mail-message-new.png' onClick='sendmailandsms("+row.id+");' /></a>"+
	"&nbsp&nbsp<a href='#referPage'><img title='Full View' src='images/WFPM/fullView.jpg' onClick='openDialogFullHistory("+row.id+");' /></a>"+
	'&nbsp&nbsp<a href="#"><img title="Create Activity" src="images/WFPM/commonDashboard/action.jpg" onClick="createActivity('+row.id+',\''+row.associateName+'\');" /></a>&nbsp';
}

function createActivity(id, associateName)
{
	$("#data_part").html("");
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Associate/beforeContactTakeAction.action?id="+id+"&isExistingAssociate="+isExistingAssociate+"&mainHeaderName="+tempHeaderName+"&associateName="+associateName));
}

function formatLink(cellvalue, options, rowObject) {
	  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }

function sendmailandsms(id) {
    var emailidtwo= "services@dreamsol.biz";
	var emailid = jQuery("#gridBasicDetails3").jqGrid('getCell',id, 'associateEmail');
	var subject= "Information";
	var assoType = $("#selectstatus").val();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load("view/Over2Cloud/WFPM/Lead/leadmailandsms.action?isExistingAssociate="+assoType+"&id="+id+"&subject="+subject+"&emailidone="+emailid+"&emailidtwo="+emailidtwo+"&mainHeaderName=Associate");
	
} 
function openDialogFullHistory(id) 
{
	//alert("isExistingAssociate:"+isExistingAssociate);
	$("#data_part").html("");
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Associate/viewAssociateFullHistory.action?id="+id+"&isExistingAssociate="+isExistingAssociate+"&mainHeaderName="+tempHeaderName));
}
//==============================================================================================================================================================

function contactTakeactionLink(cellvalue, options, row) {
	return "<a href='#'><img title='Create Activity' src='images/WFPM/commonDashboard/action.jpg' onClick='openDialogForContactTakeaction("+row.id+")'/></a>";
}
function openDialogForContactTakeaction(id) 
{
	$("#data_part").html("");
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/Associate/beforeContactTakeAction.action?id="+id+"&isExistingAssociate="+isExistingAssociate+"&mainHeaderName="+tempHeaderName));
}
//==============================================================================================================================================================

function finishOffering(id,th)
{
	if(confirm("Are you sure you want to convert this prospective associate to existing associate for this offering !"))
	{
		associateId = $("#id").val();
		//alert(clientId);
		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/WFPM/Associate/convertAssociateForOffering.action?tempVariable="+associateId,
		    data : "id="+id,
		    success : function(subdeptdata) {
			    th.disabled = true;
			    //alert("success");
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else 
	{
		th.checked = false;
	}
	
}
//==============================================================================================================================================================

function finish(id,th)
{
	if(confirm("Are you sure you want to close this activity !"))
	{
		//alert(id);
		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/WFPM/Associate/finishAssociateActivity.action",
		    data : "id="+id,
		    success : function(subdeptdata) {
			    th.disabled = true;
			    //alert("success");
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else 
	{
		th.checked = false;
	}
}
//==============================================================================================================================================================

function convertToLost(id, obj) 
{
	if(confirm("Are you sure you want to convert this prospective associate to lost for this offering !"))
	{
		associateId = $("#id").val();
			$.ajax({
	         type: "POST",
	         url: "view/Over2Cloud/WFPM/Associate/convertToLost.action?tempVariable="+associateId,
	         data: "id=" + id,
	         success: function(response){
	             // we have the response
	         	$(obj).attr("disabled", true);
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	     });
	}
	else 
	{
		obj.checked = false;
	}
}
//==============================================================================================================================================================

function convertToAssociate(id, obj) 
{
	if(confirm("Are you sure you want to convert this lost associate to prospective associate for this offering !"))
	{
		associateId = $("#id").val();
		//alert(clientId);
			$.ajax({
	         type: "POST",
	         url: "view/Over2Cloud/WFPM/Associate/convertToAssociate.action?tempVariable="+associateId,
	         data: "id=" + id,
	         success: function(response){
	             // we have the response
	         	$(obj).attr("disabled", true);
	         },
	         error: function(e){
	             alert('Error: ' + e);
	         }
	     });
	}
	else 
	{
		obj.checked = false;
	}
}
//==============================================================================================================================================================

function fetchSubType(typeId, divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/masters/fetchAssociateSubType.action",
		data: "id="+typeId, 
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


$.subscribe('level2', function(event,data)
        {
          setTimeout(function(){ $("#orglevel878").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel878").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          
});

function excelUpload()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateExcelUpload.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getCurrentColumn(downloadType,mappedTableName,masterTableName,dialogId,dataDiv,excelName,level)
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/excel/currentColumn.action",
	    data : "downloadType="+downloadType+"&mappedTableName="+mappedTableName+"&masterTableName="+masterTableName+"&excelName="+excelName+"&level="+level,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function refreshRow(rowid, result) {
	  $("#gridBasicDetails3").trigger("reloadGrid"); 
	}
/*function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}*/

function addProspectiveAssociate()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateAdd.action?modifyFlag=0&deleteFlag=0",
	    data : "existAssociate="+ 0,
	    success : function(subdeptdata) {
     $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
          alert("error");
      }
	 });
}
function searchshowassocaitedata(param){
	//alert(param);
	changeHeader(param);
	isExistingAssociate = param;
	searchResult(param,'','','');

}

var tempHeaderName;
function changeHeader(param){
	if(param==0)
	{
		tempHeaderName = 'Prospective Associate';
		$("#Aid").html(tempHeaderName);
	}
	else if(param==1)
	{
		tempHeaderName = 'Existing Associate';
		$("#Aid").html(tempHeaderName);
	}
	else if(param==2)
	{
		tempHeaderName = 'Lost Associate';
		$("#Aid").html(tempHeaderName);
	}
	
}

function fillNameSubIndustry(val,divId)
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
	/*alert("@@@:"+"isExistingAssociate="+param+"&industry="+industry+"&subIndustry="+subIndustry+"&starRating="
			+starRating+"&sourceName="+sourceName+"&location="+location
			+"&searchField="+searchField+"&searchString="+searchString+"&searchOper="+searchOper)*/
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Associate/searchAssociateView.action",
	    data: "isExistingAssociate="+param+"&industry="+industry+"&subIndustry="+subIndustry+"&starRating="
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
		val += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="searchResult(\''+param+'\',\'associateName\',\''
				+String.fromCharCode(i)+'\',\'bw\')">'+String.fromCharCode(i)+'</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	}
	$("#alphabeticalLinks").html(val);
	//alert("val:"+val);
}

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
	param = $("#selectstatus").val();
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Associate/beforeAssociateEdit.action",
	    data: "isExistingAssociate="+param+"&id="+row,
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
	row = $("#gridBasicDetails3").jqGrid('getGridParam','selarrrow');
	$("#gridBasicDetails3").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}
function searchRow()
{
	jQuery("#gridBasicDetails3").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}

function fillStatus()
{
	$("#associateStatus").val($("#status option:selected").text().trim());
}








