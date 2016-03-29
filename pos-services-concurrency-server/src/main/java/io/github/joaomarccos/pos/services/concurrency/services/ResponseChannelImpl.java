package io.github.joaomarccos.pos.services.concurrency.services;

import io.github.joaomarccos.pos.services.concurrency.produtor.Produtor;
import javax.jws.WebService;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
@WebService(endpointInterface = "io.github.joaomarccos.pos.services.concurrency.services.ResponseChannel", 
        serviceName = "ResponseChannel", targetNamespace = "http://joaomarccos.github.io/", portName = "ResponseChannelPort")
public class ResponseChannelImpl implements ResponseChannel {

    private final Produtor produtor;

    public ResponseChannelImpl(Produtor produtor) {
        this.produtor = produtor;
    }

    @Override
    public String response(String hash) {
        return produtor.getResponse(hash);
    }

}
