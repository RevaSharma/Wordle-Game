/* *****************************************
 * CSCI205 -Software Engineering and Design
 * Fall2022
 * Instructor: Prof. Brian King
 *
 * Name: Reva Sharma, Kit Jackson
 * Section: 11am - 02
 * Date: 10/11/22
 * Time: 2:30 PM
 *
 * Project: csci205_hw
 * Package: csci205_hw_wordle
 * Class: WordDictionary
 *
 * Description:
 *
 * ****************************************
 */
package csci205_hw_wordle;

import java.io.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Class that represents a set of unique valid words
 * Reads in words.txt, stores them, and provides random words for the game while checking for valid words as guesses
 */
public class WordDictionary {

    private String[] LIST_OF_TEXT_URLS;

    public Set<String> getWordSet() {
        return wordSet;
    }

    private Set<String> wordSet;
    private TextProcessor textPros;

    private ArrayList<String> wordList;

    /**
     * WordDictionary constructor
     * Populates LIST_OF_TEXT_URLS with the URLs of the books we want to analyze
     */
    public WordDictionary() throws MalformedURLException {
        wordSet=new HashSet<String>();
        LIST_OF_TEXT_URLS=new String[5];
        LIST_OF_TEXT_URLS[0]="https://www.gutenberg.org/cache/epub/69132/pg69132.txt";
        LIST_OF_TEXT_URLS[1]="https://www.gutenberg.org/cache/epub/69135/pg69135.txt";
        LIST_OF_TEXT_URLS[2]="https://www.gutenberg.org/cache/epub/69130/pg69130.txt";
        LIST_OF_TEXT_URLS[3]="https://www.gutenberg.org/cache/epub/69128/pg69128.txt";
        LIST_OF_TEXT_URLS[4]="https://www.gutenberg.org/cache/epub/69127/pg69127.txt";
    }

    public String[] getLIST_OF_TEXT_URLS()
    {
        return this.LIST_OF_TEXT_URLS;
    }

    /**
     * Takes in 5 hard coded URLs for the books, generates a word set, and writes it to words.txt
     * @throws IOException
     * @throws URISyntaxException
     */
    public void generateNewWordSet() throws IOException, URISyntaxException {

        URL book1 = new URL(LIST_OF_TEXT_URLS[0]);
        URL book2 = new URL(LIST_OF_TEXT_URLS[1]);
        URL book3 = new URL(LIST_OF_TEXT_URLS[2]);
        URL book4 = new URL(LIST_OF_TEXT_URLS[3]);
        URL book5 = new URL(LIST_OF_TEXT_URLS[4]);
        textPros = new TextProcessor();
        System.out.println("Reading master word set from Webster's Unabridged Dictionary");
        textPros.masterDictionary();
        System.out.println("Finding common words from novels:");
        textPros.processTextAtURL(book1);
        System.out.println("- Reading in Betty Wales on the campus, by Margaret Warde......done");
        textPros.processTextAtURL(book2);
        System.out.println("- Reading in Flying Plover, by G. E. Theodore Roberts......done");
        textPros.processTextAtURL(book3);
        System.out.println("- Reading in The island of the stairs, by Cyrus Townsend Brady......done");
        textPros.processTextAtURL(book4);
        System.out.println("- Reading in The making of a mountaineer, by George Ingle Finch......done");
        textPros.processTextAtURL(book5);
        System.out.println("- Reading in Bromoil printing and bromoil transfer, by Dr. Emil Mayer......done");
        System.out.println("Storing word dataset as words.txt...");
        textPros.writeListOfWords();
        textPros.printReport();
    }

    /**
     * Reads in words from words.txt and adds it to wordSet
     */
    public void setWordSet() throws FileNotFoundException, MalformedURLException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/Users/revasharma/Desktop/CSCI 205/csci205_hw/src/main/java/csci205_hw_wordle/words.txt"));
            String line = reader.readLine();
            while (line != null) {
                wordSet.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the word provided by the user exists in wordSet
     * @param word provided by the user
     * @return boolean value true if word exists in wordSet, otherwise false
     */
    public boolean isWordinSet(String word)
    {
        if(wordSet.contains(word))
            return true;
        else
            return false;
    }

    /**
     * Picks a random word from wordSet
     * @return the random word picked
     * @throws IOException if words.txt does not exist
     */
    public String getRandomWord() throws IOException {
        setWordSet();
        //Create a new ArrayList<String> and adds words from wordSet to it
        wordList = new ArrayList<String>();
        for(String word : wordSet)
        {
            wordList.add(word);
        }
        Random rand = new Random();
        //Get a random index with max value being the size of the wordSet
        System.out.println("Keeping "+ wordList.size() +" valid words for the game...");
        System.out.println("READY!");
        System.out.println("");
        int randIndex = rand.nextInt(wordList.size());
        //Access the word corresponding to the random index
        String randWord = wordList.get(randIndex);

        return randWord;
    }

}
