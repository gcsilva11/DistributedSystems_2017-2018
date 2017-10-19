package adminPackage;

import java.rmi.*;
import java.util.ArrayList;

import RMIPackage.*;

public interface VotingAdminInterface extends Remote {
	public boolean registerUser(User user) throws java.rmi.RemoteException;
	public boolean registerDep(Department dep) throws java.rmi.RemoteException;
	public boolean editDep(Department dep) throws java.rmi.RemoteException;
	public boolean deleteDep(Department dep) throws java.rmi.RemoteException;
	public ArrayList <candidateList> getList(int type) throws java.rmi.RemoteException;
	public boolean newElection(Election election) throws java.rmi.RemoteException;
}