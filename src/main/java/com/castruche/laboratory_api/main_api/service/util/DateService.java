package com.castruche.laboratory_api.main_api.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateService {

    private static final Logger logger = LogManager.getLogger(DateService.class);

    public LocalDateTime getStartOfDay(LocalDateTime date) {
        if(date == null) {
            return null;
        }
        return date.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
