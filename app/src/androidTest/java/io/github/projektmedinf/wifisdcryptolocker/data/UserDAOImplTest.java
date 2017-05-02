package io.github.projektmedinf.wifisdcryptolocker.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.UserDao;
import io.github.projektmedinf.wifisdcryptolocker.data.dao.impl.UserDaoImpl;
import io.github.projektmedinf.wifisdcryptolocker.model.User;
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
public class UserDAOImplTest {

    private static final String VALID_USER_NAME = "validUser";
    private static final String ANOTHER_VALID_USER_NAME = "anotherValidUser";
    private static final String INVALID_USER_NAME = "invalidUser";

    private UserDao userDao;
    private User validUser;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        userDao = new UserDaoImpl(appContext);
        userDao.insertUser(VALID_USER_NAME, "12345");
        validUser = userDao.getUserByUserName(VALID_USER_NAME);
    }

    @Test
    public void shouldReturnUserMatchingTheGivenUserName() throws Exception {
        assertThat(userDao.getUserByUserName(VALID_USER_NAME), is(validUser));
    }

    @Test
    public void shouldReturnNullIfNoMatchWasFound() throws Exception {
        assertThat(userDao.getUserByUserName(INVALID_USER_NAME), is(nullValue()));
    }

    @Test
    public void shouldReturnTheIdOfTheUserOnSuccess() throws Exception {
        Object userId = userDao.insertUser(ANOTHER_VALID_USER_NAME, "12345");
        assertThat(userId instanceof Long, is(true));
    }

    @Test
    public void shouldReturnMinusOneIfAGeneralDatabaseErrorOccurs() throws Exception {
        //TODO
    }

    @Test
    public void shouldReturnMinusTwoIfTheUserAlreadyExists() throws Exception {
        long userId = userDao.insertUser(VALID_USER_NAME, "12345");
        assertThat(userId, is(-2l));
    }
}
