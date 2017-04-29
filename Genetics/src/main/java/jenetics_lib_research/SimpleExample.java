package jenetics_lib_research;

import org.jenetics.BitChromosome;
import org.jenetics.BitGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

public class SimpleExample {

    private static Integer evaluation(Genotype<BitGene> gt) {
        //returns amount of true bits
        return ((BitChromosome) gt.getChromosome()).bitCount();
    }

    public static void main(String[] args) {
        Factory<Genotype<BitGene>> gtf = Genotype.of(BitChromosome.of(14, 0.5));

        Engine<BitGene, Integer> engine = Engine
                .builder(SimpleExample::evaluation, gtf)
                .build();

        Genotype<BitGene> result = engine.stream()
                .limit(100)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println("Result:\n\t" + result);
    }

}
