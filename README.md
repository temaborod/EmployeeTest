# EmployeeTest
A JAVA program that creates a table in a database, executes an SQL query from
a database table and displays the result on the screen. The JDBC standard was used.

Task 1 – Create a table
Create an Employee table and fill it with data.
The following columns are in the table:
• Employee ID
• Name
• Last name
• Date of birth
• Department
• Salary

Task 2 – Implement The Service
Implement the EmployeeService class, which will have the following functionality:
Task 2.1 – Find a user by ID
For the EmployeeService service, you need to implement the findById method to search for employees by ID in
the table. At the same time, it must be remembered that the ID passed as a parameter may not be contained
in the table.
Task 2.2 – Group users by name
For the EmployeeService service, you need to implement the groupByName method, which will return
a list of grouped employee names.
Task 2.3 – Searching between dates
For the EmployeeService service, you need to implement the findBetween method, which will return
a list of employees whose dates of birth are in the interval (example 1990 and 1992).
