package com.example.ellioc.watchlist.dagger.components;

import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;
import com.example.ellioc.watchlist.ui.landing.LandingActivity;
import com.example.ellioc.watchlist.ui.search.SearchFragment;
import com.example.ellioc.watchlist.ui.search.SearchResultsFragment;
import com.example.ellioc.watchlist.ui.watchlist.ViewWatchListFragment;

import dagger.Subcomponent;

@Subcomponent
public interface LandingComponent {

    void inject(final LandingActivity activity);

    void inject(final SearchFragment fragment);

    void inject(final SearchResultsFragment fragment);

    void inject(final ViewWatchListFragment fragment);

    @Subcomponent.Builder interface Builder
            extends ActivityComponentBuilder<LandingActivity, LandingComponent> {
    }
}
