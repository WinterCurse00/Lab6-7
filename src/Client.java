import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        System.out.println("Conectat la portul 8000");

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        // Aici clientul alege file-urile care vrea sa le transmita
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numarul de file-uri care vreti sa introduceti:");
        int numFiles = scanner.nextInt();
        File[] files = new File[numFiles];
        for (int i = 0; i < numFiles; i++) {
            System.out.println("Introduceti numele file-ului " + (i+1) + ":");
            String fileName = scanner.next();
            files[i] = new File(fileName);
        }

        // Transmite file-urile serverului si inscrie in host name file
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
    }
}
