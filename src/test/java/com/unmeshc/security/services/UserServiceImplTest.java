package com.unmeshc.security.services;

import com.unmeshc.security.domain.Role;
import com.unmeshc.security.domain.User;
import com.unmeshc.security.repositories.RoleRepository;
import com.unmeshc.security.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserByEmailFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(
                Optional.of(User.builder().id(1L).build()));

        User foundUser = userService.findUserByEmail("unmeshchow@gmail.com");

        assertThat(foundUser).isNotNull();
    }

    @Test
    public void findUserByEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        User foundUser = userService.findUserByEmail("unmeshchow@gmail.com");

        assertThat(foundUser).isNull();
    }

    @Test
    public void saveUser() {
        Role admin = Role.builder().id(1L).name("ADMIN").build();
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        User user =
                User.builder().id(1L).password("unmesh").active(true).roles(roles).build();

        when(passwordEncoder.encode(anyString())).thenReturn("unmesh");
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(admin));
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(
                User.builder().password("unmesh").roles(new HashSet<>()).build());

        assertThat(savedUser).isNotNull();
    }
}