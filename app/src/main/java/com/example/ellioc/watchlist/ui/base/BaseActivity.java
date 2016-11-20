package com.example.ellioc.watchlist.ui.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresenter, V extends BaseView> extends AppCompatActivity {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoader();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.bindView((V) this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    public abstract int getLayoutId();

    protected abstract void initializeDependencyInjector();

    public void setUpSupportActionBar(
            final Toolbar toolbar,
            final String toolbarTitle,
            @DrawableRes final int homeIconRes) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(toolbarTitle);
                actionBar.setHomeAsUpIndicator(homeIconRes);
            }
        }
    }
    private void initLoader() {
        getSupportLoaderManager().initLoader(getClass().getCanonicalName().hashCode(), null,
                new LoaderManager.LoaderCallbacks<P>() {

                    @Override
                    public Loader<P> onCreateLoader(final int id, Bundle args) {
                        return new PresenterLoader<>(BaseActivity.this, getClass().getCanonicalName(), presenter);
                    }

                    @Override
                    public void onLoadFinished(Loader<P> loader, final P presenter) {
                        BaseActivity.this.presenter = presenter;
                    }

                    @Override
                    public void onLoaderReset(Loader<P> loader) {
                        BaseActivity.this.presenter = null;
                    }
                });
    }

}
