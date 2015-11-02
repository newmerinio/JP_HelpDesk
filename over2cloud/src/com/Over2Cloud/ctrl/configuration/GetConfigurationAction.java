package com.Over2Cloud.ctrl.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.ListConfigbean;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;

/**
 * @author pankaj
 */
public class GetConfigurationAction extends GridPropertyBean
{

	String dbName = (String) session.get("Dbname");
	/** id name */
	private String id = null;
	/** levelId name */
	private String levelId = null;
	/** mapedtable name */
	private String mapedtable = null;
	/** active name */
	private boolean active;
	/** inactive name */
	private boolean inactive;
	/** field_value name */
	private String field_name = null;
	/** level_name name */
	private String level_name = null;
	/** field_type name */
	private String field_type = null;

	private String comonmaped = null;
	private String dataFor = null;
	/** id name */
	private static final long serialVersionUID = 1L;
	private Map<ListConfigbean, List<ConfigurationUtilBean>> dataList = new LinkedHashMap<ListConfigbean, List<ConfigurationUtilBean>>();
	private Map<String, List<ConfigurationUtilBean>> dataList1 = new LinkedHashMap<String, List<ConfigurationUtilBean>>();
	private String colType;
	private String validationType;

	@SuppressWarnings("rawtypes")
    public String GetLevelConfiguration()
	{

		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		List resultdata = null;
		String[] ListArray = null;
		String[] ListArrayTemp = null;
		String[] ListArrayTemp1 = null;
		try
		{
			setLevelId("string" + getId());
			// System.out.println("mapedtable"+mapedtable);
			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			Map<String, Object> paramMaps = new LinkedHashMap<String, Object>();
			paramMap.put("level_congtable", mapedtable);
			// all common data
			resultdata = new CommonOperAtion().getSelectTabledata("allcommontabtable", paramMap, connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();)
			{
				Object[] objectCal = (Object[]) iterator.next();
				if (objectCal[9] != null && !objectCal[9].toString().equalsIgnoreCase("") && objectCal[12] != null && !objectCal[12].toString().equalsIgnoreCase(""))
				{
					ListArray = objectCal[9].toString().split("#");
					ListArrayTemp = objectCal[12].toString().split("#");
					ListArrayTemp1 = objectCal[13].toString().split("#");
					int levlLght = Integer.parseInt(getId());
					for (int i = 0; i < levlLght; i++)
					{

						List<ConfigurationUtilBean> tempDataConfi = new ArrayList<ConfigurationUtilBean>();

						List listresultdata = new CommonOperAtion().getSelectTabledata(ListArray[i], paramMaps, connectionSpace);
						for (Object c : listresultdata)
						{
							Object[] dataC = (Object[]) c;
							ConfigurationUtilBean ctb = new ConfigurationUtilBean();
							ctb.setId(Integer.parseInt(dataC[0].toString()));
							ctb.setField_name(dataC[1].toString());
							ctb.setField_value(dataC[2].toString());
							if (dataC[11] == null)
							{
								ctb.setMandatory(false);
							}
							else if (dataC[11].toString().equalsIgnoreCase("1"))
							{
								ctb.setMandatory(true);
							}
							else if (dataC[11].toString().equalsIgnoreCase("0"))
							{
								ctb.setMandatory(false);
							}
							tempDataConfi.add(ctb);
						}
						ListConfigbean ltb = new ListConfigbean();
						ltb.setConfigTable(ListArray[i]);
						ltb.setMappedConfigLevelView(ListArrayTemp1[i]);
						ltb.setMappedConfigDataTable(ListArrayTemp[i]);
						dataList.put(ltb, tempDataConfi);
					}
				}
			}

			returnString = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnString;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String editeconfiguration()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		try
		{
		/*	*//** HashMap Object name to put Parameter *//*
			Map<String, Object> paramMaps = new HashMap<String, Object>();
		*/
			/** HashMap Object name to set WhereClouse Parameter */
			
			
		/*	List<String> data = new ArrayList<String>();
			*//** to Set table column to get value of these Parameter *//*
			data.add("mappedid");
			*//** mappedTabless name *//*
			data.add("table_name");*/
			/** mappedTabless name */
			//boolean mappedTabless = false;
			/** StringBuilder Object */
			//StringBuilder Objectdata = new StringBuilder();
			/** checked mapped table can not be null for any cases */
			/*if (getMapedtable() != null)
			{
				*//** get mapped table name *//*
				List getMappedTableForConfg = cbt.viewAllDataEitherSelectOrAll(getMapedtable(), data, setMap, connectionSpace);
				String commonmapedtabe = getMapedtable();
				if (getMappedTableForConfg != null)
				{
					for (Object c1 : getMappedTableForConfg)
					{
						Object[] dataC1 = (Object[]) c1;
						mapedtable = dataC1[1].toString().trim();
						mappedTabless = true;
					}
				}
				*//** checked level name value can not be null or blank *//*
				if (getLevel_name() != null && getField_value().equalsIgnoreCase("") && !active && !inactive)
				{
					// all common data
					setMap.put("field_name", getLevel_name());
					WhereClouse.put("id", getId());
					boolean flag = new CommonOperAtion().updateIntoTable(mapedtable, setMap, WhereClouse, connectionSpace);
					if (flag)
					{
						addActionMessage("Update Record Sucess Fully!!!");
					}
					else
					{
						addActionError("Opps There is some Problem!");
					}
				}
				if (getField_value() != null && !getField_value().equalsIgnoreCase("") && !active && !inactive)
				{
					List resultdata;
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob = new InsertDataTable();
					ob.setColName("field_value");
					ob.setDataName(getField_value().replace(' ', '_').toLowerCase());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("field_name");
					ob.setDataName(getField_value());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("colType");
					ob.setDataName(getField_type());
					insertData.add(ob);
					List<Object[]> queryResult = new CommonOperAtion().insertIntoTableReturnId(mapedtable, insertData, connectionSpace);
					boolean flag;
					if (mappedTabless)
					{
						resultdata = new CommonOperAtion().getSelectTabledata(commonmapedtabe, paramMaps, connectionSpace);
						if (resultdata != null)
						{
							for (Iterator literator = resultdata.iterator(); literator.hasNext();)
							{
								Object[] objectCalg = (Object[]) literator.next();
								String[] ArrayobjectCal = objectCalg[1].toString().split("#");

								for (int i = 0; i < ArrayobjectCal.length; i++)
								{
									Objectdata.append(ArrayobjectCal[i]);
									Objectdata.append("#");
								}
								Objectdata.append(queryResult.get(0));
								Objectdata.append("#");
								WhereClouse.put("id", objectCalg[0].toString());
								setMap.put("mappedid", Objectdata.toString());
							}
						}
						flag = new CommonOperAtion().updateIntoTable(commonmapedtabe, setMap, WhereClouse, connectionSpace);
					}
					else
					{
						Map<String, Object> paramMapss = new HashMap<String, Object>();
						paramMapss.put("table_name", mapedtable);
						resultdata = new CommonOperAtion().getSelectTabledata(comonmaped, paramMapss, connectionSpace);
						if (resultdata != null)
						{
							for (Iterator literator = resultdata.iterator(); literator.hasNext();)
							{
								Object[] objectCalg = (Object[]) literator.next();
								String[] ArrayobjectCal = objectCalg[1].toString().split("#");

								for (int i = 0; i < ArrayobjectCal.length; i++)
								{
									Objectdata.append(ArrayobjectCal[i]);
									Objectdata.append("#");
								}
								Objectdata.append(queryResult.get(0));
								Objectdata.append("#");
								WhereClouse.put("id", objectCalg[0].toString());
								setMap.put("mappedid", Objectdata.toString());
							}
						}
						flag = new CommonOperAtion().updateIntoTable(comonmaped, setMap, WhereClouse, connectionSpace);
					}
					if (flag)
					{
						addActionMessage("Update Record Sucessfully");
					}
					else
					{
						addActionError("Opps There is some Problem!");
					}
				}
			}
			if (active && getLevel_name() != null && (getField_value().equalsIgnoreCase(null) || getField_value().equalsIgnoreCase("")))
			{
				if (getId() != null)
				{
					WhereClouse.put("id", getId());
					setMap.put("activeType", "true");
					boolean flag = new CommonOperAtion().updateIntoTable(mapedtable, setMap, WhereClouse, connectionSpace);
					if (flag)
					{
						addActionMessage("Active Record SucessFully");
					}
					else
					{
						addActionError("Opps There is some Problem!");
					}

				}
			}
			if (inactive && getLevel_name() != null && (getField_value().equalsIgnoreCase(null) || getField_value().equalsIgnoreCase("")))
			{
				if (getId() != null)
				{
					WhereClouse.put("id", getId());
					setMap.put("activeType", "false");
					boolean flag = new CommonOperAtion().updateIntoTable(mapedtable, setMap, WhereClouse, connectionSpace);
					if (flag)
					{
						addActionMessage("Inactive Record SucessFully");
					}
					else
					{
						addActionError("Opps There is some Problem!");
					}
				}
			}*/
			//System.out.println("COlumn Type ::::::::"+validationType.replaceAll("-1", "").replace(",", "").trim());
			Map<String, Object> setMap = new LinkedHashMap<String, Object>();
			Map<String, Object> WhereClouse = new LinkedHashMap<String, Object>();
			WhereClouse.put("id", getId());
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			 while (it.hasNext()) 
			 {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null&& !paramValue.equalsIgnoreCase("-1")&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("colType1")&& !Parmname.equalsIgnoreCase("field_length1")&& !Parmname.equalsIgnoreCase("mapedtable")&&!Parmname.equalsIgnoreCase("comonmaped")&& !Parmname.equalsIgnoreCase("dataFor"))
					{
						if(Parmname.equalsIgnoreCase("colType"))
						{
							if(paramValue.equalsIgnoreCase("Cal"))
							{
								setMap.put("colType", request.getParameter("colType1"));
							}
							else if(paramValue.equalsIgnoreCase("F"))
							{
								setMap.put(Parmname, paramValue);
								setMap.put("field_length", request.getParameter("field_length1"));
							}
							else
							{
								setMap.put(Parmname, paramValue);
							}
						}
						else
						{
							setMap.put(Parmname, paramValue);
						}
					}
			 }
			 if(validationType!=null && !validationType.equalsIgnoreCase("-1"))
			 {
				 setMap.put("validationType", validationType.replaceAll("-1", "").replace(",", "").trim());
			 }
			 boolean flag =false;
			 CommonOperAtion cbt=new CommonOperAtion();
			 System.out.println("DATA FOR ::::: "+dataFor);
			 if(dataFor!=null && dataFor.startsWith("update"))
			 {
				 flag = cbt.updateIntoTable(mapedtable, setMap, WhereClouse, connectionSpace);
			 }
			 else  if(dataFor!=null && dataFor.startsWith("insert"))
			 {
				 	String qry="SELECT MAX(sequence) FROM "+mapedtable ;
				 	List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				    int seq = 0;
				    if(data!=null && data.size()>0)
				    {
				    	String s = data.get(0).toString();
				    	seq = Integer.parseInt(s)+1;
				    }
				    
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob = new InsertDataTable();
					ob.setColName("field_value");
					ob.setDataName(getField_name().replace(' ', '_').toLowerCase());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("data_type");
					ob.setDataName("varchar(255)");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("sequence");
					ob.setDataName(seq);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("activeType");
					ob.setDataName("true");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("add_via");
					ob.setDataName("Manual");
					insertData.add(ob);
					
					 if(setMap!=null && setMap.size()>0)
					 {
						 for (Map.Entry<String, Object> entry : setMap.entrySet())
						 {
							 	ob = new InsertDataTable();
								ob.setColName(entry.getKey());
								ob.setDataName(entry.getValue());
								insertData.add(ob);
						 }
					 }
					List returnData = cbt.insertIntoTableReturnId(mapedtable, insertData, connectionSpace);
					if(returnData!=null && returnData.size()>0)
					{
						StringBuilder mappIds = new StringBuilder();
						String query = "SELECT mappedid FROM "+comonmaped+ " WHERE table_name ='"+mapedtable+"'";
						List mappedIds = cbt.executeAllSelectQuery(query, connectionSpace);
						if(mappedIds!=null && mappedIds.size()>0)
						{
							Object obj =(Object)mappedIds.get(0);
							mappIds.append(obj.toString());
							mappIds.append(returnData.get(0).toString()+"#");
						}
						StringBuilder qryss= new StringBuilder();
						qryss.append("UPDATE "+comonmaped+ " SET mappedid = '"+mappIds.toString()+"' WHERE table_name ='"+mapedtable+"'");
						flag = new createTable().updateTableColomn(connectionSpace, qryss);
					}
					
			 }
			if (flag)
			{
				addActionMessage("Record Updated SucessFully");
			}
			else
			{
				addActionError("Opps There is some Problem!");
			}
			returnString = SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnString;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public String changeConfSetting()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				Map<String, Map<String, String>> test = new LinkedHashMap<String, Map<String, String>>();
				Map<String, String> test1 = new LinkedHashMap<String, String>();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					// System.out.println("param Name ::::"+Parmname);
					// System.out.println("Param Value:::::: "+paramValue);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !paramValue.equalsIgnoreCase("-1") && !Parmname.equalsIgnoreCase("id") && !Parmname.startsWith("_") && !Parmname.equalsIgnoreCase("mapedtable") && !Parmname.equalsIgnoreCase("mappedTableConf"))
					{
						Pattern p = Pattern.compile("(\\d+)");
						Matcher m = p.matcher(Parmname);
						while (m.find())
						{
							String abc = Parmname.substring(0, Parmname.indexOf(m.group(1)));
							if(!abc.equalsIgnoreCase("activeTypeCheck"))
							{
								if (test.keySet().contains(abc))
								{
									test1.put(m.group(1), paramValue);
									test.put(abc, test1);
								}
								else
								{
									test1 = new LinkedHashMap<String, String>();
									test1.put(m.group(1), paramValue);
									test.put(abc, test1);
								}
							}
						}
					}
				}
				
			//	System.out.println("Test is as ::: "+test.toString());
				createTable cbt = new createTable();
				StringBuilder query = new StringBuilder();
				if (test != null && test.size() > 0)
				{
					for (Map.Entry<String, Map<String, String>> entry : test.entrySet())
					{
						if (entry.getValue() != null && entry.getValue().size() > 0)
						{
							for (Map.Entry<String, String> entry1 : entry.getValue().entrySet())
							{
								query.append("UPDATE " + mapedtable + " SET " + entry.getKey() + " = '" + entry1.getValue() + "' WHERE id=" + entry1.getKey());
								cbt.updateTableColomn(connectionSpace, query);
								query.setLength(0);
							}
						}
					}
					
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String addproperty()
	{

		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		try
		{

			returnString = SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnString;

	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isInactive()
	{
		return inactive;
	}

	public void setInactive(boolean inactive)
	{
		this.inactive = inactive;
	}

	public String getField_name()
	{
		return field_name;
	}

	public void setField_name(String field_name)
	{
		this.field_name = field_name;
	}

	public String getMapedtable()
	{
		return mapedtable;
	}

	public void setMapedtable(String mapedtable)
	{
		this.mapedtable = mapedtable;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLevelId()
	{
		return levelId;
	}

	public void setLevelId(String levelId)
	{
		this.levelId = levelId;
	}

	public Map<String, List<ConfigurationUtilBean>> getDataList1()
	{
		return dataList1;
	}

	public void setDataList1(Map<String, List<ConfigurationUtilBean>> dataList1)
	{
		this.dataList1 = dataList1;
	}

	public Map<ListConfigbean, List<ConfigurationUtilBean>> getDataList()
	{
		return dataList;
	}

	public void setDataList(Map<ListConfigbean, List<ConfigurationUtilBean>> dataList)
	{
		this.dataList = dataList;
	}

	public String getLevel_name()
	{
		return level_name;
	}

	public void setLevel_name(String level_name)
	{
		this.level_name = level_name;
	}

	public String getField_type()
	{
		return field_type;
	}

	public void setField_type(String field_type)
	{
		this.field_type = field_type;
	}

	public String getComonmaped()
	{
		return comonmaped;
	}

	public void setComonmaped(String comonmaped)
	{
		this.comonmaped = comonmaped;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getColType()
    {
	    return colType;
    }

	public void setColType(String colType)
    {
	    this.colType = colType;
    }

	public String getValidationType()
    {
	    return validationType;
    }

	public void setValidationType(String validationType)
    {
	    this.validationType = validationType;
    }

}
