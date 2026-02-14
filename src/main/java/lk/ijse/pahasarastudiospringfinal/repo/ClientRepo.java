package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    // ✅ FIXED: Renamed to match the Entity field 'phoneNumber'
    Optional<Client> findByPhoneNumber(String phoneNumber);

    // ✅ FIXED: Renamed to match the Entity field 'phoneNumber'
    boolean existsByPhoneNumber(String phoneNumber);
}