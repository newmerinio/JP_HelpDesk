package com.Over2Cloud.ctrl.asset.NetworkMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class InsertMachineInfo extends ActionSupport
{
	private String ip;
	private String osName;
	private String manufacturer;
	private String model;
	private String bios;
	private String dir;
	private String timezone;
	private String osinstall;
	private String osver;
	private String domain;
	private String serial;
	private String hdpart;
	private String ram;
	private String tempsize;
	private String prcess;
	private String swin;
	private String id;
	
	
	@SuppressWarnings("unchecked")
	public String insertDeviceInfoByURL()
	{
		try
		{
			//String abc="http://192.168.1.12:8080/over2cloud/deviceInfo?ip=192.168.1.12&osName=wnidos&manufacturer=manufacturer&model=model&bios=bios&dir=dir&timezone=timezone&osinstall=osinstall&osver=osver&domain=localhost&serial=123456&hdpart=abc&ram=4&prcess=test&tempsize=15&swin=test&id=test";
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			
			
			ob = new InsertDataTable();
			ob.setColName("ip");
			ob.setDataName(ip);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("os_name");
			ob.setDataName(osName);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("systemManufacturer");
			ob.setDataName(manufacturer);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("systemModel");
			ob.setDataName(model);
			insertData.add(ob);
		
			ob = new InsertDataTable();
			ob.setColName("BIOSVersion");
			ob.setDataName(bios);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("windowsDirectory");
			ob.setDataName(dir);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("timeZone");
			ob.setDataName(timezone);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("OSInstallDate");
			ob.setDataName(osinstall);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("OSVersion");
			ob.setDataName(osver);
			insertData.add(ob);
		
			ob = new InsertDataTable();
			ob.setColName("domain");
			ob.setDataName(domain);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("hd_serialno");
			ob.setDataName(serial);
			insertData.add(ob);
		
			ob = new InsertDataTable();
			ob.setColName("hd_partitaion");
			ob.setDataName(hdpart);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("ram_details");
			ob.setDataName(ram);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("process_list");
			ob.setDataName(prcess);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("temp_file_size");
			ob.setDataName(tempsize);
			insertData.add(ob);
			
			ob = new InsertDataTable();
			ob.setColName("software_inventory");
			ob.setDataName(swin);
			insertData.add(ob);
		
			ob = new InsertDataTable();
			ob.setColName("machine_id");
			ob.setDataName(id);
			insertData.add(ob);
			
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-9");
			List dataList=new ArrayList();
			String query=null;
			query="SELECT id FROM asset_detail WHERE serialno='"+id+"'";
			dataList=new createTable().executeAllSelectQuery(query, connection);
			if(dataList!=null && dataList.size()>0)
			{
				dataList.clear();
				query="SELECT id FROM machine_details WHERE machine_id='"+id+"'";
				dataList=new createTable().executeAllSelectQuery(query, connection);
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				if(dataList!=null && dataList.size()>0)
				{
					condtnBlock.put("machine_id", "'"+getId()+"'");
					wherClause.put("ip", ip);
					wherClause.put("os_name",osName);
					wherClause.put("systemManufacturer", manufacturer);
					wherClause.put("systemModel",model);
					wherClause.put("BIOSVersion", bios);
					wherClause.put("windowsDirectory",dir);
					wherClause.put("timeZone",timezone);
					wherClause.put("OSInstallDate",osinstall);
					wherClause.put("OSVersion", osver);
					wherClause.put("domain",domain);
					wherClause.put("hd_serialno", serial);
					wherClause.put("hd_partitaion",hdpart);
					wherClause.put("ram_details", ram);
					wherClause.put("process_list",prcess);
					wherClause.put("temp_file_size", tempsize);
					wherClause.put("software_inventory",swin);
					
					status = new createTable().updateTableColomn("machine_details", wherClause, condtnBlock, connection);
				}
				else
				{
					status=new createTable().insertIntoTable("machine_details", insertData, connection);
				}
				if(status)
				{
					addActionMessage("Data Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			else
			{
				addActionMessage("Wrong Machine Id");
				return ERROR;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	
	
	
	
	
	
	
	public String getIp()
	{
		System.out.println(ip);
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getOsName()
	{
		System.out.println(osName);
		return osName;
	}
	public void setOsName(String osName)
	{
		this.osName = osName;
	}
	public String getManufacturer()
	{
		System.out.println(manufacturer);
		return manufacturer;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	public String getModel()
	{
		System.out.println(model);
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getBios()
	{
		System.out.println(bios);
		return bios;
	}
	public void setBios(String bios)
	{
		this.bios = bios;
	}
	public String getDir()
	{
		System.out.println(dir);
		return dir;
	}
	public void setDir(String dir)
	{
		this.dir = dir;
	}
	public String getTimezone()
	{
		System.out.println(timezone);
		return timezone;
	}
	public void setTimezone(String timezone)
	{
		this.timezone = timezone;
	}
	public String getOsinstall()
	{
		System.out.println(osinstall);
		return osinstall;
	}
	public void setOsinstall(String osinstall)
	{
		this.osinstall = osinstall;
	}
	public String getOsver()
	{
		System.out.println(osver);
		return osver;
	}
	public void setOsver(String osver)
	{
		this.osver = osver;
	}
	public String getDomain()
	{
		System.out.println(domain);
		return domain;
	}
	public void setDomain(String domain)
	{
		this.domain = domain;
	}
	public String getSerial()
	{
		System.out.println(serial);
		return serial;
	}
	public void setSerial(String serial)
	{
		this.serial = serial;
	}
	public String getHdpart()
	{
		System.out.println(hdpart);
		return hdpart;
	}
	public void setHdpart(String hdpart)
	{
		this.hdpart = hdpart;
	}
	public String getRam()
	{
		System.out.println(ram);
		return ram;
	}
	public void setRam(String ram)
	{
		this.ram = ram;
	}
	public String getTempsize()
	{
		System.out.println(tempsize);
		return tempsize;
	}
	public void setTempsize(String tempsize)
	{
		this.tempsize = tempsize;
	}
	public String getPrcess()
	{
		System.out.println(prcess);
		return prcess;
	}
	public void setPrcess(String prcess)
	{
		this.prcess = prcess;
	}
	public String getSwin()
	{
		System.out.println(swin);
		return swin;
	}
	public void setSwin(String swin)
	{
		this.swin = swin;
	}
	public String getId()
	{
		System.out.println(id);
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	
	
	
	
}
