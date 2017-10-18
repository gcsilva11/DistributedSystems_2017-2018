package projectRMI;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;

import adminPackage.VotingAdminInterface;

public class serverRMI extends UnicastRemoteObject implements VotingAdminInterface {

	
	private static final long serialVersionUID = 1L;
	private static UserList users = new UserList();
	private static DepList departments = new DepList();
	private static candidateListList candidateList = new candidateListList();
	private static ElectionList elList = new ElectionList();

	public serverRMI() throws RemoteException {
		super();
	}

	public boolean registerUser(User user) throws RemoteException {
		//Register a new user in the object file
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		//Go through the currently registered people and check if there's one with same ID
		for(int i=0;i<users.getUsers().size();i++){
			if(user.getID().equals(users.getUsers().get(i).getID())){
				System.out.println("ID already exists.");
				return false;
			}
		}
		
		//FALTA VER SO O DEP. EXISTE
		
		
		users.addUser(user);
		
		//Update object file
		try{
			fo.abreEscrita("users.dat");
        	fo.escreveObjecto(users);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("New user registered.");

		return true;
	}

	public boolean registerDep(Department dep) throws RemoteException{
		//Add new department to object file
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		//Check if dep already exists
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				System.out.println("Department ID already exists.");
				return false;
			}
		}
		
		departments.addDep(dep);
		
		//Update object file
		try{
			fo.abreEscrita("deps.dat");
        	fo.escreveObjecto(departments);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("New department registered.");
		
		return true;
		
	}

	public boolean editDep(Department dep) throws RemoteException{
		//Edit department information
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		//Check if dep exists
		
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				departments.getDeps().get(i).setDep(dep.getDep());
				departments.getDeps().get(i).setFac(dep.getFac());
				
				//Update object file
				try{
					fo.abreEscrita("deps.dat");
		        	fo.escreveObjecto(departments);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
				
				
				System.out.println("Department edited!");
				return true;
			}
		}
		
		System.out.println("Department ID not found...");
		return false;
		
	}
	
	public boolean deleteDep(Department dep) throws RemoteException{
		//Delete department information
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		//Check if dep exists
		
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				
				departments.getDeps().remove(i);
				
				//Update object file
				try{
					fo.abreEscrita("deps.dat");
		        	fo.escreveObjecto(departments);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
				
				
				System.out.println("Department removed!");
				return true;
			}
		}
		
		System.out.println("Department ID not found...");
		return false;
		
	}
	
	public ArrayList <candidateList> getList(int type){
		
		//Search for desirable candidate lists by id
		ArrayList <candidateList> chosenLists = new ArrayList <candidateList>();
		if(type==1){
			for(int i=0;i<candidateList.getCandidateList().size();i++){
				if(candidateList.getCandidateList().get(i).getType()==type){
					chosenLists.add(candidateList.getCandidateList().get(i));
				}
			}
		}
		else{
			for(int i=0;i<candidateList.getCandidateList().size();i++){
				chosenLists.add(candidateList.getCandidateList().get(i));
			}
		}
		return chosenLists;
	}
	
	public boolean newElection(Election el) throws RemoteException{
		//Create a new election
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;
		
		//Check if  election title exists
		
		for(int i=0;i<elList.getElections().size();i++){
			if(el.getTitle().equals(elList.getElections().get(i).getTitle())){
				exists = true;
			}
		}
				
		if(exists){
			System.out.println("Election title already exists...");
			return false;
		}
		
		//Update object file
		try{
			fo.abreEscrita("elections.dat");
        	fo.escreveObjecto(elList);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("Election created");
		return true;
		
	}
	
	// =========================================================
	public static void main(String args[]) {

		try {
			setupObjectFiles();
			serverRMI server = new serverRMI();
			Registry reg = LocateRegistry.createRegistry(6500);
			reg.rebind("vote_booth", server);
			System.out.println("RMI server connected.");
		} catch (RemoteException re) {
			System.out.println("Exception in serverRMI.main: " + re);
		}
	}
	
	public static void setupObjectFiles(){

		FicheiroDeObjectos foUser = new FicheiroDeObjectos();
		FicheiroDeObjectos foDeps = new FicheiroDeObjectos();
		FicheiroDeObjectos foLists = new FicheiroDeObjectos();
		FicheiroDeObjectos foElections = new FicheiroDeObjectos();
		
		//Read users file and add them to user Array
		try{
			if (foUser.abreLeitura("users.dat")){
                users = (UserList) foUser.leObjecto();
                foUser.fechaLeitura();
            }
			
		}
		catch (Exception e) {
            System.out.println("Exception caught reading users.dat - "+e);
        }
		
		//Read deparment file and add them to department Array
		try{
			if (foDeps.abreLeitura("deps.dat")){
                departments = (DepList) foDeps.leObjecto();
                foDeps.fechaLeitura();
            }
			
		}
		catch (Exception e) {
            System.out.println("Exception caught reading deps.dat - "+e);
        }
		
		//Read list file and add them to candidateList list 
		try{
			if (foLists.abreLeitura("lists.dat")){
                candidateList = (candidateListList) foLists.leObjecto();
                foLists.fechaLeitura();
            }
			
		}
		catch (Exception e) {
            System.out.println("Exception caught reading lists.dat - "+e);
        }
		
		//Read elections file and add them to electionList Array
		try{
			if (foElections.abreLeitura("elections.dat")){
                elList = (ElectionList) foElections.leObjecto();
                foElections.fechaLeitura();
            }
			
		}
		catch (Exception e) {
            System.out.println("Exception caught reading elections.dat - "+e);
        }
	}
}