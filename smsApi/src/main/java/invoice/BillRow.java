package invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author chopra
 * 15/02/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRow {
    private String aggrementNumber;
    private String customerName;
    private int netAmount;
    private double grid;
    private double incentive;
    private double pfDeductionFormula;
    private double netPayout;

    public String getAggrementNumber() {
        return aggrementNumber;
    }

    public void setAggrementNumber(String aggrementNumber) {
        this.aggrementNumber = aggrementNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(int netAmount) {
        this.netAmount = netAmount;
    }

    public double getGrid() {
        return grid;
    }

    public void setGrid(double grid) {
        this.grid = grid;
    }

    public double getIncentive() {
        return incentive;
    }

    public void setIncentive(double incentive) {
        this.incentive = incentive;
    }

    public double getNetPayout() {
        return netPayout;
    }

    public void setNetPayout(double netPayout) {
        this.netPayout = netPayout;
    }

    public double getPfDeductionFormula() {
        return pfDeductionFormula;
    }

    public void setPfDeductionFormula(double pfDeductionFormula) {
        this.pfDeductionFormula = pfDeductionFormula;
    }

    @Override
    public String toString() {
        return "BillRow{" +
                "aggrementNumber='" + aggrementNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", netAmount=" + netAmount +
                ", grid=" + grid +
                ", incentive=" + incentive +
                ", pfDeductionFormula=" + pfDeductionFormula +
                ", netPayout=" + netPayout +
                '}';
    }
}
