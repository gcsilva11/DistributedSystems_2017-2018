package Web.Beans;

import java.rmi.RemoteException;

public class CreateListBean extends RMIBean{

    private String listName;
    private int elecID, listType,userID;

	public CreateListBean(){
		super();
	}

    public void setListName(String listName){
	    this.listName = listName;
    }

    public void setElecID(String id){
	    this.elecID = Integer.parseInt(id);
    }

    public void setListType(String listType){
	    this.listType = Integer.parseInt(listType);
    }

    public void setUserID(String userID){
	    this.userID = Integer.parseInt(userID);
    }

    public boolean getListCreateSuccess() throws RemoteException {
        return this.server.addLista(this.listName,this.listType,0,this.elecID);
	}

    public boolean getUserAddSuccess() throws RemoteException {
	    int findListID = this.server.getListID(this.listName,this.elecID);
        return this.server.addUserLista(findListID,this.userID);
    }
}
