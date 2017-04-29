package jenetics_lib_research;

import org.jenetics.DoubleChromosome;
import org.jenetics.DoubleGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

public class MiddlePointSearch {

    private static final double HEIGHT = 20;
    private static final double WIDTH = 20;

    /**
     * equidistant point is better
     */
    private static Double evaluation(Genotype<DoubleGene> gt) {
        DoubleGene yGene = gt.getChromosome(0).getGene();
        DoubleGene xGene = gt.getChromosome(1).getGene();
        return yGene.doubleValue() * (HEIGHT - yGene.doubleValue())
                + xGene.doubleValue() * (WIDTH - xGene.doubleValue());
    }

    /**
     * Will look for point between [0, 0] and [WIDTH, HEIGHT]
     * In this case HEIGHT = 20, WIDTH = 20, the middle point is [10, 10]
     */
    public static void main(String[] args) {

        Factory<Genotype<DoubleGene>> gtf = Genotype.of(
                DoubleChromosome.of(0, HEIGHT),
                DoubleChromosome.of(0, WIDTH));

        Engine<DoubleGene, Double> engine = Engine
                .builder(MiddlePointSearch::evaluation, gtf)
                .maximizing()
                .offspringSize(10)
                .build();

        Genotype<DoubleGene> result = engine.stream()
                .limit(10)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println("Result:\n\t" + result);
    }

}
