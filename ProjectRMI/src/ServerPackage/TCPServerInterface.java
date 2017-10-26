package ServerPackage;

import RMIPackage.*;

import java.rmi.*;
import java.util.*;

public interface TCPServerInterface extends Remote {
    ArrayList<User> getUsers() throws RemoteException;
    ArrayList<candidateList> getCandidateList() throws RemoteException;
    ArrayList<Department> getDepList() throws RemoteException;
    ArrayList<Election> getElList() throws RemoteException;
    Election getElection(String title) throws RemoteException;
    void voteElection(User u, Election e, candidateList cl) throws RemoteException;
}
