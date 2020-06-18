package es.technest.technestexercise.service;

import es.technest.technestexercise.model.Account;
import es.technest.technestexercise.repository.AccountsRepository;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    final AccountsRepository repo;

    public AccountService(AccountsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Account createAccount(String name, Currency currency, BigDecimal money, Boolean treasury) {
        Account account = findAccount(name);
        if (account == null) {
            return repo.saveAndFlush(new Account(
                    name,
                    currency.getCurrencyCode(),
                    money,
                    treasury));
        }

        return null;
    }

    @Override
    public Account findAccount(String name) {
        Optional<Account> accountOptional = repo.findById(name);
        return accountOptional.orElse(null);
    }

    @Override
    public List<Account> findAllAccounts() {
       return repo.findAll();
    }

    @Override
    public Account accessAccount(String accountName, String newName, Currency newCurrency, Money newMoney) {
        Account account;
        Optional<Account> accountOptional = repo.findById(accountName);
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
        } else {
            return null;
        }

        if (newName != null) {
            account.setName(accountName);
        }

        if (newCurrency != null) {
            account.setCurrency(newCurrency.getCurrencyCode());
        }

        if (newMoney != null) {
            account.setMoney(newMoney.getNumberStripped());
        }

        return repo.saveAndFlush(account);
    }

    @Override
    public boolean transferMoney(String sendersName, String receiversName, Currency currency, Money amount) {
        Account sendersAccount = findAccount(sendersName);
        Account receiversAccount = findAccount(receiversName);

        // Must be valid existing accounts
        if (sendersAccount == null || receiversAccount == null) {
            return false;
        }

        //Accounts must be different
        if (sendersAccount.getName().equals(receiversAccount.getName())) {
            return false;
        }

        //Accounts must have same currency
        if (!(sendersAccount.getCurrency().equals(receiversAccount.getCurrency()))) {
            return false;
        }

        //Senders account must be checked for treasury
        if (sendersAccount.getTreasury()) {
            return performTransfer(sendersAccount, receiversAccount, amount);

            //Money transfer should be made if balance available
        } else if (sendersAccount.getMoney().compareTo(amount.getNumberStripped()) >= 0) {
            return performTransfer(sendersAccount, receiversAccount, amount);
        }

        //Money transfer should not be made due to lack of balance
        return false;
    }

    private boolean performTransfer(Account sendersAccount, Account receiversAccount, Money amount) {
        BigDecimal sendersAccountMoney = sendersAccount.getMoney();
        BigDecimal receiversAccountMoney = receiversAccount.getMoney();

        sendersAccount.setMoney(sendersAccountMoney.subtract(receiversAccountMoney));
        receiversAccount.setMoney(receiversAccountMoney.add(sendersAccountMoney));

        repo.save(sendersAccount);
        repo.save(receiversAccount);

        repo.flush();
        return true;
    }

}
