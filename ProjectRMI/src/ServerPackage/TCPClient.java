package ServerPackage;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static final boolean DEBUG = true;
    public static void main(String args[]) {
        Socket s = null;
        int serversocket = 6000;
        try {
            s = new Socket("localhost", serversocket);
            String text = "";

            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);

            while (true) {

                System.out.println("\nInsert User ID:");

                try {
                    text = reader.readLine();
                } catch (Exception e) {
                }

                outputStream.writeUTF(text);
                String data = inputStream.readUTF();

                if(DEBUG)System.out.println("\t#DEBUG#\n"+data+"\n");

                if(data.compareTo("UserIDNotFound")==0)
                    System.out.println("User ID not valid");

                else{
                    Terminal votingTerminal = new Terminal(s,text);
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
            if (s != null){
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }
}

class Terminal extends Thread {
    public static final boolean DEBUG = true;
    private String id;
    private Socket clientSocket;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
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
        String text = "";
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        System.out.println("Insert Username:");

        try {
            text = reader.readLine();
        } catch (Exception e) {
        }
        try {
            outputStream.writeUTF(text);
            boolean user_bool = inputStream.readBoolean();
            if(DEBUG)System.out.println("\t#DEBUG# User "+user_bool);

            if(user_bool){
                System.out.println("Insert Password:");
                try{
                    text = reader.readLine();
                } catch (Exception e){
                }

                outputStream.writeUTF(text);
                boolean pass_bool = inputStream.readBoolean();
                if(DEBUG)System.out.println("\t#DEBUG# Password "+pass_bool);

                if(pass_bool){
                    if(DEBUG)System.out.println("\t#DEBUG# Authentication Complete");
                    /*
                    //Cliente pode votar
                    //
                    //
                    //
                    */
                }
                else{
                    boolean bool = inputStream.readBoolean();
                    System.out.println(bool);
                    System.out.println("Wrong Password");
                }
            }
            else{
                boolean bool = inputStream.readBoolean();
                System.out.println(bool);
                System.out.println("Could not find Username");
            }

        } catch (IOException e) {
        }
        if(DEBUG)System.out.println("\t#DEBUG# Vote Terminal Job Complete");
    }
}