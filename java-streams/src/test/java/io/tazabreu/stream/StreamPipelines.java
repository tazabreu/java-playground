package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StreamPipelines {

    @Test
    public void terminalOperationsExample() {
        Stream<Employee> employeesWithHighSalary = StreamCreation.getEmployees()
                .filter(e -> e.salary().intValue() > 5000);

        long empCount = employeesWithHighSalary.count();

        assertEquals(empCount, 3L);
        assertThrows(IllegalStateException.class, employeesWithHighSalary::count);
    }

    @Test
    public void shortCircuitingOperationsExample() {
        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

        List<Integer> collect = infiniteStream
                .skip(3)
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(collect, Arrays.asList(16, 32, 64, 128, 256));
    }
}
