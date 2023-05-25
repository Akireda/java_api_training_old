package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.io.IOException;


class LauncherTest {

    @Test
    void LauncherClient() throws IOException, InterruptedException {
        int serverPort = 10003;
        Launcher launcher1 = new Launcher();
        launcher1.main(new String[]{String.valueOf(serverPort)});
        Launcher launcher2 = new Launcher();
        launcher2.main(new String[]{String.valueOf(serverPort + 1), "http://localhost:" + serverPort});
    }

    @Test
    void LauncherServ() throws IOException, InterruptedException {
        int serverPort = 10000;
        Launcher launcher = new Launcher();
        launcher.main(new String[]{String.valueOf(serverPort)});
    }
}
