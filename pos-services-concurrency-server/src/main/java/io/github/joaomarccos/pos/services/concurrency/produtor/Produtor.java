package io.github.joaomarccos.pos.services.concurrency.produtor;

import io.github.joaomarccos.pos.services.concurrency.services.OneWayChannelNotificator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author João Marcos <joaomarccos.github.io>
 */
public class Produtor extends Thread {

    //Mapa com respostas processadas e disponíveis para consumo
    private final Map<String, Long> responses;
    //Pilha de execução
    private final Queue<Task> taskQueue;
    private OneWayChannelNotificator oneWayChannelNotificator;

    public Produtor() {
        this.responses = new HashMap<>();
        this.taskQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //Pausa de 10 a 30 segundos
                Thread.sleep(Math.round((Math.random() * 20000) + 10000));
            } catch (InterruptedException ex) {
                Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
            }

            Task currentTask = taskQueue.poll();
            if (currentTask != null) {
                try {
                    responses.put(currentTask.getHash(), System.currentTimeMillis() - currentTask.getinitialTime());

                    /*
                     Notifica cliente através do notificador
                     */
                    getPushingChannel().notify(currentTask.getHash());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void addTask(String hash) {
        this.taskQueue.add(new Task(hash, System.currentTimeMillis()));
    }

    public String getResponse(String hash) {
        if (responses.containsKey(hash)) {
            Long response = responses.get(hash);
            responses.remove(hash);
            return hash + ":" + response;
        }

        return "noresponse";
    }

    private class Task {

        private final String hash;
        private final Long initialTime;

        public Task(String hash, long data) {
            this.hash = hash;
            this.initialTime = data;
        }

        public String getHash() {
            return hash;
        }

        public long getinitialTime() {
            return initialTime;
        }

    }

    /**
     * Obtém conexão com o canal responsável por notificar os clientes
     *
     * @return
     * @throws MalformedURLException
     */
    private OneWayChannelNotificator getPushingChannel() throws MalformedURLException {
        if (oneWayChannelNotificator != null) {
            return oneWayChannelNotificator;
        }

        URL wsdl = new URL("http://127.0.0.1:9875/oneWayChannel?wsdl");
        QName qname = new QName("http://joaomarccos.github.io/", "OneWayChannelNotificator");
        Service ws = Service.create(wsdl, qname);
        oneWayChannelNotificator = ws.getPort(OneWayChannelNotificator.class);
        return oneWayChannelNotificator;
    }

}
