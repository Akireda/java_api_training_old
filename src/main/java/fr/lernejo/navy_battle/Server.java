package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final String url;
    private final Client clt;

    public Server(int port, String url)
    {
        // server + url
        this.clt = null;
        this.port = port;
        this.url = url;
    }

    public Server(int port, String url,String servUrl)
    {
        // server + url + servurl
        this.port = port;
        this.clt = new Client(port,servUrl);
        this.url = url;
    }

    public Client getClt() {
        // Get clt
        return clt;
    }

    public boolean init() throws IOException {
        // Init serv
        HttpServer server = HttpServer.create(new InetSocketAddress(this.url, this.port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new PingHttpHandler());
        server.createContext("/api/game/start", new StartHttpHandler());
        server.createContext("/api/game/fire", new FireHttpHandler(this));
        server.start();
        System.out.println("Server started at port : " + this.port);
        return true;
    }

}
