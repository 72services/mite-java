package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.Bookmark;
import io.seventytwo.oss.mite.model.Bookmarks;
import io.seventytwo.oss.mite.model.Customer;
import io.seventytwo.oss.mite.model.Customers;
import io.seventytwo.oss.mite.model.Errors;
import io.seventytwo.oss.mite.model.Project;
import io.seventytwo.oss.mite.model.Projects;
import io.seventytwo.oss.mite.model.Service;
import io.seventytwo.oss.mite.model.Services;
import io.seventytwo.oss.mite.model.TimeEntries;
import io.seventytwo.oss.mite.model.TimeEntry;
import io.seventytwo.oss.mite.model.TimeEntryGroups;
import io.seventytwo.oss.mite.model.Tracker;
import io.seventytwo.oss.mite.model.User;
import io.seventytwo.oss.mite.model.Users;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class MiteClient {

    private final String host;
    private final OkHttpClient client;
    private final String apikey;

    public MiteClient(String host, String apikey) {
        this.host = host;
        this.apikey = apikey;

        client = new OkHttpClient().newBuilder().build();
    }

    public Account getAccount() {
        return getObject(Account.class, "account.xml");
    }

    public User getMyself() {
        return getObject(User.class, "myself.xml");
    }

    public TimeEntries getTimeEntries(TimeEntriesRequest timeEntriesRequest) {
        var queryParameters = new HashMap<String, String>();

        if (timeEntriesRequest.userId != null) {
            queryParameters.put("user_id", timeEntriesRequest.userId);
        }
        if (timeEntriesRequest.customerId != null) {
            queryParameters.put("customer_id", timeEntriesRequest.customerId);
        }
        if (timeEntriesRequest.projectId != null) {
            queryParameters.put("project_id", timeEntriesRequest.projectId);
        }
        if (timeEntriesRequest.serviceId != null) {
            queryParameters.put("service_id", timeEntriesRequest.serviceId);
        }
        if (timeEntriesRequest.note != null) {
            queryParameters.put("note", timeEntriesRequest.note);
        }
        if (timeEntriesRequest.at != null) {
            queryParameters.put("at", timeEntriesRequest.at);
        }
        if (timeEntriesRequest.from != null) {
            queryParameters.put("from", timeEntriesRequest.from);
        }
        if (timeEntriesRequest.to != null) {
            queryParameters.put("to", timeEntriesRequest.to);
        }
        if (timeEntriesRequest.billable != null) {
            queryParameters.put("billable", timeEntriesRequest.billable.toString());
        }
        if (timeEntriesRequest.locked != null) {
            queryParameters.put("locked", timeEntriesRequest.locked.toString());
        }
        if (timeEntriesRequest.tracking != null) {
            queryParameters.put("tracking", timeEntriesRequest.tracking.toString());
        }
        if (timeEntriesRequest.sort != null) {
            queryParameters.put("sort", timeEntriesRequest.sort);
        }
        if (timeEntriesRequest.direction != null) {
            queryParameters.put("direction", timeEntriesRequest.direction);
        }
        if (timeEntriesRequest.limit != null) {
            queryParameters.put("limit", timeEntriesRequest.limit.toString());
        }
        if (timeEntriesRequest.page != null) {
            queryParameters.put("page", timeEntriesRequest.page.toString());
        }

        return getObject(TimeEntries.class, queryParameters, "time_entries.xml");
    }

    public TimeEntries getDaily() {
        return getObject(TimeEntries.class, "daily.xml");
    }

    public TimeEntryGroups getTimeEntriesGroupBy(String groupBy) {
        return getObject(TimeEntryGroups.class, Map.of("group_by", groupBy), "time_entries.xml");
    }

    public TimeEntry getTimeEntry(long id) {
        return getObject(TimeEntry.class, "time_entries", id + ".xml");
    }

    public TimeEntry createTimeEntry(TimeEntry timeEntry) {
        return createObject(TimeEntry.class, timeEntry, "time_entries.xml");
    }

    public void updateTimeEntry(TimeEntry timeEntry) {
        updateObject(TimeEntry.class, timeEntry, "time_entries", timeEntry.getId().getValue() + ".xml");
    }

    public void deleteTimeEntry(long id) {
        deleteObject(TimeEntry.class, "time_entries", id + ".xml");
    }

    public Tracker getTracker() {
        return getObject(Tracker.class, "tracker.xml");
    }

    public Tracker startTracker(Long id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Tracker stopTracker(Long id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Bookmarks getBookmarks() {
        return getObject(Bookmarks.class, "time_entries", "bookmarks.xml");
    }

    public Bookmark getBookmark(Long id) {
        return getObject(Bookmark.class, "time_entries", "bookmarks", id + ".xml");
    }

    public Customers getCustomers(String name, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Customers.class, queryParameters, "customers.xml");
    }

    public Customers getArchivedCustomers(String name, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Customers.class, queryParameters, "customers", "archived.xml");
    }

    public Customer getCustomer(long id) {
        return getObject(Customer.class, "customers", id + ".xml");
    }

    public Customer createCustomer(Customer customer) {
        return createObject(Customer.class, customer, "customers.xml");
    }

    public void updateCustomer(Customer customer) {
        updateObject(Customer.class, customer, "customers", customer.getId().getValue() + ".xml");
    }

    public void deleteCustomer(long id) {
        deleteObject(Customer.class, "customers", id + ".xml");
    }

    public Projects getProjects(String name, String customerId, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (customerId != null) {
            queryParameters.put("customer_id", customerId);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Projects.class, queryParameters, "projects.xml");
    }

    public Projects getArchivedProjects(String name, String customerId, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (customerId != null) {
            queryParameters.put("customer_id", customerId);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Projects.class, queryParameters, "projects", "archived.xml");
    }

    public Project getProject(long id) {
        return getObject(Project.class, "projects", id + ".xml");
    }

    public Project createProject(Project project) {
        return createObject(Project.class, project, "projects.xml");
    }

    public void updateProject(Project project) {
        updateObject(Project.class, project, "projects", project.getId().getValue() + ".xml");
    }

    public void deleteProject(long id) {
        deleteObject(Project.class, "projects", id + ".xml");
    }

    public Services getServices(String name, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Services.class, queryParameters, "services.xml");
    }

    public Services getArchivedServices(String name, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Services.class, queryParameters, "services", "archived.xml");
    }

    public Service getService(long id) {
        return getObject(Service.class, "services", id + ".xml");
    }

    public Service createService(Service service) {
        return createObject(Service.class, service, "services.xml");
    }

    public void updateService(Service service) {
        updateObject(Service.class, service, "services", service.getId().getValue() + ".xml");
    }

    public void deleteService(long id) {
        deleteObject(Service.class, "services", id + ".xml");
    }

    public Users getUsers(String name, String email, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (email != null) {
            queryParameters.put("email", email);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Users.class, queryParameters, "users.xml");
    }

    public Users getArchivedUsers(String name, String email, Integer limit, Integer page) {
        var queryParameters = new HashMap<String, String>();

        if (name != null) {
            queryParameters.put("name", name);
        }
        if (email != null) {
            queryParameters.put("email", email);
        }
        if (limit != null) {
            queryParameters.put("limit", limit.toString());
        }
        if (page != null) {
            queryParameters.put("page", page.toString());
        }

        return getObject(Users.class, queryParameters, "users", "archived.xml");
    }

    public User getUser(long id) {
        return getObject(User.class, "users", id + ".xml");
    }

    private Response get(HttpUrl httpUrl) {
        try {
            var request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-MiteApiKey", apikey)
                    .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Response post(HttpUrl httpUrl, Class<T> objClass, Object obj) {
        try {
            var stringWriter = new StringWriter();
            JAXBContext.newInstance(objClass).createMarshaller().marshal(obj, stringWriter);

            var requestBody = RequestBody.create(MediaType.parse("application/xml"), stringWriter.toString());

            var request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-MiteApiKey", apikey)
                    .post(requestBody)
                    .build();
            return client.newCall(request).execute();
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Response patch(HttpUrl httpUrl, Class<T> objClass, Object obj) {
        try {
            var stringWriter = new StringWriter();
            JAXBContext.newInstance(objClass).createMarshaller().marshal(obj, stringWriter);

            var requestBody = RequestBody.create(MediaType.parse("application/xml"), stringWriter.toString());

            var request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-MiteApiKey", apikey)
                    .patch(requestBody)
                    .build();
            return client.newCall(request).execute();
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private Response delete(HttpUrl httpUrl) {
        try {
            var request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-MiteApiKey", apikey)
                    .delete()
                    .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getModel(Response response, Class<T> modelClass) {
        try {
            if (response.body() != null) {
                var responseString = response.body().string();

                if (responseString.contains("<errors>")) {
                    throw new MiteException(response.code(),
                            (Errors) JAXBContext.newInstance(Errors.class).createUnmarshaller().unmarshal(new StringReader(responseString)));
                } else {
                    return (T) JAXBContext.newInstance(modelClass).createUnmarshaller().unmarshal(new StringReader(responseString));
                }
            } else {
                throw new MiteException(response.code());
            }
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getObject(Class<T> clazz, String... pathSegments) {
        var builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host);
        for (String pathSegment : pathSegments) {
            builder.addPathSegment(pathSegment);
        }

        var response = get(builder.build());
        return getModel(response, clazz);
    }

    private <T> T getObject(Class<T> clazz, Map<String, String> queryParameters, String... pathSegments) {
        var builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host);
        for (String pathSegment : pathSegments) {
            builder.addPathSegment(pathSegment);
        }
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        var response = get(builder.build());
        return getModel(response, clazz);
    }

    private <T> T createObject(Class<T> clazz, Object object, String... pathSegments) {
        var builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host);
        for (String pathSegment : pathSegments) {
            builder.addPathSegment(pathSegment);
        }

        var response = post(builder.build(), clazz, object);
        return getModel(response, clazz);
    }

    private <T> void updateObject(Class<T> clazz, Object object, String... pathSegments) {
        var builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host);
        for (String pathSegment : pathSegments) {
            builder.addPathSegment(pathSegment);
        }

        var response = patch(builder.build(), clazz, object);
        if (response.code() != 200) {
            throw new MiteException(response.code());
        }
    }

    private <T> void deleteObject(Class<T> clazz, String... pathSegments) {
        var builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host);
        for (String pathSegment : pathSegments) {
            builder.addPathSegment(pathSegment);
        }

        var response = delete(builder.build());
        if (response.code() != 200) {
            throw new MiteException(response.code());
        }
    }
}
