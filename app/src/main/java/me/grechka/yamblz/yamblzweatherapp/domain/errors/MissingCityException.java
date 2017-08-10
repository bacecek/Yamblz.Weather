package me.grechka.yamblz.yamblzweatherapp.domain.errors;

/**
 * Created by alexander on 10/08/2017.
 */

public class MissingCityException extends RuntimeException {
    public MissingCityException() {
        super("There is no choosen city");
    }
}
