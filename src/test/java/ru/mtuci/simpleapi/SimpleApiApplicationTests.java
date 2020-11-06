package ru.mtuci.simpleapi;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mtuci.simpleapi.controller.StatusController;
import ru.mtuci.simpleapi.dto.Status;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertSame;


@SpringBootTest
class SimpleApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @SneakyThrows
    @Test
    void checkStatusController() {
        StatusController controller = new StatusController();
        Status finalStatus = new Status(InetAddress.getLocalHost().getHostAddress());
        Status testStatus = controller.get();
        assertSame(finalStatus,testStatus);
    }

}
