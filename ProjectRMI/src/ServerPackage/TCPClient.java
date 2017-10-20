package ServerPackage;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static final boolean DEBUG = false;

    // Main
    public static void main(String args[]) {
        Socket socket = null;
        int serversocket = 6000;
        try {
            //Cria ligação ao socket
            socket = new Socket("localhost", serversocket);
            String text = "";

            // Inicializa dados socket
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);

            while (true) {
                System.out.println("\nInsert User ID (0 to Quit): ");
                try {
                    text = reader.readLine();
                } catch (Exception e) {
                }
                // Termina se 0
                if(Integer.parseInt(text)==0)
                    return;

                //Envia ID a pesquisar
                outputStream.writeUTF(text);

                //DEBUG
                String data = inputStream.readUTF();
                if(DEBUG)System.out.println("\t#DEBUG# "+data+"\n");

                // Rejeita ID
                if(data.compareTo("UserIDNotFound")==0){
                    System.out.println("User ID not valid");
                }
                // Aceita ID: Cria Thread Terminal de Voto
                else{
                    Terminal votingTerminal = new Terminal(socket,text);
                    votingTerminal.start();
                    try {
                        votingTerminal.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}

class Terminal extends Thread {
    public static final boolean DEBUG = false;
    private String id;
    private Socket clientSocket;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    // Construtor: Inicializa dados socket
    public Terminal(Socket s ,String id) {
        this.clientSocket = s;
        this.id = id;
        try {
            this.inputStream = new DataInputStream(this.clientSocket.getInputStream());
            this.outputStream = new DataOutputStream(this.clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" - Vote Terminal Unblocked - \n");
    }

    public void run(){
        // Inicializa dados socket
        String text = "";
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        System.out.println("Insert Username:");

        try {
            text = reader.readLine();
        } catch (Exception e) {
        }
        try {
            // Envia username
            outputStream.writeUTF(text);
            boolean user_bool = inputStream.readBoolean();
            if(DEBUG)System.out.println("\t#DEBUG# User "+user_bool);

            // Aceitou username
            if(user_bool){
                System.out.println("Insert Password:");
                try{
                    text = reader.readLine();
                } catch (Exception e){
                }

                // Envia password
                outputStream.writeUTF(text);
                boolean pass_bool = inputStream.readBoolean();
                if(DEBUG)System.out.println("\t#DEBUG# Password "+pass_bool);

                // Aceita password
                if(pass_bool){
                    if(DEBUG)System.out.println("\t#DEBUG# Authentication Complete");

                    System.out.println("\ttoImplement: Voting Act");
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*
                    //Cliente pode votar
                    //
                    //
                    //
                    */
                }
                // Rejeita password
                else{
                    System.out.println("Wrong Password");
                }
            }
            // Rejeita username
            else{
                System.out.println("Could not find Username");
            }

        } catch (IOException e) {
        }
        if(DEBUG)System.out.println("\t#DEBUG# Vote Terminal Job Complete");
        System.out.println(" - Vote Terminal Blocked - ");
    }
}