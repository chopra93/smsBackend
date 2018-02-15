package invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author chopra
 * 15/02/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bill {
    private List<BillRow> billRowList;
    private double total;
    private double totalPayout;
    private String amountInWords;

    public List<BillRow> getBillRowList() {
        return billRowList;
    }

    public void setBillRowList(List<BillRow> billRowList) {
        this.billRowList = billRowList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billRowList=" + billRowList +
                ", total=" + total +
                ", totalPayout=" + totalPayout +
                ", amountInWords='" + amountInWords + '\'' +
                '}';
    }
}
