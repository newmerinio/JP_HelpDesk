var feedTypeFlag;
var feedCatgFlag;
var feedSCatgFlag;
var empFlag;

function createAsset() {
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeAssetAdd.action?empModuleFalgForDeptSubDept=1&assetAdd=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });	
}

function createProcurement(){
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createProcurementAddPage.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function createSupport() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?supportAdd=1",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function createAllotment() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotmentAddPage.action?allotmentAdd=1",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function createReAllot() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createFreeReallotAddPage.action?freeReallotAdd=1",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function createRepair() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createRepairAddPage.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function createRepaired() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createRepairedAddPage.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function createBarcode() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/barcode.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
function importAsset() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetExcelUpload.jsp");	
}

function modifyAsset()
{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetViewPage.action?modifyFlag=1&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	
}
function lifeCycle() {
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
		type : "post",
		url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetLifeGraph.action",
		success : function(data){
			$("#data_part").html(data);
		},
		error : function(){
			alert("error");
		}
	});
}
function importProcurement() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/procurementExcelUpload.jsp");	
}
function importSupport() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/supportExcelUpload.jsp");	
}
function importAllotment() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#data_part").load("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/allotExcelUpload.jsp");	
}
// Form CAncel button to view page*****
function viewPage() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createModuleViewPage.action?modifyFlag=0&deleteFlag=0",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
	
	
}



//get Asset detail by asset id
function getAssetDetail(assetId,onlyAsset,assetWithSuprt,assetWithAllot,barcode)
{
	if(onlyAsset=='1' && barcode=='0')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDetailByAjax.action?assetId="+assetId,
		    success : function(data) {
			$("#assetname").val(data.assetname);
			$("#assetbrand").val(data.assetbrand);
			$("#assetmodel").val(data.assetmodel);
			$("#assetserialno").val(data.assetserialno);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(assetWithSuprt=='1')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDetailByAjax.action?assetId="+assetId+"&assetWithSuprt="+assetWithSuprt,
		    success : function(data) {
			$("#serialno").val(data.assetserialno);
			$("#assetbrand").val(data.assetbrand);
			$("#assetmodel").val(data.assetmodel);
			$("#supportfrom").val(data.supportfrom);
			$("#supportto").val(data.supportto);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(assetWithAllot=='1')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDetailByAjax.action?assetId="+assetId+"&assetWithAllot="+assetWithAllot,
		    success : function(data) {
			$("#serialno").val(data.assetserialno);
			$("#assetbrand").val(data.assetbrand);
			$("#assetmodel").val(data.assetmodel);
			$("#empid").val(data.empid);
			$("#mobile").val(data.mobile);
			$("#dept").val(data.dept);
			$("#subdept").val(data.subdept);
			
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(onlyAsset=='1' && barcode=='1')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDetailByAjax.action?assetId="+assetId+"&barcode="+barcode,
		    success : function(data) {
			$("#serialno").val(data.assetserialno);
			$("#assetbrand").val(data.assetbrand);
			$("#assetmodel").val(data.assetmodel);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	
}

//get total amount
function getTotalAmount(UnitCost,Quantity,Taxes)
{
	 var unitcost = document.getElementById(UnitCost).value;
	 var quantity = document.getElementById(Quantity).value;
	 var taxes = document.getElementById(Taxes).value;
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/AmountCalculateAjax.action?unitCost="+unitcost+"&quantity="+quantity+"&taxes="+taxes,
		    success : function(data)
		    {
				if(data>0)
				{
					$("#totalamount").val(data);
				}
				else
				{
					$("#totalamount").val("0");
				}
		    },
		    error: function()
		    {
		        alert("error");
		    }
		 });
}

//get vendor mobileno

function getVendorMobNo(vendorId)
{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/vendorDetailByAjax.action?vendorId="+vendorId,
		    success : function(data) {
			$("#mobileno").val(data.contactno);
		},
		   error: function() {
		        alert("error");
		    }
		 });
}

function hideBlock(divId)
{
	document.getElementById(divId).style.display='none';
}

