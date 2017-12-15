package Web.Beans;

import java.rmi.RemoteException;

public class UserBean extends RMIBean {
    private int idElection, idList, idFac, idUser;
    private String username;

    private int[] eleicoes, listas;

    public UserBean() {
        super();
    }

    public int[] getEleicoes() throws RemoteException{
        return this.server.getMesaDeVotoEls(idFac);
    }

    public boolean IsElActive() throws RemoteException{
        return this.server.isElActive(idElection);
    }

    public boolean hasVoted() throws RemoteException{
        return this.server.hasVoted(idUser,idElection);
    }

    public boolean userCanVote() throws RemoteException{
        return this.server.userCanVote(idUser,idElection);
    }

    public String getElName() throws RemoteException{
        return this.server.getElName(idElection);
    }

    public int[] getElectionLists() throws RemoteException{
        return this.server.getElectionLists(idElection);
    }

    public String getListName() throws RemoteException{
        return this.server.getListName(idList);
    }

    public int getUserID() throws RemoteException{
        return this.server.getUserID(username);
    }

    public boolean voteElection() throws RemoteException{
        return this.server.voteElection(idUser,idElection,idList,idFac);
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

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
