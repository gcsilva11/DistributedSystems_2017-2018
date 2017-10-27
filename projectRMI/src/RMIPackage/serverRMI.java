package RMIPackage;

import ServerPackage.TCPServerInterface;
import adminPackage.VotingAdminInterface;

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
	private static candidateListList listOfCandidateLists = new candidateListList();
	private static ElectionList elList = new ElectionList();
	private static ElectionList closedElections = new ElectionList();
	private static DepList depsWithBooth = new DepList();

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

		for (int i = 0; i < departments.getDeps().size(); i++) {
			if (user.getDepartment().equals(departments.getDeps().get(i).getDep())) {

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
		}


		System.out.println("Error: That department doesn't exist");

		return false;



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
			e.printStackTrace();
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

		for (int i = 0; i < elList.getElections().size(); i++) {
			if (elList.getElections().get(i).getTitle().equals(elTitle)) {
				for (int j = 0; j < departments.getDeps().size(); j++) {
					for (int k = 0; k < depId.size(); k++) {
						if (departments.getDeps().get(j).getID().equals(depId.get(k))) {
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
                System.out.println("Election edited");
                done = true;
			}
		}

        System.out.println("Couldn't find election title");
        return done;
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
			if (users.getUsers().get(i).getID().equals(u.getID())) {
				users.getUsers().get(i).setVotes(e, cl);
				System.out.println(users.getUsers().get(i).getVotes());
				e.nVotos++;
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

	public ArrayList<Department> getDeps(Election e) throws RemoteException { return e.getViableDeps(); }

	// ==================================================================================================================
	// Main
	public static void main(String args[]) {
		RMIFailover UDPConn;

		String hostname;
		int serverPort;

		// argumentos da linha de comando: hostname, serverPort
		if (args.length != 3) {
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
        FicheiroDeObjectos foDepBooth = new FicheiroDeObjectos();

		String path;

		// Lê ficheiro users e adiciona-o a um array
		try {
			path = "out/users.dat";
			if (foUser.abreLeitura(path)) {
				users = (UserList) foUser.leObjecto();
				foUser.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading users.dat - " + e);
		}

		// Lê ficheiro departamentos e adiciona-o a um array
		try {
			path = "out/deps.dat";
			if (foDeps.abreLeitura(path)) {
				departments = (DepList) foDeps.leObjecto();
				foDeps.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading deps.dat - " + e);
		}

		// Lê ficheiro lista de candidatos e adiciona-o a um array
		try {
			path = "out/lists.dat";
			if (foLists.abreLeitura(path)) {
                listOfCandidateLists = (candidateListList) foLists.leObjecto();
				foLists.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading lists.dat - " + e);
		}

		// Lê ficheiro eleiçoes e adiciona-o a um array
		try {
			path = "out/elections.dat";
			if (foElections.abreLeitura(path)) {
				elList = (ElectionList) foElections.leObjecto();
				foElections.fechaLeitura();
			}
		} catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - " + e);
		}

		// Le ficheiro de eleicoes fechadas e poe no array
		try {
			path = "out/closedelections.dat";
			if (foClosedElections.abreLeitura(path)) {
				closedElections = (ElectionList) foClosedElections.leObjecto();
				foClosedElections.fechaLeitura();
			}

		} catch (Exception e) {
			System.out.println("Exception caught reading elections.dat - " + e);
		}
        try {
            path = "out/booths.dat";
            if (foDepBooth.abreLeitura(path)) {
                depsWithBooth = (DepList) foDepBooth.leObjecto();
                foDepBooth.fechaLeitura();
            }

        } catch (Exception e) {
            System.out.println("Exception caught reading lists.dat - " + e);
        }
	}

	// Seleciona se vai ser Main ou Backup RMI
	public void startRMI() {
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
			aSocket = new DatagramSocket(this.serverPort);
			System.out.println("Main RMI Server started");

			serverRMI serverRMI = new serverRMI();
			serverRMI.startRMI();

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
			int heartbeatsFailed = 0, maxHeartbeats = 10;
			byte[] buffer = new byte[1000];

			try {
				// Abre receiver socket UDP
				aSocket = new DatagramSocket(null);
				aSocket.setReuseAddress(true);
				aSocket.bind(new InetSocketAddress(hostname, serverPort));
				while (heartbeatsFailed < maxHeartbeats) {
					// Define timeout de recepção de heartbeat
					aSocket.setSoTimeout(1500);

					InetAddress aHost = InetAddress.getByName(hostname);

					// Cria e recebe pacote UDP
					DatagramPacket request = new DatagramPacket(buffer, buffer.length, aHost, serverPort);
					try {
						aSocket.receive(request);
						System.out.println("Received heartbeat from Main RMI server");
					} catch (SocketTimeoutException i){
						heartbeatsFailed++;
						System.err.println("Did not receive heartbeat from Main RMI server\tRetrying... "+heartbeatsFailed+"/"+maxHeartbeats);
					}
				}

				if(heartbeatsFailed >= maxHeartbeats) {
					aSocket.close();
					UDPConn = new RMIFailover(hostname, serverPort);
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