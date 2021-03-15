package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamSpecialization {
    @Test
    public void creationExample() {
        Long latestEmpId = StreamCreation.getEmployees()
        .mapToLong(Employee::id)
                .max()
                .orElseThrow(NoSuchElementException::new);

        assertThat(latestEmpId, Matchers.is(5L));

        IntStream intStream = IntStream.range(0, 100);
        assertThat(intStream.max().orElseThrow(NoSuchElementException::new), Matchers.is(99));
    }

    @Test
    public void specializedOperationsExample() {
        Double avgSal = StreamCreation.getEmployees()
                .mapToDouble(employee -> employee.salary().doubleValue())
                .average()
                .orElseThrow(NoSuchElementException::new);

        assertEquals(avgSal, Double.valueOf("12500"));
    }

}
