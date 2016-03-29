package io.github.joaomarccos.pos.services.concurrency.services;

import javax.xml.ws.Endpoint;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Loader {

    public static void main(String[] args) {
        Notificator n = new Notificator();
        Endpoint.publish("http://127.0.0.1:9876/pushingChannel", new PushingChannelImpl(n));
        System.out.println("PushingChannel Ativo!");
        Endpoint.publish("http://127.0.0.1:9875/oneWayChannel", new OneWayChannelImpl(n));
        System.out.println("OneWayChannel Ativo!");

    }
}
