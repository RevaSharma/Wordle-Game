package csci205_hw_wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GuessEvaluator unit tests
 */
class GuessEvaluatorTest {

    public GuessEvaluator guessEval;
    public WordDictionary wd;

    @BeforeEach
    void setUp() throws MalformedURLException, URISyntaxException, FileNotFoundException {
        guessEval = new GuessEvaluator();
        wd = new WordDictionary();
        wd.setWordSet();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void analyzeGuess() throws IOException, URISyntaxException {
        guessEval.setSecretWord();
        guessEval.secretWordSet("betty");
        guessEval.analyzeGuess("betty");
        //System.out.println(guessEval.getGuessAnalysis());
        assertEquals(guessEval.getGuessAnalysis(), "*****");
        guessEval.analyzeGuess("blood");
        assertEquals(guessEval.getGuessAnalysis(), "*----");
        guessEval.analyzeGuess("local");
        assertEquals(guessEval.getGuessAnalysis(), "-----");
        guessEval.analyzeGuess("crumb");
        assertEquals(guessEval.getGuessAnalysis(), "----+");
        guessEval.analyzeGuess("balls");
        assertNotEquals(guessEval.getGuessAnalysis().length(), 5);
        guessEval.analyzeGuess("dsjnfjsdkbfjsdbf");
        assertNotEquals(guessEval.getGuessAnalysis().length(), 5);
    }
}