package RMIPackage;

import ServerPackage.TCPServerInterface;
import adminPackage.VotingAdminInterface;

import java.io.IOException;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.sql.Date;
import java.util.*;
import java.sql.*;

public class serverRMI extends UnicastRemoteObject implements VotingAdminInterface, TCPServerInterface {
	private static final long serialVersionUID = 1L;

	private static Connection connection = null;


	public serverRMI() throws RemoteException {
		super();
	}

	// ==================================================================================================================
	// VotingAdminInterface

	// Regista um novo user no ficheiro
	public boolean registerUser(int numberID, String name, String password, String phone, String expDate) throws RemoteException {
		String query = "INSERT INTO USER VALUES ("+numberID+",'"+name+"' ,'"+password+"' ,'"+phone+"' ,STR_TO_DATE('"+expDate+"', '%d-%m-%Y %H:%i:%s'));";

		if (connectDB()) {
			if(serverRMI.updateDB(query))
				return true;
		}
		return false;
	}
/*
	// Adiciona departamento ao ficheiro
	public boolean registerDep(Department dep) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		// Verifica se departamento já existe
		for (int i = 0; i < departments.getDeps().size(); i++) {
			if (dep.getID().equals(departments.getDeps().get(i).getID())) {
				System.out.println("Department ID already exists.");
				return false;
			}
		}

		departments.addDep(dep);

		//Update object file
		try {
			fo.abreEscrita("out/deps.dat");
			fo.escreveObjecto(departments);
			fo.fechaEscrita();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("New department registered.");

		return true;

	}
	*/
	/*
	// Edita ficheiro departamento
	public boolean editDep(Department dep) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		// Verifica se o departamento existe
		for (int i = 0; i < departments.getDeps().size(); i++) {
			if (dep.getID().equals(departments.getDeps().get(i).getID())) {
				departments.getDeps().get(i).setDep(dep.getDep());
				departments.getDeps().get(i).setFac(dep.getFac());

				// Update ficheiro
				try {
					fo.abreEscrita("out/deps.dat");
					fo.escreveObjecto(departments);
					fo.fechaEscrita();
				} catch (Exception e) {
				}


				System.out.println("Department edited!");
				return true;
			}
		}

		System.out.println("Department ID not found...");
		return false;

	}

	// Apaga informação departamento
	public boolean deleteDep(Department dep) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		// Verifica se o departamento existe
		for (int i = 0; i < departments.getDeps().size(); i++) {
			if (dep.getID().equals(departments.getDeps().get(i).getID())) {

				departments.getDeps().remove(i);

				// Update ficheiro
				try {
					fo.abreEscrita("out/deps.dat");
					fo.escreveObjecto(departments);
					fo.fechaEscrita();
				} catch (Exception e) {
				}


				System.out.println("Department removed!");
				return true;
			}
		}

		System.out.println("Department ID not found...");
		return false;

	}

	// Procura candidatos por ID
	public ArrayList<candidateList> getList(int type) {
		ArrayList<candidateList> chosenLists = new ArrayList<candidateList>();
		if (type == 1) {
			for (int i = 0; i < listOfCandidateLists.getCandidateList().size(); i++) {
				if (listOfCandidateLists.getCandidateList().get(i).getType() == type) {
					chosenLists.add(listOfCandidateLists.getCandidateList().get(i));
				}
			}
		} else {
			for (int i = 0; i < listOfCandidateLists.getCandidateList().size(); i++) {
				chosenLists.add(listOfCandidateLists.getCandidateList().get(i));
			}
		}
		return chosenLists;
	}

	//Create a new election
	public boolean newElection(Election el) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;

		// Verifica  se titulo da eleicao ja existe
		for (int i = 0; i < elList.getElections().size(); i++) {
			if (el.getTitle().equals(elList.getElections().get(i).getTitle())) {
				exists = true;
			}
		}

		if (exists) {
			System.out.println("Election title already exists...");
			return false;
		}

		elList.addELection(el);

		//Update ficheiro
		try {
			fo.abreEscrita("out/elections.dat");
			fo.escreveObjecto(elList);
			fo.fechaEscrita();
		} catch (Exception e) {
		}

		System.out.println("Election created");
		return true;

	}

	//Procura id do tipo de pessoa definida
	public User findId(String s, int type) throws RemoteException {

		User sendUser = null;

		for (int i = 0; i < users.getUsers().size(); i++) {
			if (users.getUsers().get(i).getID().equals(s)) {
				if (users.getUsers().get(i).getProfession() == type) {
					sendUser = new User(users.getUsers().get(i).getName(), users.getUsers().get(i).getID(), users.getUsers().get(i).getExpDate(), users.getUsers().get(i).getPhone(), users.getUsers().get(i).getProfession(), users.getUsers().get(i).getDepartment(), users.getUsers().get(i).getPassword());
				} else {
					sendUser = new User();
				}
			}
		}

		return sendUser;
	}

	//
	public boolean createList(candidateList cl) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;

		// Verifica se ID da lista de candidatos ja existe
		for (int i = 0; i < listOfCandidateLists.getCandidateList().size(); i++) {
			if (listOfCandidateLists.getCandidateList().get(i).getID() == cl.getID()) {
				exists = true;
			}
		}

		if (exists) {
			System.out.println("List ID already exists...");
			return false;
		}

		listOfCandidateLists.addCandidateList(cl);
		;

		//Update ficheiro
		try {
			fo.abreEscrita("out/lists.dat");
			fo.escreveObjecto(listOfCandidateLists);
			fo.fechaEscrita();
		} catch (Exception e) {
		}

		System.out.println("Candidate List created");
		return true;

	}

	//
	public boolean deleteList(String id) throws java.rmi.RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean exists = false;

		for (int i = 0; i < listOfCandidateLists.getCandidateList().size(); i++) {
			if (listOfCandidateLists.getCandidateList().get(i).getID().equals(id)) {
				listOfCandidateLists.getCandidateList().remove(i);
				exists = true;
				//Update file
				try {
					fo.abreEscrita("out/lists.dat");
					fo.escreveObjecto(listOfCandidateLists);
					fo.fechaEscrita();
				} catch (Exception e) {
				}

			}
		}
		return exists;
	}

	//
	public boolean editList(String id, String title) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean done = false;

		for (int i = 0; i < listOfCandidateLists.getCandidateList().size(); i++) {
			if (listOfCandidateLists.getCandidateList().get(i).getID().equals(id)) {
				listOfCandidateLists.getCandidateList().get(i).setName(title);
				done = true;
				//Update file
				try {
					fo.abreEscrita("out/lists.dat");
					fo.escreveObjecto(listOfCandidateLists);
					fo.fechaEscrita();
				} catch (Exception e) {
				}
			}
		}

		return done;
	}

	//
	public boolean addBooth(String elTitle, ArrayList<String> depId) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean done = false;
		Department addThisOne = null;

		//Check if department is already added
		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(elTitle)) {
				if(!(elList.getElections().get(i).getViableDeps() ==null)) {
					for (int j = 0; j < elList.getElections().get(i).getViableDeps().size(); j++) {
						for (int k = 0; k < depId.size(); k++) {
							if (elList.getElections().get(i).getViableDeps().get(j).getID().equals(depId.get(k))) {
								return false;
							}
						}
					}
				}
			}
		}


		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(elTitle)) {
				for (int j = 0; j < departments.getDeps().size(); j++) {
					for (int k = 0; k < depId.size(); k++) {
						if (departments.getDeps().get(j).getID().equals(depId.get(k))) {
							System.out.println("Adding table");
							addThisOne = departments.getDeps().get(j);
							elList.getElections().get(i).addDep(addThisOne);
							depsWithBooth.getDeps().add(addThisOne);
							done = true;
						}
					}

				}
			}
		}
		//Update ficheiro
		try {
			fo.abreEscrita("out/elections.dat");
			fo.escreveObjecto(elList);
			fo.fechaEscrita();
		} catch (Exception e){
			e.printStackTrace();
		}

		try {
			fo.abreEscrita("out/booths.dat");
			fo.escreveObjecto(depsWithBooth);
			fo.fechaEscrita();
		} catch (Exception e){
			e.printStackTrace();
		}
		return done;
	}

	//
	public boolean removeBooth(String elTitle, ArrayList<String> depId) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		boolean done = false;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(elTitle)) {
				for (int j = 0; j < elList.getElections().get(i).getViableDeps().size(); j++) {
					for (int k=0;k<depId.size();k++){
						if (elList.getElections().get(i).getViableDeps().get(j).getID().equals(depId.get(k))){
							for(int l=0;l<depsWithBooth.getDeps().size();l++){
								System.out.println("Removing table");
								if(depsWithBooth.getDeps().get(l).getID().equals(depId.get(k))){
									depsWithBooth.getDeps().remove(l);
									done = true;
								}
							}
							elList.getElections().get(i).getViableDeps().remove(j);
						}
					}
				}
			}
		}
		//Update ficheiro
		try {
			fo.abreEscrita("out/elections.dat");
			fo.escreveObjecto(elList);
			fo.fechaEscrita();
		} catch (Exception e){
			e.printStackTrace();
		}

		try {
			fo.abreEscrita("out/booths.dat");
			fo.escreveObjecto(depsWithBooth);
			fo.fechaEscrita();
		} catch (Exception e){
			e.printStackTrace();
		}
		return done;
	}

	//
	public boolean editElec(Election el,String oldElecName) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		boolean done = false;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(oldElecName)) {
				elList.getElections().set(i, el);
				//Update ficheiro
				try {
					fo.abreEscrita("out/elections.dat");
					fo.escreveObjecto(elList);
					fo.fechaEscrita();
				} catch (Exception e) {
				}
				System.out.println("Election edited");
				done = true;
			}
		}

		System.out.println("Couldn't find election title");
		return done;
	}

	//
	public User getUser(String userID) throws java.rmi.RemoteException{

		User toSend = null;

		for(int i=0;i<users.getUsers().size();i++){
			if(users.getUsers().get(i).getID().equals(userID)){
				toSend = users.getUsers().get(i);
			}
		}

		return toSend;
	}
	//
	public ArrayList <Election> checkElecDate() throws java.rmi.RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getEndDate().before(Calendar.getInstance()) && elList.getElections().get(i).getClosed() == false) {
				elList.getElections().get(i).setClosed();
				closedElections.addELection(elList.getElections().get(i));

				System.out.println("Election closed.");


				//Update ficheiro eleicoes
				try {
					fo.abreEscrita("out/elections.dat");
					fo.escreveObjecto(elList);
					fo.fechaEscrita();
				} catch (Exception e) {
					e.printStackTrace();
				}

				//Update ficheiro eleicoes
				try {
					fo.abreEscrita("out/closedelections.dat");
					fo.escreveObjecto(closedElections);
					fo.fechaEscrita();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return closedElections.getElections();
			}
		}

		return closedElections.getElections();

	}

	public ArrayList <Department> checkTables() throws java.rmi.RemoteException{
		return depsWithBooth.getDeps();
	}

	// ==================================================================================================================
	// TCPServerInterface

	// Devolve lista de users
	public ArrayList<User> getUsers() throws RemoteException {
		return users.getUsers();
	}

	// Devolve lista de candidatos
	public ArrayList<candidateList> getCandidateList() throws RemoteException {
		return listOfCandidateLists.getCandidateList();
	}

	// Devolve lista de departamentos
	public ArrayList<Department> getDepList() throws RemoteException {
		return departments.getDeps();
	}

	//
	public ArrayList<Election> getElList() throws RemoteException {
		return elList.getElections();
	}

	//
	public Election getElection(String title) throws RemoteException {

		Election toSend = null;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(title)) {
				toSend = elList.getElections().get(i);
			}
		}

		return toSend;
	}

	//
	public void voteElection(User u, Election e, candidateList cl) throws RemoteException {
		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		for (int i = 0; i < users.getUsers().size(); i++) {
			for(int j = 0;j<elList.getElections().size();j++){
				if(elList.getElections().get(j).getTitle().equals(e.getTitle())){
					if (users.getUsers().get(i).getID().equals(u.getID())) {
						users.getUsers().get(i).setVotes(elList.getElections().get(j), cl);
						elList.getElections().get(j).incrementVotes();

						System.out.println(users.getUsers().get(i).getVotes());
					}
				}
			}


		}
		// Update ficheiro
		try {
			fo.abreEscrita("out/users.dat");
			fo.escreveObjecto(users);
			fo.fechaEscrita();
		} catch (Exception ei) {
		}
	}

	//
	public boolean hasVoted(User u, Election e) throws RemoteException {
		for (int i = 0; i < users.getUsers().size(); i++) {
			if (users.getUsers().get(i).getID().equals(u.getID())) {
				if (users.getUsers().get(i).hasVoted(e))
					return true;
			}
		}
		return false;
	}
	*/
	// ==================================================================================================================
	// Main
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		RMIFailover UDPConn;

