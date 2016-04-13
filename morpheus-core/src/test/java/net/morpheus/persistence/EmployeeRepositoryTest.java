package net.morpheus.persistence;

import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import net.morpheus.domain.Skill;
import org.junit.Test;

import java.util.ArrayList;

import static net.morpheus.domain.Role.Developer;
import static net.morpheus.domain.Role.TeamLead;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmployeeRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void canCreateEmployee() {
        createEmployee();

        Employee employeeByName = employeeRepository.findByName(employee.username());
        assertThat(employeeByName.username(), is("Pedr"));
        assertThat(employeeByName.role(), is(Developer));
        assertThat(employeeByName.skills().size(), is(1));
        assertThat(employeeByName.skills().get(0).value(), is(7));
        assertThat(employeeByName.skills().get(0).description(), is("Functional Delivery"));
    }

    @Test
    public void canUpdateEmployee() {
        createEmployee();
        employee.addNewSkill(new Skill("Quality of code", 2));
        employee.setRole(Role.TeamLead);

        employeeRepository.update(employee);

        Employee retrievedEmployee = employeeRepository.findByName(employee.username());
        assertThat(retrievedEmployee.skills().size(), is(2));
        assertThat(retrievedEmployee.skills().get(1).description(), is("Quality of code"));
        assertThat(retrievedEmployee.skills().get(1).value(), is(2));
        assertThat(retrievedEmployee.role(), is(TeamLead));
    }

    private void createEmployee() {
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Functional Delivery", 7));
        employee = new Employee("Pedr", Developer, skills);

        employeeRepository.create(employee);
    }
}