package io.tazabreu.springrestapi.persistence;

import io.tazabreu.springrestapi.domain.enumerable.Race;
import io.tazabreu.springrestapi.persistence.entity.Hero;
import io.tazabreu.springrestapi.persistence.repository.HeroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    @Profile("in-memory")
    CommandLineRunner initDatabase(HeroRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Hero("Samwise Gamgee", "burglar", Race.HOBBIT)));
            log.info("Preloading " + repository.save(new Hero("Frodo Baggins", "thief", Race.HOBBIT)));
            log.info("Preloading " + repository.save(new Hero("Aragorn II Elessar Telcontar", "swordsman", Race.HUMAN)));
            log.info("Preloading " + repository.save(new Hero("Legolas", "archer", Race.ELF)));
            log.info("Preloading " + repository.save(new Hero("Gimli", "axeman", Race.DWARF)));
        };
    }
}