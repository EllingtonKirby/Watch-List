package com.example.ellioc.watchlist.dagger.components;

import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;
import com.example.ellioc.watchlist.ui.detail.DetailViewActivity;

import dagger.Subcomponent;

@Subcomponent
public interface DetailViewComponent {
    void inject(final DetailViewActivity activity);

    @Subcomponent.Builder interface Builder
            extends ActivityComponentBuilder<DetailViewActivity, DetailViewComponent> {
    }
}
