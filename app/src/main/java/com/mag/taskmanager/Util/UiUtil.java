package com.mag.taskmanager.Util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UiUtil {


    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment, int containerId) {
        changeFragment(fragmentManager, fragment, containerId, true);
    }

    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment, int containerId, boolean isreplaced) {

        if (isreplaced)
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
        else
            fragmentManager
                    .beginTransaction()
                    .add(containerId, fragment)
                    .commit();

    }

}
