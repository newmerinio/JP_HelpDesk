function fetchEntityData(value)
{
	if(value == '3'){
		$("#parameterFields").css("display","none");
		$("#subType").css("display","none");
		$('#industryID option').remove();
		$('#industryID').append($("<option></option>").attr("value",-1).text('All Industry'));
		$('#sourceName option').remove();
		$('#sourceName').append($("<option></option>").attr("value",-1).text('All Source'));
		$('#locationID option').remove();
		$('#locationID').append($("<option></option>").attr("value",-1).text('All Location'));
		$('#employee option').remove();
		$('#employee').append($("<option></option>").attr("value",-1).text('All Account Manager'));
	}
	else
	{
		$("#subType").css("display","block");
		$("#parameterFields").css("display","block");
		//Lead
		if(value == '0'){
			$('#relationshipSubType option').remove();
			$('#relationshipSubType').append($("<option></option>").attr("value",-1).text('All Lead'));
			$('#relationshipSubType').append($("<option></option>").attr("value",0).text('Raw Lead'));
			$('#relationshipSubType').append($("<option></option>").attr("value",4).text('Lost Lead'));
		}
		//Client
		if(value == '1'){
			$('#relationshipSubType option').remove();
			$('#relationshipSubType').append($("<option></option>").attr("value",-1).text('All Client'));
			$('#relationshipSubType').append($("<option></option>").attr("value",0).text('Prospect Client'));
			$('#relationshipSubType').append($("<option></option>").attr("value",1).text('Existing Client'));
			$('#relationshipSubType').append($("<option></option>").attr("value",2).text('Lost Client'));
		}
		//Associate
		if(value == '2'){
			$("#forAssociateDiv").css("display","block");
			$('#relationshipSubType option').remove();
			$('#relationshipSubType').append($("<option></option>").attr("value",-1).text('All Associate'));
			$('#relationshipSubType').append($("<option></option>").attr("value",0).text('Prospect Associate'));
			$('#relationshipSubType').append($("<option></option>").attr("value",1).text('Existing Associate'));
			$('#relationshipSubType').append($("<option></option>").attr("value",2).text('Lost Associate'));
		}else	{
			$("#forAssociateDiv").css("display","none");
		}
		if(value == '0' || value == '1' || value == '2')
		{
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/WFPM/CRM/fetchAllParameters.action",
			    data : "entityType="+value,
			    success : function(data) {
				//alert("data:"+data);
				if(data != null)
				{
					//Industry
					$('#industry option').remove();
					$('#industry').append($("<option></option>").attr("value",-1).text('All Industry'));
					$(data.jsonArrayIndustry).each(function(index)
					{
					   $('#industry').append($("<option></option>").attr("value",this.ID).text(this.NAME));
					});
					//Source
					$('#source option').remove();
					$('#source').append($("<option></option>").attr("value",-1).text('All Source'));
					$(data.jsonArraySource).each(function(index)
					{
						$('#source').append($("<option></option>").attr("value",this.ID).text(this.NAME));
					});
					//Location
					$('#location option').remove();
					$('#location').append($("<option></option>").attr("value",-1).text('All Location'));
					$(data.jsonArrayLocation).each(function(index)
					{
						$('#location').append($("<option></option>").attr("value",this.ID).text(this.NAME));
					});
					//Account Manager
					$('#employee option').remove();
					$('#employee').append($("<option></option>").attr("value",-1).text('All Account Manager'));
					$(data.jsonArrayAccMgr).each(function(index)
					{
						$('#employee').append($("<option></option>").attr("value",this.ID).text(this.NAME));
					});
					//Birthday To
					//$('#birthdayTo').val(data.birthdayTo);
					//Anniversary To
					//$('#anniversaryTo').val(data.anniversaryTo);
					//Department
					$('#department option').remove();
					$('#department').append($("<option></option>").attr("value",-1).text('All Department'));
					$(data.jsonArraydepartment).each(function(index)
					{
						$('#department').append($("<option></option>").attr("value",this.ID).text(this.NAME));
					});
					//Designation
					$('#designation option').remove();
					$('#designation').append($("<option></option>").attr("value",-1).text('All Designation'));
					$(data.jsonArrayDesignation).each(function(index)
					{
						$('#designation').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
					});
					//Designation
					$('#allergic_to option').remove();
					$('#allergic_to').append($("<option></option>").attr("value",-1).text('All Allergic To'));
					$(data.jsonArrayAllergic).each(function(index)
					{
						$('#allergic_to').append($("<option></option>").attr("value",this.NAME).text(this.NAME));
					});
				}
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}	
	}
	
	//Fetch grid
	beforeFetchCRMDataView();
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
				$('#'+divId).append($("<option></option>").attr("value",-1).text('All Sub-Industry'));
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
	//Fetch grid
	beforeFetchCRMDataView();
}

function fillNameNew(val,divId,param)
{
	//	param is either 1 or 0
	//	1: for fetching name on both key and value place
	//	0: for fetching id on key and name on value place
	
	//in case of param 1 fetch dropdown both parameter as name only otherwise set it as id and name  
	var id = val;
	if(id == null || id == '-1')
	{
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text('All Refered Name'));
		return;
	}

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/fetchReferredName.action",
		data: "id="+id+"&param="+param, 
		success : function(data) {   
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text('All Refered Name'));
		$(data).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		   
		});
	},
	   error: function() {
            alert("error");
        }
	 });
	
	//Fetch grid
	beforeFetchCRMDataView();
}

