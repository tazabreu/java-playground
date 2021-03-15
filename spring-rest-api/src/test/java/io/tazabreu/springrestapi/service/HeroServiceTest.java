package io.tazabreu.springrestapi.service;

import io.tazabreu.springrestapi.domain.enumerable.QuestStatus;
import io.tazabreu.springrestapi.domain.enumerable.Race;
import io.tazabreu.springrestapi.domain.exception.InvalidTransitionException;
import io.tazabreu.springrestapi.domain.exception.RestResourceNotFoundException;
import io.tazabreu.springrestapi.persistence.entity.Hero;
import io.tazabreu.springrestapi.persistence.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @Mock
    HeroRepository heroRepository;

    @InjectMocks
    HeroService heroService;

    @Test
    public void shouldSaveHero(@Mock Hero hero) {
        heroService.save(hero);
        then(heroRepository).should().save(hero);
    }

    @Test
    public void shouldFindAll() {
        heroService.findAll();
        then(heroRepository).should().findAll();
    }

    @Test
    public void shouldFindById() {
        Long someId = 1L;
        heroService.findById(someId);
        then(heroRepository).should().findById(someId);
    }

    @Test
    public void shouldReplaceHeroWhenIdIsValid(@Mock Hero newHero, @Mock Hero heroToReplace) {
        Long someId = 1L;
        String someName = "some-name";
        Race someRace = Race.DWARF;
        String someRole = "some-role";

        given(heroRepository.findById(someId)).willReturn(Optional.of(heroToReplace));
        given(newHero.getName()).willReturn(someName);
        given(newHero.getFirstName()).willReturn(someName);
        given(newHero.getLastName()).willReturn(someName);
        given(newHero.getRole()).willReturn(someRole);
        given(newHero.getRace()).willReturn(someRace);
        given(heroService.save(heroToReplace)).willReturn(heroToReplace);
        heroService.replaceHero(newHero, someId);
        then(heroToReplace).should().setName(newHero.getName());
        then(heroToReplace).should(never()).setFirstName(anyString());
        then(heroToReplace).should(never()).setLastName(anyString());
        then(heroToReplace).should().setName(newHero.getName());
        then(heroToReplace).should().setRole(newHero.getRole());
        then(heroToReplace).should().setRace(newHero.getRace());
        then(heroRepository).should().save(heroToReplace);

        reset(heroToReplace);
        reset(heroRepository);
        given(newHero.getName()).willReturn(null);
        given(heroRepository.findById(someId)).willReturn(Optional.of(heroToReplace));
        given(heroService.save(heroToReplace)).willReturn(heroToReplace);
        heroService.replaceHero(newHero, someId);
        then(heroToReplace).should(never()).setName(anyString());
        then(heroToReplace).should().setFirstName(newHero.getFirstName());
        then(heroToReplace).should().setLastName(newHero.getLastName());
        then(heroToReplace).should().setRole(newHero.getRole());
        then(heroToReplace).should().setRace(newHero.getRace());
        then(heroRepository).should().save(heroToReplace);
    }

    @Test
    public void shouldNotReplaceHeroByThrowingExceptionWhenIdIsNotValid() {
        Long someId = 1L;
        given(heroRepository.findById(someId)).willReturn(Optional.empty());
        RestResourceNotFoundException thrownException = assertThrows(
                RestResourceNotFoundException.class,
                () -> heroService.replaceHero(any(), someId),
                "Expected updateQuestStatus() to throw, but it didn't"
        );
        assertThat(thrownException.getMessage(), is(String.format("Could not find Rest Resource [%s] with id [%s]", Hero.class.getName(), someId)));
    }

    @Test
    public void shouldDeleteById() {
        Long someId = 1L;
        heroService.deleteById(someId);
        then(heroRepository).should().deleteById(someId);
    }

    @Test
    public void shouldUpdateQuestStatusWhenTransitionIsValid(@Mock Hero hero) {
        QuestStatus someInitialQuestStatus = QuestStatus.NOT_ASSIGNED;
        QuestStatus validQuestStatus = QuestStatus.IN_PROGRESS;
        given(hero.getQuestStatus()).willReturn(someInitialQuestStatus);
        given(hero.getId()).willReturn(10L);
        given(heroRepository.findById(hero.getId())).willReturn(Optional.of(hero));
        given(heroRepository.save(hero)).willReturn(hero);
        heroService.updateQuestStatus(hero.getId(), validQuestStatus);
        then(heroRepository).should().findById(hero.getId());
        then(hero).should().setQuestStatus(validQuestStatus);
        then(heroRepository).should().save(hero);
    }


    @Test
    public void shouldNotUpdateQuestStatusWhenTransitionIsInvalid(@Mock Hero hero) {
        QuestStatus someInitialQuestStatus = QuestStatus.NOT_ASSIGNED;
        QuestStatus invalidQuestStatus = QuestStatus.COMPLETED;
        given(hero.getQuestStatus()).willReturn(someInitialQuestStatus);
        given(hero.getId()).willReturn(10L);
        given(heroRepository.findById(hero.getId())).willReturn(Optional.of(hero));
        InvalidTransitionException thrownException = assertThrows(
                InvalidTransitionException.class,
                () -> heroService.updateQuestStatus(hero.getId(), invalidQuestStatus),
                "Expected updateQuestStatus() to throw, but it didn't"
        );
        assertThat(thrownException.getMessage(), is(String.format("It is not possible to transition from [%s] to [%s]. Reference is [%s]",
                someInitialQuestStatus, invalidQuestStatus, hero)));
        then(heroRepository).should().findById(hero.getId());
        then(heroRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void shouldNotUpdateQuestStatusWhenResourceWithSuchIdIsNotFound(@Mock Hero hero) {
        given(hero.getId()).willReturn(10L);
        given(heroRepository.findById(hero.getId())).willReturn(Optional.empty());
        RestResourceNotFoundException thrownException = assertThrows(
                RestResourceNotFoundException.class,
                () -> heroService.updateQuestStatus(hero.getId(), any()),
                "Expected updateQuestStatus() to throw, but it didn't"
        );
        assertThat(thrownException.getMessage(), is(String.format("Could not find Rest Resource [%s] with id [%s]", Hero.class.getName(), hero.getId())));
    }

}