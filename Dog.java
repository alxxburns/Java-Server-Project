// On the Internet no one knows you're a dog...
// unless you tell them.

import java.io.*;
import java.net.*;

class Dog 
{	public static void main(String a[]) throws IOException 
	{	String loopback = "ec2-54-171-91-103.eu-west-1.compute.amazonaws.com"; // "localhost" will also work here. 
		String server = loopback;
		// Open our connection to the server, at port 4444
		Socket sock = new Socket(server,4444);
    
		// Get I/O streams from the socket
		BufferedReader dis = new BufferedReader(new InputStreamReader( sock.getInputStream() ));
		PrintStream dat =  new PrintStream(sock.getOutputStream() );
 		
		// Now we can just read from and write to the streams. 
		// Start with reading for this server. 
		String fromServer = dis.readLine();  
		System.out.println("Got this from server: " + fromServer);
		
		String myReply = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		myReply = in.readLine();
		dat.println(myReply);
 	
		fromServer = dis.readLine();
		System.out.println("More from Server: "+ fromServer);
 	
		// All done. Close the connection.
		sock.close();
	}  
}