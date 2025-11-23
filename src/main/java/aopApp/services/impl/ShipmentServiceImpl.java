package aopApp.services.impl;

import aopApp.aspects.MyAnnotation;
import aopApp.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Override
    @MyAnnotation
    public String orderPackage(Long orderId) {
        try {
            log.info("Processing the order...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Error occurred while processing the order", e);
        }
        return "Order has been processed successfully, orderId: "+orderId;
    }

    @Override
    public String trackPackage(Long orderId) {
        try {
            log.info("Tracking the order...");
            Thread.sleep(500);
            throw new RuntimeException("Exception occurred during trackPackage");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
