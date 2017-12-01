package ServerPackage;

import RMIPackage.*;
import sun.awt.windows.ThemeReader;

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
        int failed = 0;
        while (failed <= 30) {
            try {
                tcp = (TCPServerInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
                break;
            } catch (RemoteException|NotBoundException e) {
                System.out.println("RMI Timeout on User registry, trying to reconnect");
                try {
                    Thread.sleep(500);
                    tcp = (TCPServerInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
                    break;
                } catch (Exception e2) { failed++; }
                try { Thread.sleep(500); } catch (InterruptedException e1) { }
            }
        }
    }

    // Main: À espera de novas ligações ao socket
    public static void main(String[] args) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        int defPort, rmiPort, num_Cliente = 0;
        String hostname;

        System.out.println("RMI Hostname: ");
        hostname = sc.nextLine();

        System.out.println("RMI Port");
        rmiPort = sc.nextInt();

        System.out.println("TCPSocket Port");
        defPort = sc.nextInt();

        // Conecta ao RMI
        TCPServer tcpServer = new TCPServer(hostname, rmiPort);
        System.out.println("Ready to receive user");

        // while(true) a aceitar novos clientes
        ServerSocket listenSocket = null;
        while (true) {
            try {

                // Cria e aceita socket connection
                listenSocket = new ServerSocket(defPort);
                Socket clientSocket = listenSocket.accept();

                clientSocket.setSoTimeout(120000);

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Thread que trata da identificação de users no TCP Server
                /*new Thread() {
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
                        int failed = 0;
                        Scanner sc = new Scanner(System.in);

                        System.out.println("Identify yourself by ID: ");

                        String data = sc.nextLine();

                        System.out.println();

                        // Procura ID
                        boolean unblocks = false;
                        for (int i = 0; i < user.size(); i++) {
                            if (user.get(i).getID().compareTo(data) == 0) {
                                userID = user.get(i).getID();
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
                            System.out.println("Vote terminal unblocked");
                            Connection newClient = new Connection(clientSocket, userID, hostname, rmiPort);
                            newClient.start();
                        }
                    }
                }.start();*/
            } catch (IOException e) {
                System.out.println("Error creating TCP socket");
            }
            try { listenSocket.close(); } catch (IOException e) { }
        }
    }
}

class Connection extends Thread {
    private BufferedReader input;
    private PrintWriter output;

    private Socket clientSocket;

    private StringTokenizer token;

    private TCPServer tcpServer;
    //private ArrayList<User> user = new ArrayList<>();
    //private ArrayList<candidateList> candidateList = new ArrayList<>();
    //private ArrayList<Department> department = new ArrayList<>();
    //private ArrayList<Election> election = new ArrayList<>();

    private String hostname;
    private String userID;
    private int rmiPort;

