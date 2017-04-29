package jenetics_lib_research.uniform_resource_distribution.definition;

import jenetics_lib_research.uniform_resource_distribution.model.DistributionHolder;
import jenetics_lib_research.uniform_resource_distribution.model.DistributionUnit;
import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Chromosome;
import org.jenetics.Genotype;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Problem;
import org.jenetics.util.RandomRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public class UniformResourceDistributionProblem implements Problem<DistributionHolder, AnyGene<DistributionUnit>, Integer> {

    private static final int BAD_PLACEMENT_PENALTY = 2000;

    private final int[] customersNeeds;
    private final int[] resourceCapacities;
    private final List<List<Integer>> availabilities;

    public UniformResourceDistributionProblem(int[] customersNeeds,
                                              int[] resourceCapacities,
                                              boolean[][] inputAvailabilityMatrix) {

        this.customersNeeds = Arrays.copyOf(customersNeeds, customersNeeds.length);
        this.resourceCapacities = Arrays.copyOf(resourceCapacities, resourceCapacities.length);

        boolean[][] availabilityMatrix = Arrays.copyOf(inputAvailabilityMatrix, inputAvailabilityMatrix.length);
        this.availabilities = new ArrayList<>(availabilityMatrix.length);

        fillAvailabilities(availabilityMatrix);
    }

    private void fillAvailabilities(boolean[][] availabilityMatrix) {
        for (int customerIdx = 0; customerIdx < availabilityMatrix.length; customerIdx++) {

            boolean[] resourceAvailability = availabilityMatrix[customerIdx];
            this.availabilities.add(customerIdx, new ArrayList<>());

            for (int resourceIdx = 0; resourceIdx < resourceAvailability.length; resourceIdx++) {

                availabilityMatrix[customerIdx] = resourceAvailability;
                if (availabilityMatrix[customerIdx][resourceIdx]) {
                    this.availabilities.get(customerIdx).add(resourceIdx);
                }
            }
        }
    }

    @Override
    public Function<DistributionHolder, Integer> fitness() {
        return (this::estimate);
    }

    private Integer estimate(DistributionHolder distributionHolder) {

        int[] usedResourceCapacities = new int[this.resourceCapacities.length];
        int[] coveredCustomersNeeds = new int[this.customersNeeds.length];

        List<DistributionUnit> resourceDistributions = distributionHolder.getDistributionUnits();

        boolean distributionIsSuccessful = true;
        for (int i = 0; i < coveredCustomersNeeds.length; i++) {

            DistributionUnit allele = resourceDistributions.get(i);
            int customerIdx = allele.getCustomerIdx();
            int resourceIdx = allele.getResourceIdx();

            boolean customerHasResources = (coveredCustomersNeeds[customerIdx] > 0);

            int remainedResourceCapacity = this.resourceCapacities[resourceIdx] - usedResourceCapacities[resourceIdx];
            boolean resourceIsNotEnough = this.customersNeeds[customerIdx] > remainedResourceCapacity;

            if (customerHasResources || resourceIsNotEnough) {
                distributionIsSuccessful = false;
            }

            usedResourceCapacities[resourceIdx] += this.customersNeeds[customerIdx];
            coveredCustomersNeeds[customerIdx] += this.customersNeeds[customerIdx];

            if (!distributionIsSuccessful) break;
        }

        int maxDeviation = IntStream.range(0, this.resourceCapacities.length)
                .map(idx -> Math.abs(resourceCapacities[idx] - usedResourceCapacities[idx]))
                .max()
                .orElseThrow(IllegalStateException::new);

        if (!distributionIsSuccessful) {
            return maxDeviation + BAD_PLACEMENT_PENALTY;
        }

        return maxDeviation;
    }

    @Override
    public Codec<DistributionHolder, AnyGene<DistributionUnit>> codec() {
        Random random = RandomRegistry.getRandom();

        List<Chromosome<AnyGene<DistributionUnit>>> chromosomes = new ArrayList<>();
        for (int customerNeedIdx = 0; customerNeedIdx < customersNeeds.length; customerNeedIdx++) {
            int customerIdx = customerNeedIdx;
            AnyChromosome<DistributionUnit> chromosome = AnyChromosome.of(
                    () -> {
                        List<Integer> availabilities = this.availabilities.get(customerIdx);
                        int resourceNum = random.nextInt(availabilities.size());

                        return new DistributionUnit(customerIdx, availabilities.get(resourceNum));
                    });
            chromosomes.add(chromosome);
        }

        Function<Genotype<AnyGene<DistributionUnit>>, DistributionHolder> decoder = (gt) -> {
            List<DistributionUnit> distributionUnits = new ArrayList<>(customersNeeds.length);

            int[] remainedCustomerNeeds = Arrays.copyOf(customersNeeds, customersNeeds.length);
            int[] remainedResourceAvailabilities = Arrays.copyOf(resourceCapacities, resourceCapacities.length);

            for (int customerIdx = 0; customerIdx < customersNeeds.length; customerIdx++) {
                Chromosome<AnyGene<DistributionUnit>> customerChromosome = gt.getChromosome(customerIdx);
                DistributionUnit allele = new DistributionUnit(customerChromosome.getGene().getAllele());
                distributionUnits.add(allele);
                remainedCustomerNeeds[allele.getCustomerIdx()] -= customersNeeds[allele.getCustomerIdx()];
                remainedResourceAvailabilities[allele.getResourceIdx()] -= customersNeeds[allele.getCustomerIdx()];
            }

            return new DistributionHolder(distributionUnits, remainedCustomerNeeds, remainedResourceAvailabilities);
        };

        return Codec.of(Genotype.of(chromosomes), decoder);
    }

    public List<List<Integer>> getAvailabilities() {
        return availabilities;
    }
}