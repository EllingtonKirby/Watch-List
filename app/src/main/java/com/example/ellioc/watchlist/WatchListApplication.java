package com.example.ellioc.watchlist;

import android.app.Activity;
import android.app.Application;

import com.example.ellioc.watchlist.dagger.SubcomponentBuilderProvider;
import com.example.ellioc.watchlist.dagger.modules.WatchListModule;
import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;

import java.util.Map;

import javax.inject.Inject;


public class WatchListApplication extends Application implements SubcomponentBuilderProvider{

    @Inject Map<Class<? extends Activity>, ActivityComponentBuilder> componentBuilders;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerWatchListComponent
                .builder()
                .watchListModule(new WatchListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass) {
        return componentBuilders.get(activityClass);
    }

}
