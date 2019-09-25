import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class customTests {

    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        System.out.println("Success");
    }

    @DisplayName("testing MyUserPrincipal implements UserDetails")
    @Test
    void testIsMyUserPrincipalImplementsUserDetails() {
        MyUserPrincipal myuserPrincipal = new MyUserPrincipal(new User());
        User user = new User();
        assertAll(
                () -> assertTrue(UserDetails.class.isInstance(myuserPrincipal), "test FAIL cause of class not instance of UserDetails"),
                () -> assertTrue(User.class.isInstance(user), "test FAIL cause of class not instance of User")
        );
    }


    @DisplayName("testing exception")
    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> Integer.valueOf(str));
    }
}
