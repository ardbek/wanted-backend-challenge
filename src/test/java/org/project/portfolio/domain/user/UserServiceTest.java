package org.project.portfolio.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.portfolio.domain.user.helper.UserHelper;
import org.project.portfolio.domain.user.mapper.UserMapper;
import org.project.portfolio.domain.user.repository.UserRepository;
import org.project.portfolio.domain.user.service.UserService;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserHelper userHelper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입에_성공하면_회원번호를_반환한다() {


    }


}
