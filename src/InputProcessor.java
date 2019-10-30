import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class InputProcessor {

    private double[] input = new double[26];
    private double count = 0;

//    public static void main(String[] args) {
//        String s = "The diacritic letters ä, ö and ü";
//        InputProcessor i = new InputProcessor();
//        i.countLetters(normalize(s));
//        System.out.println(normalize(s));
//        System.out.println(Arrays.toString(i.getInput()));
//
//    }


    public InputProcessor() {

    }

    public InputProcessor(String s) {
        this.countLetters(s);
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String normalize(String str) {
        str = deAccent(str);
        str = str.toLowerCase();
        str = str.replaceAll("\\s", "");
        return str;
    }

    public void countLetters(String str) {
        int[] result = new int[26];

        for (int i = 0; i < str.length(); ++i) {
            char charAt = str.charAt(i);
            if (charAt > 96 && charAt < 123) {
                result[charAt - 97] += 1;
                ++count;
            }
        }
        for (int i = 0; i < input.length; ++i) {
            input[i] += result[i];
        }
    }

    public double[] getInput() {
        input = Arrays.stream(input).map(i -> i / count).toArray();
        return input;
    }
}
