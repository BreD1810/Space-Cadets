import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client extends Thread{

    Socket client;
    String name = null;

    Client(Socket socket, String name) {
        this.client = socket;
        this.name = name;
    }

    private String setName() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your name:");

        while (name == null)
        {
            try
            {
                name = br.readLine();
            }
            catch (IOException ioe)
            {
                System.out.println("Error: Please try again!");
            }
        }
        return name;
    }

    public void run() {
        //Listen for messages on an infinite loop
        while (true) {
            try {
                DataInputStream inputStream = new DataInputStream(client.getInputStream());
                String receivedMessage = inputStream.readUTF();
                System.out.println(receivedMessage);
                //End session if the client already exists
                if (receivedMessage.equals("Client with that name already exists."))
                {
                    System.exit(0);
                }
            } catch (IOException ioe) {
                System.out.println("Server connection has been lost...");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        try
        {
            //Required to stop the server from getting nullpointers when checking name (connection accepted before name was set)
            Client tempClient = new Client(null, null);
            //Determine clients name anc connect to server using that name
            String name = tempClient.setName();
            Client chatClient = new Client(new Socket(InetAddress.getLocalHost(), 1337), null);
            DataOutputStream outputStream = new DataOutputStream(chatClient.client.getOutputStream());
            outputStream.writeUTF(name);
            System.out.println("Connection established!");

            //Separate thread for listening for messages
            (new Client(chatClient.client, chatClient.name)).start();

            //Infinite loop for sending messages.
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String message = br.readLine();
                if (message.equals("/quit"))
                {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                outputStream.writeUTF(message);
            }
        }
        catch (SocketException se)
        {
            System.out.println("Connection to the server lost...");
            System.exit(0);
        }

    }
}
