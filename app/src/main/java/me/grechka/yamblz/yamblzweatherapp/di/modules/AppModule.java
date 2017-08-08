package me.grechka.yamblz.yamblzweatherapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.grechka.yamblz.yamblzweatherapp.utils.MetricsUtils;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulers;
import me.grechka.yamblz.yamblzweatherapp.utils.RxSchedulersImpl;

/**
 * Created by Grechka on 18.07.2017.
 */

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context context) {
        appContext = context;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return appContext;
    }

    @Provides
    @NonNull
    @Singleton
    RxSchedulers provideRxSchedulers() {
        return new RxSchedulersImpl();
    }

    @Provides
    @NonNull
    @Singleton
    MetricsUtils provideMetricsUtils(Context context) {
        return new MetricsUtils(context);
    }
}
