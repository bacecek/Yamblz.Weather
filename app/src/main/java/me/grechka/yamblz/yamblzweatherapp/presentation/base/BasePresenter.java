package me.grechka.yamblz.yamblzweatherapp.presentation.base;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by alexander on 06/08/2017.
 */

public abstract class BasePresenter<T extends MvpView> extends MvpPresenter<T> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addSubscription(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
