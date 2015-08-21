import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/* Name: Srinivasan Nambi	
 * 49er ID: 800861816
 * This program accepts the folder path and filenames as input from the user and
 * calculates the Longest common subsequence between two sequences and displays the output.
 */
public class LinearRecursiveLCS {
	private StringBuilder subSequence = new StringBuilder();

	public String getSubsequence(String s1, String s2) {
		lcs_recursive(s1, s2);

		return subSequence.toString();
	}

	/*
	 * Calculates the longest common subsequence between two sequences by the
	 * method of linear recursion without storing the entire table in memory and
	 * finally returns the LCS between two given sequences
	 */
	private void lcs_recursive(String seq1, String seq2) {
		int i = 0;
		// Base case of recursion when the first input sequence has one
		// character
		if (seq1.length() == 1) {
			while (i < seq2.length()) {
				if (seq1.charAt(0) == seq2.charAt(i)) {
					subSequence.append(seq1.charAt(0));
					break;
				}
				i++;
			}

		}
		// Base case of recursion when the second input sequence has one
		// character
		else if (seq2.length() == 1) {
			i = 0;
			while (i < seq1.length()) {
				if (seq2.charAt(0) == seq1.charAt(i)) {
					subSequence.append(seq2.charAt(0));
					break;
				}
				i++;
			}

		} else {
			seq1 = seq1.trim();
			seq2 = seq2.trim();
			// Find the horizontal split by finding the median index of
			// sequence1
			int horizontalSplit = seq1.length() / 2 - 1;
			// Find the vertical index using the logic of forward and reverse
			// middle rows
			int verticalSplit = getVerticalSplit(seq1, seq2, horizontalSplit);
			// Generate the sequence xFront and yFront
			String xFront = seq1.substring(0, horizontalSplit + 1);
			String yFront = seq2.substring(0, verticalSplit);
			// Generate the sequence xBack and yBack
			String xBack = seq1.substring(horizontalSplit + 1, seq1.length());
			String yBack = seq2.substring(verticalSplit, seq2.length());
			// Call the function recursively
			lcs_recursive(xFront, yFront);
			lcs_recursive(xBack, yBack);
		}
	}

	/*
	 * Function that calculates the forward and reverse middle row and returns
	 * the index where the vertical split happens
	 */
	private int getVerticalSplit(String verticalSeq, String horizontalSeq,
			int horizontalSplit) {
		// calculate the forward middle row
		// gets forward middle row
		int[] fwdMiddleRow = getFwdMiddleRow(
				verticalSeq.substring(0, horizontalSplit + 1), horizontalSeq);
		// gets reverse middle row
		int[] reverseMiddleRow = getReverseMiddleRow(verticalSeq.substring(
				horizontalSplit + 1, verticalSeq.length()), horizontalSeq);
		// calculate the sum of both by adding the elements diagonally
		for (int i = 0; i < fwdMiddleRow.length; i++) {
			fwdMiddleRow[i] = fwdMiddleRow[i] + reverseMiddleRow[i];

		}
		// finds the index of the minimum value in the row and returns the same
		int min = fwdMiddleRow[0];
		int minIndex = 0;
		for (int i = 0; i < fwdMiddleRow.length; i++) {
			if (fwdMiddleRow[i] < min) {
				min = fwdMiddleRow[i];
				minIndex = i;
			}
		}
		// returns the index of vertical split to calling function
		return minIndex;
	}

	// Calculates the reverse middle row and returns it
	private int[] getReverseMiddleRow(String s1, String s2) {
		/*
		 * pass the input strings in reverse order to the function that
		 * calculates the forward middle row
		 */
		int[] reverseRow = getFwdMiddleRow(new StringBuilder(s1).reverse()
				.toString(), new StringBuilder(s2).reverse().toString());
		// Reverse the output from the function to get the reverse middle row
		int[] actualRow = new int[reverseRow.length];
		int i = reverseRow.length - 1, j = 0;
		while (j < reverseRow.length) {
			actualRow[j] = reverseRow[i];
			i--;
			j++;

		}
		// returns reverse middle row
		return actualRow;
	}

	/* Calculates the forward middle row by having only two rows in memory */
	private int[] getFwdMiddleRow(String s1, String s2) {
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

			} else {
				for (int j = 0; j < s2.length(); j++) {
					tablerows[0][j] = tablerows[1][j];
				}
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
			}

		}
		return tablerows[1];
	}

	public static void main(String[] args) {
		LinearRecursiveLCS linearRecursiveLCS = new LinearRecursiveLCS();
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
			System.out
					.print("Longest Common Subsequence(using linear memory) is: ");
			System.out.println(linearRecursiveLCS.getSubsequence(sequence1,
					sequence2));
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
