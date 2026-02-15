package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