function beforeFetchCRMDataView()
{
	$("#dynamicGridData").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	var entityType = $("#entityType").val();
	var relationshipSubType = $("#relationshipSubType").val();
	var industry = $("#industry").val();
	var subIndustry = $("#subIndustry").val();
	var source = $("#source").val();
	var rating = $("#rating").val();
	var location = $("#location").val();
	var department = $("#department").val();
	var designation = $("#designation").val();
	var influence = $("#influence").val();
	
	var age = $("#age").val();
	var sex = $("#sex").val();
	var allergic_to = $("#allergic_to").val();
	var blood_group = $("#blood_group").val();
	
	var birthdayFrom = $("#birthdayFrom").val();
	
	var anniversaryFrom = $("#anniversaryFrom").val();
	var chkBirthday = $("#chkBirthday").attr('checked') == 'checked' ? true:false;
	var chkAnniversary = $("#chkAnniversary").attr('checked') == 'checked' ? true:false;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/CRM/beforeFetchCRMDataView.action",
		data: "entityType="+entityType+"&relationshipSubType="+relationshipSubType+"&industry="+industry+"&subIndustry="+subIndustry+"&source="
				+source+"&rating="+rating+"&location="+location+"&department="+department+"&designation="+designation+"&influence="+influence
				+"&birthdayFrom="+birthdayFrom+"&anniversaryFrom="+anniversaryFrom
				+"&chkBirthday="+chkBirthday+"&chkAnniversary="+chkAnniversary
				+"&age="+age+"&sex="+sex
				+"&allergic_to="+allergic_to+"&blood_group="+blood_group,
		success : function(data) {   
		$("#dynamicGridData").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function birthdayBeforeFetchCRMDataView()
{
	var chkBirthday = $("#chkBirthday").attr('checked') == 'checked' ? true:false;
	if(chkBirthday) beforeFetchCRMDataView();
}

function anniversaryBeforeFetchCRMDataView()
{
	var chkAnniversary = $("#chkAnniversary").attr('checked') == 'checked' ? true:false;
	if(chkAnniversary) beforeFetchCRMDataView();
}

function communicateMe()
{
	$("#data_part").dialog("open");
	$("#data_part").load("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var entityType = $("#entityType").val();
	var groupName = $("#groupName").val();
	var comment = $("#comment").val();
	var createType = $("#createType option:selected").text();
	var viewType = $("#viewType option:selected").text();
	//alert("comment  "+comment+"  createType  "+createType+"  viewType  "+viewType);
	var chkGroup = $("#chkGroup").is(':checked');
	//alert("chkGroup:"+chkGroup);
	var entityContacts = $("#CRMview").jqGrid('getGridParam','selarrrow');
	//alert("entityContacts>>"+entityContacts);
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/CRM/beforeSendPage.action?entityType="+entityType+"&entityContacts="+entityContacts+"&groupName="+groupName+"&chkGroup="+chkGroup+"&comment="+comment+"&createType="+createType+"&viewType="+viewType));
}
function backToMainView()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeCommActivityPage.action?status=Active",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
}
function communicateViaMail()
{
	var entityContacts = $("#CRMview").jqGrid('getGridParam','selarrrow');
	
	
	if(entityContacts==0)
	{
		alert("Please Select atleast one  row")
	}
	else{
	$("#data_part").dialog("open");
	$("#data_part").load("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var entityType = $("#entityType").val();
	var groupName = $("#groupName").val();
	var comment = $("#comment").val();
	var createType = $("#createType option:selected").text();
	var viewType = $("#viewType option:selected").text();
	//alert("comment  "+comment+"  createType  "+createType+"  viewType  "+viewType);
	var chkGroup = $("#chkGroup").is(':checked');
	//alert("chkGroup:"+chkGroup);
	
	//alert(entityContacts);
	var subIndustry = jQuery("#CRMview").jqGrid('getCell','selarrrow', 'subIndustry');
	//alert(subIndustry);
	//alert("entityContacts>>"+entityContacts+"subIndustry  >"+subIndustry);
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/WFPM/CRM/beforeMailSendPage.action?entityType="+entityType+"&entityContacts="+entityContacts+"&groupName="+groupName+"&chkGroup="+chkGroup+"&comment="+comment+"&createType="+createType+"&viewType="+viewType,
		success : function(subdeptdata) {
		$("#data_part").html(subdeptdata);
	},
	error: function() {
		alert("error");
	}
	});
	}
/*	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/CRM/beforeMailSendPage.action?entityType="+entityType+"&entityContacts="+entityContacts+"&groupName="+groupName+"&chkGroup="+chkGroup+"&comment="+comment+"&createType="+createType+"&viewType="+viewType));*/
}

function showGroup(chk,group)
{
	$("#"+group).val("");
	if(chk.checked)
	{
		$("#"+group).show();
	}
	else
	{
		$("#"+group).hide();
	}
}
function ccreateGroup()
{
	$("#data_part").dialog("open");
	$("#data_part").load("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var entityType = $("#entityType").val();
	var groupName = $("#groupName").val();
	var chkGroup = $("#chkGroup").is(':checked');
	//alert("chkGroup:"+chkGroup);
	var entityContacts = $("#CRMview").jqGrid('getGridParam','selarrrow');
	$("#data_part").load(encodeURI("view/Over2Cloud/WFPM/CRM/beforecreateGroupPage.action?entityType="+entityType+"&entityContacts="+entityContacts+"&groupName="+groupName+"&chkGroup="+chkGroup));
}