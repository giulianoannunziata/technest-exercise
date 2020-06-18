package es.technest.technestexercise.repository;

import es.technest.technestexercise.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, String> {

}
