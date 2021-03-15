package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamOperations {

    private Logger logger = LoggerFactory.getLogger(StreamOperations.class);

    @Test()
    public void forEachExample() {
        List<Employee> employeesBeforeReadjustment  = StreamCreation.getEmployees().collect(Collectors.toList());
        List<Employee> employeesAfterReadjustment = StreamCreation.getEmployees().collect(Collectors.toList());
        BigDecimal readjustmentValue = BigDecimal.TEN;

        employeesAfterReadjustment.forEach(employee -> {
            employee.changeSalary(readjustmentValue);
            logger.info(employee.toString());
        });

        IntStream.of(employeesAfterReadjustment.size() - 1).forEach(index -> {
            assertEquals(employeesAfterReadjustment.get(index).salary(),
                    employeesBeforeReadjustment.get(index).salary().add(readjustmentValue), "asserting salary readjustment");
        });
    }

    private Employee findEmployee(Long id) {
        return StreamCreation.getEmployees()
                .filter(employee -> employee.isIdentifiedBy(id)).findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void mapExample() {
        Long[] employeeIds = { 1L, 2L, 3L };
        List<Employee> mappedEmployees = Stream.of(employeeIds)
                .map(this::findEmployee)
                .collect(Collectors.toList());
        assertEquals(mappedEmployees.size(), employeeIds.length);
    }

    @Test
    public void collectExample() {
        List<Employee> employees = StreamCreation.getEmployees().collect(Collectors.toList());
        assertEquals(5,employees.size());
    }

    @Test
    public void filterExample() {
        List<Employee> employees = StreamCreation.getEmployees()
                .filter(employee -> employee.salary().longValue() > 10000)
                .collect(Collectors.toList());
        assertEquals(2,employees.size());
    }

    @Test
    public void findFirstExample() {
        Employee employee = StreamCreation.getEmployees()
                .filter(e -> e.salary().longValue() > 5000)
                .findFirst()
                .orElse(null);

        assertEquals(employee.name(), "Jeff Bezos");
    }

    @Test
    public void flatMapExample() {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));

        List<String> namesFlatStream = namesNested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertEquals(namesFlatStream.size(), namesNested.size() * 2);
    }

    @Test
    public void peekExample() {
        List<Employee> employees = StreamCreation.getEmployees()
                .peek(employee -> employee.changeSalary(BigDecimal.valueOf(10000)))
                .peek(employee -> logger.info(employee.toString()))
                .collect(Collectors.toList());

        employees.forEach(employee -> assertThat(employee.salary(), greaterThan(BigDecimal.valueOf(10000))));
    }

}
