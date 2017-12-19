package Web.Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListUserVotePlacesBean extends RMIBean {
    private int idUser;
    private String username;
    private ArrayList<ArrayList<String>> placesVoted = new ArrayList<ArrayList<String>>();

    public ListUserVotePlacesBean() {
        super();
    }

    public ArrayList<ArrayList<String>> getPlacesVoted() throws RemoteException {
        ArrayList<Integer> elecIds = this.server.getEls();
        ArrayList<String> aux = new ArrayList<>();

        for (int i = 0; i < elecIds.size(); i++) {
            if(this.server.hasVoted(this.idUser,elecIds.get(i)) && this.server.userCanVote(this.idUser,elecIds.get(i)) && !this.server.isElActive(elecIds.get(i))){
                if(this.server.getFaculdadeVoted(this.idUser, elecIds.get(i)).equals("")) {
                    aux.add(Integer.toString(elecIds.get(i)));
                    aux.add(this.server.getFaculdadeVoted(this.idUser, elecIds.get(i)));
                    this.placesVoted.add(aux);
                } else{
                    aux.add(Integer.toString(elecIds.get(i)));
                    aux.add("Web");
                    this.placesVoted.add(aux);
                }
            }

            aux.clear();
        }
        return this.placesVoted;
    }
}
