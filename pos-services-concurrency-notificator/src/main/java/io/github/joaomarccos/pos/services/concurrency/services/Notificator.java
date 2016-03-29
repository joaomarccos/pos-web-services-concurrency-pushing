package io.github.joaomarccos.pos.services.concurrency.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Notificator implements Serializable {

    private final Map<String, Boolean> statusOfResponses;

    public Notificator() {
        this.statusOfResponses = new HashMap<>();
    }

    public Map<String, Boolean> getStatusOfResponses() {
        return statusOfResponses;
    }

}
