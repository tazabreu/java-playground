package io.tazabreu.springrestapi.domain.exception;

public class RestResourceNotFoundException extends RuntimeException {

    private final Class entityClass;
    private final Long id;

    public RestResourceNotFoundException(Class entityClass, Long id) {
        super(String.format("Could not find Rest Resource [%s] with id [%s]", entityClass.getName(), id.toString()));
        this.entityClass = entityClass;
        this.id = id;
    }

}
