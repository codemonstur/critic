package critic;

import critic.util.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class TextTest {

    @Test
    public void countSyllablesBasic() {
        assertEquals(1, Text.countSyllables("cat"));
        assertEquals(2, Text.countSyllables("hello"));
        assertEquals(1, Text.countSyllables("a"));
    }

    @Test
    public void countWords() {
        assertEquals(4, Text.getNumberOfWords("one two three four"));
        assertEquals(0, Text.getNumberOfWords("   "));
    }

    @Test
    public void countSentences() {
        assertEquals(2, Text.getNumberOfSentences("Hello world. How are you?"));
    }

    @Test
    public void spellingErrors() {
        assertEquals(0L, Text.countSpellingErrors("This is a correct sentence."));
    }

}