package ServerPackage;

import RMIPackage.*;

import java.net.*;
import java.io.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class TCPServer {
    public TCPServerInterface tcp;
    public TCPServer(){
        try {
            tcp = (TCPServerInterface) LocateRegistry.getRegistry(6500).lookup("vote_booth");
        } catch (RemoteException|NotBoundException e) {
            e.printStackTrace();
        }
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
        }catch(IOException e)
        {System.out.println("Listen:" + e.getMessage());}
    }
}

class Connection extends Thread{
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
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
    }

    public void run(){
        String data;
        boolean bol;
            try {
                while (true) {
                    data = inputStream.readUTF();
                    System.out.println("T[" + thread_number + "] Recebeu: " + data);
                    bol = false;
                    for (int i = 0; i < user.size(); i++) {
                        if (Integer.parseInt(data) == Integer.parseInt(user.get(i).getID())) {
                            outputStream.writeUTF(user.get(i).getInfo());
                            bol = true;
                        }
                    }
                    if(!bol)
                        outputStream.writeUTF("UserIDNotFound");

                    System.out.println("\t\t" + bol);
                    outputStream.writeBoolean(bol);
                }


            } catch (EOFException e) {
                System.out.println("EOF:" + e);
            } catch (IOException e) {
                System.out.println("IO:" + e);
            }

    }
}

