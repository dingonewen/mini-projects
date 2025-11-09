import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class WordRecommenderTest {
    private WordRecommender wr;

    @BeforeEach
    public void setUp() {
        wr  = new WordRecommender("src/engDictionary.txt");
    }

    @Test
    public void testCalculatePerInCom1() {
        double actual = wr.calculatePerInCom("red","yellow");
        double expected = (double) 1 / 7;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculatePerInCom2() {
        double actual = wr.calculatePerInCom("significant","magnificent");
        double expected = 0.7;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSimilarity1() {
        double actual = wr.getSimilarity("apple","orange");
        double expected = 0.5;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSimilarity2() {
        double actual = wr.getSimilarity("elephant","eel");
        double expected = 0.5;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSimilarity3() {
        double actual = wr.getSimilarity("same","same");
        double expected = 4.0;
        assertEquals(expected, actual);
    }

    // premise: call getWordSuggestions with: tolerance = 2, commonPercent = 0.5, topN = 4
    // assume the word argument in getWordSuggestions() are not in the dictionary
    @Test
    public void testGetWordSuggestions() {
        ArrayList<String> sug = wr.getWordSuggestions("puppy",2,0.5,4);
        assertTrue(sug.size() <= 4);  // test topN
        for (String word : sug) {
            assertTrue(wr.getCandidateSet().contains(word));  // test word
            assertTrue(Math.abs("puppy".length() - word.length()) <= 2);  // test tolerance
            assertTrue(wr.calculatePerInCom("puppy", word) >= 0.5);  // test commonPercent
        for (int i = 0; i < sug.size() - 1; i++) {
            double sim1 = wr.getSimilarity("puppy", sug.get(i));
            double sim2 = wr.getSimilarity("puppy", sug.get(i + 1));
            assertTrue(sim1 >= sim2);  // test similarity order
            }
        }
    }
}