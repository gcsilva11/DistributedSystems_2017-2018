package Web.Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListAllElectionsBean extends RMIBean{

    private ArrayList<ArrayList<String>> elections = new ArrayList<ArrayList<String>>();
    private ArrayList<Integer> elecIds = new ArrayList<Integer>();



	public ListAllElectionsBean(){
		super();
	}

    public ArrayList<ArrayList<String>> getElections() throws RemoteException{

        this.elecIds.addAll(this.server.getElsID());
        for (int i = 0; i < this.elecIds.size(); i++) {
            this.elections.add(this.server.getEl(this.elecIds.get(i)));
        }
        return this.elections;
    }
}
