package Web.Beans;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class DetailElectionBean extends RMIBean{

    private int eleID;

    private ArrayList<String> election = new ArrayList<String>();


	public DetailElectionBean(){
		super();
	}

    public void setEleID(String eleID) {
        this.eleID = Integer.parseInt(eleID);
    }

    public ArrayList<String> getElection() throws RemoteException{
	    this.election = this.server.getEl(this.eleID);
        return this.election;
    }

    public void setElection(ArrayList<String> election) {
        this.election = election;
    }
}
