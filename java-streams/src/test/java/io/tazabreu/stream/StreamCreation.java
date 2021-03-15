package io.tazabreu.stream;

import io.tazabreu.domain.Employee;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamCreation {
    private static Employee[] managementBoard() {
        return new Employee[] {
                new Employee(1L, "Jeff Bezos", BigDecimal.valueOf(10000d)),
                new Employee(2L, "Bill Gates", BigDecimal.valueOf(20000d)),
                new Employee(3L, "Mark Zuckerberg", BigDecimal.valueOf(30000d))
        };
    }

    private static Employee[] engineers() {
        return new Employee[]{
                new Employee(4L, "Aaron Arnold", BigDecimal.valueOf(1000d)),
                new Employee(5L, "Bruce Banner", BigDecimal.valueOf(1500d))
        };
    }

    public static Stream<Employee> getEmployees() {
        return Stream.concat(getManagementBoard(), getEngineers());
    }

    public static Stream<Employee> getManagementBoard() {
        return Stream.of(managementBoard());
    }

    public static Stream<Employee> getEngineers() {
        return Arrays.stream(engineers());
    }
}
