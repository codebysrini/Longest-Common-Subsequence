import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/* Name: Srinivasan Nambi	
 * 49er ID: 800861816
 * This program accepts the folder path and filenames as input from the user and
 * calculates the Longest common subsequence between two sequences 
 * by storing the table in memory and displays the output.
 */
public class LongestCommonSubsequence {
	// Have a stack to store the values while traversing through the table
	Stack<Character> charStack = new Stack<Character>();

	public String getSubsequence(String s1, String s2) {
		return buildSubsequence(s1, s2);
	}

	/*
	 * Calculates the longest common subsequence between two sequences by
	 * storing the entire table and finally returns the LCS between two given
	 * sequences
	 */
	private String buildSubsequence(String s1, String s2) {
		// Append trailing spaces so that the table can be computed easily by
		// referring the index
		s1 = " " + s1;
		s2 = " " + s2;
		StringBuilder subSequence = new StringBuilder();
		// initialize an array to store the computed values of the table
		int[][] tablerows = new int[s1.length()][s2.length()];
		for (int i = 0; i < s1.length(); i++) {
			if (i == 0) {
				// Compute the first row of the table
				for (int j = 0; j < s2.length(); j++) {
					tablerows[0][j] = j;
				}
			} else {
				// Compute the first element of the remaining rows
				tablerows[i][0] = tablerows[i - 1][0] + 1;
				// Compute the value of each cell in the table
				for (int j = 1; j < s2.length(); j++) {
					// If a character match is found calculate the value of cell
					// by adding one to the diagonally top element
					if (s1.charAt(i) == s2.charAt(j)) {
						tablerows[i][j] = tablerows[i - 1][j - 1];
					} else {
						// If a character match cannot be found then
						// find the minimum values of the immediate left and top
						// elements
						// Then add 1 to its value
						if (tablerows[i][j - 1] <= tablerows[i - 1][j]) {
							tablerows[i][j] = tablerows[i][j - 1] + 1;
						} else {
							tablerows[i][j] = tablerows[i - 1][j] + 1;
						}
					}

				}
			}

		}

		// Traversal and building the subsequence
		// start from bottom of the table
		int i = s1.length() - 1, j = s2.length() - 1;
		while (i >= 0 && j >= 0) {
			// If character in row and column matches then pop the character to
			// a stack
			if (s1.charAt(i) == s2.charAt(j) && s1.charAt(i) != ' ') {
				charStack.push(s1.charAt(i));
				i = i - 1;
				j = j - 1;
				continue;
			}

			else {
				/*
				 * check for boundary conditions if they are reached before
				 * proceeding with the traversal
				 */
				if (i == 0 && j > 0) {
					j = j - 1;
					continue;
				} else if (j == 0 && i > 0) {
					i = i - 1;
					continue;
				} else if (i == 0 && j == 0) {
					break;
				} else {
					/*
					 * If characters do not match then traverse the minimum of
					 * top cell or immediate left cell
					 */
					if (tablerows[i][j - 1] <= tablerows[i - 1][j]) {
						j = j - 1;
						continue;
					} else {
						i = i - 1;
						continue;
					}
				}
			}

		}
		// Pop the values from stack
		while (!charStack.isEmpty()) {
			subSequence.append(charStack.pop());
		}
		// return the subsequence after popping from stack
		return subSequence.toString();
	}

	public static void main(String[] args) {
		LongestCommonSubsequence longestCommonSubsequence = new LongestCommonSubsequence();
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
		// read the file from input directory.
		try {
			sequence1 = readFile(directory + "\\" + inputfile1 + ".txt");
			sequence2 = readFile(directory + "\\" + inputfile2 + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Handle if the input sequences are empty
		if (sequence1.length() == 0 || sequence2.length() == 0) {

			System.out.println("Input sequences cannot be empty.");

		}
		// Print the Longest common subsequence
		else {
			System.out.print("Longest Common Subsequence is: ");
			System.out.println(longestCommonSubsequence.getSubsequence(
					sequence1, sequence2));
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
