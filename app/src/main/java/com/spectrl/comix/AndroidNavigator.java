package com.spectrl.comix;

import android.app.Activity;

import com.spectrl.comix.comic.ComicActivity;
import com.spectrl.comix.view.Navigator;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class AndroidNavigator implements Navigator {

    private final Activity activity;

    public AndroidNavigator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openComic(int id) {
        activity.startActivity(ComicActivity.createIntent(activity));
    }
}
