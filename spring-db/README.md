# SpringData
* **SpringData JPA** - CRUD db operations via Spring Data JPA. One-One, Many-One relationship
* **Spring JDBC**

**Write APIs** :

* [.../api/v2/emp]() - Save an employee with personal details like salary, address etc., but without dept.

* [.../api/v2/dept]() - Save department with single/multiple employee(s)

**Read APIs** :

* [.../api/v2/emp/{empId}]() - Get employee details based on Id
* [.../api/v2/dept/{deptName}]() - Get Department details based on name.
* [.../api/v2/dept/{deptName}/emps]() - Get all employee details of a department.