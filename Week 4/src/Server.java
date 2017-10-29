import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;

public class Server extends Thread{

    ServerSocket serverSocket;
    Socket connection;
    static ArrayList<Socket> socketList = new ArrayList();

    public Server(Socket clientSocket) {
        this.connection = clientSocket;
    }

    public void run(){
        //Runs for a new thread - receive and send messages.
        try
        {
            //Create input stream for the threads client
            DataInputStream inputStream = new DataInputStream(connection.getInputStream());

            //Establish client name
            String clientName = inputStream.readUTF();

            //Infinitely listen for incoming messages
            while (true) {
                String message = inputStream.readUTF();
                System.out.println(clientName + ": " + message);

                //Loop through all of the sockets and send the message to them
                for (Socket socket:socketList)
                {
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(clientName + ": " + message);
                }
            }
        }
        catch (SocketException se)
        {
            System.out.println("Connection lost in thread " + Thread.currentThread().getId() + "...");
            Thread.currentThread().interrupt();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException");
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) throws IOException {

        //Establish the server host
        Server serverObj = new Server(new Socket());
        serverObj.serverSocket = new ServerSocket(1337, 1, InetAddress.getLocalHost());

        //Infinite loop to accept multiple clients
        while (true) {
            //Wait for a connecting client
            serverObj.connection = serverObj.serverSocket.accept();

            //Start a new thread to listen for messages
            (new Server(serverObj.connection)).start();

            //Add the socket to the list so it can receive other clients messages.
            socketList.add(serverObj.connection);
        }

    }

}
