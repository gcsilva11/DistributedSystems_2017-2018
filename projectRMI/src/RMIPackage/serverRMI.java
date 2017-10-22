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

	// ==================================================================================================================
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

	//
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

	//
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

	//
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

	//
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

	//
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

	//
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

	// ==================================================================================================================
	// TCPServerInterface

	// Devolve lista de users
	public ArrayList<User> getUsers() throws RemoteException{ return users.getUsers(); }

	// Devolve lista de candidatos
	public ArrayList<candidateList> getCandidateList() throws RemoteException{ return candidateList.getCandidateList(); }

	// Devolve lista de departamentos
	public ArrayList<Department> getDepList() throws RemoteException{ return departments.getDeps(); }

	//
	public ArrayList<Election> getElList() throws RemoteException{ return elList.getElections(); }

	//
	public Election getElection(String title)throws RemoteException{
		
		Election toSend = null;
	
		for(int i=0;i<elList.getElections().size();i++){
			if(elList.getElections().get(i).getTitle().equals(title)){
				toSend = elList.getElections().get(i);
			}
		}
		
		return toSend;
	}

	// ==================================================================================================================
	// Main
	public static void main(String args[]) {
		RMIFailover UDPConn;

		String hostname;
		int serverPortOne, serverPortTwo, choice;
		boolean isMain = true;

		try {
			// argumentos da linha de comando: hostname, serverPort
			if (args.length != 2) {
				hostname = "localhost";
				serverPortOne = 7000;
				serverPortTwo = 7050;

			} else {
				hostname = args[0];
				serverPortOne = Integer.parseInt(args[1]);
				serverPortTwo = Integer.parseInt(args[2]);
			}

			isMain = selectRMI();

			if(isMain){
				System.out.println("Main Server starting...");
				// Inicia thread que lida com a conexão UDP
				UDPConn = new RMIFailover(hostname, serverPortOne, serverPortTwo, isMain);
				UDPConn.start();

				// Atualiza dados ficheiros
				setupObjectFiles();

				// Criação RMI
				serverRMI server = new serverRMI();
				Registry reg = LocateRegistry.createRegistry(6500);
				reg.rebind("vote_booth", server);
				System.out.println("Main RMI Server connected");
			}
			else{
				System.out.println("Backup Server starting...");
				// Inicia thread que lida com a conexão UDP
				UDPConn = new RMIFailover(hostname, serverPortOne, serverPortTwo, isMain);
				UDPConn.start();
				try {
					System.out.println("Backup Server waiting for Main Server to fail...");
					UDPConn.join();
				} catch (InterruptedException e) { }

				// Atualiza dados ficheiros
				setupObjectFiles();

				// Criação RMI
				serverRMI server = new serverRMI();
				Registry reg = LocateRegistry.createRegistry(6500);
				reg.rebind("vote_booth", server);
				System.out.println("Backup RMI Server connected");
			}

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

	// Seleciona se vai ser Main ou Backup RMI
	public static boolean selectRMI(){
		Scanner sc = new Scanner(System.in);
		System.out.println("RMI Server:\n1. Main Server\n2. Backup Server\n3. Exit");

		int choice = sc.nextInt();
		switch (choice) {
			case 1: return true;
			case 2: return false;
			case 3: System.exit(0);
			default: System.out.println("Non suported choice"); return selectRMI();
		}
	}
}

// Thread que trata do Failover
class RMIFailover extends Thread{
	public static  final boolean DEBUG = true;

	private DatagramSocket aSocketOne = null, aSocketTwo = null;

	private String hostname;
	private int serverPortOne, serverPortTwo;
	private boolean mainServer;

	public RMIFailover(String hostname, int serverPortOne, int serverPortTwo, boolean mainServer) {
		this.hostname = hostname;
		this.serverPortOne = serverPortOne;
		this.serverPortTwo = serverPortTwo;
		this.mainServer = mainServer;
	}

	public void run() {
		if(this.mainServer)
			isMainServer();
		else
			isNotMainServer();
	}

	void isMainServer(){
		String texto = "";
		byte [] msg = texto.getBytes();
		byte[] buffer = new byte[1000];
		int heartbeatsFailed = 0;

		try {
			// Abre socket UDP
			InetAddress aHost = InetAddress.getByName(this.hostname);
			this.aSocketOne = new DatagramSocket(this.serverPortOne);
			this.aSocketTwo = new DatagramSocket();

			while(heartbeatsFailed < 5){
				// Define timeout de recepção de heartbeat
				this.aSocketOne.setSoTimeout(1000);

				// Cria pacotes
				DatagramPacket request = new DatagramPacket(msg, msg.length, aHost, this.serverPortTwo);
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

				// Envia
				this.aSocketTwo.send(request);
				if(DEBUG) System.out.println("\t#DEBUG# Enviou heartbeat na porta: "+this.serverPortTwo);

				// Recebe
				try {
					this.aSocketOne.receive(reply);
					if(DEBUG) System.out.println("\t#DEBUG# Recebeu reply na porta: " + this.serverPortOne);
				} catch (SocketTimeoutException e) {
					if (DEBUG) System.out.println("\t#DEBUG# Heartbeat falhado");
					heartbeatsFailed++;
				}

				// Espera 1 segundo
				try { Thread.sleep(500); } catch (InterruptedException i) { }
			}

		}catch (SocketException e){ if(DEBUG) System.out.println("\t#DEBUG# Socket: " + e.getMessage());
		}catch (IOException e){ if(DEBUG) System.out.println("\t#DEBUG# IO: " + e.getMessage());
		}finally {if(this.aSocketOne != null || this.aSocketTwo != null) {this.aSocketOne.close();this.aSocketTwo.close();}}
	}

	void isNotMainServer(){
		String texto = "";
		byte [] msg = texto.getBytes();
		byte[] buffer = new byte[1000];
		int heartbeatsFailed = 0;

		try {
			// Abre socket UDP
			InetAddress aHost = InetAddress.getByName(this.hostname);
			this.aSocketOne = new DatagramSocket();
			this.aSocketTwo = new DatagramSocket(this.serverPortTwo);

			while(heartbeatsFailed < 5){
				// Define timeout de recepção de heartbeat
				this.aSocketTwo.setSoTimeout(1000);

				// Cria pacotes
				DatagramPacket request = new DatagramPacket(msg, msg.length, aHost, this.serverPortOne);
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

				// Envia
				this.aSocketOne.send(request);
				System.out.println("\t#DEBUG# Enviou reply na porta: "+this.serverPortOne);

				// Recebe
				try{
					this.aSocketTwo.receive(reply);
					if(DEBUG) System.out.println("\t#DEBUG# Recebeu heartbeat na porta: "+this.serverPortTwo);
				} catch (SocketTimeoutException e) {
					if(DEBUG) System.out.println("\t#DEBUG# Heartbeat falhado");
					heartbeatsFailed++;
				}

				// Espera 1 segundo
				try { Thread.sleep(500); } catch (InterruptedException i) { }
			}

		}catch (SocketException e){ if(DEBUG) System.out.println("\t#DEBUG# Socket: " + e.getMessage());
		}catch (IOException e){ if(DEBUG) System.out.println("\t#DEBUG# IO: " + e.getMessage());
		}finally {if(this.aSocketOne != null || this.aSocketTwo != null) {this.aSocketOne.close();this.aSocketTwo.close();}}
	}
}