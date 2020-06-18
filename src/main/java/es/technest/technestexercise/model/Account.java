package es.technest.technestexercise.model;

import org.javamoney.moneta.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "account")
public class Account {

    /**
     * - Name:	    String
     * - Currency:	Currency
     * - Balance:	Money
     * - Treasury:	Boolean
     */

    @Id
    private String name;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal money;

    @Column(nullable = false)
    private final boolean treasury;

    public Account(String name, String currency, BigDecimal money, Boolean treasury) {
        this.name = name;
        this.currency = currency;
        this.money = money;
        this.treasury = treasury;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getTreasury() {
        return treasury;
    }
}
