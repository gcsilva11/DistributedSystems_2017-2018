package adminPackage;

import RMIPackage.*;

import java.rmi.*;
import java.util.*;

public interface VotingAdminInterface extends Remote {
	boolean registerUser(User user) throws java.rmi.RemoteException;
	boolean registerDep(Department dep) throws java.rmi.RemoteException;
	boolean editDep(Department dep) throws java.rmi.RemoteException;
	boolean deleteDep(Department dep) throws java.rmi.RemoteException;
	ArrayList <candidateList> getList(int type) throws java.rmi.RemoteException;
	boolean newElection(Election election) throws java.rmi.RemoteException;
	User findId(String s,int type) throws java.rmi.RemoteException;
	boolean createList(candidateList cl) throws java.rmi.RemoteException;
}