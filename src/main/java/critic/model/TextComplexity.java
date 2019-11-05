package critic.model;

import static critic.util.Text.*;

/**
 * Implements various readability indexes
 * http://code.google.com/p/panos-ipeirotis/source/browse/trunk/src/com/ipeirotis/readability/?r=2
 * Original code written by Panos Ipeirotis, fixed and cleaned up
 */
public final class TextComplexity {

    public final int sentences;
    public final int complex;
    public final int words;
    public final int syllables;
    public final int characters;

    public final double automaticReadabilityIndex;
    public final double colemanLiau;
    public final double fleschKincaidGradeLevel;

    // A 90.0–100.0 score is easily understood by the average 11-year-old student.
    // A 60.0–70.0 score is easily understood by the average 13 to 15-year-old students.
    // A 0.0–30.0 score is best understood by university graduates.
    public final double fleschReadingEase;
    public final double gunningFog;
    public final double smog;
    public final double smogIndex;

    public TextComplexity(final String text) {
        this.sentences = getNumberOfSentences(text);
        this.complex = getNumberOfComplexWords(text);
        this.words = getNumberOfWords(text);
        this.syllables = getNumberOfSyllables(text);
        this.characters = getNumberOfCharacters(text);

        this.automaticReadabilityIndex = calculateAutomatedReadabilityIndex(characters, words, sentences);
        this.colemanLiau = calculateColemanLiau(words, characters, sentences);
        this.fleschKincaidGradeLevel = calculateFleschKincaidGradeLevel(words, sentences, syllables);
        this.fleschReadingEase = calculateFleschReadingEase(words, sentences, syllables);
        this.gunningFog = calculateGunningFog(words, sentences, complex);
        this.smog = calculateSMOG(complex, sentences);
        this.smogIndex = calculateSMOGIndex(complex, sentences);
    }

    /**
     * http://en.wikipedia.org/wiki/SMOG_Index
     * @return The SMOG index of the text
     */
    public static double calculateSMOGIndex(final int complex, final int sentences) {
        if (sentences == 0) return Double.MIN_VALUE;

        return Math.sqrt( complex * (30.0 / sentences) ) + 3;
    }

    /**
     * http://en.wikipedia.org/wiki/SMOG
     * @return the SMOG value for the text
     */
    public static double calculateSMOG(final int complex, final int sentences) {
        if (sentences == 0) return Double.MIN_VALUE;
        return 1.043 * Math.sqrt( complex * (30.0 / sentences) ) + 3.1291;
    }

    /**
     * http://en.wikipedia.org/wiki/Flesch-Kincaid_Readability_Test
     * @return the Flesch_Reading Ease value for the text
     */
    public static double calculateFleschReadingEase(final int words, final int sentences, final int syllables) {
        if (sentences == 0 || words == 0) return Double.MIN_VALUE;

        return 206.835 - 1.015 * words / sentences - 84.6 * syllables / words;
    }

    /**
     * http://en.wikipedia.org/wiki/Flesch-Kincaid_Readability_Test
     * @return the Flesch-Kincaid_Readability_Test value for the text
     */
    public static double calculateFleschKincaidGradeLevel(final int words, final int sentences, final int syllables) {
        if (sentences == 0 || words == 0) return Double.MIN_VALUE;

        return 0.39 * words / sentences + 11.8 * syllables / words - 15.59;
    }

    /**
     * http://en.wikipedia.org/wiki/Automated_Readability_Index
     * @return the Automated Readability Index for text
     */
    public static double calculateAutomatedReadabilityIndex(final int characters, final int words, final int sentences) {
        if (words == 0 || sentences == 0) return Double.MIN_VALUE;

        return 4.71 * characters / words + 0.5 * words / sentences - 21.43;
    }

    /**
     * The original written by Panos Ipeirotis is wrong. This is corrected.
     * http://en.wikipedia.org/wiki/Gunning-Fog_Index
     * @return the Gunning-Fog Index for text
     */
    public static double calculateGunningFog(final int words, final int sentences, final int complex) {
        if (words == 0 || sentences == 0) return Double.MIN_VALUE;

        return 0.4 * (words / (double)sentences + 100 * (complex / (double)words));
    }

    /**
     * The original written by Panos Ipeirotis is wrong. This is corrected.
     * http://en.wikipedia.org/wiki/Coleman-Liau_Index
     * @return The Coleman-Liau_Index value for the text
     */
    public static double calculateColemanLiau(final int words, final int characters, final int sentences) {
        if (words == 0) return Double.MIN_VALUE;

        double num100Words = words / 100.0;
        return 0.0588 * ( characters / num100Words ) - (0.296 * (sentences / num100Words)) - 15.8;
    }

}
