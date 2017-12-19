package Web.Beans;

import java.rmi.RemoteException;

public class EditElectionBean extends RMIBean{
	int eleID;
	String eleTitle,eleDesc,startDate,endDate;

	public EditElectionBean(){
		super();
	}

	public void setEleID(String id){
	    this.eleID = Integer.parseInt(id);
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

    public boolean getEleTextEditSuccess() throws RemoteException {
        return this.server.editELText(this.eleID,this.eleTitle,this.eleDesc);
	}

    public boolean getEleDateEditSuccess() throws RemoteException {
        return this.server.editElDate(this.eleID,this.startDate,this.endDate);
    }
}
