package packageRMI;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import packageAdmin.VotingAdminInterface;

public class serverRMI extends UnicastRemoteObject implements VotingAdminInterface {

	
	private static final long serialVersionUID = 1L;

	public serverRMI() throws RemoteException {
		super();
	}

	public String sayHello() throws RemoteException {
		System.out.println("print do lado do servidor...!.");

		return "Hello, World!";
	}

	// =========================================================
	public static void main(String args[]) {

		try {
			serverRMI server = new serverRMI();
			Registry reg = LocateRegistry.createRegistry(6500);
			reg.rebind("vote_booth", server);
			System.out.println("Hello Server ready.");
		} catch (RemoteException re) {
			System.out.println("Exception in HelloImpl.main: " + re);
		}
	}

}