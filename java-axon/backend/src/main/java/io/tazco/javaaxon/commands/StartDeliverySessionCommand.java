package io.tazco.javaaxon.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.Objects;

public class StartDeliverySessionCommand {

    @TargetAggregateIdentifier
    private final String deliverySessionId;
    private final LocalDateTime startTime;

    public StartDeliverySessionCommand(String deliverySessionId, LocalDateTime startTime) {
        this.deliverySessionId = deliverySessionId;
        this.startTime = startTime;
    }

    public String getDeliverySessionId() {
        return deliverySessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StartDeliverySessionCommand)) return false;
        StartDeliverySessionCommand that = (StartDeliverySessionCommand) o;
        return deliverySessionId.equals(that.deliverySessionId) &&
                startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliverySessionId, startTime);
    }

}
