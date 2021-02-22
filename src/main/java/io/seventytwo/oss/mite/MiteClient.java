package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.TimeEntries;
import io.seventytwo.oss.mite.model.User;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

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
        ResponseBody responseBody = callMite("account.xml");
        if (responseBody != null) {
            return getModel(responseBody, Account.class);
        } else {
            return null;
        }
    }

    public User getMyself() {
        ResponseBody responseBody = callMite("myself.xml");
        if (responseBody != null) {
            return getModel(responseBody, User.class);
        } else {
            return null;
        }
    }

    public TimeEntries getTimeEntries(String userId, String customerId, String projectId, String serviceId, String note,
                                      String at, String from, String to, Boolean billable, Boolean locked, Boolean tracking,
                                      String sort, String direction, String groupBy, Integer limit, Integer page) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("time_entries.xml");

        if (userId != null) {
            builder.addQueryParameter("user-id", userId);
        }
        if (customerId != null) {
            builder.addQueryParameter("customer-id", customerId);
        }
        if (projectId != null) {
            builder.addQueryParameter("project-id", projectId);
        }

        ResponseBody responseBody = callMite(builder.build());
        if (responseBody != null) {
            return getModel(responseBody, TimeEntries.class);
        } else {
            return new TimeEntries();
        }
    }

    private ResponseBody callMite(String endpoint) {
        return callMite(new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment(endpoint).build());
    }

    private ResponseBody callMite(HttpUrl httpUrl) {
        try {
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-MiteApiKey", apikey)
                    .build();
            return client.newCall(request).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getModel(ResponseBody responseBody, Class<T> modelClass) {
        try {
            String responseString = responseBody.string();

            JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(new StringReader(responseString));
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
