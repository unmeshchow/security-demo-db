package com.unmeshc.security.repositories;

import com.unmeshc.security.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by uc on 10/9/2019
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
