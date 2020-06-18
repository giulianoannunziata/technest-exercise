package es.technest.technestexercise.controller;

import es.technest.technestexercise.helper.CurrencyCodes;
import es.technest.technestexercise.model.Account;
import es.technest.technestexercise.service.IAccountService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.javamoney.moneta.Money;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class AccountController {

    private final IAccountService service;


    public AccountController(IAccountService service) {
        this.service = service;
    }

    @GetMapping("/account")
    public List<Account> getAllAccounts() {
        return service.findAllAccounts();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity.HeadersBuilder<?> getSpecificAccount(@PathVariable(value = "id") String id) throws AccountNotFoundException {
        Account result = service.findAccount(id);
        if (result != null) {
            return ResponseEntity.notFound();
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/account")
    public ResponseEntity.BodyBuilder addUser(@RequestBody JSONObject request) {
        if (!isValidJSON(request)) {
            return ResponseEntity.badRequest();
        }

        String accountName = request.getString("name");
        BigDecimal money = request.getJSONObject("money").getBigDecimal("money");
        Currency currency = Currency.getInstance(request.getJSONObject("currency").getString("currency"));
        boolean treasury = request.getBoolean("treasury");

        CurrencyUnit currencyUnit = Monetary.getCurrency(String.valueOf(CurrencyCodes.valueOf(currency.getCurrencyCode())));

        service.createAccount(accountName, currency, money , treasury);
        return ResponseEntity.ok();
    }

    private boolean isValidJSON(JSONObject request) {
        try {

            JSONObject jsonSchema = new JSONObject(
                    new JSONTokener(this.getClass().getResourceAsStream("/schema.json"))
            );
            Schema schema =  SchemaLoader.load(jsonSchema);
            schema.validate(request);
            return true;
        } catch (ValidationException exception) {
            return false;
        }
    }

    @PostMapping("/transfer/{acconuntX}/{accountY}/{currency}/{balance}")
    public ResponseEntity<String> transferMoney(
            @PathVariable(value = "accountX") Account accountX,
            @PathVariable(value = "accountY") Account accountY,
            @PathVariable(value = "currency") Currency currency,
            @PathVariable(value = "balance") Money balance
    ) {

        // TODO CHECK IF ANY ACCOUNT IS TREASURY

        // TODO ALLOW TRANSFER OR REJECT BASED ON BALANCE

        // TODO LAUNCH H2 DB on startup


        return ResponseEntity.ok("WIP");
    }


}
