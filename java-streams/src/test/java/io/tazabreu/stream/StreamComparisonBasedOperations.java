package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamComparisonBasedOperations {
    @Test
    public void sortedExample() {
        Employee employee = StreamCreation.getEmployees()
                .sorted(Comparator.comparing(Employee::salary))
                .findFirst()
                .orElse(null);

        assertEquals(employee.name(), "Aaron Arnold");
    }

    @Test
    public void minMaxExample() {
        Employee worstPaidEmployee = StreamCreation.getEmployees().min(Comparator.comparing(Employee::salary))
                .orElse(null);

        Employee bestPaidEmployee = StreamCreation.getEmployees().max(Comparator.comparing(Employee::salary))
                .orElse(null);

        assertEquals(worstPaidEmployee.name(), "Aaron Arnold");
        assertEquals(bestPaidEmployee.name(), "Mark Zuckerberg");
    }

    @Test
    public void distinctExample() {
        List<Integer> intList = Arrays.asList(2, 5, 3, 2, 4, 3);
        List<Integer> distinctIntList = intList.stream().distinct().collect(Collectors.toList());
        assertEquals(distinctIntList, Arrays.asList(2, 5, 3, 4));
    }

    @Test
    public void matchingExample() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);

        boolean allEven = intList.stream().allMatch(i -> i % 2 == 0);
        boolean anyEven = intList.stream().anyMatch(i -> i % 2 == 0);
        boolean noneMultipleOfThree = intList.stream().noneMatch(i -> i % 3 == 0);

        assertFalse(allEven);
        assertTrue(anyEven);
        assertFalse(noneMultipleOfThree);
    }
}
