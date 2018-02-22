package fr.axonaut.axonaut.UI.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.HashMap;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.Controllers.NavigationController;
import fr.axonaut.axonaut.Models.FragmentInstantition;
import fr.axonaut.axonaut.UI.Fragments.OpportunityFragment;
import fr.axonaut.axonaut.R;
import fr.axonaut.axonaut.UI.Fragments.ContactFragment;
import fr.axonaut.axonaut.UI.Fragments.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawer;
        NavigationView mNavigationView;
    NavigationController mNavigationController;

    private HashMap<String, FragmentInstantition> mFragmentMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentMap.put("Home", new FragmentInstantition(Fragment.instantiate(this,HomeFragment.class.getName()), R.id.nav_camera));
        mFragmentMap.put("Contact", new FragmentInstantition(Fragment.instantiate(this,ContactFragment.class.getName()), R.id.nav_gallery));
        mFragmentMap.put("Opportunity", new FragmentInstantition(Fragment.instantiate(this,OpportunityFragment.class.getName()), R.id.nav_slideshow));

        mNavigationController = NavigationController.getInstance();
        switchFragment(mFragmentMap.get("Opportunity"));
        setTitle("Opportunités");

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                switchFragment(mFragmentMap.get("Home"));
                setTitle("Accueil");
                break;
            case R.id.nav_gallery:
                switchFragment(mFragmentMap.get("Contact"));
                setTitle("Contacts");
                break;
            case R.id.nav_slideshow:
                switchFragment(mFragmentMap.get("Opportunity"));
                setTitle("Opportunités");
                break;
            default:
                switchFragment(mFragmentMap.get("Opportunity"));
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void switchFragment(FragmentInstantition fragment) {
        mNavigationController.setFragmentInstantition(fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content);
        if (f != fragment.getFragment()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment.getFragment());
            fragmentTransaction.commit();
            mNavigationView.setCheckedItem(fragment.getIdDrawer());
        }
    }
}
