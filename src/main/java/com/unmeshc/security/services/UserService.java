package com.unmeshc.security.services;

import com.unmeshc.security.domain.User;

/**
 * Created by uc on 10/9/2019
 */
public interface UserService {

    User findUserByEmail(String email);

    User saveUser(User user);
}
