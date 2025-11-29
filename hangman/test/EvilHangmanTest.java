import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

class EvilHangmanTest {
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeAll
    public static void createTestFile() throws IOException {       // create a testDictionary.txt with much less words for testing purposes
        FileWriter writer = new FileWriter("testDictionary.txt");
        writer.write("hello\n");
        writer.write("from\n");
        writer.write("the\n");
        writer.write("other\n");
        writer.write("side\n");
        writer.close();
    }

    @BeforeEach
    public void setUp() {               // store original system.in & system.out
        originalIn = System.in;
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {           // restore system.in & system.out
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // test repeated guess
    @Test
    public void testStart1() {
        String input = "e\ne\nh\nl\no\nf\nr\nm\nt\ni\nd\ns\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        new EvilHangman("testDictionary.txt").start();
        String output = outputStream.toString();
        assertTrue(output.contains("already") || output.contains("You've already guessed"),
                "Should warn about repeated guess");
    }

    // test invalid input (int, punc)
    @Test
    public void testStart2() {
        String input = "66\n!\ne\nh\nl\no\nf\nr\nm\nt\ni\nd\ns\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        new EvilHangman("testDictionary.txt").start();
        String output = outputStream.toString();
        assertTrue(output.contains("alphabetic") || output.contains("letter"),
                "Should prompt for alphabetic character");
    }

    // test string input comparing to char
    @Test
    public void testStart3() {
        String input = "hello\ne\nh\nl\no\nf\nr\nm\nt\ni\nd\ns\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        new EvilHangman("testDictionary.txt").start();
        String output = outputStream.toString();
        assertTrue(output.contains("single"),
                "Should prompt for single character");
    }

    // test non-existing chars
    @Test
    public void testStart4() {
        String input = "e\nx\nz\nh\nl\no\nf\nr\nm\nt\ni\nd\ns\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        new EvilHangman("testDictionary.txt").start();
        String output = outputStream.toString();
        assertTrue(output.contains("_"), "Should display underscores");
        assertTrue(output.contains("Incorrect guesses"), "Should display incorrect guesses literal");
    }
}
