package io.github.joaomarccos.pos.services.concurrency.loader;

import io.github.joaomarccos.pos.services.concurrency.produtor.Produtor;
import io.github.joaomarccos.pos.services.concurrency.services.OneWayChannelImpl;
import io.github.joaomarccos.pos.services.concurrency.services.ResponseChannelImpl;
import javax.xml.ws.Endpoint;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Loader {
    public static void main(String[] args) {
        Produtor p = new Produtor();
        p.start();        
        Endpoint.publish("http://127.0.0.1:9877/oneWayChannel", new OneWayChannelImpl(p));
        System.out.println("ResonseChannel Ativo");
        Endpoint.publish("http://127.0.0.1:9878/responseChannel", new ResponseChannelImpl(p));
        System.out.println("oneWayChannel Ativo");
    }
}
