package lk.ijse.pahasarastudiospringfinal.repo;

import lk.ijse.pahasarastudiospringfinal.entity.StudioServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioServiceRepo extends JpaRepository<StudioServiceEntity, Long> {
}