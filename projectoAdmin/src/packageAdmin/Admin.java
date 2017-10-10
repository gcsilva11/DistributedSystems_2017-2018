package packageAdmin;
import java.rmi.registry.LocateRegistry;

public class Admin {

	public static void main(String[] args) {
		
		
		try{
			
			VotingAdminInterface vote = (VotingAdminInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
			String test = vote.sayHello();
			System.out.println(test);
		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}

	}

}
