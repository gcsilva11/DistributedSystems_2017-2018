package ServerPackage;

import RMIPackage.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TCPServer {
    public static TCPServerInterface tcp;
    private static String userID;

    // Contrutor: Inicializa conexão ao RMI
    public TCPServer(String hostname,int rmiPort){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(rmiPort).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) { System.out.println("Error connecting to RMI"); }
    }

    // Main: À espera de novas ligações ao socket
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int defPort, rmiPort, num_Cliente = 0, id;
        String hostname, departmentID ;

        if (args.length == 3){
            hostname = args[0];
            rmiPort = Integer.parseInt(args[1]);
            defPort = Integer.parseInt(args[2]);
        }
        else{
            hostname = "localhost";
            rmiPort = 6500;
            defPort = 6000;
        }

        System.out.println("Department ID:");
        id = sc.nextInt()-1;
        departmentID = Integer.toString(id);

        // Conecta ao RMI
        TCPServer tcpServer = new TCPServer(hostname, rmiPort);

        // Recebe input na consola de qual é o departamento deste Servidor

        boolean aux = false;
        try {
            for (int i=0;i<tcp.getDepList().size();i++){
                if(id == Integer.parseInt(tcp.getDepList().get(i).getID()))
                    aux = true;
            }
        } catch (RemoteException e) { }
        if(aux) {
            System.out.println("Department doesnt exist");
            System.exit(0);
        } else
            System.out.println("Ready to receive user");

        // while(true) a aceitar novos clientes
        ServerSocket listenSocket = null;
        while (true) {
            try {

                // Cria e aceita socket connection
                listenSocket = new ServerSocket(defPort);
                Socket clientSocket = listenSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Thread que trata da identificação de users n o TCP Server
                new Thread() {
                    // getListaUsers
                    // Identifica user
                    public void run() {
                        TCPServer tcpServer = new TCPServer(hostname, rmiPort);
                        ArrayList<User> user = new ArrayList<>();

                        try {
                            user = tcpServer.tcp.getUsers();
                        } catch (RemoteException e) {
                        }
                        identifyUser(user);
                    }

                    // Identifica user e desbloqueia terminal de voto (Thread Connection)
                    public void identifyUser(ArrayList<User> user) {
                        Scanner sc = new Scanner(System.in);

                        System.out.println("Identify yourself by ID: ");

                        String data = sc.nextLine();

                        // Procura ID
                        boolean unblocks = false;
                        for (int i = 0; i < user.size(); i++) {
                            if (user.get(i).getID().compareTo(data) == 0) {
                                userID = user.get(i).getID();


                                System.out.println(user.get(i).getInfo());

                                unblocks = true;
                            }
                        }

                        // Nao encontrou ID
                        if (!unblocks) {
                            System.out.println("ID not found");
                            identifyUser(user);
                        }
                        // Thread Connection que trata da autentificação e do voto do cada cliente
                        else {
                            Connection newClient = new Connection(clientSocket, userID, hostname, rmiPort, departmentID);
                            newClient.start();
                        }
                    }
                }.start();
            }catch(IOException e) { System.out.println("Error creating TCP socket"); }
            try { listenSocket.close(); } catch (IOException e) { }
        }
    }
}

class Connection extends Thread{
    private BufferedReader input;
    private PrintWriter output;

    private Socket clientSocket;

    private StringTokenizer token;

    private TCPServer tcpServer;
    private ArrayList<User> user = new ArrayList<>();
    private ArrayList<candidateList> candidateList = new ArrayList<>();
    private ArrayList<Department> department = new ArrayList<>();
    private ArrayList<Election> election = new ArrayList<>();

    private String userID;
    private String departmentID;

    // Construtor: Inicializa dados do socket e do RMI
    public Connection (Socket aClientSocket, String userID, String hostname, int rmiPort, String departmentID) {
        this.clientSocket = aClientSocket;
        try{
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.user = tcpServer.tcp.getUsers();
            this.candidateList = tcpServer.tcp.getCandidateList();
            this.department = tcpServer.tcp.getDepList();
            this.election = tcpServer.tcp.getElList();
            this.userID = userID;
            this.tcpServer = new TCPServer(hostname, rmiPort);
            this.departmentID = departmentID;
        }catch(IOException e){ }
    }

