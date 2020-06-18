package es.technest.technestexercise.service;

import es.technest.technestexercise.model.Account;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface IAccountService {

    Account createAccount(String name, Currency currency, BigDecimal money, Boolean treasury);

    Account findAccount(String name);

    List<Account> findAllAccounts();

    Account accessAccount(String accountName, String newName, Currency newCurrency, Money newMoney);

    boolean transferMoney(String sendersName, String receiversName, Currency currency, Money money);

}
