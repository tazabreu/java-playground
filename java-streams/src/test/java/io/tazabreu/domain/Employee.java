package io.tazabreu.domain;

import java.math.BigDecimal;

public class Employee {
    private Long id;
    private String name;
    private BigDecimal salary;
    public Employee(Long id, String name, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public void changeSalary(BigDecimal delta) {
        this.salary = this.salary.add(delta);
    }

    @Override
    public String toString() {
        return String.format("Employee = {\"id\": %s, \"name\": %s, \"salary\": %s}",
                id,
                name,
                salary);
    }

    public boolean isIdentifiedBy(Long id) {
        return this.id.equals(id);
    }

    public Long id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public BigDecimal salary() {
        return this.salary;
    }

}
