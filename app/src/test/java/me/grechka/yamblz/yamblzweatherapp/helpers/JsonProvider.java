package me.grechka.yamblz.yamblzweatherapp.helpers;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by alexander on 27/07/2017.
 */

public class JsonProvider {

    private static Gson gson = new Gson();

    private JsonProvider() {
    }

    public static <T> T openFile(@NonNull Class<T> to,
                                        @NonNull String fileName) {
        InputStream inputStream = to.getClassLoader().getResourceAsStream(fileName);
        InputStreamReader reader = new InputStreamReader(inputStream);
        return gson.fromJson(reader, to);
    }

    @SuppressLint("NewApi")
    public static String openFile(@NonNull String fileName) {
        InputStream inputStream = JsonProvider.class.getClassLoader().getResourceAsStream(fileName);
        StringBuilder textBuilder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader
                (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return textBuilder.toString();
    }
}
