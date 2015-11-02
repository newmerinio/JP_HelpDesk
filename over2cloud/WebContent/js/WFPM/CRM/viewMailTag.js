	function searchMailTagResult(searchField,searchString)
	{
		
		//var from=$("#fromDate").val();
		//var to=$("#toDate").val();
			$.ajax({
		    type : "post",
	    	url  : "view/Over2Cloud/WFPM/CRM/showMailTagData.action",
	    	data : "searchField="+searchField+"&searchString="+searchString,
		    success : function(subdeptdata) 
		    {
		       $("#"+"mailtagdatapart").html(subdeptdata);
		    },
		    error: function() 
		    {
		            alert("error");
		    }
		 });
	}

	function addMailTag()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
		    type : "post",
	    	url  : "view/Over2Cloud/WFPM/CRM/beforeAddMailTagData.action",
	    	//data : "searchField="+searchField+"&searchString="+searchString,
		    success : function(subdeptdata) 
		    {
		       $("#"+"data_part").html(subdeptdata);
		    },
		    error: function() 
		    {
		            alert("error");
		    }
		 });
	}

	function backtoMailTagView()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/WFPM/CRM/beforeMailTagPage.action",
			success : function(subdeptdata) {
			$("#data_part").html(subdeptdata);
		},
		error: function() {
			alert("error");
		}
		});
	}
	

