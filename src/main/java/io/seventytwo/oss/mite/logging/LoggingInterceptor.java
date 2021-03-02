package io.seventytwo.oss.mite.logging;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger("io.seventytwo.oss.mite");

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LOGGER.debug("{} {}\n{}", request.method(), request.url(), request.headers());

            Buffer requestBuffer = new Buffer();
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                requestBody.writeTo(requestBuffer);
                LOGGER.debug(requestBuffer.readUtf8());
            }

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LOGGER.debug("{} {} in {}\n{}\n", response.request().method(), response.request().url(), (t2 - t1) / 1e6d, response.headers());

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                MediaType contentType = responseBody.contentType();
                String content = responseBody.string();
                LOGGER.debug(content);

                ResponseBody wrappedBody = ResponseBody.create(contentType, content);
                return response.newBuilder().body(wrappedBody).build();
            } else {
                return response.newBuilder().build();
            }
        } else {
            return chain.proceed(chain.request());
        }
    }
}
