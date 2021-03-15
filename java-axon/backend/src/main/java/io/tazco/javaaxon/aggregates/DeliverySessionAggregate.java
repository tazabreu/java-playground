package io.tazco.javaaxon.aggregates;

import io.tazco.javaaxon.commands.EndDeliverySessionCommand;
import io.tazco.javaaxon.commands.StartDeliverySessionCommand;
import io.tazco.javaaxon.events.models.DeliverySessionEndedEvent;
import io.tazco.javaaxon.events.models.DeliverySessionStartedEvent;
import io.tazco.javaaxon.exceptions.InvalidCommandException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;
import java.util.Objects;

@Aggregate
public class DeliverySessionAggregate {
    @AggregateIdentifier
    private String deliverySessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @CommandHandler
    public DeliverySessionAggregate(StartDeliverySessionCommand startDeliverySessionCommand) {
        LocalDateTime safeStartTime = Objects.nonNull(startDeliverySessionCommand.getStartTime()) ?
                startDeliverySessionCommand.getStartTime() : LocalDateTime.now();
        AggregateLifecycle.apply(new DeliverySessionStartedEvent(startDeliverySessionCommand.getDeliverySessionId(),
                safeStartTime));
    }

    @CommandHandler
    public void handle(EndDeliverySessionCommand endDeliverySessionCommand) throws InvalidCommandException {
        if (this.endTime != null) System.out.println(String.format("Unable to end delivery session %s",
                this.deliverySessionId));
        LocalDateTime safeEndTime = Objects.nonNull(endDeliverySessionCommand.getEndTime()) ?
                endDeliverySessionCommand.getEndTime() : LocalDateTime.now();
        AggregateLifecycle.apply(new DeliverySessionEndedEvent(endDeliverySessionCommand.getDeliverySessionId(),
                safeEndTime));
    }

    @EventSourcingHandler
    public void on(DeliverySessionStartedEvent deliverySessionStartedEvent) {
        this.deliverySessionId = deliverySessionStartedEvent.getDeliverySessionId();
        this.startTime = deliverySessionStartedEvent.getStartTime();
    }

    @EventSourcingHandler
    public void on(DeliverySessionEndedEvent deliverySessionEndedEvent) {
        this.deliverySessionId = deliverySessionEndedEvent.getDeliverySessionId();
        this.endTime = deliverySessionEndedEvent.getEndTime();
    }

    protected DeliverySessionAggregate() {}
}
