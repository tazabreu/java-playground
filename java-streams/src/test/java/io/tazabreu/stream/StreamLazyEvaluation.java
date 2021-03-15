package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamLazyEvaluation {
    @Test
    public void lazyEvaluationExample() {
        List<Employee> employees = StreamCreation.getEmployees().collect(Collectors.toList());
        final Integer HIGH_AMOUNT = 100000;

        Optional<Employee> employee = employees.stream()
                .peek(e -> e.changeSalary(BigDecimal.valueOf(HIGH_AMOUNT)))
                .findFirst();

        assertEquals(1, employees.stream().filter(e -> e.salary().intValue() > HIGH_AMOUNT).count());
    }
}
