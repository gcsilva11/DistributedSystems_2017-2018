package RMIPackage;

import ServerPackage.TCPServerInterface;
import adminPackage.VotingAdminInterface;

import java.io.IOException;
import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class serverRMI extends UnicastRemoteObject implements VotingAdminInterface, TCPServerInterface {
	private static final long serialVersionUID = 1L;

	private static UserList users = new UserList();
	private static DepList departments = new DepList();
	private static candidateListList candidateList = new candidateListList();
	private static ElectionList elList = new ElectionList();
	private static ElectionList closedElections = new ElectionList();

	public serverRMI() throws RemoteException {
		super();
	}

	// =================================================================================================
	// VotingAdminInterface

	// Regista um novo user no ficheiro
	public boolean registerUser(User user) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		// Verifica se existe algum user com o mesmo ID
		for(int i=0;i<users.getUsers().size();i++){
			if(user.getID().equals(users.getUsers().get(i).getID())){
				System.out.println("ID already exists.");
				return false;
			}
		}
		
		//FALTA VER SO O DEP. EXISTE
		
		
		users.addUser(user);
		
		// Update ficheiro
		try{
			fo.abreEscrita("out/users.dat");
        	fo.escreveObjecto(users);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("New user registered.");

		return true;
	}

	// Adiciona departamento ao ficheiro
	public boolean registerDep(Department dep) throws RemoteException{
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		// Verifica se departamento já existe
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				System.out.println("Department ID already exists.");
				return false;
			}
		}
		
		departments.addDep(dep);
		
		//Update object file
		try{
			fo.abreEscrita("out/deps.dat");
        	fo.escreveObjecto(departments);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("New department registered.");
		
		return true;
		
	}

	// Edita ficheiro departamento
	public boolean editDep(Department dep) throws RemoteException{
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		// Verifica se o departamento existe
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				departments.getDeps().get(i).setDep(dep.getDep());
				departments.getDeps().get(i).setFac(dep.getFac());
				
				// Update ficheiro
				try{
					fo.abreEscrita("out/deps.dat");
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

	// Apaga informação departamento
	public boolean deleteDep(Department dep) throws RemoteException{
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		// Verifica se o departamento existe
		for(int i=0;i<departments.getDeps().size();i++){
			if(dep.getID().equals(departments.getDeps().get(i).getID())){
				
				departments.getDeps().remove(i);

				// Update ficheiro
				try{
					fo.abreEscrita("out/deps.dat");
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

	// Procura candidatos por ID
	public ArrayList <candidateList> getList(int type){
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

	//Create a new election
	public boolean newElection(Election el) throws RemoteException{
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;
		
		// Verifica  se titulo da eleicao ja existe
		for(int i=0;i<elList.getElections().size();i++){
			if(el.getTitle().equals(elList.getElections().get(i).getTitle())){
				exists = true;
			}
		}
				
		if(exists){
			System.out.println("Election title already exists...");
			return false;
		}
		
		elList.addELection(el);
		
		//Update ficheiro
		try{
			fo.abreEscrita("out/elections.dat");
        	fo.escreveObjecto(elList);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("Election created");
		return true;
		
	}

	//Procura id do tipo de pessoa definida
	public User findId(String s,int type) throws RemoteException{
		
		User sendUser = null;
		
		for(int i=0;i<users.getUsers().size();i++){
			if(users.getUsers().get(i).getID().equals(s)){
				if(users.getUsers().get(i).getProfession()==type){
					sendUser = new User(users.getUsers().get(i).getName(),users.getUsers().get(i).getID(),users.getUsers().get(i).getExpDate(),users.getUsers().get(i).getPhone(),users.getUsers().get(i).getProfession(),users.getUsers().get(i).getDepartment(),users.getUsers().get(i).getPassword());
				}
				else{
					sendUser = new User();
				}
			}
		}
		
		return sendUser;
	}
	
	public boolean createList(candidateList cl)throws RemoteException{

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;
		
		// Verifica se ID da lista de candidatos ja existe
		for(int i=0;i<candidateList.getCandidateList().size();i++){
			if(candidateList.getCandidateList().get(i).getID()==cl.getID()){
				exists = true;
			}
		}
				
		if(exists){
			System.out.println("List ID already exists...");
			return false;
		}
		
		candidateList.addCandidateList(cl);;
		
		//Update ficheiro
		try{
			fo.abreEscrita("out/lists.dat");
        	fo.escreveObjecto(cl);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		System.out.println("Candidate List created");
		return true;
		
	}
	
	public boolean deleteList(String id) throws java.rmi.RemoteException{
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;
		
		for(int i=0;i<candidateList.getCandidateList().size();i++){
			if(candidateList.getCandidateList().get(i).getID().equals(id)){
				candidateList.getCandidateList().remove(i);
				exists = true;
				//Update file
				try{
					fo.abreEscrita("out/lists.dat");
		        	fo.escreveObjecto(candidateList);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
				
			}
		}
		return exists;
	}

	public boolean editList(String id, String title)throws RemoteException{
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean done = false;
		
		for(int i=0;i<candidateList.getCandidateList().size();i++){
			if(candidateList.getCandidateList().get(i).getID().equals(id)){
				candidateList.getCandidateList().get(i).setName(title);
				done = true;
				//Update file
				try{
					fo.abreEscrita("out/lists.dat");
		        	fo.escreveObjecto(candidateList);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
			}
		}
		
		return done;
	}
	
	public boolean addBooth(String elTitle,ArrayList <String> depId)throws RemoteException{
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean done = true;
		
		for(int i=0;i<elList.getElections().size();i++){
			if(elList.getElections().get(i).getTitle().equals(elTitle)){
				for(int j=0;j<departments.getDeps().size();j++){
					for(int k=0;k<depId.size();k++){
						if(departments.getDeps().get(j).getID().equals(depId.get(k))){
							elList.getElections().get(i).addDep(departments.getDeps().get(j));
						}
					}
					
				}
			}
		}
		
		//Update ficheiro
		try{
			fo.abreEscrita("out/elections.dat");
        	fo.escreveObjecto(elList);
        	fo.fechaEscrita();
        }catch (Exception e){}
		
		return done;
		
	}
	
	public boolean editElec(Election el)throws RemoteException{
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		
		boolean done = false;
		
		for(int i=0;i<elList.getElections().size();i++){
			if(elList.getElections().get(i).getTitle().equals(el.getTitle())){
				elList.getElections().set(i, el);
				//Update ficheiro
				try{
					fo.abreEscrita("out/elections.dat");
		        	fo.escreveObjecto(elList);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
			
				done = true;
			}
		}
		
		
		return done;
	}
	
	public Election checkElecDate()throws java.rmi.RemoteException{
		
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		Election stub = null;
		
		for(int i=0;i<elList.getElections().size();i++){
			if(elList.getElections().get(i).getEndDate().before(Calendar.getInstance())&&elList.getElections().get(i).getClosed()==false){
				elList.getElections().get(i).setClosed();
				closedElections.addELection(elList.getElections().get(i));
				
				//Update ficheiro eleicoes
				try{
					fo.abreEscrita("out/elections.dat");
		        	fo.escreveObjecto(elList);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
				
				//Update ficheiro eleicoes
				try{
					fo.abreEscrita("out/closedelections.dat");
		        	fo.escreveObjecto(closedElections);
		        	fo.fechaEscrita();
		        }catch (Exception e){}
				
				return elList.getElections().get(i);
			}
		}
		
		return stub;
		
	}
	// =================================================================================================
	// TCPServerInterface

	// Devolve lista de users
	public ArrayList<User> getUsers() throws RemoteException{ return users.getUsers(); }

	// Devolve lista de candidatos
	public ArrayList<candidateList> getCandidateList() throws RemoteException{ return candidateList.getCandidateList(); }

	// Devolve lista de departamentos
	public ArrayList<Department> getDepList() throws RemoteException{ return departments.getDeps(); }

	public ArrayList<Election> getElList() throws RemoteException{ return elList.getElections(); }

	public Election getElection(String title)throws RemoteException{
		
		Election toSend = null;
	
		for(int i=0;i<elList.getElections().size();i++){
			if(elList.getElections().get(i).getTitle().equals(title)){
				toSend = elList.getElections().get(i);
			}
		}
		
		return toSend;
	}

	// =================================================================================================
	// Main
	public static void main(String args[]) {
		//RMIFailover rmiFailover;
		try {
			/*
			// Failover
			if(args.length == 2)
				rmiFailover = new RMIFailover(args[0], args[1]);
			else
				rmiFailover = new RMIFailover();
			rmiFailover.start();
		*/
			// Atualiza dados ficheiros
			setupObjectFiles();

			// Criação RMI
			serverRMI server = new serverRMI();
			Registry reg = LocateRegistry.createRegistry(6500);
			reg.rebind("vote_booth", server);
			System.out.println("RMI server connected");
		} catch (RemoteException re) {
			System.out.println("Exception in serverRMI.main: " + re);
		}
	}

	// Atualiza dados ficheiros
	public static void setupObjectFiles(){

		FicheiroDeObjectos foUser = new FicheiroDeObjectos();
		FicheiroDeObjectos foDeps = new FicheiroDeObjectos();
		FicheiroDeObjectos foLists = new FicheiroDeObjectos();
		FicheiroDeObjectos foElections = new FicheiroDeObjectos();
		FicheiroDeObjectos foClosedElections = new FicheiroDeObjectos();

		// Lê ficheiro users e adiciona-o a um array
		try{
			if (foUser.abreLeitura("out/users.dat")){
				users = (UserList) foUser.leObjecto();
				foUser.fechaLeitura();
			}

		}
		catch (Exception e) {
			System.out.println("Exception caught reading users.dat - "+e);
		}

		// Lê ficheiro departamentos e adiciona-o a um array
		try{
			if (foDeps.abreLeitura("out/deps.dat")){
				departments = (DepList) foDeps.leObjecto();
				foDeps.fechaLeitura();
			}

		}
		catch (Exception e) {
			System.out.println("Exception caught reading deps.dat - "+e);
		}

		// Lê ficheiro lista de candidatos e adiciona-o a um array
		try{
			if (foLists.abreLeitura("out/lists.dat")){
				candidateList = (candidateListList) foLists.leObjecto();
				foLists.fechaLeitura();
			}

		}
		catch (Exception e) {
			System.out.println("Exception caught reading lists.dat - "+e);
		}

		// Lê ficheiro eleiçoes e adiciona-o a um array
		try{
			if (foElections.abreLeitura("out/elections.dat")){
				elList = (ElectionList) foElections.leObjecto();
				foElections.fechaLeitura();
			}

		}
		catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - "+e);
		}
		
		// Le ficheiro de eleicoes fechadas e poe no array
		try{
			if (foClosedElections.abreLeitura("out/closedelections.dat")){
				closedElections = (ElectionList) foClosedElections.leObjecto();
				foClosedElections.fechaLeitura();
			}

		}
		catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - "+e);
		}
	}
}

class RMIFailover extends Thread{
	private DatagramSocket RMISocket = null;
	private int port;
	private InetAddress aHost;

	private byte[] buffer;


	public RMIFailover() throws RemoteException {
		this.port=7000;
		this.buffer = new byte[1024];
		try {
			this.aHost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {}
	}

	public RMIFailover(String hostname, String port) throws RemoteException {
		this.port = Integer.parseInt(port);
		this.buffer = new byte[1024];
		try{
			this.aHost = InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {}
	}

	public void run(){
		try {
			this.RMISocket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			try {
				this.RMISocket = new DatagramSocket();
			} catch (SocketException i) {}

			while (true) {
				// Tenta mandar pings
				String str = "PING";
				this.buffer = str.getBytes();

				try {
					DatagramPacket ping = new DatagramPacket(buffer, buffer.length, this.aHost, this.port);
					this.RMISocket.send(ping);
				} catch (IOException i) {
				}
			}
		}

		// Recebe pings
		while (true) {
			DatagramPacket request = new DatagramPacket(this.buffer, this.buffer.length);

			try {
				this.RMISocket.receive(request);
			} catch (IOException | NullPointerException i) { }

			System.out.println(buffer);
		}



	}
}