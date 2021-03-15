package io.tazco.javaaxon.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.Objects;

public class EndDeliverySessionCommand {

    @TargetAggregateIdentifier
    private final String deliverySessionId;
    private final LocalDateTime endTime;

    public EndDeliverySessionCommand(String deliverySessionId, LocalDateTime endTime) {
        this.deliverySessionId = deliverySessionId;
        this.endTime = endTime;
    }

    public String getDeliverySessionId() {
        return deliverySessionId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndDeliverySessionCommand)) return false;
        EndDeliverySessionCommand that = (EndDeliverySessionCommand) o;
        return deliverySessionId.equals(that.deliverySessionId) &&
                endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliverySessionId, endTime);
    }

}
