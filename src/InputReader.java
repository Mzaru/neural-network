import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class InputReader {

    private Map<String, List<double[]>> vectors = new HashMap<>();

    public static double getLearningRate() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter learning rate");
        double lr = in.nextDouble();
        return lr;
    }

    public InputReader(Map<String, Path> folders) {
        for (String s : folders.keySet()) {
            List<double[]> inputs = new ArrayList<>();
            vectors.put(s, inputs);
            try {
                List<Path> texts = Files.list(folders.get(s)).collect(Collectors.toList());
                for (Path text : texts) {
                    InputProcessor proc = new InputProcessor();
                    inputFile(text, proc);
                    inputs.add(proc.getInput());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void inputFile(Path file, InputProcessor proc) {
        String line;
        try (BufferedReader in = new BufferedReader(new FileReader(file.toFile()))) {
            while ((line = in.readLine()) != null) {
                proc.countLetters(InputProcessor.normalize(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<double[]>> getVectors() {
        return vectors;
    }
}
