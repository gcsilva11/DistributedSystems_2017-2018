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
        ArrayList<String> aux = null;
        System.out.println(elecIds.size());
        for (int i = 0; i < elecIds.size(); i++) {
            System.out.println(this.server.getFaculdadeVoted(this.idUser, elecIds.get(i)));

            if(this.server.getFaculdadeVoted(this.idUser, elecIds.get(i)).equals("")){
                aux.add(Integer.toString(elecIds.get(i)));
                aux.add(this.server.getFaculdadeVoted(this.idUser, elecIds.get(i)));

                System.out.println("oi1");

                if(aux!=null)
                    this.placesVoted.add(aux);
            }
            else{
                aux.add(Integer.toString(elecIds.get(i)));
                aux.add("Web");

                System.out.println("oi2");

                if(aux!=null)
                    this.placesVoted.add(aux);
            }
            aux = null;
        }
        return this.placesVoted;
    }
}
