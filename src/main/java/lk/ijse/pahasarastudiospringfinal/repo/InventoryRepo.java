package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByItemName(String itemName);

    boolean existsByItemName(String itemName);

    // ✅ Find items running low (Critical for Studio Admin)
    List<Inventory> findByQuantityLessThanEqual(int reorderLevel);

    // ✅ Search items by name fragment (e.g., searching "Paper" returns "A4 Paper", "Glossy Paper")
    List<Inventory> findByItemNameContainingIgnoreCase(String itemName);

    // ✅ Filter by Status (e.g., "OUT_OF_STOCK", "IN_STOCK")
    List<Inventory> findByStatus(String status);
}