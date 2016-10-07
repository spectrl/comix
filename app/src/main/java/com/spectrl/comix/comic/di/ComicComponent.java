package com.spectrl.comix.comic.di;

import com.spectrl.comix.comic.ComicActivity;
import com.spectrl.comix.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Kavi @ SPECTRL Ltd. on 07/10/2016.
 */
@ActivityScope
@Subcomponent(
        modules = ComicModule.class
)
public interface ComicComponent {
    void inject(ComicActivity comicActivity);
}
