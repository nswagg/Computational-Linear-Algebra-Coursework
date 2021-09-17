
/**
 * @author Nick Waggoner
 * Computational Linear Algebra
 * Due Nov 13, 2020
 * 
 * Description:
 * 		This program will calculate whether a candidate vector is in the subspace created
 * by the basis vector by calculating the cross-product. If the cross-product is 0 or close to 
 * zero after augmenting the matrices, the candidate vector is revealed to be in the subspace.
 * 
 * Tags: vector, cross product, augment, matrix
 * 
 */

public class Project3 {

	public static void main(String[] args) {
		
		
		//It was stated during class that the "hardcoding" of variables into the program
		//was acceptable to save time and as FILE I/O was commonplace. This block
		//instantiates our variables. 
		
		//These are the 2x3 matrices we are testing
		double [][] bset1 = { {1, 0}, {0, 1}, {1, 0} };
		double [][] bset2 = { {6.9, 0}, {0, -3.2}, {0, 0} };
		double [][] bset3 = { {-3, 0}, {3, 1}, {4, 0} };
		double [][] bset4 = { {-3, 1}, {3, 1}, {4, 1} };
		
		//These are the respective test candidate vectors
		double [] cvec1 = {15, -10, 15};
		double [] cvec2 = {1.5, -6.2, 0.37};
		double [] cvec3 = {-6, 4, -8};
		double [] cvec4 = {1, 7, 8};
		
		/*The general form for our check is that each basis vector will generate an equation
		 * in the form a1*i + a2*j = c 
		 * We are checking whether there is an i and j that exist to provide c. 
		 * To do so, we will be augmenting the inputed matrices with the test vector
		 * and taking the determinant. If the determinant is zero, the matrix is linearly
		 * dependent and the test vector is not in the span of the basis vector
		*/
		System.out.printf("Running...\n");
		test(bset1, cvec1);
		test(bset2, cvec2);
		test(bset3, cvec3);
		test(bset4, cvec4);
		
		
		System.out.printf("\n\nCompleted");
		
	}//main

	public static double[][] augment(double [][] basis, double [] test) {
		
		//augments the basis vectors and the candidate vector into one 3x3 matrix		
		double [][] augment = new double[3][3];
		
		for(int i = 0; i< 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(j == 2) {
					augment[i][j] = test[i];
				}
				else {
					augment[i][j] = basis[i][j];
				}
			}
		}
		
		return augment;
	}//augment
	
	public static double determinant(double [][] matrix) {
		
		//calculates the determinant of the augmented matrix to find the cross product of the augment
		double deter0 = matrix[0][0] * (matrix[1][1]*matrix[2][2] - matrix[1][2]*matrix[2][1]);
		double deter1 = matrix[0][1] * (matrix[1][0]*matrix[2][2] - matrix[1][2]*matrix[2][0]);
		double deter2 = matrix[0][2] * (matrix[1][0]*matrix[2][1] - matrix[1][1]*matrix[2][0]);
			
		double determinant = deter0 - deter1 + deter2;
		
		return determinant;
	}//determinant
 
	
	public static void test(double[][] basis, double[] candidate) {
		//runs the entire process for the basis vector and candidate vector
		//augmenting the matrix and finding the cross-product to determine linearity
		
		double[][] augment = augment(basis, candidate);
		double det = determinant(augment);
		
		if(det != 0) {
			System.out.printf("\nNo, the vector <%.2f, %.2f %.2f> is not in the subspace spanned by\n", candidate[0], candidate[1], candidate[2]);
			
		}
		else {
			System.out.printf("\nYes, the vector <%.2f, %.2f %.2f> is in the subspace spanned by\n", candidate[0], candidate[1], candidate[2]);
		}
		
		
		for(int i = 0; i < 3; i++ ){
			System.out.printf("< ");
			
			for(int j = 0; j < 2; j++) {
				System.out.printf("%.2f ", basis[i][j]);
			}
			
			System.out.printf("> ");
		}
		System.out.printf("\n");
	}//test
}//Project3
