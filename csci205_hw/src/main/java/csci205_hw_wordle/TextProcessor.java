/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall2022
 * Instructor: Prof. Brian King
 *
 * Name: Reva Sharma, Kit Jackson
 * Section: 11am - 02
 * Date: 10/11/22
 * Time: 2:22 PM
 *
 * Project: csci205_hw
 * Package: csci205_hw_wordle
 * Class: TextProcessor
 *
 * Description:
 * ****************************************
 */
package csci205_hw_wordle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;

/**
 * A simple class that reads in the URL and processes the URL stream in the form of a BufferedReader
 * Connects to the URL and filters a stream of text for all valid, cleaned, filtered words
 * Prints information related to text that was read in and writes words.txt using the processed BufferedReader
 */
public class TextProcessor {

    /**
     * URL to connect to
     */
    private URL url;

    /**
     * Master dictionary containing all 5-letter words
     */
    private ArrayList<String> masterDict;

    /**
     * Map that keeps track of common words and the number of times it appears in the text
     */
    private Map<String, Integer> wordMap;

    /**
     * Total number of words in the text
     */
    private int totalWords;
    /**
     * Total number of words that got discarded and could not be used in the final set
     */
    private int totalGoodWordsDiscarded;
    /**
     * Total number of usable words
     */
    private int totalGoodWords;
    /**
     * Total number of unique words that appeared in the text
     */
    private int totalUniqueWords;

    /**
     * TextProcessor constructor
     */
    public TextProcessor() throws URISyntaxException, MalformedURLException {
        this.totalWords = 0;
        this.totalGoodWordsDiscarded = 0;
        this.totalGoodWords = 0;
        this.totalUniqueWords = 0;
        wordMap = new HashMap<>();
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public ArrayList<String> getMasterDict() {
        return masterDict;
    }
    public int getTotalWords() {
        return totalWords;
    }

    /**
     * Reads in Websters Unabridged Dictionary at  https://www.gutenberg.org/cache/epub/29765/pg29765.txt and creates a lit of 5 letter uppercase words
     * @throws IOException
     */
    public void masterDictionary() throws IOException {
        URL WEB_URL = new URL("https://www.gutenberg.org/cache/epub/29765/pg29765.txt");
        //Creates array list to store all 5-letter words in the dictionary
        masterDict = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(WEB_URL.openStream()));
        String inputLine = "";
        // creates list for words from line to be added to
        String[] words = new String[0];
        // while there is still a line available
        while(inputLine!= null) {
            inputLine = in.readLine();
            if (inputLine != null) {
                // adds all words from a single line to list "words" splitting at spaces
                words = inputLine.split(" ");
                for (String word : words) {
                    // if the word is uppercase (a word defined in the dictionary) and of length 5
                    if (word.length() == 5 && word.equals(word.toUpperCase())) {
                        masterDict.add(word.toLowerCase());
                    }
                }

            }
        }
    }

    /**
     * Connects to given URL, and populates wordMap with validated words
     * @param url the URL to connect to
     * @throws URISyntaxException
     * @throws IOException
     */
    public void processTextAtURL(URL url) throws URISyntaxException,IOException {
        // creates list for words from line to be added to
        String[] words = new String[0];
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine = "";
        while(inputLine!= null)
        {
            inputLine = in.readLine();
            if(inputLine!=null){
                //Replaces all punctuations with "", inspiration from: https://www.geeksforgeeks.org/removing-punctuations-given-string/
                inputLine = inputLine.replaceAll("\\p{Punct}","");
                //Replaces all characters from 'space' to '~' with "", inspiration from: https://vinaynotes.wordpress.com/2017/01/13/purpose-of-x20-x7e-in-regular-expressions/
                inputLine = inputLine.replaceAll("[^\\x20-\\x7e]", "");
                //Replace all uppercase letters to lowercase
                inputLine = inputLine.toLowerCase();
                //Split the line based on spaces in between words and store them in an array
                words = inputLine.split(" ");
                //Adds the elements from the array words to wordMap with their relative frequency
                for(int i=0;i<words.length;i++)
                {
                    totalWords++;
                    // if the word is in the dictionary
                    if(masterDict.contains(words[i]))
                    {
                        // if the wordmap is empty add this word to it and increase frequency by 1
                        if(wordMap.size() == 0){
                            wordMap.put(words[i], 1);
                        }
                        // if the wordmap does not contain this word add it to the wordmap and increase frequency by 1
                        if (!(wordMap.containsKey(words[i])))
                            wordMap.put(words[i], 1);
                        else {
                            // increase the frequency of the word that is already in the wordmap by 1
                            int newFreq = wordMap.get(words[i]) + 1;
                            wordMap.put(words[i], newFreq);
                        }
                    }
                }

            }
        }
        in.close();
    }

    /**
     * @param url the URL to be set to
     */
    private void setUrl(URL url)
    {
        this.url = url;
    }

    /**
     * Prints a report analyzing the data related to the words collected
     */
    public void printReport()
    {
        //Sums all the values in wordMap, inspiration from https://www.techiedelight.com/calculate-sum-of-values-map-java/
        int kept = getWordMap().values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total number of words processed: " + getTotalWords());
        //TODO figure out what these are
        System.out.println("Total number of words eliminated: " + (getTotalWords() - kept));
        System.out.println("Total number of words kept: " + kept);
        System.out.println("Number of unique words: " + getWordMap().size());
        outTop20();
    }

    /**
     * Prints out the top 20 words with most frequency
     */
    private void outTop20() {
        System.out.println("Top 20 most frequently occurring words:");
        int count = 0;
        Map<String,Integer> wordMap1 = getWordMap();
        //Creates array list with all the values in wordMap
        ArrayList<Integer> valList = new ArrayList<>(wordMap1.values());
        //Sorts values in descending order
        valList.sort(Collections.reverseOrder());

        TreeSet valSet = new TreeSet();
        valSet.addAll(valList);
        TreeSet<Integer> valSetD = (TreeSet<Integer>) valSet.descendingSet();
        for (Integer val : valSetD) {
            for (String key : wordMap1.keySet()) {
                if (val == wordMap1.get(key) && count < 20) {
                    System.out.println(key + ": " + wordMap1.get(key));
                    count++;
                }
            }
        }
    }

    /**
     * Writes words from wordMap to words.txt
     * Words.txt will contain valid words that can be used in the game
     */
    public void writeListOfWords() throws IOException, URISyntaxException {
        File filename = new File("/Users/revasharma/Desktop/CSCI 205/csci205_hw/src/main/java/csci205_hw_wordle/words.txt");
        PrintStream outFile = new PrintStream(filename);
        for (String word: wordMap.keySet()){
            outFile.println(word);
        }

    }
}
