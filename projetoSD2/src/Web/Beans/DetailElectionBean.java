package Web.Beans;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DetailElectionBean extends RMIBean{

    private int eleID;

    private ArrayList<ArrayList<String>> votes;


	public DetailElectionBean(){
		super();
	}

    public void setEleID(String eleID) {
        this.eleID = Integer.parseInt(eleID);
    }

    public ArrayList<ArrayList<String>> getVotes() throws RemoteException{
        ArrayList<ArrayList<String>> votos = new ArrayList<>();
        if(!this.server.isElActive(this.eleID)){
            for (int i = 0;i<this.server.getElectionLists(this.eleID).size();i++){
                ArrayList<String> lista = new ArrayList<>();

                if(this.server.getListName(this.server.getElectionLists(this.eleID).get(i),this.eleID).equals("BLANKVOTE"))
                    lista.add("Votos em Branco");
                else if(this.server.getListName(this.server.getElectionLists(this.eleID).get(i),this.eleID).equals("NULLVOTE"))
                    lista.add("Votos Nulos");
                else lista.add(this.server.getListName(this.server.getElectionLists(this.eleID).get(i),this.eleID));

                lista.add(Integer.toString(this.server.getVotes(this.server.getElectionLists(this.eleID).get(i))));

                if(this.server.getTotalVotes(this.eleID) == 0)
                    lista.add("0");
                else {
                    int numVotos = this.server.getVotes(this.server.getElectionLists(this.eleID).get(i));
                    int numTotal = this.server.getTotalVotes(this.eleID);
                    double aux = ((double)numVotos / (double)numTotal) * 100;

                    String smtg = String.format("$%.2f",aux);
                    lista.add(smtg+" %");
                }
                votos.add(lista);
            }
            return votos;
        }
        else return null;
    }

    public void setVotes(ArrayList<ArrayList<String>> votes) {
        this.votes = votes;
    }
}
