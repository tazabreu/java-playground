package io.tazco.javaaxon.events.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliverySessionEndedEvent {

    private final String deliverySessionId;
    private final LocalDateTime endTime;

    public DeliverySessionEndedEvent(String deliverySessionId, LocalDateTime endTime) {
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
        if (!(o instanceof DeliverySessionEndedEvent)) return false;
        DeliverySessionEndedEvent that = (DeliverySessionEndedEvent) o;
        return deliverySessionId.equals(that.deliverySessionId) &&
                endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliverySessionId, endTime);
    }

}
