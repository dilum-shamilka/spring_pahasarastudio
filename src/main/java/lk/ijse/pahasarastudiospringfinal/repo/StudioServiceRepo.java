package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.StudioServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudioServiceRepo extends JpaRepository<StudioServiceEntity, Long> {

    Optional<StudioServiceEntity> findByServiceName(String serviceName);

    boolean existsByServiceName(String serviceName);

    // ✅ Added: To retrieve only ACTIVE services for the booking page
    List<StudioServiceEntity> findByStatus(String status);

    // ✅ Added: To search services by name (e.g., searching "Wed" returns "Wedding Photography")
    List<StudioServiceEntity> findByServiceNameContainingIgnoreCase(String serviceName);
}