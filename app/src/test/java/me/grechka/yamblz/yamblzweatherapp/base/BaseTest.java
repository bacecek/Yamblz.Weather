package me.grechka.yamblz.yamblzweatherapp.base;

import org.mockito.MockitoAnnotations;

/**
 * Created by alexander on 27/07/2017.
 */

public abstract class BaseTest {

    public void onInit() {
        MockitoAnnotations.initMocks(this);
        onMockInit();
    }

    public void onMockInit() {
    }
}
