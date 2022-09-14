/**
 * A test program for CS 1501 Assignment 2. The program takes the dictionay file name
 * as a command line argument.
 * @author Sherif Khattab
 */
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

public class A2Test {

  private static final int NUM_ACCESSES = 10; //number of word selections
  private static final int SEED = 1501; //seed for the random number generator
  private AutoComplete ac;
  private ArrayList<StringBuilder> dictionary;

  public static void main(String[] args) throws java.io.IOException{
    if(args.length != 1){
      System.out.println("Usage: java A2Test <dictionary file>");
    } else {
      new A2Test(args);
    }
  }

  public A2Test(String[] args) throws java.io.IOException {
    dictionary = new ArrayList<>();
    ac = new AutoComplete(args[0]);
    //read in the dictionary
    Scanner fileScan = new Scanner(new FileInputStream(args[0]));
    while(fileScan.hasNextLine()){
      dictionary.add(new StringBuilder(fileScan.nextLine()));
    }
    fileScan.close();

    //seed the AutoComplete object with random word selections
    Random rand = new Random(SEED);
    for(int i=0; i<NUM_ACCESSES; i++){
      StringBuilder word = dictionary.get(rand.nextInt(dictionary.size()));
      ac.notifyWordSelected(word);
      System.out.println("Incrementing score of " + word);
    }

    testScores(); //test function for Part 1
    testSuggestions();  //test function for Part 2
  }

  /**
   * Test function for Part 1. Check the scores of randomly selected words.
   */
  private void testScores(){
    Random rand = new Random(SEED+1);
    System.out.println("Part 1: Testing word scores:");
    for(int i=0; i<NUM_ACCESSES; i++){
      StringBuilder word = dictionary.get(rand.nextInt(dictionary.size()));
      System.out.println(word + ": " + ac.getScore(word));
    }
  }

   /**
   * Test function for Part 2. Provide word suggestions for user input.
   */

  private void testSuggestions(){
    System.out.println("Part 2: Testing suggestions:");

    Scanner scan = new Scanner(System.in);
    boolean foundInSuggestions = false; //is the user input one of the returned word suggestions?

    while(true){
      foundInSuggestions = false;
      System.out.println("Type one or more letters then press enter " +
                        "to get auto-complete suggestions...");
      String currentString = scan.nextLine();

      int i = 0;
      ArrayList<AutoComplete.Suggestion> suggestions = ac.retrieveWords(new StringBuilder(currentString));
      for(AutoComplete.Suggestion s : suggestions){
        System.out.println(i++ + ": " + s.word.toString() + ": " + s.score);
        if(s.word.toString().equals(currentString.toString())){
          foundInSuggestions = true;
        }
      }

      //offer to add user input as a new word if it is not a word
      if(!foundInSuggestions){
        System.out.println(i++ + ": Add " + currentString.toString() + " to the dictionary.");
      }
      //offer to do nothing
      System.out.println(i + ": No selection");

      //loop until reading a valid selection
      int userSelection = -1;
      while(true){
        System.out.print("Select a number (-1 to exit): ");
        try{
          userSelection = Integer.parseInt(scan.nextLine());
          if(userSelection == -1 || userSelection == i-1 || userSelection == i || suggestions.get(userSelection) != null){
            break;
          }
        } catch (Exception e){
          System.out.println("Invalid input. Please try again.");
        }
      }
      if(userSelection == -1){
        break;
      }
      if(!foundInSuggestions && userSelection == i-1){
        //user opted to add the word: add it and update its score
        ac.add(new StringBuilder(currentString));
        ac.notifyWordSelected(new StringBuilder(currentString));
      } else {
        if(userSelection != i){
          //update score
          ac.notifyWordSelected(suggestions.get(userSelection).word);
        }
      }
    }
    scan.close();
  }

}
