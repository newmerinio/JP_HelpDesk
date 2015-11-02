package com.Over2Cloud.ctrl.wfpm.offering;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class OfferingHelper
{
	public int getOfferingLevel(Map session)
	{
		String offeringLevel = session.get("offeringLevel").toString();
		String[] oLevels = null;
		int level = 0;

		if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
		{
			oLevels = offeringLevel.split("#");
			level = Integer.parseInt(oLevels[0]);
		}
		return level;
	}

	@SuppressWarnings("unchecked")
	public static int fetchOfferingRecordCount(String tableName, SessionFactory factory)
	{
		int count = 0;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query = "select count(*) from " + tableName;
			List data = coi.executeAllSelectQuery(query, factory);
			if (data != null && !data.isEmpty())
			{
				count = Integer.parseInt(data.get(0).toString());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public int insertOfferingInToTable(String value, int dataLevel, String foreignKeyValue, SessionFactory factory)
	{
		int id = 0;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String table = null, columnn = null, foreignKey = null;
			if (dataLevel == 1)
			{
				table = "offeringlevel1";
				columnn = "verticalname";
			}
			else if (dataLevel == 2)
			{
				table = "offeringlevel2";
				columnn = "offeringname";
				foreignKey = "verticalname";
			}
			else if (dataLevel == 3)
			{
				table = "offeringlevel3";
				columnn = "subofferingname";
				foreignKey = "offeringname";
			}
			else if (dataLevel == 4)
			{
				table = "offeringlevel4";
				columnn = "variantname";
				foreignKey = "subofferingname";
			}
			else if (dataLevel == 5)
			{
				table = "offeringlevel5";
				columnn = "subvariantname";
				foreignKey = "variantname";
			}

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName(columnn);
			ob.setDataName(value);
			insertData.add(ob);

			if (foreignKeyValue != null && foreignKeyValue.equals("") && dataLevel > 1)
			{
				ob = new InsertDataTable();
				ob.setColName(foreignKey);
				ob.setDataName(foreignKeyValue);
				insertData.add(ob);
			}

			boolean status = coi.insertIntoTable(table, insertData, factory);
			if (status)
			{
				String query = "select id from " + table + " where " + columnn + " = '" + value + "' ";
				List list = coi.executeAllSelectQuery(query.toString(), factory);
				if (list != null && !list.isEmpty()) id = Integer.parseInt(list.get(0).toString().trim());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public int insertOffering(ArrayList<ArrayList<String>> offeringList, SessionFactory factory)
	{
		int count = 0;
		if (offeringList != null && !offeringList.isEmpty())
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			int level = offeringList.get(0).size();
			ArrayList<HashMap<String, String>> preLoadedOffering = new ArrayList<HashMap<String, String>>();
			if (level > 0)
			{
				String query = "select id, verticalname from offeringlevel1 group by verticalname";
				List list1 = coi.executeAllSelectQuery(query.toString(), factory);
				HashMap<String, String> map1 = CommonHelper.convertListToMap(list1, true);
				preLoadedOffering.add(map1);
			}
			if (level > 2)
			{
				String query = "select id, offeringname from offeringlevel2 group by offeringname";
				List list1 = coi.executeAllSelectQuery(query.toString(), factory);
				HashMap<String, String> map1 = CommonHelper.convertListToMap(list1, true);
				preLoadedOffering.add(map1);
			}
			if (level > 3)
			{
				String query = "select id, subofferingname from offeringlevel3 group by subofferingname";
				List list1 = coi.executeAllSelectQuery(query.toString(), factory);
				HashMap<String, String> map1 = CommonHelper.convertListToMap(list1, true);
				preLoadedOffering.add(map1);
			}
			if (level > 4)
			{
				String query = "select id, variantname from offeringlevel4 group by variantname";
				List list1 = coi.executeAllSelectQuery(query.toString(), factory);
				HashMap<String, String> map1 = CommonHelper.convertListToMap(list1, true);
				preLoadedOffering.add(map1);
			}
			if (level > 4)
			{
				String query = "select id, subvariantname from offeringlevel5 group by subvariantname ";
				List list1 = coi.executeAllSelectQuery(query.toString(), factory);
				HashMap<String, String> map1 = CommonHelper.convertListToMap(list1, true);
				preLoadedOffering.add(map1);
			}

			if (preLoadedOffering != null && !preLoadedOffering.isEmpty())
			{
				int dataLevel = 0, tempCount = 0;
				String foreignKeyValue = "", lastDataList = "";
				for (ArrayList<String> dataList : offeringList)
				{
					try
					{
						tempCount = 0;
						for (int i = 0; i < dataList.size(); i++)
						{
							dataLevel = i + 1;
							if (preLoadedOffering.get(i).containsKey(dataList.get(i)))
							{
								foreignKeyValue = preLoadedOffering.get(i).get(dataList.get(i));
							}
							else
							{
								foreignKeyValue = String.valueOf(insertOfferingInToTable(dataList.get(i), dataLevel, foreignKeyValue, factory));
								if (foreignKeyValue.equals("0"))
								{
									preLoadedOffering.get(i).put(dataList.get(i), foreignKeyValue);
									if (!lastDataList.equals(dataList.toString())) tempCount++;
								}
							}
						}
						if (tempCount > 0) count++;
						lastDataList = dataList.toString();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}// //////////////
			}
		}
		return count;
	}

	public ArrayList<ArrayList<String>> readOfferingExcel(InputStream inputStream, String fileName, String fileContentType, int offeringLevel)
	{
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		if (fileName.contains(".xlsx")) // Read data from .xlsx
		{
			GenericReadExcel7 GRE7 = new GenericReadExcel7();
			XSSFSheet excelSheet = GRE7.readExcel(inputStream);
			ArrayList<String> paramList = null;
			XSSFRow row = null;
			int cellNo = offeringLevel;
			for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
			{
				row = excelSheet.getRow(rowIndex);
				if (cellNo > 0)
				{
					paramList = new ArrayList<String>();
					for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
					{
						paramList.add(CommonHelper.getFieldValue(GRE7.readExcelSheet(row, cellIndex).toString().trim()));
					}
					list.add(paramList);
				}
			}
		}
		return list;
	}
}
