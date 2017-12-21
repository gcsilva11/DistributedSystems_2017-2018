package Web.Beans;

import com.sun.org.apache.regexp.internal.RE;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends RMIBean {
    private int idUser, idElection, idList;
    private String username, electionName, listName, idFacebook;

    private ArrayList<Integer> eleicoes = null, listas = null;
    private ArrayList<String> elnames = null, lnames = null;

    private ArrayList<ArrayList<String>> placesVoted = new ArrayList<ArrayList<String>>();

    public UserBean() {
        super();
    }

    public boolean getJaVotou() throws RemoteException{
        return this.server.hasVoted(this.idUser,this.idElection);
    }

    public boolean getUserPodeVotar() throws RemoteException{
        return this.server.userCanVote(this.idUser,this.idElection);
    }

    public boolean getEleicaoEstaAtiva() throws RemoteException{
        return this.server.isElActive(this.idElection);
    }

    public int getElectionID(String name) throws RemoteException{
        return this.server.getElectionID(name);
    }

    public int getListID(String name) throws RemoteException{
        return this.server.getListID(name,this.idElection);
    }

    public boolean getVote() throws RemoteException{
        return this.server.antecipatedVote(this.idUser,this.idElection,this.idList);
    }


    public ArrayList<Integer> getEleicoes() throws RemoteException{
        return this.server.getEls();
    }

    public void setEleicoes(ArrayList<Integer> eleicoes) {
        this.eleicoes = eleicoes;
    }

    public ArrayList<Integer> getListas() throws RemoteException{
        return this.server.getElectionLists(this.idElection);
    }

    public void setListas(ArrayList<Integer> listas) throws RemoteException{
        this.listas = listas;
    }

    public int getIdUser() throws RemoteException {
        return this.server.getUserID(this.username);
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdElection(int idElection) {
        this.idElection = idElection;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }


    public String getElectionName() throws RemoteException{
        return this.server.getElName(this.idElection);
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public String getListName() throws  RemoteException{
        return this.server.getListName(this.idList, this.idElection);
    }

    public void setListName(String listName) {
        this.listName = listName;
    }


    public ArrayList<String> getElNames() {
        return elnames;
    }

    public void setElNames(ArrayList<String> elNames) {
        this.elnames = elNames;
    }

    public ArrayList<String> getLNames() {

        return lnames;
    }

    public void setLNames(ArrayList<String> lnames) {
        this.lnames = lnames;
    }


    public String getNameFac() throws RemoteException{
        return this.server.getFaculdadeVoted(this.idUser, this.idElection);
    }

    public ArrayList<Integer> getEls() throws RemoteException{
        return this.server.getEls();
    }

    public ArrayList<ArrayList<String>> getPlacesVoted() {
        return placesVoted;
    }

    public void setPlacesVoted(ArrayList<ArrayList<String>> placesVoted) {
        this.placesVoted = placesVoted;
    }


    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public boolean getAssociateUser() throws RemoteException{
        return this.server.associateFacebookUser(this.idUser,this.idFacebook);
    }
}