package com.example.ellioc.watchlist.ui.landing;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.ellioc.watchlist.R;
import com.example.ellioc.watchlist.dagger.SubcomponentBuilderProvider;
import com.example.ellioc.watchlist.dagger.components.LandingComponent;
import com.example.ellioc.watchlist.dagger.scope.ActivityComponentBuilder;
import com.example.ellioc.watchlist.ui.base.BaseActivity;
import com.example.ellioc.watchlist.ui.search.SearchFragment;
import com.example.ellioc.watchlist.ui.search.SearchResultsFragment;
import com.example.ellioc.watchlist.ui.watchlist.ViewWatchListFragment;

import butterknife.BindView;

public class LandingActivity extends BaseActivity<LandingActivityPresenter, LandingActivityView> implements SearchFragment.OnSearchListener{

    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nvView) NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private LandingComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        drawerToggle = setUpDrawerToggle();

        SearchFragment firstFragment = SearchFragment.newInstance();
        firstFragment.setArguments(getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContent, firstFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        drawerToggle.syncState();
        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeDependencyInjector() {
        final ActivityComponentBuilder<LandingActivity, LandingComponent>
                componentBuilder = ((SubcomponentBuilderProvider) getApplicationContext())
                .getActivityComponentBuilder(LandingActivity.class);
        component = componentBuilder.build();
        component.inject(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private ActionBarDrawerToggle setUpDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    private void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass;
        String tag;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = SearchFragment.class;
                tag = "view_watch_list";
                break;
            case R.id.nav_second_fragment:
                fragmentClass = ViewWatchListFragment.class;
                tag = "search";
                break;
            default:
                //default to welcome screen
                fragmentClass = SearchFragment.class;
                tag = "welcome_screen";
        }

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    public void OnSearch(String query){
        hideKeyboard(this);
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
