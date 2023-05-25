package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final String url;
    private final Client clt;

    public Server(int port, String url) {
        this.port = port;
        this.url = url;
        this.clt = null;
    }

    public Server(int port, String url, String servUrl) {
        this.port = port;
        this.url = url;
        this.clt = new Client(port, servUrl);
    }

    public Client getClt() {
        return clt;
    }

    public boolean init() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(url, port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new PingHttpHandler());
        server.createContext("/api/game/start", new StartHttpHandler());
        server.createContext("/api/game/fire", new FireHttpHandler(this));
        server.start();
        System.out.println("Server started at port : " + port);
        return true;
    }
}
