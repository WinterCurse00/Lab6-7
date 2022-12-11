import java.io.*;
import java.net.*;

public class Server {

    class ConcatThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }
    public static void main(String[] args) {

        try{

            ServerSocket listen = new ServerSocket(9999);

            while(true){
                System.out.println("Server waiting for connection");
                Socket socket = listen.accept();

                BufferedReader from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter to_client = new PrintWriter(socket.getOutputStream());

                File existing_file = new File(listen.getInetAddress().getHostName());
                String filename_from_client = from_client.readLine();

                PrintWriter exisWriter = new PrintWriter(existing_file);


                File inputFile = new File(filename_from_client);
                if (!inputFile.exists()) {
                    to_client.println("cannot open " + filename_from_client);
                    to_client.close();
                    from_client.close();
                    socket.close();
                }



                System.out.println("Reading from file" + filename_from_client);
                BufferedReader input = new BufferedReader(new FileReader(inputFile));
                String line;



                while ((line = input.readLine()) != null)
                    exisWriter.println(line);


                to_client.close();
                from_client.close();
                socket.close();

            }

        } catch (Exception e){
            System.err.println(e);
        }
    }
}
