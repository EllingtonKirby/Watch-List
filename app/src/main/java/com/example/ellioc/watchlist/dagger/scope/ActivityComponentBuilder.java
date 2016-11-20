package com.example.ellioc.watchlist.dagger.scope;

import android.app.Activity;

public interface ActivityComponentBuilder<A extends Activity, C> {

    C build();

}