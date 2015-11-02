<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	    
	    <%@taglib prefix="s" uri="/struts-tags" %>
	    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
	    <%@ taglib prefix="sjg"  uri="/struts-jquery-grid-tags" %>
	    
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<script type="text/javascript" src="<s:url value="/js/menucontrl.js"/>"></script>
	<script type="text/javascript" src="js/VAM/menucontrl_VAM.js"></script>
	
    <title id='Description'>Visitor Record with image.</title>
    <link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxgrid.selection.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxtabs.js"></script>
    <script type="text/javascript" src="scripts/demos.js"></script>
    
    <script type="text/javascript">
    var  sampleData =null;
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/VAM/reports/jsonGrid.action",
	    type : "json",
	    success : function(data) {
	    	 var source =
	            {
	                localdata: data,
	                datatype: "json"
	            };
            var initrowdetails = function (index, parentElement, gridElement, datarecord) {
                var tabsdiv = null;
                var information = null;
                var notes = null;
                tabsdiv = $($(parentElement).children()[0]);
                if (tabsdiv != null) {
                    information = tabsdiv.find('.information');
                    notes = tabsdiv.find('.notes');
                    var title = tabsdiv.find('.title');
                    title.text(datarecord.firstname);

                    var container = $('<div style="margin: 55px;"></div>');
                    container.appendTo($(information));
                    var photocolumn = $('<div style="float: left; width: 15%;"></div>');
                    var leftcolumn = $('<div style="float: left; width: 45%;"></div>');
                    var rightcolumn = $('<div style="float: left; width: 40%;"></div>');
                    container.append(photocolumn);
                    container.append(leftcolumn);
                    container.append(rightcolumn);

                    var photo = $("<div class='jqx-rc-all' style='margin: 10px;'><b>Photo:</b></div>");
                    var image = $("<div style='margin-top: 10px;'></div>");
                    //var imgurl = 'images1/' + datarecord.country.toLowerCase() + '.png';
                    var imgurl = 'visitorsnap/' + datarecord.country.toLowerCase();
                    var img = $('<img height="60" src="'+imgurl+'"/>');
                   // var img = $('<img height="60" src="visitorsnap/image18t01t201414t44t59.jpg" />');
                    alert(imgurl+"img>>>>"+img);
                   image.append(img);
                    image.appendTo(photo);
                    photocolumn.append(photo);

                    var firstname = "<div style='margin: 10px;'><b>First Name:</b> " + datarecord.firstname + "</div>";
                    var lastname = "<div style='margin: 10px;'><b>Last Name:</b> " + datarecord.lastname + "</div>";
                    var title = "<div style='margin: 10px;'><b>Title:</b> " + datarecord.title + "</div>";
                    var address = "<div style='margin: 10px;'><b>Address:</b> " + datarecord.address + "</div>";
                    $(leftcolumn).append(firstname);
                    $(leftcolumn).append(lastname);
                    $(leftcolumn).append(title);
                    $(leftcolumn).append(address);

                    var postalcode = "<div style='margin: 10px;'><b>Postal Code:</b> " + datarecord.postalcode + "</div>";
                    var city = "<div style='margin: 10px;'><b>City:</b> " + datarecord.city + "</div>";
                    var phone = "<div style='margin: 10px;'><b>Phone:</b> " + datarecord.homephone + "</div>";
                    var hiredate = "<div style='margin: 10px;'><b>Hire Date:</b> " + datarecord.hiredate + "</div>";

                    $(rightcolumn).append(postalcode);
                    $(rightcolumn).append(city);
                    $(rightcolumn).append(phone);
                    $(rightcolumn).append(hiredate);

                    var notescontainer = $('<div style="white-space: normal; margin: 5px;"><span>' + datarecord.notes + '</span></div>');
                    $(notes).append(notescontainer);
                    $(tabsdiv).jqxTabs({ width: 600, height: 270});
                }
            }
            var dataAdapter = new $.jqx.dataAdapter(source);

            $("#jqxgrid").jqxGrid(
            {
                width: 1000,
                height: 500,
                source: dataAdapter,
                rowdetails: true,
                rowdetailstemplate: { rowdetails: "<div style='margin: 10px;'><ul style='margin-left: 30px;'><li class='title'></li><li>Notes</li></ul><div class='information'></div><div class='notes'></div></div>", rowdetailsheight: 200 },
               
                initrowdetails: initrowdetails,
                columns: [
                      { text: 'Visitor Name', datafield: 'firstname', width: 200 },
                      { text: 'Mobile', datafield: 'lastname', width: 200 },
                      { text: 'Company', datafield: 'title', width: 180 },
                      { text: 'Department', datafield: 'city', width: 200 },
                      { text: 'Country', datafield: 'country', width: 240 }
                  ]
            });
	},
	   error: function() {
	        alert("error");
	    }
	 });
    </script>
</head>
<body class='default'>
	<div class="list-icon">
		<div class="head">Visitor Record</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
	</div>
    <div id='visitorallreportid' style="font-size: 13px; font-family: Verdana; float: left;">
        <div id="jqxgrid" class="jqx-grid jqx-reset jqx-rc-all jqx-widget jqx-widget-content jqx-disableselect" align="center" style="width: 983px; height: 480px;" role="grid"></div>
    </div>
    
</body>
</html>