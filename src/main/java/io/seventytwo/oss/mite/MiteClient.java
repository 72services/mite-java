package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.Errors;
import io.seventytwo.oss.mite.model.TimeEntries;
import io.seventytwo.oss.mite.model.TimeEntry;
import io.seventytwo.oss.mite.model.TimeEntryGroups;
import io.seventytwo.oss.mite.model.Tracker;
import io.seventytwo.oss.mite.model.User;
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
import java.util.logging.Logger;

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
        Response response = get("account.xml");
        return getModel(response, Account.class);
    }

    public User getMyself() {
        Response response = get("myself.xml");
        return getModel(response, User.class);
    }

    public TimeEntries getTimeEntries(TimeEntriesRequest timeEntriesRequest) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries.xml");

        if (timeEntriesRequest.userId != null) {
            builder.addQueryParameter("user_id", timeEntriesRequest.userId);
        }
        if (timeEntriesRequest.customerId != null) {
            builder.addQueryParameter("customer_id", timeEntriesRequest.customerId);
        }
        if (timeEntriesRequest.projectId != null) {
            builder.addQueryParameter("project_id", timeEntriesRequest.projectId);
        }
        if (timeEntriesRequest.serviceId != null) {
            builder.addQueryParameter("service_id", timeEntriesRequest.serviceId);
        }
        if (timeEntriesRequest.note != null) {
            builder.addQueryParameter("note", timeEntriesRequest.note);
        }
        if (timeEntriesRequest.at != null) {
            builder.addQueryParameter("at", timeEntriesRequest.at);
        }
        if (timeEntriesRequest.from != null) {
            builder.addQueryParameter("from", timeEntriesRequest.from);
        }
        if (timeEntriesRequest.to != null) {
            builder.addQueryParameter("to", timeEntriesRequest.to);
        }
        if (timeEntriesRequest.billable != null) {
            builder.addQueryParameter("billable", timeEntriesRequest.billable.toString());
        }
        if (timeEntriesRequest.locked != null) {
            builder.addQueryParameter("locked", timeEntriesRequest.locked.toString());
        }
        if (timeEntriesRequest.tracking != null) {
            builder.addQueryParameter("tracking", timeEntriesRequest.tracking.toString());
        }
        if (timeEntriesRequest.sort != null) {
            builder.addQueryParameter("sort", timeEntriesRequest.sort);
        }
        if (timeEntriesRequest.direction != null) {
            builder.addQueryParameter("direction", timeEntriesRequest.direction);
        }
        if (timeEntriesRequest.limit != null) {
            builder.addQueryParameter("limit", timeEntriesRequest.limit.toString());
        }
        if (timeEntriesRequest.page != null) {
            builder.addQueryParameter("page", timeEntriesRequest.page.toString());
        }

        Response response = get(builder.build());
        return getModel(response, TimeEntries.class);
    }

    public TimeEntries getDaily() {
        Response response = get("daily.xml");
        return getModel(response, TimeEntries.class);
    }

    public TimeEntryGroups getTimeEntriesGroupBy(String groupBy) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries.xml");

        builder.addQueryParameter("group_by", groupBy);

        Response response = get(builder.build());
        return getModel(response, TimeEntryGroups.class);
    }

    public TimeEntry getTimeEntry(long id) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries")
                .addPathSegment(id + ".xml");

        Response response = get(builder.build());
        return getModel(response, TimeEntry.class);
    }

    public TimeEntry createTimeEntry(TimeEntry timeEntry) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries.xml");

        Response response = post(builder.build(), TimeEntry.class, timeEntry);
        return getModel(response, TimeEntry.class);
    }

    public TimeEntry updateTimeEntry(TimeEntry timeEntry) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries")
                .addPathSegment(timeEntry.getId().getValue() + ".xml");

        Response response = patch(builder.build(), TimeEntry.class, timeEntry);
        return getModel(response, TimeEntry.class);
    }

    public void deleteTimeEntry(long id) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries.xml")
                .addPathSegment(id + ".xml");

        Response response = delete(builder.build());
        if (response.code() != 200) {
            throw new MiteException(response.code());
        }
    }

    public Tracker getTracker() {
        Response response = get("tracker.xml");
        return getModel(response, Tracker.class);
    }

    public Tracker startTracker(Long id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Tracker stopTracker(Long id) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Tracker getBookmarks() {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Tracker getBookmark(Long id) {
        // TODO
        throw new UnsupportedOperationException();
    }



    private Response get(String endpoint) {
        return get(new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment(endpoint).build());
    }

    private Response get(HttpUrl httpUrl) {
        try {
            Request request = new Request.Builder()
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
            StringWriter stringWriter = new StringWriter();
            JAXBContext.newInstance(objClass).createMarshaller().marshal(obj, stringWriter);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml"), stringWriter.toString());

            Request request = new Request.Builder()
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
            StringWriter stringWriter = new StringWriter();
            JAXBContext.newInstance(objClass).createMarshaller().marshal(obj, stringWriter);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml"), stringWriter.toString());

            Request request = new Request.Builder()
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
            Request request = new Request.Builder()
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
                String responseString = response.body().string();

                Logger.getLogger("mite").warning(responseString);

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
}
