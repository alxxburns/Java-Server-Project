import java.io.*;
import java.net.*;

public class DogServer {
    private static int connectionCount = 0; // Counter for the number of connections
    private static int dogCount = 0; // Counter for the number of dogs
    private static int humanCount = 0; // Counter for the number of humans

    public static void main(String[] a) throws IOException {
        int port = 4444;
        ServerSocket servsock = new ServerSocket(port);

        String query = "If you met me, would you shake my hand, or sniff it?";

        while (true) {
            Socket client = servsock.accept();
            handleConnection(client);
        }
    }

    private static void handleConnection(Socket client) throws IOException {
        connectionCount++; // Increment the connection count

        PrintStream out = new PrintStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String query = "If you met me, would you shake my hand, or sniff it?";
        out.println(query);
        out.flush();

        String reply = in.readLine();
        if (reply != null && reply.contains("sniff")) {
            System.out.println("On the Internet, I know this is a DOG!");
            out.println("You're a dog.");
            dogCount++; // Increment the dog count
        } else {
            System.out.println("Probably a person or an AI experiment");
            out.println("You're a person or something.");
            humanCount++; // Increment the human count
        }
        out.flush();

        client.close();

        System.out.println("Number of connections made: " + connectionCount);
        System.out.println("Number of dogs: " + dogCount);
        System.out.println("Number of humans: " + humanCount);
    }
}

