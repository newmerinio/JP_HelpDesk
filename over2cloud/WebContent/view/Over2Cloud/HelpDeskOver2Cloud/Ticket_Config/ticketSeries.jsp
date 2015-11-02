<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/ticketSeries.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Ticket Series</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="height: 180px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:hidden  id="dataFor" value="%{dataFor}"/>
	<s:form id="formone" name="formone" action="addTicketSeries" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden name="dataFor" value="%{dataFor}"/>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>          
          
       <s:iterator value="pageFieldsColumns" status="status">
          
       			 <s:if test="key=='ticket_type'">
       			 	<s:if test="%{mandatory}">
          				<span id="mandatoryFields" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          				<span id="mandatoryFields" class="normalpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          			</s:if>
	       			 <div class="newColumn">
		       			 <div class="leftColumn"><s:property value="%{value}"/>:</div>
		       			 <div class="rightColumn">
		       			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                  <s:select 
			                              id="%{key}"
			                              name="%{key}"
			                              list="#{'D' : 'Departmentwise'}"
		                                  headerKey="N"
		                                  headerValue="Normal" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              onchange="getTicketType(this.value,'nseries','dseries1','dddSE','prefixxx','floor');"
			                              >
			                  </s:select>
						</div>
					</div>
				</s:if>
				<s:elseif test="key=='sub_type_id'">
				    <s:if test="%{mandatory}">
          				<span id="mandatoryFields" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          			</s:if>
				   <div id="dseries1" style="display :none">
				   <div class="clear"></div>
					     <div class="newColumn">
			              <div class="leftColumn"><s:property value="%{value}"/>:</div>
							  <div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                   <s:select 
				                              id="%{key}"
				                              name="deptName"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Contact Sub Type" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="getPrefix(this.value,'dataFor');"
				                              >
				                   </s:select>
				              </div>
			              </div>
		           </div>
               </s:elseif>
               <s:elseif test="key=='d_series'">
              		<s:if test="%{mandatory}">
         				<span id="mandatoryFields" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
         			</s:if>
                   <div id="dddSE" style="display :none">
                   <div class="clear"></div>
		               <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn">
		                  		     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
				                </div>
		               </div>
		           </div>
                </s:elseif>
       			 <s:elseif test="key=='n_series'">
       			 <s:if test="%{mandatory}">
          				<span id="mandatoryFields" class="normalpIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          			</s:if>
       			    <div id="nseries" style="display :block;"> 
	       			    <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
						     </div>
						</div>
					</div>
				</s:elseif>
                <s:elseif test="key=='prefix'">
                <s:if test="%{mandatory}">
          				<span id="mandatoryFields" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          			</s:if>
                  <div id="prefixxx" style="display :none;"> 
	                <div class="newColumn">
			                <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
							<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							    <s:textfield name="%{key}" readonly="true"  id="%{key}"  cssClass="textField" placeholder="Enter Prefix" />
							</div>
						 </div>
				  </div>
               </s:elseif>
                <s:elseif test="key=='floor_status'">
                <s:if test="%{mandatory}">
          				<span id="mandatoryFields" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          			</s:if>
                  <div id="floor" style="display :none;"> 
	                <div class="newColumn">
			                <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
							<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							    <s:select 
		                              id="%{key}"
		                              name="%{key}"
		                              list="#{'N' : 'No Need','F' : 'Floorwise','B' : 'Bedwise'}"
		                              cssClass="select"
	                                  cssStyle="width:82%">
		                        </s:select>
							</div>
						 </div>
				  </div>
               </s:elseif>
               <s:else>
                   <div class="newColumn">
			                <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
							<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							    <s:textfield name="%{key}"   id="%{key}"  cssClass="textField" placeholder="Enter %{key}" />
							</div>
						 </div>
               
               </s:else>
            </s:iterator>
            <div class="clear"></div>
		    <center>
				<ul>
					<li class="submit" style="background:none;">
						<div class="type-button">
		                <sj:submit 
		                        targets="target1" 
		                        clearForm="true"
		                        value=" Save " 
		                        button="true"
		                        cssClass="submit"
		                        onCompleteTopics="beforeFirstAccordian"
		                        onBeforeTopics="validateTicketSeries"
		                        cssStyle="margin-left: -40px;"
		                        />
		            <sj:a cssStyle="margin-left: 175px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
		            <sj:a cssStyle="margin-left: 2px;margin-top: -45px;" button="true" href="#" onclick="backForm('dataFor');">Back</sj:a>
		                        
		               </div>
		               </li>
			     </ul>
		       <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>
		   </center>
     </s:form>
</div>
</div>
</body>
</html>
