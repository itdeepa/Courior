package easycbs.courior.function;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import easycbs.common.DateTime.DateTimeFunction;
import easycbs.common.dataclasses.DataBaseClass;
import easycbs.common.dataclasses.SelectDataBeanRowCol;
import easycbs.courior.dto.BkngConsDto;

public class BkngConsFunction {
	
public String saveBkngConsignment(BkngConsDto bkngConsDto, DataBaseClass db,String userId) { 
	
	ArrayList<String> inList = new ArrayList<String>();
	ResultSet rs = null;
	String docTypeValue="";
	
		if(bkngConsDto.getDocTypeValue().equalsIgnoreCase("Doc"))
			docTypeValue="Y";
		else
			docTypeValue="N";;
		
		try { 
			db.doBeginTrans();
			inList.add("0");
			inList.add(bkngConsDto.getBrnKid());
			inList.add(bkngConsDto.getBrnCode());
			inList.add(DateTimeFunction.dateToStrFmt(bkngConsDto.getConsDate(),"yyyy/MM/dd"));
			inList.add(bkngConsDto.getConsNo());
			inList.add(bkngConsDto.getDestinationBr());
			inList.add(bkngConsDto.getDestination());
			inList.add(bkngConsDto.getWeight());
			inList.add(docTypeValue);
			inList.add(bkngConsDto.getDimension());
			inList.add("P");
			inList.add(userId);
			
			ArrayList<Object> outList = db.execStoredProc("ConsignmentProcess",inList, 0, 1);
			db.doCommit();
			rs = (ResultSet)outList.get(0);
			
			return "success" ;
			
		}catch(Exception e)  { 
			db.doRollback();
			e.printStackTrace();
		}
		
		
		return "failure" ; 
		
	}

public String[][] fetchBkngCons(String consNo, DataBaseClass db) { 
	
	ArrayList<String> inList = new ArrayList<String>();
	ArrayList<Object> outList=new ArrayList<Object>();
	ResultSet rs;
	SelectDataBeanRowCol bean = new SelectDataBeanRowCol(db);
	String data[][] = null;
		try {   

			inList.add("1");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add(consNo);
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			
			
			outList = db.execStoredProc("ConsignmentProcess",inList, 0, 1);
			rs = (ResultSet) outList.get(0);
			if (rs != null && rs.next()) {
				data = bean.convertResultSetToArrayRowCol(rs, 9);
			}
			
			
		}catch(Exception e)  { 
			e.printStackTrace();
		}
		return data ; 
		
	}

public String saveBkngConsFwdDtl(BkngConsDto bkngConsDto,String brnCode, DataBaseClass db,String userId) { 
	
	ArrayList<String> inList = new ArrayList<String>();
	ResultSet rs = null;
	String docTypeValue="";
	String data[][] = null;
	String target="success";
	SelectDataBeanRowCol bean = new SelectDataBeanRowCol(db);
	
		if(bkngConsDto.getFwdArrivValue().equalsIgnoreCase("fwd"))
			docTypeValue="F";
		else
			docTypeValue="A";
		
		try { 
			db.doBeginTrans();
			inList.add("2");
 			inList.add(bkngConsDto.getFwdbrncode());
			inList.add(brnCode);
			inList.add("");
			inList.add(bkngConsDto.getConsNo());
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");;
			inList.add("");
			inList.add(docTypeValue);
			inList.add(userId);
			
	    	int count = 0;
			if(!bkngConsDto.getFwdArrivValue().equalsIgnoreCase("fwd")){
			String sSQl="select count(*) from S_TRACKBOOKINGCONS where trackbookingcons_consno = '"+bkngConsDto.getConsNo().trim()+"'";
			 rs = db.db_select(sSQl);  
			if(rs.next()){
				count= rs.getInt(1);  
			}
			if(count ==0){
				target="FirstForward"; 
				return target ; 
			 }
			}
						
			
			ArrayList<Object> outList = db.execStoredProc("ConsignmentProcess",inList, 0, 1);
			db.doCommit();
			rs = (ResultSet)outList.get(0);
			if (rs != null && rs.next()) {
				data = bean.convertResultSetToArrayRowCol(rs, 2);
				if(data[0][0].equalsIgnoreCase("2")){
				target="Duplicate";
				}
				else if(data[0][0].equalsIgnoreCase("3")){
					target="Approved";
					}
			}
						
		}catch(Exception e)  { 
			target="failure";
			db.doRollback();
			e.printStackTrace();
		}
		
		
		return target ; 
		
	}

public HashMap<String,Object> trackBkngConsignment(String consNo, DataBaseClass db){
	
	ArrayList<String> inList = new ArrayList<String>();
	ArrayList<Object> outList=new ArrayList<Object>();
	HashMap<String,Object> map = new HashMap<String,Object>() ;
	ResultSet rs,rs1;
	SelectDataBeanRowCol bean = new SelectDataBeanRowCol(db);
	String data[][] = null,data1 [][]=null;
		try {   

			inList.add("3");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add(consNo);
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			
			
			
			outList = db.execStoredProc("ConsignmentProcess",inList, 0, 1);
			rs = (ResultSet) outList.get(0);
			if (rs != null && rs.next()) {
				data = bean.convertResultSetToArrayRowCol(rs, 6);
				map.put("bkngData", data);
				rs1 = (ResultSet) outList.get(1);
				if (rs1 != null && rs1.next()) {
					data1 = bean.convertResultSetToArrayRowCol(rs1, 2);
					map.put("trackData", data1);
					
				}
			}
			
		}catch(Exception e)  { 
			e.printStackTrace();
		}
		return map ; 
}

public String[][] deliveryCons(String delPacket,String brnCode, DataBaseClass db){
	
	ArrayList<String> inList = new ArrayList<String>();
	ArrayList<Object> outList=new ArrayList<Object>();
	ResultSet rs;
	SelectDataBeanRowCol bean = new SelectDataBeanRowCol(db);
	String data[][] = null;
		try {   

			inList.add("4");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add(brnCode);
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add("");
			inList.add(delPacket.toUpperCase());
			
			
			outList = db.execStoredProc("ConsignmentProcess",inList, 0, 1);
			rs = (ResultSet) outList.get(0);
			if(delPacket.equalsIgnoreCase("INCOMING")){
			if (rs != null && rs.next()) {
				data = bean.convertResultSetToArrayRowCol(rs, 6);
			}
				}
				else if(delPacket.equalsIgnoreCase("OUTGOING")){
				if (rs != null && rs.next()) {
					data = bean.convertResultSetToArrayRowCol(rs, 2);
					}
			}
			
		}catch(Exception e)  { 
			e.printStackTrace();
		}
		return data; 
}

public String saveDeliveryDtl(String[] consNo, DataBaseClass db, String brnCode, String usrId){

	
	String result="";
	String sql,sSql,SQL="";

	try { 
		db.doBeginTrans();
		if(consNo!=null){ 
			for(int i=0; i<consNo.length; i++){ 
				
				sSql="INSERT INTO S_TRACKBOOKINGCONS(TrackBookingCons_ConsNo,TrackBookingCons_BrnCode,TrackBookingCons_Date, "+                  
                 " TrackBookingCons_Status,TrackBookingCons_UserId) VALUES('"+consNo[i]+"','"+brnCode+"',GETDATE(),'D','"+usrId+"')";
				
				SQL="INSERT INTO s_TrackHist ( TrackHist_ConsID,  TrackHist_BranchID,TrackHist_StatusID,TrackHist_Date,TrackHist_UserID) "+        
						" SELECT BookingCons_kid,'"+brnCode+"',3, GETDATE() ,'"+usrId+"' FROM s_BookingCons "+
						" WHERE BookingCons_ConsNo ='"+consNo[i]+"' ";

				sql="INSERT INTO S_DELIVERYDTL(DELIVERYDTL_CONSNO,DELIVERYDTL_DATE,DELIVERYDTL_USERID,DELIVERYDTL_FLAG) "+
						"VALUES('"+consNo[i]+"',GETDATE(),'"+usrId+"','D')";
				
				db.db_Insert(sql); 
				db.db_Insert(SQL); 
				db.db_Insert(sSql); 
				result="success";

			}
			db.doCommit();
		}

	}
	catch(Exception e)  { 
		db.doRollback();
		e.printStackTrace();
	}
	return result ; 
}

public String[][] RptData(String rptCode,BkngConsDto bkngConsDto, DataBaseClass db){
	
	ArrayList<String> inList = new ArrayList<String>();
	ArrayList<Object> outList=new ArrayList<Object>();
	ResultSet rs;
	
	SelectDataBeanRowCol bean = new SelectDataBeanRowCol(db);;
	String data[][] = null;
		try {
			String sql="select mnu_ename from s_mnu where mnu_next='"+rptCode+"'";
			data = bean.getData(sql);
			
			inList.add(DateTimeFunction.dateToStrFmt(bkngConsDto.getFromDate(),"yyyy/MM/dd"));
			inList.add(DateTimeFunction.dateToStrFmt(bkngConsDto.getToDate(),"yyyy/MM/dd"));
			inList.add(bkngConsDto.getBrnCode());
						
			outList = db.execStoredProc("usp_"+data[0][0].replaceAll(" ", ""),inList, 0, 1);
			rs = (ResultSet) outList.get(0);
			if (rs != null && rs.next()) {
				data = bean.convertResultSetToArrayRowCol(rs, 9);
				
			}
			else{
                data=null;
			}
	
			
		}catch(Exception e)  { 
			e.printStackTrace();
		}
		return data; 
}
}// class end




