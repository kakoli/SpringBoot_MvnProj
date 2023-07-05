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
public interface DeptRepository extends JpaRepository<Department, Integer> {

    //@Query(value = "select name from dept where name = ?1", nativeQuery = true)
    @Query("SELECT d FROM Department d WHERE d.name = ?1")
    public Optional<Department> findDeptByName(String name);

    @Query(value = "SELECT e.firstname, d.name, e.salary, a.city, a.zip FROM dept d, emp e, address a \n" +
            "WHERE e.dept_id = d.id and e.addr_id = a.id and d.name = ?1", nativeQuery = true)
    public List<EmpData> getAllEmps(String deptName);

    /*@Query(value = "UPDATE dept SET hod = ?1 where id = ?2", nativeQuery = true)
    public int updateDept(String s, Integer deptId);*/

}
