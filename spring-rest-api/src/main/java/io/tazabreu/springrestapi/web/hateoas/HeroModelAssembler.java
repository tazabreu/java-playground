package io.tazabreu.springrestapi.web.hateoas;

import io.tazabreu.springrestapi.domain.enumerable.QuestStatus;
import io.tazabreu.springrestapi.persistence.entity.Hero;
import io.tazabreu.springrestapi.web.controller.HeroController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HeroModelAssembler implements RepresentationModelAssembler<Hero, EntityModel<Hero>> {

    @Override
    public EntityModel<Hero> toModel(Hero hero) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(HeroController.class).findOne(hero.getId())).withSelfRel());
        links.add(linkTo(methodOn(HeroController.class).findAll()).withRel("heroes"));

        if (QuestStatus.NOT_ASSIGNED.equals(hero.getQuestStatus())) {
            links.add(linkTo(methodOn(HeroController.class).assignQuest(hero.getId())).withRel("assign-quest"));
        } else if (QuestStatus.IN_PROGRESS.equals(hero.getQuestStatus())) {
            links.add(linkTo(methodOn(HeroController.class).completeQuest(hero.getId())).withRel("complete-quest"));
        }

        if (!QuestStatus.NOT_ASSIGNED.equals(hero.getQuestStatus())) {
            links.add(linkTo(methodOn(HeroController.class).cancelQuest(hero.getId())).withRel("cancel-quest"));
        }

        return new EntityModel<>(hero, links);
    }

}
