import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server extends Thread{

    ServerSocket serverSocket;
    Socket connection;
    static ArrayList<Socket> socketList = new ArrayList();
    static ArrayList<String> socketNames = new ArrayList();

    public Server(Socket clientSocket) {
        this.connection = clientSocket;
    }

    public void sendPM(String senderName, String message)
    {
        try
        {
            Pattern pmPattern = Pattern.compile("\\/pm (\\w+) (\\w+)");
            Matcher pmMatcher = pmPattern.matcher(message);
            if (pmMatcher.matches())
            {
                String recipientName = pmMatcher.group(1);
                String pmMessage = pmMatcher.group(2);
                for (int i = 0; i < socketList.size(); i++)
                {
                    DataOutputStream outputStream = new DataOutputStream(this.socketList.get(i).getOutputStream());
                    if (recipientName.equals(this.socketNames.get(i)))
                    {
                        outputStream.writeUTF("PM from " + senderName + ": " + pmMessage);
                    }

                }
            }

        }
        catch (IOException ioe)
        {
            System.out.println("IOException to " + senderName);
            int index = socketNames.indexOf(senderName);
            socketNames.remove(index);
            socketList.remove(index);
            Thread.currentThread().interrupt();
        }
    }

    public void run(){
        //Runs for a new thread - receive and send messages.
        String clientName = null;
        try
        {
            //Create input stream for the threads client
            DataInputStream inputStream = new DataInputStream(connection.getInputStream());

            //Establish client name
            clientName = inputStream.readUTF();
            for (String name:socketNames)
            {
                //Check for duplicate names
                if (clientName.equals(name))
                {
                    DataOutputStream rejectionOutputStream = new DataOutputStream(connection.getOutputStream());
                    rejectionOutputStream.writeUTF("Client with that name already exists.");
                    Thread.currentThread().interrupt();
                }
            }
            socketNames.add(clientName);

            //Infinitely listen for incoming messages
            while (true) {
                String message = inputStream.readUTF();
                System.out.println(clientName + ": " + message);

                //Check for pm
                if (message.startsWith("/pm"))
                {
                    sendPM(clientName, message);
                }
                else
                {
                    //Loop through all of the sockets and send the message to them
                    for (int i = 0; i < socketList.size(); i++)
                    {
                        DataOutputStream outputStream = new DataOutputStream(this.socketList.get(i).getOutputStream());
                        if (!(clientName.equals(this.socketNames.get(i))))
                        {
                            outputStream.writeUTF(clientName + ": " + message);
                        }

                    }
                }

            }
        }
        catch (SocketException se)
        {
            System.out.println("Connection lost to " + clientName);
            int index = socketNames.indexOf(clientName);
            socketNames.remove(index);
            socketList.remove(index);
            Thread.currentThread().interrupt();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException to " + clientName);
            int index = socketNames.indexOf(clientName);
            socketNames.remove(index);
            socketList.remove(index);
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
