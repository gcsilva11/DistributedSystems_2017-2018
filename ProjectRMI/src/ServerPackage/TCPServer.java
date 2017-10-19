package ServerPackage;

import RMIPackage.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class TCPServer {
    public static final boolean DEBUG = true;
    public TCPServerInterface tcp;
    public TCPServer(){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) { System.out.println("Error connecting to RMI"); }
    }
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
    public static final boolean DEBUG = true;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Socket clientSocket;
    int thread_number;

    ArrayList<User> user = new ArrayList<>();
    TCPServer tcpServer = new TCPServer();

    public Connection (Socket aClientSocket, int numero) {
        thread_number = numero;
        try{
            clientSocket = aClientSocket;
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            user = tcpServer.tcp.getUsers();
        }catch(IOException e){ }
    }

    public void run(){
        String data;
        boolean bool;
            try {
                while (true) {
                    data = inputStream.readUTF();
                    if(DEBUG)System.out.println("Received "+data+" from Client "+thread_number);
                    bool = false;

                    for (int i = 0; i < user.size(); i++) {

                        if (Integer.parseInt(data) == Integer.parseInt(user.get(i).getID())) {
                            outputStream.writeUTF(user.get(i).getInfo());
                            String username = inputStream.readUTF();

                            if (username.compareTo(user.get(i).getName())==0) {
                                outputStream.writeBoolean(true);
                                if(DEBUG) System.out.println("\t#DEBUG# Client "+thread_number+"'s Usernames match");

                                String password = inputStream.readUTF();
                                if (password.compareTo(user.get(i).getPassword())==0){
                                    outputStream.writeBoolean(true);
                                    if(DEBUG)System.out.println("\t#DEBUG# Client \"+thread_number+\"'s Passwords match");
                                    /*
                                    //Cliente pode votar
                                    //
                                    //
                                    //
                                    */
                                }
                                else {
                                    outputStream.writeBoolean(false);
                                    if(DEBUG)System.out.println("\t#DEBUG# Client \"+thread_number+\"'s Password did not match");
                                }
                            }
                            else {
                                outputStream.writeBoolean(false);
                                if(DEBUG) System.out.println("\t#DEBUG# Client \"+thread_number+\"'s Usernames did not match");
                            }
                            bool = true;
                        }
                    }
                    if (!bool)
                        outputStream.writeUTF("UserIDNotFound");
                }

            } catch (EOFException e) {
                System.out.println("Client "+thread_number+" disconnected");
            } catch (IOException e) {
                System.out.println("IO:" + e);
            }

    }
}

