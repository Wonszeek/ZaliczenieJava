import java.io.*;
import java.net.*;
import java.util.*;

public class Klient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            int clientId = i;
            new Thread(() -> {
                try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    System.out.println("Klient: " + clientId + " connected");
                    out.writeInt(clientId);
                    String response = (String) in.readObject();

                    if ("REFUSED".equals(response)) {
                        System.out.println("Client " + clientId + " was refused connection");
                        return;
                    }

                    System.out.println("Client " + clientId + " connected successfully");

                    String[] requests = {"get_kot", "get_pies", "get_kon"};
                    for (String request : requests) {
                        out.writeObject(request);
                        List<?> objects = (List<?>) in.readObject();
                        System.out.println("Received objects for " + request + ": " + objects);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
