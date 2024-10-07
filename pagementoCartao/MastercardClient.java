package sistemas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MastercardClient {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // localhost
        int port = 8901;

        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Step 1: Payment request
            out.println("PAYMENT");
            System.out.println("Server: " + in.readLine());

            // Step 2: Send card parts (4 parts)
            out.println("5348"); // Part 1 of card number
            System.out.println("Server: " + in.readLine());

            out.println("3453"); // Part 2 of card number
            System.out.println("Server: " + in.readLine());

            out.println("5489"); // Part 3 of card number
            System.out.println("Server: " + in.readLine());

            out.println("3654"); // Part 4 of card number
            System.out.println("Server: " + in.readLine());

            // Step 3: Send cardholder name
            out.println("ROQUE BEZERRA");
            System.out.println("Server: " + in.readLine());

            // Step 4: Send card expiration date
            out.println("12/2025");
            System.out.println("Server: " + in.readLine());

            // Step 5: Send transaction amount
            out.println("252.43");
            System.out.println("Server: " + in.readLine());

            // Step 6: Commit transaction
            out.println("COMMIT");
            System.out.println("Server: " + in.readLine());
            System.out.println("Transaction Data: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

