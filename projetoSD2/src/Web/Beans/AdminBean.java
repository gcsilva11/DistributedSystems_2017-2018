package Web.Beans;

import java.rmi.RemoteException;

public class AdminBean extends RMIBean{
	private String username, password,phone,address,expDate;
	private int faculdade,id,profType;

	public AdminBean(){
		super();
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public void setExpDate(String expDate){
		this.expDate = expDate;
	}

	public void setId(String id){
		this.id = Integer.parseInt(id);
	}

	public void setFaculdade(String fac){
		this.faculdade = Integer.parseInt(fac);
	}

	public void setProfType(String prof){
		this.profType=Integer.parseInt(prof);
	}

	public boolean getRegistrationSucess() throws RemoteException{
		return this.server.registerUser(this.id,this.username,this.password,this.phone,this.address,this.expDate,this.profType);
	}
}