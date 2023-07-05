package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.firstname = ?1 and e.lastname = ?2")
    public Optional<Employee> findEmpByName(String name1, String name2);

    @Query(value = "SELECT e.firstname, e.lastname, d.name, e.joindate FROM dept d, emp e WHERE \n" +
            "d.id = e.dept_id and d.name = ?1", nativeQuery = true)
    public List<EmpData> getEmpFromDept(String deptName);

    @Query(value = "UPDATE emp SET salary = ?1 where id = ?2", nativeQuery = true)
    public int updateEmp(Integer sal, Integer empId);
}
