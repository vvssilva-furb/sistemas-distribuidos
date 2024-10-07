package sistemas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MastercardServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8901);
        System.out.println("Mastercard Payment Server is running...");

        while (true) {
            try (Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                String request = input.readLine();
                System.out.println("Received request: " + request);

                if (request.equals("PAYMENT")) {
                    output.println("OK");

                    // Process card number
                    String cardNumber = input.readLine();
                    if (cardNumber.length() != 16 || !isValidMastercard(cardNumber)) {
                        output.println("ERROR 4001"); // Número de cartão de crédito inválido
                        continue;
                    } else {
                        output.println("PROCEED");
                    }

                    // Process card type (Mastercard validation)
                    if (!isValidMastercard(cardNumber)) {
                        output.println("ERROR 4002"); // Cartão de crédito não é da bandeira Visa
                        continue;
                    }

                    // Process name
                    String name = input.readLine();
                    if (!name.matches("^[a-zA-Z ]+$")) {
                        output.println("ERROR 4003"); // Nome da pessoa inválido
                        continue;
                    } else {
                        output.println("PROCEED");
                    }

                    // Process expiration date
                    String expirationDate = input.readLine();
                    if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{4}")) {
                        output.println("ERROR 4004"); // Data de expiração do cartão inválida
                        continue;
                    } else {
                        output.println("PROCEED");
                    }

                    // Process transaction value
                    String value = input.readLine();
                    try {
                        Double.parseDouble(value);
                        output.println("PROCEED");
                    } catch (NumberFormatException e) {
                        output.println("ERROR 4005"); // Valor da transação inválido
                        continue;
                    }

                    // Commit transaction
                    String commit = input.readLine();
                    if (commit.equals("COMMIT")) {
                        String transactionData = generateTransactionData(cardNumber, name, expirationDate, value);
                        output.println("OK (1ª linha)");
                        output.println(transactionData);
                    } else {
                        output.println("ERROR 4000"); // Comando inválido
                    }
                } else {
                    output.println("ERROR 4000"); // Comando inválido
                }
            } catch (IOException e) {
                System.out.println("ERROR 4006"); // Sistema indisponível
            }
        }
    }

    private static boolean isValidMastercard(String cardNumber) {
        // Mastercard numbers start with 51-55
        return cardNumber.startsWith("51") || cardNumber.startsWith("52") ||
                cardNumber.startsWith("53") || cardNumber.startsWith("54") ||
                cardNumber.startsWith("55");
    }

    private static String generateTransactionData(String cardNumber, String name, String expirationDate, String value) {
        String transactionId = java.util.UUID.randomUUID().toString();
        String lastSixDigits = cardNumber.substring(cardNumber.length() - 6);
        return transactionId + ":" + name + ":" + lastSixDigits + ":" + expirationDate + ":" + value;
    }
}
