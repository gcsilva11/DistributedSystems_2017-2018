package actions.Login;

import actions.rmi.RMIServerInterface;

import java.net.MalformedURLException;
import java.rmi.*;

public class LoginBean{
	private String username; // username and password supplied by the user
	private String password;
	private RMIServerInterface server;
	String hostname = "localhost";
	int rmiPort = 6500;

	public LoginBean() {
		try {
			server = (RMIServerInterface) Naming.lookup("rmi://" + hostname + ":" + rmiPort + "/" + "vote_booth");
		} catch(MalformedURLException|NotBoundException|RemoteException e) {
			e.printStackTrace();
		}
	}

	public boolean getAuthenticateUser() throws RemoteException{
		return this.server.authenticateUser(username, password);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
