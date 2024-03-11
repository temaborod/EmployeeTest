import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        // creating an instance of a class
        EmployeeService es = new EmployeeService();
        // creating of the table
        es.createTable();
        // filling in the table
        try {
            es.tableFilling();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // working with other methods via the menu
        es.menu();
    }

}
