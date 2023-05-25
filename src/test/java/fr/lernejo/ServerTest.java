package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class ServerTest {

    @Test
    void getClt() throws IOException {
        Server server = new Server(8765, "localhost","http://localhost:9875");
        server.init();
        Assertions.assertNotNull(server.getClt());
    }

    @Test
    void init() throws IOException {
        Server server = new Server(9875, "localhost");
        boolean res = server.init();
        Assertions.assertTrue(res);
    }
}
