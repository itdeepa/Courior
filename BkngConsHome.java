package easycbs.courior.ejbserver;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

import easycbs.courior.dto.BkngConsDto;

public interface BkngConsHome extends EJBHome{
	BkngConsRemote create() throws RemoteException,CreateException;
	 
}
