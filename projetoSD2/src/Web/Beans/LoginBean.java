package Web.Beans;

import java.rmi.*;

public class LoginBean extends RMIBean{
	private String username, password, idFacebook;

	public LoginBean(){
		super();
	}

	public boolean getAuthenticateUser() throws RemoteException{
		return this.server.authenticateUser(this.username, this.password);
	}

	public String getAuthenticateFacebook() throws RemoteException{
		return this.server.authenticateFacebook(this.idFacebook);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}
}
