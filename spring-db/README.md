# SpringData
* **SpringData JPA** - CRUD db operations via Spring Data JPA. One-One, Many-One relationship
* **Spring JDBC**

**POST APIs** :

* [.../api/v2/dept]() - Save department with single/multiple employee(s). Saves in all 3 tables.
Dept-Emp has unidirectional One-Many relation.
* [.../api/v2/emp]() - Save an employee with personal details like salary, address etc., but without dept.

**GET APIs** :

* [.../api/v2/dept/{deptName}]() - Get only Department details based on name.
* [.../api/v2/dept/{deptName}/emps]() - Get all employee details of a department via JPQL's class-based projection.
* [.../api/v2/emp/{empId}]() - Get employee details based on Id
* [.../api/v2/emp?deptName={deptName}]() - Get all employees of a department via JdbcTemplate-RowMapper.

**PUT APIs** :
* [.../api/v2/dept/{deptName}]() - Update specific attribute 'hod' of a department having specific 'name'. 
* [.../api/v2/emp/{empId}]() - Update specific attribute 'salary' of an employee having specific Id.