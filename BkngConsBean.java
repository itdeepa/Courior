package easycbs.courior.ejbserver;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.struts.action.ActionErrors;

import com.mysql.jdbc.ResultSet;

import easycbs.common.DateTime.DateTimeFunction;
import easycbs.common.dataclasses.DataBaseClass;
import easycbs.common.dataclasses.SelectDataBean;
import easycbs.common.dto.ErrorVar;
import easycbs.common.ejbserver.PasswordBean;
import easycbs.common.logger.BMLogger;
import easycbs.common.parameter.LoginParameter;
import easycbs.common.web.ErrorHandler;
import easycbs.courior.dto.BkngConsDto;
import easycbs.courior.function.BkngConsFunction;

public class BkngConsBean implements SessionBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void ejbCreate(){}
	public void ejbRemove(){}
	public void ejbActivate(){}
	public void ejbPassivate(){}
	public void setSessionContext(SessionContext sc){}
	
	public boolean checkConsNo(String consNo,ErrorVar error)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		SelectDataBean bean=new SelectDataBean(db);
		boolean returnValue=false;
		try
		{
			String sql="Select count(*) FROM s_bookingcons where BookingCons_ConsNo='"+consNo+"' ";
			logger.info("checkConsNo Query Is :: "+sql);
			String[] data = bean.getRow(1,sql);
			if(data!=null && !data[0].equals("0")){
				returnValue=false;
			}
			else
				returnValue=true;
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return returnValue;
	}
	
	public HashMap<Object, Object> saveBkngConsignment(BkngConsDto bkngConsDto,ErrorVar error,String userId)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		BkngConsFunction bkngConsFunction =new BkngConsFunction();
		String returnValue="";
		try
		{
			logger.info("In saveBkngConsignment bean");
			returnValue=bkngConsFunction.saveBkngConsignment(bkngConsDto, db, userId);
			if(returnValue.equalsIgnoreCase("success")){
				error.add("1122", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue);
			}
			else{
				error.add("421", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue);
			}
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return reverMap;
	}
	
	public BkngConsDto fetchBkngCons(String consNo,ErrorVar error)
	{
		BMLogger logger = new BMLogger(PasswordBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		BkngConsFunction bkngConsFunction =new BkngConsFunction();
		BkngConsDto bkngConsDto= new BkngConsDto();
		String returnValue[][]=null;
		  
		try   
		{  
			logger.info("In saveBkngConsignment bean");
			returnValue=bkngConsFunction.fetchBkngCons(consNo, db);
			if(returnValue!=null){
		
				
				bkngConsDto.setBrnKid(returnValue[0][0]);
				bkngConsDto.setBrnCode(returnValue[0][1]);
				bkngConsDto.setConsDate(returnValue[0][2]);
				bkngConsDto.setConsNo(returnValue[0][3]);
				bkngConsDto.setDestination(returnValue[0][4]);
				bkngConsDto.setWeight(returnValue[0][5]);
				bkngConsDto.setDocTypeValue(returnValue[0][6]);
				bkngConsDto.setDimension(returnValue[0][7]);
				bkngConsDto.setShowDestbrn(returnValue[0][8]);
			}
			else{
				error.add("421", "BkngCons");
				reverMap.put("Errors", error);
				reverMap.put("Target", "saveFailure");
			}
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return bkngConsDto;
	}
	
	public HashMap<Object, Object> saveBkngConsFwdDtl(BkngConsDto bkngConsDto, ErrorVar error,LoginParameter login)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		BkngConsFunction bkngConsFunction =new BkngConsFunction();
		String brnCode = login.getsBranchCode();
		String returnValue="";
		String userId=String.valueOf(login.getlUserKid());
		try   
		{  
			logger.info("In saveBkngConsignment bean");
			if(BkngConsBean.isValidForward(bkngConsDto,brnCode)){
				returnValue=bkngConsFunction.saveBkngConsFwdDtl(bkngConsDto, brnCode,db,userId);
				if(returnValue.equalsIgnoreCase("success")){
					error.add("1122", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
				else if(returnValue.equalsIgnoreCase("Duplicate")){  
					error.add("147", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
		else if(returnValue.equalsIgnoreCase("FirstForward")){  
			error.add("71594", "accNo");
			reverMap.put("Errors", error);
			reverMap.put("Target", returnValue);
		}
				
				else if(returnValue.equalsIgnoreCase("Approved")){  
					error.add("1965", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
				else{
					error.add("421", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}	
			}
			else{
				error.add("1306", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue);
			}
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return reverMap;
}
	
	public HashMap<String,Object> trackBkngConsignment(String consNo,ErrorVar error)
	{
		BMLogger logger = new BMLogger(PasswordBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		BkngConsFunction bkngConsFunction =new BkngConsFunction();
		HashMap<String,Object> returnValue=null;
		  
		try   
		{  
			logger.info("In saveBkngConsignment bean");
			returnValue=bkngConsFunction.trackBkngConsignment(consNo, db);
			if(returnValue!=null){
				return returnValue;
			}
			else{
				error.add("421", "BkngCons");
				reverMap.put("Errors", error);
				reverMap.put("Target", "saveFailure");
			}
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return returnValue;
	}
	
	public String[][] deliveryCons(String DelType,ErrorVar error,LoginParameter login)
	{
		BMLogger logger = new BMLogger(PasswordBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		BkngConsFunction bkngConsFunction =new BkngConsFunction();
		String[][] returnValue=null;
		String brnCode = login.getsBranchCode(); 
		try   
		{  
			logger.info("In saveBkngConsignment bean");
			returnValue=bkngConsFunction.deliveryCons(DelType,brnCode, db);
			if(returnValue!=null){
				return returnValue;
			}
			else{
				error.add("421", "BkngCons");
				reverMap.put("Errors", error);
				reverMap.put("Target", "saveFailure");
			}
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return returnValue;
	}

	public HashMap<Object, Object> saveDeliveryDtl(String ConsNo,ErrorVar error,LoginParameter login)
	{
	BMLogger logger = new BMLogger(BkngConsBean.class);
	DataBaseClass db = new DataBaseClass() ;
	HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
	BkngConsFunction bkngConsFunction =new BkngConsFunction();
	SelectDataBean bean=new SelectDataBean(db);
	String returnValue="";
	String[] consNoSplit=null;
	String brnCode=login.getsBranchCode();
	String usrId=String.valueOf(login.getlUserKid());
	try
	{
		consNoSplit=ConsNo.split(",");
		logger.info("In saveBkngConsignment bean");

		String sql="Select count(*) FROM S_DELIVERYDTL where DELIVERYDTL_CONSNO IN('"+ConsNo+"' )";
		logger.info("checkConsNo Query Is :: "+sql); 
		
		String[] data = bean.getRow(1,sql);
		
		if(data==null || data[0].equals("0")){
			
			returnValue=bkngConsFunction.saveDeliveryDtl(consNoSplit, db, brnCode,usrId); 
			if(returnValue.equalsIgnoreCase("success")){
				error.add("1965", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue);
			}
			else{
				error.add("421", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue);
			}
		}
		else{  
			error.add("147", "accNo");
			reverMap.put("Errors", error);
			reverMap.put("Target", returnValue);
		}

	}catch(Exception e) { 
		e.printStackTrace() ;
	}
	finally { 
		db.Finalize() ;
	}
	return reverMap;
}
	
	public boolean checkDelivery(String consNo,ErrorVar error)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		SelectDataBean bean=new SelectDataBean(db);
		boolean returnValue=false;
		try
		{
			String sql="Select count(*) FROM s_deliveryDtl where deliveryDtl_consNo='"+consNo+"'  ";
			logger.info("checkConsNo Query Is :: "+sql);
			String[] data = bean.getRow(1,sql);
			if(data!=null && data[0].equals("0")){
				returnValue=false;
			}
			else
				returnValue=true;
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return returnValue;
	}

	public BkngConsDto rcvPacketsAddr(String consNo,ErrorVar error)
	{
		BMLogger logger = new BMLogger(PasswordBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		SelectDataBean bean=new SelectDataBean(db);
		BkngConsDto bkngConsDto= new BkngConsDto();
		String returnValue[]=null;
		  
		try   
		{  
			logger.info("In saveBkngConsignment bean");
			String sql="select BookingCons_Destination from s_bookingcons where BookingCons_ConsNo='"+consNo+"' ";
			returnValue =bean.getRow(1,sql);
			if(returnValue!=null){
				bkngConsDto.setConsNo(consNo);
				bkngConsDto.setDestination(returnValue[0]);
			}
			else{
				error.add("421", "BkngCons");
				reverMap.put("Errors", error);
				reverMap.put("Target", "saveFailure");
			}
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return bkngConsDto;
	}

	public HashMap<Object, Object> saveRecConsignement(BkngConsDto bkngConsDto,ErrorVar error, LoginParameter login)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		String returnValue="success";
		SelectDataBean bean=new SelectDataBean(db);
		String userId=String.valueOf(login.getlUserKid());
		String brnCode=login.getsBranchCode();

		try
		{
			logger.info("In saveBkngConsignment bean");
			String photoName = bkngConsDto.getFileDataName();
			File f = new File(photoName);
			byte b[] = (byte[]) bkngConsDto.getFileDataArr();
			OutputStream out = new FileOutputStream(f);

			for (int j = 0; j < b.length; j++) {
				out.write(b[j]);
			}
			out.flush();
			out.close();
			FileInputStream fis = new FileInputStream(f);
			db.doBeginTrans();

			String sql="Select count(*) FROM s_deliveryDtl where deliveryDtl_consNo='"+bkngConsDto.getConsNo()+"' and deliveryDtl_flag IN ('C' , 'R')";
			logger.info("checkConsNo Query Is :: "+sql);
			String[] data = bean.getRow(1,sql);

			if(data!=null && !data[0].equals("0")){
				error.add("72076", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue); ;
			}
			else{
				String Ssql="update s_bookingcons set BookingCons_Status='C' where BookingCons_ConsNo='"+bkngConsDto.getConsNo()+"' ";
				db.db_update(Ssql);

				String Ssql1="update s_TrackBookingCons set TrackBookingCons_Status='C',TrackBookingCons_Date=GETDATE(),TrackBookingCons_UserId='"+userId+"' "
						+ "where TrackBookingCons_ConsNo='"+bkngConsDto.getConsNo()+"' and  TrackBookingCons_Status='D'";
				db.db_update(Ssql1);

				String SQL="INSERT INTO s_TrackHist ( TrackHist_ConsID,  TrackHist_BranchID,TrackHist_StatusID,TrackHist_Date,TrackHist_UserID) "+        
						" SELECT BookingCons_kid,'"+brnCode+"',4, GETDATE() ,'"+userId+"' FROM s_BookingCons "+
						" WHERE BookingCons_ConsNo ='"+bkngConsDto.getConsNo()+"' ";
				db.db_update(SQL);

				PreparedStatement pstmt = (db.getConnectionDB()).prepareStatement("update s_deliveryDtl set "
						+ "deliveryDtl_signImage=?,deliveryDtl_flag=?,deliveryDtl_userid=? where deliveryDtl_consNo=? " );
				pstmt.setBinaryStream(1, fis, (int) f.length());
				pstmt.setString(2, "C");
				pstmt.setString(3, userId);
				pstmt.setString(4, bkngConsDto.getConsNo()); 
				pstmt.executeUpdate();
				pstmt.close();
				logger.info("insert");
				
				String Ssql2="update s_deliveryDtl set deliveryDtl_date=GETDATE() where deliveryDtl_consNo='"+bkngConsDto.getConsNo()+"' ";
				db.db_update(Ssql2);
				
				db.doCommit();

				if(db.getErrorCount()>0){
					error.add("1122", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
				else{
					error.add("1122", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue); 
				}

			}
		}
		catch(Exception e) { 
			e.printStackTrace() ;
			db.doRollback();
		}
		finally { 
			db.Finalize() ;
		}
		return reverMap;
	}
	
	public static boolean isValidForward(BkngConsDto bkngConsDto,String loginBrn)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		SelectDataBean bean=new SelectDataBean(db);
		boolean returnValue=false;
		String sql="";
		String[] data =null;
		try
		{
		
			sql="Select count(*) FROM s_TrackBookingCons where TrackBookingCons_ConsNo='"+bkngConsDto.getConsNo()+"' ";
			logger.info("checkConsNo Query Is :: "+sql);
			data = bean.getRow(1,sql);
			if((data==null || data[0].equals("0")) && loginBrn.equals(bkngConsDto.getBrnCode())) // for first forwarding
				returnValue=true;
			
			else if(!returnValue){
				sql="select TrackBookingCons_FwdBrnCode from s_TrackBookingCons where "
						+ "TrackBookingCons_kid in (select max(TrackBookingCons_kid) from s_TrackBookingCons where "
						+ "TrackBookingCons_ConsNo='"+bkngConsDto.getConsNo()+"') ";
				data=bean.getRow(1,sql);
				if(data!=null && data[0].equals(loginBrn))
					returnValue=true;
			
			}
			else
				returnValue=false;
			
		}catch(Exception e) { 
			e.printStackTrace() ;
		}
		finally { 
			db.Finalize() ;
		}
		return returnValue;
	}
	
	public HashMap<Object, Object> cancelRecCons(BkngConsDto bkngConsDto,ErrorVar error, LoginParameter login)
	{
		BMLogger logger = new BMLogger(BkngConsBean.class);
		DataBaseClass db = new DataBaseClass() ;
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		String returnValue="success";
		String userId=String.valueOf(login.getlUserKid());
		String brnCode=login.getsBranchCode();
		SelectDataBean bean=new SelectDataBean(db);

		try
		{
			logger.info("In saveBkngConsignment bean");
			db.doBeginTrans();

			String sql="Select count(*) FROM s_deliveryDtl where deliveryDtl_consNo='"+bkngConsDto.getConsNo()+"' and deliveryDtl_flag IN('R', 'C' )";
			logger.info("checkConsNo Query Is :: "+sql);
			String[] data = bean.getRow(1,sql);

			if(data!=null && !data[0].equals("0")){
				error.add("72077", "accNo");
				reverMap.put("Errors", error);
				reverMap.put("Target", returnValue); 
			}
			else{

				String Ssql="update s_bookingcons set BookingCons_Status='R' where BookingCons_ConsNo='"+bkngConsDto.getConsNo()+"' ";
				db.db_update(Ssql);

				String Ssql1="update s_TrackBookingCons set TrackBookingCons_Status='R',TrackBookingCons_UserId='"+userId+"' "
						+ "where TrackBookingCons_ConsNo='"+bkngConsDto.getConsNo()+"' and  TrackBookingCons_Status='D'";
				db.db_update(Ssql1);

				String SQL="INSERT INTO s_TrackHist ( TrackHist_ConsID,  TrackHist_BranchID,TrackHist_StatusID,TrackHist_Date,TrackHist_UserID) "+        
						" SELECT BookingCons_kid,'"+brnCode+"',5, GETDATE() ,'"+userId+"' FROM s_BookingCons "+
						" WHERE BookingCons_ConsNo ='"+bkngConsDto.getConsNo()+"' ";
				db.db_update(SQL);

				PreparedStatement pstmt = (db.getConnectionDB()).prepareStatement("update s_deliveryDtl set "
						+ "deliveryDtl_reason=?,deliveryDtl_flag=?,deliveryDtl_userid=? where deliveryDtl_consNo=? " );
				pstmt.setString(1, bkngConsDto.getReason());
				pstmt.setString(2, "R");
				pstmt.setString(3, userId);
				pstmt.setString(4, bkngConsDto.getConsNo()); 
				pstmt.executeUpdate();
				pstmt.close();
				logger.info("insert");
				db.doCommit();
				if(db.getErrorCount()>0){
					error.add("1122", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
				else{  
					error.add("1122", "accNo");
					reverMap.put("Errors", error);
					reverMap.put("Target", returnValue);
				}
			}


		}catch(Exception e) { 
			e.printStackTrace() ;
			db.doRollback();
		}
		finally { 
			db.Finalize() ;
		}
		return reverMap;
	}
	
	public String[][] RptData(String rptCode,BkngConsDto bkngConsDto,ErrorVar error)
{
	BMLogger logger = new BMLogger(PasswordBean.class);
	DataBaseClass db = new DataBaseClass() ;
	HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
	BkngConsFunction bkngConsFunction =new BkngConsFunction();
	String[][] returnValue=null;  
	try   
	{  
		logger.info("In saveBkngConsignment bean");
		returnValue=bkngConsFunction.RptData(rptCode,bkngConsDto, db);
		if(returnValue!=null){
			return returnValue;		
		} 
		else{
			error.add("421", "BkngCons");
			reverMap.put("Errors", error);
			reverMap.put("Target", "saveFailure");
		}
		
	}catch(Exception e) { 
		e.printStackTrace() ;
	}
	finally { 
		db.Finalize() ;
	}
	return returnValue;
}
}
