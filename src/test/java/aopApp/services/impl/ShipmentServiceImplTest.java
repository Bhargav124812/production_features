package aopApp.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class ShipmentServiceImplTest {

    @Autowired
    private  ShipmentServiceImpl shipmentService;

    @Test
    void orderPackage() {
        String orderString=shipmentService.orderPackage(-1L);
        log.info(orderString);
    }

    @Test
    void trackPackage() {

        shipmentService.trackPackage(4L);
    }
}