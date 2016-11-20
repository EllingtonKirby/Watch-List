package com.example.ellioc.watchlist.dagger;

import com.example.ellioc.watchlist.dagger.components.DetailViewComponent;
import com.example.ellioc.watchlist.dagger.components.LandingComponent;
import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;
import com.example.ellioc.watchlist.dagger.scope.ActivityKey;
import com.example.ellioc.watchlist.ui.landing.LandingActivity;
import com.example.ellioc.watchlist.ui.detail.DetailViewActivity;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module( subcomponents = {
        LandingComponent.class,
        DetailViewComponent.class
})
public abstract class ActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(LandingActivity.class)
    public abstract ActivityComponentBuilder
    landingActivityComponentBuilder(LandingComponent.Builder impl);

    @Binds
    @IntoMap
    @ActivityKey(DetailViewActivity.class)
    public abstract ActivityComponentBuilder
    detailViewActivityComponentBuilder(DetailViewComponent.Builder impl);
}
