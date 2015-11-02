<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
              <div class="clear"></div>
              <s:iterator value="subDept_DeptLevelName" status="status" begin="0" end="0">
	                 <s:if test="%{mandatory}">
	                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	                 </s:if>
                 	 <s:if test="#status.odd">
                          <div class="newColumn">
						  <div class="leftColumn"><s:property value="%{value}"/>:</div>
						  <div class="rightColumn">
                          <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                          <s:select  
	                          id="%{key}"
	                          name="%{key}"
	                          list="contMappedDeptList"
	                          headerKey="-1"
	                          headerValue="--Select Department--" 
	                          cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="getEmployeeBydeptId(this.value,'employeeid')"
                          >
                         </s:select>
                         </div>
                         </div>    
              		</s:if>
              	</s:iterator>
	 			
	 			<s:iterator value="commonDDMap">
	 	          <s:if test="%{mandatory}">
          		  <span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
	 			    <div class="newColumn">
						<div class="leftColumn"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
            		            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                        <s:select 
                          cssClass="select"
                          cssStyle="width:82%"
                          id="%{key}"
                          name="employeeid" 
                          list="{'No Data'}" 
                          headerKey="-1"
                          headerValue="Select Employee Name" 
                          onchange="getEmpMob(this.value,'EmpMobDiv1');"
                          > 
                        </s:select>
                        </div>
               		    </div>
	           </s:iterator>
                <div class="newColumn">
							<div class="leftColumn">Mobile No:</div>
							<div class="rightColumn">
                            <div id="AjaxDivMohb">
                            <s:textfield name="empMobileNo" id="EmpMobDiv1" disabled="true"  cssClass="textField" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
                            </div>
                            </div>
		        </div>
	 		 		  
			     
			     <s:iterator value="allotmentColumnMap" status="status" begin="0" end="1">
             	 <s:if test="%{mandatory}">
               		  <span id="reallot" class="reallotPIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	 </s:if>
				 	<s:if test="#status.odd">
					  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}"maxlength="50"  placeholder="Enter Data"  cssClass="textField"/>
	                            </div>
	                  </div>
					</s:if>
					<s:else>
					   <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data"  cssClass="textField"/>
					            </div>
					  </div>
				 	</s:else>
				 </s:iterator>
				 
				 <s:if test="%{locationMap.isEmpty()}">
				</s:if>
				<s:else>
					<s:iterator value="locationMap" begin="0" end="1" status="status">
	 	          <s:if test="%{mandatory}">
          		  	<span id="normal" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>
                  <s:if test="#status.odd">
	 			    <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
              		            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              id="%{key}"
	                              list="floorList" 
	                              headerKey="-1"
	                              headerValue="Select Floor Name" 
	                              onchange="commonSelectAjaxCall('buddy_sub_floor_info','id','subfloorname','floorname1',this.value,'','','subfloorname','ASC','subfloorname','Select Sub-Floor Name');"
	                              > 
	                           </s:select>
	                           </div>
	                  </div>
	                  </s:if>
	                  <s:else>
	                  		<div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
              		            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              id="%{key}"
	                              name="sublocation"
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Sub-Floor Name" 
	                              > 
	                           </s:select>
	                           </div>
	                  </div>
	                  </s:else>
	           </s:iterator>
				</s:else>
				
				 <s:iterator value="allotmentColumnMap" status="status" begin="2">
             	 <s:if test="%{mandatory}">
               		  <span id="reallot" class="reallotPIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	 </s:if>
				 	<s:if test="#status.odd">
					  <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}"maxlength="50"  placeholder="Enter Data"  cssClass="textField"/>
	                            </div>
	                  </div>
					</s:if>
					<s:else>
					   <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  		    <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data"  cssClass="textField"/>
					            </div>
					  </div>
				 	</s:else>
				 </s:iterator>
				
				 
				 <s:iterator value="dateColumnMap" status="status">
				 <s:if test="%{mandatory}">
               		  <span id="reallot" class="reallotPIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
						<s:if test="#status.odd">
							 <div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							    <sj:datepicker name="%{key}" id="%{key}" minDate="0" readonly="true" showOn="focus" displayFormat="dd-mm-yy"  cssClass="textField" placeholder="Select Date"/>
								</div>
							</div>
						</s:if>
						<s:else>
							<div class="newColumn">
								<div class="leftColumn"><s:property value="%{value}"/>:</div>
								<div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<s:if test="key=='issueat'">
								<sj:datepicker name="%{key}" id="%{key}" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridMinute="10"  cssClass="textField" placeholder="Select Time"/>
								</s:if>
								<s:else>
								<sj:datepicker name="%{key}" id="%{key}" readonly="true" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridMinute="10"  cssClass="textField" placeholder="Select Time"/>
								</s:else>
								</div>
							</div>
						
					</s:else>
				</s:iterator>
</body>
</html>