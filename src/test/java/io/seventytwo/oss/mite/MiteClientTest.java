package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MiteClientTest {

    public static final Logger LOGGER = Logger.getLogger(MiteClientTest.class.getName());

    private static MiteClient miteClient;

    @BeforeAll
    static void beforeAll() {
        miteClient = new MiteClient("https://simas.mite.yo.lk", System.getenv("MITE_APIKEY"));
    }

    @Test
    void getAccount() {
        Account account = miteClient.getAccount();

        assertNotNull(account);
    }

    @Test
    void getMyself() {
        User user = miteClient.getMyself();

        assertNotNull(user);
    }
}
