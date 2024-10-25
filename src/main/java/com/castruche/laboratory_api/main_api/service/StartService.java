package com.castruche.laboratory_api.main_api.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class StartService implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(StartService.class);

    public StartService() {}
    @Override
    public void run(String... args) {
    }
}
