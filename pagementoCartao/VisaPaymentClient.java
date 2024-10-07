package sistemas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class VisaPaymentClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 8900;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            output.println("PAY");
            System.out.println("Server: " + input.readLine());

            output.println("4234567890123456");
            System.out.println("Server: " + input.readLine());

            output.println("ROQUE BEZERRA");
            System.out.println("Server: " + input.readLine());

            output.println("12/2025");
            System.out.println("Server: " + input.readLine());

            output.println("252.43");
            System.out.println("Server: " + input.readLine());

            output.println("COMMIT");
            System.out.println("Server: " + input.readLine());
            System.out.println("Transaction Data: " + input.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
