import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import com.csvreader.*;


public class Data {
	
	public static void main(String[] args){
		try{
			String sDriverName = "org.sqlite.JDBC";
			Class.forName(sDriverName);
			
			String sTempDb = "data.db";
			String sJdbc = "jdbc:sqlite";
			String sDbUrl = sJdbc + ":" + sTempDb;
			String sMakeInsert = "INSERT INTO data VALUES(";
			String sMakeSelect = "SELECT time from data";
			String time = "";
			 
			// create a database connection
			Connection conn = DriverManager.getConnection(sDbUrl);
			try{
				Statement s = conn.createStatement();
				try{
					ResultSet rs = s.executeQuery(sMakeSelect);
					rs.last();
					try{
						time = rs.getString("time");
					}
					finally{
						rs.close();
					}
				}
				finally{
					s.close();
				}
			}
			finally{
				conn.close();
			}
			File src = new File("wxobservations.csv");
			CsvReader reader = new CsvReader("wxobservations.csv");
			reader.readHeaders();
			String[] headers = reader.getHeaders();
			while(reader.readRecord()){
				String cur = reader.get("Time (UTC)");
				
			}
			
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void update(){
		try{
			URL website = new URL("http://weather.gladstonefamily.net/cgi-bin/wxobservations.pl?site=AS221");
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		    FileOutputStream fos = new FileOutputStream("wxobservations.csv");
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    fos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
