public class WordInfo {
    public String word;
    public String meaning;

    public WordInfo(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return  "Word: " + word + "; "
                + "Meaning: " + meaning;
    }
}
