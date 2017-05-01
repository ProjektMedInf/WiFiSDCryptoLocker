package io.github.projektmedinf.wifisdcryptolocker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.UserDao;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.impl.UserDaoImpl;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
import io.github.projektmedinf.wifisdcryptolocker.service.UserService;
import io.github.projektmedinf.wifisdcryptolocker.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UserServiceImplTest {

    private static final String VALID_USER_NAME = "validUser";
    private static final String INVALID_USER_NAME = "invalidUser";

    private UserService userService;
    private User validUser;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        userService = new UserServiceImpl(appContext);
        UserDao userDao = new UserDaoImpl(appContext);
        userDao.insertUser(VALID_USER_NAME, "12345");
        validUser = userDao.getUserByUserName(VALID_USER_NAME);
    }

    @Test
    public void shouldReturnUserMatchingTheGivenUserName() throws Exception {
        assertThat(userService.getUserByUserName(VALID_USER_NAME), is(validUser));
    }

    @Test
    public void shouldReturnNullIfNoMatchWasFound() throws Exception {
        assertThat(userService.getUserByUserName(INVALID_USER_NAME), is(nullValue()));
    }
}
