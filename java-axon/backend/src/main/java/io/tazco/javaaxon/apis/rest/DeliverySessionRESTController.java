package io.tazco.javaaxon.apis.rest;

import io.tazco.javaaxon.commands.EndDeliverySessionCommand;
import io.tazco.javaaxon.commands.StartDeliverySessionCommand;
import io.tazco.javaaxon.queries.DeliverySessionQueryModel;
import io.tazco.javaaxon.queries.FindActiveDeliverySessionQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/delivery-sessions")
public class DeliverySessionRESTController {

    public DeliverySessionRESTController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @GetMapping("/hello-world")
    public Mono<String> helloWorld() {
        return Mono.just("hello world");
    }

    @PostMapping
    public Mono<String> startDeliverySession() {
        String deliverySessionId = UUID.randomUUID().toString();
        commandGateway.send(new StartDeliverySessionCommand(deliverySessionId, LocalDateTime.now()))
                .thenAccept(System.out::println);
        return Mono.just("StartDeliverySessionCommand sent successfully");
    }

    @PostMapping("/end")
    public Mono<String> endDeliverySession() {
        DeliverySessionQueryModel activeDeliverySession = queryGateway.query(new FindActiveDeliverySessionQuery(),
                ResponseTypes.instanceOf(DeliverySessionQueryModel.class)).join();

        commandGateway.send(new EndDeliverySessionCommand(activeDeliverySession.getDeliverySessionId(),
                LocalDateTime.now())).join();

        return Mono.just(String.format("Closed Delivery Session with id = [%s]",
                activeDeliverySession.getDeliverySessionId()));
    }

    @GetMapping
    public Mono<DeliverySessionQueryModel> retrieve() {
        return Mono.just(queryGateway.query(new FindActiveDeliverySessionQuery(),
                ResponseTypes.instanceOf(DeliverySessionQueryModel.class)).join());
    }

    @GetMapping("/is-open")
    public Mono<String> isDeliverySessionActive() {
        DeliverySessionQueryModel activeDeliverySession = queryGateway.query(new FindActiveDeliverySessionQuery(),
                ResponseTypes.instanceOf(DeliverySessionQueryModel.class)).join();

        if (Objects.nonNull(activeDeliverySession)) {
            return Mono.just(
                    String.format("Delivery session %s is open. Started at %s.",
                            activeDeliverySession.getDeliverySessionId(),
                            activeDeliverySession.getStartTime()));
        } else {
            return Mono.just("There are no active delivery sessions at this moment");
        }
    }

}
