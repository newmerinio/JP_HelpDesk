
function pageisNotAvibale()
{
$("#facilityisnotavilable").dialog('open');
}



function searchRow()
{
	 $("#gridedittable11").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		jQuery("#gridedittable11").jqGrid('editGridRow', row ,{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
}
function deleteRow()
{
	  var status = jQuery("#gridedittable11").jqGrid('getCell',row,'status');
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else if(status=='Inactive')
	{
		alert("The selected data is already Inactive.");
	}
	else
	{
		$("#gridedittable11").jqGrid('delGridRow',row, {height:120, width:425,reloadAfterSubmit:true,closeAfterEdit:false,top:150,left:350,modal:true,caption:'Inactive',msg: "Inactive selected record(s)?",bSubmit: "Inactive" , afterSubmit: function () {
			reloadPage();
	    }});
		
	}
	
}
function reloadGrid()
{
	loadGrid();
}


function reloadPage()
{
	row=0;
	$.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeGroupHeaderView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function openPreVisitRequestAddForm()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
	    url : "view/Over2Cloud/leaveManagement/preVisitRequestOpenAddForm.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });


}


function addNewGroup() {

	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforegroupCreate.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
	
}

 function visitingPurposeAddPageOpen()
{
	 

		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    $.ajax({
	        type : "post",
	        url :"view/Over2Cloud/leaveManagement/leaveVisitingPurposeAddPageOpen.action",
	        success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	    },
	       error: function() {
	            alert("error");
	        }
	     });
		

}

function loadGrid()
{
	row=0;
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",

	    url : "view/Over2Cloud/leaveManagement/leaveVisitingPurposeViewGrid.action ",
	    success : function(subdeptdata) {
       $("#result_partss").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function loadGrid1()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
      url : "view/Over2Cloud/leaveManagement/leavePreVisitRequestViewGrid.action ",
	    success : function(subdeptdata) {
       $("#result_partss").html(subdeptdata);
               },
	   error: function() {
            alert("error");
        }
	 });
}


