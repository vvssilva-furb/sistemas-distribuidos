import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculoDeTaxaDeComissao extends Remote {
    float calcularTaxaDeComissao(float valor) throws RemoteException;
}
