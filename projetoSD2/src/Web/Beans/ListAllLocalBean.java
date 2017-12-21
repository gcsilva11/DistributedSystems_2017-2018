package Web.Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListAllLocalBean extends RMIBean{
    private int idElection, idUser;

    private ArrayList<ArrayList<String>> placesVoted = new ArrayList<>();

    public ListAllLocalBean(){
        super();
    }

    public boolean getJaVotou() throws RemoteException{
        return this.server.hasVoted(this.idUser,this.idElection);
    }

    public boolean getUserPodeVotar() throws RemoteException{
        return this.server.userCanVote(this.idUser,this.idElection);
    }

    public String getNameFac() throws RemoteException{
        return this.server.getFaculdadeVoted(this.idUser, this.idElection);
    }

    public ArrayList<Integer> getEls() throws RemoteException{
        return this.server.getEls();
    }

    public void setIdElection(int idElection) {
        this.idElection = idElection;
    }

    public void setIdUser(String idUser) {
        this.idUser = Integer.parseInt(idUser);
    }

    public ArrayList<ArrayList<String>> getPlacesVoted() {
        return placesVoted;
    }

    public void setPlacesVoted(ArrayList<ArrayList<String>> placesVoted) {
        this.placesVoted = placesVoted;
    }

}
