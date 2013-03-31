import java.sql.ResultSet;
import java.sql.SQLException;


public class Predictor {
	
	public final int NUM = 6, AHEAD = 30;
	public final int[] year = {31,28,31,30,31,30,31,31,30,31,30,31};
	DataSet ds;
	
	public long getSum(int n){
		int ret=0;
		for(int i=0;i<n-2;i++)
			ret+=year[i];
		return ret;
	}
	
	//conversion from year to number
	public long convert(String input){
		String[] part1 = input.split(" ")[0].split("-");
		String[] part2 = input.split(" ")[1].split(":");
		return Integer.parseInt(part1[0])*365*24*60 + getSum(Integer.parseInt(part1[1]))*24*60
				+ Integer.parseInt(part1[2])*24*60 + Integer.parseInt(part2[0])*60 + Integer.parseInt(part2[1]);
	}
	
	public double[][] extract(String column){
		//Initializing, counting rows
		int count = 0;
		double[][] temp = new double[NUM][2];
		try{
			ds = new DataSet();
			ResultSet du = ds.executeQry("SELECT COUNT(*) AS rowcount FROM data");
			du.next();
			count = du.getInt("rowcount") ;
			du.close() ;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		count-=NUM;
		ResultSet trs = null,prs = null;
		try{
			// Get times.
			trs = ds.executeQry("SELECT time FROM data " + "LIMIT " + NUM +" OFFSET " + count);
			String[] rawtime = new String[NUM];
			while(trs.next()){
				rawtime[trs.getRow()-1] = trs.getString(1);
			}
			temp[0][0]=0;
			long mintime = convert(rawtime[0]);
			for(int i=1;i<NUM;i++){
				temp[i][0] = convert(rawtime[i])-mintime;
			}
			//Get data.
			prs = ds.executeQry("SELECT " + column +" FROM data " + "LIMIT " + NUM + " OFFSET " + count);
			while(prs.next()){
				temp[prs.getRow()-1][1]=prs.getDouble(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				ds.closeConnection();
				trs.close();
				prs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

}
