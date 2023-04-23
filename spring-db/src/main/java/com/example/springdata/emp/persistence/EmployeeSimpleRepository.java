package com.example.springdata.emp.persistence;

import com.example.persistence.entity.EmployeeSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeSimpleRepository extends JpaRepository<EmployeeSimple, Integer> {

    //@Query(value = "select name from emp_simple where name = ?1", nativeQuery = true)
    @Query("SELECT emp FROM EmployeeSimple emp WHERE emp.name = ?1")
    public Optional<EmployeeSimple> findEmpByName(String name);

    @Query(value = "UPDATE emp_simple SET dept = ?1 where id = ?2", nativeQuery = true)
    public int updateEmp(String dept, Integer empId);

}
