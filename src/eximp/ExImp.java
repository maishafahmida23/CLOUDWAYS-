package eximp;

 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExImp {

    public void connectDB() {
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=ExImp;selectMethod=cursor", "sa", "112233");

	    System.out.println("DATABASE NAME IS:"+ connection.getMetaData().getDatabaseProductName());
	    
	    Statement statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery("SELECT ProductPrice FROM Product");
	    
	    while (resultSet.next()) {
		System.out.println("Product Price is:" + resultSet.getString("ProductPrice"));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
}
