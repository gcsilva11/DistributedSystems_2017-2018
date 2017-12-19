package Web.Beans;

import java.rmi.RemoteException;

public class BoothBean extends RMIBean{

	int facID,elecID;

	public BoothBean(){
		super();
	}

	public void setFacID(String facID){
		this.facID = Integer.parseInt(facID);
	}

	public void setElecID(String elecID){
		this.elecID = Integer.parseInt(elecID);
	}

	public boolean getAddBoothSuccess() throws RemoteException {
		return this.server.addBooth(this.facID,this.elecID);
	}

	public boolean getRemoveBoothSuccess() throws RemoteException {
		return this.server.deleteBooth(this.facID,this.elecID);
	}

}
