

function fetchOfferingContacts()
	{
		var key = obj[count].ID;
		var value = obj[count].VALUE;
		$("#offeringValue").html(value);
		
		$.ajax({
			type : "post",
			url  : "view/Over2Cloud/wfpm/dashboard/fetchContactPerson.action",
			data : "id="+id+"&temp="+temp+"&offeringId="+key,
			success : function(data){
				$("#offeringContactDivID").html("");
				if(data.jsonArray != null && data.jsonArray.length > 0)
				{
					var tableMy = $('<table></table>').attr({ id: "offeringContactTableID" });
					tableMy.css("width","98%");
					for(var i=0; i < data.jsonArray.length; i++)
					{
						
						var row = $('<tr></tr>').appendTo(tableMy);
						var thRow = '<th style="cursor:pointer;padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 4%;">';
							thRow+= '<img onclick="openActivityOfferingAndContactWise('+data.jsonArray[i].ID+');" title="Mapped Activity" alt="Mapped Activity" src="images/WFPM/commonDashboard/fullHistory.jpg">';
							thRow+= '</th>';
							$(thRow).appendTo(row);
						var thRow1 = '<th colspan="4" style="background-color:#ccc;cursor: pointer;padding:2px;margin:0px;border:1px solid #ccc;text-align:center;">&nbsp;';
							thRow1+= data.jsonArray[i].NAME ;
						for(var h=0; h<data.jsonArray[i].DOI; h++)
						{
							thRow1 += '<img alt="star" src="images/WFPM/commonDashboard/star.jpg">&nbsp;';
						}
						thRow1 += '</th>';
						$(thRow1).appendTo(row);
						
						var row1 = $('<tr></tr>').appendTo(tableMy);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 4%;"></td>').appendTo(row1);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#9fd7fb;">Designation</td>').appendTo(row1);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#9fd7fb;">Department</td>').appendTo(row1);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#9fd7fb;">Mobile No.</td>').appendTo(row1);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#9fd7fb;">Email ID</td>').appendTo(row1);
						
						var row2 = $('<tr></tr>').appendTo(tableMy);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 4%;"></td>').appendTo(row2);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#c3e6fc;"></td>').text(data.jsonArray[i].DESIGNATION).appendTo(row2);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#c3e6fc;"></td>').text(data.jsonArray[i].DEPARTMENT).appendTo(row2);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#c3e6fc;"></td>').text(data.jsonArray[i].CONTACT_NO).appendTo(row2);
						$('<td style="padding:2px;margin:0px;border:1px solid #ccc;text-align:center; width: 24%;background-color:#c3e6fc;"></td>').text(data.jsonArray[i].EMAIL).appendTo(row2);
	
						var row3 = $('<tr></tr>').attr({ "id" : "TR"+data.jsonArray[i].ID }).appendTo(tableMy);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 4%;"></td>').appendTo(row3);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 24%;"></td>').appendTo(row3);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 24%;"></td>').appendTo(row3);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 24%;"></td>').appendTo(row3);
						$('<td style="padding:2px;margin:0px;border:1px solid #ffffff;text-align:center; width: 24%;"></td>').appendTo(row3);
					}
					tableMy.appendTo("#offeringContactDivID");
				}
			},
			error   : function(){
				alert("error");
			}
		});
	}
	function previousOffering()
	{
		count--;
		showDiv();
		fetchOfferingContacts();		
	}
	function nextOffering()
	{
		count++;
		showDiv();
		fetchOfferingContacts();
	}
	function showDiv()
	{
		if((obj.length - 1) == 0)
		{
			$("#prevDivID").css("display","none");
			$("#nextDivID").css("display","none");
		}
		else if(count == 0)
		{
			$("#prevDivID").css("display","none");
			$("#nextDivID").css("display","block");
		}
		else if(count == (obj.length - 1))
		{
			$("#prevDivID").css("display","block");
			$("#nextDivID").css("display","none");
		}
		else if(count > 0 && count < (obj.length - 1))
		{
			$("#prevDivID").css("display","block");
			$("#nextDivID").css("display","block");
		}
	}
	nextOffering();
	
function backToActivityPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/></center>");
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/beforeCommonDashboard.action",
		data : "currDate="+currDate,
		success : function(data){
			$("#data_part").html(data);
		},
		error   : function(){
			alert("error");
		}
	});
}

function openActivityOfferingAndContactWise(id)
{
	var offeringId = obj[count].ID;
	//alert("id:"+id+"#offeringId:"+offeringId);
	$.ajax({
		type : "post",
		url  : "view/Over2Cloud/wfpm/dashboard/fetchActivityByOfferingAndContact.action",
		data : "id="+id+"&temp="+temp+"&offeringId="+offeringId,
		success : function(data){
			if(data.jsonArray != null && data.jsonArray.length > 0)
			{
				var row = $('<tr style=" margin-top:10px; margin-bottom:10px;"></tr>');
				$('<td style="padding:0px;margin:0px;margin:0px;border:1px solid #FFFFF;text-align:center;"></td>').appendTo(row);
				$('<td colspan="3" style="background-color:#EE9A00;padding:0px;margin:0px;margin:0px;border:1px solid #000000;text-align:center;">&nbsp;Activity History&nbsp;</td>').appendTo(row);
				$("#TR"+id).after(row); 
				
				var row1 = $('<tr style=" margin-top:10px; margin-bottom:10px;"></tr>');
				$('<td style="padding:0px;margin:0px;margin:0px;border:1px solid #ffffff;text-align:center;"></td>').appendTo(row1);
				$('<td style="background-color:#EE9A00;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;">Activity</td>').appendTo(row1);
				$('<td style="background-color:#EE9A00;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;">Comment</td>').appendTo(row1);
				$('<td style="background-color:#EE9A00;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;">Date</td>').appendTo(row1);
				$('<td style="padding:0px;margin:0px;border:1px solid #ffffff;text-align:center;"></td>').appendTo(row1);
				$(row).after(row1); 
				
				
				var i=0;
				while(i < data.jsonArray.length)
				{
					var row = $('<tr style=" margin-top:10px; margin-bottom:10px;"></tr>').attr({ "id": "TRR"+i });
					$('<td style="padding:0px;margin:0px;margin:0px;border:1px solid #FFFFF;text-align:center;"></td>').appendTo(row);
					$('<td style="background-color:#FFDB99;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;"></td>').text(data.jsonArray[i].STATUS).appendTo(row);
					$('<td style="background-color:#FFDB99;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;"></td>').text(data.jsonArray[i].COMMENT).appendTo(row);
					$('<td style="background-color:#FFDB99;padding:0px;margin:0px;border:1px solid #ccc;text-align:center;"></td>').text(data.jsonArray[i].DATE).appendTo(row);
					$('<td style="padding:0px;margin:0px;border:1px solid #ffffff;text-align:center;"></td>').appendTo(row);
					
					if(i == 0) $(row1).after(row); 
					else $("#TRR"+(i - 1)).after(row);
					
					i++;
				}
				
				var row = $('<tr style="background-color:FFFFCC; margin-top:10px; margin-bottom:10px;"></tr>');
				$('<td style="padding:0px;margin:0px;margin:0px;border:1px solid #FFFFF;text-align:center;"></td>').appendTo(row);
				$('<td style="padding:0px;margin:0px;background-color:#ffffff;border:1px solid #000000;text-align:center;"></td>').appendTo(row);
				$('<td style="padding:0px;margin:0px;background-color:#ffffff;border:1px solid #000000;text-align:center;"></td>').appendTo(row);
				$('<td style="padding:0px;margin:0px;background-color:#ffffff;border:1px solid #000000;text-align:center;"></td>').appendTo(row);
				$('<td style="padding:0px;margin:0px;border:1px solid #ffffff;text-align:center;"></td>').appendTo(row);
				$("#TRR"+(i - 1)).after(row);
			}
		},
		error   : function(){
			alert("error");
		}
	});

}
