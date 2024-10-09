import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {
    public static void main(String[] args) {
        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Look up the remote object
            CalculoDeTaxaDeComissao stub = (CalculoDeTaxaDeComissao) registry.lookup("CalculoDeTaxaDeComissao");
            
            // Call the remote method
            float valor = 100.0f;
            float resultado = stub.calcularTaxaDeComissao(valor);
            System.out.println("Taxa de comissão para o valor " + valor + " é: " + resultado);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
