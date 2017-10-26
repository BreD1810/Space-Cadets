import java.io.DataInputStream;
import java.net.*;
import java.io.IOException;

public class Server extends Thread{

    static ServerSocket server;
    static Socket connection;

    public void run(){

        try
        {
            //Establish the server host
            server = new ServerSocket(1337, 1, InetAddress.getLocalHost());

            //Wait for a connecting client
            System.out.println("Waiting for client connection...");
            connection = server.accept();
            System.out.println("Client connection accepted!");

            DataInputStream inputStream = new DataInputStream(connection.getInputStream());
            String clientName = inputStream.readUTF();

            //Infinite loop so the server never closes
            while (true) {
                String message = inputStream.readUTF();
                System.out.println(clientName + ": " + message);
            }
        }
        catch (SocketException se)
        {
            System.out.println("Connection lost...");
            System.exit(0);
        }
        catch (IOException ioe)
        {

        }
    }


    public static void main(String[] args) throws IOException {

        Server serverObj = new Server();
        serverObj.start();
        //TODO: Figure out multiple clients.

    }

}
