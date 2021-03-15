package io.tazabreu.springrestapi.web.controller;

import io.tazabreu.springrestapi.domain.enumerable.QuestStatus;
import io.tazabreu.springrestapi.domain.exception.RestResourceNotFoundException;
import io.tazabreu.springrestapi.persistence.entity.Hero;
import io.tazabreu.springrestapi.service.HeroService;
import io.tazabreu.springrestapi.web.hateoas.HeroModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public
class HeroController {

    private final HeroService heroService;
    private final HeroModelAssembler heroModelAssembler;

    HeroController(HeroService heroService, HeroModelAssembler heroModerAssembler) {
        this.heroService = heroService;
        this.heroModelAssembler = heroModerAssembler;
    }

    @GetMapping("/heroes")
    public CollectionModel<EntityModel<Hero>> findAll() {
        List<EntityModel<Hero>> heroList = heroService.findAll()
                .stream().map(heroModelAssembler::toModel).collect(Collectors.toList());
        return new CollectionModel<>(heroList,
                linkTo(methodOn(HeroController.class).findAll()).withSelfRel());
    }

    @PostMapping("/heroes")
    public EntityModel<Hero> saveHero(@RequestBody Hero hero) {
        return heroModelAssembler.toModel(heroService.save(hero));
    }

    @GetMapping("/heroes/{id}")
    public EntityModel<Hero> findOne(@PathVariable Long id) {
        Hero hero = heroService.findById(id)
                .orElseThrow(() -> new RestResourceNotFoundException(Hero.class, id));
        return heroModelAssembler.toModel(hero);
    }

    @PutMapping("/heroes/{id}")
    public EntityModel<Hero> replaceHero(@RequestBody Hero newHero, @PathVariable Long id) {
        return heroModelAssembler.toModel(heroService.replaceHero(newHero, id));
    }

    @DeleteMapping("/heroes/{id}")
    public ResponseEntity<Hero> deleteHero(@PathVariable Long id) {
        heroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/heroes/{id}/assign-quest")
    public EntityModel<Hero> assignQuest(@PathVariable Long id) {
        return heroModelAssembler.toModel(heroService.updateQuestStatus(id, QuestStatus.IN_PROGRESS));
    }

    @PutMapping("/heroes/{id}/complete-quest")
    public EntityModel<Hero> completeQuest(@PathVariable Long id) {
        return heroModelAssembler.toModel(heroService.updateQuestStatus(id, QuestStatus.COMPLETED));
    }

    @PutMapping("/heroes/{id}/cancel-quest")
    public EntityModel<Hero> cancelQuest(@PathVariable Long id) {
        return heroModelAssembler.toModel(heroService.updateQuestStatus(id, QuestStatus.NOT_ASSIGNED));
    }

}