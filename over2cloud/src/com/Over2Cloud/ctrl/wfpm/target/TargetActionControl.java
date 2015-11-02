package com.Over2Cloud.ctrl.wfpm.target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TargetActionControl extends ActionSupport
{

	Map																	session					= ActionContext
																													.getContext()
																													.getSession();
	String															userName				= (String) session
																													.get("uName");
	String															accountID				= (String) session
																													.get("accountid");
	SessionFactory											connectionSpace	= (SessionFactory) session
																													.get("connectionSpace");
	private Map<Integer, String>				empDataList			= null;
	private Map<Integer, String>				mapKPIList			= null;
	private Map<Integer, String>				mapOfferingList	= null;
	private String											empID;
	private String											empName;
	private String											mobileno;
	private String											empDesg;
	private String											header;
	private String											targetvalue;
	private String											targetInPercent;
	private String											offeringTargetValues;
	private String											kpiIds;
	private String											targetMonth;
	private String											offeringId;
	private String											targetOn;
	// view work of target
	private List<GridDataPropertyView>	mappedFields		= new ArrayList<GridDataPropertyView>();
	// GRID OPERATION
	private Integer											rows						= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page						= 0;
	// sorting order - asc or desc
	private String											sord						= "";
	// get index row - i.e. user click to sort.
	private String											sidx						= "";
	// Search Field
	private String											searchField			= "";
	// The Search String
	private String											searchString		= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String											searchOper			= "";
	// Your Total Pages
	private Integer											total						= 0;
	// All Record
	private Integer											records					= 0;
	private boolean											loadonce				= false;
	// Grid colomn view
	private String											oper;
	private int													id;
	private List<Object>								gridModel;
	private String											headerName;
	private String											offeringLevel		= session.get(
																													"offeringLevel")
																													.toString();

	public String beforeTargetAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			empDataList = new HashMap<Integer, String>();
			empDataList = CommonWork.allEmpList(connectionSpace);
			setHeader(getHeader());
		    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String mapTarget()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			mapKPIList = new HashMap<Integer, String>();
			mapOfferingList = new LinkedHashMap<Integer, String>();
			StringBuilder query = new StringBuilder("");
			StringBuilder kpiIds = new StringBuilder("");
			StringBuilder kpiIdsTemp = new StringBuilder("");
			query
					.append("select eb.empName,eb.mobOne,de.designationname from employee_basic as eb inner join designation as de on eb.designation=de.id where eb.id="
							+ getEmpID() + "");
			//System.out.println(">>>>>>>>>>>Emp Query : " + query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					if (obdata[0] != null) empName = obdata[0].toString();
					if (obdata[1] != null) mobileno = obdata[1].toString();
					if (obdata[2] != null) empDesg = obdata[2].toString();
				}
			}
			query.delete(0, query.length());
			query
					.append("select KPIId from krakpimap where empID=" + getEmpID() + "");
			data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			//System.out.println("KPI ID " + query.toString());
			String tempKPIIds[] = null;
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					tempKPIIds = obdata.toString().split(", ");
				}
				int i = 0;
				if (tempKPIIds != null)
				{
					for (String H : tempKPIIds)
					{
						if (i < tempKPIIds.length - 1)
						{
							kpiIds.append("'" + H + "',");
							kpiIdsTemp.append(H + ",");
						}
						else
						{
							kpiIds.append("'" + H + "'");
							kpiIdsTemp.append(H);
						}
						i++;
					}
					query.delete(0, query.length());
					query.append("select id,kpi from krakpicollection where id in("
							+ kpiIds.toString() + ")");
					data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							mapKPIList.put((Integer) obdata[0], obdata[1].toString());
						}
					}
				}
				this.kpiIds = kpiIdsTemp.toString();
				String offering[] = session.get("offeringLevel").toString().split("#");

				// String q =
				// "select id,subvariantname from offeringlevel"+offering[0].trim();
				if (offering[0] != null)
				{
					String q = " from offeringlevel" + offering[0].trim();

					if (offering[0].equalsIgnoreCase("1")) q = "select id, verticalname"
							+ q;
					else if (offering[0].equalsIgnoreCase("2")) q = "select id, offeringname"
							+ q;
					else if (offering[0].equalsIgnoreCase("3")) q = "select id, subofferingname"
							+ q;
					else if (offering[0].equalsIgnoreCase("4")) q = "select id, variantname"
							+ q;
					else if (offering[0].equalsIgnoreCase("5")) q = "select id, subvariantname"
							+ q;

					data = cbt.executeAllSelectQuery(q, connectionSpace);

					if (data != null)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							mapOfferingList.put((Integer) object[0], object[1].toString());
						}
					}
				}
				return SUCCESS;
			}
			else
			{
				addActionError("No Mapped KPI!!!");
				return ERROR;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ooops there is some problem!!!");
			return ERROR;
		}

	}

	public String alloteTargetToEmploee()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			boolean status = false;
			boolean offeringSelect = false;

			if (getTargetOn().trim().equalsIgnoreCase("offering"))
			{
				offeringSelect = true;
			}
			if (getKpiIds() != null && !getKpiIds().equalsIgnoreCase("")
					&& getEmpID() != null && !getEmpID().equalsIgnoreCase(""))
			{
				String kpi = getKpiIds().replaceAll(",", "#").replaceAll("\\s", "")
						+ "#";
				String targetValue = getTargetvalue().replaceAll(",", "#").replaceAll(
						"\\s", "")
						+ "#";

				String offeringId = "";
				String offeringValue = "";

				if (offeringSelect)
				{
					offeringId = getOfferingId().replaceAll(",", "#").replaceAll("\\s",
							"")
							+ "#";
					offeringValue = getOfferingTargetValues().replaceAll(",", "#")
							.replaceAll("\\s", "");

					StringTokenizer tokenizer = new StringTokenizer(offeringValue, "#");
					offeringValue = "";
					while (tokenizer.hasMoreTokens())
					{
						offeringValue += tokenizer.nextToken() + "#";
					}
				}

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1 = null;

				ob1 = new TableColumes();
				ob1.setColumnname("empID");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				if (!offeringSelect)
				{
					ob1 = new TableColumes();
					ob1.setColumnname("KPIId");
					ob1.setDatatype("text");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
				}
				else
				{
					ob1 = new TableColumes();
					ob1.setColumnname("offeringId");
					ob1.setDatatype("text");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
				}

				ob1 = new TableColumes();
				ob1.setColumnname("targetvalue");
				ob1.setDatatype("text");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("targetMonth");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				if (!offeringSelect)
				{
					cbt.createTable22("target", Tablecolumesaaa, connectionSpace);
				}
				else
				{
					cbt.createTable22("offeringTarget", Tablecolumesaaa, connectionSpace);
				}
				boolean exist = true;
				int mappID = 0;
				StringBuilder query = new StringBuilder("");

				if (!offeringSelect)
				{
					query.append("select id from target where empID=" + getEmpID() + "");
				}
				else
				{
					query.append("select id from offeringtarget where empID="
							+ getEmpID() + "");
				}
				List data = cbt
						.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						mappID = (Integer) obdata;
						exist = false;
					}
				}
				if (exist)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					ob = new InsertDataTable();
					ob.setColName("empID");
					ob.setDataName(getEmpID());
					insertData.add(ob);

					if (!offeringSelect)
					{
						ob = new InsertDataTable();
						ob.setColName("KPIId");
						ob.setDataName(kpi.toString());
						insertData.add(ob);
						ob = new InsertDataTable();

						ob = new InsertDataTable();
						ob.setColName("targetvalue");
						ob.setDataName(targetValue);
						insertData.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(offeringId.toString());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("targetvalue");
						ob.setDataName(offeringValue);
						insertData.add(ob);
					}

					ob = new InsertDataTable();
					ob.setColName("targetMonth");
					ob.setDataName(getTargetMonth());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					if (!offeringSelect)
					{
						status = cbt.insertIntoTable("target", insertData, connectionSpace);
					}
					else
					{
						status = cbt.insertIntoTable("offeringTarget", insertData,
								connectionSpace);
					}

				}
				else
				{
					// update employee mapping

					if (mappID != 0)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();

						if (!offeringSelect)
						{
							wherClause.put("KPIId", kpi.toString());
							wherClause.put("targetvalue", targetValue.toString());
						}
						else
						{
							wherClause.put("offeringId", offeringId.toString());
							wherClause.put("targetvalue", offeringValue.toString());
						}

						wherClause.put("targetMonth", getTargetMonth());
						wherClause.put("date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("time", DateUtil.getCurrentTime());
						condtnBlock.put("id", mappID);

						if (!offeringSelect)
						{
							status = cbt.updateTableColomn("target", wherClause, condtnBlock,
									connectionSpace);
						}
						else
						{
							status = cbt.updateTableColomn("offeringtarget", wherClause,
									condtnBlock, connectionSpace);
						}
					}
				}
			}
			if (status)
			{
				addActionMessage("Target Mapped Successfully!!!");
				return SUCCESS;
			}
			else
			{
				addActionMessage("Oops There is some error in data!");
				return SUCCESS;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ooops there is some problem Exception!!!");
			return ERROR;
		}
	}

	public String beforeTargetGridView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setGridColomnNames();
			headerName = " Target >> View";

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setGridColomnNames()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		mappedFields.add(gpv);
		gpv = new GridDataPropertyView();
		gpv.setColomnName("empname");
		gpv.setHeadingName("Emp Name");
		mappedFields.add(gpv);
		gpv = new GridDataPropertyView();
		gpv.setColomnName("targetMonth");
		gpv.setHeadingName("Target Month");
		mappedFields.add(gpv);
		Map<String, String> dataList = allMappedtargetKPIName();
		for (Map.Entry<String, String> entry : dataList.entrySet())
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName(entry.getKey());
			gpv.setHeadingName(entry.getValue());
			mappedFields.add(gpv);
		}
	}

	public Map<String, String> allMappedtargetKPIName()
	{
		Map<String, String> dataList = new LinkedHashMap<String, String>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			String offeringLavel = "";
			offeringLavel = session.get("offeringLevel").toString();
			// //System.out.println(">>>>>>>>>>>Target On : "+ getTargetOn());
			/*
			 * if(targetOn == null) { targetOn = "kpi"; }
			 */
			if (targetOn.equalsIgnoreCase("offering"))
			{
				query.append("select offeringId from offeringtarget where empID="
						+ getEmpID() + "");
			}
			else
			{
				query.append("select KPIId from target where empID=" + getEmpID() + "");
			}

			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			String tempKPIds[] = null;
			StringBuilder kpi = new StringBuilder();
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					tempKPIds = obdata.toString().split("#");
				}

				int i = 0;
				if (tempKPIds != null)
				{
					for (String H : tempKPIds)
					{
						if (i < tempKPIds.length - 1) kpi.append("'" + H + "',");
						else kpi.append("'" + H + "'");
						i++;
					}
				}
				query.delete(0, query.length());
				String offering[] = offeringLavel.split("#");
				if (getTargetOn().equalsIgnoreCase("offering"))
				{
					// Start: Fetch Offering names and client name
					int level = 0;
					String[] oLevels = null;

					if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
					{
						oLevels = offeringLevel.split("#");
						level = Integer.parseInt(oLevels[0]);
					}

					String tableName = "", colName = "";
					if (level == 1)
					{
						tableName = "offeringlevel1";
						colName = "verticalname";
					}
					if (level == 2)
					{
						tableName = "offeringlevel2";
						colName = "offeringname";
					}
					if (level == 3)
					{
						tableName = "offeringlevel3";
						colName = "subofferingname";
					}
					if (level == 4)
					{
						tableName = "offeringlevel4";
						colName = "variantname";
					}
					if (level == 5)
					{
						tableName = "offeringlevel5";
						colName = "subvariantname";
					}

					query.append("select id,"+colName+" from "
							+ tableName + " where id in(" + kpi.toString() + ")");
				}
				else
				{
					query.append("select id,kpi from krakpicollection where id in("
							+ kpi.toString() + ")");
				}
				data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						dataList.put(Integer.toString((Integer) obdata[0]), obdata[1]
								.toString());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public String targetGridView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("id");
			fieldNames.add("empname");
			fieldNames.add("targetMonth");
			Map<String, String> dataList = allMappedtargetKPIName();
			for (Map.Entry<String, String> entry : dataList.entrySet())
			{

				fieldNames.add(entry.getKey());
			}
			StringBuilder query = new StringBuilder("");
			setRecords(1);
			int to = (getRows() * getPage());
			int from = to - getRows();
			if (to > getRecords()) to = getRecords();

			if (getTargetOn().equalsIgnoreCase("offering"))
			{
				query
						.append(" select t.id,eb.empname,t.targetMonth,t.targetvalue from offeringtarget as t inner join employee_basic as eb on eb.id=t.empID where  t.empID='"
								+ getEmpID() + "'");
			}
			else
			{
				query
						.append(" select t.id,eb.empname,t.targetMonth,t.targetvalue from target as t inner join employee_basic as eb on eb.id=t.empID where  t.empID='"
								+ getEmpID() + "'");
			}
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			List<Object> Listhb = new ArrayList<Object>();
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					int i = 0;
					Object[] obdata = (Object[]) it.next();
					Map<String, Object> one = new HashMap<String, Object>();
					for (int k = 0; k < fieldNames.size(); k++)
					{

						try
						{
							if (i < 3 && obdata[k] != null)
							{
								if (k == 0) one.put(fieldNames.get(k).toString(),
										(Integer) obdata[k]);
								else one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
							else
							{
								String tempValue[] = obdata[k].toString().split("#");
								for (String H : tempValue)
								{
									if (H != null && !H.equalsIgnoreCase(""))
									{
										one.put(fieldNames.get(k).toString(), H);
									}
									k++;
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						i++;
					}
					Listhb.add(one);
				}
				setGridModel(Listhb);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Map<Integer, String> getMapKPIList()
	{
		return mapKPIList;
	}

	public void setMapKPIList(Map<Integer, String> mapKPIList)
	{
		this.mapKPIList = mapKPIList;
	}

	public Map<Integer, String> getEmpDataList()
	{
		return empDataList;
	}

	public void setEmpDataList(Map<Integer, String> empDataList)
	{
		this.empDataList = empDataList;
	}

	public String getEmpID()
	{
		return empID;
	}

	public void setEmpID(String empID)
	{
		this.empID = empID;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getMobileno()
	{
		return mobileno;
	}

	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}

	public String getEmpDesg()
	{
		return empDesg;
	}

	public void setEmpDesg(String empDesg)
	{
		this.empDesg = empDesg;
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}

	public String getTargetvalue()
	{
		return targetvalue;
	}

	public void setTargetvalue(String targetvalue)
	{
		this.targetvalue = targetvalue;
	}

	public String getKpiIds()
	{
		return kpiIds;
	}

	public void setKpiIds(String kpiIds)
	{
		this.kpiIds = kpiIds;
	}

	public String getTargetMonth()
	{
		return targetMonth;
	}

	public void setTargetMonth(String targetMonth)
	{
		this.targetMonth = targetMonth;
	}

	public List<GridDataPropertyView> getMappedFields()
	{
		return mappedFields;
	}

	public void setMappedFields(List<GridDataPropertyView> mappedFields)
	{
		this.mappedFields = mappedFields;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getHeaderName()
	{
		return headerName;
	}

	public void setHeaderName(String headerName)
	{
		this.headerName = headerName;
	}

	public List<Object> getGridModel()
	{
		return gridModel;
	}

	public void setGridModel(List<Object> gridModel)
	{
		this.gridModel = gridModel;
	}

	public Map<Integer, String> getMapOfferingList()
	{
		return mapOfferingList;
	}

	public void setMapOfferingList(Map<Integer, String> mapOfferingList)
	{
		this.mapOfferingList = mapOfferingList;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferingTargetValues()
	{
		return offeringTargetValues;
	}

	public void setOfferingTargetValues(String offeringTargetValues)
	{
		this.offeringTargetValues = offeringTargetValues;
	}

	public String getTargetInPercent()
	{
		return targetInPercent;
	}

	public void setTargetInPercent(String targetInPercent)
	{
		this.targetInPercent = targetInPercent;
	}

	public String getTargetOn()
	{
		return targetOn;
	}

	public void setTargetOn(String targetOn)
	{
		this.targetOn = targetOn;
	}
}
