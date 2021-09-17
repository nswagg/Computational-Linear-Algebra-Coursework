
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

/**
 * @author Nick Waggoner
 * Computational Linear Algebra
 * Due Oct 3, 2020
 * 
 * Description:
 *	FILE IO to read through a text file and creating a line in the appropriate form; either implicit or parametric.
 *	From there, sent data to a converter which translated and outputs the data in both forms and also finds the 
 *	point-normal of the equation. Using the implicit form, we can calculate the distance from any given point to
 *	the line, which is outputted as well, with a special case if the point is on the line.
 *
 * Tags: file i/o, implicit, parametric, point normal
 */
public class Project2 {

	public static void main(String[] args) throws IOException {
		
		int count = 0;
		String name = "";
		//creates a file object to access text file
		
		File file = new File("CS2300P2Windows.txt");
		//creates fileRead object to read file
		Scanner fileRead = new Scanner(file);		
		
		while(fileRead.hasNextLine()) {
			
			//checks for blank lines and skips line in .txt file if so.
			if(fileRead.hasNext()) {
				name = fileRead.next();
				
				if(name.equals("i")) { //implicit case
					impProcess(file, fileRead);
				}
				else if(name.equals("p")) {  //parametric case
					paramProcess(file, fileRead);
				}
			}
			else {
				//in the event that the blank line comes first, this will navigate out of it.
				fileRead.nextLine();
			}
			
			//resets name for further checking
			name = "";
		}
		
		//close the scanner - it's good practice
		fileRead.close();
		System.out.println("\n\n\nEnd of Program; Scanner closed");
		
	}//main
	
	public static void impProcess(File file, Scanner fileRead) {
		//method designed for handling back-end of the implicit case (when the data is formatted for implicit equation
		System.out.printf("\n\n%s", "This set is in implicit form");
		double a = fileRead.nextDouble();
		double b = fileRead.nextDouble();
		double c = fileRead.nextDouble();
		
		double p1;
		double p2;
		double v1 = b;
		double v2 = a * (-1);
		
		double norm = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		
		if(a > b) {
			p1 = -c/a;
			p2 = 0;
		}
		else {
			p1 = 0;
			p2 = -c/b;
		}
		
		System.out.print("\nImplicit Form: ");
		System.out.printf("%.2f" + "x1 + %.2f" + "x2 + (%.2f) = 0", a, b, c);
		System.out.print("\nParametric Form: ");
		System.out.printf("l(t) = [%.2f, %.2f] + t[%.2f, %.2f]", p1, p2, v1, v2);
		System.out.print("\nPoint-normal Form: ");
		//finds point-normal by dividing each variable by the normal
		System.out.printf("%.2f" + "a + %.2f" +"b + (%.2f) = 0", a/norm, b/norm, c/norm);
		
		while(fileRead.hasNextDouble()) { //plugs numbers in to find distances
			//Calculates the distance from the point to the line and announces if it is on the line
			double r1 = fileRead.nextDouble();
			double r2 = fileRead.nextDouble();
			
			double distance = ((a*r1)+(b*r2) + c)/(norm);
			System.out.printf("\nDistance from [%.2f, %.2f] to the line is %.2f.", r1, r2, distance);
			if(distance == 0.0) {
				System.out.print(" The point is on the line.");
			}			
		}
	}//impProcess
	

	
	public static void paramProcess( File file, Scanner fileRead) {
		//method designed for handling back-end of the parametric case (when the data is formatted for implicit equation
		System.out.printf("\n\n%s", "This set is in parametric form");
		double p1 = fileRead.nextDouble();
		double p2 = fileRead.nextDouble();
		double v1 = fileRead.nextDouble();
		double v2 = fileRead.nextDouble();
		
		//doing these conversions before the print statement allowed for consistent looking output and made calculating
		//the point-normal form easier when going from implicit
		double a = -v2;
		double b = v1;
		double c = -(p1*v1 + p2*v2);
		
		double norm = Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2)));
		
		
		//printing outputs
		System.out.print("\nImplicit Form: ");
		System.out.printf("%.2f" + "x1 + %.2f" + "x2 + (%.2f) = 0", a, b, c);
		System.out.print("\nParametric Form: ");
		System.out.printf("l(t) = [%.2f, %.2f] + t[%.2f, %.2f]", p1, p2, v1, v2);
		System.out.print("\nPoint-normal Form: ");
		//finds point-normal by dividing each variable by the normal
		System.out.printf("%.2f" + "a + %.2f" +"b + (%.2f) = 0", a/norm, b/norm, c/norm);
		System.out.printf("");
		
		while(fileRead.hasNextDouble()) { //plugs numbers in to find distances
			//Calculates the distance from the point to the line and announces if it is on the line
			double r1 = fileRead.nextDouble();
			double r2 = fileRead.nextDouble();
			
			double distance = ((a*r1)+(b*r2) + c)/(norm);
			System.out.printf("\nDistance from [%.2f, %.2f] to the line is %.2f.", r1, r2, distance);
			if(distance == 0.0) {
				System.out.print("The point is on the line.");
			}
			
		}
		
	}//paramProcess
	
}//Project2
