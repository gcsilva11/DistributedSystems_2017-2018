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
    public TCPServerInterface tcp;
    public static int userID;

    // Contrutor: Inicializa conexão ao RMI
    public TCPServer(){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) { System.out.println("Error connecting to RMI"); }
    }

    // Main: À espera de novas ligações ao socket
    public static void main(String[] args){
        int def_port;
        int num_Cliente = 0;

        if(args.length != 1)
            def_port = 6000;
        else
            def_port = Integer.parseInt(args[0]);

        TCPServer tcpServer = new TCPServer();

        try{
            ServerSocket listenSocket = new ServerSocket(def_port);
            while(true) {

                // Accepts socket connection
                Socket clientSocket = listenSocket.accept();
                Connection newClient = new Connection(clientSocket, userID);
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Thread que trata da identificação de users n o TCP Server
                new Thread() {
                    // getListaUsers
                    // Identifica user
                    public void run() {
                        TCPServer tcpServer = new TCPServer();
                        ArrayList<User> user = new ArrayList<>();

                        try {
                            user = tcpServer.tcp.getUsers();
                        } catch (RemoteException e) { }
                        while(true)
                                identifyUser(user);


                    }
                    public void identifyUser(ArrayList<User> user) {
                        Scanner sc = new Scanner(System.in);

                        System.out.println("Identify yourself by ID: ");

                        String data = sc.nextLine();

                        // Procura ID
                        boolean unblocks = false;
                        for (int i = 0; i < user.size(); i++) {
                            if (user.get(i).getID().compareTo(data) == 0) {
                                userID = i;
                                output.println("VOTE TERMINAL UNBLOCKED");
                                unblocks = true;
                            }
                        }

                        // Nao encontrou ID
                        if(!unblocks) {
                            System.out.println("ID not found");
                            identifyUser(user);
                        }
                        // Thread que trata da autentificação e do voto do cada cliente
                        else {
                            newClient.start();
                        }
                    }
                }.start();
            }
        }catch(IOException e) { System.out.println("Error creating TCP socket"); }
    }
}

class Connection extends Thread{
    private BufferedReader input;
    private PrintWriter output;

    private Socket clientSocket;

    private StringTokenizer token;

    private TCPServer tcpServer = new TCPServer();
    private ArrayList<User> user = new ArrayList<>();
    private ArrayList<candidateList> candidateList = new ArrayList<>();
    private ArrayList<Department> department = new ArrayList<>();
    private ArrayList<Election> election = new ArrayList<>();

    private int userID;

    // Construtor: Inicializa dados do socket e do RMI
    public Connection (Socket aClientSocket, int userID) {
        this.clientSocket = aClientSocket;
        try{
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.user = tcpServer.tcp.getUsers();
            this.candidateList = tcpServer.tcp.getCandidateList();
            this.department = tcpServer.tcp.getDepList();
            this.election = tcpServer.tcp.getElList();
            this.userID = userID;
        }catch(IOException e){ }
    }

    // Run: Aceita Cliente
    public void run(){
        if(authenticateUser()){
            System.out.println("Authentication successfull");
            try { vote(); } catch (IOException e) { }
        }
        else {
            output.println("Failed authentication\nRe-Identify yourself to continue");
            System.out.println("Failed authentication\n Closing thread");
        }
    }

    public boolean authenticateUser(){
        StringTokenizer tokenizer;
        String data="", aux="";
        output.println("Insert [username]/[password]");

        try {
            data = input.readLine();
        } catch (IOException e) {}

        tokenizer = new StringTokenizer(data, "/");
        data = tokenizer.nextToken();
        try{
            aux = tokenizer.nextToken();
        } catch (NoSuchElementException e) {}

        for (int i=0;i<user.size();i++){
            if(user.get(userID).getName().compareTo(data)==0 && user.get(userID).getPassword().compareTo(aux)==0) {
                output.println("Authentication successfull");
                return true;
            }
        }
        return false;
    }

    public void vote() throws IOException,NumberFormatException{
        String data="";
        int idElection, idList;

        // Lista as eleiçoes
        output.println("Choose election to vote on: ");
        for(int i = 0;i<election.size();i++) {
            output.println(i + ". " + election.get(i).getTitle());
        }

        try {
            data = input.readLine();
        } catch (IOException e) { }

        idElection = Integer.parseInt(data);

        output.println("0. ");
        int listSize = election.get(idElection).getCandidates().size();
        for (int i = 0; i<listSize;i++){
            output.println(i-1+". "+election.get(idElection).getCandidates().get(i).getName());
        }
        data = input.readLine();
        idList = Integer.parseInt(data);
        if(!(idList>listSize)){
            System.out.println("eleiçao: "+idElection+"lista: "+idList);

            if(idList == 0) {
                System.out.println("implementar voto em branco");
            }
                // voto branco
            // voto em lista
            else
                user.get(userID).voteElection(election.get(idElection),candidateList.get(idList));

        } else{
            System.out.println("implementar voto nulo");
            // voto nulo
        }

    }
}

