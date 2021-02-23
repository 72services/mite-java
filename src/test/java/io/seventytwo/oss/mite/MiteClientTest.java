package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.Customer;
import io.seventytwo.oss.mite.model.Customers;
import io.seventytwo.oss.mite.model.TimeEntries;
import io.seventytwo.oss.mite.model.TimeEntry;
import io.seventytwo.oss.mite.model.TimeEntryGroups;
import io.seventytwo.oss.mite.model.Tracker;
import io.seventytwo.oss.mite.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MiteClientTest {

    public static final Logger LOGGER = Logger.getLogger(MiteClientTest.class.getName());

    private static MiteClient miteClient;

    @BeforeAll
    static void beforeAll() {
        miteClient = new MiteClient("simas.mite.yo.lk", System.getenv("MITE_APIKEY"));
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

    @Test
    void getTimeEntries() {
        TimeEntries timeEntries = miteClient.getTimeEntries(new TimeEntriesRequest.Builder().limit(1).build());

        assertNotNull(timeEntries);
        assertEquals(1, timeEntries.getTimeEntry().size());
    }

    @Test
    void getDaily() {
        TimeEntries timeEntries = miteClient.getDaily();

        assertNotNull(timeEntries);
        assertFalse(timeEntries.getTimeEntry().isEmpty());
    }

    @Test
    void getDailgetTimeEntriesGroupBy() {
        TimeEntryGroups timeEntryGroups = miteClient.getTimeEntriesGroupBy("user");

        assertNotNull(timeEntryGroups);
        assertFalse(timeEntryGroups.getTimeEntryGroup().isEmpty());
    }

    @Test
    void getTimeEntryNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getTimeEntry(1));
    }

    @Test
    void getTimeEntry() {
        TimeEntry timeEntry = miteClient.getTimeEntry(92425425);

        assertNotNull(timeEntry);
    }

    @Test
    void createUpdateDeleteTimeEntry() {
        TimeEntry request = new TimeEntry();
        TimeEntry response = miteClient.createTimeEntry(request);

        assertNotNull(response);

        response = miteClient.updateTimeEntry(response);

        assertNotNull(response);

        miteClient.deleteTimeEntry(response.getId().getValue());
    }

    @Test
    void getTracker() {
        Tracker tracker = miteClient.getTracker();

        assertNotNull(tracker);
    }

    @Test
    void getCustomers() {
        Customers customers = miteClient.getCustomers();

        assertNotNull(customers);
        assertFalse(customers.getCustomer().isEmpty());
    }

    @Test
    void getArchivedCustomers() {
        Customers customers = miteClient.getArchivedCustomers();

        assertNotNull(customers);
        assertFalse(customers.getCustomer().isEmpty());
    }

    @Test
    void getCustomerNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getCustomer(1));
    }

    @Test
    void getCustomer() {
        Customer customer = miteClient.getCustomer(264184);

        assertNotNull(customer);
    }

    @Test
    void createUpdateDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("Dummy");
        Customer response = miteClient.createCustomer(customer);

        assertNotNull(response);

        miteClient.updateCustomer(response);

        assertNotNull(response);

        miteClient.deleteCustomer(response.getId().getValue());
    }
}
