package ServerPackage;

import RMIPackage.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;


public class TCPServer {
    public static final boolean DEBUG = false;

    public TCPServerInterface tcp;

    // Contrutor: Inicializa conexão ao RMI
    public TCPServer(){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) { System.out.println("Error connecting to RMI"); }
    }

    // Main: À espera de novas ligações ao socket
    public static void main(String[] args){
        int numero = 0;
        try{
            int serverPort = 6000;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                numero++;
                Connection newClient = new Connection(clientSocket, numero);
                newClient.start();
            }
        }catch(IOException e) { System.out.println("Error connecting to client"); }
    }
}

class Connection extends Thread{
    public static final boolean DEBUG = false;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Socket clientSocket;
    int thread_number;

    TCPServer tcpServer = new TCPServer();
    ArrayList<User> user = new ArrayList<>();
    ArrayList<candidateList> candidateList = new ArrayList<>();
    ArrayList<Department> depList = new ArrayList<>();

    // Construtor: Inicializa dados do socket e do RMI
    public Connection (Socket aClientSocket, int numero) {
        thread_number = numero;
        try{
            clientSocket = aClientSocket;

            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());

            user = tcpServer.tcp.getUsers();
            candidateList = tcpServer.tcp.getCandidateList();
            depList = tcpServer.tcp.getDepList();
        }catch(IOException e){ }
    }

    // Run: Aceita Cliente
    public void run(){
        String data;
        boolean bool;
            try {
                while (true) {
                    // Recebe ID do cliente
                    data = inputStream.readUTF();
                    System.out.println("Client "+thread_number+" requested access");
                    bool = false;

                    // Percorre lista de users
                    for (int i = 0; i < user.size(); i++) {
                        if (Integer.parseInt(data) == Integer.parseInt(user.get(i).getID())) {
                            if(DEBUG) System.out.println("\t#DEBUG# Client "+thread_number+"'s ID match");

                            //DEBUG
                            outputStream.writeUTF(user.get(i).getInfo());

                            // Recebe username
                            String username = inputStream.readUTF();

                            //Verifica username
                            if (username.compareTo(user.get(i).getName())==0) {
                                outputStream.writeBoolean(true);
                                if(DEBUG) System.out.println("\t#DEBUG# Client "+thread_number+"'s Usernames match");

                                //Recebe password
                                String password = inputStream.readUTF();

                                //Verifica password
                                if (password.compareTo(user.get(i).getPassword())==0){
                                    outputStream.writeBoolean(true);
                                    if(DEBUG)System.out.println("\t#DEBUG# Client "+thread_number+"'s Passwords match");

                                    System.out.println("Client "+thread_number+" access granted");/*
                                    //Cliente pode votar
                                    //
                                    //
                                    //
                                    */
                                }
                                else {
                                    // Rejeita password
                                    outputStream.writeBoolean(false);
                                    if(DEBUG)System.out.println("\t#DEBUG# Client "+thread_number+"'s Password did not match");
                                }
                            }
                            else {
                                // Rejeita username
                                outputStream.writeBoolean(false);
                                if(DEBUG) System.out.println("\t#DEBUG# Client "+thread_number+"'s Usernames did not match");
                            }
                            bool = true;
                        }
                    }
                    if (!bool){
                        // Rejeita ID
                        if(DEBUG) System.out.println("\t#DEBUG# Client "+thread_number+"'s ID match");
                        outputStream.writeUTF("UserIDNotFound");
                    }
                }

            } catch (EOFException e) {
                System.out.println("Client "+thread_number+" disconnected");
            } catch (IOException e) {
                System.out.println("IO:" + e);
            }

    }
}

