package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    List<Equipment> findByStatus(String status);

    List<Equipment> findByItemNameContainingIgnoreCase(String itemName);

    long countByStatus(String status);
}
