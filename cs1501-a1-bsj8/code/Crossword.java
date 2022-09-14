import java.io.*;
import java.util.*;

public class Crossword
{
  private DictInterface D;
  private char [][] theBoard;
  private StringBuilder[] rowStr; // print out rowStr[i].toString() for solution
  private StringBuilder[] colStr;
  private int[] rTracker;
  private int[] cTracker;
  private long start;

  public static void main(String [] args) throws IOException
	{
		new Crossword(args);
	}
  public Crossword(String[] args) throws IOException{

    Scanner fileScan = new Scanner(new FileInputStream(args[0]));
    String st;
    D = new MyDictionary();
    //Reads from a file and populates the dictionary. The name of the text file is passed in as an argument
    while (fileScan.hasNext())
    {
      st = fileScan.nextLine();
      D.add(st);
    }
    fileScan.close();

		Scanner fReader = new Scanner(new File(args[1]));
      //This code gets the size of the board and then creates two StringBuilder[] which will later be used to find the solutions
      // two arrays called rTracker and cTracker are also created to keep track of the '-'
    int size= Integer.parseInt(fReader.nextLine());
    theBoard= new char[size][size];
    rowStr = new StringBuilder[size];
    colStr = new StringBuilder[size];
    rTracker= new int[size];
    cTracker= new int[size];

    //This code just fills the arrays previously created  and also populates theBoard.
    for(int i=0; i<size; i++){
      String line=fReader.nextLine();
      rowStr[i] = new StringBuilder("");
      colStr[i] = new StringBuilder("");
      rTracker[i]=-1;
      cTracker[i]=-1;
      for(int j=0; j<line.length();j++){
         theBoard[i][j]= line.charAt(j);
      }
    }

    fReader.close();
    // test that board is populated correctly
    // for (int i = 0; i < size; i++)
		// {
		// 	for (int j = 0; j < size; j++)
		// 	{
		// 		System.out.print(theBoard[i][j] + " ");
		// 	}
		// 	System.out.println();
		// }

       start=System.nanoTime();
     solve(0, 0,rowStr,colStr);


  }
private void solve(int row, int col, StringBuilder[] rowStr, StringBuilder[] colStr){
  int size=rowStr.length-1;
  switch(theBoard[row][col]){
    case '+':
              //When it is a '+' case any letter can go there so I used a for loop to try every letter and see which one works
              for(char c='a'; c<='z';c++){
                //Call the isValid method to see if the letter can be used on theBoard.
                //whether isValid returns true or false depends on where we are on the board and what is returned by calling the searchprefix method
                if(isValid(c,row,col)){
                //if isValid is true then we append to the two StringBuilder[] created previously
                  rowStr[row].append(c);
                  colStr[col].append(c);

                  //If we are at the last column and row of theBoard then we have a found a solution
                  // So we print out our solution using a nested for bloop
                  // I also call a method called score that calculates the score of the solution
                  //finally I exit because there is nothing else to do
                  if(row==size && col==size){
                    for(int i=0; i<size+1; i++){
                      for(int j=0; j<size+1;j++){
                        System.out.print(Character.toUpperCase(rowStr[i].charAt(j)));
                      }
                      System.out.println();
                    }
                    System.out.println("Score: " + score(rowStr));

                    System.exit(0);
                  }else{
                    //If we are not at the last column and row then I call the method again incrementing the row or column
                    // If I am at the last column then I set column to zero when I call the method and increment the row
                        if(col==size){

                          solve(row+1,0,rowStr,colStr);
                        }else{
                          solve(row,col+1,rowStr,colStr);
                        }
                        //when we backtrack we have to get rid of the character that we appended because its not part of the solution anymorre
                        if(rowStr[row].length()>0){
                          rowStr[row].deleteCharAt(rowStr[row].length()-1);
                        }
                        if(colStr[col].length()>0){
                          colStr[col].deleteCharAt(colStr[col].length()-1);

                        }
                  }

                }


              }
              break;

      case '-':

                //in the '-' case  I have two ints that are used to save values in rTracker and cTraker( the two arrays keeping track of the '-')
                // when we backtrack we need to be able to change the values in rTracker and cTracker back to what they previously were and use two ints to do that
               // I also add '-' to the two StringBuilder[] because we need to show it when we print the solution

                int rSave=rTracker[row];
                int cSave=cTracker[col];
                rowStr[row].append("-");
                colStr[col].append("-");
                // Here are the two arrays that keep track of the positions of the '-' in theBoard
                // whenever we append a  '-' it is usually the last position in the StringBuilder
                // so I simply set the arrays equal to the last position of the StringBuilder for whatever row or column
                rTracker[row]=rowStr[row].length()-1;
                cTracker[col]=colStr[col].length()-1;
                // The code below here is pretty similar to '+' case except isValid is not called because no need to check for '-'
                // Also when we back track I restore some the values in rTracker and cTracker by setting them equal the ints previously declared
                // there is also no for loop because we don't have to iterate over the alphabet
                if(row==size && col==size){
                  System.out.println("hello");
                  for(int i=0; i<size+1; i++){
                    for(int j=0; j<size+1;j++){
                      System.out.print(Character.toUpperCase(rowStr[i].charAt(j)));
                    }
                    System.out.println();
                  }
                  System.out.println("Score: " + score(rowStr));

                System.exit(0);
                }else{
                      if(col==size){
                        solve(row+1,0,rowStr,colStr);
                      }else{
                        solve(row,col+1,rowStr,colStr);
                      }
                      rTracker[row]=rSave;
                      cTracker[col]=cSave;
                      if(rowStr[row].length()>0){
                        rowStr[row].deleteCharAt(rowStr[row].length()-1);

                      }
                      if(colStr[col].length()>0){
                        colStr[col].deleteCharAt(colStr[col].length()-1);

                      }
                }

                  break;
        default:
                  //here if there is a pre-exisitng character in theBoard then you call isValid to make sure that you get a valid solution
                  // when you append other characters in the '+' case
                  if(isValid(theBoard[row][col],row,col)){
                    rowStr[row].append(theBoard[row][col]);
                    colStr[col].append(theBoard[row][col]);

                    //The rest of this code is similar to the '+' case without the for loop
                    if(row==size && col==size){
                      for(int i=0; i<size+1; i++){
                        for(int j=0; j<size+1;j++){
                          System.out.print(Character.toUpperCase(rowStr[i].charAt(j)));
                        }
                        System.out.println();
                      }
                      System.out.println("Score: " + score(rowStr));

                      System.exit(0);
                    }else{
                          if(col==size){

                            solve(row+1,0,rowStr,colStr);
                          }else{
                            solve(row,col+1,rowStr,colStr);
                          }
                          if(rowStr[row].length()>0){
                            rowStr[row].deleteCharAt(rowStr[row].length()-1);
                          }
                          if(colStr[col].length()>0){
                            colStr[col].deleteCharAt(colStr[col].length()-1);

                          }
                    }

                  }



  }

  }
  private boolean isValid(char c, int row, int col){
    //here I append the character to the StringBuilders inside the StringBuilder[] because you have to include the character when you call searchPrefix
    // rTracker and cTracker are initialized with -1s so if there is a row or column with no minus's then we will start searching from 0
    rowStr[row].append(c);
    colStr[col].append(c);
    int result=D.searchPrefix(rowStr[row],rTracker[row]+1,rowStr[row].length()-1);
    int cResult=D.searchPrefix(colStr[col],cTracker[col]+1,colStr[col].length()-1);

    //delete the character once you are done searching since you will later append it if isValid is true
    rowStr[row].deleteCharAt(rowStr[row].length()-1);
    colStr[col].deleteCharAt(colStr[col].length()-1);

    //if it is not a valid word or character then return false because you can't do anything with it
    if(result==0 || cResult==0){
      return false;
    }
    //if the column has a valid prefix and we are at the last row then return false because we need a word
    // if we are not at the last row but the next position contains a '-' return false because we also need a word here
    //if we have a valid word but it is not a prefix and we are not at the last row and the next positon does not contain a '-' return false
    if(cResult==1){
      if(row==rowStr.length-1){
        return false;
      }else{
        if (theBoard[row+1][col]=='-') {
          return false;
        }
      }
    }else if(cResult == 2){
      if(row!=rowStr.length-1){
        if(theBoard[row+1][col]!='-')
        return false;
      }
    }
    //if the row contains a valid prefix but we are a the last column then we return false because we need a word
    //if we are not at the last column but the next position contains a '-' we return false because we need a word
    // if we the row contains a word but we are not at the last column and the next position does not contain a '-' we return false because we need a prefix
    if(result == 1){
      if(col==colStr.length-1){
        return false;
      }
      else {
       if (theBoard[row][col+1]=='-'){
         return false;
       }
      }
    }else if(result == 2){
      if(col!=colStr.length-1){
        if(theBoard[row][col+1]!='-')
        return false;
      }

    }
    //if fails all of the previous tests then that means the character is valid and so we return true
    return true;
  }
  private int score(StringBuilder[] rowStr){
    //this method calculates the score by reading the file and using a nested for loop to compare the characters in the StringBuilder with those in the text file
    // If there is a match we add the score associated with the letter and then once we have gone over all the rows we return the score
    try{
    Scanner letters = new Scanner(new FileInputStream("letterpoints.txt"));
    String let;
    int score=0;
    while (letters.hasNext())
    {
      let = letters.nextLine();
      for(int i=0;i<rowStr.length;i++){
        for(int j=0;j<rowStr[i].length();j++){
          if(let.charAt(0)==Character.toUpperCase(rowStr[i].charAt(j))){
            String temp="";
            temp+=let.charAt(2);
            score+=Integer.parseInt(temp);
          }
        }
      }
    }
    letters.close();
    return score;
  }catch(IOException e){
      return 0;
   }
  }

}
