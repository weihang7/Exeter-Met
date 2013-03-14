import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
	
public class DataSet {

	
	 
	public  String sDriver = "org.sqlite.JDBC"; 
	public  String sUrl="data.db";
	public  int iTimeout = 30;
	public  Connection conn;
	public  Statement statement;
	
	public DataSet() throws Exception{
		setConnection();
		setStatement();
		if(conn!=null)
			System.out.println("Connected to database");
		else
			System.out.println("Connection failed");
	}
	 
	public void setConnection() throws Exception {
		Class.forName(sDriver);
		conn = DriverManager.getConnection(sUrl);
	}
	 
	public Connection getConnection() {
		return conn;
	}
	 
	public void setStatement() throws Exception {
		if (conn == null) {
			setConnection();
		}
		statement = conn.createStatement();
		statement.setQueryTimeout(iTimeout);  // set timeout to 30 sec.
	}
	 
	public  Statement getStatement() {
		return statement;
	}
	 
	public  void executeStmt(String instruction) throws SQLException {
		statement.executeUpdate(instruction);
	}
	 
	public void executeStmt(String[] instructionSet) throws SQLException {
		for (int i = 0; i < instructionSet.length; i++) {
			executeStmt(instructionSet[i]);
		}
	}
	 
	public ResultSet executeQry(String instruction) throws SQLException {
		return statement.executeQuery(instruction);
	} 
	 
	public void closeConnection() {
		try {
			conn.close();
		} 
		catch (Exception e){
			e.printStackTrace();
		}
	}
	 
}
