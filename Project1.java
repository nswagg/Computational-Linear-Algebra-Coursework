import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Nick Waggoner
 * Computational Linear Algebra
 * 9/12/2020
 * 
 * Description
 * 	This program is designed for basic matrix manipulation. It includes 
 * 	File I/O to instanciate two matrices from a text file and print them
 * 	to their own respective files. The program will also perform a 
 * 	subtraction calculation of the matrices after multiplying each matrix
 * 	by its respective coefficient and prints the result to a new file.
 * 	Lastly, there is a method which will transpose a matrix, and we test 
 * 	it on the SecondMatrix or Y matrix and write it to a file.
 * 
 * Tags: file i/o, matrix, matrix manipulation, 3d vectors, transpose
 */

public class WaggonerNicholasProject1 {

	public static void main(String[] args) throws IOException {
		
		File matrixFile = new File("matrixFile.txt");
		Scanner fileScanner = new Scanner(matrixFile);	
		
		FileWriter fileWrite1 = new FileWriter("FirstMatrix.txt", true);
		FileWriter fileWrite2 = new FileWriter("SecondMatrix.txt", true);
		FileWriter fileWriteC = new FileWriter("CalcMatrix.txt", true);
		FileWriter fileWriteT = new FileWriter("TransposedMatrix.txt", true);
		
		//checks that there are more lines - designed to skip the initial
		//free space in the matrixFile.txt file
		String name = fileScanner.next();
		if(name == "" && fileScanner.hasNext()) {
			name = fileScanner.next();
		}
		
		int numRows = fileScanner.nextInt();
		int numCols = fileScanner.nextInt();
		
		//initialize matrix X and filling it with values
		Matrix matrixX = new Matrix(name, numRows, numCols);
		fillMat(matrixX, fileScanner);
		
		name = fileScanner.next();
		numRows = fileScanner.nextInt();
		numCols = fileScanner.nextInt();
		
		//initialize matrix Y and filling it with values
		Matrix matrixY = new Matrix(name, numRows, numCols);
		fillMat(matrixY, fileScanner);
		
		printToFile(matrixX,fileWrite1);
		printToMonitor(matrixX);
		printToFile(matrixY, fileWrite2);
		printToMonitor(matrixY);
		
		//initialize calcMatrix matrix
		Matrix calcMatrix = calculate(matrixX, matrixY);
		
		printToFile(calcMatrix,fileWriteC);
		printToMonitor(calcMatrix);

		//initialize transposed matrix
		Matrix transpose = transpose(matrixY);
		
		printToFile(transpose,fileWriteT);
		printToMonitor(transpose);
		
		//have to close writers and readers to prevent leaking
		fileScanner.close();
		fileWrite1.close();
		fileWrite2.close();
		fileWriteC.close();
		fileWriteT.close();
		
	}//main
	
	public static void fillMat(Matrix matrix, Scanner fileScanner) {
		for(int i = 0; i < matrix.getLength(); i++) {
			for(int j = 0; j < matrix.getHeight(); j++) {
				int value = fileScanner.nextInt();
				matrix.addVal(i, j, value);		
			}
		}
	}
	
	public static void printToFile(Matrix matrix, FileWriter writer) throws IOException {
		//designed to read every number in the 2D array and write it to the correct file
		writer.write(matrix.getName() +  " matrix\n");
		//iterator
		for(int x = 0; x < matrix.getLength(); x++) {
			for(int y = 0; y < matrix.getHeight(); y++) {
				String num = String.valueOf(matrix.getVal(x, y));
				writer.write(num + "\t");
			}
			writer.write("\n");
		}
	}//printToFile
	
	public static void printToMonitor(Matrix matrix) {
		//method used to print an integer matrix to the monitor or stdout
		
		//header
		System.out.printf("\t\tPrinting %s matrix:\n", matrix.getName());
		System.out.println("--------------------------------------------");
		
		//iterator
		for(int a = 0; a < matrix.getLength(); a++) {
			for(int b = 0; b < matrix.getHeight(); b++) {
				System.out.printf("%d\t", matrix.getVal(a,b));
			}
			System.out.println("");
		}
		//footer
		System.out.println("\n\t\tMatrix complete\n");
	}//printToMonitor
	
	
	public static Matrix calculate(Matrix matrixX, Matrix matrixY) {
		//method used to perform matrix caluculation using the coefficients given
		double coefB = 1.5;
		double coefA = 2.5;
		Matrix calcMatrix = null;
		
		//looking to perform calculation 1.5B - 2.5A where matrix X is A
		//so 1.5(Y) - 2.5(X)
		//and return the evaluated calcMatrix matrix to print to a file
		
		if(matrixX.getLength() == matrixY.getLength() && matrixX.getHeight() == matrixY.getHeight()) {
			//checks to see if the matrices are the same dimensions. Doesn't work otherwise.
			
			calcMatrix = new Matrix("Calculated", matrixX.getLength(), matrixX.getHeight());
			
			for(int rowCalc = 0; rowCalc < matrixX.getLength(); rowCalc++) {
				for(int colCalc = 0; colCalc < matrixX.getHeight(); colCalc++) {
					
					double xCalc = matrixX.getVal(rowCalc, colCalc)*coefA;
					double yCalc = matrixY.getVal(rowCalc, colCalc)*coefB;
					
					//calcMatrix can be an integer in this case because
					//1.5Y - 2.5X will always yield a whole integer
					
					calcMatrix.addVal(rowCalc, colCalc, (int)(yCalc-xCalc));
				}
			}
		}

		return calcMatrix;
		
	}//calculate
	
	public static Matrix transpose(Matrix matrix) {
		//this method "inverts" a matrix (transposes it) where is
		//essentially flips the row and column order of the 2D array
		Matrix transpose = new Matrix("T", matrix.getHeight(), matrix.getLength());
		
		for(int h = 0; h < matrix.getHeight(); h++) {
			for(int l = 0; l < matrix.getLength(); l++) {
				transpose.addVal(h, l, matrix.getVal(l, h));
			}
		}		
		
		return transpose;
	}//transpose
	
}//project1

class Matrix {
	/*
	 * This object class was not in my prototype design but was later
	 * changed to an object class in order to store the name of the
	 * matrices alongside their data. Also helped clean up some of
	 * the methods in main and make them more applicable and general
	 */
	
	private int[][] matrix;
	private String name;
	private int val;
	
	public Matrix() {}
	public Matrix(String name, int numRows, int numCols) {
		this.name = name;
		matrix = new int[numRows][numCols];
	}
	
	public String getName() {
		return this.name;
	}//getName
	
	public int getLength() {
		return this.matrix.length;
	}//getLength
	
	public int getHeight() {
		return this.matrix[0].length;
	}
 	
	public int getVal(int row, int column) {
		return matrix[row][column];
	}//getVal
	
	public void addVal(int row, int column, int value) {
		this.matrix[row][column] = value;
	}//addVal
	

}//Matrix class
