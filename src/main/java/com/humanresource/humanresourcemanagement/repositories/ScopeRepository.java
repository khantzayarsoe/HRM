// repositories/ScopeRepository.java
package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long> {
    Optional<Scope> findByName(String name);
}