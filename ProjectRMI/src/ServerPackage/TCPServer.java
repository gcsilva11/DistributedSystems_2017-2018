package ServerPackage;

import RMI.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

public class TCPServer {
    public static RMIServerInterface tcp;
    private static String userID;

    // Contrutor: Inicializa conexão ao RMI
    public TCPServer(String hostname,int rmiPort){
        int failed = 0;
        while (failed <= 30) {
            try {
                tcp = (RMIServerInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
                break;
            } catch (RemoteException|NotBoundException e) {
                System.out.println("RMI Timeout on User registry, trying to reconnect");
                try {
                    Thread.sleep(500);
                    tcp = (RMIServerInterface) LocateRegistry.getRegistry(hostname,rmiPort).lookup("vote_booth");
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

        System.out.println("ID da faculdade:");
        int facID = sc.nextInt();

        if(tcp.checkFaculdade(facID)) {

            // while(true) a aceitar novos clientes
            ServerSocket listenSocket = null;
            while (true) {
                try {
                    System.out.println("\nPronto para receber user");
                    // Cria e aceita socket connection
                    listenSocket = new ServerSocket(defPort);
                    Socket clientSocket = listenSocket.accept();

                    clientSocket.setSoTimeout(120000);

                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Thread que trata da identificação de users no TCP Server
                    new Thread() {
                        // getListaUsers
                        // Identifica user
                        public void run() {
                            try {
                                while (true) {
                                    Scanner sc = new Scanner(System.in);

                                    System.out.println("\nIdentificacao por ID: ");
                                    int data = Integer.parseInt(sc.nextLine());
                                    if (tcp.identifyID(data, facID)) {
                                        System.out.println("\nTerminal de Voto desbloqueado");
                                        Connection newClient = new Connection(clientSocket, data, hostname, rmiPort, tcp, facID);
                                        newClient.start();
                                    } else {
                                        System.out.println("\nID nao encontrado");
                                    }
                                }
                            } catch (RemoteException e) {
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    System.out.println("Error creating TCP socket");
                }
                try {
                    listenSocket.close();
                } catch (IOException e) {
                }
            }

        } else System.out.println("Faculdade nao existente");
    }
}

class Connection extends Thread {
    private BufferedReader input;
    private PrintWriter output;

    private Socket clientSocket;

    private StringTokenizer token;

    private TCPServer tcpServer;

    private RMIServerInterface tcp;

    private String hostname;
    private int userID, rmiPort, facID;

    // Construtor: Inicializa dados do socket e do RMI
    public Connection(Socket aClientSocket, int userID, String hostname, int rmiPort, RMIServerInterface tcp, int facID) {
        this.clientSocket = aClientSocket;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.userID = userID;
            this.hostname = hostname;
            this.rmiPort = rmiPort;
            this.tcpServer = new TCPServer(this.hostname, this.rmiPort);
            this.tcp = tcp;
            this.facID = facID;
        } catch (IOException e) {
        }
    }

    // Run: Aceita Cliente
    public void run() {
        StringTokenizer tokenizer;
        String data = "", aux = "";
        try {
            output.println("-->Welcome to iVotas<--");

            // Recebe user e pass
            output.println("\nLogin [username]/[password]");
            data = input.readLine();

            // Trata dados recebidos
            tokenizer = new StringTokenizer(data, "/");
            try {
                data = tokenizer.nextToken();
                aux = tokenizer.nextToken();
            } catch (NoSuchElementException e) {
            }

            // Verifica se user pertence à faculdade

            // Autentica user
            if (tcp.authenticateUser(data, aux)) {

                // Lista eleicoes
                ArrayList<Integer> eleicoes = tcp.getMesaDeVotoEls(facID);
                output.println("\nEscolha eleicao para votar: ");
                for (int i = 0; i < eleicoes.size(); i++) {
                    if (!tcp.getElName(eleicoes.get(i)).equals("") && !tcp.hasVoted(userID,eleicoes.get(i)) && tcp.userCanVote(userID,eleicoes.get(i)) && tcp.isElActive(eleicoes.get(i)))
                        output.println(eleicoes.get(i) + ". " + tcp.getElName(eleicoes.get(i)));
                }
                aux = input.readLine();
                int idEleicao = Integer.parseInt(aux);

                // Lista listas candidatas
                ArrayList<Integer> listas = tcp.getElectionLists(idEleicao);
                output.println("\nEscolha lista para votar: ");
                for (int i = 0; i < listas.size(); i++) {
                    if (!tcp.getListName(listas.get(i),idEleicao).equals("") && !tcp.getListName(listas.get(i),idEleicao).equals("NULLVOTE")) {
                        if (!tcp.getListName(listas.get(i),idEleicao).equals("BLANKVOTE"))
                            output.println(listas.get(i) + ". " + tcp.getListName(listas.get(i),idEleicao));
                        else
                            output.println(listas.get(i) + ". ");
                    }
                }
                aux = input.readLine();
                int idLista = Integer.parseInt(aux);

                boolean contains = false;
                for (int i = 0;i<listas.size();i++)
                    if(listas.get(i) == idLista) contains = true;

                // Efetua voto
                if (contains) {
                    if (tcp.voteElection(userID, idEleicao, idLista, facID))
                        output.println("\nVoto registado!");
                    else
                        output.println("\nErro");
                }
                else {
                    for (int i = 0 ;i<listas.size();i++){
                        if(tcp.getListName(listas.get(i),idEleicao).equals("NULLVOTE"))
                            idLista = listas.get(i);
                    }
                    if (tcp.voteElection(userID, idEleicao, idLista, facID))
                        output.println("\nVoto registado!");
                    else
                        output.println("\nVoto registado!");
                }

            }
            // Rejeita autenticaçao e fecha thread
            else {
                output.println("Autenticacao falhada\n\tA fechar sessao...");
                System.out.println("Autenticacao falhada\n\tA fechar sessao");
            }
        } catch (NullPointerException e) {
        } catch (SocketTimeoutException e) {
            output.println("Session expired");
            try {
                clientSocket.close();
            } catch (IOException e1) {
            }
        } catch (IOException e) {
        }
    }
}

