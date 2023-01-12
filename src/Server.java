import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started on port 8000");

        while (true) {

            Socket socket = serverSocket.accept();
            InetAddress clientIP = socket.getInetAddress();
            String hostName = clientIP.getHostName();


            Thread thread = new Thread(new ClientHandler(socket, hostName));
            thread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private String hostName;

        public ClientHandler(Socket socket, String hostName) {
            this.socket = socket;
            this.hostName = hostName;
        }

        @Override
        public void run() {
            try {
                // Cream un file cu host name
                File hostFile = new File(hostName + ".txt");
                hostFile.createNewFile();

                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();

                byte[] buffer = new byte[4096];
                int bytesRead;

                // Citim stream-ul de la client si inscriem in host name file
                FileOutputStream fos = new FileOutputStream(hostFile, true);
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
