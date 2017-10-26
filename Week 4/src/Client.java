import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client {

    static Socket client;

    private String setName() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your name:");
        String name = null;
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

    public static void main(String[] args) throws IOException {
        Client chatClient = new Client();
        //Connect to the server
        try
        {
            client = new Socket(InetAddress.getLocalHost(), 1337);
            DataOutputStream messageStream = new DataOutputStream(client.getOutputStream());
            messageStream.writeUTF(chatClient.setName()); //Establish client name
            System.out.println("Connection established!");

            //Infinite loop checking for messages sent.
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String message = br.readLine();
                //
                messageStream.writeUTF(message);
            }
        }
        catch (SocketException se)
        {
            System.out.println("Connection to the server lost...");
            System.exit(0);
        }

    }
}
