package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.*;


class TCPClient {
    public static final boolean DEBUG = true;
    public static final String def_address = "localhost";
    public static final int def_port = 6000;

    public static void main(String[] args) {
        Socket socket = null;

        PrintWriter output;
        BufferedReader input = null;

        Terminal votingTerminal;

        String data;
        int choice;

        Scanner sc = new Scanner(System.in);

        try {
            // Conecta ao socket address:port (default -> localhost:6000)
            if (args.length == 2)
                socket = new Socket(args[0], Integer.parseInt(args[1]));
            else
                socket = new Socket(def_address, def_port);

            // Cria streams para ler e escrever no socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true){
                System.out.println("\nSelect how to identfy yourself:\n1. ID\n2. Name\n0. Quit");
                choice = sc.nextInt();
                switch (choice) {
                    case 0:
                        output.println("quit");
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou exit");
                        return;
                    case 1:
                        output.println("true");
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou true");

                        System.out.println("\nEnter ID:");
                        sc = new Scanner(System.in);
                        data = sc.nextLine();

                        output.println(data);
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou " + data);

                        data = input.readLine();
                        if (DEBUG) System.out.println("\t#DEBUG# Recebeu " + data);

                        if (data.compareTo("NotFound") != 0) {
                            votingTerminal = new Terminal(socket, input, output, data);
                            votingTerminal.start();
                            try {
                                votingTerminal.join();
                            } catch (InterruptedException e) {
                            }
                        } else
                            System.out.println("ID not found");
                        break;
                    case 2:
                        output.println("false");
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou false");

                        System.out.println("\nEnter Name:");
                        sc = new Scanner(System.in);
                        data = sc.nextLine();

                        output.println(data);
                        if (DEBUG) System.out.println("\t#DEBUG# Enviou " + data);

                        data = input.readLine();
                        if (DEBUG) System.out.println("\t#DEBUG# Recebeu " + data);

                        if (data.compareTo("NotFound") != 0) {
                            votingTerminal = new Terminal(socket, input, output, data);
                            votingTerminal.start();
                            try {
                                votingTerminal.join();
                            } catch (InterruptedException e) {
                            }
                        } else
                            System.out.println("Name not found");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                input.close();
            } catch (Exception e) {
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

class Terminal extends Thread{
    public static final boolean DEBUG = true;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Scanner sc = new Scanner(System.in);
    private String data;

    public Terminal(Socket socket, BufferedReader input, PrintWriter output, String data){
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.data = data;
    }
    public void run() {
        /*
        //
        //
        // FALTA ESCOLHER ELEIÇÃO CASO EXISTAM MAIS DO QUE UMA
        // FALTA BLOQUEAR PASSADOS 120s DE INATIVIDADE
        //
        //
        */
        System.out.println(" - Vote Terminal Unblocked - ");
        try {
            String data;

            System.out.println("Insert \"[username]/[password]\":");
            data = sc.nextLine();


            // Envia username & password
            output.println(data);
            if(DEBUG)System.out.println("\t#DEBUG# Enviou "+data);


            data = this.input.readLine();
            if(DEBUG)System.out.println("\t#DEBUG# Recebeu "+data);
            if(data.compareTo("true") == 0){
                if(DEBUG)System.out.println("\t#DEBUG# Autenticação completa");
                /*
                //
                //
                // FALTA CLIENTE VOTAR!!
                //
                //
                */
            }
            else
                System.out.println("Could not find Username or Password");
        } catch (IOException e) {
        }
        if(DEBUG)System.out.println("\t#DEBUG# Thread terminou");
        System.out.println(" - Vote Terminal Blocked - ");

    }
}