    // Construtor: Inicializa dados do socket e do RMI
    public Connection(Socket aClientSocket, String userID, String hostname, int rmiPort) {
        this.clientSocket = aClientSocket;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            //this.user = tcpServer.tcp.getUsers();
            //this.candidateList = tcpServer.tcp.getCandidateList();
            //this.department = tcpServer.tcp.getDepList();
            //this.election = tcpServer.tcp.getElList();
            this.userID = userID;
            this.hostname = hostname;
            this.rmiPort = rmiPort;
            this.tcpServer = new TCPServer(this.hostname, this.rmiPort);
        } catch (IOException e) {
        }
    }

    // Run: Aceita Cliente
    public void run() {/*
        StringTokenizer tokenizer;
        String data = "", aux = "";
        int idElection = -1, idList = -1;
        try {
            output.println("-->Welcome to iVotas<--");

            // Recebe user e pass
            output.println("Insert [username]/[password]");
            data = input.readLine();

            // Trata dados recebidos
            tokenizer = new StringTokenizer(data, "/");
            try {
                data = tokenizer.nextToken();
                aux = tokenizer.nextToken();
            } catch (NoSuchElementException e) {
            }

            // Autentica user
            if (authenticateUser(data, aux, userID)) {
                output.println("Authentication successfull");

                // Lista as eleiçoes
                output.println("Choose election to vote on: ");
                boolean auxB = false;
                for (int i = 0; i < election.size(); i++) {
                    for (int j = 0; j < user.size(); j++) {
                        if (user.get(j).getID().equals(userID) && !user.get(j).hasVoted(election.get(i))) {
                            output.println(i + ". " + election.get(i).getTitle());
                            auxB = true;
                        }
                    }
                }
                if (auxB) {
                    // Recebe escolha eleiçao
                    aux = input.readLine();

                    idElection = Integer.parseInt(aux);
                    if (idElection < election.size()) {
                        for (int i = 0; i < user.size(); i++) {
                            if (user.get(i).getID().equals(userID)) {
                                int failed = 0;
                                while (failed < 10) {
                                    try {
                                        if (!tcpServer.tcp.hasVoted(user.get(i), election.get(idElection))) {
                                            // Lista as listas de determinada eleiçao
                                            output.println("Choose list to vote on: \n0. ");
                                            for (int j = 0; j < election.get(idElection).getCandidates().size(); j++) {
                                                if (!(election.get(idElection).getCandidates().get(j).getID().equals("BLANKVOTE"))) {
                                                    if (!(election.get(idElection).getCandidates().get(j).getID().equals("NULLVOTE")))
                                                        output.println(j - 1 + ". " + election.get(idElection).getCandidates().get(j).getName());
                                                }
                                            }

                                            // Recebe escolha lista
                                            aux = input.readLine();
                                            idList = Integer.parseInt(aux);

                                            // Envia informaçao de voto para RMI
                                            failed = 0;
                                            while (failed <= 30) {
                                                try {
                                                    vote(election.get(idElection).getTitle(), idList + 1);
                                                    break;
                                                // Failover RMI
                                                } catch (Exception e) {
                                                    System.out.println("RMI Timeout on User registry, trying to reconnect");
                                                    try {
                                                        Thread.sleep(500);
                                                        TCPServer tcpServer = new TCPServer(this.hostname, this.rmiPort);
                                                        tcpServer.tcp = (TCPServerInterface) LocateRegistry.getRegistry(this.hostname, this.rmiPort).lookup("vote_booth");
                                                        System.out.println("Reconnect sucecssfull");
                                                    } catch (Exception e2) {
                                                        failed++;
                                                    }
                                                    try {
                                                        Thread.sleep(500);
                                                    } catch (InterruptedException e1) {
                                                    }
                                                }
                                            }
                                        } else {
                                            output.println("This user already voted for election " + election.get(idElection).getTitle() + "\n\tClosing session...");
                                            System.out.println("Invalid Vote\n\tClosing client's session...");
                                            break;
                                        }
                                        break;
                                    // Failover RMI
                                    } catch (Exception e) {
                                        System.out.println("RMI Timeout on User registry, trying to reconnect");
                                        try {
                                            Thread.sleep(500);
                                            TCPServer tcpServer = new TCPServer(this.hostname, this.rmiPort);
                                            tcpServer.tcp = (TCPServerInterface) LocateRegistry.getRegistry(this.hostname, this.rmiPort).lookup("vote_booth");
                                            System.out.println("Reconnect sucecssfull");
                                        } catch (Exception e2) {
                                            failed++;
                                        }
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e1) {
                                        }
                                    }
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
        } catch (NullPointerException e) {
        } catch (SocketTimeoutException e) {
            output.println("Session expired");
            try {
                clientSocket.close();
            } catch (IOException e1) {
            }
        } catch (IOException e) {
        }*/
    }
/*
    // Autentica Cliente
    public boolean authenticateUser(String username, String password, String userID) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getID().equals(userID) && user.get(i).getName().equals(username) && user.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }

    // Efetua voto
    public void vote(String electionName, int idList) throws java.rmi.RemoteException {
        Election e = tcpServer.tcp.getElection(electionName);
        // Voto nulo
        if (idList >= e.getCandidates().size()) {
            for (int i = 0; i < user.size(); i++) {
                if (user.get(i).getID().equals(userID)) {
                    for (int j = 0; j < e.getCandidates().size(); j++) {
                        if (e.getCandidates().get(j).getID().equals("NULLVOTE"))
                            tcpServer.tcp.voteElection(user.get(i), e, e.getCandidates().get(j));
                    }
                }
            }
        } else {
            // Voto em branco
            if (idList == -1) {
                for (int i = 0; i < user.size(); i++) {
                    if (user.get(i).getID().equals(userID)) {
                        for (int j = 0; j < e.getCandidates().size(); j++) {
                            if (e.getCandidates().get(j).getID().equals("BLANKVOTE"))
                                tcpServer.tcp.voteElection(user.get(i), e, e.getCandidates().get(j));
                        }
                    }
                }
            }
            // Voto numa lista
            else {
                for (int i = 0; i < user.size(); i++) {
                    if (user.get(i).getID().equals(userID)) {
                        tcpServer.tcp.voteElection(user.get(i), e, e.getCandidates().get(idList));
                    }
                }
            }
            output.println("Vote Accepted\n You can logout now");
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e1) {
            }
        }
    }
    */
}

