package com.spectrl.comix.di.activity;

import com.spectrl.comix.collection.CollectionActivity;
import com.spectrl.comix.comic.ComicActivity;

import dagger.Subcomponent;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 */
@ActivityScope
@Subcomponent(
        modules = ActivityModule.class
)
public interface ActivityComponent {
    void inject(CollectionActivity collectionActivity);
    void inject(ComicActivity comicActivity);
    // Subcomponents
}
