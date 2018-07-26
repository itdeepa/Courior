package easycbs.courior.ejbserver;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.EJBObject;

import easycbs.common.dto.ErrorVar;
import easycbs.common.parameter.LoginParameter;
import easycbs.courior.dto.BkngConsDto;

public interface BkngConsRemote extends EJBObject{
	
	public boolean checkConsNo(String consNo,ErrorVar error) throws RemoteException ;

	public HashMap<Object, Object> saveBkngConsignment(BkngConsDto bkngConsDto,ErrorVar error, String userId) throws RemoteException;
	public HashMap<Object, Object> saveBkngConsFwdDtl(BkngConsDto bkngConsDto, ErrorVar error,LoginParameter login)throws RemoteException;
	public HashMap<String,Object> trackBkngConsignment(String consNo,ErrorVar error)throws RemoteException;
	public String[][] deliveryCons(String DelType,ErrorVar error,LoginParameter login)throws RemoteException;
	public HashMap<Object, Object> saveDeliveryDtl(String ConsNo,ErrorVar error,LoginParameter login) throws RemoteException;
	public BkngConsDto fetchBkngCons(String consNo,ErrorVar error)throws RemoteException;
	public boolean checkDelivery(String consNo,ErrorVar error) throws RemoteException ;
	public BkngConsDto rcvPacketsAddr(String consNo,ErrorVar error)throws RemoteException;
	public HashMap<Object, Object> saveRecConsignement(BkngConsDto bkngConsDto,ErrorVar error, LoginParameter login) throws RemoteException;
	public HashMap<Object, Object> cancelRecCons(BkngConsDto bkngConsDto,ErrorVar error, LoginParameter login) throws RemoteException;
	public String[][] RptData(String rptCode,BkngConsDto bkngConsDto,ErrorVar error) throws RemoteException; 

}
