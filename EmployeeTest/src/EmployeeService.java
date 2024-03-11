import java.sql.*;
import java.util.Scanner;

public class EmployeeService {
    // data for connecting to the server
    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    // class constructor
    public EmployeeService() {
        try {
            // connecting the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // connecting to the server
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }
    }

    // the method of creating the table
    public void createTable(){
        // checking for the existence of the table, if it exists, we delete it
        try {
            Statement dropTable = connection.createStatement();
            dropTable.executeUpdate ("DROP TABLE employee");
        }
        catch (SQLException e){}

        // creating a table via a statement
        Statement createTable = null;
        try {
            createTable = connection.createStatement();
            createTable.executeUpdate("CREATE TABLE `employee_db`.`employee` (\n" +
                    "  `idEmployee` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `surname` VARCHAR(45) NOT NULL,\n" +
                    "  `yearOfBirth` INT NOT NULL,\n" +
                    "  `wage` DECIMAL(12,2) UNSIGNED NOT NULL,\n" +
                    "  PRIMARY KEY (`idEmployee`),\n" +
                    "  UNIQUE INDEX `idEmployee_UNIQUE` (`idEmployee` ASC) VISIBLE);");
            System.out.println("The 'Employee' table was created!");
        } catch (SQLException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }
    }

    // the method of printing of the table
    private static void printTable(){
        String query = "SELECT * FROM employee";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int dateOfBirth = resultSet.getInt(4);
                double wage = resultSet.getDouble(5);
                System.out.println(id+"\t"+name+"\t"+surname+"\t"+dateOfBirth+"\t"+wage);
            }
        } catch (SQLException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }
    }

    // the method of filling in the table
    public void tableFilling() throws SQLException {
        PreparedStatement insert = connection.prepareStatement ("INSERT INTO employee (name, surname, yearOfBirth, wage) " +
                "VALUES (?, ?, ?, ?)");
        Scanner in = new Scanner(System.in);
        String name;
        String surname;
        int yearOfBirth;
        double wage;
        int flag = 1;
        while (flag==1){
            System.out.print("\033[H\033[J");
            System.out.println("Do you want to add a line?(1/0): ");
            flag = in.nextInt();
            if(in.hasNextLine()) in.nextLine();
            if(flag==1) {
                System.out.println("name: ");
                name = in.nextLine();
                if(in.hasNextLine()) in.nextLine();
                System.out.println("surname: ");
                surname = in.nextLine();
                if(in.hasNextLine()) in.nextLine();
                System.out.println("yearOfBirth: ");
                yearOfBirth = in.nextInt();
                if(in.hasNextLine()) in.nextLine();
                System.out.println("wage: ");
                wage = in.nextDouble();
                if(in.hasNextLine()) in.nextLine();
                insert.setString(1, name);
                insert.setString(2, surname);
                insert.setInt(3, yearOfBirth);
                insert.setDouble(4, wage);
                insert.executeUpdate();
            }else continue;
        }
    }

    public static void menu(){
        Scanner in = new Scanner(System.in);
        int x;
        do{
            System.out.print("\033[H\033[J");
            System.out.println("MENU\n"+
                    "1. Show the table\n"+
                    "2. Find by ID\n"+
                    "3. Group by name\n"+
                    "3. Searching between dates\n"+
                    "4. Exit\n"+
                    "::: ");
            x = in.nextInt();
            switch (x){
                case 1:
                    printTable();
                    break;
                case 2:
                    findById();
                    break;
                case 3:
                    groupByName();
                    break;
                case 4:
                    findBetween();
                    break;
            }
        }while(x!=5);
    }

    private static void findById(){
        System.out.println("Enter the ID: ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        String query = "SELECT * FROM employee WHERE idEmployee = "+id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            int empId = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);
            int dateOfBirth = resultSet.getInt(4);
            double wage = resultSet.getDouble(5);
            System.out.println(empId+"\t"+name+"\t"+surname+"\t"+dateOfBirth+"\t"+wage);
        } catch (SQLException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }

    }

    private static void groupByName(){
        System.out.println("Enter name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        String query = "SELECT * FROM employee GROUP BY "+name;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String empName = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int dateOfBirth = resultSet.getInt(4);
                double wage = resultSet.getDouble(5);
                System.out.println(id+"\t"+empName+"\t"+surname+"\t"+dateOfBirth+"\t"+wage);
            }
        } catch (SQLException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }
    }

    private static void findBetween(){
        System.out.println("Enter 1st date: ");
        Scanner in = new Scanner(System.in);
        int year1 = in.nextInt();
        System.out.println("Enter 2nd date: ");
        int year2 = in.nextInt();
        String query = "SELECT * FROM employee WHERE yearOfBirth BETWEEN "+year1+" AND "+year2;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String empName = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int dateOfBirth = resultSet.getInt(4);
                double wage = resultSet.getDouble(5);
                System.out.println(id+"\t"+empName+"\t"+surname+"\t"+dateOfBirth+"\t"+wage);
            }
        } catch (SQLException e) {
            System.out.println ();
            System.out.println ("Error: " + e.getMessage());
        }
    }
}
