package Web.Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UserBean extends RMIBean {
    private int idElection, idList, idFac, idUser;
    private String username;
    private ArrayList<String> eleicoes, listas;

    public UserBean() {
        super();
    }

    public int getIdFac() {
        return idFac;
    }

    public ArrayList<Integer> getMesaDeVotoEls() throws RemoteException{
        return this.server.getMesaDeVotoEls(idFac);
    }

    public boolean IsElActive() throws RemoteException {
        return this.server.isElActive(idElection);
    }

    public boolean hasVoted() throws RemoteException {
        return this.server.hasVoted(idUser, idElection);
    }

    public boolean userCanVote() throws RemoteException {
        return this.server.userCanVote(idUser, idElection);
    }

    public String getElName() throws RemoteException {
        return this.server.getElName(idElection);
    }

    public ArrayList<Integer> getElectionLists() throws RemoteException {
        return this.server.getElectionLists(idElection);
    }

    public String getListName() throws RemoteException {
        return this.server.getListName(idList);
    }

    public boolean voteElection() throws RemoteException {
        return this.server.voteElection(idUser, idElection, idList, idFac);
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser() throws RemoteException {
        idUser = this.server.getUserID(username);
    }

    public void setIdElection(int idElection) {
        this.idElection = idElection;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdFac(int idFac) {
        this.idFac = idFac;
    }
/*
    ArrayList<Integer> eleicoes = this.getUserBean().getMesaDeVotoEls();
    ArrayList<String> elNames = new ArrayList<>();

						for(int i = 0;i<eleicoes.size();i++) {
        this.getUserBean().setIdElection(i);
        if (!this.getUserBean().getElName().equals("") && !this.getUserBean().hasVoted() && this.getUserBean().userCanVote() && this.getUserBean().IsElActive()) {
            elNames.add(this.getUserBean().getElName());
        }
    }
						this.session.put("eleicoes",elNames);
    */
}