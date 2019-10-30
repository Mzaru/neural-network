import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LanguageClassifier {
    private List<String> languages = new ArrayList<>();
    private Map<String, Path> folders = new HashMap<>();
    private Network network;

    public LanguageClassifier() {
        Path folder = Paths.get("Languages");
        try {
            Files.list(folder).forEach(path -> {
                languages.add(path.getFileName().toString());
                folders.put(path.getFileName().toString(), path);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        network = new Network(languages.size(), 26, InputReader.getLearningRate());
    }


    public void train(InputReader r) {
        for (String s : languages) {
            boolean[] decision = new boolean[languages.size()];
            decision[languages.indexOf(s)] = true;
            for (double[] input : r.getVectors().get(s)) {
                network.train(input, decision);
            }
        }
    }

    public String compute(double[] input) {
        return languages.get(network.compute(input));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter epochs ");
        int epoch = in.nextInt();
        in.nextLine();

        long start = System.nanoTime();
        LanguageClassifier l = new LanguageClassifier();
        InputReader train = new InputReader(l.folders);
        System.out.println(l.network);
        for (int i = 0; i < epoch; i++) {
            l.train(train);
        }
        long end = System.nanoTime();
        long res = (end - start) / 1_000_000_000;
        System.out.println("It took " + res + " seconds");
        System.out.println(l.network);

        while (true) {
            System.out.println("Would you like to enter input manually? Y/N ");
            String line = in.nextLine();
            if (line.equalsIgnoreCase("y")) {
                line = in.nextLine();
                System.out.println("This must be " + l.compute(new InputProcessor(InputProcessor.normalize(line)).getInput()));
                System.exit(0);
            } else if (line.equalsIgnoreCase("n")) {
                break;
            }
        }

        InputProcessor p = new InputProcessor();
        InputReader.inputFile(Paths.get("test2.txt"), p);
        System.out.println(l.compute(p.getInput()));


    }
}
