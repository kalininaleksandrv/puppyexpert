import com.eyeslessdev.needmypuppyapi.entity.MyUserPrincipal;
import com.eyeslessdev.needmypuppyapi.entity.User;
import org.junit.jupiter.api.*;
import org.opentest4j.TestAbortedException;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class customTests {

    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        int today = LocalDate.now().getDayOfMonth();
            assumeTrue(today == 26, "not yor day, test");
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

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");

        testReporter.publishEntry(values);
    }
}
