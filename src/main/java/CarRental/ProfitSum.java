package CarRental;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class ProfitSum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private BigDecimal profit;

    public ProfitSum() {
    }

    public ProfitSum(double profit) {
        this.profit = BigDecimal.valueOf(profit);
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = BigDecimal.valueOf(profit);
    }
}
