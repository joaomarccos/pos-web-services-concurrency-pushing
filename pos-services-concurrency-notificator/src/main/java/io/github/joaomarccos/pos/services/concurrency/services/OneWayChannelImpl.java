package io.github.joaomarccos.pos.services.concurrency.services;

import javax.jws.WebService;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
@WebService(endpointInterface = "io.github.joaomarccos.pos.services.concurrency.services.OneWayChannelNotificator",
        serviceName = "OneWayChannelNotificator", targetNamespace = "http://joaomarccos.github.io/", portName = "OneWayChannelNotificatorPort")
public class OneWayChannelImpl implements OneWayChannelNotificator {

    private final Notificator notificator;

    public OneWayChannelImpl(Notificator notificator) {
        this.notificator = notificator;
    }

    @Override
    public void notify(String id) {
        notificator.getStatusOfResponses().put(id, Boolean.TRUE);
    }

}
