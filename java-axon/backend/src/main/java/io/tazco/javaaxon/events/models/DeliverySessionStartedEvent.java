package io.tazco.javaaxon.events.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliverySessionStartedEvent {

    private final String deliverySessionId;
    private final LocalDateTime startTime;

    public DeliverySessionStartedEvent(String deliverySessionId, LocalDateTime startTime) {
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
        if (!(o instanceof DeliverySessionStartedEvent)) return false;
        DeliverySessionStartedEvent that = (DeliverySessionStartedEvent) o;
        return deliverySessionId.equals(that.deliverySessionId) &&
                startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliverySessionId, startTime);
    }

}
