import java.sql.*;
public class Main {
    private static final String db_username="postgres";
    private static final String db_password="stj624";
    private static final String db_url="jdbc:postgresql://localhost:5432/postgres";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        Connection connection = DriverManager.getConnection(db_url, db_username, db_password);
        //Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
        //объект который умеет отравлять запросы в базу
        Statement statement= connection.createStatement();
        String SqlSelectTask="select * from data order by id desc";
        ResultSet result=statement.executeQuery(SqlSelectTask);
//        while (result.next()){
//            System.out.println(result.getInt("id"));
//        }
    }
}
