var alldataList="";

$.subscribe('beforeFirstAccordian', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });

$.subscribe('beforeSecondAccordian', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect2").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect2").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });

$.subscribe('beforeThirdAccordian', function(event,data)
        {
          setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
          setTimeout(function(){ $("#resultTarget").fadeOut();cancelButton11(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });
function resetColor(id)
{    
         var mystring = $(id).text();
        var fieldtype = mystring.split(",");
        for(var i=0; i<fieldtype.length; i++)
        {        
            var fieldsvalues = fieldtype[i].split("#")[0];
            $("#"+fieldsvalues).css("background-color","");
    }
}

$.subscribe('beforeFirstAccordian1', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect11").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect11").fadeOut(); cancelButton();}, 4000);
        });

//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function commonSelectAjaxCall(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	var module=$("#"+conditionVar_Value2).val();
	//alert("module  "+module)
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+module+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }



function getDept(outlet,targetid)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/OutletDetail/Location/getDept.action?outlet="+outlet,
	    success : function(empData) {
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("--Select Department--"));
    	$(empData).each(function(index)
		{
    		 $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getOutlet(outlet,targetid)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/OutletDetail/Location/getOutlet.action?outlet="+outlet,
	    success : function(empData) {
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("--Select Outlet--"));
    	$(empData).each(function(index)
		{
    		 $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getCate(assetType,outlet,feedType,targetid)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/OutletDetail/Location/getCategory.action?outlet="+outlet+"&assetType="+assetType+"&feedType="+feedType,
	    success : function(empData) {
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("--Select Category--"));
    	$(empData).each(function(index)
		{
    		 $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	},
	   error: function() {
            alert("error");
        }
	 });
}

//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function getServiceDept(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	//alert("module  "+conditionVar_Value2);
	$.ajax
	 (
	   {
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/getServiceDept.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	   }
	 );
  }
function AssetCategory(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/AssetCategoryList.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }

function viewRefereceRecords(divId,dept) {
	$.ajax({
		type :"post",
		url:"view/Over2Cloud/KRAKPI/viewReferenceRecords.action?dept="+dept,
		success : function(datapart){
			
			 $("#"+divId).html(datapart);
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
 }


function commonSelectAjaxCall2New(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+'_widget option').remove();
		$('#'+targetid+'_widget').append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid+'_widget').append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }
$.subscribe('commonSelectAjaxCall21', function(event, data) {
	commonSelectAjaxCall2New('feedback_type','id','fbType','dept_subdept',data.value,'moduleName','DREAM_HDM','fbType','ASC','fbType','Select Feedback Type'); 
});








function commonSelectAjaxCallNew(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	var module=$("#"+conditionVar_Value2).val();
	//alert("module  "+module)
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+module+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+'_widget option').remove();
		$('#'+targetid+'_widget').append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid+'_widget').append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }
$.subscribe('commonSelectAjaxCall1', function(event, data) {
	commonSelectAjaxCallNew('feedback_category','id','categoryName','fbType',data.value,'','','categoryName','ASC','categoryName','Select Category'); 
});
$.subscribe('commonSelectAjaxCall2', function(event, data) {
	commonSelectAjaxCallNew('feedback_subcategory','id','subCategoryName','categoryName',this.value,'','','subCategoryName','ASC','subCategoryName','Select Sub Category'); 
});





//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function commonSelectAjaxCall2(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

function getDrList(val,targetid){
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/patientActivity/getDoctorList.action?offeringId="+val,
		    success : function(data) 
		    {
		    	//allDataList=data;
		  		$('#'+targetid+' option').remove();
				$('#'+targetid).append($("<option></option>").attr("value",-1).text("--Select Doctor Name--"));
		    	$(data).each(function(index)
				{
				   $('#'+targetid).append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
		    },
		   error: function() {
		        alert("error");
		    }
		 });
	// setOwnerAndBuddy();
	}
	 
	 function backPatientBasic()
	 {
		 	var url=null;
		 	if($("#laststatus").val()=='1')
		 	{
		 		url="view/Over2Cloud/WFPM/Patient/viewVisitheader.action";
		 	}	
		 	else
		 	{
		 		url="view/Over2Cloud/WFPM/Patient/beforeviewPatient.action";
		 	}	
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
			$.ajax({
			    type : "post",
			    url : url,
			    
			    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
			            alert("error");
			        }
			 });
				
	 }

