package io.tazabreu.springrestapi.domain.exception;

public class InvalidTransitionException extends RuntimeException {

    private final Object object;
    private final Object current;
    private final Object next;

    public InvalidTransitionException(Object object, Object current, Object next) {
        super(String.format("It is not possible to transition from [%s] to [%s]. Reference is [%s]",
                current, next, object));
        this.object = object;
        this.current = current;
        this.next = next;
    }

}
