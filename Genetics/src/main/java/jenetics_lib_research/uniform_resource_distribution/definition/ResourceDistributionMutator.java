package jenetics_lib_research.uniform_resource_distribution.definition;

import jenetics_lib_research.uniform_resource_distribution.model.DistributionUnit;
import org.jenetics.AnyGene;
import org.jenetics.Mutator;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;

import java.util.List;
import java.util.Random;

import static org.jenetics.internal.math.random.indexes;

public class ResourceDistributionMutator extends Mutator<AnyGene<DistributionUnit>, Integer> {

    private List<List<Integer>> availabilities;

    public ResourceDistributionMutator(List<List<Integer>> availabilities) {
        this.availabilities = availabilities;
    }

    @Override
    protected int mutate(MSeq<AnyGene<DistributionUnit>> genes, double p) {
        Random random = RandomRegistry.getRandom();
        return (int) indexes(random, genes.length(), p)
                .peek(i -> {
                    DistributionUnit originalAllele = genes.get(i).getAllele();
                    int customerIdx = originalAllele.getCustomerIdx();
                    genes.set(i, AnyGene.of(
                            () -> {
                                List<Integer> availabilities = this.availabilities.get(customerIdx);
                                return new DistributionUnit(customerIdx, availabilities.get(random.nextInt(availabilities.size())));
                            }));
                })
                .count();
    }
}