function showBlock(divId)
{
	$("#allotDivId").html("<br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	document.getElementById(divId).style.display='block';
}

function getAllotPage(pageId)
{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/reallotmentPage.action?reallotPage=1",
		    success : function(data) {
			$("#allotDivId").html(data);
		},
		   error: function() {
		        alert("error");
		    }
		 });
}

function getSpareName(catgId,divId)
{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/spareName.action?catgId="+catgId,
		    success : function(data) {
			$("#"+divId).html(data);
		},
		   error: function() {
		        alert("error");
		    }
		 });
}
function getNonConsumeSpare(catgId, divId)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/nonConsumeSpareName.action?catgId="+catgId,
	    success : function(spareName) {
        $("#"+divId).html(spareName);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getAvailableSpareViaAjax(spareCount)
{
	var spareName=null;
	spareName=$('#spareNameDiv option:selected').val();
	if(spareName==null)
	{
		spareName=$('#spare_name option:selected').val();
	}
	document.getElementById("indicator2").style.display="block";
    $.getJSON("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/spareAvailabilty.action?spareCount="+spareCount+"&spareNameId="+spareName,
		function(data){
    	     $("#spareStatus").html(data.msg);
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=data.status;
        
    });
    
}

function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType,ftf,fcf,fscf,ef) 
{
	feedTypeFlag=ftf;
	feedCatgFlag=fcf;
	feedSCatgFlag=fscf;
	empFlag=ef;
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') 
	{
	    if (subdeptType=='1') 
		{
			$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
				success : function(subDeptData){
				$("#" + destAjaxDiv).html(subDeptData);
			    },
			    error : function () {
					alert("Somthing is wrong to get Sub Department");
				}
			});
		}
	    else if (subdeptType=='2') 
	    {
	    	$.ajax({
	    		type :"post",
	    		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/BySubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
	    		success : function(subDeptData){
	    		$("#" + destAjaxDiv).html(subDeptData);
	    	    },
	    	    error : function () {
	    			alert("Somthing is wrong to get Sub Department");
	    		}
	    	});
		}
	    else if (subdeptType=='3') 
	    {
			$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/ToSubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
				success : function(subDeptData){
				$("#" + destAjaxDiv).html(subDeptData);
			    },
			    error : function () {
					alert("Somthing is wrong to get Sub Department");
				}
			});
	    }
	}
	else if(deptLevel=='1')
	{
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/EmpData.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+deptId,
			success : function(empData){
			$("#" + destAjaxDiv).html(empData);
		    },
		    error : function () {
				alert("Somthing is wrong to get Employee Data");
			}
		});
		
	}
}
	
function getEmpData(subDeptId,destAjaxDiv) {
	if (empFlag=='1') {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/EmpData.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId,
		success : function(empData){
		$("#" + destAjaxDiv).html(empData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Employee Data");
		}
	});
	}
}

function getEmpMob(empId,destAjaxDiv) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/EmpMobData.action?destination="+ destAjaxDiv+"&empId="+empId,
		success : function(empData){
		$("#" + destAjaxDiv).val(empData.mobOne);
	    },
	    error : function () {
			alert("Somthing is wrong to get Employee Mobile Data");
		}
	});
	}

function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName+"&dialogId="+dialogId,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function isExistSLNoAjax(serialNo)
{
	document.getElementById("indicator2").style.display="block";
    $.getJSON("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/SLNoAvailabilty.action?serialNo="+serialNo,
		function(data){
    	$("#serialno").val(data.serialno);
    	/*if(data.status=='false')
    	{
    		$("#SLNoStatus").html(data.msg);
    	}
    	else
    	{
    		
    		$("#SLNoStatus").html(data.msg);
    		document.getElementById("SLNoStatus").style.display="none";
    	}
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=data.status;*/
    });
}

function getEmployeeBydeptId(deptId,targetid)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getEmployeeBydeptId?deptId="+deptId,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Employee Name"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data")
			;
		}
	});
}


