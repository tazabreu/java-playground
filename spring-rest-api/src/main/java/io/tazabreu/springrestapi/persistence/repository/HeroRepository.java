package io.tazabreu.springrestapi.persistence.repository;

import io.tazabreu.springrestapi.persistence.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

}
