package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class establishes a TCP connection to a specified server, and loops
 * sending/receiving strings to/from the server.
 * <p>
 * The main() method receives two arguments specifying the server address and
 * the listening port.
 * <p>
 * The usage is similar to the 'telnet <address> <port>' command found in most
 * operating systems, to the 'netcat <host> <port>' command found in Linux,
 * and to the 'nc <hostname> <port>' found in macOS.
 *
 * @author Raul Barbosa
 * @author Alcides Fonseca
 * @version 1.1
 */
class TCPClient {
    public static final String def_address = "localhost";
    public static final int def_port = 6000;

    public static void main(String[] args) {
        Socket socket;

        PrintWriter output;
        BufferedReader input = null;

        try {
            // create socket connection
            if (args.length == 2)
                socket = new Socket(args[0], Integer.parseInt(args[1]));
            else
                socket = new Socket(def_address, def_port);

            // create streams for writing to and reading from the socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            // create a thread for reading from the keyboard and writing to the server
            new Thread() {
                public void run() {
                    Scanner keyboardScanner = new Scanner(System.in);
                    while(!socket.isClosed()) {
                        String readKeyboard = keyboardScanner.nextLine();
                        output.println(readKeyboard);
                    }
                }
            }.start();

            // the main thread loops reading from the server and writing to System.out
            String messageFromServer;
            while((messageFromServer = input.readLine()) != null)
                System.out.println(messageFromServer);
        } catch (IOException e) {
            if(input == null)
                System.out.println("\nUsage: java TCPClient <host> <port>\n");
            System.out.println(e.getMessage());
        } finally {
            try { input.close(); } catch (Exception e) {}
        }
    }
}
