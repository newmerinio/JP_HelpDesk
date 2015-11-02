<%@ taglib prefix="s" uri="/struts-tags" %>
<s:select 
                            cssClass="select"
                            cssStyle="width: 260px;"
                            id="categoryId5"
                            name="category" 
                            list="feedbackCategoryColumns" 
                            headerKey="-1"
                            onchange="getFeedSubCategoryFeedback(this.value,'subCategoryDiv3');"
                            headerValue="Category">
                  </s:select>