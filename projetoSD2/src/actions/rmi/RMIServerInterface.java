package actions.rmi;

import java.rmi.*;

public interface RMIServerInterface extends Remote {

    boolean checkFaculdade(int facID) throws RemoteException;
    boolean registerUser(int numberID, String name, String password, String phone, String address, String expDate, int profession) throws RemoteException;
    boolean addUserFac(int numberID, int faculdadeID) throws RemoteException;
    void editUser(int numberID, String name, String password, String phone, String expDate, String address) throws RemoteException;
    boolean deleteUser(int numberID) throws RemoteException;
    boolean registerFac(String facName) throws RemoteException;
    boolean registerDep(String depName, int facID) throws RemoteException;
    boolean registerUnit(int facID) throws RemoteException;
    boolean editFac(int facID, String facName) throws RemoteException;
    boolean editDep(int depID, String depName) throws RemoteException;
    boolean deleteFac(int facID) throws RemoteException;
    boolean deleteDep(int depID) throws RemoteException;
    boolean deleteUnit(int facID) throws RemoteException;
    boolean addEl(int eleicaoID, String title, String description, int type, String startDate, String endDate,int faculdadeID) throws RemoteException;
    boolean editELText(int eleicaoID, String title, String description) throws RemoteException;
    boolean editElDate(int eleicaoID, String startdate, String enddate) throws RemoteException;
    boolean deleteEL(int eleicaoID) throws RemoteException;
    boolean addLista(String name, int type, int numvotes, int eleicaoID) throws RemoteException;
    boolean addUserLista(int listid, int userid) throws RemoteException;
    boolean deleteLista(int listid) throws RemoteException;
    boolean addBooth(int facid, int electionid) throws RemoteException;
    boolean deleteBooth(int facid, int electionid) throws RemoteException;
    boolean addUserBooth(int userID, int facID , int electionID) throws RemoteException;
    boolean removeUserBooth(int userID, int facID , int electionID) throws RemoteException;
    int getElectionType(int id) throws RemoteException;
    int getListType(int id) throws RemoteException;
    int getUserType(int id) throws RemoteException;
    int getListID(String name, int electionID) throws RemoteException;
    boolean elAntecipated(int electionID) throws RemoteException;
    boolean elTerminated(int electionID) throws RemoteException;
    boolean antecipatedVote(int userID, int electionID, int listID) throws RemoteException;
    int[] getEls() throws RemoteException;
    boolean pastEl(int electionID) throws RemoteException;
    int getVotes(int listID) throws RemoteException;
    int getTotalVotes(int electionID) throws RemoteException;
    String getFaculdadeVoted(int userID, int electionID) throws RemoteException;
    boolean authenticateUser(String name, String password) throws RemoteException;
    boolean identifyID(int userID, int facID) throws RemoteException;
    int[] getMesaDeVotoEls(int facid) throws RemoteException;
    boolean isElActive (int electionID) throws RemoteException;
    boolean voteElection(int userID, int electionID, int listID, int facID) throws RemoteException;
    boolean hasVoted(int userID, int electionID) throws RemoteException;
    boolean userCanVote (int userID, int electionID) throws RemoteException;
    String getElName(int id) throws RemoteException;
    int[] getElectionLists(int electionid) throws RemoteException;
    String getListName(int id) throws RemoteException;
}