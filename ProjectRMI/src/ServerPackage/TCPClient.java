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
                identifyUser(output, input);
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

    // Identifica utilizador
    public static void identifyUser(PrintWriter output, BufferedReader input) {
        Scanner sc = new Scanner(System.in);

        Terminal votingTerminal;

        String data;
        int choice;

        System.out.println("\nSelect how to identfy yourself:\n1. ID\n2. Name\n0. Quit");
        choice = sc.nextInt();
        switch (choice) {
            case 0:
                System.exit(0);
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

                try {
                    data = input.readLine();
                } catch (IOException e) { }
                if (DEBUG) System.out.println("\t#DEBUG# Recebeu " + data);

                if (data.compareTo("NotFound") != 0) {
                    votingTerminal = new Terminal(input, output);
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

                try {
                    data = input.readLine();
                } catch (IOException e) { }
                if (DEBUG) System.out.println("\t#DEBUG# Recebeu " + data);

                if (data.compareTo("NotFound") != 0) {
                    votingTerminal = new Terminal(input, output);
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
}

// Terminal de Voto
class Terminal extends Thread{
    public static final boolean DEBUG = true;

    private BufferedReader input;
    private PrintWriter output;
    private Scanner sc = new Scanner(System.in);

    public Terminal(BufferedReader input, PrintWriter output){
        this.input = input;
        this.output = output;
    }
    public void run() {
        /*
        //
        // FALTA ESCOLHER ELEIÇÃO CASO EXISTAM MAIS DO QUE UMA
        // FALTA BLOQUEAR PASSADOS 120s DE INATIVIDADE
        //
        */
        System.out.println(" - Vote Terminal Unblocked - ");

        // Autenticação
        autenticateUser();

        System.out.println(" - Vote Terminal Blocked - ");

    }

    // Autentica utilizador
    public void autenticateUser() {
        try {
            String data;

            System.out.println("Insert \"[username]/[password]\":");
            data = sc.nextLine();

            // Envia username & password
            output.println(data);
            if (DEBUG) System.out.println("\t#DEBUG# Enviou " + data);

            data = this.input.readLine();

            if (DEBUG) System.out.println("\t#DEBUG# Recebeu " + data);
            // Aceita username & password
            if (data.compareTo("true") == 0) {
                if (DEBUG) System.out.println("\t#DEBUG# Autenticação completa");
                voteAct();
            // Rejeita
            } else
                System.out.println("Could not find Username or Password");

        } catch (IOException e) {
        }
        if (DEBUG) System.out.println("\t#DEBUG# Autenticação terminou");
    }

    // Utilizador vota
    public void voteAct() {
        System.out.println("Vote por implementar");
        /*
        //
        // FALTA CLIENTE VOTAR!!
        // QUANDO VOTA ESCREVE NO CLIENTE ONDE VOTOU E TITULO DA ELEICAO
        //
        */
    }
}