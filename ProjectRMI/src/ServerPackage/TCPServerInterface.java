package ServerPackage;

import RMIPackage.*;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.*;
import java.util.*;

public interface TCPServerInterface extends Remote {
    ArrayList<User> getUsers() throws RemoteException;
    ArrayList<candidateList> getCandidateList() throws RemoteException;
    ArrayList<Department> getDepList() throws RemoteException;
    ArrayList<Election> getElList() throws RemoteException;
    Election getElection(String title) throws RemoteException;
    public void voteElection(User u, Election e, candidateList cl) throws RemoteException;
    boolean hasVoted(User u, Election e) throws RemoteException;
    ArrayList<Department> getDeps(Election e) throws RemoteException;
}
