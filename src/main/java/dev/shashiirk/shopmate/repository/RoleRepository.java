package dev.shashiirk.shopmate.repository;

import dev.shashiirk.shopmate.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the {@link Role} entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
