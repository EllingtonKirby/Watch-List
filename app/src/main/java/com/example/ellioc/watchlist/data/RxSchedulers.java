package com.example.ellioc.watchlist.data;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RxSchedulers {
    private RxSchedulers() {
        throw new AssertionError("No instances.");
    }

    public static <T> ObservableTransformer<T, T> DefaultScheduler() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> TestScheduler() {
        return observable -> observable.subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }
}
