package ServerPackage;

import RMIPackage.*;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.*;
import java.util.*;

public interface TCPServerInterface extends Remote {
    /**
     *
     * @param facID
     * @return
     * @throws RemoteException
     */
    boolean checkFaculdade(int facID) throws RemoteException;

    /**
     *
     * @param id
     * @param name
     * @param password
     * @return
     * @throws RemoteException
     */
    boolean authenticateUser(int id, String name, String password) throws RemoteException;

    /**
     *
     * @param userID
     * @param facID
     * @return
     * @throws RemoteException
     */
    boolean identifyID(int userID, int facID) throws RemoteException;

    /**
     *
     * @param facid
     * @return
     * @throws RemoteException
     */
    int[] getMesaDeVotoEls(int facid) throws RemoteException;

    /**
     *
     * @param userID
     * @param electionID
     * @return
     * @throws RemoteException
     */
    boolean hasVoted(int userID, int electionID) throws RemoteException;

    /**
     *
     * @param userID
     * @param electionID
     * @return
     * @throws RemoteException
     */
    boolean userCanVote (int userID, int electionID) throws RemoteException;

    /**
     *
     * @param electionID
     * @return
     * @throws RemoteException
     */
    boolean isElActive (int electionID) throws RemoteException;

    /**
     *
     * @param userID
     * @param electionID
     * @param listID
     * @param facID
     * @return
     * @throws RemoteException
     */
    boolean voteElection(int userID, int electionID, int listID, int facID) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    String getElName(int id) throws RemoteException;

    /**
     *
     * @param electionid
     * @return
     * @throws RemoteException
     */
    int[] getElectionLists(int electionid) throws RemoteException;

    /**
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    String getListName(int id) throws RemoteException;
}
