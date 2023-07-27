package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import com.example.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.firstname = ?1 and e.lastname = ?2")
    public Optional<Employee> findEmpByName(String name1, String name2);

    @Modifying
    @Query(value = "UPDATE emp SET salary = ?1 where id = ?2", nativeQuery = true)
    public int updateEmp(Integer sal, Integer empId);
}
