package se.jennifer.preorderjenstarball.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.preorderjenstarball.model.BallStock;

import java.util.Optional;

public interface BallStockRepository extends JpaRepository<BallStock, Long>{

    Optional<BallStock> findByType(String type);
}
