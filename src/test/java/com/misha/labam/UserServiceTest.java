package com.misha.labam;

import com.misha.labam.dto.LoginDto;
import com.misha.labam.entity.User;
import com.misha.labam.service.UserService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final UserService userService = new UserService();

    @Test
    public void testRegisterAndAuthenticate() {
        LoginDto dto = new LoginDto("test@example.com", "secure123");

        User user = userService.register(dto);
        assertNotNull(user.getId());

        User auth = userService.authenticate(dto.getEmail(), dto.getPassword());
        assertEquals(dto.getEmail(), auth.getEmail());
    }

    @Test
    public void testDuplicateRegistrationFails() {
        LoginDto dto = new LoginDto("dupe@example.com", "password");

        userService.register(dto);
        assertThrows(RuntimeException.class, () -> userService.register(dto));
    }
}
