package io.tazco.javaaxon.aggregates;

import io.tazco.javaaxon.commands.EndDeliverySessionCommand;
import io.tazco.javaaxon.commands.StartDeliverySessionCommand;
import io.tazco.javaaxon.events.models.DeliverySessionEndedEvent;
import io.tazco.javaaxon.events.models.DeliverySessionStartedEvent;
import io.tazco.javaaxon.exceptions.InvalidCommandException;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

public class DeliverySessionAggregateTest {

    private AggregateTestFixture<DeliverySessionAggregate> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(DeliverySessionAggregate.class);
    }

    @Test
    public void whenHandlingStartDeliverySessionCommand_thenDeliverySessionStartedEventIsProduced() {
        String deliverySessionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        fixture.givenNoPriorActivity()
                .when(new StartDeliverySessionCommand(deliverySessionId, now))
                .expectEvents(new DeliverySessionStartedEvent(deliverySessionId, now));
    }

    @Test
    public void whenHandlingEndDeliverySessionCommand_thenDeliverySessionEndedEventIsProduced() {
        String deliverySessionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        fixture.given(new DeliverySessionStartedEvent(deliverySessionId, now))
                .when(new EndDeliverySessionCommand(deliverySessionId, now))
                .expectEvents(new DeliverySessionEndedEvent(deliverySessionId, now));
    }

    @Test
    public void whenHandlingEndDeliverySessionCommand_thenExceptionIsThrownIfDeliverySessionWasNotStarted() {
        String deliverySessionId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        fixture.givenNoPriorActivity()
                .when(new EndDeliverySessionCommand(deliverySessionId, now))
                .expectException(AggregateNotFoundException.class);
    }

    @Test
    public void whenHandlingEndDeliverySessionCommand_thenExceptionIsThrownIfDeliverySessionIsAlreadyEnded() {
        String deliverySessionId = UUID.randomUUID().toString();
        LocalDateTime then = LocalDateTime.now().minus(Duration.of(10, MINUTES));
        LocalDateTime now = LocalDateTime.now();
        fixture.given(new DeliverySessionStartedEvent(deliverySessionId, then),
                new DeliverySessionEndedEvent(deliverySessionId, now))
                .when(new EndDeliverySessionCommand(deliverySessionId, now))
                .expectException(InvalidCommandException.class);
    }

    // FIXME: business rule related tests

}
