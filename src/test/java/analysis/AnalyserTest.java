package analysis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author nilstes
 */
public class AnalyserTest {
    
    Analyser analyser = new Analyser();
    
    @Test
    public void testGoodSentiment() {
        assertEquals(3, analyser.findSentiment("I am so happy"));
        assertEquals(3, analyser.findSentiment("What a beautiful day"));
    }
    
    @Test
    public void testVeryGoodSentiment() {
        assertEquals(4, analyser.findSentiment("Happy good beautiful"));
    }
    
    @Test
    public void testBadSentiment() {
        assertEquals(1, analyser.findSentiment("I hate you"));
        assertEquals(1, analyser.findSentiment("Work was really bad today"));
    }
    
    @Test
    public void testVeryBadSentiment() {
        assertEquals(3, analyser.findSentiment("You are very smart"));
//        assertEquals(0, analyser.findSentiment("Bad"));
    }
    
    @Test
    public void testSoSoSentiment() {    
        assertEquals(2, analyser.findSentiment("Well well"));
        assertEquals(2, analyser.findSentiment("Yet another day"));
    }
}
