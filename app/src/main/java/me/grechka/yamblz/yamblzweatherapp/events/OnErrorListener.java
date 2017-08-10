package me.grechka.yamblz.yamblzweatherapp.events;

/**
 * Created by alexander on 10/08/2017.
 */

public interface OnErrorListener {
    void onMissingCity();
    void onNetworkError();
}
