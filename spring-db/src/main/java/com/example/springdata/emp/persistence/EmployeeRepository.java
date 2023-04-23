package com.example.springdata.emp.persistence;

import com.example.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    //@Query(value = "select name from emp_gradle where name = ?1", nativeQuery = true)
    @Query("SELECT emp FROM Employee emp WHERE emp.firstname = ?1")
    public Optional<Employee> findEmpByName(String name);

    /*@Query(value = "", nativeQuery = true)
    public List<EmpData> getDeptProj(String empName);

    @Query(value = "UPDATE emp SET dept = ?1 where id = ?2", nativeQuery = true)
    public int updateEmp(String dept, Integer empId);*/

}
