package com.unmeshc.security.repositories;

import com.unmeshc.security.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by uc on 10/9/2019
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
