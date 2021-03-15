package io.tazabreu.springrestapi.service;

import io.tazabreu.springrestapi.domain.enumerable.QuestStatus;
import io.tazabreu.springrestapi.domain.exception.InvalidTransitionException;
import io.tazabreu.springrestapi.domain.exception.RestResourceNotFoundException;
import io.tazabreu.springrestapi.persistence.entity.Hero;
import io.tazabreu.springrestapi.persistence.repository.HeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero save(Hero hero) {
        return heroRepository.save(hero);
    }

    public List<Hero> findAll() {
        return heroRepository.findAll();
    }

    public Optional<Hero> findById(Long id) {
        return heroRepository.findById(id);
    }

    public Hero replaceHero(Hero newHero, Long id) {
        return heroRepository.findById(id)
                .map(heroToReplace -> {
                    Optional.ofNullable(newHero.getName()).ifPresentOrElse(heroToReplace::setName,
                            () -> {
                                heroToReplace.setFirstName(newHero.getFirstName());
                                heroToReplace.setLastName(newHero.getLastName());
                            });
                    heroToReplace.setRace(newHero.getRace());
                    heroToReplace.setRole(newHero.getRole());
                    return heroRepository.save(heroToReplace);
                })
                .orElseThrow(() -> new RestResourceNotFoundException(Hero.class, id));
    }

    public void deleteById(Long id) {
        heroRepository.deleteById(id);
    }

    public Hero updateQuestStatus(Long id, QuestStatus nextStatus) {
        return heroRepository.findById(id)
                .map(hero -> {
                    if (QuestStatus.isTransitionValid(hero.getQuestStatus(), nextStatus)) {
                        hero.setQuestStatus(nextStatus);
                        return heroRepository.save(hero);
                    } else {
                        throw new InvalidTransitionException(hero, hero.getQuestStatus(), nextStatus);
                    }
                })
                .orElseThrow(() -> new RestResourceNotFoundException(Hero.class, id));
    }

}
