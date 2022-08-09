package me.nanqic.raffle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class RaffleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaffleApplication.class, args);
    }
}
