function fetchTerritory(val,targetid)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/fetchTerritory.action?city="+val,
	    success : function(subdeptdata)
	    {
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Territory"));
	    	$(subdeptdata).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() {
	            alert("error");
	        }
	 });
	
}
function fetchRelationSubType(val,targetid)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/fetchRelationSubType.action?type="+val,
	    success : function(subdeptdata)
	    {
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Relationship Sub Type"));
	    	$(subdeptdata).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() {
	            alert("error");
	        }
	 });
}
function fetchActivityFor(subType,territory,targetid)
{
	
	var subt=$("#"+subType+' option:selected').text();
	$("#rel").val(subt);
	$("#SubRel").val($("#"+subType).val());
	var terri=$("#"+territory).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/fetchActivityFor.action?subType="+subt+"&territory="+terri,
	    success : function(subdeptdata)
	    {
			$('#'+targetid).html(subdeptdata);
        },
	    error: function() {
	            alert("error");
	        }
	 });
}
function addnewplan()
{
	var ids = $("#gridedittable").jqGrid('getGridParam','selarrrow'); 
	if(ids==0)
	{
		alert("Please select atleast one row.");
	}
	else if(ids.length>1)
	{
		alert("Please select only one row.");
	}	
	else
	{
		var name =  jQuery("#gridedittable").jqGrid('getCell',ids,'schedule_id');
		var status =  jQuery("#gridedittable").jqGrid('getCell',ids,'status');
		var month =  jQuery("#gridedittable").jqGrid('getCell',ids,'for_month');
		var extend =  jQuery("#gridedittable").jqGrid('getCell',ids,'extend_by');
		var date =  jQuery("#gridedittable").jqGrid('getCell',ids,'created_date');
		var time =  jQuery("#gridedittable").jqGrid('getCell',ids,'created_time');
		if(status == 'Pending')
		{
			$('#abc').dialog('open');
		    $('#abc').dialog({title: 'Approve ',height: '450',width:'1000'});
			$("#result").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/ActivityPlanner/activityPlanManagerAction.action?id="+ids+"&name="+name+"&status="+status+"&month="+month+"&extend="+extend+"&date="+date+"&time="+time,
			    success : function(subdeptdata) 
			   {
					$("#"+"result").html(subdeptdata);
			   },
			   error: function() 
			   {
		           alert("error");
		      }
			 });
		}
		else
		{
			alert("Action Already Taken.");
		}
		
	}
}
function fetchActivityTypeEvent(relsub,schedule,targetid,targetid1)
{
	var scheVal=$("#"+schedule).val();
	var rel=$("#"+relsub).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/ActivityPlanner/fetchActivityTypeEvent.action?subType="+rel+"&schedule="+scheVal,
	    success : function(subdeptdata) 
	   {
		if(scheVal=='Field Activity')
		{
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Activity Type"));
	    	$(subdeptdata).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		}
		else
		{
			$('#'+targetid1+' option').remove();
			$('#'+targetid1).append($("<option></option>").attr("value",-1).text("Select Event Type"));
	    	$(subdeptdata).each(function(index)
			{
			   $('#'+targetid1).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		}
		
		
	   },
	   error: function() 
	   {
           alert("error");
      }
	 });
}


var countRow=parseInt('0');
var countRoi=parseInt('0');

function setParameterFields(srcVal,tarId)
{
	var etype=$("#"+tarId+" :selected").val();
	if(etype != 'NA' && etype != '-1'){
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/patientCRMMaster/fetchEventParameters.action",
		data : "eventid="+etype+"&unitType="+srcVal,
		success : function(data)
		{
			//alert(JSON.stringify(data));
			$("#parameterFieldsDiv").html("");
			var mytable = $('<table width="100%"></table>').attr({ id: "basicTable" });
			countRow=data.dataArray.length-2;
			var mybool=true;
			$(data.dataArray).each(function(index)
			{
			    if(data.dataArray.length-2 == index)
			    {
			    	mybool=false;
			    }
				var temp = "";
				var tdValues = this.VALUE.split(",");
				var row = $('<tr></tr>').appendTo(mytable);
				if(mybool)
				{
					temp = '<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9;  background-color: #E6E6E6; border-bottom:1px solid #e7e9e9;text-align:center; width: 4.2%;">'+(index+1)+'</td>';
				}
				$(temp).appendTo(row);
				for (var j = 0; j < (tdValues.length); j++) 
				{
					tdValues[j] = tdValues[j].trim();
					if(j == 0)
					{
						if(mybool)
						{	
							$('<td style="padding:2px;margin:1px;  background-color: #E6E6E6; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 11.5%;"></td>').text(tdValues[j]).appendTo(row);
						}
						else
						{
							$('<td colspan="2" style="padding:2px;margin:1px;  background-color: #E6E6E6; border-right: 1px solid #e7e9e9; border-bottom:1px solid #e7e9e9;text-align:center; width: 11.5%;"></td>').text(tdValues[j]).appendTo(row);
						}
					}
					if(j == 1)
					{
						if(mybool)
						{
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="eparam'+tdValues[j]+'" id="eparam'+ (index+1) + '" onchange="calTotalBudget(this.id,this.value);" /></td>').appendTo(row);
						}
						else if(data.dataArray.length-2 == index+1)
						{
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="'+tdValues[j]+'" id="eparam'+ (index+1) + '" onchange="calTotalBudget(this.id,this.value);" /></td>').appendTo(row);
						}
						else if(data.dataArray.length-1 == index+1)
						{
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" readonly="true" name="'+tdValues[j]+'" id="'+ tdValues[j] + '"/></td>').appendTo(row);
						}						
						else
						{
							$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="'+tdValues[j]+'" id="'+ tdValues[j] + '"/></td>').appendTo(row);
						}
					}	
				}
			});
			mytable.appendTo("#parameterFieldsDiv");
		},
		error   : function(){
			alert("error");
		}
	});
	}else{
			alert("Please Select Activity Type !!");
		}
}


