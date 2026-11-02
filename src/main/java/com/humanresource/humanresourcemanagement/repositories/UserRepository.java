package com.humanresource.humanresourcemanagement.repositories;

import com.humanresource.humanresourcemanagement.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//not use
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update users set email_verified=true where email = :email")
    void setEmailVerifiedToTrue(@Param(value = "email") String email);

}
