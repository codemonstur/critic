package critic.util;

import com.aliasi.sentences.IndoEuropeanSentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import critic.model.Counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public enum Text {;

    private static SpellDictionary SPELL_DICT = null;
    private static SpellDictionary getSpellingDictionary() {
        if (SPELL_DICT == null) {
            try (InputStreamReader dict = new InputStreamReader(Text.class.getResourceAsStream("/spelling/english.0"));
                 InputStreamReader phonet = new InputStreamReader(Text.class.getResourceAsStream("/spelling/phonet.en"))) {
                SPELL_DICT = new SpellDictionaryHashMap(dict, phonet);
            } catch (IOException e) { e.printStackTrace(); }
        }
        return SPELL_DICT;
    }

    public static long countSpellingErrors(final String englishText) {
        final var numWrong = new Counter();

        final SpellChecker spellCheck = new SpellChecker(getSpellingDictionary());
        spellCheck.addSpellCheckListener(event -> numWrong.increment());
        try (final BufferedReader reader = new BufferedReader(new StringReader(englishText))) {
            String line;
            while ((line = reader.readLine()) != null) {
                spellCheck.checkSpelling(new StringWordTokenizer(line));
            }
        } catch (IOException e) { /* not possible */ }

        return numWrong.get();
    }

    /*
     * Returns true if the word contains 3 or more syllables
     */
    public static boolean isComplex(final String word) {
        return countSyllables(word) > 2;
    }

    public static int getNumberOfCharacters(final String text) {
        int ret = 0;
        for (final char c : text.toCharArray()) {
            if (isASCIILetter(c)) ret++;
        }
        return ret;
    }

    /*
     * Returns the number of words with 3 or more syllables
     */
    public static int getNumberOfComplexWords(final String text) {
        int complex = 0;
        for (final var word : nonASCIILettersToSpace(text).split(" ")) {
            if (isComplex(word)) complex++;
        }
        return complex;
    }

    public static int getNumberOfWords(final String text) {
        int words = 0;
        for (final var w : nonASCIILettersToSpace(text).split(" ")) {
            if (w.length() > 0) words ++;
        }
        return words;
    }

    public static int getNumberOfSyllables(final String text) {
        int syllables = 0;
        for (final var word : nonASCIILettersToSpace(text).split(" ")) {
            if (word.length() > 0) {
                syllables += countSyllables(word);
            }
        }
        return syllables;
    }

    public static int getNumberOfSentences(final String text) {
        final int length = Text.getSentences(text).length;
        if (length > 0) return length;
        if (text.length() > 0) return 1;
        return 0;
    }

    public static String nonASCIILettersToSpace(final String text) {
        final StringBuilder builder = new StringBuilder();
        for (final char c : text.toCharArray()) {
            builder.append( isASCIILetter(c) ? c : ' ' );
        }
        return builder.toString().toLowerCase();
    }

    public static boolean isASCIILetter(final char c) {
        return c < 128 && Character.isLetter(c);
    }

    public static String[] getSentences(final String text) {

        final var tokenList = new ArrayList<String>();
        final var whiteList = new ArrayList<String>();
        Tokenizer tokenizer = IndoEuropeanTokenizerFactory.INSTANCE.tokenizer(text.toCharArray(), 0, text.length());
        tokenizer.tokenize(tokenList, whiteList);

        String[] tokens = new String[tokenList.size()];
        String[] whites = new String[whiteList.size()];
        tokenList.toArray(tokens);
        whiteList.toArray(whites);

        int[] sentenceBoundaries = new IndoEuropeanSentenceModel().boundaryIndices(tokens, whites);

        if (sentenceBoundaries.length < 1) {
            return new String[0];
        }

        String[] result = new String[sentenceBoundaries.length];

        int sentStartTok = 0;
        int sentEndTok = 0;
        for (int i = 0; i < sentenceBoundaries.length; ++i) {
            sentEndTok = sentenceBoundaries[i];
            StringBuffer sb = new StringBuffer();
            for (int j = sentStartTok; j <= sentEndTok; j++) {
                sb.append(tokens[j] + whites[j + 1]);
            }
            result[i] = sb.toString();
            sentStartTok = sentEndTok + 1;
        }
        return result;
    }

    private static String[] SubSyl = { "cial", "tia", "cius", "cious", "giu", "ion", "iou", "sia$", ".ely$" };
    private static String[] AddSyl = { "ia", "riet", "dien", "iu", "io", "ii", "[aeiouym]bl$", "[aeiou]{3}", "^mc", "ism$", "[^aeiouy][^aeiouy]l$", "[^l]lien","^coa[dglx].", "[^gq]ua[^auieo]", "dnt$" };

    /*
     * Estimates the number of syllables in a word.
     *
     * Translation of the Perl code by Greg Fast, found at:
     * http://search.cpan.org/author/GREGFAST/Lingua-EN-Syllable-0.251/
     *
     * For documentation and comments
     * http://search.cpan.org/src/GREGFAST/Lingua-EN-Syllable-0.251/Syllable.pm
     */
    public static int countSyllables(String word) {

        word = word.toLowerCase();
        word = word.replaceAll("'", " ");

        if (word.equals("i")) return 1;
        if (word.equals("a")) return 1;

        if (word.endsWith("e")) {
            word = word.substring(0, word.length() - 1);
        }

        String[] phonems = word.split("[^aeiouy]+");

        int syl = 0;
        for (String syllable : SubSyl) {
            if (word.matches(syllable)) {
                syl--;
            }
        }
        for (String syllable : AddSyl) {
            if (word.matches(syllable)) {
                syl++;
            }
        }
        if (word.length() == 1) {
            syl++;
        }

        for (String phonem : phonems) {
            if (phonem.length() > 0)
                syl++;
        }

        if (syl == 0) {
            syl = 1;
        }

        return syl;
    }

}
