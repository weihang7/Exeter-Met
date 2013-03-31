public class PolynomialPredictor extends Predictor{
	
	PolynomialFitter pf;
	
	public PolynomialPredictor(int degree) {
		pf = new PolynomialFitter(degree);
	}
	
	public Double predict(String column){
		//Initializing
		Double ret = 0.0;
		double[][] temp = extract(column);
		//Adding data and predicting
		for(int i=0;i<NUM;i++){
			pf.addPoint(temp[i][0],temp[i][1]);
		}
		ret=pf.getBestFit().getY(temp[NUM-1][0] + AHEAD);
		return ret;
	}

}
