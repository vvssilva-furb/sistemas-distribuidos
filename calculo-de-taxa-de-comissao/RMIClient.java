import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Connect to RMI registry
            HelloService helloService = (HelloService) registry.lookup("HelloService"); // Lookup the remote object

            String response = helloService.sayHello("World"); // Call the remote method
            System.out.println(response); // Print the response
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
