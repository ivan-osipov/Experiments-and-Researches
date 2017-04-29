package jenetics_lib_research.codec_using;

import org.jenetics.*;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.Problem;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.jenetics.engine.limit.bySteadyFitness;
import static org.jenetics.internal.math.random.indexes;

public class CodecUsingExample {

    static class DomainModel {
        private int x;
        private int y;

        public DomainModel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public DomainModel(DomainModel original) {
            this.x = original.x;
            this.y = original.y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%s, %s]", x, y);
        }
    }

    public static void main(String[] args) {

        Engine<AnyGene<DomainModel>, Integer> engine = Engine
                .builder(new Problem<DomainModel, AnyGene<DomainModel>, Integer>() {
                    @Override
                    public Function<DomainModel, Integer> fitness() {
                        return (instance -> instance.getX() + instance.getY());
                    }

                    @Override
                    public Codec<DomainModel, AnyGene<DomainModel>> codec() {
                        AnyChromosome<DomainModel> chromosome = AnyChromosome.of(() -> {
                            Random random = RandomRegistry.getRandom();
                            return new DomainModel(random.nextInt(1000) - 500, random.nextInt(1000) - 500);
                        });
                        return Codec.of(Genotype.of(chromosome), gt -> gt.getChromosome().getGene().getAllele());
                    }
                })
                .alterers(new Mutator<AnyGene<DomainModel>, Integer>() {
                    @Override
                    protected int mutate(MSeq<AnyGene<DomainModel>> genes, double p) {
                        return (int)indexes(RandomRegistry.getRandom(), genes.length(), p)
                                .peek(i -> {
                                    DomainModel domainModel = new DomainModel(genes.get(i).getAllele());
                                    genes.set(i, AnyGene.of(() -> domainModel));
                                    if(RandomRegistry.getRandom().nextBoolean()) {
                                        domainModel.setX(domainModel.getX() + RandomRegistry.getRandom().nextInt(10));
                                    } else {
                                        domainModel.setY(domainModel.getY() + RandomRegistry.getRandom().nextInt(10));
                                    }
                                })
                                .count();
                    }
                })
                .maximizing()
                .offspringSize(20)
                .build();

        Phenotype<AnyGene<DomainModel>, Integer> result = engine.stream()
//                .limit(bySteadyFitness(20))
                .limit(1000)
                .collect(EvolutionResult.toBestPhenotype());

        System.out.println("Result:\n\t" + result);
        System.out.println("\tGeneration: " + result.getGeneration());
        System.out.println(result.getGenotype().getNumberOfGenes());
        System.out.println(result.getGenotype().getGene().getAllele());
        System.out.println(result.getFitness());
    }

}
