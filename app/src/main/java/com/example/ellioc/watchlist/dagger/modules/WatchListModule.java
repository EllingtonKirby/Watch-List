package com.example.ellioc.watchlist.dagger.modules;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.view.inputmethod.InputMethodManager;

import com.example.ellioc.watchlist.WatchListApplication;
import com.example.ellioc.watchlist.dagger.scope.PerApp;

import dagger.Module;
import dagger.Provides;

@Module
public final class WatchListModule {

    private final WatchListApplication application;

    public WatchListModule(WatchListApplication application) {
        this.application = application;
    }

    @Provides @PerApp WatchListApplication provideWatchListApplication() {
        return application;
    }

    @Provides @PerApp static InputMethodManager provideInputMethodManager(final WatchListApplication app) {
        return (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Provides @PerApp static ConnectivityManager provideConnectivityManager(final WatchListApplication application) {
        return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides @PerApp static Resources provideResources(final WatchListApplication app) {
        return app.getResources();
    }

}
