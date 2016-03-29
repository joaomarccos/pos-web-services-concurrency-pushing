package io.github.joaomarccos.pos.services.concurrency.services;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Client implements Serializable {

    public static void main(String[] args) {
        try {
            /*
             Gera id unico para o cliente;
             */
            String clientId = UUID.randomUUID().toString();

            /*
             Requisita processamento
             */
            getOneWayChannel().process(clientId);

            Runnable r = () -> {
                try {
                    /*
                     Registra-se no notificador e aguarda a notificação
                     */
                    System.out.println(getPushingChannel().watingForResponse(clientId));

                    /*
                     Obtém a resposta
                     */
                    getResponse(clientId);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            new Thread(r).start();

            System.out.println("Wainting...");

        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static OneWayChannel getOneWayChannel() throws MalformedURLException {
        URL wsdl = new URL("http://127.0.0.1:9877/oneWayChannel?wsdl");
        QName qname = new QName("http://joaomarccos.github.io/", "OneWayChannel");
        Service ws = Service.create(wsdl, qname);
        OneWayChannel port = ws.getPort(OneWayChannel.class);
        return port;
    }

    public static PushingChannel getPushingChannel() throws MalformedURLException {
        URL wsdl = new URL("http://127.0.0.1:9876/pushingChannel?wsdl");
        QName qname = new QName("http://joaomarccos.github.io/", "PushingChannel");
        Service ws = Service.create(wsdl, qname);
        PushingChannel port = ws.getPort(PushingChannel.class);
        return port;
    }

    private static ResponseChannel getResponseChannel() throws MalformedURLException {
        URL wsdl = new URL("http://127.0.0.1:9878/responseChannel?wsdl");
        QName qname = new QName("http://joaomarccos.github.io/", "ResponseChannel");
        Service ws = Service.create(wsdl, qname);
        ResponseChannel port = ws.getPort(ResponseChannel.class);
        return port;
    }

    public static void getResponse(String id) {
        try {
            System.out.println(getResponseChannel().response(id));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
