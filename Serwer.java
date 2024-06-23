import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;



class Server {
    private static final int PORT = 9999;
    private static final int MAX_CLIENTS = 5;
    private static Map<String, Object> objects = new ConcurrentHashMap<>();
    private static Set<Integer> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void main(String[] args) throws IOException {
        objects.put("kot_1", new Kot("Stefan"));
        objects.put("kot_2", new Kot("Cat"));
        objects.put("pies_1", new Pies("Dog"));
        objects.put("pies_2", new Pies("Fado"));
        objects.put("kura_1", new Krowa("Chicken"));
        objects.put("kura_2", new Krowa("Curry"));

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started and listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                int clientId = in.readInt();

                if (clients.size() >= MAX_CLIENTS) {
                    out.writeObject("REFUSED");
                    return;
                }

                clients.add(clientId);
                out.writeObject("OK");

                while (true) {
                    Thread.sleep(new Random().nextInt(1500)); // Random delay
                    String request = (String) in.readObject();

                    if (request.startsWith("get_")) {
                        String className = request.substring(4).toLowerCase();
                        List<Object> response = new ArrayList<>();
                        for (Map.Entry<String, Object> entry : objects.entrySet()) {
                            if (entry.getKey().contains(className)) {
                                response.add(entry.getValue());
                            }
                        }
                        out.writeObject(response);
                        System.out.println("Sent " + className + " objects to client " + clientId);
                    } else {
                        break;
                    }
                }
                clients.remove(clientId);

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
