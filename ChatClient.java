import java.io.*;
import java.net.*;

public class ChatClient {
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(String serverAddress) throws IOException {
        Socket socket = new Socket(serverAddress, 12345);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Read messages from server in a new thread
        new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Read messages from user and send to server
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String message = userInput.readLine();
            if (message != null) {
                out.println(message);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java ChatClient <server address>");
            return;
        }
        new ChatClient(args[0]);
    }
}
