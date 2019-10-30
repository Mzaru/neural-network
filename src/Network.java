import java.util.ArrayList;
import java.util.List;

public class Network {
    private List<Perceptron> neurons = new ArrayList<>();

    public Network(int numberOfNeurons, int numberOfInputs, double learningRate) {
        for (int i = 0; i < numberOfNeurons; i++) {
            neurons.add(new Perceptron(numberOfInputs, learningRate));
        }
    }

    public void train(double[] input, boolean[] decision) {
        if (input.length != neurons.get(0).getWeights().length || decision.length != neurons.size()) {
            throw new IllegalArgumentException("The number of inputs or number of answers is incorrect");
        }
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).train(input, decision[i]);
        }
    }

    public int compute(double[] input) {
        double[] net = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            net[i] = neurons.get(i).compute(input);
        }
        double max = net[0];
        int pos = 0;
        for (int i = 0; i < net.length; i++) {
            if (net[i] > max) {
                max = net[i];
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public String toString() {
        return neurons.toString();
    }
}
