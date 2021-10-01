/**
 * @author Nick Waggoner
 * CS 2300 - Computational Linear Algebra
 * Due Dec 4, 2020
 * 
 * Description:
 * 	This program computes where given 3D points intersect an image plane with a given focal length.
 * 	The program is also designed to rotate a set of points about a plane and perform similar calculations.
 * 
 * Tags: plane, focal, rotate, homogeneous, multiply matrix, coordinate
 */


@SuppressWarnings("unused")
public class Project4 {

	public static void main(String[] args) {
		
		//Test points
		//Note: We could easily set this up for File I/O to scan the test points and focal lengths,
		// but it was not expected for this assignment. 
		double[][] pt0 = {{ 10, 10, 10}}; 
		double[][] pt1 = {{-10, 10, 10}};
		double[][] pt2 = {{ 10,-10, 10}}; 
		double[][] pt3 = {{-10,-10, 10}};
		
		//Focal lengths for testing
		int f1 = 1;
		int f5 = 5;
		int f2 = 2;
		//List of Focal Lengths
		int[] fLengths = {f1, f5, f2};
		
		//Rotation value
		double rotate = Math.PI/4;

		mainHelper(pt0, fLengths, rotate);
		mainHelper(pt1, fLengths, rotate);
		mainHelper(pt2, fLengths, rotate);
		mainHelper(pt3, fLengths, rotate);
		
	}//main
	
	/****************************************************************************************
	//MAIN FUNCTION RUN
	/ Sets up each point into a matrix, reduces them, multiplies them by the focal matrix to 
	/ give us our image coordinates. The final method will rotate the 3D point and find the 
	/ image coordinate with the given focal length.
	/
	/ "hom" denotes a homogeneous point
	/ "coordPt" denotes the image coordinate points
	/ "testPoint" is the given point for testing
	/ "rotation" is the corresponding rotation matrix using PI/4 in this example
	/****************************************************************************************/
	public static void mainHelper(double[][] testPt, int[] fLengths, double rotateVal ) {
		double[][] hom = homogeneous(testPt);	//Converts point to Homogeneous point
		
		for(int i = 0; i < fLengths.length; i++) {
			double [][] focalMat = focalMat(fLengths[i]);	//Sets up focal lengths into a 3x4 matrix to find the coordinate points
			
			//Checking for the specific rotation matrix. We need the rotate on the 3rd entity, so check fLengths[2]
			if(i == 2) { 
				double[][] homPt = multiplyMatrices(focalMat, rotation(hom, rotateVal));//Captures the Homogeneous point multiplication for the special rotation case
				double[][] coordPt = coordinate(homPt);		//Captures the Image's coordinate points
				RotateOut(testPt, fLengths[i], coordPt);	//Prints out the Image Projection
			}
			
			//Every other case. Not great to check every time, but uses less code than hard-coding the full block. More reusable.
			else {
				double[][] homPt = multiplyMatrices(focalMat, hom);//Captures the Homogeneous point multiplication
				double[][] coordPt = coordinate(homPt);		//Captures the Image's coordinate points
				StringOut(testPt, fLengths[i], coordPt);	//Prints out the Image Projection
			}
		}
		
		System.out.println();
	}//mainHelper

	public static double[][] homogeneous(double[][] pt) {
		//Creates the homogeneous points and inputs them into a matrix
		//Converts the given test point to a Homogeneous point
		double[][] newMat = {{ pt[0][0] },
				{ pt[0][1] },
				{ pt[0][2] },
				{ 1 }};
		
		return newMat;
		
	}//homogeneous
	
	public static double[][] focalMat (int focal) {
		//Sets up focal lengths into a 3x4 matrix to find the coordinate points
		//Appends the focal point into a matrix
		double[][] newMat = 
				{{focal, 0, 0, 0},
				{0, focal, 0, 0},
				{0, 0, 1, 0}};
		
		return newMat;
	}//focalMat
	
	public static double[][] coordinate (double[][] threeD) {
		//Divides each element in the 3D point to provide the corresponding coordinate points
		//Sets up Normalized coordinate points in form [ fx/z, fy/z, 1 ]
		double first =  threeD[0][0]/threeD[2][0];
		double second = threeD[1][0]/threeD[2][0];
		double third =  threeD[2][0]/threeD[2][0];
		
		double[][] coordinate = {{first},{second}, {third}};

		return coordinate;
		
	}//coordinate
	
	public static double[][] rotation(double[][] pt, double rotate) {
		//Multiplies by the homogeneous points
		double[][] rotation = {
				{Math.cos(rotate), (-1)*Math.sin(rotate), 0, 0},
				{Math.sin(rotate), Math.cos(rotate), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}};

		//Multiplies matrices to demonstrate a rotation about a point
		double[][] result = multiplyMatrices(rotation, pt);
		return result;
	}//rotation
	

	public static double[][] multiplyMatrices(double[][] mat1, double[][] mat2) {
		//Multiplies two matrices as long as the inputs are valid
		int r1 = mat1.length;
		int c1 = mat1[0].length;
		int r2 = mat2.length;
		int c2 = mat2[0].length;
		
		if(c1 == r2) {
			//Confirms the matrices are the correct dimension before multiplication
			double[][] product = new double[r1][c2];
			//This loop will be going FOR a while...
    		for(int i = 0; i < r1; i++) {
        		for (int j = 0; j < c2; j++) {
            		for (int k = 0; k < c1; k++) {
                		product[i][j] += mat1[i][k] * mat2[k][j];	//Matrix Multiplication is weird...
            		}
        		}
    		}
    		
    		return product;
		}

		else {
			System.out.print("ERROR; THESE MATRICES CANNOT BE MULTIPLIED");
		}
		return null;
		
	}//multiplyMatrices

        public static void StringOut(double[][] pt, int focal, double[][] coords) {
    	System.out.printf("Image Projection of point "
    			+ "[%.0f,%.0f,%.0f] with focal length %d is [%.2f,%.2f].\n",
    			pt[0][0], pt[0][1], pt[0][2], focal, coords[0][0], coords[1][0]);
    }//StringOut
    
    public static void RotateOut(double[][] pt, int focal, double[][] coords) {
    	System.out.printf("Image Projection of point "
    			+ "[%.0f,%.0f,%.0f] rotated PI/4 radians with focal length %d is [%.2f,%.2f].\n",
    			pt[0][0], pt[0][1], pt[0][2], focal, coords[0][0], coords[1][0]);
    }//RotateOut
}//Project4
