package io.github.joaomarccos.pos.services.concurrency.cliente;

import io.github.joaomarccos.pos.services.concurrency.services.ProdutorServiceCanal1;
import io.github.joaomarccos.pos.services.concurrency.services.ProdutorServiceCanal2;
import io.github.joaomarccos.pos.services.concurrency.services.ProdutorServiceCanal3;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Client implements Consumer, Serializable {

    private static URL wsdl;
    private static QName qname;
    private static Service ws;
    private String hash;
    private boolean obteveResposta;

    public Client() {
        this.obteveResposta = false;
    }  

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws MalformedURLException, InterruptedException, RemoteException {
        Client c = new Client();
        ProdutorServiceCanal1 canal1 = c.getChanel1();
        ProdutorServiceCanal2 canal2 = c.getChanel2();
        ProdutorServiceCanal3 canal3 = c.getChanel3();

        String request = canal1.request("id");
        System.out.println(request);
        c.setHash(request.split(":")[1]);

        /*
         Disponibilizando cliente via RMI
         */
        Registry registry = LocateRegistry.createRegistry(10998);
        registry.rebind(c.getHash(), c);

        /*
         Solicita processamento da requisição
         */
        canal2.process(c.getHash());
    }

    private ProdutorServiceCanal3 getChanel3() throws MalformedURLException {
        wsdl = new URL("http://127.0.0.1:9878/concurService?wsdl");
        qname = new QName("http://services.concurrency.services.pos.joaomarccos.github.io/", "ProdutorServiceCanal3ImplService");
        ws = Service.create(wsdl, qname);
        ProdutorServiceCanal3 canal3 = ws.getPort(ProdutorServiceCanal3.class);
        return canal3;
    }

    private ProdutorServiceCanal2 getChanel2() throws MalformedURLException {
        wsdl = new URL("http://127.0.0.1:9877/concurService?wsdl");
        qname = new QName("http://services.concurrency.services.pos.joaomarccos.github.io/", "ProdutorServiceCanal2ImplService");
        ws = Service.create(wsdl, qname);
        ProdutorServiceCanal2 canal2 = ws.getPort(ProdutorServiceCanal2.class);
        return canal2;
    }

    private ProdutorServiceCanal1 getChanel1() throws MalformedURLException {
        wsdl = new URL("http://127.0.0.1:9876/concurService?wsdl");
        qname = new QName("http://services.concurrency.services.pos.joaomarccos.github.io/", "ProdutorServiceCanal1ImplService");
        ws = Service.create(wsdl, qname);
        ProdutorServiceCanal1 canal1 = ws.getPort(ProdutorServiceCanal1.class);
        return canal1;
    }

    /**
     * Chamado pelo Produtor ao finalizar processamento da tarefa
     */
    @Override
    public void notifty() {
        try {
            this.obteveResposta = true;
            String r = getChanel3().response(hash);
            System.out.println(this+ " Conteúdo da resposta: "+r);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return "Client{" + "hash=" + hash + ", obteveResposta=" + obteveResposta + '}';
    }

}
