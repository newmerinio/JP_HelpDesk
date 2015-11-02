package com.Over2Cloud.service.clientdata.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.CommonClasses.Cryptography;

import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ClientDataIntegrationHelper{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public void checkLoginDetailHibernate(Log log, SessionFactory clientConnection, Session sessionHis) 
	 {
		 /*
		  * Step1: Picking Current day data from client database
		  */ 
		  // Simple Method call for import Data From HIS to MHDM DB...
		    getSetCurrentDayData(log, clientConnection, sessionHis);
		  /* End of Picking Current day data from client database
		  */ 
		 
		 
		  /* Case 1. Code for update Employees Basic detail on the basis of empid
		  */ 
		 HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
		 // List of Service Department Sub Dept Id's 
		 //List dept_SubdeptID = null;
		   try {
			 List<String> empId  = null;
			 Map<String, Object> parameter=null;
			 Map<String, Object> conditionBlock=null;
			 
		     String emailIdCDB ="",empIdCDB="",mobOneCDB="",empNameCDB="",designation="";
			 // List of non-service department employees data from client_data table of current date
			 empId = new ClientDataIntegrationHelper().getCurrentDateData("client_data",clientConnection);
			 if (empId!=null && empId.size()>0) 
			  {
				 for (Iterator iterator = empId.iterator(); iterator.hasNext();) 
				  {
					 Object[] object = (Object[]) iterator.next();
					 parameter=new HashMap<String, Object>();
					 conditionBlock=new HashMap<String, Object>();
					 
					 empIdCDB = object[1].toString();
					 empNameCDB = object[2].toString();
					 mobOneCDB = correctMobileNo(object[3].toString());
					 if (object[4].toString().equals("") || object[4].toString().equals("NA")) {
						  emailIdCDB = "helpdesk@blkhospital.com";
					 }
					 else {
						   String[] emailarr = null;
						   if (object[4].toString().contains(",")) {
							 emailarr = object[4].toString().split(",");
							 if (emailarr[0].trim().length()>0) {
								 emailIdCDB = emailarr[0].trim();
							 }
							 else if (emailarr[1].trim().length()>0) {
								 emailIdCDB = emailarr[1].trim();
							 }
						   }
						   else {
							 emailIdCDB = object[4].toString();
						   }
					 }
					 
					 if (object[10]!=null && !object[10].toString().equals("")) {
						  designation = object[10].toString();
					 }
					 else {
						 designation = "Not Exist";
					}
					  
					  // extensionNo = object[5].toString();
					  parameter.put("emailIdOne", emailIdCDB);
					  parameter.put("mobOne", mobOneCDB);
					  parameter.put("empName", empNameCDB);
					  parameter.put("designation", designation);
					  parameter.put("date", DateUtil.getCurrentDateUSFormat());
					  parameter.put("time", DateUtil.getCurrentTime());
					  conditionBlock.put("empId", empIdCDB);
					  
					  HUH.updateTableColomn("employee_basic", parameter, conditionBlock, clientConnection);
				 }
			 }
		}catch(Exception E) {
			E.printStackTrace();
		}
		
		
		 /* Case 2. Create User A/c for all employees once who don't have User Account	
		 */ 
		
	    try {
			// List of Employees in database who don't have User Account and go inside for creating User Account
			 List empBasicData = new ClientDataIntegrationHelper().recordCheck4UserAccount(clientConnection);
			// System.out.println("Emp Basic Data Size is  "+empBasicData.size());
			 if (empBasicData!=null && empBasicData.size()>0) {
			     for (Iterator iterator = empBasicData.iterator(); iterator.hasNext();) {
			          Object[] object = (Object[]) iterator.next();
			          if (object[0]!=null && !object[0].toString().equals("") && object[6]!=null && !object[6].toString().equals("") && object[7]!=null && !object[7].toString().equals("") && object[9]!=null && !object[9].toString().equals("") ) {
			        	  registerUser(object[0].toString(),object[6].toString(),object[7].toString(),object[9].toString(),clientConnection);
					}
			     }
			   }
		 }
	    catch (Exception e) {
			e.printStackTrace();
		}
			  	 
		 
	    	
		//Case 3. No Employee Record & No User Account Exist 
		try
		  {
			boolean flag = false;
		    List client_data_empid = getEmployeeId(clientConnection);
			if (client_data_empid!=null && client_data_empid.size()>0) {
			for (Iterator iterator = client_data_empid.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					boolean status= HUH.isExist("employee_basic","empId", object.toString(),clientConnection);
					//System.out.println("Employee Flag Status  "+status);
					if (!status) {
						//System.out.println("Inside If When Flag is true");
						//System.out.println("Employee Id "+object.toString()+" doesn't exist in database");
						List newEmployeeList =getEmployeeData4Add(object.toString(),clientConnection);
					    String empid="",empname="",emailid="",mobno="",subdept="",desig="",id="";
					
					    for (Iterator iterator2 = newEmployeeList.iterator(); iterator2.hasNext();) {
						     Object[] newEmp = (Object[]) iterator2.next();
						     if (newEmp[0]!=null && !newEmp[0].toString().equals("") && newEmp[1]!=null && !newEmp[1].toString().equals("") && newEmp[2]!=null && !newEmp[2].toString().equals("") && newEmp[3]!=null && !newEmp[3].toString().equals("") && newEmp[6]!=null && !newEmp[6].toString().equals("")) {
						    	 empid=newEmp[0].toString();
						    	// System.out.println("Emp Id  "+newEmp[0].toString());
						    	 empname=DateUtil.makeTitle(newEmp[1].toString());
						    	 //System.out.println("Emp Name  "+newEmp[1].toString());
						    	 if (newEmp[2]!=null && !newEmp[2].toString().equals("") && !newEmp[2].toString().equals("NA")) {
							    	 mobno=correctMobileNo(newEmp[2].toString());
								 }
							     else {
							    	 mobno="NA";
								 }
						    	// System.out.println("Mob No  "+mobno);
							     if (newEmp[3]==null || newEmp[3].toString().equals("") || newEmp[3].toString().equals("NA")) {
							    	 emailid = "helpdesk@blkhospital.com";
								 }
								 else {
									   String[] emailarr = null;
									   if (newEmp[3].toString().contains(",")) {
										 emailarr = newEmp[4].toString().split(",");
										 if (emailarr[0].trim().length()>0) {
											 emailid = emailarr[0].trim();
										 }
										 else if (emailarr[1].trim().length()>0) {
											 emailid = emailarr[1].trim();
										 }
									   }
									   else {
										   emailid = newEmp[3].toString();
									   }
								 }
							    // System.out.println("Email Id   "+emailid); 
							    
							     subdept=newEmp[6].toString();
							     //System.out.println("Dept Id   "+subdept);
							     //Integrated User where designation Id is 517
							     desig=newEmp[7].toString();
							     if (subdept!=null && !subdept.equals("") && !subdept.equals("NA")) {
							    	 flag =addEmployee(empid,empname,emailid,mobno,subdept,desig,clientConnection);
								     if (flag) {
										 id = HUH.getEmpValueId("employee_basic", "empId", empid, "empName", empname, "","", clientConnection);
										 if (id!=null && !id.equals("")) {
										// System.out.println("New generated Id  "+id);
											 registerUser(id,empid,empname,mobno,clientConnection);
										}
								     }
								  }
							  }
					        }
			              }
					    }
					  }
	       }
	     catch(Exception E) {
		     E.printStackTrace();
	     } 
	     }
	
	
	/**
	 * Purpose Of this Methos is to import All records from HIS database to MHDM Database...
	 * @param log <log4j>
	 * @param sessionMysql
	 * @param sessionHis
	 */
	@SuppressWarnings("unchecked")
	public void getSetCurrentDayData(Log log, SessionFactory sessionMysql, Session sessionHis) {
		//List<String> empID  = new ArrayList<String>();
		try {
		        Query query = sessionHis.createSQLQuery(
				" select EMP_OLD_NO as empid,NAME as empname, mobile_no as mobno,email_id as emailid,doJ as doj, dos,SubDeptID_HD as subdept_id,extno,desig_code,desig_name from EMP_DB_HELPDESK");
				List clientData = query.list();
				//System.out.println("Client data List Size is "+clientData.size());
				if(clientData != null && clientData.size() > 0) {
				  CommonOperInterface cot = new CommonConFactory().createInterface();
				  boolean ckecktable = new HelpdeskUniversalHelper().checkTable("client_data", sessionMysql);
				  if (!ckecktable) {
				  List<TableColumes> tablecolumn = new ArrayList<TableColumes>();
				      
		    	  TableColumes tc = new TableColumes();
                  tc.setColumnname("empid");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("empname");
				  tc.setDatatype("varchar(100)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("mobno");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("emailid");
				  tc.setDatatype("varchar(80)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  
				  tc = new TableColumes();
                  tc.setColumnname("doj");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  
				  tc = new TableColumes();
                  tc.setColumnname("dos");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("subdept_id");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("extno");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("desig_code");
				  tc.setDatatype("varchar(50)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("desig_name");
				  tc.setDatatype("varchar(200)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  tc = new TableColumes();
                  tc.setColumnname("importdate");
				  tc.setDatatype("varchar(10)");
				  tc.setConstraint("default NULL");
				  tablecolumn.add(tc);
				  
				  cot.createTable22("client_data", tablecolumn, sessionMysql);
				}
				for (Iterator iterator = clientData.iterator(); iterator .hasNext();) {
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					 {
						List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
						InsertDataTable ob=new InsertDataTable();
						
						ob.setColName("empid");
						if (object[0]!=null && !object[0].toString().equals("")) {
							ob.setDataName(object[0].toString());
						}
						else {
							ob.setDataName("NA");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("empname");
						if (object[1]!=null && !object[1].toString().equals("")) {
							//System.out.println("In Client table Emp Name  >>>>    "+DateUtil.makeTitle(object[1].toString()));
							ob.setDataName(DateUtil.makeTitle(object[1].toString()));
						}
						else {
							//System.out.println("NA");
							ob.setDataName("In Client table Emp Name  >>>>  NA");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("mobno");
						if (object[2]!=null && !object[2].toString().equals("")) {
							ob.setDataName(correctMobileNo(object[2].toString()));
						}
						else {
							ob.setDataName("NA");
						}
						insertData.add(ob);
						
						
						ob=new InsertDataTable();
						ob.setColName("emailid");
						
						
						if (object[3]!=null && !object[3].toString().equals("")) {
							ob.setDataName(DateUtil.makeTitle(correctEmailId(object[3].toString())));
						}
						else {
							ob.setDataName("helpdesk@blkhospital.com");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("doj");
						if (object[4]!=null && !object[4].toString().equals("") && object[4].toString().length()==10) {
							ob.setDataName(object[4].toString().substring(0, 10));
						}
						else {
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
						}
						insertData.add(ob);
						
						
						ob=new InsertDataTable();
						ob.setColName("dos");
						if (object[5]!=null && !object[5].toString().equals("") && object[5].toString().length()==10) {
							ob.setDataName(object[5].toString().substring(0, 10));
						}
						else {
							ob.setDataName("NA");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("subdept_id");
						if (object[6]!=null && !object[6].toString().equals("")) {
							ob.setDataName(object[6].toString());
						}
						else {
							ob.setDataName("NA");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("extno");
						if (object[7]!=null && !object[7].toString().equals("")) {
							ob.setDataName(object[7].toString());
						}
						else {
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
						}
						insertData.add(ob);
						
						
						ob=new InsertDataTable();
						ob.setColName("desig_code");
						if (object[8]!=null && !object[8].toString().equals("")) {
							ob.setDataName(object[8].toString());
						}
						else {
							ob.setDataName("NA");
						}
						insertData.add(ob);
						
						
						ob=new InsertDataTable();
						ob.setColName("desig_name");
						if (object[9]!=null && !object[9].toString().equals("")) {
							ob.setDataName(object[9].toString());
						}
						else {
							ob.setDataName("New Joinee");
						}
						insertData.add(ob);
						
						ob=new InsertDataTable();
						ob.setColName("importdate");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
						cot.insertIntoTable("client_data",insertData,sessionMysql);
				    }
				   }
				 }
				else {
					log.error("@ClientDataIntegration @"+DateUtil.getCurrentTimeStamp()+" :: No Record Found");
				}	
		}catch(Exception E) {
			E.printStackTrace();
			
		}finally {
			sessionHis.close();
		}
	}
	
	
	
	public void registerUser(String id, String empId, String empName, String mobNo,SessionFactory connection)
	  {
		try 
		    {
				CommonOperInterface coi = new CommonConFactory().createInterface();
	            List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	            
				InsertDataTable ob=new InsertDataTable();
				ob.setColName("mobileNo");
				ob.setDataName(mobNo);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("name");
				ob.setDataName(empName);
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("userID");
				ob.setDataName(Cryptography.encrypt(empId,DES_ENCRYPTION_KEY));
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("linkVal");
				ob.setDataName("vion_ADD#");
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("password");
				ob.setDataName(Cryptography.encrypt(empId,DES_ENCRYPTION_KEY));
				insertData.add(ob);
				
	    		ob=new InsertDataTable();
		    	ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				 
				ob=new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				 
				ob=new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName("prashant");
				insertData.add(ob);
				 
				ob=new InsertDataTable();
				ob.setColName("active");
			    ob.setDataName("1");
				insertData.add(ob);
				 
				ob=new InsertDataTable();
				ob.setColName("userForProductId");
				ob.setDataName("1,2,3");
				insertData.add(ob);
				
				ob=new InsertDataTable();
				ob.setColName("userType");
				ob.setDataName("N");
				insertData.add(ob);
				 
				boolean status=coi.insertIntoTable("useraccount",insertData,connection);
				if(status && id!=null && !id.equals(""))
				 {
					 CommonOperInterface cbt=new CommonConFactory().createInterface();
					 int userAcctId=cbt.getMaxId("useraccount",connection);
					 if(userAcctId!=0)
					 {
						StringBuffer buffer=new StringBuffer("update employee_basic set useraccountid='"+userAcctId+"' where empId='"+empId+"'");
						//System.out.println("Emp Update Query for new User "+buffer.toString());
						coi.executeAllUpdateQuery(buffer.toString(),connection);
					 }
				 }
			} 
		catch (Exception e) {
			  e.printStackTrace();
			}
		}
	
	
	@SuppressWarnings("unchecked")
	/*public List getUniqueData(String table,String column,List list, String field,boolean flag,SessionFactory connection)
	  {*/
		public List getUniqueData(String table,String column,List list, String field,boolean flag,SessionFactory connection)
		  {
		String deptall="";
		/*String yyy ="";
		if (list!=null && list.size()>0) {
			yyy= list.toString().replace("[", "").replace("]", "");
			deptall="select * from "+table+" where "+column+" not in('NA',"+yyy+") and importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
		}
		else
		{
			if (flag) {
				yyy=field;
			    deptall="select * from "+table+" where "+column+"='"+yyy+"' and importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
			}
			
			else {
				yyy=field;
			    deptall="select * from "+table+" where "+column+"='"+yyy+"'";
			}
		}*/
		deptall="select * from "+table+" where importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
	//	System.out.println("Query is  "+deptall);
	    List  subdeptlist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(deptall);
			subdeptlist=query.list();
		} 
		catch (Exception e) {
					e.printStackTrace();
				} 
		finally {
					session.flush();
					session.close();
				}
				return subdeptlist;
	}
	
	
	@SuppressWarnings("unchecked")
	/*public List getUniqueData(String table,String column,List list, String field,boolean flag,SessionFactory connection)
	  {*/
		public List getEmployeeData(String empid,SessionFactory connection)
		  {
		String deptall="";
		
		deptall="select empid,empname,mobno,emailid,doj,dos,subdept_id,desig_name from client_data where importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
		//System.out.println("Query is  "+deptall);
	    List  subdeptlist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(deptall);
			subdeptlist=query.list();
		} 
		catch (Exception e) {
					e.printStackTrace();
				} 
		finally {
					session.flush();
					session.close();
				}
				return subdeptlist;
	}
	
	
	@SuppressWarnings("unchecked")
	/*public List getUniqueData(String table,String column,List list, String field,boolean flag,SessionFactory connection)
	  {*/
		public List getEmployeeData4Add(String empid,SessionFactory connection)
		  {
		String deptall="";
		
		deptall="select empid,empname,mobno,emailid,doj,dos,subdept_id,desig_name from client_data where empid='"+empid+"'";
		//System.out.println("Query is  "+deptall);
	    List  subdeptlist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(deptall);
			subdeptlist=query.list();
		} 
		catch (Exception e) {
					e.printStackTrace();
				} 
		finally {
					session.flush();
					session.close();
				}
				return subdeptlist;
	}
	
	@SuppressWarnings("unchecked")
		public List getCurrentDateData(String table,SessionFactory connection)
		  {
		String deptall="";
		
		deptall="select * from "+table+" where importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
	    //	System.out.println("Query is  "+deptall);
	    List  subdeptlist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(deptall);
			subdeptlist=query.list();
		} 
		catch (Exception e) {
					e.printStackTrace();
				} 
		finally {
					session.flush();
					session.close();
				}
				return subdeptlist;
	}
	
	
	@SuppressWarnings("unchecked")
	public List recordCheck4UserAccount(SessionFactory connection)
	  {
		String qry="select * from employee_basic where flag = 0 and empId !='' and empId !='NA' and useraccountid is null";
	   // System.out.println("User Query  "+qry);
		List  emplist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(qry);
			emplist=query.list();
		} 
		catch (Exception e) {
					e.printStackTrace();
				}
		finally {
					session.flush();
					session.close();
				 }
		return emplist;
	  }
	
	@SuppressWarnings("unchecked")
	public List getEmpId(String table,String column,String searchColumn,List list, Boolean flag,SessionFactory connection)
	  {
		String uniquelist ="";
		String qry="";
		if (flag) {
			       if (list!=null && list.size()>0 ){
			    	    uniquelist= list.toString().replace("[", "").replace("]", "");
			    	    qry="select "+column+" from "+table+" where "+searchColumn+" not in("+uniquelist+") and importDate='"+DateUtil.getCurrentDateUSFormat()+"'";
			       }	
			      }
		else {
			if (list!=null && list.size()>0 ){
				    uniquelist= list.toString().replace("[", "").replace("]", "");
				    qry="select "+column+" from "+table+" where "+searchColumn+" not in("+uniquelist+")";
				}
		}
	    List  emplist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(qry);
			emplist=query.list();
		} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.flush();
					session.close();
				 }
				return emplist;
		    }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeId(SessionFactory connection)
	  {
		String qry="";
		 qry="select empid from client_data";
		//System.out.println("qqq   "+qry);
	    List  emplist = new ArrayList();
		Session session = null;
		try 
		 {
			session = connection.openSession();
			Query query =session.createSQLQuery(qry);
			emplist=query.list();
		} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.flush();
					session.close();
				 }
				return emplist;
		    }
	
	
	
	public boolean addEmployee(String empid,String empname,String emailid,String mobno,String subdept,String designation,SessionFactory connection)
	 {
		boolean status=false;
		try
		  {
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
		     List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			 InsertDataTable ob=null;
			 
			 ob=new InsertDataTable();
			 ob.setColName("regLevel");
			 ob.setDataName("1");
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("groupId");
			 ob.setDataName("1");
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("deptname");
			 ob.setDataName(subdept);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("designation");
			 ob.setDataName(designation);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("empName");
			 ob.setDataName(empname);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("mobOne");
			 ob.setDataName(mobno);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("emailIdOne");
			 ob.setDataName(emailid);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("empId");
			 ob.setDataName(empid);
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("flag");
			 ob.setDataName("0");
			 insertData.add(ob);
				 
			 ob=new InsertDataTable();
			 ob.setColName("date");
			 ob.setDataName(DateUtil.getCurrentDateUSFormat());
			 insertData.add(ob);
			 
			 ob=new InsertDataTable();
			 ob.setColName("time");
			 ob.setDataName(DateUtil.getCurrentTime());
			 insertData.add(ob);
			 
			 status= cbt.insertIntoTable("employee_basic",insertData,connection);
		  }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	

	public String correctMobileNo(String mobNo)
	 {
		List<String> mobileno = new ArrayList<String>();
		String mobileOne = "NA";
		try {
			//Check for specifier 
			if(mobNo != null && mobNo != "NA")
			 {
			  if(mobNo.indexOf('-') > 0) {
				  mobileno = Arrays.asList(mobNo.split("-"));
			  }else if(mobNo.indexOf('/') > 0) {
				  mobileno = Arrays.asList(mobNo.split("/"));
			  }else if(mobNo.indexOf('\\') > 0) {
				  mobileno = Arrays.asList(mobNo.split("\\"));
			  }else if(mobNo.indexOf('.') > 0) {
				  mobileno = Arrays.asList(mobNo.split("\\."));
			  }else if(mobNo.indexOf(' ') > 0) {
				  mobileno = Arrays.asList(mobNo.split(" "));
			  }else if(mobNo.indexOf(':') > 0) {
				  mobileno = Arrays.asList(mobNo.split(":"));
			  } else if(mobNo.indexOf('#') > 0) {
				  mobileno = Arrays.asList(mobNo.split("#"));
			  }else if(mobNo.indexOf('~') > 0) {
				  mobileno = Arrays.asList(mobNo.split("~"));
			  }else if(mobNo.indexOf('`') > 0) {
				  mobileno = Arrays.asList(mobNo.split("`"));
			  }else if(mobNo.indexOf('!') > 0) {
				  mobileno = Arrays.asList(mobNo.split("!"));
			  }else if(mobNo.indexOf('@') > 0) {
				  mobileno = Arrays.asList(mobNo.split("@"));
			  }else if(mobNo.indexOf('$') > 0) {
				  mobileno = Arrays.asList(mobNo.split("$"));
			  }else if(mobNo.indexOf('%') > 0) {
				  mobileno = Arrays.asList(mobNo.split("%"));
			  }else if(mobNo.indexOf('^') > 0) {
				  mobileno = Arrays.asList(mobNo.split("^"));
			  }else if(mobNo.indexOf('&') > 0) {
				  mobileno = Arrays.asList(mobNo.split("&"));
			  }else if(mobNo.indexOf('*') > 0) {
				  mobileno = Arrays.asList(mobNo.split("*"));
			  }else if(mobNo.indexOf('(') > 0) {
				  mobileno = Arrays.asList(mobNo.split("("));
			  }else if(mobNo.indexOf(')') > 0) {
				  mobileno = Arrays.asList(mobNo.split(")"));
			  }else if(mobNo.indexOf('_') > 0) {
				  mobileno = Arrays.asList(mobNo.split("_"));
			  }else if(mobNo.indexOf('=') > 0) {
				  mobileno = Arrays.asList(mobNo.split("="));
			  }else if(mobNo.indexOf('|') > 0) {
				  mobileno = Arrays.asList(mobNo.split("|"));
			  }else if(mobNo.indexOf(";") > 0) {
				  mobileno = Arrays.asList(mobNo.split(";"));
			  }else if(mobNo.indexOf('>') > 0) {
				  mobileno = Arrays.asList(mobNo.split(">"));
			  }else if(mobNo.indexOf('<') > 0) {
				  mobileno = Arrays.asList(mobNo.split("<"));
			  }else if(mobNo.indexOf('?') > 0) {
				  mobileno = Arrays.asList(mobNo.split("?"));
			  }else {
				  mobileno.add(mobNo);
			  }
			}
			if(mobileno != null && mobileno.size() > 0) {
				for(String itr : mobileno) {
					itr = checkDualNumber(itr); 
					
					if(itr != null && itr.trim().length() == 10 &&  itr.trim().indexOf(" ") <  0) {
						mobileOne = itr.trim();
						break;
					}
				}
			}
		}catch(Exception E) {
	   }
	  return mobileOne;
	}
	
	public String checkDualNumber(String itr) 
	 {
		if(itr != null && itr.startsWith("+91")) {
			itr = itr.substring(3);
		}else if (itr != null && itr.startsWith("0")) {
			itr = itr.substring(1);
		}else if (itr != null && itr.startsWith("+")) {
			itr = itr.substring(1);
		}else if (itr != null && itr.trim().length() == 12) {
			itr = itr.substring(2);
		}
		if(itr.startsWith("+")  || itr.startsWith("0")) itr = checkDualNumber(itr);
		return itr;
	}
	/**
	 * @param mobNo
	 * @return
	 */
	public String correctEmailId(String emailId)
	 {
		try {
			//Check for specifier 
			if(emailId != null && emailId != "NA" && emailId.trim().length() > 0 )
			 {
			  if(emailId.indexOf(',') > 0) {
				  
				  String[] subEmailId = emailId.split(",");
				  
				  if(subEmailId[0] != null  && subEmailId[0].length() > 0)  emailId = subEmailId[0];
				  else  if(subEmailId[1] != null  && subEmailId[1].length() > 0) emailId = subEmailId[1];
				  else emailId = "helpdesk@blkhospital.com";
			  }
			}else{
				emailId = "helpdesk@blkhospital.com";
			}
			
		}catch(Exception E) {
			E.printStackTrace();
	   }
	  return emailId;
	}
	
	
}
