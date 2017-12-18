package Web.Beans;

import java.rmi.RemoteException;

public class CreateElectionBean extends RMIBean{
	int eleID,eleType,elecFacID;
	String eleTitle,eleDesc,startDate,endDate;

	public CreateElectionBean(){
		super();
	}

	public void setEleID(String id){
	    this.eleID = Integer.parseInt(id);
    }

    public void setEleType(String type){
	    this.eleType = Integer.parseInt(type);
    }

    public void setElecFacID(String facID){
        this.elecFacID = Integer.parseInt(facID);
    }

    public void setEleTitle(String title){
        this.eleTitle = title;
    }

    public void setEleDesc(String desc){
        this.eleDesc = desc;
    }

    public void setEleStartDate(String start){
        this.startDate = start;
    }

    public void setEleEndDate(String end){
        this.endDate = end;
    }

    public boolean getEleCreateSuccess() throws RemoteException {
        return this.server.addEl(this.eleID,this.eleTitle,this.eleDesc,this.eleType,this.startDate,this.endDate,this.elecFacID);
	}
}
