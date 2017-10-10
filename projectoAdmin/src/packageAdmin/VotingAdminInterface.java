package packageAdmin;

import java.rmi.*;

public interface VotingAdminInterface extends Remote {
	public String sayHello() throws java.rmi.RemoteException;
}