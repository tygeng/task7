package edu.cmu.webapp.task7.databean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *For the use of printing customer portfolio in "ViewCustomerAccount" & "ViewMyAccount" action.
 */
@Entity
@Table(name = "CUSTOMER_ACCOUNT_ITEM_BEAN")
public class CustomerAccountItemBean {
    @Id
    @Column(name = "FUND_NAME")
    private String fundName;
    @Column(name = "SYMBOL")
    private String symbol;
    @Column(name = "SHARE")
    private String share;
    @Column(name = "AMOUNT")
    private String amount;


    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
