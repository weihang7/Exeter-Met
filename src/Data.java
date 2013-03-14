import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.csvreader.CsvReader;

public class Data {
	
	public static void main(String[] args){
		update();
		try{
			DataSet ds = new DataSet();
			String sMakeInsert = "INSERT INTO data VALUES(";
			String time = "";
			
			CsvReader reader = new CsvReader("wxobservations.csv");
			reader.readHeaders();
			boolean flag=false;
			while(reader.readRecord()){
				String cur = reader.get("Time (UTC)");
				if(flag){
					System.out.print(".");
					sMakeInsert += "\"" + cur + "\",";
					for(int i = 1; i < 7 ; i++){
						sMakeInsert += reader.get(i);
						sMakeInsert += (i==6?"":",");
					}
					sMakeInsert+=")";
					ds.executeStmt(sMakeInsert);
					sMakeInsert = "INSERT INTO data VALUES(";
				}
				if(time==cur){
					flag=true;
					System.out.println("Adding entries");
				}
			}
			ds.closeConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void update(){
		try{
			System.out.println("Fetching");
			long startTime = System.nanoTime();
			URL website = new URL("http://weather.gladstonefamily.net/cgi-bin/wxobservations.pl?site=AS221");
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		    FileOutputStream fos = new FileOutputStream("wxobservations.csv");
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    fos.close();
		    System.out.println("Finished fetching");
		    long endTime = System.nanoTime();
		    long duration = endTime - startTime;
		    File f = new File("wxobservations.csv");
		    double speed=(f.length()/1024.0)/(duration/1000000000);
		    System.out.println("Speed is "+speed+"kb/s");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
