package io.github.joaomarccos.pos.services.concurrency.services;

import javax.jws.WebService;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
@WebService(endpointInterface = "io.github.joaomarccos.pos.services.concurrency.services.PushingChannel", 
        serviceName = "PushingChannel", targetNamespace = "http://joaomarccos.github.io/", portName = "PushingChannelPort")
public class PushingChannelImpl implements PushingChannel {

    private final Notificator notificator;

    public PushingChannelImpl(Notificator notificator) {
        this.notificator = notificator;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public String watingForResponse(String id) {
        notificator.getStatusOfResponses().put(id, Boolean.FALSE);
        while (!notificator.getStatusOfResponses().get(id));
        return "finish";
    }

}
