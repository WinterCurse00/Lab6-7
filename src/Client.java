//Se cere elaborarea unei perechi de programe client-server cu următoarea funcţionalitate:
//        -serverul creează la prima conectare a unui client un fişier text vid cu numele acestuia (clientului);
//        -clientul poate trimite serverului unul sau mai multe fişiere şi cere interclasarea acestora cu fişierul care-i poartă numele;
//        rezultatul interclasării va fi reţinut în fişierul cu numele clientului;
//        -clientul poate cere serverului returnarea conţinutului fişierului ce-i poartă numele OBS.
//        Se cere scrierea unui server concurent folosind thread-uri. Interclasarea fişierelor se va face folosind thread-uri.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        try {

            String hostname = InetAddress.getLocalHost().getHostName();

//            System.out.println("Introduceti numarul de fisiere care il doriti sal transmiteti");
//            int nr = scanner.nextInt();
            System.out.println("Introduceti filename");
            String filename = scanner.nextLine();

            Socket socket = new Socket(hostname, 9999);

            BufferedReader from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter to_server = new PrintWriter(socket.getOutputStream());

            to_server.println(hostname);
            to_server.flush();

            to_server.println(filename);
            to_server.flush();

            String line;

            while (((line = from_server.readLine()) != null)) {
                System.out.println(line);
            }
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
