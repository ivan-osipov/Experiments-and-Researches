package jenetics_lib_research.uniform_resource_distribution.model;

public class DistributionUnit {
    private int customerIdx;
    private int resourceIdx;

    public DistributionUnit(int customerIdx, int resourceIdx) {
        this.customerIdx = customerIdx;
        this.resourceIdx = resourceIdx;
    }

    public DistributionUnit(DistributionUnit original) {
        this(original.getCustomerIdx(), original.getResourceIdx());
    }

    public int getCustomerIdx() {
        return customerIdx;
    }

    public int getResourceIdx() {
        return resourceIdx;
    }

    @Override
    public String toString() {
        return String.format("{c : %s, r : %s}", customerIdx, resourceIdx);
    }

    public void setResourceIdx(int resourceIdx) {
        this.resourceIdx = resourceIdx;
    }
}
