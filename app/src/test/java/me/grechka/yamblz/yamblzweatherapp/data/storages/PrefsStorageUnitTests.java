package me.grechka.yamblz.yamblzweatherapp.data.storages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;

import me.grechka.yamblz.yamblzweatherapp.base.BaseUnitTest;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander on 09/08/2017.
 */

public class PrefsStorageUnitTests extends BaseUnitTest {

    private Storage<String> prefs;

    @Mock Context context;
    @Mock SharedPreferences sharedPreferences;
    @Mock SharedPreferences.Editor editor;

    @Before
    @Override
    public void onInit() {
        super.onInit();

        prefs = new PrefsStorage(context);
    }

    @Override
    public void onMockInit() {
        super.onMockInit();

        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);

        when(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(0);
        when(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenReturn(false);
        when(sharedPreferences.getFloat(anyString(), anyFloat())).thenReturn(0.0f);
        when(sharedPreferences.getLong(anyString(), anyLong())).thenReturn(0L);
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("");

        when(editor.remove(anyString())).thenReturn(editor);
        when(editor.clear()).thenReturn(editor);
    }

    @Test
    public void PrefsStorage_putBoolean_valueStoredSuccessfully() {
        prefs.put("haha", false);

        verify(editor).putBoolean(anyString(), anyBoolean());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_putInteger_valueStoredSuccessfully() {
        prefs.put("haha", 0);

        verify(editor).putInt(anyString(), anyInt());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_putFloat_valueStoredSuccessfully() {
        prefs.put("haha", 0.0f);

        verify(editor).putFloat(anyString(), anyFloat());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_putDouble_valueStoredSuccessfully() {
        prefs.put("haha", 0.0);

        verify(editor).putString(anyString(), anyString());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_putLong_valueStoredSuccessfully() {
        prefs.put("haha", 0L);

        verify(editor).putLong(anyString(), anyLong());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_putString_valueStoredSuccessfully() {
        prefs.put("haha", "");

        verify(editor).putString(anyString(), anyString());
        verify(editor).apply();
    }

    @Test(expected = IllegalArgumentException.class)
    public void PrefsStorage_putObject_throwAnError() {
        prefs.put("haha", new ArrayList<Intent>());
    }

    @Test
    public void PrefsStorage_getBoolean_valueSuccessfullyGet() {
        prefs.getBoolean("haha", false);
        verify(sharedPreferences).getBoolean(anyString(), anyBoolean());
    }

    @Test
    public void PrefsStorage_getInt_valueSuccessfullyGet() {
        prefs.getInteger("haha", 0);
        verify(sharedPreferences).getInt(anyString(), anyInt());
    }

    @Test
    public void PrefsStorage_getFloat_valueSuccessfullyGet() {
        prefs.getFloat("haha", 0.0f);
        verify(sharedPreferences).getFloat(anyString(), anyFloat());
    }

    @Test
    public void PrefsStorage_getLong_valueSuccessfullyGet() {
        prefs.getLong("haha", 0L);
        verify(sharedPreferences).getLong(anyString(), anyLong());
    }

    @Test
    public void PrefsStorage_getString_valueSuccessfullyGet() {
        prefs.getString("haha", "");
        verify(sharedPreferences).getString(anyString(), anyString());
    }

    @Test
    public void PrefsStorage_removeByKey_valueSuccessfullyRemove() {
        prefs.remove("haha");

        verify(sharedPreferences).edit();
        verify(editor).remove(anyString());
        verify(editor).apply();
    }

    @Test
    public void PrefsStorage_clear() {
        prefs.clear();

        verify(sharedPreferences).edit();
        verify(editor).clear();
        verify(editor).apply();
    }
}
