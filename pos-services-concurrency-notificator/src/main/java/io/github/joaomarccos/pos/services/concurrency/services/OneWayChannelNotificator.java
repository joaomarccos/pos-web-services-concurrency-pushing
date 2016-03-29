package io.github.joaomarccos.pos.services.concurrency.services;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OneWayChannelNotificator {

    @WebMethod
    @Oneway
    public void notify(String id);
}
