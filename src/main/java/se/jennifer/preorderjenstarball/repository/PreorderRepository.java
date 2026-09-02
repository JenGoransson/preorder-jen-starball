package se.jennifer.preorderjenstarball.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.preorderjenstarball.model.Preorder;

public interface PreorderRepository extends JpaRepository<Preorder,Long> {
}
