
//find the data according to selected module from client
function krSearchViaAjax(krSearchResult) 
{
	alert("function called<><><><><><><>>>>  ");
	var singleValues = $("#krSearchResult").val();
	var singleValues2 = $("#single").val();
	alert("the value of DIV IS>>>>   "+singleValues);
	
	var tagValue=krSearchForm.tags.value;
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/cloudapp/view/Over2Cloud/KRLibraryOver2Cloud/searchKRViaAjax.action?tags="+tagValue.split(" ").join("%20"),
	        success : function(data) 
	        {	alert("inside the success   "+data);
	        	document.getElementById("indicator1").style.display="none";
	        	$("#"+krSearchResult).html(data);},
	        	error: function() {
	        		alert("error");
	        		}
	     }
	  );
}

function getDivForShow(theValueAre) {
		
	//var abc=$('input:checkbox:checked').val(); 
	
	var singleValues = $("#shareTo").val();
	alert($('s:checked').val());
	alert("called<><><>>>>>>");
	alert("the value of abs is><><><><><>>>>>     "+singleValues);
	
		document.getElementById("shareToDiv").style.display='block';
		document.getElementById("mail").style.display='none';
		
	}

function getEmpName(FromdeptId, divId) 
{
	
	var fromDept=$("#dept").val();
	
	
	$.ajax( {
				type :"post",
				url :"/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/getEmpName.action?fromDeptId="+fromDept,
				success : function(datapart) 
				{
					$("#"+divId).html(datapart);
				},
				error : function() {
					alert("error");
				}
			});
}
function fetchGroup(value,selectId)
{ 
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/fetchGroup.action?grpID="+value,
	    success : function(data) {

		$('#'+selectId+' option').remove();
		$('#'+selectId).append($("<option></option>").attr("value",-1).text('Select Group'));
    	$(data).each(function(index)
		{
		   $('#'+selectId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getDocName(value,selectId,fromDept)
{ 
	var fDept=$("#"+fromDept);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/KRLibraryOver2Cloud/fetchDocument.action?grpID="+value+"&fromDept="+fDept,
	    success : function(data) 
	    {
	    	$("#"+selectId).html(data);
	   },
	   error: function() {
            alert("error");
        }
	 });
}
var indexVal=1;
function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
  indexVal++;
  $("#indexVal").val(indexVal);
}
function deletediv(divID)
{
	document.getElementById(divID).style.display="none";
	indexVal--;
	$("#indexVal").val(indexVal);
}
function getAutoStatingId()
{
	var subGroupAbb=$("#sub_group_abbr").val();
	var groupId=$("#groupNameNew").val();
	if (subGroupAbb!=null && subGroupAbb=='') 
	{
		alert("Please Enter Sub Group Abbrivation First!!!");
	} 
	else 
	{
		$.ajax
		  ({
		        type: "post",
		        url : "view/Over2Cloud/KRLibraryOver2Cloud/getGroupAbbr.action?groupId="+groupId,
		        success : function(data) 
		        {	
			 // console.log(data);
		        	$("#kr_starting_id").val(data.abbreviation+"/"+subGroupAbb);
		        },
	        	error: function() 
	        	{
	        		alert("error");
	        	}
		     }
		  );
	}
}
function addNewDataUpload()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=KR",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function viewUploadKR()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUploadViewHeader.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function resetTaskName(formId)
{
	$('#'+formId).trigger("reset");
}
function showHide(value,divId)
{
	if ( value=='Yes') 
	{
		$("#"+divId).show();
	}
	else 
	{
		
		$("#"+divId).hide();
	}
}

function unableCheckBox(value,fieldName)
{
	  if (jQuery("#"+value).is(':checked')) {
          jQuery('#'+fieldName).prop("disabled", false);
    } else {
          jQuery('#'+fieldName).prop("disabled", true);
    }
}
function selectRadio()
{
	$('input:radio[name=action_req]')[1].checked = true;
}
function getSubGrp(grpID, divId) 
{
	$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/getSubGrp.action?grpID="+ grpID,
				success : function(companyData) {
					$('#' + divId + ' option').remove();
					$('#' + divId).append($("<option></option>").attr("value", -1).text('Select Sub Group'));
					$(companyData).each
					(
						function(index) 
						{
							$('#'+divId).append($("<option></option>").attr("value", this.id).text(this.groupName));
						}
					);
				},
				error : function() {
					alert("error");
				}
			});

}
function getKrName(subgrpID, divId) 
{
	$.ajax({
				type :"post",
				url :"/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/getKRName.action?subgrpID="+ subgrpID,
				success : function(companyData) {
					$('#' + divId + ' option').remove();
					$('#' + divId).append($("<option></option>").attr("value", -1).text('Select KR'));
					$(companyData).each
					(
						function(index) 
						{
							$('#'+divId).append($("<option></option>").attr("value", this.id).text(this.subGroupName));
						}
					);
				},
				error : function() {
					alert("error");
				}
			});

}
function getDocument(divName) 
{
	document.getElementById(divName).style.display="block";
}
function downloadReport(id)
{
	var krName=jQuery("#gridedittable").jqGrid('getCell',id,'kr_name');
	var krId=jQuery("#gridedittable").jqGrid('getCell',id,'kr_starting_id');
	
	$('#takeActionDialog').dialog('open');
	$('#takeActionDialog').dialog({height: '400',width:'900',title:'Download History For '+krName+', KR ID: '+krId});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/downloadReport.action?docName="+id,
	    success : function(data) 
	    {
			$("#"+"resultDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });	

}
function shareReport(id)
{
	var krName=jQuery("#gridedittable").jqGrid('getCell',id,'kr_name');
	var krId=jQuery("#gridedittable").jqGrid('getCell',id,'kr_starting_id');
	
	$('#takeActionDialog').dialog('open');
	$('#takeActionDialog').dialog({height: '400',width:'900',title:'Share History For '+krName+', KR ID: '+krId});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/shareReport.action?docName="+id,
	    success : function(data) 
	    {
			$("#"+"resultDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });	

}
function editReport(id)
{
	var krName=jQuery("#gridedittable").jqGrid('getCell',id,'kr_name');
	var krId=jQuery("#gridedittable").jqGrid('getCell',id,'kr_starting_id');
	$('#takeActionDialog').dialog('open');
	$('#takeActionDialog').dialog({height: '400',width:'1000',title:'Edit History For '+krName+', KR ID: '+krId});
	$("#resultDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/editReport.action?docName="+id,
	    success : function(data) 
	    {
			$("#"+"resultDiv").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });	

}