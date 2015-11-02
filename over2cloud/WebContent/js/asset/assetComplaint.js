function showHideBlock(fieldValue)
{
	$('#formone').trigger("reset");
	if (fieldValue=='asset') {
		document.getElementById('emp_block').style.display="none";
		document.getElementById('asset_block').style.display="block";
	}
	else if (fieldValue=='employee') {
		document.getElementById('asset_block').style.display="none";
		document.getElementById('emp_block').style.display="block";
	}
}


function getFeedBreifViaAjax(subCatg)
{
     // var subCatg = document.getElementById(subCatgId).value;
      $("#scatgid").val(subCatg);
      $("#test").val(subCatg);
       $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/SubCatgDetail.action?subcatg="+subCatg,
    	 function(data){
        	   $("#subCatgId").val(data.id);
    	   
               if(data.feed_msg == null){
            	   $("#feed_brief").val("NA");
               }
               else {
            	   $("#feed_brief").val(data.feed_msg);
			   }
       });
}


// function for getting data on AssetOnline page.
function getDetail4CallOrOnline(id,dataFor,show)
{
      var uniqueIdVal = document.getElementById(id).value;
       $.getJSON("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getDataById.action?uniqueid="+uniqueIdVal+"&dataFor="+dataFor,
    	 function(data){
    	  // alert("Inside Success   ")
    	   if (show=='online') {
    		   alert("Inside If"+data.serialNo);
    		   $("#bydept").val(data.deptId);
        	   $("#assetid").val(data.assetId);
    		   if(data.serialNo == null || data.serialNo == ''){$("#serialno").val("NA");}
               else {$("#serialno").val(data.serialNo);}
		   }
    	   else if (show=='asset_call') {
    		  // alert("Inside Else If");
    		   $("#bydept").val(data.deptId);
        	   $("#assetid").val(data.assetId);
    		   if(data.assetName == null){$("#asset_assetid").val("NA");}
               else {$("#asset_assetid").val(data.assetName);}
    		   
    		   if(data.empId == null){$("#uniqueid").val("NA");}
               else {$("#uniqueid").val(data.empId);}

               if(data.empName == null){$("#feed_by").val("NA");}
               else {$("#feed_by").val(data.empName);}

               if(data.mobOne == null){$("#feed_by_mobno").val("NA");}
               else {$("#feed_by_mobno").val(data.mobOne);}

               if(data.emailId == null){$("#feed_by_emailid").val("NA");}
               else {$("#feed_by_emailid").val(data.emailId);}
		   }
    	   else if (show=='emp_call') {
    		//   alert("Inside second Else If");
    		   $("#bydept").val(data.deptId);
        	   $("#assetid").val(data.assetId);
    		   if(data.empId == null){$("#uniqueid").val("NA");}
               else {$("#uniqueid").val(data.empId);}

               if(data.empName == null){$("#feed_by").val("NA");}
               else {$("#feed_by").val(data.empName);}

               if(data.mobOne == null){$("#feed_by_mobno").val("NA");}
               else {$("#feed_by_mobno").val(data.mobOne);}

               if(data.emailId == null){$("#feed_by_emailid").val("NA");}
               else {$("#feed_by_emailid").val(data.emailId);}
		   }
    	   else if (show=='emp_call_asset') {
    		 //  alert("Inside third Else If");
    		   $("#bydept").val(data.deptId);
        	   $("#assetid").val(data.assetId);
    		   if(data.serialNo == null){$("#emp_assetserialno").val("NA");}
               else {$("#emp_assetserialno").val(data.serialNo);}
		   }
       });
}



function getComplaintType(assetid,targetid,frontVal)
{
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getAssetTypeDetail.action?uniqueid="+ assetid,
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



function getAssetDetail(uniqueid,targetid,frontVal)
{
	if (uniqueid=='uniqueid') {
		  var uniqueidVal = document.getElementById(uniqueid).value;
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getAssetDetail.action?uniqueid="+ uniqueidVal,
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
}


function getCategory(assetId,feedType,targetid,frontVal)
{
		var asset_Id = document.getElementById(assetId).value;
		//alert("Asset Id "+asset_Id);
	//	alert("Feed Id "+feedType);
		//alert("Target Id"+targetid);
		
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getComplaintCategory.action?uniqueid="+ asset_Id+"&feedType="+feedType,
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



//function for getting data on AssetOnline page.
function getAssetDetail(id)
{
      var uniqueIdVal = document.getElementById(id).value;
       $.getJSON("/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/getAssetSerialNo.action?uniqueid="+uniqueIdVal,
    	 function(data){
    		   $("#bydept").val(data.deptId);
        	   $("#assetid").val(data.assetId);
    		   if(data.serialNo == null || data.serialNo == ''){$("#serialno").val("NA");}
               else {$("#serialno").val(data.serialNo);}
       });
}



$.subscribe('validate',function(event,data){
	$("#printSelect").dialog('open');
});

$.subscribe('validateOnline',function(event,data){
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmEscalationDialog").dialog({title:'Action Status'});
	setTimeout(function(){ $("#confirmEscalationDialog").dialog('close'); }, 7000);
});