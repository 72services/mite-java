package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Customer;
import io.seventytwo.oss.mite.model.Project;
import io.seventytwo.oss.mite.model.Service;
import io.seventytwo.oss.mite.model.TimeEntry;
import io.seventytwo.oss.mite.model.Tracker;
import io.seventytwo.oss.mite.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MiteClientTest {

    private static MiteClient miteClient;

    @BeforeAll
    static void beforeAll() {
        miteClient = new MiteClient("mite-java.mite.yo.lk", "966b6e5770747412");
    }

    @Test
    void getAccount() {
        var account = miteClient.getAccount();

        assertNotNull(account);
    }

    @Test
    void getMyself() {
        User user = miteClient.getMyself();

        assertNotNull(user);
    }

    @Test
    void getTimeEntries() {
        var timeEntries = miteClient.getTimeEntries(new TimeEntriesRequest.Builder().limit(1).build());

        assertNotNull(timeEntries);
        assertEquals(1, timeEntries.getTimeEntry().size());
    }

    @Test
    void getDaily() {
        var timeEntries = miteClient.getDaily();

        assertNotNull(timeEntries);
        assertTrue(timeEntries.getTimeEntry().isEmpty());
    }

    @Test
    void getDailgetTimeEntriesGroupBy() {
        var timeEntryGroups = miteClient.getTimeEntriesGroupBy("user");

        assertNotNull(timeEntryGroups);
        assertFalse(timeEntryGroups.getTimeEntryGroup().isEmpty());
    }

    @Test
    void getTimeEntryNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getTimeEntry(1));
    }

    @Test
    void getTimeEntry() {
        var timeEntries = miteClient.getTimeEntries(new TimeEntriesRequest.Builder().limit(1).build());

        assertNotNull(timeEntries);
        assertEquals(1, timeEntries.getTimeEntry().size());

        var timeEntry = miteClient.getTimeEntry(timeEntries.getTimeEntry().get(0).getId().getValue());

        assertNotNull(timeEntry);
    }

    @Test
    void createUpdateDeleteTimeEntry() {
        var request = new TimeEntry();
        var response = miteClient.createTimeEntry(request);

        assertNotNull(response);

        response.setNote("Notiz");
        miteClient.updateTimeEntry(response);

        assertNotNull(response);

        miteClient.deleteTimeEntry(response.getId().getValue());
    }

    @Test
    void getTracker() {
        var tracker = miteClient.getTracker();

        assertNotNull(tracker);
    }

    @Test
    void getCustomers() {
        var customers = miteClient.getCustomers(null, null, null);

        assertNotNull(customers);
        assertFalse(customers.getCustomer().isEmpty());
    }

    @Test
    void getArchivedCustomers() {
        var customers = miteClient.getArchivedCustomers(null, null, null);

        assertNotNull(customers);
        assertTrue(customers.getCustomer().isEmpty());
    }

    @Test
    void getCustomerNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getCustomer(1));
    }

    @Test
    void getCustomer() {
        var customers = miteClient.getCustomers(null, 1, null);
        var customer = miteClient.getCustomer(customers.getCustomer().get(0).getId().getValue());

        assertNotNull(customer);
    }

    @Test
    void createUpdateDeleteCustomer() {
        var customer = new Customer();
        customer.setName("Dummy");
        var response = miteClient.createCustomer(customer);

        assertNotNull(response);

        miteClient.updateCustomer(response);

        assertNotNull(response);

        miteClient.deleteCustomer(response.getId().getValue());
    }

    @Test
    void getProjects() {
        var projects = miteClient.getProjects(null, null, null, null);

        assertNotNull(projects);
        assertFalse(projects.getProject().isEmpty());
    }

    @Test
    void getArchivedProjects() {
        var projects = miteClient.getArchivedProjects(null, null, null, null);

        assertNotNull(projects);
        assertTrue(projects.getProject().isEmpty());
    }

    @Test
    void getProject() {
        var projects = miteClient.getProjects(null, null, 1, null);

        assertNotNull(projects);
        assertFalse(projects.getProject().isEmpty());

        var project = miteClient.getProject(projects.getProject().get(0).getId().getValue());

        assertNotNull(project);
    }

    @Test
    void createUpdateDeleteProject() {
        var project = new Project();
        project.setName("Dummy");
        var response = miteClient.createProject(project);

        assertNotNull(response);

        miteClient.updateProject(response);

        assertNotNull(response);

        miteClient.deleteProject(response.getId().getValue());
    }

    @Test
    void getServices() {
        var services = miteClient.getServices(null, null, null);

        assertNotNull(services);
        assertFalse(services.getService().isEmpty());
    }

    @Test
    void getArchivedServices() {
        var services = miteClient.getArchivedServices(null, null, null);

        assertNotNull(services);
        assertTrue(services.getService().isEmpty());
    }

    @Test
    void getServiceNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getService(1));
    }

    @Test
    void getService() {
        var services = miteClient.getServices(null, 1, null);
        var service = miteClient.getService(services.getService().get(0).getId().getValue());

        assertNotNull(service);
    }

    @Test
    void createUpdateDeleteService() {
        var service = new Service();
        service.setName("Dummy");
        var response = miteClient.createService(service);

        assertNotNull(response);

        miteClient.updateService(response);

        assertNotNull(response);

        miteClient.deleteService(response.getId().getValue());
    }

    @Test
    void getUsers() {
        var users = miteClient.getUsers(null, null, null, null);

        assertNotNull(users);
        assertFalse(users.getUser().isEmpty());
    }

    @Test
    void getArchivedUsers() {
        var users = miteClient.getArchivedUsers(null, null, null, null);

        assertNotNull(users);
        assertTrue(users.getUser().isEmpty());
    }

    @Test
    void getUserNotFound() {
        assertThrows(MiteException.class, () -> miteClient.getUser(1));
    }

    @Test
    void getUser() {
        var users = miteClient.getUsers(null, null, 1, null);
        var user = miteClient.getUser(users.getUser().get(0).getId().getValue());

        assertNotNull(user);
    }

    @Test
    void getBookmarks() {
        var bookmarks = miteClient.getBookmarks();

        assertNotNull(bookmarks);
        assertFalse(bookmarks.getBookmark().isEmpty());
    }

    @Test
    void getBookmark() {
        var bookmarks = miteClient.getBookmarks();

        assertNotNull(bookmarks);
        assertFalse(bookmarks.getBookmark().isEmpty());

        var bookmark = miteClient.getBookmark(bookmarks.getBookmark().get(0).getId().getValue());

        assertNotNull(bookmark);
    }

    @Test
    void startStopTracker() {
        var request = new TimeEntry();
        var response = miteClient.createTimeEntry(request);

        assertNotNull(response);

        Tracker tracker = miteClient.startTracker(response.getId().getValue());
        assertNotNull(tracker);

        tracker = miteClient.stopTracker(response.getId().getValue());
        assertNotNull(tracker);

        assertNotNull(response);

        miteClient.deleteTimeEntry(response.getId().getValue());
    }
}
