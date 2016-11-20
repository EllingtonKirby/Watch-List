package com.example.ellioc.watchlist.ui.base;

import android.content.Context;
import android.support.v4.content.Loader;

public class PresenterLoader<P extends BasePresenter> extends Loader<P> {

    private final String tag;
    private P presenter;

    public PresenterLoader(Context context, final String tag, final P presenter) {
        super(context);
        this.tag = tag;
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        if(presenter != null) {
            deliverResult(presenter);
            return;
        }

        forceLoad();
    }

    @Override
    public void forceLoad() {
        deliverResult(presenter);
    }

    @Override
    public void deliverResult(P data) {
        super.deliverResult(data);
    }


    @Override
    protected void onReset() {
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
