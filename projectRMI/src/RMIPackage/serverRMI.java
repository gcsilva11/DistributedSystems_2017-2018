package RMIPackage;

import ServerPackage.TCPServerInterface;
import adminPackage.VotingAdminInterface;

import javax.management.remote.rmi.RMIServer;
import java.io.IOException;
import java.net.*;
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
		for (int i = 0; i < users.getUsers().size(); i++) {
			if (user.getID().equals(users.getUsers().get(i).getID())) {
				System.out.println("ID already exists.");
				return false;
			}
		}

		//FALTA VER SO O DEP. EXISTE


		users.addUser(user);

		// Update ficheiro
		try {
			fo.abreEscrita("out/users.dat");
			fo.escreveObjecto(users);
			fo.fechaEscrita();
		} catch (Exception e) {
		}

		System.out.println("New user registered.");

		return true;
	}

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
		}

		System.out.println("New department registered.");

		return true;

	}

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
			for (int i = 0; i < candidateList.getCandidateList().size(); i++) {
				if (candidateList.getCandidateList().get(i).getType() == type) {
					chosenLists.add(candidateList.getCandidateList().get(i));
				}
			}
		} else {
			for (int i = 0; i < candidateList.getCandidateList().size(); i++) {
				chosenLists.add(candidateList.getCandidateList().get(i));
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
		for (int i = 0; i < candidateList.getCandidateList().size(); i++) {
			if (candidateList.getCandidateList().get(i).getID() == cl.getID()) {
				exists = true;
			}
		}

		if (exists) {
			System.out.println("List ID already exists...");
			return false;
		}

		candidateList.addCandidateList(cl);
		;

		//Update ficheiro
		try {
			fo.abreEscrita("out/lists.dat");
			fo.escreveObjecto(cl);
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

		for (int i = 0; i < candidateList.getCandidateList().size(); i++) {
			if (candidateList.getCandidateList().get(i).getID().equals(id)) {
				candidateList.getCandidateList().remove(i);
				exists = true;
				//Update file
				try {
					fo.abreEscrita("out/lists.dat");
					fo.escreveObjecto(candidateList);
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

		for (int i = 0; i < candidateList.getCandidateList().size(); i++) {
			if (candidateList.getCandidateList().get(i).getID().equals(id)) {
				candidateList.getCandidateList().get(i).setName(title);
				done = true;
				//Update file
				try {
					fo.abreEscrita("out/lists.dat");
					fo.escreveObjecto(candidateList);
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
		boolean done = true;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(elTitle)) {
				for (int j = 0; j < departments.getDeps().size(); j++) {
					for (int k = 0; k < depId.size(); k++) {
						if (departments.getDeps().get(j).getID().equals(depId.get(k))) {
							elList.getElections().get(i).addDep(departments.getDeps().get(j));
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
		} catch (Exception e) {
		}

		return done;

	}

	//
	public boolean editElec(Election el) throws RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();

		boolean done = false;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(el.getTitle())) {
				elList.getElections().set(i, el);
				//Update ficheiro
				try {
					fo.abreEscrita("out/elections.dat");
					fo.escreveObjecto(elList);
					fo.fechaEscrita();
				} catch (Exception e) {
				}

				done = true;
			}
		}


		return done;
	}

	//
	public Election checkElecDate() throws java.rmi.RemoteException {

		FicheiroDeObjectos fo = new FicheiroDeObjectos();
		Election stub = null;

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getEndDate().before(Calendar.getInstance()) && elList.getElections().get(i).getClosed() == false) {
				elList.getElections().get(i).setClosed();
				closedElections.addELection(elList.getElections().get(i));

				//Update ficheiro eleicoes
				try {
					fo.abreEscrita("out/elections.dat");
					fo.escreveObjecto(elList);
					fo.fechaEscrita();
				} catch (Exception e) {
				}

				//Update ficheiro eleicoes
				try {
					fo.abreEscrita("out/closedelections.dat");
					fo.escreveObjecto(closedElections);
					fo.fechaEscrita();
				} catch (Exception e) {
				}

				return elList.getElections().get(i);
			}
		}

		return stub;

	}

	// ==================================================================================================================
	// TCPServerInterface

	// Devolve lista de users
	public ArrayList<User> getUsers() throws RemoteException {
		return users.getUsers();
	}

	// Devolve lista de candidatos
	public ArrayList<candidateList> getCandidateList() throws RemoteException {
		return candidateList.getCandidateList();
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

	// ==================================================================================================================
	// Main
	public static void main(String args[]) {
		RMIFailover UDPConn;

		String hostname;
		int serverPort;
		boolean choice;

		// argumentos da linha de comando: hostname, serverPort
		if (args.length != 2) {
			hostname = "localhost";
			serverPort = 7000;

		} else {
			hostname = args[0];
			serverPort = Integer.parseInt(args[1]);
		}

		// Inicia thread que lida com a conexão UDP
		UDPConn = new RMIFailover(hostname, serverPort);
		UDPConn.start();

		// Atualiza dados ficheiros
		setupObjectFiles();

	}

	// Atualiza dados ficheiros
	public static void setupObjectFiles() {

		FicheiroDeObjectos foUser = new FicheiroDeObjectos();
		FicheiroDeObjectos foDeps = new FicheiroDeObjectos();
		FicheiroDeObjectos foLists = new FicheiroDeObjectos();
		FicheiroDeObjectos foElections = new FicheiroDeObjectos();
		FicheiroDeObjectos foClosedElections = new FicheiroDeObjectos();

		// Lê ficheiro users e adiciona-o a um array
		try {
			if (foUser.abreLeitura("out/users.dat")) {
				users = (UserList) foUser.leObjecto();
				foUser.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading users.dat - " + e);
		}

		// Lê ficheiro departamentos e adiciona-o a um array
		try {
			if (foDeps.abreLeitura("out/deps.dat")) {
				departments = (DepList) foDeps.leObjecto();
				foDeps.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading deps.dat - " + e);
		}

		// Lê ficheiro lista de candidatos e adiciona-o a um array
		try {
			if (foLists.abreLeitura("out/lists.dat")) {
				candidateList = (candidateListList) foLists.leObjecto();
				foLists.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading lists.dat - " + e);
		}

		// Lê ficheiro eleiçoes e adiciona-o a um array
		try {
			if (foElections.abreLeitura("out/elections.dat")) {
				elList = (ElectionList) foElections.leObjecto();
				foElections.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - " + e);
		}

		// Le ficheiro de eleicoes fechadas e poe no array
		try {
			if (foClosedElections.abreLeitura("out/closedelections.dat")) {
				closedElections = (ElectionList) foClosedElections.leObjecto();
				foClosedElections.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - " + e);
		}
	}

	// Seleciona se vai ser Main ou Backup RMI
	public static void startRMI() {
		try {
			// Criação RMI
			serverRMI server = new serverRMI();
			Registry reg = LocateRegistry.createRegistry(6500);
			reg.rebind("vote_booth", server);
			System.out.println("RMI Server connected");
		} catch (RemoteException e) {
			System.out.println("Could not bind RMI registry");
		}
	}
}

	// Thread que trata do Failover
class RMIFailover extends Thread {
	public static final boolean DEBUG = true;

	private DatagramSocket aSocket = null;

	private String hostname;
	private int serverPort;

	public RMIFailover(String hostname, int serverPort) {
		this.hostname = hostname;
		this.serverPort = serverPort;
	}

	public void run() {
		RMIFailover UDPConn;
		try {
			// Abre socket UDP
			aSocket = new DatagramSocket(serverPort);
			if (DEBUG) System.out.println("\t#DEBUG# Main RMI Server: A enviar hearbeats para Backup RMI Server");

			serverRMI serverRMI = new serverRMI();
			serverRMI.startRMI();

			while (true) {
				String texto = "";
				byte[] m = texto.getBytes();

				// Cria e envia pacote UDP
				InetAddress aHost = InetAddress.getByName(hostname);
				DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
				aSocket.send(request);

				if (DEBUG) System.out.println("\t#DEBUG# Enviou hearbeat");

				try {
					Thread.sleep(1000);
				} catch (InterruptedException i) {
				}
			}

		} catch (SocketException e) {
			if (DEBUG) System.out.println("\t#DEBUG# Backup RMI Server: À espera de falha do Main RMI Server");
			int heartbeatsFailed = 0;
			byte[] buffer = new byte[1000];

			try {
				// Abre receiver socket UDP
				aSocket = new DatagramSocket(null);
				aSocket.setReuseAddress(true);
				aSocket.bind(new InetSocketAddress(hostname, serverPort));

				while (heartbeatsFailed < 5) {
					// Define timeout de recepção de heartbeat
					this.aSocket.setSoTimeout(1500);

					InetAddress aHost = InetAddress.getByName(hostname);

					// Cria e recebe pacote UDP
					DatagramPacket request = new DatagramPacket(buffer, buffer.length, aHost, serverPort);
					try {
						aSocket.receive(request);
						if (DEBUG) System.out.println("\t#DEBUG# Recebeu heartbeat");
					} catch (SocketTimeoutException i){
						heartbeatsFailed++;
						System.out.println("\t#DEBUG# Heartbeat falhado");
					}
				}

				if(heartbeatsFailed == 5) {
					this.aSocket.close();
					UDPConn = new RMIFailover(hostname, serverPort);
					UDPConn.start();
					try {
						Thread.currentThread().join();
					} catch (InterruptedException i) {
					}
				}

			} catch (SocketException i) { if (DEBUG) System.out.println("\t#DEBUG# Socket: " + e.getMessage());
			} catch (IOException i) { if (DEBUG) System.out.println("\t#DEBUG# IO: " + e.getMessage());
			} finally { if (this.aSocket != null) this.aSocket.close(); }

		} catch (IOException e) { if (DEBUG) System.out.println("\t#DEBUG# IO: " + e.getMessage());
		} finally { if (this.aSocket != null) this.aSocket.close(); }
	}
}