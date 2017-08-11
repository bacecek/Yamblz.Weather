package me.grechka.yamblz.yamblzweatherapp.base;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexander on 28/07/2017.
 */

public abstract class BaseApiTest {

    private MockWebServer mockWebServer;
    private GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

    protected  <T> T createService(Class<T> cls) throws IOException {
        mockWebServer = new MockWebServer();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .baseUrl(mockWebServer.url("/"))
                .build()
                .create(cls);
    }

    protected RecordedRequest getRequest() throws InterruptedException {
        return mockWebServer.takeRequest();
    }

    protected void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    protected <T> Predicate<T> check(Consumer<T> consumer) {
        return t -> {
            consumer.accept(t);
            return true;
        };
    }

    protected void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(Charset.forName("UTF-8"))));
    }
}
