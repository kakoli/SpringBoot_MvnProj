package com.example.springdata.dept.persistence;

import com.example.model.EmpData;
import com.example.persistence.entity.Department;
import com.example.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE d.name = ?1")
    public Optional<Department> findDeptByName(String name);

    /* EmpData is not known to JPA engine, gives error - No converter found capable of converting from type.
    Hence, JPQL projections of Constructor reference is used to return non-Entity POJO from this Repo class.
    Also native query like the one below does not work here.
    @Query(value = "SELECT e.firstname, e.lastname, d.name, e.joindate FROM dept d, emp e WHERE \n" +
            "d.id = e.dept_id and d.name = ?1", nativeQuery = true) */
    @Query("SELECT new com.example.model.EmpData(e.firstname, e.lastname, d.name, a.city, a.state, a.zip) " +
            "FROM Department d JOIN d.emps e JOIN e.address a WHERE d.name = ?1")
    public List<EmpData> getEmpsFromDept(String name);

    @Modifying  //Using indexed parameters
    @Query(value = "UPDATE Department d SET d.hod = ?1 where d.name = ?2")
    public int updateDept(String hod, String name);
}
