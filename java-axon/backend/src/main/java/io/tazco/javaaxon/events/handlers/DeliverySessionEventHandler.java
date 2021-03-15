package io.tazco.javaaxon.events.handlers;

import io.tazco.javaaxon.events.models.DeliverySessionEndedEvent;
import io.tazco.javaaxon.events.models.DeliverySessionStartedEvent;
import io.tazco.javaaxon.exceptions.NoActiveDeliverySessionWasFoundException;
import io.tazco.javaaxon.queries.FindActiveDeliverySessionQuery;
import io.tazco.javaaxon.queries.DeliverySessionQueryModel;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DeliverySessionEventHandler {

    // ideally, this should be provided by some kind of external DataSource. Let's use a map for simplicity
    private final Map<String, DeliverySessionQueryModel> deliverySessions = new HashMap<>();

    @EventHandler
    public void on(DeliverySessionStartedEvent deliverySessionStartedEvent) {
        deliverySessions.put(deliverySessionStartedEvent.getDeliverySessionId(),
                new DeliverySessionQueryModel(deliverySessionStartedEvent.getDeliverySessionId(),
                        deliverySessionStartedEvent.getStartTime(), null));
    }

    @EventHandler
    public void on(DeliverySessionEndedEvent deliverySessionEndedEvent) {
        Optional.ofNullable(deliverySessions.get(deliverySessionEndedEvent.getDeliverySessionId()))
                .ifPresent(DeliverySessionQueryModel::end);
    }

    @QueryHandler
    public List<DeliverySessionQueryModel> handle(FindActiveDeliverySessionQuery findActiveDeliverySessionQuery) {
        return deliverySessions.values().stream()
                .filter(it -> Objects.isNull(it.getEndTime()))
                .collect(Collectors.toList());
    }

}
