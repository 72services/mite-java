package io.seventytwo.oss.mite;

import io.seventytwo.oss.mite.model.Account;
import io.seventytwo.oss.mite.model.TimeEntries;
import io.seventytwo.oss.mite.model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

public class MiteClient {

    private final String baseUrl;
    private final OkHttpClient client;
    private final String apikey;

    public MiteClient(String baseUrl, String apikey) {
        this.baseUrl = baseUrl;
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

    public TimeEntries getTimeEntries() throws IOException {
        ResponseBody responseBody = callMite("time_entries.xml");
        if (responseBody != null) {
            return getModel(responseBody, TimeEntries.class);
        } else {
            return new TimeEntries();
        }
    }

    private ResponseBody callMite(String endpoint) {
        try {
            Request request = new Request.Builder()
                    .url(baseUrl + "/" + endpoint)
                    .addHeader("X-MiteApiKey", apikey)
                    .build();
            return client.newCall(request).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getModel(ResponseBody responseBody, Class<T> modelClass) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(new StringReader(responseBody.string()));
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
