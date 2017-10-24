package ServerPackage;

import RMIPackage.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TCPServer {
    public TCPServerInterface tcp;

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
                Socket clientSocket = listenSocket.accept();
                num_Cliente++;
                Connection newClient = new Connection(clientSocket, num_Cliente);

                newClient.start();
            }
        }catch(IOException e) { System.out.println("Error creating TCP socket"); }
    }
}

class Connection extends Thread{
    public static final boolean DEBUG = true;

    private BufferedReader input;
    private PrintWriter output;

    private Socket clientSocket;
    int thread_number;

    private StringTokenizer token;

    private TCPServer tcpServer = new TCPServer();
    private ArrayList<User> user = new ArrayList<>();
    private ArrayList<candidateList> candidateList = new ArrayList<>();
    private ArrayList<Department> department = new ArrayList<>();
    private ArrayList<Election> election = new ArrayList<>();

    // Construtor: Inicializa dados do socket e do RMI
    public Connection (Socket aClientSocket, int numero) {
        this.thread_number = numero;
        this.clientSocket = aClientSocket;
        try{
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.user = tcpServer.tcp.getUsers();
            this.candidateList = tcpServer.tcp.getCandidateList();
            this.department = tcpServer.tcp.getDepList();
            this.election = tcpServer.tcp.getElList();
        }catch(IOException e){ }
    }

    // Run: Aceita Cliente
    public void run(){
        if(DEBUG) System.out.println("\t#DEBUG# Cliente "+this.thread_number+" conectado");

        int id = -1;
        while (true) {
            id = identifyUser(id);
        }
    }

    public int identifyUser(int id) {
        int userID = 0;
        try {
            String data;
            boolean bool = false;

            // Recebe string true se for ID, false se for Nome

            data = this.input.readLine();

            if (DEBUG) System.out.println("\t#DEBUG# Cliente " + this.thread_number + " recebeu " + data);

            if (data.compareTo("quit") == 0) {
                if (DEBUG) System.out.println("\t#DEBUG# Cliente " + this.thread_number + " desconectado");
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                }
            }

            // ID
            else if (data.compareTo("true") == 0) {

                // Recebe ID
                data = this.input.readLine();
                if (DEBUG) System.out.println("\t#DEBUG# Cliente " + this.thread_number + " recebeu " + data);

                for (int i = 0; i < this.user.size(); i++) {
                    if (Integer.parseInt(data) == Integer.parseInt(this.user.get(i).getID())) {
                        // Envia ID
                        userID = Integer.parseInt(this.user.get(i).getID());
                        this.output.println(this.user.get(i).getID());
                        if (DEBUG)
                            System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " - " + this.user.get(i).getID());
                        id = i;
                        bool = true;
                    }
                }
            }

            // Nome
            else if (data.compareTo("false") == 0) {
                // Recebe Nome
                data = this.input.readLine();
                if (DEBUG) System.out.println("\t#DEBUG# Cliente " + this.thread_number + " recebeu " + data);

                for (int j = 0; j < this.user.size(); j++) {
                    if (data.compareTo(this.user.get(j).getName()) == 0) {
                        userID = Integer.parseInt(this.user.get(j).getID());
                        this.output.println(this.user.get(j).getID());
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " - " + this.user.get(j).getID());
                        // Envia Nome
                        this.output.println(this.user.get(j).getName());
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " - " + this.user.get(j).getName());
                        id = j;
                        bool = true;
                    }
                }
            }

            // Envia não encontrado
            if (!bool) {
                this.output.println("NotFound");
                if (DEBUG) System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " NotFound");
            }

            // Autenticação
            else {

                // Recebe username
                data = this.input.readLine();
                if (DEBUG) System.out.println("\t#DEBUG# Cliente " + this.thread_number + " recebeu " + data);

                token = new StringTokenizer(data, "/");

                // Sucesso na autenticaçao
                if ((token.countTokens() == 2) && (token.nextToken().compareTo(this.user.get(id).getName()) == 0) && (token.nextToken().compareTo(this.user.get(id).getPassword()) == 0)) {
                    // Envia confirmação autenticaçao
                    output.println("true");
                    if (DEBUG) System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " - 1");

                    //
                    //   VOTAR AQUI
                    //
                    voteAct(userID);

                // Falhou autenticação
                } else {
                    output.println("false");
                    if (DEBUG) System.out.println("\t#DEBUG# Enviou para cliente " + this.thread_number + " - 0");
                }
            }
        } catch (IOException e) { }
        return id;
    }

    // Utilizador vota
    public void voteAct(int userID) {
        try {
            int elections = 0;
            for (int i = 0; i < election.size(); i++)
                elections++;
            output.println(elections);
            for (int i = 0; i < election.size(); i++)
                output.println(election.get(i).getTitle());

            elections = Integer.parseInt(input.readLine());

            int list = 0;
            for (int i = 0; i < candidateList.size(); i++)
                list++;
            output.println(list);

            for (int i = 0; i < candidateList.size(); i++)
                output.println(candidateList.get(i).getName());

            list = Integer.parseInt(input.readLine());

            user.get(userID).Vote();
            //e preciso refazer Vote() para por um boolean para cada eleiçao

            output.println(user.get(userID).getVote());

        } catch (IOException e) {}
    }
}