package es.technest.technestexercise.entity;


import es.technest.technestexercise.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class ResponseEntity extends org.springframework.http.ResponseEntity<Account> {

    public ResponseEntity(HttpStatus status) {
        super(status);
    }

    public ResponseEntity(Account body, HttpStatus status) {
        super(body, status);
    }

    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public ResponseEntity(Account body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
