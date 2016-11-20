package com.example.ellioc.watchlist.dagger;

import android.app.Activity;

import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;

public interface SubcomponentBuilderProvider {
    ActivityComponentBuilder getActivityComponentBuilder(Class<? extends Activity> activityClass);
}