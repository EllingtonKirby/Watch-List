package com.example.ellioc.watchlist.dagger.components;

import com.example.ellioc.watchlist.WatchListApplication;
import com.example.ellioc.watchlist.dagger.modules.WatchListModule;
import com.example.ellioc.watchlist.dagger.scope.PerApp;

import dagger.Component;

@PerApp
@Component(modules = {
        WatchListModule.class
})
public interface WatchListComponent {
    void inject(WatchListApplication app);
}
