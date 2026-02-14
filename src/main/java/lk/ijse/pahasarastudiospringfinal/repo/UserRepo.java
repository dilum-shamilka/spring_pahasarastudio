package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> { // ✅ Changed to Long to match bigint

    // Finds user by their unique username
    Optional<User> findByUsername(String username);

    // Checks if an email already exists in the database
    boolean existsByEmail(String email);

    // Checks if a username already exists
    boolean existsByUsername(String username);

    // Finds user by their unique email
    Optional<User> findByEmail(String email);
}