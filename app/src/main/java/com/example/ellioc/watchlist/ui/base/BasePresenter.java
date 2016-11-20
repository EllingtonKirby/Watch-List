package com.example.ellioc.watchlist.ui.base;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    public void bindView(final V view) {
        this.view = view;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    protected abstract void onDestroy();

    public void unbindView() {
        this.view = null;
    }
}
