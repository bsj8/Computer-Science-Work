/** A program to find all words of length at least three in a Boggle board.
* The board and the dictionary are read from input files.
* @author Sherif Khattab (Code adapted from Dr. Ramirez's CS 1501 Assignment 1)
*
* Changed by Injung 9/6/2019
* There are two parts to implemented by students
*/

import java.io.*;
import java.util.*;

public class BoggleSolver
{
	private DictInterface D;
	private char [][] theBoard;
	private StringBuilder currentSolution;

	public static void main(String [] args) throws IOException
	{
		new BoggleSolver();
	}

	public BoggleSolver() throws IOException{
		//Read the dictionary
		Scanner fileScan = new Scanner(new FileInputStream("dict8.txt"));
		String st;
		D = new MyDictionary();

		while (fileScan.hasNext())
		{
			st = fileScan.nextLine();
			D.add(st);
		}
		fileScan.close();

		// Parse input file of the Boggle board to create 2-d grid of characters

		Scanner inScan = new Scanner(System.in);
		Scanner fReader;
		File fName;
		String fString = "";

		// Make sure the file name for the Boggle board is valid
		while (true)
		{
			try
			{
				System.out.println("Please enter Boggle board filename:");
				fString = inScan.nextLine();
				fName = new File(fString);
				fReader = new Scanner(fName);

				break;
			}
			catch (IOException e)
			{
				System.out.println("Problem: " + e);
			}
		}

		inScan.close();

		theBoard = new char[4][4];

		for (int i = 0; i < 4; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}
		fReader.close();

		// Show user the board
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
		}

		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				resetBoard();
				currentSolution = new StringBuilder();
				currentSolution.append(theBoard[i][j]);
				theBoard[i][j] = Character.toUpperCase(theBoard[i][j]);
				solve(i, j, 0);
			}
		}
	}
	//We have 8 directions:
	// 0 => up left
	// 1 => up
	// 2 => up right
	// 3 => right
	// 4 => down right
	// 5 => down
	// 6 => down left
	// 7 => left


	private void solve(int row, int col, /*for debugging*/ int depth){
		//for debugging
		// for(int i=0; i<depth; i++){
		// 	System.out.print(" ");
		// }
		// System.out.println(row + ", " + col + " " + currentSolution.toString());
		for(int direction=0; direction<8; direction++){
			if(isValid(row, col, direction)){
				currentSolution.append(nextChar(row, col, direction));
				//mark the letter as used
				Coordinates nextCoords = nextCoordinates(row, col, direction);
				theBoard[nextCoords.row][nextCoords.col] =
					Character.toUpperCase(theBoard[nextCoords.row][nextCoords.col]);
				int res = D.searchPrefix(currentSolution);

				if(res == 1){ //prefix but not word
					solve(nextCoords.row, nextCoords.col, depth + 1);
				}
				if(res == 2){ //word but not prefix
					if(currentSolution.length() >= 3){
						System.out.println(currentSolution.toString());
					}
				}

				if(res == 3){ //word and prefix
					//TODO: Write the code for the word and prefix case
					if(currentSolution.length()>=3){
						System.out.println(currentSolution.toString());
					}
					solve(nextCoords.row,nextCoords.col,depth +1);
				}

				currentSolution.deleteCharAt(currentSolution.length()-1);
				theBoard[nextCoords.row][nextCoords.col] =
					Character.toLowerCase(theBoard[nextCoords.row][nextCoords.col]);
			}

		}
	}

	//is the letter already used (upper case) or are we at an edge of the board?
	private boolean isValid(int row, int col, int direction){
		Coordinates coords = nextCoordinates(row, col, direction);
		//TODO: Write the code to check whether already used / at an edge of the board
		if(coords.row <0 || coords.row >3 || coords.col < 0 || coords.col>3){
			return false;
		}else{
			char temp=theBoard[coords.row][coords.col];
			 if(theBoard[coords.row][coords.col]==Character.toLowerCase(temp)){
			 	return true;
			}else{
				return false;
			}
		}
	}

	private char nextChar(int row, int col, int direction){
		Coordinates coords = nextCoordinates(row, col, direction);
		return theBoard[coords.row][coords.col];
	}

	private Coordinates nextCoordinates(int row, int col, int direction){
		Coordinates result = null;
		switch(direction){
			case 0: //up left
				result = new Coordinates(row-1, col-1);
				break;
			case 1: //up
				result = new Coordinates(row-1, col);
				break;
			case 2: //up right
				result = new Coordinates(row-1, col+1);
				break;
			case 3: //right
				result = new Coordinates(row, col+1);
				break;
			case 4: //bottom right
				result = new Coordinates(row+1, col+1);
				break;
			case 5: //bottom
				result = new Coordinates(row+1, col);
				break;
			case 6: //bottom left
				result = new Coordinates(row+1, col-1);
				break;
			case 7: //left
				result = new Coordinates(row, col-1);
				break;
		}
		return result;
	}

	//reset all characters to lower case
	private void resetBoard(){
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
			}
		}
	}

	//inner class
	private class Coordinates {
		int row;
		int col;

		private Coordinates(int row, int col){
			this.row = row;
			this.col = col;
		}
	}


}
