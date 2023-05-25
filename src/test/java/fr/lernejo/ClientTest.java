package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class ClientTest {

    @Test
    void startGameValid() throws IOException, InterruptedException {
        Server server = new Server(9898, "localhost");
        server.init();

        Server client1 = new Server(9897, "localhost", "http://localhost:9898");
        client1.init();
        int res1 = client1.getClt().startGame();
        Assertions.assertEquals(202, res1);

        Server client2 = new Server(9896, "localhost", "http://localhost:9898");
        client2.init();
        int res2 = client2.getClt().startGame();
        Assertions.assertEquals(202, res2);
    }
}
