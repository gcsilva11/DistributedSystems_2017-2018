package adminPackage;

import RMIPackage.*;

import java.rmi.*;
import java.util.*;

public interface VotingAdminInterface extends Remote {
	/**
	 *
	 * @param numberID
	 * @param name
	 * @param password
	 * @param phone
	 * @param address
	 * @param date
	 * @param profession
	 * @return
	 * @throws RemoteException
	 */
	boolean registerUser(int numberID, String name, String password, String phone, String address, String date, int profession) throws RemoteException;

	/**
	 *
	 * @param numberID
	 * @param name
	 * @param password
	 * @param phone
	 * @param expDate
	 * @param address
	 * @throws RemoteException
	 */
	void editUser(int numberID, String name, String password, String phone, String expDate, String address) throws RemoteException;

	/**
	 *
	 * @param numberID
	 * @param faculdadeID
	 * @return
	 * @throws RemoteException
	 */
	boolean addUserFac(int numberID, int faculdadeID) throws RemoteException;

	/**
	 *
	 * @param listid
	 * @param userid
	 * @return
	 * @throws RemoteException
	 */
	boolean addUserLista(int listid, int userid) throws RemoteException;

	/**
	 *
	 * @param numberID
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteUser(int numberID) throws RemoteException;

	/**
	 *
	 * @param facName
	 * @return
	 * @throws RemoteException
	 */
	boolean registerFac(String facName) throws RemoteException;

	/**
	 *
	 * @param depName
	 * @param facID
	 * @return
	 * @throws RemoteException
	 */
	boolean registerDep(String depName, int facID) throws RemoteException;

	/**
	 *
	 * @param facID
	 * @return
	 * @throws RemoteException
	 */
	boolean registerUnit(int facID) throws RemoteException;

	/**
	 *
	 * @param facID
	 * @param facName
	 * @return
	 * @throws RemoteException
	 */
	boolean editFac(int facID, String facName) throws RemoteException;

	/**
	 *
	 * @param depID
	 * @param depName
	 * @return
	 * @throws RemoteException
	 */
	boolean editDep(int depID, String depName) throws RemoteException;

	/**
	 *
	 * @param facID
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteFac(int facID) throws RemoteException;

	/**
	 *
	 * @param depID
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteDep(int depID) throws RemoteException;

	/**
	 *
	 * @param facID
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteUnit(int facID) throws RemoteException;

	/**
	 *
	 * @param eleicaoID
	 * @param title
	 * @param description
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param faculdadeID
	 * @return
	 * @throws RemoteException
	 */
	boolean addEl(int eleicaoID, String title, String description, int type, String startDate, String endDate, int faculdadeID) throws RemoteException;

	/**
	 *
	 * @param eleicaoID
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteEL(int eleicaoID) throws RemoteException;

	/**
	 *
	 * @param eleicaoID
	 * @param title
	 * @param description
	 * @return
	 * @throws RemoteException
	 */
	boolean editELText(int eleicaoID, String title, String description) throws RemoteException;

	/**
	 *
	 * @param eleicaoID
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws RemoteException
	 */
	boolean editElDate(int eleicaoID, String startdate, String enddate) throws RemoteException;

	/**
	 *
	 * @param name
	 * @param type
	 * @param numvotes
	 * @param eleicaoID
	 * @return
	 * @throws RemoteException
	 */
	boolean addLista(String name, int type, int numvotes, int eleicaoID) throws RemoteException;

	/**
	 *
	 * @param listid
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteLista(int listid) throws RemoteException;

	/**
	 *
	 * @param facid
	 * @param electionid
	 * @return
	 * @throws RemoteException
	 */
	boolean addBooth(int facid, int electionid) throws RemoteException;

	/**
	 *
	 * @param facid
	 * @param electionid
	 * @return
	 * @throws RemoteException
	 */
	boolean deleteBooth(int facid, int electionid) throws RemoteException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	int getElectionType(int id) throws RemoteException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	int getListType(int id) throws RemoteException;

	/**
	 *
	 * @param name
	 * @param electionID
	 * @return
	 * @throws RemoteException
	 */
	int getListID(String name, int electionID) throws RemoteException;

	/**
	 *
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	int getUserType(int id) throws RemoteException;
}