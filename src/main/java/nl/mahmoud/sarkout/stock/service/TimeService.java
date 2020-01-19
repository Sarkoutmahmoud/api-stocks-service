package nl.mahmoud.sarkout.stock.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface TimeService {

    Timestamp getCurrentTimestamp();

    LocalDateTime getCurrentLocalDateTime();
}
