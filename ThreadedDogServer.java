import java.io.*;
import java.net.*;

/**
 * A network server that detects the presence of dogs on the Internet.
 * This version of dogserver is multithreaded, spawning one new thread for each client.
 *
 * @version   1.2 March 26 2000
 * @author    Joseph Bergin
 * Adapted from the book "Just Java" by Peter van der Linden
 * Adapted by J. Bergin
 */
public class ThreadedDogServer {
    private static int connectionCount = 0; // Counter for the number of connections

    public static void main(String[] a) throws IOException {
        int port = 4444;
        Socket client = null;
        ServerSocket servsock = new ServerSocket(port);

        while (true) {
            client = servsock.accept();
            connectionCount++; // Increment the connection count
            new ClientHandler(client).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket client;

        ClientHandler(Socket client) {
            this.client = client;
        }

        public void run() {
            try (
                PrintStream out = new PrintStream(client.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))
            ) {
                String query = "If you met me, would you shake my hand, or sniff it?";
                out.println(query);
                out.flush();

                String reply = in.readLine();
                if (reply != null && reply.toLowerCase().contains("sniff")) {
                    System.out.println("On the Internet, I know this is a DOG!");
                    out.println("You're a dog.");
                } else {
                    System.out.println("Probably a person or an AI experiment");
                    out.println("You're a person or something.");
                }
                out.flush();

                // All done. Close this connection
                client.close();
            } catch (IOException e) {
                System.out.println("Missed client.");
                e.printStackTrace();
            } finally {
                connectionCount--; // Decrement the connection count
            }
        }
    }
}
