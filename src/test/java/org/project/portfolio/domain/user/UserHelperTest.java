package org.project.portfolio.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.project.portfolio.domain.user.domain.User;
import org.project.portfolio.domain.user.exception.DuplicateNickname;
import org.project.portfolio.domain.user.exception.DuplicateUser;
import org.project.portfolio.domain.user.helper.UserHelper;
import org.project.portfolio.domain.user.repository.UserRepository;
import org.project.portfolio.global.common.ValidationUtil;

public class UserHelperTest {

    @InjectMocks
    private UserHelper userHelper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationUtil validationUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 이메일이_중복되면_예외가_발생한다() {
        // given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // when, then
        assertThatThrownBy(() -> userHelper.isEmailDuplicate(email))
                .isInstanceOf(DuplicateUser.class);
    }

    @Test
    void 이메일이_중복되지_않으면_예외가_발생하지_않는다() {
        // given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when, then
        userHelper.isEmailDuplicate(email);
    }

    @Test
    void 닉네임이_중복되면_예외가_발생한다() {
        // given
        String nickname = "testUSer";
        when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(new User()));

        // when, then
        assertThatThrownBy(() -> userHelper.isNicknameDuplicate(nickname))
                .isInstanceOf(DuplicateNickname.class);
    }

}
