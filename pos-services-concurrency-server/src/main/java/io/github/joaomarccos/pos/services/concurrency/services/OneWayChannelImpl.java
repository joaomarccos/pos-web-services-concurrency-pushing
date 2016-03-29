package io.github.joaomarccos.pos.services.concurrency.services;

import io.github.joaomarccos.pos.services.concurrency.produtor.Produtor;
import javax.jws.Oneway;
import javax.jws.WebService;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
@WebService(endpointInterface = "io.github.joaomarccos.pos.services.concurrency.services.OneWayChannel", 
        serviceName = "OneWayChannel", targetNamespace = "http://joaomarccos.github.io/", portName = "OneWayChannelPort")
public class OneWayChannelImpl implements OneWayChannel {

    private final Produtor produtor;

    public OneWayChannelImpl(Produtor produtor) {
        this.produtor = produtor;
    }

    @Override
    @Oneway
    public void process(String hash) {
        produtor.addTask(hash);
    }

}