function getSubLoc11(value,destAjaxDiv) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/LocationAjax.action?destination="+ destAjaxDiv+"&locationId="+value,
		success : function(data){
		$("#" + destAjaxDiv).html(data);
	    },
	    error : function () {
			alert("Somthing is wrong to get Location");
		}
	});
}
function getLoactionViaAjax11(subLoc)
{
     // var subCatg = document.getElementById(subCatgId).value;
       $.getJSON("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/SubLocationDetail.action?subLocId="+subLoc,
    	 function(data){
    	   
    	   if(data.intecom == null){
        	   $("#intecom").val("NA");
           }
           else {
        	   $("#intecom").val(data.intecom);
		   }
           
           if(data.location == null){
        	   $("#location").val("NA");
           }
           else {
        	   $("#location").val(data.location);
		   }
               
       });
}

function addNewEntry(newEntryFor)
{
	var datafor=$("#"+newEntryFor).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(newEntryFor=='Vendor')
	{
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorAddPage.action?pageFor=Vendor",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	}
	else if(newEntryFor=='VendorType')
	{
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorAddPage.action?pageFor=VendorType",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	}
	else if(newEntryFor=='AssetSuprtCatg')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportCatgAddPage.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='SpareCatg')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareCatgAddPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='AssetRegister')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeAssetAdd.action?empModuleFalgForDeptSubDept=1&assetAdd=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });	
	}
	else if(datafor=='Support')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?moduleName=Support",
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function()
		    {
	            alert("error");
	        }
		 });
	}
	else if(datafor=='Preventive')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?moduleName=Preventive",
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function()
		    {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='allotment')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotmentAddPage.action?allotmentAdd=1",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='groupAllotment')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeViewAllRemainingAsset.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='assetType')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetTypeAddPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	
	else if(newEntryFor=='Network Monitor')
	{
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/CreateNetworkMonitorAddPage.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	}
	else if(newEntryFor=='Support')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?moduleName=Support",
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function()
		    {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='Preventive')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSupportAddPage.action?moduleName=Preventive",
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function()
		    {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='SpareDetails')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSparePage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='ReceiveSpare')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareReceivePage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='ProcessRestriction')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/CreateProcessRestrictionAddPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(newEntryFor=='Free Reallot')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createFreeReallotAddPage.action?freeReallotAdd=1",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
}

function getViewData(viewFor)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(viewFor=='Vendor')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='VendorType')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/Vendor/createVendorTypeViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='AssetSuprtCatg')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createSuportCatgView.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='SpareCatg')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareCatgViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='AssetRegister')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetViewPage.action?modifyFlag=0&deleteFlag=0",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='Support')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName="+viewFor,
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='Preventive')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetActionGridPage.action?moduleName="+viewFor,
		    success : function(data) 
		    {
	       		$("#data_part").html(data);
			},
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='allotment')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAllotViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='AssetType')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createAssetTypeView.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='activitydashboard')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/createModuleViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='NetMonitor')
	{
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/ViewNetworkMonitor.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	}
	else if(viewFor=='SpareDetails')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createSpareViewPage.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='SpareReceiveDetails')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Spare/createReceiveViewPage.action?",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	else if(viewFor=='ProcessRestriction')
	{
		$.ajax({
			type : "post",
			url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewRestrictedExe.action",
			success : function(data){
				$("#data_part").html(data);
			},
			error : function(){
				alert("error");
			}
		});
	}
	else if(viewFor=='MachineDetails')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewMachineDetails.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
 
}

function uploadData(uploadFor)
{
	$("#UploadDialog").dialog('open');
	$("#dataDiv").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	if(uploadFor=='assetDetail')
	{
		$("#dataDiv").load("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetExcelUpload.jsp");	
	}
}



