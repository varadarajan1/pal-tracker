package io.pivotal.pal.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PalTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }
//
//    @Bean
//    TimeEntryRepository timeEntryRepository(DataSource source) {
//        return new JdbcTimeEntryRepository(source);
//    }
}
