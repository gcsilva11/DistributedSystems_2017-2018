package Web.Beans;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ListAllLocalBean extends RMIBean{

    private ArrayList<ArrayList<String>> elections = new ArrayList<ArrayList<String>>();

    public ListAllLocalBean(){
        super();
    }

    public ArrayList<ArrayList<String>> getElections() throws RemoteException {
        ArrayList<Integer> elecIds = this.server.getElsID();
        for (int i = 0; i < elecIds.size(); i++)
            this.elections.add(this.server.getEl(elecIds.get(i)));
        return this.elections;
    }
}
