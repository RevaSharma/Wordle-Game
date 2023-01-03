/* *****************************************
 * CSCI205 -Software Engineering and Design
 * Fall2022
 * Instructor: Prof. Brian King
 *
 * Name: Reva Sharma, Kit Jackson
 * Section: 11am - 02
 * Date: 10/11/22
 * Time: 2:15 PM
 *
 * Project: csci205_hw
 * Package: csci205_hw_wordle
 * Class: Wordle
 *
 * Description:
 *
 * ****************************************
 */
package csci205_hw_wordle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Primary game class - governs the overall logic and flow of communication between objects
 */
public class Wordle {

    /**
     * Number of guesses
     */
    private int guessNumber;
    /**
     * Word to be guessed
     */
    private String secretWord;
    /**
     * Keeps track of the last guess made by the user
     */
    private String lastGuess;
    /**
     * Object of enum type GameState that keeps track of state of the game
     */
    private GameState gameState;
    /**
     * Object of the GuessEvaluator class
     */
    private GuessEvaluator guessEval;

    /**
     * Wordle constructor
     */
    public Wordle() throws MalformedURLException, URISyntaxException {
        this.guessEval = new GuessEvaluator();
    }

    /**
     * Starts the game by providing the user with a fixed initial number of guesses
     * Sets the secret by using the instance of GuessEvaluator class
     * @throws IOException
     * @throws URISyntaxException
     */
    private void initNewGame() throws IOException, URISyntaxException{
        //Set the state of the game as a new one
        this.gameState = GameState.NEW_GAME;
        //Initial number of guesses allowed should be 6
        this.guessNumber = 6;
        //Set the secret word i.e. the word to be guessed and set it to secretWord
        guessEval.setSecretWord();
        this.secretWord = guessEval.getSecretWord();
        //Ready to start a new game!
        System.out.println("Welcome to Wordle!");
        System.out.println("Ready to play Wordle! You have 6 guesses");
        System.out.println("A \'*\' means you have the right letter in the right place");
        System.out.println("A \'+\' means the letter is in the word, but in a different place");
        System.out.println("A \'-\' means that letter is not anywhere in the word to be guessed");
        playNextTurn();
    }

    /**
     * Asks the user to type in their guesses and evaluates it
     * @throws IOException
     * @throws URISyntaxException
     */
    private void playNextTurn() throws IOException, URISyntaxException {
        Scanner scan = new Scanner(System.in);
        //Change the state of the game to in progress
        this.gameState = GameState.GAME_IN_PROGRESS;
        //For ease of guessing print out the secret word
        System.out.println("The word to be guessed: "+ this.secretWord.toUpperCase());
        System.out.println("Enter your first guess below: ");
        //Captures the answer guessed by the user and prints out the result as a combination of "*", "+", "-"
        String resultTurn = null;
        //Keeps track of the number of current guess
        int currentTurn = 0;
        //While number of guesses is not exhausted
        while(this.guessNumber>0){
            currentTurn = 7 - this.guessNumber;
            //Stores in the guess inputted by the user
            this.lastGuess = scan.nextLine();
            System.out.println("Guess "+ currentTurn + ": "+ this.lastGuess.toUpperCase());
            //Guess is analyzed and stored in resultTurn
            guessEval.analyzeGuess(this.lastGuess);
            resultTurn = guessEval.getGuessAnalysis();
            //If user guesses the secret word within given number of tries, then they are declared winner
            if(resultTurn.equals("*****"))
            {
                this.gameState = GameState.GAME_WINNER;
                System.out.println("   -->   " + "YOU WON! You guessed the word in " + currentTurn + "turns!");
                this.gameState = GameState.GAME_WINNER;
                break;
            }
            //If user has not yet guessed the right word and still has tries left
            else{
                //If user guesses a word that is greater than 5 in length, the word is invalid and
                // the response does not count as a guess
                if(resultTurn.length()>5){
                    System.out.println(resultTurn);
                    guessNumber+=1;
                }
                //If response is valid but user still hasn't guessed the right word
                else {
                    System.out.println("  -->  " + resultTurn + " Try again. " + (6-currentTurn) + " guesses left.");
                }
            }
            //Decrement the number of guesses left everytime an incorrect guess is made
            this.guessNumber-=1;

        }
        //If user runs out of tries, tell them they lost and let them know the correct word
        if(this.gameState==GameState.GAME_IN_PROGRESS)
        {
            this.gameState=GameState.GAME_LOSER;
            System.out.println("YOU LOST. The correct word was "+ this.secretWord.toUpperCase());
        }
        //If game is over ask them if they would like to play again
        if(isGameOver())
        {
            System.out.println("Would you like to play again? [Y/N]");
            String userChoice = scan.nextLine();
            if((userChoice.toLowerCase().equals("y"))||(userChoice.toUpperCase().equals("Y")))
                initNewGame();
            else
                System.out.println("Goodbye!");
        }

    }

    /**
     * @return boolean value true if user either guesses or is unable to guess the secret word in the given number of tries
     */
    private boolean isGameOver()
    {
       if (this.gameState==GameState.GAME_WINNER || this.gameState==GameState.GAME_LOSER)
           return true;
       else
           return false;
    }

    /**
     * Simple main program that runs the wordle game
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String args[]) throws IOException, URISyntaxException {
        Scanner scan = new Scanner(System.in);
        //Create instance of Wordle and then start the game
        Wordle game = new Wordle();
        game.initNewGame();
    }
}
