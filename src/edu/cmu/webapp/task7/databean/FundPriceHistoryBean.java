package edu.cmu.webapp.task7.databean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FUND_PRICE_HISTORY")
@IdClass(FundPriceHistoryPrimaryKeys.class)
public class FundPriceHistoryBean implements Serializable {
    @Id
    @Column(name = "FUND_ID")
    private int fundId;
    @Id
    @Column(name = "PRICE_DATE")
    private String priceDate;
    @Column(name = "PRICE")
    private double price;

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
