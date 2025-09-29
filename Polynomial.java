public class Polynomial{
	double [] coefficients;
	
	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
	}

	public Polynomial(double [] values){
		coefficients = new double[values.length];
		for(int x = 0; x<values.length; x++){
			coefficients[x] = values[x];
		}
	}

	public Polynomial add(Polynomial expression){
		double [] sum = new double[Math.max(coefficients.length,expression.coefficients.length)];

		for(int x = 0; x < Math.max(coefficients.length,expression.coefficients.length); x++){

			if(x >= coefficients.length){
				sum[x] = expression.coefficients[x];
			}

			else if(x >= expression.coefficients.length){
				sum[x] = coefficients[x];
			}

			else{
				sum[x] = coefficients[x] + expression.coefficients[x];
			}
		}


		Polynomial added = new Polynomial(sum);
		return added;
	}

	public double evaluate(double x){
		double eval = 0;
		for(int y = 0; y < coefficients.length; y++){
			eval += coefficients[y]*Math.pow(x,y);

		}
		return eval;
	}
	
	public boolean hasRoot(double x){
		return(evaluate(x) == 0);
	}
}