import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/* Name: Srinivasan Nambi	
 * 49er ID: 800861816
 * This program accepts the folder path and filenames as input from the user and
 * calculates the normalized edit distance between two sequences and displays the output.
 */

public class NormalizedEditDistance {

	public float getEditDistance(String s1, String s2) {
		return buildEditDistance(s1, s2);
	}

	/*
	 * Class function that calculates the edit distance between two sequences
	 * using only two rows in memory and finally returns the edit distance
	 * between two sequences
	 */
	private float buildEditDistance(String s1, String s2) {
		int lenS1 = s1.length(), lenS2 = s2.length(), deletions = 0;
		float distance = 0;
		s1 = " " + s1;
		s2 = " " + s2;

		int[][] tablerows = new int[2][s2.length()];
		for (int i = 0; i < s1.length(); i++) {
			if (i == 0) {
				// Calculates the first row
				for (int j = 0; j < s2.length(); j++) {
					tablerows[i][j] = j;
				}

			} else if (i == 1) {
				// Calculates the second row
				tablerows[1][0] = tablerows[0][0] + 1;
				for (int j = 1; j < s2.length(); j++) {
					if (s1.charAt(i) == s2.charAt(j)) {
						tablerows[1][j] = tablerows[0][j - 1];
					} else {
						if (tablerows[1][j - 1] <= tablerows[0][j]) {
							tablerows[1][j] = tablerows[1][j - 1] + 1;
						} else {
							tablerows[1][j] = tablerows[0][j] + 1;
						}
					}

				}

			} else {
				// calculates row >2
				for (int j = 0; j < s2.length(); j++) {
					tablerows[0][j] = tablerows[1][j];
				}
				tablerows[1][0] = tablerows[0][0] + 1;
				for (int j = 1; j < s2.length(); j++) {
					// If a character match is found calculate the value of cell
					// by adding one to the diagonally top element
					if (s1.charAt(i) == s2.charAt(j)) {
						tablerows[1][j] = tablerows[0][j - 1];
					} else {
						// If a character match cannot be found then
						// find the minimum values of the immediate left and top
						// elements
						// Then add 1 to its value
						if (tablerows[1][j - 1] <= tablerows[0][j]) {
							tablerows[1][j] = tablerows[1][j - 1] + 1;
						} else {
							tablerows[1][j] = tablerows[0][j] + 1;
						}
					}
				}
			}
		}
		/*
		 * After computing the table the last element in the table gives the
		 * number of deletions
		 */
		deletions = tablerows[1][s2.length() - 1];
		/*
		 * Apply the formula for normalized edit distance to get the edit
		 * distance
		 */
		distance = (float) (lenS1 + lenS2 - deletions)
				/ (float) (lenS1 + lenS2);
		return distance;

	}

	public static void main(String[] args) {
		NormalizedEditDistance normalizedEditDistance = new NormalizedEditDistance();
		Scanner scan = new Scanner(System.in);
		// Gets user input i.e. folder path and filenames
		System.out.println("Enter the folder path of the input files: ");
		String directory = scan.nextLine().trim();
		System.out.println("Enter the filename1(without extension) :");
		String inputfile1 = scan.nextLine().trim();
		System.out.println("Enter the filename2(without extension) :");
		String inputfile2 = scan.nextLine().trim();
		String sequence1 = null;
		String sequence2 = null;
		// read the files from input directory.
		try {
			sequence1 = readFile(directory + "\\" + inputfile1 + ".txt");
			sequence2 = readFile(directory + "\\" + inputfile2 + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * Check if the input sequences are empty and handle the cases
		 * accordingly If one of the input sequences is empty then the edit
		 * distance cannot be computed.
		 */
		if (sequence1.length() == 0 || sequence2.length() == 0) {
			System.out.println("Input sequences cannot be empty.");

		}
		// Print the normalized edit distance
		else {
			System.out.print("Normalized edit distance is: ");
			System.out.println(String.format("%.6f", normalizedEditDistance
					.getEditDistance(sequence1, sequence2)));
		}
	}

	/*
	 * Function to Read the sequences from the files provided as input The
	 * function returns the input sequence from the file
	 */
	public static String readFile(String fileName) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				fileName));
		try {
			StringBuilder builder = new StringBuilder();
			String lineText = bufferedReader.readLine().trim();

			while (lineText != null) {
				builder.append(lineText);
				lineText = bufferedReader.readLine();
			}
			return builder.toString();
		} finally {
			bufferedReader.close();
		}
	}
}
