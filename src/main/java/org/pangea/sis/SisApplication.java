package org.pangea.sis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Student Information System (SIS) application.
 * <p>
 * This class boots the Spring application using Spring Boot's auto-configuration.
 */
@SpringBootApplication
public class SisApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SisApplication.class, args); // Launches the application
    }

}