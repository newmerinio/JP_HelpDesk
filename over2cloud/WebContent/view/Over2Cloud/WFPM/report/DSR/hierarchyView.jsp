<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<SCRIPT type="text/javascript">
	function formatLink(cellvalue, options, row) {
		return "<a href='#' onClick='javascript:openDialog("+row.id+")'>View Detail</a>";
	}

	function openDialog(id) 
	{
		$("#showDetail").html("");
		$("#showDetail").show();
		$("#showDetail").load("<%=request.getContextPath()%>/view/Over2Cloud/WFPM/report/DSR/viewDSRByUser.action?id="+id);
	}
		
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head"><s:property value="%{mainHeader}"/></div>
	</div>

<div class="container_block"><div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="hierarchyDSR" action="hierarchyDSRRecords"></s:url>
<s:url id="subHierarchyDSR" action="subHierarchyDSRRecords">
</s:url>
		<div >
	 	<div class="listviewBorder">
	 		<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
				<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
					<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a>
					<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>
					<s:property value="%{month}"/>
				</td></tr></tbody></table>
			</td><td class="alignright printTitle">
					
						<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
					    <sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importAsset();">Import</sj:a>
                        <sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
					
				</td>   
				</tr></tbody></table></div>
			</div>
	 	</div>
		<sjg:grid
			id="hierarchyGrid"
			dataType="json"
			href="%{hierarchyDSR}" 
			pager="true" 
			navigator="true" 
			navigatorSearch="false"
			navigatorView="true" 
			navigatorAdd="false"
			navigatorDelete="false"
			navigatorEdit="false"
			gridModel="empDetail"
			rowList="10,15,20,25,30" 
			rowNum="10" 
			multiselect="true"
			loadingText="Requesting Data" 
			autowidth="true"
			>
				<sjg:grid
				id="hierarchyGrid2"
				caption="%{month}" 
				dataType="json"
				href="%{subHierarchyDSR}"
				pager="true" 
				navigator="true" 
				navigatorSearch="false"
				navigatorView="true" 
				navigatorAdd="false"
				navigatorDelete="false"
				navigatorEdit="false"
				gridModel="empDetail"
				rowList="10,15,20,25,30" 
				rowNum="10" 
				multiselect="true"
				loadingText="Requesting Data" 
				width="850"
				>
					<sjg:grid
						id="hierarchyGrid3"
						caption="%{month}" 
						dataType="json"
						href="%{subHierarchyDSR}" 
						pager="true" 
						navigator="true" 
						navigatorSearch="false"
						navigatorView="true" 
						navigatorAdd="false"
						navigatorDelete="false"
						navigatorEdit="false"
						gridModel="empDetail"
						rowList="10,15,20,25,30" 
						rowNum="10" 
						multiselect="true"
						loadingText="Requesting Data" 
						width="850"
						>
						<sjg:grid
							id="hierarchyGrid4"
							caption="%{month}" 
							dataType="json"
							href="%{subHierarchyDSR}" 
							pager="true" 
							navigator="true" 
							navigatorSearch="false"
							navigatorView="true" 
							navigatorAdd="false"
							navigatorDelete="false"
							navigatorEdit="false"
							gridModel="empDetail"
							rowList="10,15,20,25,30" 
							rowNum="10" 
							multiselect="true"
							loadingText="Requesting Data" 
							width="850"
							>
								<sjg:grid
									id="hierarchyGrid5"
									caption="%{month}" 
									dataType="json"
									href="%{subHierarchyDSR}" 
									pager="true" 
									navigator="true" 
									navigatorSearch="false"
									navigatorView="true" 
									navigatorAdd="false"
									navigatorDelete="false"
									navigatorEdit="false"
									gridModel="empDetail"
									rowList="10,15,20,25,30" 
									rowNum="10" 
									multiselect="true"
									loadingText="Requesting Data" 
									width="850"
									>
									
										<sjg:grid
											id="hierarchyGrid6"
											caption="%{month}" 
											dataType="json"
											href="%{subHierarchyDSR}" 
											pager="true" 
											navigator="true" 
											navigatorSearch="false"
											navigatorView="true" 
											navigatorAdd="false"
											navigatorDelete="false"
											navigatorEdit="false"
											gridModel="empDetail"
											rowList="10,15,20,25,30" 
											rowNum="10" 
											multiselect="true"
											loadingText="Requesting Data" 
											width="850"
											>
												<sjg:grid
													id="hierarchyGrid7"
													caption="%{month}" 
													dataType="json"
													href="%{subHierarchyDSR}" 
													pager="true" 
													navigator="true" 
													navigatorSearch="false"
													navigatorView="true" 
													navigatorAdd="false"
													navigatorDelete="false"
													navigatorEdit="false"
													gridModel="empDetail"
													rowList="10,15,20,25,30" 
													rowNum="10" 
													multiselect="true"
													loadingText="Requesting Data" 
													width="850"
													>
														<sjg:grid
															id="hierarchyGrid8"
															caption="%{month}" 
															dataType="json"
															href="%{subHierarchyDSR}" 
															pager="true" 
															navigator="true" 
															navigatorSearch="false"
															navigatorView="true" 
															navigatorAdd="false"
															navigatorDelete="false"
															navigatorEdit="false"
															gridModel="empDetail"
															rowList="10,15,20,25,30" 
															rowNum="10" 
															multiselect="true"
															loadingText="Requesting Data" 
															width="850"
															>
																<sjg:grid
																	id="hierarchyGrid9"
																	caption="%{month}" 
																	dataType="json"
																	href="%{subHierarchyDSR}" 
																	pager="true" 
																	navigator="true" 
																	navigatorSearch="false"
																	navigatorView="true" 
																	navigatorAdd="false"
																	navigatorDelete="false"
																	navigatorEdit="false"
																	gridModel="empDetail"
																	rowList="10,15,20,25,30" 
																	rowNum="10" 
																	multiselect="true"
																	loadingText="Requesting Data" 
																	width="850"
																	>
																		<sjg:grid
																			id="hierarchyGrid10"
																			caption="%{month}" 
																			dataType="json"
																			href="%{subHierarchyDSR}" 
																			pager="true" 
																			navigator="true" 
																			navigatorSearch="false"
																			navigatorView="true" 
																			navigatorAdd="false"
																			navigatorDelete="false"
																			navigatorEdit="false"
																			gridModel="empDetail"
																			rowList="10,15,20,25,30" 
																			rowNum="10" 
																			multiselect="true"
																			loadingText="Requesting Data" 
																			width="850"
																			>
																    		<sjg:gridColumn 
																					name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
																					 align="center"/>
																			<sjg:gridColumn 
																					name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
																					 align="left"/>
																			<sjg:gridColumn 
																					name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
																					formatter="formatLink" align="left"/>		 
																		</sjg:grid>
														    		<sjg:gridColumn 
																			name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
																			 align="center"/>
																	<sjg:gridColumn 
																			name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
																			 align="left"/>
																	<sjg:gridColumn 
																			name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
																			formatter="formatLink" align="left"/>		 
																</sjg:grid>	
												    		<sjg:gridColumn 
																	name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
																	 align="center"/>
															<sjg:gridColumn 
																	name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
																	 align="left"/>
															<sjg:gridColumn 
																	name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
																	formatter="formatLink" align="left"/>		 
														</sjg:grid>
										    		<sjg:gridColumn 
															name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
															 align="center"/>
													<sjg:gridColumn 
															name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
															 align="left"/>
													<sjg:gridColumn 
															name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
															formatter="formatLink" align="left"/>		 
												</sjg:grid>
								    		<sjg:gridColumn 
													name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
													 align="center"/>
											<sjg:gridColumn 
													name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
													 align="left"/>
											<sjg:gridColumn 
													name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
													formatter="formatLink" align="left"/>		 
										</sjg:grid>
						    		<sjg:gridColumn 
											name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
											 align="center"/>
									<sjg:gridColumn 
											name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
											 align="left"/>
									<sjg:gridColumn 
											name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
											formatter="formatLink" align="left"/>		 
								</sjg:grid>
				    		<sjg:gridColumn 
									name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
									 align="center"/>
							<sjg:gridColumn 
									name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
									 align="left"/>
							<sjg:gridColumn 
									name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
									formatter="formatLink" align="left"/>
						</sjg:grid>
			    		<sjg:gridColumn 
								name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
								 align="center"/>
						<sjg:gridColumn 
								name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
								 align="left"/>
						<sjg:gridColumn 
								name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
								formatter="formatLink" align="left"/>
					</sjg:grid>
	    		<sjg:gridColumn 
						name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120" hidden="true"
						 align="center"/>
				<sjg:gridColumn 
						name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
						 align="left"/>
				<sjg:gridColumn 
					name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
					formatter="formatLink" align="left"/>
			</sjg:grid>
				
				
    		<sjg:gridColumn 
					name="id" index="id" title="ID" editable="false" edittype="text" sortable="true" search="false" width="120"
					hidden="true" align="center"/>
			<sjg:gridColumn 
					name="empDetail" index="empDetail" title="Emp Name" editable="true" edittype="text" sortable="true" search="false" width="120"
					 align="left"/>
					 
			<sjg:gridColumn 
					name="index" index="Index" title="View Detail" editable="true" edittype="text" sortable="true" search="false" width="120"
					formatter="formatLink" align="left"/>
					 
	</sjg:grid>
</div></div>
<div id="showDetail" style="display: none; margin-left: -36px;">
</div></div>
</center>
</div>
</div>
</body>
</html>