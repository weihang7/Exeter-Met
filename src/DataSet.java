import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
	
public class DataSet {

	private final String sDriver = "org.sqlite.JDBC"; 
	private String sUrl="jdbc:sqlite:data.db";
	private final int iTimeout = 30;
	private Connection conn;
	private Statement statement;
	
	public DataSet() throws ClassNotFoundException, SQLException{
		setConnection();
		setStatement();
		if(conn==null)
			System.out.println("Connection failed");
	}
	
	public DataSet(boolean ver) throws ClassNotFoundException, SQLException{
		sUrl = "jdbc:sqlite:verify.db";
		setConnection();
		setStatement();
		if(conn==null)
			System.out.println("Connection failed");
	}
	 
	public void setConnection() throws ClassNotFoundException, SQLException {
		Class.forName(sDriver);
		conn = DriverManager.getConnection(sUrl);
	}
	 
	public Connection getConnection() {
		return conn;
	}
	 
	public void setStatement() throws SQLException {
		statement = conn.createStatement();
		statement.setQueryTimeout(iTimeout);
	}
	 
	public Statement getStatement() {
		return statement;
	}
	 
	public void executeStmt(String instruction) throws SQLException {
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
	 
	public void closeConnection() throws SQLException{
		conn.close();
	}
	 
}
