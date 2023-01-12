import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultipleClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numerul de clienti:");
        int numClients = scanner.nextInt();
        for (int i = 0; i < numClients; i++) {
            Thread thread = new Thread(new ClientHandler());
            thread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket("localhost", 8000);
                System.out.println("Conectat la portul 8000");

                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();

                // Allow user to choose files to send to server
                Scanner scanner = new Scanner(System.in);
                System.out.println("Introduceti numarul de file-uri de vreti sa le concatenati:");
                int numFiles = scanner.nextInt();
                File[] files = new File[numFiles];
                for (int i = 0; i < numFiles; i++) {
                    System.out.println("Introduceti numele file-ului nr. " + (i+1) + ":");
                    String fileName = scanner.next();
                    files[i] = new File(fileName);
                }

                // Send files to server
                for (File file : files) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    FileInputStream fis = new FileInputStream(file);
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    fis.close();
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
