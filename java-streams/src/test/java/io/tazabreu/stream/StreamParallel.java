package io.tazabreu.stream;

import io.tazabreu.domain.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class StreamParallel {
    @Test
    public void parallelExample() {
        BigDecimal salaryRaise = BigDecimal.valueOf(1000);
        Map<Long, BigDecimal> employeesAfterSalaryRaise = StreamCreation.getEmployees()
                .parallel()
                .peek(employee -> employee.changeSalary(salaryRaise))
                .collect(Collectors.toMap(Employee::id, Employee::salary));

        StreamCreation.getEmployees().parallel().forEach(employeeBeforeSalaryRaise -> {
            assertThat(employeeBeforeSalaryRaise.salary(),
                    Matchers.is(employeesAfterSalaryRaise.get(employeeBeforeSalaryRaise.id()).subtract(salaryRaise)));
        });
    }
}
