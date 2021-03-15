package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StreamCollection {
    @Test
    public void joiningExample() {
        String employeeNames = StreamCreation.getEmployees()
                .map(Employee::name)
                .collect(Collectors.joining(", "));

        assertThat(employeeNames,
                Matchers.is("Jeff Bezos, Bill Gates, Mark Zuckerberg, Aaron Arnold, Bruce Banner"));
    }

    @Test
    public void toListExample() {
        List<Employee> employeeList = StreamCreation.getEmployees()
                .collect(Collectors.toList());
        assertThat(employeeList.size(), Matchers.is(5));
    }

    @Test
    public void toSetExample() {
        Set<Employee> employeeSet = StreamCreation.getEmployees()
                .collect(Collectors.toSet());
        assertThat(employeeSet.size(), Matchers.is(5));
    }

    @Test
    public void toCollectionExample() {
        Vector<Employee> employeeVector = StreamCreation.getEmployees()
                .collect(Collectors.toCollection(Vector::new));
        assertThat(employeeVector.size(), Matchers.is(5));
    }

    @Test
    public void summarizingDoubleExample() {
        DoubleSummaryStatistics statistics = StreamCreation.getEmployees()
                .collect(Collectors.summarizingDouble(e -> e.salary().doubleValue()));

        assertAll(
                () -> assertThat("Count should be 5", statistics.getCount(), Matchers.is(5L)),
                () -> assertThat("Average should be 12500", statistics.getAverage(), Matchers.is(12500.0)),
                () -> assertThat("Max should be 30000", statistics.getMax(), Matchers.is(30000.0)),
                () -> assertThat("Min should be 1000", statistics.getMin(), Matchers.is(1000.0)),
                () -> assertThat("Sum should be 62500", statistics.getSum(), Matchers.is(62500.0))
        );
    }

    @Test
    public void partitioningByExample() {
        Map<Boolean, List<Employee>> salaryMap = StreamCreation.getEmployees()
                .collect(Collectors.partitioningBy(employee -> employee.salary().intValue() > 8000));

        assertAll(
                () -> assertThat(salaryMap.get(true).size(), Matchers.is(3)),
                () -> assertThat(salaryMap.get(false).size(), Matchers.is(2))
        );
    }

    @Test
    public void groupingByExample() {
        Map<Character, List<Employee>> salaryMap = StreamCreation.getEmployees()
                .collect(Collectors.groupingBy(employee -> employee.name().charAt(0)));

        assertAll(
                () -> assertThat(salaryMap.get('A').size(), Matchers.is(1)),
                () -> assertThat(salaryMap.get('B').size(), Matchers.is(2)),
                () -> assertThat(salaryMap.get('J').size(), Matchers.is(1)),
                () -> assertThat(salaryMap.get('M').size(), Matchers.is(1))
        );
    }

    @Test
    public void reducingExample() {
        double percentage = 10.0;
        Double salIncrOverhead = StreamCreation.getEmployees()
                .collect(Collectors.reducing(0.0,
                        e -> e.salary().doubleValue() * percentage / 100,
                        (s1, s2) -> s1 + s2
                        )
                );

        assertThat(salIncrOverhead, Matchers.is(6250d));
    }

    @Test
    public void groupingAndReducingExample() {
        Comparator<Employee> byNameLength = Comparator.comparing(Employee::name);

        Map<Character, Optional<Employee>> longestNameByAlphabet = StreamCreation.getEmployees().collect(
                Collectors.groupingBy(e -> e.name().charAt(0),
                        Collectors.reducing(BinaryOperator.maxBy(byNameLength))));

        assertThat(longestNameByAlphabet.get('A').get().name(), Matchers.is("Aaron Arnold"));
        assertThat(longestNameByAlphabet.get('B').get().name(), Matchers.is("Bruce Banner"));
        assertThat(longestNameByAlphabet.get('J').get().name(), Matchers.is("Jeff Bezos"));
        assertThat(longestNameByAlphabet.get('M').get().name(), Matchers.is("Mark Zuckerberg"));
    }

}