		String hostname;
		int rmiPort, defPort;

		System.out.println("RMI Hostname: ");
		hostname = sc.nextLine();

		System.out.println("RMI Port");
		rmiPort = sc.nextInt();

		System.out.println("UPDSocket Port");
		defPort = sc.nextInt();

		// Inicia thread que lida com a conexão UDP
		UDPConn = new RMIFailover(hostname, defPort, rmiPort);
		UDPConn.start();

	}

	// Seleciona se vai ser Main ou Backup RMI
	public void startRMI(int rmiPort) {
		try {
			// Criação RMI
			serverRMI server = new serverRMI();
			Registry reg = LocateRegistry.createRegistry(rmiPort);
			reg.rebind("vote_booth", server);
			System.out.println("RMI Server connected");
		} catch (RemoteException e) {
			System.out.println("Could not bind RMI registry");
		}
	}

	// Cria conneção à base de dados
	public static boolean connectDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found");
			return false;
		}
		connection = null;
		try{
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/sdProjectDatabase",
					"bd_user",
					"password");
		} catch (SQLException e) {
			System.err.println("Database connection failed");
			return false;
		}
		System.out.println("Connected to database");
		return true;
	}

	// Executa update à base de dados
	public static boolean updateDB(String str){
		try {
			Statement stmt;
			if (connection.createStatement() == null) {
				connection = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/sdProjectDatabase",
						"bd_user",
						"password");
			}
			if ((stmt = connection.createStatement()) == null) {
				System.out.println("Could not create statement");
			}
			stmt.executeUpdate(str);
		}catch (SQLException e){
			System.err.println("Database update failed");
			e.printStackTrace();
			return false;
		}
		System.out.println("Successfully updated database");
		return true;
	}

	// Executa query à base de dados
	public static ResultSet queryDB(String str){
		try {
			Statement stmt;
			if (connection.createStatement() == null) {
				connection = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/sdProjectDatabase",
						"bd_user",
						"password");
			}
			if ((stmt = connection.createStatement()) == null) {
				System.out.println("Could not create statement");
			}
			return stmt.executeQuery(str);
		}catch (SQLException e){
			System.err.println("Error querying database");
		}
		System.out.println("Successfully queried database");
		return null;
	}
}

	// Thread que trata do Failover
