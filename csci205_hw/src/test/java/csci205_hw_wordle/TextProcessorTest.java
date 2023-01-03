package csci205_hw_wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TextProcessor unit tests
 */
class TextProcessorTest {

    public TextProcessor tp;
    URL bookURL;

    @BeforeEach
    void setUp() throws MalformedURLException, URISyntaxException {
        tp = new TextProcessor();
        bookURL = new URL("https://www.gutenberg.org/cache/epub/69132/pg69132.txt");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void masterDictionary() throws IOException {
        assertEquals(tp.getMasterDict(), null);
        tp.masterDictionary();
        assertNotEquals(tp.getMasterDict().size(), 0);
    }

    @Test
    void processTextAtURL() throws URISyntaxException, IOException {
        assertEquals(tp.getWordMap().size(), 0);
        tp.masterDictionary();
        tp.processTextAtURL(bookURL);
        assertNotEquals(tp.getWordMap().size(), 0);
    }

    @Test
    void writeListOfWords() throws IOException, URISyntaxException {
        tp.masterDictionary();
        tp.processTextAtURL(bookURL);
        tp.writeListOfWords();
        File file = new File("/Users/revasharma/Desktop/CSCI 205/csci205_hw/src/main/java/csci205_hw_wordle/words.txt");
        assertTrue(file.exists());
    }
}