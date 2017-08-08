package me.grechka.yamblz.yamblzweatherapp.events;

/**
 * Created by alexander on 05/08/2017.
 */

public interface Action<T> {
    void execute(T t);
}
