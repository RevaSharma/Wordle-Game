package csci205_hw_wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WordDictionary unit tests
 */
class WordDictionaryTest {
    File filename = new File("/Users/revasharma/Desktop/CSCI 205/csci205_hw/src/main/java/csci205_hw_wordle/words.txt");
    public Scanner scan;
    public WordDictionary wd;


    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        scan = new Scanner(filename);
        wd = new WordDictionary();
        wd.generateNewWordSet();
        wd.setWordSet();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateNewWordSet() {
        assertTrue(scan.hasNextLine());
    }

    @Test
    void setWordSet() {
        assertNotEquals(wd.getWordSet().size(), 0);
    }

    @Test
    void isWordinSet() {
        assertTrue(wd.isWordinSet("betty"));
    }

    @Test
    void getRandomWord() throws IOException {
        String rand = wd.getRandomWord();
        assertTrue(rand instanceof String);
    }
}