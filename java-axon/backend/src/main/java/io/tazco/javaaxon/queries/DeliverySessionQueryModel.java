package io.tazco.javaaxon.queries;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeliverySessionQueryModel {

    private final String deliverySessionId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private DeliverySessionStatus status;

    public DeliverySessionQueryModel(String deliverySessionId, LocalDateTime startTime, LocalDateTime endTime) {
        this.deliverySessionId = deliverySessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = DeliverySessionStatus.STARTED;
    }

    public void end() {
        this.status = DeliverySessionStatus.ENDED;
    }

    public String getDeliverySessionId() {
        return deliverySessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public enum DeliverySessionStatus {
        STARTED, ENDED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliverySessionQueryModel)) return false;
        DeliverySessionQueryModel that = (DeliverySessionQueryModel) o;
        return Objects.equals(getDeliverySessionId(), that.getDeliverySessionId()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(endTime, that.endTime) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeliverySessionId(), getStartTime(), endTime, status);
    }

}
