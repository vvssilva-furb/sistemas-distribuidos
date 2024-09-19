import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    // javac -target 1.8 -source 1.8 .\HelloService.java HelloServiceImpl.java RMIServer.java RMIClient.java
    // java RMIServer
    // java RMIClient

    public static void main(String[] args) {
        try {
            HelloService helloService = new HelloServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099); // RMI registry on port 1099
            registry.rebind("HelloService", helloService); // Bind the service
            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
