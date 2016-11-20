package com.example.ellioc.watchlist.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment<P extends BasePresenter<V>, V extends BaseView> extends Fragment {

    protected P presenter;

    private boolean viewAttached = false;
    private boolean delivered = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Initialize DI
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initLoader();
    }

    @Override public void onStart() {
        super.onStart();
        if (presenter != null && !viewAttached) {
            presenter.bindView((V) BaseFragment.this);
            viewAttached = true;
        }
    }

    @Override
    public void onStop() {
        if (presenter != null && viewAttached) {
            presenter.unbindView();
            viewAttached = false;
        }
        super.onStop();
    }

    private void initLoader() {
        getLoaderManager().initLoader(getClass().getCanonicalName().hashCode(), null,
                new LoaderManager.LoaderCallbacks<P>() {
                    @Override
                    public final Loader<P> onCreateLoader(int i, Bundle bundle) {
                        return new PresenterLoader<>(getActivity(), getClass().getCanonicalName(), presenter);
                    }

                    @Override
                    public void onLoadFinished(Loader<P> loader, P p) {
                        if (!delivered) {
                            delivered = true;
                            BaseFragment.this.presenter = presenter;
                            if (!viewAttached) {
                                presenter.bindView((V) BaseFragment.this);
                                viewAttached = true;
                            }
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<P> loader) {
                        BaseFragment.this.presenter = null;
                    }
                });
    }
    protected abstract void initializeDependencyInjector();
    public abstract int getLayoutId();
}
