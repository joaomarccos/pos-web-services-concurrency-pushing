package io.github.joaomarccos.pos.services.concurrency.cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public interface Consumer extends Remote {

    public void notifty() throws RemoteException;

}
