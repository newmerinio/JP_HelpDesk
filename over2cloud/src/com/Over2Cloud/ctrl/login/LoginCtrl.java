package com.Over2Cloud.ctrl.login;

import hibernate.common.HibernateSessionFactory;

import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.signup.BeanApps;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LoginCtrl extends ActionSupport implements SessionAware, ServletRequestAware
{
	private String username;
	private String password;
	private String accountid;
	private String newPwd;
	private List<BeanApps> listuser = new ArrayList<BeanApps>();
	private HttpServletRequest request;
	private Map<String, Object> session;
	private boolean chunkSpaceBlock;
	private String errorMessege;
	private String empName;
	private String userType;
	private String oldPwd;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String orgLogoName;
	private String profilePicName;
	private String empDept;
	private String smsNotification = null;
	private String emailNotification = null;
	private String changeType;
	private String orgImgPath;

	@SuppressWarnings("unchecked")
	public String doLoginAuthenticate()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String empdetails[] = CommonWork.fetchEmployeeDetails(connectionSpace, getUsername());
			if(empdetails != null && empdetails[0]!=null)
			{
				int empID = Integer.parseInt(empdetails[0]);
				empDept = empdetails[3];
				if (empID != 0)
				{
					StringBuffer buffer = new StringBuffer("select image from primary_contact where id='" + empID + "'");
					List dataList = cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
					{
						try
						{
							profilePicName = dataList.get(0).toString();
						}
						catch (Exception e)
						{
							profilePicName = null;
						}
					}
					else
					{
						profilePicName = null;
					}
				}
				if (empdetails != null)
				{
					if (empdetails[6].equalsIgnoreCase("M"))
					{
						userType = "Management";
					}
					else if (empdetails[6].equalsIgnoreCase("H"))
					{
						userType = "HOD";
					}
					else if (empdetails[6].equalsIgnoreCase("N"))
					{
						userType = "Normal";
					}

				}
			}
			orgImgPath = new CommonWork().getOrganizationImage((SessionFactory) session.get("connectionSpace"));
			return "normalUserLogin";
		}
		else
		{
			try
			{
				Calendar currentDate = Calendar.getInstance();
				SessionFactory sessfact = null;
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
				String dateNow = formatter.format(currentDate.getTime());
				if (getUsername().trim().equalsIgnoreCase("dreamsol") && getPassword().trim().equalsIgnoreCase("dreamsol") && getAccountid().trim().equalsIgnoreCase("1000002"))
				{
					try
					{
						session.put("uName", "dreamsol");
						session.put("accountid", "over2cloud");
						session.put("regusername", "Mr. Prashant");
						session.put("countryid", "IN");
						session.put("orgname", "Over2Cloud.com");
						session.put("accounttype", "All");
						session.put("currentdate", dateNow);
						session.put("accountidCounty", getAccountid().trim());
						AllConnection Conn = new ConnectionFactory("localhost");
						AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
						sessfact = Ob1.getSession();
						session.put("connectionSpace", sessfact);
						session.put("Dbname", "clouddb");
						return "dreamsolLogin";
					}
					catch (Exception e)
					{
						return ERROR;
					}
				}
				else if (getAccountid().trim().equalsIgnoreCase("demo"))
				{
					try
					{
						// check for demo account existance
						if (getUsername().trim().equalsIgnoreCase(getPassword().trim()))
						{
							List demoAccount = LoginImp.getDemoAccountDetails(getUsername());

							if (demoAccount != null && demoAccount.size() > 0)
							{
								boolean accountExpire = LoginImp.checkDemoClientAccountExpire(getUsername());
								if (accountExpire)
								{
									// org_reg_name,org_name,accountType,country,id
									int noOFDemoDaysRemain = LoginImp.checkDemoClientRemainingDays(getUsername());
									session.put("noOFDemoDaysRemain", noOFDemoDaysRemain);
									int id = 0;
									for (Iterator it = demoAccount.iterator(); it.hasNext();)
									{
										Object[] obDemo = (Object[]) it.next();
										session.put("regusername", obDemo[0].toString());
										session.put("countryid", obDemo[3].toString());
										session.put("orgname", obDemo[1].toString());
										session.put("accounttype", obDemo[2].toString());
										id = (Integer) obDemo[4];
									}
									// getting mapped server id for getting
									// server data
									LoginImp ob1 = new LoginImp();
									List mappedDemoServer = LoginImp.getMappedDemoServer(id);
									String serverId = null;
									for (Iterator it = mappedDemoServer.iterator(); it.hasNext();)
									{
										Object obdata = (Object) it.next();
										serverId = obdata.toString();
									}
									String dbbname = id + "_demo_clouddb";
									String ipAddress = null;
									String username = null;
									String paassword = null;
									String port = null;
									List singleSpceServer = ob1.GetClientspace(serverId);
									for (Object c : singleSpceServer)
									{
										Object[] dataC = (Object[]) c;
										ipAddress = dataC[0].toString();
										username = dataC[2].toString();
										paassword = dataC[3].toString();
										port = dataC[1].toString();
									}
									AllConnection Conn = new ConnectionFactory(dbbname, ipAddress, username, paassword, port);
									AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
									SessionFactory sessfactNew = Ob1.getSession();
									if (sessfactNew == null)
										return "clientSpaceError";
									session.put("connectionSpace", sessfactNew);
									session.put("Dbname", dbbname);
									int sumuser = 0;
									session.put("uName", getUsername());
									session.put("accountid", "demo");
									session.put("currentdate", dateNow);
									session.put("userTpe", "o");
									session.put("accountidCounty", getAccountid().trim());
									session.put("listofproduct", getListuser());
									session.put("totaluser", sumuser);
									/**
									 * 1000 default id for user account demo
									 */
									session.put("empIdofuser", "o-100000");
									session.put("empName", getUsername());
									/**
									 * get all products which are available in
									 * dreamsol panel
									 */
									String productNames = LoginImp.getAllproductname();

									// System.out.println("Product Names >>>"+productNames);
									session.put("validApp", productNames + "Common#");
									// common session var related to
									// dept,offering levels,organization
									try
									{
										String offeringLevel = CommonWork.offeringLevelWithName(sessfactNew);
										session.put("offeringLevel", offeringLevel);
									}
									catch (Exception e)
									{
									}
									try
									{
										String orgLevel = CommonWork.organozationLevelWithName(sessfactNew);
										session.put("orgLevel", orgLevel);
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
									try
									{
										String deptLevelWithName = CommonWork.deptLevelWithName(sessfactNew);
										session.put("deptLevel", deptLevelWithName);
										String userDeptLevel = CommonWork.deptLevel(sessfactNew);
										System.out.println("userDeptLevel:::::::"+userDeptLevel);
										session.put("userDeptLevel", userDeptLevel);
										// setting the dept id and dept mapped
										// org id of the current login user
										// based.
										StringBuilder deptQuery = new StringBuilder("");
										String tempData[] = deptLevelWithName.split(",");
										int deptLevel = Integer.parseInt(tempData[0]);
										if (deptLevel == 2)// for subdept
										{
											CommonOperInterface cbt = new CommonConFactory().createInterface();
											deptQuery.append("select orgnztnlevel,mappedOrgnztnId from department where " + "id=(select sde.deptid from subdepartment as sde inner join employee_basic as eb " + "on eb.subdept=sde.id inner join useraccount as ua on ua.id=eb.useraccountid where " + "ua.userID='" + Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "')");
											List data = cbt.executeAllSelectQuery(deptQuery.toString(), sessfactNew);
											if (data != null)
											{
												// level1Data
												for (Iterator it = data.iterator(); it.hasNext();)
												{
													Object[] obdata = (Object[]) it.next();
													session.put("orgnztnlevel", (obdata[0]).toString());
													session.put("mappedOrgnztnId", obdata[1].toString());
												}
											}
										}
										else if (deptLevel == 1)// for dept
										{
											CommonOperInterface cbt = new CommonConFactory().createInterface();
											deptQuery.append("select de.orgnztnlevel,de.mappedOrgnztnId from department as de inner join employee_basic eb " + "on eb.dept=de.id inner join useraccount as uaon ua.id=eb.useraccountid where ua.userID='" + Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "'");
											List data = cbt.executeAllSelectQuery(deptQuery.toString(), sessfactNew);
											if (data != null)
											{
												// level1Data
												for (Iterator it = data.iterator(); it.hasNext();)
												{
													Object[] obdata = (Object[]) it.next();
													if (obdata != null)
													{
														session.put("orgnztnlevel", (obdata[0]).toString());
														session.put("mappedOrgnztnId", obdata[1].toString());
													}
												}
											}

										}
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
									return "orgnLoginConfig";
								}
								else
								{
									// demo email account expire
									return "errorOfAccountExpire";
								}
							}
							else
							{
								return ERROR;
							}

						}
						else
						{
							// user name and password dose not match
							return ERROR;
						}

					}
					catch (Exception e)
					{
						return ERROR;
					}
				}
				else
				{
					/*
					 * Associate Login Code (CAA(Coloud Associate Account))
					 * Cloud Organization Account(COA) Local Organization
					 * Account (LOA)
					 */
					LoginImp ob1 = new LoginImp();
					String Accountinfo[] = getAccountid().trim().split("-");
					// This If Block Check The Client Id Is Exist Or Not
					if (ob1.isExit(Accountinfo[1].toString(), Accountinfo[0].toString()))
					{
						if (ob1.isClientBlock(Accountinfo[1].toString(), Accountinfo[0].toString()))
						{
							try
							{
								String chunkId = null;
								SessionFactory sessfactNew = null;
								SessionFactory chunkSession = null;
								String clientType = ob1.GetAccountTypeOf_User(Accountinfo[1].toString(), Accountinfo[0].toString());
								// Chunk Space Configuration Added
								List ChunkSpace = ob1.chunkDomainName(Accountinfo[1].toString(), Accountinfo[0].toString());
								if (ChunkSpace != null && !ChunkSpace.isEmpty() && ChunkSpace.size() > 0)
								{
									for (Iterator iterator = ChunkSpace.iterator(); iterator.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator.next();
										List<String> colmName = new ArrayList<String>();
										colmName.add("Souce_ip_Domain_address");
										colmName.add("id");
										Map<String, String> wherClause = new HashMap<String, String>();
										wherClause.put("accountid", Accountinfo[1].toString());
										wherClause.put("country_iso_code", Accountinfo[0].toString());
										// Single Spce Configuration Come

										String dbbname1 = "clouddb";
										String ipAddress1 = objectCol[2].toString();
										String port1 = objectCol[3].toString();
										String username1 = objectCol[4].toString();
										String paassword1 = objectCol[5].toString();
										AllConnection Conn1 = new ConnectionFactory(dbbname1, ipAddress1, username1, paassword1, port1);
										AllConnectionEntry Ob = Conn1.GetAllCollectionwithArg();
										chunkSession = Ob.getSession();
										List ClientSpace = new TableInfo().viewAllDataEitherSelectOrAll(objectCol[0].toString().trim() + "_" + objectCol[1].toString().trim() + "_space_configuration", colmName, wherClause, chunkSession);
										if (ClientSpace != null && ClientSpace.size() > 0)
										{
											for (Iterator iterator1 = ClientSpace.iterator(); iterator1.hasNext();)
											{
												String dbbname = null, ipAddress = null, username = null, paassword = null, port = null;
												Object[] objectCol1 = (Object[]) iterator1.next();
												List singleSpceServer = ob1.GetClientspace(objectCol1[0].toString().trim());

												for (Object c : singleSpceServer)
												{
													Object[] dataC = (Object[]) c;

													dbbname = Accountinfo[1].toString().trim() + "_clouddb";
													ipAddress = dataC[0].toString();
													username = dataC[2].toString();
													paassword = dataC[3].toString();
													port = dataC[1].toString();
												}
												AllConnection Conn = new ConnectionFactory(dbbname, ipAddress, username, paassword, port);
												AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
												sessfactNew = Ob1.getSession();
												session.put("connectionSpace", sessfactNew);
												// session.put("Dbname",Accountinfo[1].toString().trim()+"_clouddb");
											}
										}
										else
										{
											return "clientSpaceError";
										}
									}
								}
								else
								{
									List ChunkSpaceError = ob1.chunkBlockError(Accountinfo[1].toString(), Accountinfo[0].toString());
									if (ChunkSpaceError != null && !ChunkSpaceError.isEmpty() && ChunkSpaceError.size() > 0)
									{
										for (Iterator iterator = ChunkSpaceError.iterator(); iterator.hasNext();)
										{
											Object objectCol = (Object) iterator.next();
											chunkId = objectCol.toString();
										}
									}
									if (chunkId != null)
									{
										errorMessege = "Chunk Space Is Blocked Due To " + chunkId;
									}
									else
									{
										errorMessege = "Chunk Space Is not Alloted";
									}
									return "chunkSpaceError";
								}
								// Cloud organization account
								if (clientType.equals("COA"))
								{
									// checking in the local db of cloud
									// client_info checking
									// for Configuration user
									if (ob1.isUserExit(Accountinfo[1].toString(), Accountinfo[0].toString(), Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY), Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY)))
									{
										List userList = ob1.getUserInfomation(Accountinfo[1].toString(), Accountinfo[0].toString(), Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY), Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY));
										session.put("encryptedID", Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY));

										if (userList != null && userList.size() > 0)
										{
											for (Iterator iterator = userList.iterator(); iterator.hasNext();)
											{
												Object[] objectCol = (Object[]) iterator.next();
												session.put("uName", getUsername());
												session.put("accountid", Accountinfo[1].toString());
												session.put("regusername", objectCol[2].toString());
												session.put("countryid", Accountinfo[0].toString());
												session.put("orgname", objectCol[0].toString());
												session.put("accounttype", objectCol[1].toString());
												session.put("currentdate", dateNow);
												session.put("userTpe", "o");
												session.put("accountidCounty", getAccountid().trim());
												/**
												 * 1000 default id for user
												 * account demo
												 */
												session.put("empIdofuser", "o-100000");
												// String
												// empname=CommonWork.userEmpName(sessfactNew,
												// getUsername());
												session.put("empName", objectCol[2].toString());
												int sumuser = 0;
												StringBuilder validApp = new StringBuilder("Common#");
												if (objectCol[3] != null && objectCol[4] != null)
												{
													String ProductCode[] = objectCol[3].toString().split("#");
													String Productuser[] = objectCol[4].toString().split("#");
													@SuppressWarnings("unused")
													String validity_from[] = objectCol[5].toString().split("#");
													String validity_to[] = objectCol[6].toString().split("#");
													BeanApps hm = null;
													for (int j = 0; j < ProductCode.length; j++)
													{
														hm = new BeanApps();
														String asd = ob1.getAppsName(ProductCode[j].toString());
														hm.setApp_name(asd.substring(1, asd.lastIndexOf("]")).trim());
														hm.setApp_code(Productuser[j].trim());
														listuser.add(hm);
														boolean status = DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(), validity_to[j]);
														// boolean status1 =
														// DateUtil.comparetwoDates(DateUtil.getCurrentDateIndianFormat(),validity_to[j]);

														if (status)
														{
															validApp.append(ProductCode[j].toString() + "#");
														}

														// System.out.println("validApp Names >>>"+validApp);

														Integer.parseInt(Productuser[j].trim());
														sumuser = sumuser + Integer.parseInt(Productuser[j].trim());
													}
												}
												setListuser(listuser);
												session.put("listofproduct", getListuser());
												session.put("totaluser", sumuser);
												// session.put("moduleUsers",
												// objectCol[4].toString());//Added
												// by Anoop, # separated values
												// of product users for user
												// management
												// getting all valid app names
												// for the organization login
												if (validApp != null && !validApp.toString().equalsIgnoreCase(""))
												{
													System.out
															.println(validApp);
													session.put("validApp", validApp.toString());
													// common session var
													// related to dept,offering
													// levels,organization

													// Commented on 28 Oct 2013
													/*
													 * try { String
													 * offeringLevel=CommonWork.
													 * offeringLevelWithName
													 * (sessfactNew);
													 * session.put
													 * ("offeringLevel",
													 * offeringLevel); }
													 * catch(Exception e) { }
													 */

													String orgLevel = CommonWork.organozationLevelWithName(sessfactNew);
													session.put("orgLevel", orgLevel);

													String deptLevelWithName = CommonWork.deptLevelWithName(sessfactNew);
													session.put("deptLevel", deptLevelWithName);

													// setting the dept id and
													// dept mapped org id of the
													// current login user based.
													/*
													 * StringBuilder
													 * deptQuery=new
													 * StringBuilder(""); String
													 * tempData
													 * []=deptLevelWithName
													 * .split(","); int
													 * deptLevel=0;
													 * if(tempData[0]!=null &&
													 * !tempData
													 * [0].equalsIgnoreCase(""))
													 * {
													 * deptLevel=Integer.parseInt
													 * (tempData[0]); }
													 * if(deptLevel==2)//for
													 * subdept {
													 * CommonOperInterface
													 * cbt=new
													 * CommonConFactory()
													 * .createInterface();
													 * deptQuery.append(
													 * "select orgnztnlevel,mappedOrgnztnId from department where "
													 * +
													 * "id=(select sde.deptid from subdepartment as sde inner join employee_basic as eb "
													 * +
													 * "on eb.subdept=sde.id inner join useraccount as ua on ua.id=eb.useraccountid where "
													 * +
													 * "ua.userID='"+Cryptography
													 * .encrypt(getUsername(),
													 * DES_ENCRYPTION_KEY
													 * )+"')"); Listdata=cbt.
													 * executeAllSelectQuery
													 * (deptQuery
													 * .toString(),sessfactNew);
													 * if(data!=null) {
													 * //level1Data for(Iterator
													 * it=data.iterator();
													 * it.hasNext();) { Object[]
													 * obdata
													 * =(Object[])it.next();
													 * session
													 * .put("orgnztnlevel",
													 * (obdata[0]).toString());
													 * session
													 * .put("mappedOrgnztnId",
													 * obdata[1].toString()); }
													 * } } else
													 * if(deptLevel==1)// for
													 * dept {
													 * CommonOperInterface
													 * cbt=new
													 * CommonConFactory()
													 * .createInterface();
													 * deptQuery.append(
													 * "select de.orgnztnlevel,de.mappedOrgnztnId from department as de inner join employee_basic eb "
													 * +
													 * "on eb.deptname=de.id inner join useraccount as ua on ua.id=eb.useraccountid where ua.userID='"
													 * +Cryptography.encrypt(
													 * getUsername
													 * (),DES_ENCRYPTION_KEY
													 * )+"'"); // System.out //
													 * .
													 * println("##### "+deptQuery
													 * .toString()); List
													 * data=cbt
													 * .executeAllSelectQuery
													 * (deptQuery
													 * .toString(),sessfactNew);
													 * if(data!=null) {
													 * //level1Data for(Iterator
													 * it=data.iterator();
													 * it.hasNext();) { Object[]
													 * obdata
													 * =(Object[])it.next();
													 * if(obdata!=null) {
													 * session
													 * .put("orgnztnlevel",
													 * (obdata[0]).toString());
													 * session
													 * .put("mappedOrgnztnId",
													 * obdata[1].toString()); }
													 * } }
													 * 
													 * }
													 */

												}
												else
												{
													return "errorOfAccountExpire";
												}

												/*
												 * //Anoop, 5-8-2013 //Start:
												 * Availabel apps String rights
												 * =
												 * ob1.getAllClientRights(validApp
												 * .toString());
												 * System.out.println
												 * ("rights: "+rights);
												 * if(rights != null &&
												 * !rights.equalsIgnoreCase(""))
												 * session.put("userRights",
												 * rights); //module visible to
												 * a user, # separated values
												 * else return
												 * "errorOfAccountExpire";
												 */// End: Availabel apps

												/*
												 * START: Anoop, 3-8-2013 put
												 * user rights in session
												 */
												CommonOperInterface coi = new CommonConFactory().createInterface();
												StringBuilder query = new StringBuilder();
												query.append("select link_val from user_account where user_name='");
												query.append(Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "'");
												query.append(" and ");
												query.append(" password='");
												query.append(Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY) + "'");
												List rightsList = coi.executeAllSelectQuery(query.toString(), (SessionFactory) session.get("connectionSpace"));
												if (rightsList != null && rightsList.size() > 0)
												{
													String rights = rightsList.get(0).toString();
													if (rights == null || rights.equalsIgnoreCase(""))
													{
														return "errorOfAccountExpire";
													}
													session.put("userRights", rights);// module
																						// visible
																						// to
																						// a
																						// user,
																						// #
																						// separated
																						// values
												}

												// START: Anoop, 3-8-2013
											}
											return "orgnLoginConfig";
										}
									}
									else
									{
										// for Normal User
										// code for checking the user name and
										// password based on client id in
										// organization data base
										if (ob1.isUserExitForLocalOrg(Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY), Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY), (SessionFactory) session.get("connectionSpace")))
										{
											// set from the account id based
											// data

											session.put("encryptedID", Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY));
											session.put("accountid", Accountinfo[1].toString());
											session.put("countryid", Accountinfo[0].toString());
											session.put("uName", getUsername());
											session.put("currentdate", dateNow);
											session.put("regusername", getUsername());
											String empdetails[] = CommonWork.fetchEmployeeDetails((SessionFactory) session.get("connectionSpace"), getUsername());
											if (empdetails != null)
											{
												session.put("userTpe", empdetails[6]);
												session.put("empIdofuser", "n-" + empdetails[0]);
												session.put("empName", empdetails[1]);
												if (empdetails[6].equalsIgnoreCase("M"))
												{
													this.userType = "Management";
												}
												else if (empdetails[6].equalsIgnoreCase("H"))
												{
													this.userType = "HOD";
												}
												else if (empdetails[6].equalsIgnoreCase("N"))
												{
													this.userType = "Normal";
												}
											}
											else
											{
												session.put("userTpe", "n");
											}
											session.put("accountidCounty", getAccountid().trim());
											CommonOperInterface coi = new CommonConFactory().createInterface();
											StringBuilder query = new StringBuilder();
											query.append("select link_val from user_account where user_name='");
											query.append(Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "'");
											query.append(" and ");
											query.append(" password='");
											query.append(Cryptography.encrypt(getPassword(), DES_ENCRYPTION_KEY) + "'");
											List rightsList = coi.executeAllSelectQuery(query.toString(), (SessionFactory) session.get("connectionSpace"));
											if (rightsList != null)
											{
												String rights = rightsList.get(0).toString();
												if (rights == null || rights.equalsIgnoreCase(""))
												{
													return "errorOfAccountExpire";
												}
												session.put("userRights", rights);// module
																					// visible
																					// to
																					// a
																					// user,
																					// #
																					// separated
																					// values
											}
											List userList = ob1.getUserInfomation(Accountinfo[1].toString(), Accountinfo[0].toString());
											if (userList != null && userList.size() > 0)
											{
												for (Iterator iterator = userList.iterator(); iterator.hasNext();)
												{
													Object[] objectCol = (Object[]) iterator.next();
													session.put("orgname", objectCol[0].toString());
													session.put("accounttype", objectCol[1].toString());
													session.put("orgDetail", objectCol[0].toString() + "#" + objectCol[5].toString() + "#" + objectCol[6].toString() + "#" + objectCol[7].toString());
												}
											}
											// getting the products names and
											// the no of users of application
											// and product names
											List productData = ob1.getproductDetails((SessionFactory) session.get("connectionSpace"));
											int sumuser = 0;
											for (Iterator iterator = productData.iterator(); iterator.hasNext();)
											{
												Object[] objectCol = (Object[]) iterator.next();
												String ProductCode[] = objectCol[1].toString().split("#");
												String Productuser[] = objectCol[2].toString().split("#");
												BeanApps hm = null;

												for (int j = 0; j < ProductCode.length; j++)
												{
													hm = new BeanApps();
													String asd = ob1.getAppsName(ProductCode[j].toString());
													hm.setApp_name(asd.substring(1, asd.lastIndexOf("]")).trim());
													hm.setApp_code(Productuser[j].trim());
													listuser.add(hm);
													Integer.parseInt(Productuser[j].trim());
													sumuser = sumuser + Integer.parseInt(Productuser[j].trim());
												}
											}
											setListuser(listuser);
											// setting purchased application
											// related variable in session
											String validApp = CommonWork.checkUserValidtyWithApp(sessfactNew, getUsername());
											/**
											 * if validApp == blank it means
											 * user validaty is expiered and
											 * he/she is not able to use the
											 * application further more
											 */

											session.put("listofproduct", getListuser());
											session.put("totaluser", sumuser);

											// common session var related to
											// dept,offering levels,organization
											
											String offeringLevel = CommonWork.offeringLevelWithName(sessfactNew);
											session.put("offeringLevel", offeringLevel);
										
											String orgLevel = CommonWork.organozationLevelWithName(sessfactNew);
											session.put("orgLevel", orgLevel);
									
											String deptLevelWithName = CommonWork.deptLevelWithName(sessfactNew);
											session.put("deptLevel", deptLevelWithName);
											// setting the dept id and dept
											// mapped org id of the current
											// login user based.
											/*StringBuilder deptQuery = new StringBuilder("");
											String tempData[] = deptLevelWithName.split(",");
											int deptLevel = Integer.parseInt(tempData[0]);
											if (deptLevel == 2)// for
																// subdept
											{
												CommonOperInterface cbt = new CommonConFactory().createInterface();
												deptQuery.append("select orgnztnlevel,mappedOrgnztnId from department where " + "id=(select distinct dpt.id from department as dpt inner join employee_basic as eb " + "on eb.deptname=dpt.id inner join useraccount as ua on ua.id=eb.useraccountid where " + "ua.userID='" + Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "')");

												List data = cbt.executeAllSelectQuery(deptQuery.toString(), sessfactNew);
												if (data != null)
												{
													// level1Data
													for (Iterator it = data.iterator(); it.hasNext();)
													{
														Object[] obdata = (Object[]) it.next();
														session.put("orgnztnlevel", (obdata[0]).toString());
														session.put("mappedOrgnztnId", obdata[1].toString());
													}
												}
											}
											else if (deptLevel == 1)// for
																	// dept
											{
												CommonOperInterface cbt = new CommonConFactory().createInterface();
												deptQuery.append("select de.orgnztnlevel,de.mappedOrgnztnId from department as de inner join employee_basic eb " + "on eb.deptName=de.id inner join useraccount as ua on ua.id=eb.useraccountid where ua.userID='" + Cryptography.encrypt(getUsername(), DES_ENCRYPTION_KEY) + "'");
												List data = cbt.executeAllSelectQuery(deptQuery.toString(), sessfactNew);
												if (data != null)
												{
													// level1Data
													for (Iterator it = data.iterator(); it.hasNext();)
													{
														Object[] obdata = (Object[]) it.next();
														if (obdata != null)
														{
															session.put("orgnztnlevel", (obdata[0]).toString());
															session.put("mappedOrgnztnId", obdata[1].toString());
														}
													}
												}

											}*/
											// common session var related to
											// dept,offering levels,organization
											session.put("validApp", validApp);
											if (empdetails[0] != null)
											{
												StringBuffer buffer = new StringBuffer("select image from primary_contact where id='" + empdetails[0] + "'");

												List dataList = new createTable().executeAllSelectQuery(buffer.toString(), (SessionFactory) session.get("connectionSpace"));
												if (dataList != null && dataList.size() > 0 && dataList.get(0) != null && !dataList.get(0).toString().equalsIgnoreCase(""))
												{
													try
													{
														profilePicName = dataList.get(0).toString();
													}
													catch (Exception e)
													{
														profilePicName = null;
													}
												}
												else
												{
													profilePicName = null;
												}
												empDept = empdetails[3];

											}
											String path=new CommonWork().getOrganizationImage((SessionFactory) session.get("connectionSpace"));
											orgLogoName = path;
											orgImgPath = path;
											return "normalUserLogin";
										}
										else
										{
											errorMessege = "Please enter correct username & password.";
											addActionMessage(" The username or password you entered is incorrect. ");
											return "errorUserNotAval";
										}
									}
								}
								else if (clientType.equals("CAA"))
								{
									// Associate login work here
								}
								else if (clientType.equals("LOA"))
								{
									// Local Organization Account
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							String chunkId = null;
							CommonOperInterface cbt = new CommonConFactory().createInterface();
							StringBuilder query = new StringBuilder("");
							query.append("select singleclientblock.regionForBlock from singleclientblock as singleclientblock " + "inner join client_info as client_info on client_info.id=singleclientblock.accountid " + "where singleclientblock.regionStatus='1' and client_info.isLive='Block' and client_info.id='" + Accountinfo[1].toString() + "'");
							SessionFactory connectionSpace = HibernateSessionFactory.getSessionFactory();
							List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (data != null)
							{
								for (Iterator it = data.iterator(); it.hasNext();)
								{
									Object obdata = (Object) it.next();
									if (obdata != null)
										chunkId = obdata.toString();

								}
							}
							if (chunkId != null)
							{
								errorMessege = "Client Id Is Blocked Due To " + chunkId;
							}
							return "errorClientIdBlock";
						}
					}
					else
					{
						return "errorClientId";
					}

				}
			}
			catch (Exception e)
			{
				return ERROR;
			}
			return ERROR;
		}

	}

	/*
	 * // Rahul Srivastava Works Start >>>>>>>>>>>>>>>>>>>>>>>>> public String
	 * changePwd() throws InvalidKeyException, IllegalBlockSizeException,
	 * BadPaddingException { try { SessionFactory
	 * connectionSpace=(SessionFactory)session.get("connectionSpace"); String
	 * encryptedID=(String)session.get("encryptedID"); Map<String, Object>
	 * wherClause = new HashMap<String, Object>(); Map<String, Object>
	 * condtnBlock = new HashMap<String, Object>(); CommonOperInterface cbt =
	 * new CommonConFactory().createInterface();
	 * 
	 * String sql_query =
	 * " select password from useraccount where userID='"+encryptedID+"'"; List
	 * listData = cbt.executeAllSelectQuery(sql_query, connectionSpace); String
	 * pwd=(String) listData.get(0);
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * if(userType.equalsIgnoreCase("true")) { String
	 * opwd=Cryptography.encrypt(getOldPwd(),DES_ENCRYPTION_KEY);
	 * if(opwd.equals(pwd)){ String empName=(String)session.get("empName");
	 * wherClause.put("password",
	 * Cryptography.encrypt(getNewPwd(),DES_ENCRYPTION_KEY));
	 * condtnBlock.put("name", "'"+empName+"'"); boolean
	 * status2=cbt.updateTableColomn("useraccount", wherClause, condtnBlock,
	 * connectionSpace); if(status2) {
	 * addActionMessage(" Password Changed Successfully "); } }else {
	 * addActionMessage(" Old Password Is wrong "); } } else {
	 * wherClause.put("password",
	 * Cryptography.encrypt(getNewPwd(),DES_ENCRYPTION_KEY));
	 * condtnBlock.put("id", empName); boolean
	 * status=cbt.updateTableColomn("useraccount", wherClause, condtnBlock,
	 * connectionSpace);
	 * 
	 * if(status) { addActionMessage(" Password Changed Successfully ");} }
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } return SUCCESS;
	 * 
	 * } // Rahul Srivastava Works End >>>>>>>>>>>>>>>>>>>>>>>>>
	 */

	@SuppressWarnings(
	{ "unchecked"})
	public String changePwd() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		String returnResult = ERROR;
		try
		{
			if (getEmpName() != null && getNewPwd() != null)
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List dataList = new createTable().executeAllSelectQuery("select emp_name,mobile_no,user_account_id from primary_contact where id='" + getEmpName() + "'", connectionSpace);
				if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
				{
					String id = "", mobile = "", empname = "";
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && !object[0].toString().equals(""))
						{
							empname = object[0].toString();
						}
						if (object[1] != null && !object[1].toString().equals(""))
						{
							mobile = object[1].toString();
						}
						if (object[2] != null && !object[2].toString().equals(""))
						{
							id = object[2].toString();
						}

						if (!id.equals(""))
						{
							int userAccId = Integer.parseInt(id);
							if (userAccId != 0)
							{
								HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
								String updateQry = "UPDATE user_account SET password='" + Cryptography.encrypt(getNewPwd(), DES_ENCRYPTION_KEY) + "' WHERE id='" + userAccId + "'";
								boolean update = HUH.updateData(updateQry, connectionSpace);
								if (update)
								{
									if (smsNotification.equals("true"))
									{
										if (!empname.equals("") && !mobile.equals(""))
										{
											String msg = null;
											String dataFor = null;
											if (changeType != null && changeType.equalsIgnoreCase("header"))
											{
												dataFor = "Reset";
											}
											else
											{
												dataFor = "Changed";
											}
											msg = "Dear " + DateUtil.makeTitle(empname) + ", your password has been successfully " + dataFor + ". Your new password is " + getNewPwd() + ". Please contact administrator for any further assistance.";
											MsgMailCommunication communctn = new MsgMailCommunication();
											communctn.addScheduledMessage(DateUtil.makeTitle(empname), "Common ", mobile, msg, "", "Pending", "0", "Common", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimewithSeconds(), connectionSpace);
										}
									}
									addActionMessage("Password Changed Successfully !!!");
									returnResult = SUCCESS;
								}
								else
								{
									addActionMessage("Password Can't Be Reset. !!!");
									returnResult = SUCCESS;
								}
							}
							else
							{
								addActionMessage("Password Can't Be Reset. !!!");
								returnResult = SUCCESS;
							}
						}
					}
				}
				else
				{
					addActionMessage("Password Can't Be Reset. !!!");
					returnResult = SUCCESS;
				}
			}
			else
			{
				addActionMessage("Password Can't Be Reset. !!!");
				returnResult = SUCCESS;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return returnResult;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	@Override
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<BeanApps> getListuser()
	{
		return listuser;
	}

	public void setListuser(List<BeanApps> listuser)
	{
		this.listuser = listuser;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAccountid()
	{
		return accountid;
	}

	public void setAccountid(String accountid)
	{
		this.accountid = accountid;
	}

	public boolean isChunkSpaceBlock()
	{
		return chunkSpaceBlock;
	}

	public void setChunkSpaceBlock(boolean chunkSpaceBlock)
	{
		this.chunkSpaceBlock = chunkSpaceBlock;
	}

	public String getErrorMessege()
	{
		return errorMessege;
	}

	public void setErrorMessege(String errorMessege)
	{
		this.errorMessege = errorMessege;
	}

	public String getNewPwd()
	{
		return newPwd;
	}

	public void setNewPwd(String newPwd)
	{
		this.newPwd = newPwd;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getOldPwd()
	{
		return oldPwd;
	}

	public void setOldPwd(String oldPwd)
	{
		this.oldPwd = oldPwd;
	}

	public String getOrgLogoName()
	{
		return orgLogoName;
	}

	public void setOrgLogoName(String orgLogoName)
	{
		this.orgLogoName = orgLogoName;
	}

	public String getProfilePicName()
	{
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName)
	{
		this.profilePicName = profilePicName;
	}

	public String getEmpDept()
	{
		return empDept;
	}

	public void setEmpDept(String empDept)
	{
		this.empDept = empDept;
	}

	public String getSmsNotification()
	{
		return smsNotification;
	}

	public void setSmsNotification(String smsNotification)
	{
		this.smsNotification = smsNotification;
	}

	public String getEmailNotification()
	{
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification)
	{
		this.emailNotification = emailNotification;
	}

	public String getChangeType()
	{
		return changeType;
	}

	public void setChangeType(String changeType)
	{
		this.changeType = changeType;
	}

	public String getOrgImgPath()
	{
		return orgImgPath;
	}

	public void setOrgImgPath(String orgImgPath)
	{
		this.orgImgPath = orgImgPath;
	}

}