class RMIFailover extends Thread {
	private DatagramSocket aSocket = null;

	private String hostname;
	private int serverPort, rmiPort;

	public RMIFailover(String hostname, int serverPort, int rmiPort) {
		this.hostname = hostname;
		this.serverPort = serverPort;
		this.rmiPort = rmiPort;
	}

	public void run() {
		RMIFailover UDPConn;
		try {
			// Abre socket UDP
			this.aSocket = new DatagramSocket(this.serverPort);

			serverRMI serverRMI = new serverRMI();
			serverRMI.startRMI(this.rmiPort);

			while (true) {
				String texto = "";
				byte[] m = texto.getBytes();

				// Cria e envia pacote UDP
				InetAddress aHost = InetAddress.getByName(this.hostname);
				DatagramPacket request = new DatagramPacket(m, m.length, aHost, this.serverPort);
				aSocket.send(request);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException i) {
				}
			}

		} catch (SocketException e) {
			System.out.println("Backup RMI Server started");
			int heartbeatsFailed = 0, maxHeartbeats = 5;
			byte[] buffer = new byte[1000];

			try {
				// Abre receiver socket UDP
				this.aSocket = new DatagramSocket(null);
				this.aSocket.setReuseAddress(true);
				this.aSocket.bind(new InetSocketAddress(this.hostname, this.serverPort));
				while (heartbeatsFailed < maxHeartbeats) {
					// Define timeout de recepção de heartbeat
					this.aSocket.setSoTimeout(1500);

					InetAddress aHost = InetAddress.getByName(this.hostname);

					// Cria e recebe pacote UDP
					DatagramPacket request = new DatagramPacket(buffer, buffer.length, aHost, this.serverPort);
					try {
						aSocket.receive(request);
						System.out.println("Received heartbeat from Main RMI server");
					} catch (SocketTimeoutException i){
						heartbeatsFailed++;
						System.err.println("Did not receive heartbeat from Main RMI server\tRetrying... "+heartbeatsFailed+"/"+maxHeartbeats);
					}
				}

				//
				if(heartbeatsFailed >= maxHeartbeats) {
					this.aSocket.close();
					UDPConn = new RMIFailover(this.hostname, this.serverPort, this.rmiPort);
					UDPConn.start();
					try {
						Thread.currentThread().join();
					} catch (InterruptedException i) {
					}
				}

			} catch (SocketException i) { System.out.println("Socket: " + e.getMessage());
			} catch (IOException i) { System.out.println("IO: " + e.getMessage());
			} finally { if (this.aSocket != null) this.aSocket.close(); }

		} catch (IOException e) { System.out.println("IO: " + e.getMessage());
		} finally { if (this.aSocket != null) this.aSocket.close(); }
	}
}




/*


ResultSet res = serverRMI.queryDB("select * from user;");
				ResultSetMetaData rsmd = res.getMetaData();

				int columnsNumber = rsmd.getColumnCount();

				for (int i = 1; i <= columnsNumber; i++) {
					System.out.print(rsmd.getColumnName(i));
					if (i < columnsNumber) System.out.print(",\t");
				}
				System.out.println("");
				while (res.next()) {
					for (int i = 1; i <= columnsNumber; i++) {
						if (i > 1) System.out.print(",\t");
						String columnValue = res.getString(i);
						System.out.print(columnValue);
					}
					System.out.println("");
				}
 */