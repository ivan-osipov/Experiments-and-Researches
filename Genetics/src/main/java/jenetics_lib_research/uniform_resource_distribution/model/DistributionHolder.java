package jenetics_lib_research.uniform_resource_distribution.model;

import java.util.List;

public class DistributionHolder {
    private List<DistributionUnit> distributionUnits;

    private int[] remainedCustomerNeeds;
    private int[] remainedResourceAvailabilities;

    public DistributionHolder(List<DistributionUnit> distributionUnits, int[] remainedCustomerNeeds, int[] remainedResourceAvailabilities) {
        this.distributionUnits = distributionUnits;
        this.remainedCustomerNeeds = remainedCustomerNeeds;
        this.remainedResourceAvailabilities = remainedResourceAvailabilities;
    }

    public List<DistributionUnit> getDistributionUnits() {
        return distributionUnits;
    }

    public int[] getRemainedCustomerNeeds() {
        return remainedCustomerNeeds;
    }

    public int[] getRemainedResourceAvailabilities() {
        return remainedResourceAvailabilities;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Distribution Holder\n");
        builder.append(distributionUnits).append("\n");
        builder.append("Remained customer needs\n");
        for (int customerIdx = 0; customerIdx < remainedCustomerNeeds.length; customerIdx++) {
            builder.append(remainedCustomerNeeds[customerIdx]).append("\t");
        }
        builder.append("\n\n");
        builder.append("Remained resource availabilities\n");
        for (int resourceIdx = 0; resourceIdx < remainedResourceAvailabilities.length; resourceIdx++) {
            builder.append(remainedResourceAvailabilities[resourceIdx]).append("\t");
        }
        builder.append("\n\n");
        return builder.toString();
    }
}
