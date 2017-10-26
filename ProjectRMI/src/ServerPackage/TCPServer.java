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
    private static int userID;

    // Contrutor: Inicializa conexão ao RMI
    public TCPServer(){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) { System.out.println("Error connecting to RMI"); }
    }

    // Main: À espera de novas ligações ao socket
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int department, def_port, num_Cliente = 0;
        String hostname;

        if (args.length == 2){
            hostname = args[0];
            def_port = Integer.parseInt(args[1]);
        }
        else{
            hostname = "localhost";
            def_port = 6000;
        }

        // Conecta ao RMI
        TCPServer tcpServer = new TCPServer();

        // Recebe input na consola de qual é o departamento deste Servidor
        boolean aux = false;
        System.out.println("Department ID:");
        department = sc.nextInt();
        try {
            for (int i=0;i<tcp.getDepList().size();i++){
                if(department == Integer.parseInt(tcp.getDepList().get(i).getID()))
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
                listenSocket = new ServerSocket(def_port);
                Socket clientSocket = listenSocket.accept();
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
                                userID = i;
                                System.out.println(user.get(userID).getInfo());
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
                            Connection newClient = new Connection(clientSocket, userID);
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
            if(authenticateUser(data,aux)){
                System.out.println("Authentication successfull");
                output.println("Authentication successfull");

                // Lista as eleiçoes
                output.println("Choose election to vote on: ");
                for(int i = 1;i<=election.size();i++)
                    output.println(i + ". " + election.get(i-1).getTitle());
                // Recebe escolha eleiçao
                aux = input.readLine();
                idElection = Integer.parseInt(aux);

                // Lista as listas de determinada eleiçao
                output.println("Choose list to vote on: \n0. ");
                for (int i = 0; i<election.get(idElection-1).getCandidates().size();i++)
                    output.println(i+1+". "+election.get(idElection).getCandidates().get(i).getName());
                // Recebe escolha lista
                aux = input.readLine();
                idList = Integer.parseInt(aux);

                // Envia informaçao de voto para RMI
                vote(idElection-1, idList-1, election.get(idElection-1).getCandidates().size());
            }
            // Rejeita autenticaçao e fecha thread
            else {
                output.println("Failed authentication\nRe-Identify yourself to continue");
                System.out.println("Failed authentication\n Closing connection to client");
            }
        } catch (IOException e) { }
    }

    public boolean authenticateUser (String username, String password) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(userID).getName().compareTo(username) == 0 && user.get(userID).getPassword().compareTo(password) == 0)
                return true;
        }
        return false;
    }

    public void vote(int idElection, int idList, int listSize) throws IOException,NumberFormatException{
        try {
            // Voto nulo
            if(idList>listSize){
                System.out.println("implementar voto nulo");
            } else{
                // Voto em branco
                if(idList == -1) {
                    System.out.println("implementar voto em branco");
                }

                // Voto numa lista
                else {
                    /*System.out.println("votou numa lista");
                    if(user.get(userID).hasVoted(election.get(idElection)))
                        System.out.println("ja votou");
                    else
                        System.out.println("ainda nao votou");

                    if(user.get(userID).voteElection(election.get(idElection), candidateList.get(idList))){
                        System.out.println("Votou\t Eleiçao: "+idElection+"lista: "+idList);
                        System.out.println(election.get(idElection).getInfo());
                        System.out.println(candidateList.get(idList).getInfo());
                    }
                    else
                        System.out.println("user ja votou na eleição "+idElection);
*/
                }
            }
        } catch (NullPointerException i){}
        output.println("Vote Accepted\n You can logout now");
    }
}

