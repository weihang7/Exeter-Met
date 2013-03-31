
public class Aggregator {
	
	private static final String[] a = {"temp", "pres", "dpt", "rh"};
	
	public static void main(String [] args){
		for(int i=1;i<6;i++){
			System.out.print(i + ":");
			for(int j=0;j<4;j++){
				PolynomialPredictor pp = new PolynomialPredictor(i);
				System.out.print(a[j] + ':' + pp.predict(a[j]) + ' ');
			}
			System.out.println();
		}
	}
}
