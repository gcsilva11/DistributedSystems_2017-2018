package Web.Beans;

import java.rmi.*;

public class LoginBean extends RMIBean{
	private String username, password;
	private int faculdade;

	public LoginBean(){
		super();
	}

	public boolean getAuthenticateUser() throws RemoteException{
		return this.server.authenticateUser(username, password);
	}

	public boolean getIdentifyName() throws RemoteException{
		return this.server.identifyName(username,faculdade);
	}

	public boolean getCheckFaculdade() throws RemoteException{
		return this.server.checkFaculdade(this.faculdade);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFaculdade(String faculdade) {
		this.faculdade = Integer.parseInt(faculdade);
	}
}
