package fr.axonaut.axonaut.Models;

import android.support.v4.app.Fragment;

/**
 * Created by Kelian on 19/02/2018.
 */

public class FragmentInstantition {

    private Fragment fragment;
    private int idDrawer;

    public FragmentInstantition(Fragment fragment, int idDrawer) {
        this.fragment = fragment;
        this.idDrawer = idDrawer;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getIdDrawer() {
        return idDrawer;
    }

    public void setIdDrawer(int idDrawer) {
        this.idDrawer = idDrawer;
    }
}
