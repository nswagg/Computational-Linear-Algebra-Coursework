/**
 * @author Nick Waggoner
 * Computational Linear Algebra
 * Due Dec 4, 2020
 * This is the first rendition. There is a shorter, cleaner version 4-1 in the Repo
 * Description:
 * 	This program computes where given 3D points intersect an image plane with a given focal length.
 * 	The program is also designed to rotate a set of points about a plane and perform similar calculations.
 * 
 * Tags: plane, focal, rotate, homogeneous, multiply matrix, coordinate
 */


@SuppressWarnings("unused")
public class Project4 {

	public static void main(String[] args) {
		
		//list of test points
		double[][] pt0 = {{ 10, 10, 10}}; 
		double[][] pt1 = {{-10, 10, 10}};
		double[][] pt2 = {{ 10,-10, 10}}; 
		double[][] pt3 = {{-10,-10, 10}};
		
		//converts points to homogeneous points
		double[][] pt0ho = homogeneous(pt0);
		double[][] pt1ho = homogeneous(pt1);
		double[][] pt2ho = homogeneous(pt2);
		double[][] pt3ho = homogeneous(pt3);
		
		//list of focal lengths for testing
		int f1 = 1;
		int f5 = 5;
		int f2 = 2;
		double rotate = Math.PI/4; //rotation value
		
		//sets up focal lengths into a 3x4 matrix to find the coordinate points
		double[][] focal1 = focalMat(f1);
		double[][] focal5 = focalMat(f5);
		double[][] focal2 = focalMat(f2);
		
		
		//MAIN FUNCTION RUN
		//Sets up each point into a matrix, reduces them, multiplies them by the focal matrix to 
		//give us our image coordinates. The final method will rotate the 3D point and find the 
		//image coordinate with the given focal length.
		// "homopt#" denotes a homogeneous point
		// "pt#coord" denotes the image coordinate points
		// "pt#" is the given point
		// "rt#" is the corresponding rotation matrix using PI/4 in this example
		
		double[][] homopt0 = multiplyMatrices( focal1, pt0ho);
		double[][] pt0coord = coordinate(homopt0);
		StringOut(pt0, f1, pt0coord);
		homopt0 = multiplyMatrices(focal5, pt0ho);
		pt0coord = coordinate(homopt0);
		StringOut(pt0, f5, pt0coord);
		double[][] rt0 = rotation(pt0ho, rotate);
		homopt0 = multiplyMatrices(focal2, rt0);
		pt0coord = coordinate(homopt0);
		RotateOut(pt0, f2, pt0coord);
		
		System.out.println();
		
		double[][] homopt1 = multiplyMatrices( focal1, pt1ho);
		double[][] pt1coord = coordinate(homopt1);
		StringOut(pt1, f1, pt1coord);
		homopt1 = multiplyMatrices(focal5, pt1ho);
		pt1coord = coordinate(homopt1);
		StringOut(pt1, f5, pt1coord);
		double[][] rt1 = rotation(pt1ho, rotate);
		homopt1 = multiplyMatrices(focal2, rt1);
		pt1coord = coordinate(homopt1);
		RotateOut(pt1, f2, pt1coord);
		
		System.out.println();
		
		double[][] homopt2 = multiplyMatrices( focal1, pt2ho);
		double[][] pt2coord = coordinate(homopt2);
		StringOut(pt2, f1, pt2coord);
		homopt2 = multiplyMatrices(focal5, pt2ho);
		pt2coord = coordinate(homopt2);
		StringOut(pt2, f5, pt2coord);
		double[][] rt2 = rotation(pt2ho, rotate);
		homopt2 = multiplyMatrices(focal2, rt2);
		pt2coord = coordinate(homopt2);
		RotateOut(pt2, f2, pt2coord);
		
		System.out.println();		
		
		double[][] homopt3 = multiplyMatrices( focal1, pt3ho);
		double[][] pt3coord = coordinate(homopt3);
		StringOut(pt3, f1, pt3coord);
		homopt3 = multiplyMatrices(focal5, pt3ho);
		pt3coord = coordinate(homopt3);
		StringOut(pt3, f5, pt3coord);
		double[][] rt3 = rotation(pt3ho, rotate);
		homopt3 = multiplyMatrices(focal2, rt3);
		pt3coord = coordinate(homopt3);
		RotateOut(pt3, f2, pt3coord);
		
	}//main

	public static double[][] homogeneous(double[][] pt0) {
		//creates the homogeneous points and inputs them into a matrix
		double[][] newMat = {{ pt0[0][0] },
				{ pt0[0][1] },
				{ pt0[0][2] },
				{ 1 }};
		
		return newMat;
		
	}//homogeneous
	
	public static double[][] focalMat (int focal) {
		//appends the focal point into a matrix
		double[][] newMat = 
				{{focal, 0, 0, 0},
				{0, focal, 0, 0},
				{0, 0, 1, 0}};
		
		return newMat;
	}//focalMat
	
	public static double[][] coordinate (double[][] threeD) {
		//divides each element in the 3D point to provide the corresponding coordinate points
		//sets up normalized coordinate points in form [ fx/z, fy/z, 1 ]
		double first = threeD[0][0] /threeD[2][0];
		double second = threeD[1][0]/threeD[2][0];
		double third = threeD[2][0] /threeD[2][0];
		
		double[][] coordinate = {{first},{second}, {third}};

		return coordinate;
		
	}//coordinate
	
	public static double[][] rotation(double[][] pt, double rotate) {
		//multiplies times the homogeneous points
		double[][] rotation = {
				{Math.cos(rotate), (-1)*Math.sin(rotate), 0, 0},
				{Math.sin(rotate), Math.cos(rotate), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}};

		//multiplies matrices to create rotated point
		double[][] result = multiplyMatrices(rotation, pt);
		return result;
	}//rotation
	

	public static double[][] multiplyMatrices(double[][] mat1, double[][] mat2) {
		//multiplies two matrices as long as the lengths are appropriate
		int r1 = mat1.length;
		int c1 = mat1[0].length;
		int r2 = mat2.length;
		int c2 = mat2[0].length;
		
		if(c1 == r2) {
			//makes sure the matrices are the correct length before multiplication
			double[][] product = new double[r1][c2];
    		for(int i = 0; i < r1; i++) {
        		for (int j = 0; j < c2; j++) {
            		for (int k = 0; k < c1; k++) {
                		product[i][j] += mat1[i][k] * mat2[k][j];
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
    	System.out.printf("Image Projection of point [%.0f,%.0f,%.0f] with focal length %d is [%.2f,%.2f].\n",pt[0][0], pt[0][1], pt[0][2], focal, coords[0][0], coords[1][0]);
    }
    
    public static void RotateOut(double[][] pt, int focal, double[][] coords) {
    	System.out.printf("Image Projection of point [%.0f,%.0f,%.0f] rotated PI/4 radians with focal length %d is [%.2f,%.2f].\n",pt[0][0], pt[0][1], pt[0][2], focal, coords[0][0], coords[1][0]);
    }
}//Project4
