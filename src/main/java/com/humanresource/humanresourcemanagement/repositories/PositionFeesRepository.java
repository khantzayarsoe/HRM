// repositories/PositionFeesRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.PositionFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PositionFeesRepository extends JpaRepository<PositionFees, Long> {
    Optional<PositionFees> findByPositionId(Long positionId);
}