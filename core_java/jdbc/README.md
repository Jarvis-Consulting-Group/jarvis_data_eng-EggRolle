# Introduction
This project is responsible for employing JDBC, Java Databas Connectivty, to interact with a Postgres database containing pertaining information regarding products,
orders and customers, mimicing a realistic practice relational database. Core JDBC principles responsible for interacting with the database such as Data Access Objects
and repository design patterns are implemented to imitate full SQL functionality within through Java while also handling ACID properties such as managing transactions 
and commits. The Java project employs Maven which is responsible for building the project, managing dependencies, compiling and appropriately testing.

# Implementaiton
## ER Diagram
![ER Diagram](/core_java/jdbc/assets/erdJDBC.png)

## Design Patterns
The project implements DAO, Data Access Object, and Reposistory design patterns. DAO pattern is an abstract method of design responsible for creainng classes/interfaces
required to impement CRUD operations in a database. Generally speaking, with DAO pattern a single database table should be represented through a single class/interface,
and every instance of the class are essentially rows within the table. During the project the DAO pattern is applied to create a class modelling the 'Customers' table,
containing class attributes representing fields within the database table and methods responsible for CRUD operations such as Creating new entries, Updating existing 
rows, Reading and Deleting rows.

Repository design pattern is a pattern similar to DAO's, although they strictly focus on single tables in the database per class. The DAO made for customer in our proejct is therefore not only a DAO, but also follows repository design pattern. While both DAO and repository implementations are similar in interacting with a database and abstracting access to it, DAO's are generally designed to be more flexible, working with various object types whereas reposity only allows for a search of specific types of object and should function moreso like a collection, supporting CRUD operations which our Customer class does.



# Test
The app was tested primarily manually by running through all sections of the project to ensure the primary goal of accessing/interacting with the database would function without issues. The primary concern of the project is using the 'Customer' DAO to interact with the database and preform simple CRUD operations, however before this several classes responsible for establishing a connection to the server were implemented. Our Dabase Connection Manager class and JDBCExecutor 'main' class were resonsible for establishing these connections and through multiple trials had no issues successfully reaching the database. From within these classes several operations for creating new entries for the table, pulling rows from the table and updating/deleting rows were executed with a variety of arguments to ensure coverage over all of the methods implement in the Customer DAO class. Following these queries, the results were then manually inspected to ensure that they all preformed and committed to the database appropriately.
