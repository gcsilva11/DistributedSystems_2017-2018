package actions.Login;

import actions.rmi.RMIServerInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class LoginBean {
	private String username; // username and password supplied by the user
	private String password;
	private RMIServerInterface server;
	String hostname = "localhost";
	int rmiPort = 3000;

	public LoginBean() {
		try {
			server =(RMIServerInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
		}
		catch(NotBoundException|RemoteException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}



}
