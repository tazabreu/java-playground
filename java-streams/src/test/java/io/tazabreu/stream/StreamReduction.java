package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

public class StreamReduction {
    @Test
    public void reduceExample() {
        Double sumViaSpecializedOperation = StreamCreation.getEmployees()
                .mapToDouble(employee -> employee.salary().doubleValue())
                .sum();

        BigDecimal sumViaReduce = StreamCreation.getEmployees()
                .map(Employee::salary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(sumViaSpecializedOperation, Matchers.is(sumViaReduce.doubleValue()));
    }
}
