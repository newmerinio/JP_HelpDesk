<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="<s:url value="/js/WFPMReport/validation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('complete', function(event,data)
 {   
	/*
	 setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
	 setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
	 */
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load(encodeURI("view/Over2Cloud/wfpm/client/viewClientFullHistory.action?id="+id+"&isExistingClient="+isExistingClient+"&mainHeaderName="+mainHeaderName));
 });


</SCRIPT>
	
<title>Offering details</title>
</head>
<body>
    <s:form id="takeActionForm" action="convertClientForOffering" method="post"  name="takeActionForm" enctype="multipart/form-data" theme="css_xhtml">
    <center>
	    <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div>
	    </div>
    </center>
    <s:hidden id="clientId" value="%{clientId}" name="clientId" />
	<s:hidden id="id" value="%{id}"  name="id"/>

	      
	    <div class="clear"></div> 
	     <span id="clientSpan" class="pIds" style="display: none; ">ammount#Value#T#n,po_date#Dated#Date#,</span>
	    <div class="newColumn">
        <div class="leftColumn">Actual Value:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:textfield  id="ammount" name="ammount" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
        
        <div class="newColumn">
        <div class="leftColumn">P.O/W.O Ref.No.:</div>
        <div class="rightColumn">
        <s:textfield  id="poNumber" name="poNumber" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
        
 	     <div class="newColumn">
         <div class="leftColumn">Dated:</div>
         <div class="rightColumn"><span class="needed"></span>
         <sj:datepicker id="po_date" name="po_date" cssClass="textField" size="20" value="today" readonly="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select P.O Date"/>
         </div>
         </div>
 	    
	     <div class="newColumn">
           <div class="leftColumn">Attachment:</div>
           <div class="rightColumn">
	       <s:file name="po_attach" id="po_attach"  cssClass="textField"/></div>
		  </div>
	 				
	     <div class="newColumn">
          <div class="leftColumn">Remarks:</div>
          <div class="rightColumn">
          <s:textfield  id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
	   <div class="clear"></div>
	   <div class="clear"></div>
	   
	   <center>
             <div class="type-button">
             <sj:submit 
                        targets			   	=		"Result" 
                        clearForm			=		"true"
                        value				=		"Save" 
                        effect				=		"highlight"
                        effectOptions		=		"{color:'#222222'}"
                        effectDuration		=		"5000"
						button				=		"true"
                        onCompleteTopics	=		"complete"
                        onBeforeTopics		=		"validate1"
                        />
              </div> 
   </center>
     <sj:div id="foldeffect"  effect="fold">
            <div id="Result"></div>
     </sj:div>
  </s:form>
</body>
</html>