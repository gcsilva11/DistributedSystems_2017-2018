package ServerPackage;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) {
        Socket s = null;
        int serversocket = 6000;
        try {
            s = new Socket("localhost", serversocket);
            System.out.println("SOCKET=" + s);

            String texto = "";

            DataInputStream inputStream;
            DataOutputStream outputStream;
            InputStreamReader input;
            BufferedReader reader;

            while (true) {
                inputStream = new DataInputStream(s.getInputStream());
                outputStream = new DataOutputStream(s.getOutputStream());
                input = new InputStreamReader(System.in);
                reader = new BufferedReader(input);

                System.out.println("\nIntroduza User ID:");

                try {
                    texto = reader.readLine();
                } catch (Exception e) {
                }


                outputStream.writeUTF(texto);
                String data =  inputStream.readUTF();

                System.out.println(data);

                boolean bol = inputStream.readBoolean();

                if (bol) {
                    Terminal votingTerminal = new Terminal();
                    votingTerminal.start();
                    try {
                        votingTerminal.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(bol);
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
    public Terminal() {
    }

    public void run(){
        System.out.println("Hello world!");
    }
}