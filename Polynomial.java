import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;



public class Polynomial{
	double [] coefficients;
	int [] exponents;
	
	public Polynomial(){
		coefficients = new double[0];
		exponents = new int[0];
		
	}

	public Polynomial(double [] cof_vals, int [] exp_vals){
		coefficients = new double[cof_vals.length];
		exponents = new int[exp_vals.length];
		for(int x = 0; x<cof_vals.length; x++){
			coefficients[x] = cof_vals[x];
			exponents[x] = exp_vals[x];
		}
	}

    public Polynomial(File file){
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + file.getPath());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                coefficients = new double[0];
                exponents = new int[0];
                return;
            }

            String[] terms = line.split("(?=[+-])");
            coefficients = new double[terms.length];
            exponents = new int[terms.length];

            int index = 0;
            for (String term : terms) {
                term = term.trim(); // remove any leading/trailing spaces

                if (term.contains("x")) {
                    String[] miniterm = term.split("x");
                    String cof;
                    if (miniterm[0].isEmpty() || miniterm[0].equals("+")) {
                        cof = "1";
                    } else if (miniterm[0].equals("-")) {
                        cof = "-1";
                    } else {
                        cof = miniterm[0];
                    }
                    coefficients[index] = Double.parseDouble(cof);

                    if (miniterm.length == 1 || miniterm[1].isEmpty()) {
                        exponents[index] = 1;
                    } else {
                        exponents[index] = Integer.parseInt(miniterm[1]);
                    }
                } else {
                    coefficients[index] = Double.parseDouble(term);
                    exponents[index] = 0;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getPath());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getPath());
            e.printStackTrace();
        }
    }

	public Polynomial add(Polynomial poly){

		double[] testcof = new double[coefficients.length+poly.coefficients.length];
		int[] testexp = new int[exponents.length+poly.exponents.length];

		int index1 = 0;
		int index2 = 0;
		int arr_index = 0;

		while((index1<coefficients.length) || (index2<poly.coefficients.length)){

			if(index1>=coefficients.length){
				testexp[arr_index] = poly.exponents[index2];
				testcof[arr_index] = poly.coefficients[index2];
				index2 ++; arr_index ++;
			}
			
			else if(index2>=poly.coefficients.length){
				testexp[arr_index] = exponents[index1];
				testcof[arr_index] = coefficients[index1];
				index1 ++; arr_index ++;
			}

			else if(exponents[index1] == poly.exponents[index2]){
				testexp[arr_index] = exponents[index1];
				testcof[arr_index] = coefficients[index1]+poly.coefficients[index2];
				index1 ++; index2 ++; arr_index ++;
			}

			else if(exponents[index1] > poly.exponents[index2]){
				testexp[arr_index] = poly.exponents[index2];
				testcof[arr_index] = poly.coefficients[index2];
				index2 ++; arr_index ++;
			}

			else{
				testexp[arr_index] = exponents[index1];
				testcof[arr_index] = coefficients[index1];
				index1 ++; arr_index ++;
			}

		}

		int final_length = 0;
		for (double num : testcof) {
			if(num != 0.0){
				final_length++;
			}
		}

		double[] sumcof = new double[final_length];
		int[] sumexp = new int[final_length];


		int pos = 0;
		for (int i = 0; i < arr_index; i++){
			if(testcof[i] != 0.0){
				sumcof[pos] = testcof[i];
				sumexp[pos] = testexp[i];
				pos++;
			}
		}

		Polynomial added = new Polynomial(sumcof,sumexp);
		return added;

	}

	public double evaluate(double x){
		double eval = 0;
		for(int y = 0; y < coefficients.length; y++){
			eval += coefficients[y]*Math.pow(x,exponents[y]);

		}
		return eval;
	}
	
	public boolean hasRoot(double x){
		return(evaluate(x) == 0);
	}

	private int max_exp(int [] exp){
		int max = 0;
		for (int i = 0; i < exp.length; i++){
			if (exp[i] > max){
				max = exp[i];
        	}
    	}
    	return max;
	}

	private int[] exponent_array(Polynomial poly){
		int max1 = max_exp(exponents);
		int max2 = max_exp(poly.exponents);
		int max_length = max1+max2+1;

		int[] max_arr = new int[max_length];

		for(int exp1:exponents){
			for(int exp2:poly.exponents){
				max_arr[exp1+exp2] = 1;
			}
		}

		int total = 0;
		for(int num:max_arr){
			total += num;
		}

		int[] final_arr = new int[total];
		int index = 0;
		for(int i = 0; i < max_arr.length; i++){
			if(max_arr[i] != 0){
				final_arr[index] = i;
				index++;
 			}
		}

		return final_arr;
	}

	public Polynomial multiply(Polynomial poly){
		int[] prod_expo = exponent_array(poly);
		double[] prod_cof = new double[prod_expo.length];

		double[][] cof_matrix = new double[coefficients.length][poly.coefficients.length];
		int[][] exp_matrix = new int[exponents.length][poly.exponents.length];

		for(int x = 0; x<coefficients.length;x++){
			for(int y = 0; y<poly.coefficients.length;y++){
				cof_matrix[x][y] = coefficients[x]*poly.coefficients[y];
				exp_matrix[x][y] = exponents[x] + poly.exponents[y];
			}
		}

		for(int i = 0; i<prod_expo.length; i++){
			int expo = prod_expo[i];
			double cof = 0;
			for(int x = 0; x<coefficients.length;x++){
				for(int y = 0; y<poly.coefficients.length;y++){
					if(exp_matrix[x][y] == expo){
						cof += cof_matrix[x][y];
					}
				}
			}
			prod_cof[i] = cof;
		}

		Polynomial product = new Polynomial(prod_cof,prod_expo);
		return product;
	}

	public void saveToFile(String file){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String expression = "";
			for(int x = 0; x < coefficients.length; x++){
				expression = expression+String.valueOf(coefficients[x]);
				if(exponents[x]!=0){
					expression += "x"+String.valueOf(exponents[x]);
				}
			}
			bw.write(expression);
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}