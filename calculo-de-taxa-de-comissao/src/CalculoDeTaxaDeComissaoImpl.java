import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class CalculoDeTaxaDeComissaoImpl extends UnicastRemoteObject implements CalculoDeTaxaDeComissao {
    
    protected CalculoDeTaxaDeComissaoImpl() throws RemoteException {
        super();
    }

    @Override
    public float calcularTaxaDeComissao(float valor) throws RemoteException {
        float taxaDeComissao = valor * 0.10f;
        return taxaDeComissao;
    }
}
