package fr.axonaut.axonaut.Controllers;

import android.support.v4.app.Fragment;

import fr.axonaut.axonaut.Models.FragmentInstantition;

public class NavigationController {

    private int pipePosition = 0;
    private FragmentInstantition fragmentInstantition;

    private static NavigationController mInstance;

    private NavigationController() {
    }

    public static NavigationController getInstance() {
        if (mInstance == null) {
            mInstance = new NavigationController();
        }
        return mInstance;
    }

    public int getPipePosition() {
        return pipePosition;
    }

    public void setPipePosition(int pipePosition) {
        this.pipePosition = pipePosition;
    }

    public FragmentInstantition getFragmentInstantition() {
        return fragmentInstantition;
    }

    public void setFragmentInstantition(FragmentInstantition fragmentInstantition) {
        this.fragmentInstantition = fragmentInstantition;
    }


}
