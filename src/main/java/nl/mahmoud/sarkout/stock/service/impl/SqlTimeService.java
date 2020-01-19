package nl.mahmoud.sarkout.stock.service.impl;

import nl.mahmoud.sarkout.stock.service.TimeService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class SqlTimeService implements TimeService {

    @Override
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    public LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }
}
