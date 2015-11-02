<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
    <script type="text/javascript" src="<s:url value="/js/feedback.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="secHead">Re-Assign To </div>

   <div class="newColumn">
     <div class="leftColumn">Department:</div>
     <div class="rightColumn">
     <span class="needed"></span>
	             <s:select 
                          id="deptname3"
                          name="todept"
                          list="serviceDeptList"
                          headerKey="-1"
                          headerValue="Select Department" 
                          cssClass="select"
      					  cssStyle="width:82%"
                          onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','subdept3','Select Sub-Department');"
                          >
                </s:select>
      </div>
      </div>
		    <div class="newColumn">
		       <div class="leftColumn">Sub-Department:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
	                  <s:select 
	                              id="subdept3"
	                              name="tosubdept"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Sub-Department" 
	                              cssClass="select"
          					      cssStyle="width:82%"
          					      onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','HDM','fbType','ASC','feedbackType','Select Feedback Type');">
	                  </s:select>
		          </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Feedback Type:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
                    <s:select 
                              id="feedbackType"
                              name="feedbackType"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Type" 
                              cssClass="select"
          					  cssStyle="width:82%"
          					  onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','feedbackCategory','Select Category');"
                              >
                    </s:select>
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn">Category:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
                    <s:select 
                              id="feedbackCategory"
                              name="feedbackCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Category" 
                              cssClass="select"
          					  cssStyle="width:82%"
          					  onchange="commonSelectAjaxCall('feedback_subcategory','id','subCategoryName','categoryName',this.value,'','','subCategoryName','ASC','feedbackSubCatg','Select Sub-Category');">
                    </s:select>
		            </div>
            </div>
            
            <div class="newColumn">
		       <div class="leftColumn">Sub Category:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
					<div id="feedTypeDiv3">
                    <s:select 
                              id="feedbackSubCatg"
                              name="feedbackSubCatgory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Sub Category" 
                              cssClass="select"
          					  cssStyle="width:82%"
                              >
                    </s:select>
                  </div>
		            </div>
            </div>
            
            
		    <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		             <span class="needed"></span>
		                   <s:textfield name="reAssignRemark" id="reAssignRemark" placeholder="Enter Re-Assign Reason"  cssClass="textField" />
		            </div>
            </div>
            </body>
</html>