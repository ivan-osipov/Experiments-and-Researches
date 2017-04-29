package jenetics_lib_research.uniform_resource_distribution;

import jenetics_lib_research.uniform_resource_distribution.definition.ResourceDistributionMutator;
import jenetics_lib_research.uniform_resource_distribution.definition.UniformResourceDistributionProblem;
import jenetics_lib_research.uniform_resource_distribution.model.DistributionUnit;
import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.limit;

public class Main {

    public static void main(String[] args) {

        //how much resources need for customers
        int[] customersNeeds = {2, 1, 2, 4};

        //how much resources there is
        int[] resourceCapacity = {1, 2, 2, 4};

        //availability [customer][resource]
        boolean[][] availabilityMatrix = {
                {false, true, false, true},
                {true, false, true, false},
                {true, true, true, false},
                {true, false, true, true}
        };

        //    Source data
        //
        //      1   2   2   4
        //  2   -   +   -   +
        //  1   +   -   +   -
        //  2   +   +   +   -
        //  4   +   -   +   +

        UniformResourceDistributionProblem problem = new UniformResourceDistributionProblem(
                customersNeeds,
                resourceCapacity,
                availabilityMatrix);

        Engine<AnyGene<DistributionUnit>, Integer> engine = Engine
                .builder(problem)
                .populationSize(100)
                .offspringFraction(0.4)
                .survivorsFraction(0.4)
                .selector(new TournamentSelector<>())
                .alterers(new ResourceDistributionMutator(problem.getAvailabilities()))
                .minimizing()
                .build();

        Phenotype<AnyGene<DistributionUnit>, Integer> result = engine.stream()
                .limit(limit.bySteadyFitness(20))
                .limit(100)
                .collect(EvolutionResult.toBestPhenotype());

        printResults(customersNeeds, resourceCapacity, result);
    }

    private static void printResults(int[] customersNeeds, int[] resourceCapacity, Phenotype<AnyGene<DistributionUnit>, Integer> result) {
        System.out.println("Result:\n\t" + result);
        System.out.println("\tGeneration: " + result.getGeneration());
        Genotype<AnyGene<DistributionUnit>> genotype = result.getGenotype();

        for (int customerIdx = 0; customerIdx < customersNeeds.length; customerIdx++) {
            Chromosome<AnyGene<DistributionUnit>> customerChromosome = genotype.getChromosome(customerIdx);
            AnyGene<DistributionUnit> distributionUnitGene = customerChromosome.getGene();
            DistributionUnit allele = distributionUnitGene.getAllele();
            resourceCapacity[allele.getResourceIdx()] -= customersNeeds[allele.getCustomerIdx()];
        }
        System.out.println("Resource capacity");
        for (int resourceIdx = 0; resourceIdx < resourceCapacity.length; resourceIdx++) {
            System.out.print(resourceCapacity[resourceIdx] + "\t");
        }
    }

}