function viewNetMonitorLog()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/NetworkMonitor/beforeViewNetworkMonitorLog.action",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function resetForm(formId) 
{
 $('#'+formId).trigger("reset");
}
function getAssetDetailForSearch(assetValue,div1,div2,div3)
{
	var outletId=$("#outletId").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDetailsForSearch.action?assetType="+assetValue+"&outletId="+outletId,
	    success : function(data) 
	    {
	    	var code=data.code;
	    	var assetName=data.assetName;
	    	var mfgSerialNo=data.mfgSerialNo;
	    	
	    	$('#'+div1+' option').remove();
			$('#'+div1).append($("<option></option>").attr("value",-1).text('Select Code'));
			$(code).each(function(index)
			{
			   $('#'+div1).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
			
			$('#'+div2+' option').remove();
			$('#'+div2).append($("<option></option>").attr("value",-1).text('Select Asset Name'));
			$(assetName).each(function(index)
			{
			   $('#'+div2).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
			
			$('#'+div3+' option').remove();
			$('#'+div3).append($("<option></option>").attr("value",-1).text('Select Serial No'));
			$(mfgSerialNo).each(function(index)
			{
			   $('#'+div3).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	    
	   error: function() 
	    {
	        alert("error");
	    }
	 });
}
function findHiddenDiv(complValue)
{
	 if(complValue=="remToSelf")
 	{
 	 	document.getElementById("remToOther").style.display="none";
 	 	document.getElementById("remToBoth").style.display="none";
 	 	document.getElementById("other").style.display="none";
 	 	document.getElementById("self").style.display="block";
 	    $('input:radio[name=action_type]')[0].checked = true;
 	 	document.getElementById("ownerShip").style.display="none";
 	}
 	else if(complValue=="remToOther")
 	{
 	
 	 	document.getElementById("remToBoth").style.display="none";
 	    document.getElementById("remToOther").style.display="block";
 	    document.getElementById("self").style.display="block";
 	    document.getElementById("other").style.display="block";
 	    document.getElementById("ownerShip").style.display="block";
 	    $('input:radio[name=action_type]')[1].checked = true;
 	}
 	else if(complValue=="remToBoth")
 	{
 		document.getElementById("remToOther").style.display="none";
 		document.getElementById("remToBoth").style.display="none";
 		document.getElementById("remToBoth").style.display="block";
 		document.getElementById("self").style.display="block";
 		document.getElementById("other").style.display="block";
 		document.getElementById("ownerShip").style.display="block";
 		$('input:radio[name=action_type]')[1].checked = true;
 	} 	
 	else if(complValue=="Y")
 	{
 		document.getElementById("escalationDIV").style.display="block";
 	}
 	else if(complValue=="N")
 	{
 		document.getElementById("escalationDIV").style.display="none";
 		document.getElementById("escalationDIV1").style.display="none";
 		document.getElementById("escalationDIV2").style.display="none";
 		document.getElementById("escalationDIV3").style.display="none";
 		
 	}
}
function getDocu(divName) 
{
	document.getElementById(divName).style.display="block";
	
}
function getNextEscMap(escL1,escL2,escL3,div)
{
	var escL1SelectValue = $("#"+escL1).val() || [];
	var escL2SelectValue = $("#"+escL2).val() || [];
	var escL3SelectValue = $("#"+escL3).val() || [];
	
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getNextEscMap.action?escL1SelectValue="+escL1SelectValue+"&escL2SelectValue="+escL2SelectValue+"&escL3SelectValue="+escL3SelectValue,
		success : function(data)
		{
			$('#'+div+' option').remove();
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	    error : function () {
			alert("Somthing is wrong to get get Next excalation Level");
		}
	});
}
function changeEnable(timeId,ddId)
{
	$( "#"+timeId).prop("disabled", false);
	$( "#"+ddId).prop("disabled", false);
}
function assetDetailsSupport(value,dataFor)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetSupportDetail.action?value="+value+"&dataFor="+dataFor,
	    success : function(data) 
	    {
	    	if(data==null)
	    		{
	    		alert("There is no data available for entered the keyword.");
	    		}
	    	else
	    		{
	    	$("#assetname11").val(data.assetName);
	    	$("#serialno1").val(data.serialno);
	    	$("#assetbrand").val(data.assetbrand);
	    	$("#assetmodel").val(data.assetmodel);
	    	$("#supportfrom").val(data.supportfrom);
			$("#dueDate").val(data.supportfor);
			$("#assetId").val(data.assetID);
			$("#assetid").val(data.assetID);
			$("#serialno11").val(data.serialno);
			$("#assetname").val(data.assetName);
			$("#assetmodel11").val(data.assetmodel);
			$("#empid1").val(data.empName);
			$("#mobile").val(data.mobileNo);
			$("#dept").val(data.dept);
			$("#assetmodel1").val(data.assetmodel);
	    		}
	    },
	   error: function() {
	        alert("error");
	    }
	 });
}
function disableFields(value)
{
	if (value=='serialno') 
	{
		document.getElementById('assetname1').disabled=true;
		document.getElementById('mfgserialno').disabled=true;
	}
	else if(value=='assetname1')
	{
		document.getElementById('serialno').disabled=true;
		document.getElementById('mfgserialno').disabled=true;
	}
	else if(value=='mfgserialno')
	{
		document.getElementById('assetname1').disabled=true;
		document.getElementById('serialno').disabled=true;
	}
	
}
function enableField()
{
	document.getElementById('assetname').disabled=false;
	document.getElementById('serialno').disabled=false;
	document.getElementById('mfgserialno').disabled=false;
}
function disableReminder(value)
{
	if(value =="D")
	{
		document.configureSupport.reminder1.disabled="true";
	}
	else
	{
		document.configureSupport.reminder1.disabled=false;
	}	
}
function validateReminder(dueDate,freq,dateDiv)
{

	//document.getElementById("reminder1").value="";
	$("#dateDiff").val(0);
	$("#reminder1" ).datepicker( "option", "maxDate", 0);
	$("#reminder1" ).datepicker( "option", "minDate", 0);
	var remindDate= $("#"+dueDate).val();
	var frequency= $("#"+freq).val();
	var minDateValue= $("#dateDiff").val();
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
	    success : function(data) {
		$("#dateDiff").val(data.minDateValue);
		$("#reminder1" ).datepicker( "option", "maxDate", data.maxDate);
		$("#reminder1" ).datepicker( "option", "minDate", data.minDate);
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

$.subscribe('getSecondReminder', function(event,data)
	{
	$("#reminder2" ).datepicker( "option", "maxDate", 0);
	$("#reminder2" ).datepicker( "option", "minDate", 0);
	var remindDate= $("#reminder1").val();
	var frequency= $("#frequency").val();
	var minDateValue= $("#dateDiff").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
	    success : function(data) {
    		$("#reminder2" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder2" ).datepicker( "option", "minDate", data.minDate);
	    	
	    },
	   error: function() {
	        alert("error");
	    }
	 });
	});

$.subscribe('getThirdReminder', function(event,data)
		{
			$("#reminder3" ).datepicker( "option", "maxDate", 0);
			$("#reminder3" ).datepicker( "option", "minDate", 0);
			var remindDate= $("#reminder2").val();
			var frequency= $("#frequency").val();
			var minDateValue= $("#dateDiff").val();
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
			    success : function(data) {
		    		$("#reminder3" ).datepicker( "option", "maxDate", data.maxDate);
					$("#reminder3" ).datepicker( "option", "minDate", data.minDate);
			    	
			},
			   error: function() {
			        alert("error");
			    }
			 });
		});

		function resetSupport(formId) 
		{
		   $('#'+formId).trigger("reset");
		    var date=new Date(); 
			var dd = date.getDate();
			var mm = date.getMonth()+1; //January is 0!
			var yyyy = date.getFullYear();
			var ddd;
			var mmm;
			if(dd<10){
				ddd = "0"+dd;
			}
			else{
				ddd = dd;
			}
			if(mm<10){
				mmm="0"+mm;
			}
			else{
				mmm=mm;
			}
			var finalDate ;
				finalDate = ddd+"-"+mmm+"-"+yyyy;
		  document.getElementById("dueDate").value =finalDate;
		  document.getElementById("assetDetailsDiv").style.display="none";
		  document.getElementById("configCompliance1").style.display="none";
		  document.getElementById("configCompliance2").style.display="none";
		  document.getElementById("file").style.display="none";
		  document.getElementById("ownerShip").style.display="none";
		  document.getElementById("escalationDIV").style.display="none";
		  document.getElementById("escalationDIV1").style.display="none";
		  document.getElementById("escalationDIV2").style.display="none";
		  document.getElementById("escalationDIV3").style.display="none";
		  document.getElementById("remToOther").style.display="none";
		  document.getElementById("remToBoth").style.display="none";
		  document.getElementById("other").style.display="none";
		  enableField();
		 
		}
function getDeptOnOutlet(outletId,targetid)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/getDeptOnOutlet?outletId="+outletId,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Dept Name"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data")
			;
		}
	});
}