    // Run: Aceita Cliente
    public void run(){
        StringTokenizer tokenizer;
        String data="", aux="";
        int idElection=-1, idList=-1;

        try {
            output.println("VOTE TERMINAL UNBLOCKED");

            // Recebe user e pass
            output.println("Insert [username]/[password]");
            data = input.readLine();

            tokenizer = new StringTokenizer(data, "/");
            try {
                data = tokenizer.nextToken();
                aux = tokenizer.nextToken();
            } catch (NoSuchElementException e) {
            }

            // Autentica user
            if(authenticateUser(data,aux)) {
                output.println("Authentication successfull");

                // Lista as eleiçoes
                output.println("Choose election to vote on: ");

                boolean auxB = false;
                for (int i = 0; i < election.size(); i++) {
                    for (int j = 0; j < user.size(); j++) {
                        if (user.get(j).getID().equals(userID) && !user.get(j).hasVoted(election.get(i))) {
                            //for (int k = 0; k < election.get(i).getViableDeps().size(); k++) {
                                //if (election.get(i).getViableDeps().get(k).getID().equals(departmentID)) {
                                    output.println(i + ". " + election.get(i).getTitle());
                                    auxB = true;
                                }
                            //}
                        //}
                    }
                }
                if (auxB){
                    // Recebe escolha eleiçao
                    aux = input.readLine();
                    idElection = Integer.parseInt(aux);

                    if (idElection >= 0) {
                        for (int i = 0; i < user.size(); i++) {
                            if (user.get(i).getID().equals(userID)) {
                                if (!tcpServer.tcp.hasVoted(user.get(i), election.get(idElection))) {
                                    // Lista as listas de determinada eleiçao
                                    output.println("Choose list to vote on: \n0. ");
                                    for (int j = 0; j < election.get(idElection).getCandidates().size(); j++)
                                        output.println(j + 1 + ". " + election.get(idElection).getCandidates().get(j).getName());

                                    // Recebe escolha lista
                                    aux = input.readLine();
                                    idList = Integer.parseInt(aux);

                                    System.out.println(idList);

                                    // Envia informaçao de voto para RMI
                                    vote(election.get(idElection).getTitle(), idList - 1);
                                } else {
                                    output.println("This user already voted for election " + election.get(idElection).getTitle() + "\n\tClosing session...");
                                    System.out.println("Invalid Vote\n\tClosing client's session...");
                                }
                            }
                        }
                    } else {
                        output.println("There is no such election\n\tClosing session...");
                        System.out.println("Invalid Election\n\tClosing client's session...");
                    }
                } else {
                    output.println("No elections to vote on\n\tClosing session...");
                    System.out.println("No elections for user to vote on\n\tClosing client's session...");
                }
            }
            // Rejeita autenticaçao e fecha thread
            else {
                output.println("Failed authentication\n\tClosing session...");
                System.out.println("Failed authentication\n\tClosing client's session...");
            }
        } catch (IOException e) {
        } catch (NullPointerException e){}
    }

    //
    public boolean authenticateUser (String username, String password) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getName().equals(username) && user.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }

    //
    public void vote(String electionName, int idList) throws java.rmi.RemoteException{
        Election e = tcpServer.tcp.getElection(electionName);
        try {
            // Voto nulo
            if(idList>=e.getCandidates().size()) {
                for (int i = 0; i < user.size(); i++) {
                    if (user.get(i).getID().equals(userID)) {
                        tcpServer.tcp.voteElection(user.get(i), e, new candidateList("NULLVOTE","NULLVOTE",-1,null));
                    }
                }
            } else{
                // Voto em branco
                if(idList == -1) {
                    for (int i = 0; i < user.size(); i++) {
                        if (user.get(i).getID().equals(userID)) {
                            tcpServer.tcp.voteElection(user.get(i), e, new candidateList("WHITEVOTE", "WHITEVOTE", -1, null));
                        }
                    }
                }
                // Voto numa lista
                else {
                    for (int i=0;i<user.size();i++){
                        if(user.get(i).getID().equals(userID)){
                            tcpServer.tcp.voteElection(user.get(i), e, e.getCandidates().get(idList));
                        }
                    }
                }
            }
        } catch (NullPointerException i){}
        output.println("Vote Accepted\n You can logout now");
    }
}

