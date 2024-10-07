package sistemas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class VisaPaymentServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8900);
        System.out.println("Visa Payment Server is running...");

        while (true) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                String request = input.readLine();
                System.out.println("Received request: " + request);

                if (request.equals("PAY")) {
                    output.println("OK");

                    // Process card number
                    String cardNumber = input.readLine();
                    if (cardNumber.length() != 16 || !cardNumber.startsWith("4")) {
                        output.println("ERROR 3001"); // Número de cartão inválido
                        continue;
                    } else {
                        output.println("OK");
                    }

                    // Process card type (Visa)
                    if (!cardNumber.startsWith("4")) {
                        output.println("ERROR 3002"); // Cartão não é da bandeira Visa
                        continue;
                    }

                    // Process name
                    String name = input.readLine();
                    if (!name.matches("^[a-zA-Z ]+$")) {
                        output.println("ERROR 3003"); // Nome inválido
                        continue;
                    } else {
                        output.println("OK");
                    }

                    // Process expiration date
                    String expirationDate = input.readLine();
                    if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{4}")) {
                        output.println("ERROR 3004"); // Data de expiração inválida
                        continue;
                    } else {
                        output.println("OK");
                    }

                    // Process transaction value
                    String value = input.readLine();
                    try {
                        Double.parseDouble(value);
                        output.println("OK");
                    } catch (NumberFormatException e) {
                        output.println("ERROR 3005"); // Valor da transação inválido
                        continue;
                    }

                    // Commit transaction
                    String commit = input.readLine();
                    if (commit.equals("COMMIT")) {
                        String transactionData = generateTransactionData(cardNumber, name, expirationDate, value);
                        output.println("OK");
                        output.println(transactionData);
                    } else {
                        output.println("ERROR 3000"); // Comando inválido
                    }
                } else {
                    output.println("ERROR 3000"); // Comando inválido
                }
            } catch (IOException e) {
                System.out.println("ERROR 3006"); // Sistema indisponível
            }
        }
    }

    private static String generateTransactionData(String cardNumber, String name, String expirationDate, String value) {
        String transactionId = java.util.UUID.randomUUID().toString();
        String lastSixDigits = cardNumber.substring(cardNumber.length() - 6);
        return transactionId + ":" + name + ":" + lastSixDigits + ":" + expirationDate + ":" + value;
    }
}
