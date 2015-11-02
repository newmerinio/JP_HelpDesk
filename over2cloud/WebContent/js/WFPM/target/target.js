
function addTargetPage()
{
	//0 : KPI		
	//1 : OFFERING
	
	var targetType = $("[name='targetType']:checked").val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/wfpm/target/beforeTargetAdd.action",
	    data : "targetType="+targetType,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function fillKpi(empId)
{
	
	$("#kpiListDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	//fill mobile number
	fillMobileNumber(empId);
	
	//for mapped KPI
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/target/fetchEmpMappedKpi.action",
		data : "empId="+empId,
		success : function(data) {
		$("#kpiListDiv").html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function fillMobileNumber(empId)
{
	//emp mobile number
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/target/fetchEmpMobile.action",
		data : "empId="+empId,
		success : function(data) {
		$("#mobile").text(data.empMobile);
	},
	error: function() {
		alert("error");
	}
	});
}

function fillOffering(empId)
{
	
	$("#kpiListDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	//fill mobile number
	fillMobileNumber(empId);
	
	//for mapped KPI
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/target/fetchAllOffering.action",
		data : "empId="+empId,
		success : function(data) {
		$("#kpiListDiv").html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

function back(targetType)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/target/beforeTargetMainView.action",
		data : "targetType="+targetType,
		success : function(subdeptdata) {
		$("#"+"data_part").html(subdeptdata);
	},
	error: function() {
		alert("error");
	}
	});
}

function resetFormKPI(formone)
{
	$('#'+formone).trigger("reset");
	$("#mobile").text("NA");
	$("#kpiListDiv").html("");
}

$.subscribe('level1', function(event,data)
        {
          setTimeout(function(){ $("#KpiResultOuter").fadeIn(); }, 10);
          setTimeout(function(){ $("#KpiResultOuter").fadeOut(); }, 4000);
          resetFormKPI('formone');
});

function viewTargetGrid()
{
	var targetType = $("[name='targetType']:checked").val();
	if(targetType == 0)
	{
		$("#headDynamic").html("KPI");
	}
	else if(targetType == 1)
	{
		$("#headDynamic").html("Offering");
	}
	$("#dynaminDataPart").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 8%;'></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/wfpm/target/beforeTargetView.action",
		data : "targetType="+targetType,
		success : function(data) {
		$("#dynaminDataPart").html(data);
	},
	error: function() {
		alert("error");
	}
	});
}

var grid;
$.subscribe('rowSelect', function(event, data) {
	  grid = event.originalEvent.grid; 
});	

function editRow()
{
	var sel_id = grid.jqGrid('getGridParam', 'selrow'); 
	var id =  grid.jqGrid('getCell', sel_id, 'id'); 
    var applicableFrom =  grid.jqGrid('getCell', sel_id, 'applicableFrom');
    //alert(id+" == "+applicableFrom);
    var currentDate = new Date();
    if(new Date(applicableFrom+'-01').getTime() - currentDate.getTime() <= 0)
    {
    	alert('You can not modify target for \''+applicableFrom+'\' month !');
    }
    else
    {
    	var targetType = $("[name='targetType']:checked").val();
    	if(targetType == 0)
    	{
    		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
        	$.ajax({
        		type : "post",
        		url : "view/Over2Cloud/wfpm/target/beforeKpiTargetEdit.action",
        		data : "id="+id+"&applicableFrom="+applicableFrom,
        		success : function(data) {
        		$("#data_part").html(data);
        	},
        	error: function() {
        		alert("error");
        	}
        	});
    	}
    	else if(targetType == 1)
    	{
    		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
        	$.ajax({
        		type : "post",
        		url : "view/Over2Cloud/wfpm/target/beforeOfferingTargetEdit.action",
        		data : "id="+id+"&applicableFrom="+applicableFrom,
        		success : function(data) {
        		$("#data_part").html(data);
        	},
        	error: function() {
        		alert("error");
        	}
        	});
    	}
    }
}

$.subscribe('validate1', function(event,data)
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
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
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
