package com.thomazllr.moovium.commons;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayReset implements CommandLineRunner {

    private final Flyway flyway;

    public FlywayReset(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) {
        flyway.clean();
        flyway.migrate();
    }

}
