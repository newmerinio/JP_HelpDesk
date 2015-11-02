function addinstantmsg()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeschdulemessageAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}

function changeFrequency(val,div1)
{

if(val=="One-Time")
{
	$("#"+div1).show();
}
else if(val=="Daily")
{
	$("#"+div1).hide();
}
else if(val=="Weekly")
{
	$("#"+div1).show();
}
else if(val=="Monthly")
{
	$("#"+div1).show();
}
else if(val=="Yearly")
{
	$("#"+div1).show();
}
else if(val=="-1")
{
	$("#"+div1).hide();
}
}