function calTotalBudget(sid,sval){
	var sum=0;
	var totalBudget = parseInt($("#eplan_current_month").val());
	$("#"+sid).val($("#"+sid).val().trim());
	
	var pattern2 = /^\d+$/;
	
	if (!pattern2.test($("#"+sid).val())) {
        errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
        $("#"+sid).css("background-color","#ff701a");  //255;165;79
        $("#"+sid).focus();
        $("#"+sid).val("");
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        }
		if($("#eplan_current_month").val() != "NA"){
    		for(var i=1;i <=countRow;i++){
					if(!(isNaN(parseInt($("#eparam"+i).val())))){
						sum=sum+parseInt($("#eparam"+i).val());	
					}
        		}
    		if(sum > totalBudget){
    			errZone.innerHTML="<div class='user_form_inputError2'>Sum should less than Total(Available) Budget </div>";
    	        $("#eplan_ptotal").css("background-color","#ff701a");  //255;165;79
    	        $("#eplan_ptotal").focus();
    	        $("#eplan_ptotal").val(sum);
    	        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
    	        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        		}else{
        			 $("#eplan_ptotal").val(sum);
            		}
        }
}

function fectchRoiParameters(srcVal,tarId){
	var etype=$("#"+srcVal+" :selected").val();
	if(etype != 'NA' && etype != '-1'){
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/patientCRMMaster/fetchEventROIParameters.action",
		data : "eventid="+etype,
		success : function(data){
		$("#"+tarId).html("");
			//alert(JSON.stringify(data));  
			var mytable = $('<table width="100%"></table>').attr({ id: "basicTable2" });
	   	countRoi=data.dataArray.length-2;
			var mybool=true;
		$(data.dataArray).each(function(index)
			{
			if(data.dataArray.length-2 == index){
				mybool=false;
			}
				var temp = "";
				var tdValues = this.VALUE.split(",");
				var row = $('<tr></tr>').appendTo(mytable);
				if(mybool){
					temp = '<td style="padding:2px;margin:1px;border-right: 1px solid #e7e9e9; background-color: #E6E6E6; border-bottom:1px solid #e7e9e9;text-align:center; width: 4.2%;">'+(index+1)+'</td>';
				}
				
				$(temp).appendTo(row);
				for (var j = 0; j < (tdValues.length); j++) 
				{	
					tdValues[j] = tdValues[j].trim();
					if(j == 0 ){
						if(mybool){	
						$('<td  style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; background-color: #E6E6E6; border-bottom:1px solid #e7e9e9;text-align:center; width: 11.5%;"></td>').text(tdValues[j]).appendTo(row);
						}else{
							
							$('<td colspan="2" style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9; background-color: #E6E6E6; border-bottom:1px solid #e7e9e9;text-align:center; width: 11.5%;"></td>').text(tdValues[j]).appendTo(row);
						}
					}
					
					if(j == 1){
							if(mybool){
									$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="eroi'+tdValues[j]+'" id="roi'+ (index+1) + '" onchange="calCheckSum(this.id,this.value);" /></td>').appendTo(row);
								}
							else if(data.dataArray.length-2 == index+1){
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="'+tdValues[j]+'" id="roi'+ (index+1) + '" onchange="calCheckSum(this.id,this.value);" /></td>').appendTo(row);
							}else if(data.dataArray.length-1 == index+1)
							{
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" readonly="true" name="'+tdValues[j]+'" id="'+ tdValues[j] + '"/></td>').appendTo(row);
							}
							else{
								$('<td style="padding:2px;margin:1px; border-right: 1px solid #e7e9e9;border-bottom:1px solid #e7e9e9;text-align:center; background-color: #E6E6E6; width: 10%;"><input type="text" name="'+tdValues[j]+'" id="'+ tdValues[j] + '"/></td>').appendTo(row);
							}
						}
					
				}
					
			});
			
			mytable.appendTo("#"+tarId);
		},
		error   : function(){
			alert("error");
		}
	});
	}else{
			alert("No Roi available! ");
		}
}

function calCheckSum(sid,val){
	var sum=0;
	$("#"+sid).val($("#"+sid).val().trim());
	var pattern2 = /^\d+$/;
	if (!pattern2.test($("#"+sid).val())) {
        errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Number </div>";
        $("#"+sid).css("background-color","#ff701a");  //255;165;79
        $("#"+sid).focus();
        $("#"+sid).val("");
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        }
    		for(var i=1;i <=countRoi;i++){
					if(!(isNaN(parseInt($("#roi"+i).val())))){
						sum=sum+parseInt($("#roi"+i).val());	
					}
        		}
    				 $("#eplan_rtotal").val(sum);
}


function fetchMappedTeam(id,targetid){
	var val =$("#"+id).val();
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/patientCRMMaster/fetchMappedTeam.action",
		data: "id="+val ,
			success : function(data){
				$("#"+targetid).html(data);
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}