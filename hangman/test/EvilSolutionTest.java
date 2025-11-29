import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class EvilSolutionTest {
    private EvilSolution solution;
    private ArrayList<String> testWords;
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        testWords = new ArrayList<>();
        testWords.add("tiny");
        testWords.add("cute");
        testWords.add("deer");
        solution = new EvilSolution(testWords);
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    // test if constructor can be created
    @Test
    void testConstructor1() {
        assertNotNull(solution);
    }

    // test if constructor can add words
    @Test
    void testConstructor2() {
        ArrayList<String> words = new ArrayList<>();
        words.add("test");
        EvilSolution s = new EvilSolution(words);
        assertNotNull(s);
    }

    // test if isSolved() is false before game
    @Test
    void testIsSolved1() {
        assertTrue(!solution.isSolved());
    }

    // test if isSolved is true when game completes
    @Test
    void testIsSolved2() {
        ArrayList<String> word = new ArrayList<>();
        word.add("at");
        EvilSolution s = new EvilSolution(word);
        s.addGuess('a');
        s.addGuess('t');
        assertTrue(s.isSolved());
    }

    @Test
    void testPrintProgress1() {
        solution.printProgress();
        assertTrue(outputStream.toString().contains("_"));
    }

    // test if print right char
    @Test
    void testPrintProgress2() {
        solution.addGuess('e');
        outputStream.reset();
        solution.printProgress();
        assertTrue(outputStream.toString().length() > 0);
    }

    // test addGuess() correct guess
    @Test
    void testAddGuess1() {
        ArrayList<String> word = new ArrayList<>();
        word.add("cat");
        EvilSolution s = new EvilSolution(word);
        assertTrue(s.addGuess('c'));
    }

    // test addGuess() incorrect guess
    @Test
    void testAddGuess2() {
        assertFalse(solution.addGuess('z'));
    }

    // test addGuess() with multiple guesses
    @Test
    void testAddGuess3() {
        solution.addGuess('e');
        solution.addGuess('a');
        solution.addGuess('l');
        assertNotNull(solution.getTarget());
    }

    // test if target is null
    @Test
    void testGetTarget() {
        assertNotNull(solution.getTarget());
    }

    // test if the pattern length is correct
    @Test
    void testGetTargetLength() {
        assertEquals(4, solution.getTarget().length());
    }

    // test if the game can be completed successfully
    @Test
    void testCompleteGame() {
        ArrayList<String> word = new ArrayList<>();
        word.add("go");
        EvilSolution s = new EvilSolution(word);
        s.addGuess('g');
        s.addGuess('o');
        assertTrue(s.isSolved());
        assertEquals("go", s.getTarget());
    }
}